package com.example.decliviacloud.DecliviaCloud.Users;

public class UserMapper {

    /**
     * Método para convertir un objeto de la entidad usuario a su dto
     * @param user Entidad usuario que queremos convertir
     * @return objeto UserDto con los datos correspondientes
     */
    public static UserRecord ConvertUserToRecord(User user) {

        return new UserRecord(
            user.getId(),
            user.getUserName(),
            user.getEmail(),
            user.getPassword(),
            user.isAdmin()
        );
    }

    /**
     * Método para convertir un objeto de tipo UsuarioDto en su entidad
     * @param userRecord Dto que queremos convertir en entidad
     * @return Entidad usuario con los datos del dto que hemos recibido por parámetros.
     */
    public static User ConvertRecordToUser(UserRecord userRecord) {

        User result = new User();

        result.setId(userRecord.Id());
        result.setUserName(userRecord.userName());
        result.setEmail(userRecord.email());
        result.setPassword((userRecord.password()));
        result.setAdmin(userRecord.isAdmin());

        return result;
    }

}
