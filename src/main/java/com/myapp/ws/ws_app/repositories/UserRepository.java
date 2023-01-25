package com.myapp.ws.ws_app.repositories;

import com.myapp.ws.ws_app.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity  ,Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
}
