package com.example.decliviacloud.DecliviaCloud.Cruds.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public int CreateUser(User user) throws Exception {

        if (!userRepository.existsByUserNameOrEmail(user.getUserName(), user.getEmail())) {
            return userRepository.save(user).getId();
        }

        throw new Exception("$Ya existe un usuario con las mismas credenciales");
    }

    /**
     * Método para buscar un usuario por sus credenciales (nombre y contraseña)
     * @param userName: Nombre del usuario que se está buscando
     * @param password: Contraseña del usuario
     * @return El usuario que coincida con las credenciales. Nulo si no coincide
     */
    public UserRecord FindUserByCredentials(String userName, String password) {

        // Hacemos la búsqueda del usuario según las credenciales
        User user = userRepository.getByUserNameAndPassword(userName, password);

        // Si el usuario existe, lo mapeamos al record y lo devolvemos
        if(user != null) {
            return UserMapper.ConvertUserToRecord(user);
        }

        // Si no existe, devolvemos null
        return null;
    }
}
