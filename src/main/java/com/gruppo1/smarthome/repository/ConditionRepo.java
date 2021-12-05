package com.gruppo1.smarthome.repository;

import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.model.SmartHomeItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConditionRepo extends BaseSmartHomeRepository<Condition, String> {

    @Query("select c from Condition c where c.device.name = :deviceName")
    List<SmartHomeItem> findConditionsByDeviceName(@Param("deviceName") String deviceName);
}
