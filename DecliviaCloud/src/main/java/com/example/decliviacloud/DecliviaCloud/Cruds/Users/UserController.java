package com.example.decliviacloud.DecliviaCloud.Cruds.Users;

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
}
