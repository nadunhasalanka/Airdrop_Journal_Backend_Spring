package com.airdrop_journal.backend.repositories;

import com.airdrop_journal.backend.model.Airdrop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirdropRepository extends MongoRepository<Airdrop, String> {

    List<Airdrop> findByUserId(String userId);
    Optional<Airdrop> findByIdAndUserId(String id, String userId);
}
