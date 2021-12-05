package com.gruppo1.smarthome.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseSmartHomeRepository<SmartHomeItem, String>
        extends CrudRepository<SmartHomeItem, String> {

    Optional<SmartHomeItem> findById(String entityId);

    Optional<SmartHomeItem> findByName(String entityName);

    int deleteByName(String entityName);

    long count();

}
