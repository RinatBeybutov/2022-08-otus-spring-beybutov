package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.config.AppProps;
import ru.otus.domain.Student;

@ShellComponent
public class QuizStarter {

  private final AppProps properties;
  private final MessageSource messageSource;
  private final QuizService quizService;
  private Student student = new Student("", "");

  @Autowired
  public QuizStarter(MessageSource messageSource, AppProps props,
      QuizService quizService) {
    this.properties = props;
    this.messageSource = messageSource;
    this.quizService = quizService;
  }

  @ShellMethod(value = "Login command.", key = {"l", "login"})
  public String login(@ShellOption String name, @ShellOption String surname) {
    student = new Student(name, surname);
    String greetings = messageSource.getMessage("greetings", null, properties.getLocale()) + ", ";
    return greetings + name + " " + surname;
  }

  @ShellMethod(value = "Starting quiz command.", key = {"s", "start"})
  @ShellMethodAvailability("isLoggedCheck")
  public void start() {
    quizService.start(student);
    student = null;
  }

  public Availability isLoggedCheck(){
    return student != null && !student.getName().equals("") && !student.getSurname().equals("") ? Availability.available() : Availability.unavailable("User must be logged");
  }
}
