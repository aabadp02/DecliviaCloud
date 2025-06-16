package com.example.decliviacloud.DecliviaCloud.Users;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUserNameOrEmail(String userName, String email);

    User getByUserNameAndPassword(String userName, String password);
}


