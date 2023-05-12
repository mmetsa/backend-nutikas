package ee.nutikas.games;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendGamesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendGamesApiApplication.class, args);
    }

}
