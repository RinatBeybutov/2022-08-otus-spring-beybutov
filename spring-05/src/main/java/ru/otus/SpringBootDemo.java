package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.config.AppProps;
import ru.otus.service.QuizStarter;

@SpringBootApplication
@EnableConfigurationProperties(AppProps.class)
public class SpringBootDemo {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootDemo.class, args);
        QuizStarter quizStarter = context.getBean(QuizStarter.class);
        quizStarter.start();
    }
}
