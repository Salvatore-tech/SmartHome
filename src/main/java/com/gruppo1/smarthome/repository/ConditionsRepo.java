package com.gruppo1.smarthome.repository;

import com.gruppo1.smarthome.crud.api.BaseSmartHomeRepository;
import com.gruppo1.smarthome.model.Conditions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConditionsRepo extends BaseSmartHomeRepository<Conditions, String> {
    @Query("select c from Conditions c where c.device.name = :deviceName")
    List<Conditions> findConditionsByDeviceName(@Param("deviceName")String deviceName);

}
