package com.raven.web.event;

import com.raven.web.messaging.NewUserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class RegistrationEventListener implements ApplicationListener<RegistrationEvent> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private GsonHttpMessageConverter gsonConverter;

    @Override
    public void onApplicationEvent(RegistrationEvent event) {
        String datetimeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        messagingTemplate.convertAndSend("/topic/newusers",
                gsonConverter.getGson().toJson(new NewUserMessage(event.getUser(), datetimeString)));
    }

}
