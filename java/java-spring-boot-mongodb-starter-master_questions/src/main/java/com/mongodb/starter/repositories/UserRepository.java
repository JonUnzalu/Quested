package com.mongodb.starter.repositories;

import com.mongodb.starter.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    User save(User user); //insert

    List<User> findAll(); //find all users

    User findOne(int user_id); // find 1 user

    long count(); // count all users

    long delete(int user_id); //delete 1 user

    User update(User user); //update 1 user

}
