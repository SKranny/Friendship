package FriendshipService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FriendshipApplication {
    public static void main(String[] args) {
        SpringApplication.run(FriendshipApplication.class, args);
    }
}
