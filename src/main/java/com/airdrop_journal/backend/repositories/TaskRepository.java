package com.airdrop_journal.backend.repositories;

import com.airdrop_journal.backend.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    List<Task> findByUserId(String userId);
    Optional<Task> findByIdAndUserId(String id, String userId);
    void deleteByAirdropIdAndUserId(String airdropId, String userId);

    boolean existsByIdAndUserId(String taskId, String id);

    long countByUserId(String userId);
    long countByUserIdAndCompleted(String userId, boolean completed);
    long countByUserIdAndIsDaily(String userId, boolean isDaily);

}
