package com.raven.web.event;

import com.raven.web.dto.UserInfoDTO;
import org.springframework.context.ApplicationEvent;

public class RegistrationEvent extends ApplicationEvent {

    private UserInfoDTO user;

    public RegistrationEvent(Object source, UserInfoDTO user) {
        super(source);
        this.user = user;
    }

    public UserInfoDTO getUser() {
        return user;
    }

}
