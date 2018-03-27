package com.raven.web.messaging;

import com.raven.web.dto.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserMessage {

    private UserInfoDTO user;
    private String dateTimeString;

}