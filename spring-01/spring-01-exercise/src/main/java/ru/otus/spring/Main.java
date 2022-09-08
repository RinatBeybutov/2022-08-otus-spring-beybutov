package ru.otus.spring;

//import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.IOException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.config.CSVConfig;
import ru.otus.spring.service.PersonService;

public class Main {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        PersonService service = context.getBean(PersonService.class);

        CSVConfig csvService = context.getBean(CSVConfig.class);

        byte[] bytes = Main.class.getResourceAsStream(csvService.getCsvPath()).readAllBytes();

        String str = new String(bytes);

        String[] lines = str.split("\n");

        for (String line : lines) {
            String[] fiels = line.split(";");
            System.out.printf("Question %s: %s\n \t%s or %s\n",fiels[0], fiels[1], fiels[2], fiels[3]);
        }

    }
}
