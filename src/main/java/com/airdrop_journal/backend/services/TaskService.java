package com.airdrop_journal.backend.services;

import com.airdrop_journal.backend.dtos.task.TaskRequest;
import com.airdrop_journal.backend.dtos.task.TaskResponse;
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
}
