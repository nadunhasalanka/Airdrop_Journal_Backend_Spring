package com.airdrop_journal.backend.repositories;


import com.airdrop_journal.backend.model.UserTag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTagRepository extends MongoRepository<UserTag, String> {

    List<UserTag> findByUserId(String userId);
    Optional<UserTag> findByNameAndUserId(String name, String userId);
}
