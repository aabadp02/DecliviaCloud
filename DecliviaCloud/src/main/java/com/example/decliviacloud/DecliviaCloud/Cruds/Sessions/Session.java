package com.example.decliviacloud.DecliviaCloud.Cruds.Sessions;

import com.example.decliviacloud.DecliviaCloud.Cruds.Users.User;
import jakarta.persistence.*;

@Entity
@Table(name="sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @OneToOne
    @JoinColumn(name="userId", nullable=false, unique = true)
    private User user;

    @Column(name="token")
    private String token;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
