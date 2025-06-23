package com.example.decliviacloud.DecliviaCloud.Cruds.Sessions;

import com.example.decliviacloud.DecliviaCloud.Cruds.Users.UserMapper;
import com.example.decliviacloud.DecliviaCloud.Cruds.Users.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public boolean ValidateTokenByUser(UserRecord userRecord, String token) {

        if(userRecord != null) {
            Session session = sessionRepository.getByUser(UserMapper.ConvertRecordToUser(userRecord));

            if(session != null) {
                return Objects.equals(token, session.getToken());
            }
        }

        return false;
    }


    /**
     * Método para abrir una nueva sesión para el usuario.
     * En caso de que ya hubiese una abierta, la eliminará y la sustituirá.
     * @param sessionRecord
     */
    public void OpenSession(SessionRecord sessionRecord) {

        if(sessionRecord != null){

            Session session = SessionMapper.ConvertRecordToSession(sessionRecord);

            // Si el usuario ya tenía una sesión abierta, la borramos
            if(sessionRepository.getByUser(session.getUser()) != null) {

                sessionRepository.deleteByUser(session.getUser());
            }

            // Guardamos la nueva sesión del usuario
            sessionRepository.save(session);
        }

    }
}
