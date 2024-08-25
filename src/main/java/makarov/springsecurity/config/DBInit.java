package makarov.springsecurity.config;

import makarov.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBInit {

    @Autowired
    public DBInit(UserService userService) {
        userService.initializeDB();
    }
}
