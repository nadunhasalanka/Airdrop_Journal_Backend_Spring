package com.airdrop_journal.backend.services;

import com.airdrop_journal.backend.dtos.task.TaskRequest;
import com.airdrop_journal.backend.dtos.task.TaskResponse;
import com.airdrop_journal.backend.dtos.task.TaskStatsResponse;
import com.airdrop_journal.backend.mappers.TaskMapper;
import com.airdrop_journal.backend.model.Task;
import com.airdrop_journal.backend.model.User;
import com.airdrop_journal.backend.repositories.AirdropRepository;
import com.airdrop_journal.backend.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final AirdropRepository airdropRepository; // To validate airdropId
    private final TaskMapper taskMapper;

    @Transactional
    public TaskResponse createTask(TaskRequest request, User currentUser) {
        if (request.getAirdropId() != null) {
            if (!airdropRepository.existsByIdAndUserId(request.getAirdropId(), currentUser.getId())) {
                throw new RuntimeException("Invalid airdrop reference.");
            }
        }

        Task task = taskMapper.toTask(request);
        task.setUserId(currentUser.getId());
        task.setCreatedAt(Instant.now());

        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskResponse(savedTask);
    }

    public List<TaskResponse> getTasksForUser(User currentUser) {
        return taskRepository.findByUserId(currentUser.getId())
                .stream()
                .map(taskMapper::toTaskResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse getTaskById(String taskId, User currentUser) {
        Task task = taskRepository.findByIdAndUserId(taskId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Task not found or you do not have permission."));
        return taskMapper.toTaskResponse(task);
    }

    @Transactional
    public TaskResponse updateTask(String taskId, TaskRequest request, User currentUser) {
        Task existingTask = taskRepository.findByIdAndUserId(taskId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Task not found or you do not have permission."));

        existingTask.setTitle(request.getTitle());
        existingTask.setDescription(request.getDescription());
        existingTask.setProject(request.getProject());
        existingTask.setCompleted(request.isCompleted());
        existingTask.setUpdatedAt(Instant.now());

        if (request.isCompleted() && existingTask.getCompletedAt() == null) {
            existingTask.setCompletedAt(Instant.now());
        } else if (!request.isCompleted()) {
            existingTask.setCompletedAt(null);
        }

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toTaskResponse(updatedTask);
    }

    @Transactional
    public void deleteTask(String taskId, User currentUser) {
        if (!taskRepository.existsByIdAndUserId(taskId, currentUser.getId())) {
            throw new RuntimeException("Task not found or you do not have permission.");
        }
        taskRepository.deleteById(taskId);
    }

    public TaskStatsResponse getTaskStats(User currentUser) {
        String userId = currentUser.getId();

        // 1. Get the simple counts directly from the repository
        long totalTasks = taskRepository.countByUserId(userId);
        long completedTasks = taskRepository.countByUserIdAndCompleted(userId, true);
        long pendingTasks = taskRepository.countByUserIdAndCompleted(userId, false);
        long dailyTasks = taskRepository.countByUserIdAndIsDaily(userId, true);
        int completionPercentage = (totalTasks > 0) ? (int) Math.round((double) completedTasks / totalTasks * 100) : 0;

        // 2. For grouped counts, fetch all tasks and process them in memory.
        // This is efficient for a reasonable number of tasks per user.
        List<Task> allUserTasks = taskRepository.findByUserId(userId);

        // Group by category and count
        Map<String, Long> byCategory = allUserTasks.stream()
                .filter(task -> task.getCategory() != null)
                .collect(Collectors.groupingBy(task -> task.getCategory().name(), Collectors.counting()));

        // Group by project and count
        Map<String, Long> byProject = allUserTasks.stream()
                .filter(task -> task.getProject() != null && !task.getProject().isEmpty())
                .collect(Collectors.groupingBy(Task::getProject, Collectors.counting()));

        // 3. Build the response DTO
        return TaskStatsResponse.builder()
                .total(totalTasks)
                .completed(completedTasks)
                .pending(pendingTasks)
                .daily(dailyTasks)
                .completionPercentage(completionPercentage)
                .byCategory(byCategory)
                .byProject(byProject)
                .build();
}
}
