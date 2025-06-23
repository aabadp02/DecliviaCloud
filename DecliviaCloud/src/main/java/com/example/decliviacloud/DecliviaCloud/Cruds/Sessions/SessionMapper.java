package com.example.decliviacloud.DecliviaCloud.Cruds.Sessions;

import com.example.decliviacloud.DecliviaCloud.Cruds.Users.UserMapper;

public class SessionMapper {

    public static SessionRecord ConvertSessionToRecord(Session session) {
        return new SessionRecord(
            session.getId(),
            UserMapper.ConvertUserToRecord(session.getUser()),
            session.getToken()
        );
    }

    public static Session ConvertRecordToSession(SessionRecord sessionRecord) {

        Session result = new Session();

        if(sessionRecord != null) {

            if(sessionRecord.Id() != null) {
                result.setId(sessionRecord.Id());
            }

            result.setUser(UserMapper.ConvertRecordToUser(sessionRecord.user()));
            result.setToken(sessionRecord.token());
        }

        return result;
    }
}
