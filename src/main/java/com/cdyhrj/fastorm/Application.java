package com.cdyhrj.fastorm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.addListeners(new ApplicationReadyEventListener());
        app.run(args);
    }

    static class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
            Environment env = event.getApplicationContext().getEnvironment();
            String configPath = env.getProperty("spring.config.location");
            if (configPath == null) {
                configPath = env.getProperty("spring.config.name") + "." + env.getProperty("spring.config.format");
            } else {
                configPath = configPath.split(",")[0];
            }
            System.out.println("Config file path: " + configPath);
        }
    }
}
