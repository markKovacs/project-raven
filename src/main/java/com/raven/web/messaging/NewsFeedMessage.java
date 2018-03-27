package com.raven.web.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsFeedMessage {

    private String userName;
    private String content;
    private String dateTimeString;

}
