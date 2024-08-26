package makarov.springsecurity.config;

import makarov.springsecurity.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public CommandLineRunner initializeDatabase() {
        return args -> userService.initializeDB();
    }
}
