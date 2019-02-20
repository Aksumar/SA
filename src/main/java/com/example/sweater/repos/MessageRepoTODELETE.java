package com.example.sweater.repos;

import com.example.sweater.domain.MessageTODELETE;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface MessageRepoTODELETE extends CrudRepository<MessageTODELETE, Integer> {
    List<MessageTODELETE> findByTag(String tag);
}
