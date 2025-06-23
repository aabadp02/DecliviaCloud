package com.example.decliviacloud.DecliviaCloud.Cruds.Sessions;

import com.example.decliviacloud.DecliviaCloud.Cruds.Users.UserRecord;

public record SessionRecord(Integer Id, UserRecord user, String token) {}
