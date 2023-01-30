package com.myapp.ws.ws_app.repositories;

import com.myapp.ws.ws_app.entities.AddressEntity;
import com.myapp.ws.ws_app.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity , Long> {
    List<AddressEntity> findByUser(UserEntity currentUser);
}
