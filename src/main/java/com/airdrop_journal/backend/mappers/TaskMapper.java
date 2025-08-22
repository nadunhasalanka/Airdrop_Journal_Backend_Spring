package com.airdrop_journal.backend.mappers;

import com.airdrop_journal.backend.dtos.task.TaskRequest;
import com.airdrop_journal.backend.dtos.task.TaskResponse;
import com.airdrop_journal.backend.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toTask(TaskRequest request) {
        if (request == null) return null;
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .project(request.getProject())
                .completed(request.isCompleted())
                .isDaily(request.isDaily())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .notes(request.getNotes())
                .airdropId(request.getAirdropId())
                .tags(request.getTags())
                .category(request.getCategory())
                .estimatedTime(request.getEstimatedTime())
                .difficulty(request.getDifficulty())
                .reward(request.getReward())
                .build();
    }

    public TaskResponse toTaskResponse(Task task) {
        if (task == null) return null;
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setProject(task.getProject());
        response.setCompleted(task.isCompleted());
        response.setDaily(task.isDaily());
        response.setPriority(task.getPriority());
        response.setDueDate(task.getDueDate());
        response.setCompletedAt(task.getCompletedAt());
        response.setNotes(task.getNotes());
        response.setAirdropId(task.getAirdropId());
        response.setUserId(task.getUserId());
        response.setTags(task.getTags());
        response.setCategory(task.getCategory());
        response.setEstimatedTime(task.getEstimatedTime());
        response.setDifficulty(task.getDifficulty());
        response.setReward(task.getReward());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        return response;
    }
}
