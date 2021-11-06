package com.gruppo1.smarthome.crud.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository<SmartHomeItem, String>
        extends CrudRepository<SmartHomeItem, String> {

    Optional<SmartHomeItem> findById(String entityId);
    Optional<SmartHomeItem> findByName(String entityName);
    Integer deleteByName (String entityName);
    long count();

}
