package com.gruppo1.smarthome.crud.beans;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Profile;
import com.gruppo1.smarthome.model.Room;
import com.gruppo1.smarthome.model.Scene;
import com.gruppo1.smarthome.repository.DeviceRepo;
import com.gruppo1.smarthome.repository.ProfileRepo;
import com.gruppo1.smarthome.repository.RoomRepo;
import com.gruppo1.smarthome.repository.SceneRepo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        this.context = context;
    }

    public static CrudRepository getRepository(Object item) {
        if (item instanceof Device || item.getClass().getName().contains("Device")) {
            return context.getBean(DeviceRepo.class);
        } else if (item instanceof Room || item.getClass().getName().contains("Room")) {
            return context.getBean(RoomRepo.class);
        } else if (item instanceof Scene || item.getClass().getName().contains("Scene")) {
            return context.getBean(SceneRepo.class);
        }
        else if (item instanceof Profile || item.getClass().getName().contains("Profile")) {
            return context.getBean(ProfileRepo.class);
        } else return null;
    }
}