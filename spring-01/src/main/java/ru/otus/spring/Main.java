package ru.otus.spring;

import java.io.IOException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.Starter;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        Starter starter = context.getBean(Starter.class);
        starter.start();
    }
}
