package com.example.decliviacloud.DecliviaCloud.Cruds.Users;

import com.example.decliviacloud.DecliviaCloud.System.Exceptions.DecliviaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Método para crear un usuario
     * @param user record con lso datos el usuario que se queire crear
     * @return El id del usuario usuario que acabamos de crear
     * @throws DecliviaException: Excepción que salta si las credenciales ya se han utilizado en alguno de los usuarios creados
     */
    public int CreateUser(UserRecord user) throws DecliviaException {

        // Comprobamos que no exista ya un usuario con ese nombre o emial y si no existe, lo creamos
        if (user != null && !userRepository.existsByUserNameOrEmail(user.userName(), user.email())) {
            return userRepository.save(UserMapper.ConvertRecordToUser(user)).getId();
        }

        // En caso de que ya exista, lanzamos excepción indicándolo
        throw new DecliviaException("El nombre de usuario o el email ya están en uso");
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
