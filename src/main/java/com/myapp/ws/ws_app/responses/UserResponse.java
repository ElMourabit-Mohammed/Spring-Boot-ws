package com.myapp.ws.ws_app.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
