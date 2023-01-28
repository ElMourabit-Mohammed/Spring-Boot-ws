package com.myapp.ws.ws_app.shared;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Utils {

    public String stringIdGenerated(){
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID;
    }


}
