package com.example.decliviacloud.DecliviaCloud.Cruds.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUserNameOrEmail(String userName, String email);

    User getByUserNameAndPassword(String userName, String password);

    User getByUserName(String userName);
}


