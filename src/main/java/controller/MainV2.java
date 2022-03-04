package controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainV2 {
    public static void main(String[] args) {
        SpringApplication.run(MainV2.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            ControllerV2 mainController = ctx.getBean(ControllerV2.class);
            mainController.process();
        };
    }
}
