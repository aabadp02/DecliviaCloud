package com.example.decliviacloud.DecliviaCloud.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.GET)
    public UserRecord FindUserById(@PathVariable int id) {
        return new UserRecord(1, "", "", "", false);
    }

    @RequestMapping(value = "api/users", method = RequestMethod.POST)
    public int CreateUser(@RequestBody UserRecord user) throws Exception {

        User userEntity = UserMapper.ConvertRecordToUser(user);

        return userService.CreateUser(userEntity);
    }
}
