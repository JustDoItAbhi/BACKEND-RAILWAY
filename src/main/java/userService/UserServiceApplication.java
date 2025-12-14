package userService;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
//		(exclude = SecurityAutoConfiguration.class)
@EnableCaching
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
