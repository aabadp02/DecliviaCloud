package com.example.decliviacloud.DecliviaCloud.Cruds.Sessions;

import com.example.decliviacloud.DecliviaCloud.Cruds.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {

    Session getByUser(User user);

    void deleteByUser(User user);
}
