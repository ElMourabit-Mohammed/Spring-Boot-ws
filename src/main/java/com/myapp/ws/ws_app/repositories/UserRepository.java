package com.myapp.ws.ws_app.repositories;

import com.myapp.ws.ws_app.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <UserEntity  ,Long> {
    UserEntity findByEmail(String email);
}
