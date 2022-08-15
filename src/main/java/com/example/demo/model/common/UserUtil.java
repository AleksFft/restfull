package com.example.demo.model.common;

import com.example.demo.model.User;
import lombok.experimental.UtilityClass;
import ru.vtb.dbo.jwt.model.ExternalUserInfo;

@UtilityClass
public class UserUtil {

    /**
     * This method make and return {@link User} object, which represent migration system.
     *
     * @return system user
     */
    public User system() {
        return new User()
                .setId("cf981379-68ed-42b0-b283-c733af367c6a")
                .setName("system");
    }

    /**
     * Map user info into internal user representation.
     *
     * @param userInfo user info
     * @return user
     */
    public User toUser(ExternalUserInfo userInfo) {
        return new User()
                .setId(userInfo.getUid())
                .setName(userInfo.getUsername());
    }
}