package com.myapp.ws.ws_app.repositories;

import com.myapp.ws.ws_app.entities.AddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity , Long> {
}
