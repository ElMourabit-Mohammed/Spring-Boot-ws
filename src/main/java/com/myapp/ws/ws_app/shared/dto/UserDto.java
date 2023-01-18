package com.myapp.ws.ws_app.shared.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {


    @Serial
    private static final long serialVersionUID = 1687940050034654259L;
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken ;
    private Boolean emailVerificationStatus = false;
}
