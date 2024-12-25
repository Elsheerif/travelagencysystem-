
package com.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import com.demo.model.Event;
import com.demo.model.HotelRoom;
import com.demo.model.Notification;
import com.demo.model.User;
import com.demo.util.JsonDataStore;

@Configuration
public class JsonStoreConfig {
    @Value("${data.file.events:classpath:data/events.json}")
    private String eventFilePath;
    
    @Value("${data.file.hotels:classpath:data/hotels.json}")
    private String hotelRoomFilePath;
    
    @Value("${data.file.users:classpath:data/users.json}")
    private String userFilePath;
    
    @Value("${data.file.notifications:classpath:data/notifications.json}")
    private String notificationFilePath;
    
    private final ResourceLoader resourceLoader;


    public JsonStoreConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public JsonDataStore<Event> eventStore() {
        return new JsonDataStore<>(resourceLoader, eventFilePath);
    }

    @Bean
    public JsonDataStore<HotelRoom> hotelRoomStore() {
        return new JsonDataStore<>(resourceLoader, hotelRoomFilePath);
    }

    @Bean
    public JsonDataStore<User> userStore() {
        return new JsonDataStore<>(resourceLoader, userFilePath);
    }

    @Bean
    public JsonDataStore<Notification> notificationStore() {
        return new JsonDataStore<>(resourceLoader, notificationFilePath);
    }
}