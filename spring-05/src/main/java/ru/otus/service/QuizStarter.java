package ru.otus.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;
import ru.otus.config.AppProps;
import ru.otus.domain.Question;
import ru.otus.domain.Student;

@Service
@ShellComponent
public class QuizStarter {

  private final TextPrinter textPrinter;
  private final AnswerReader answerReader;
  private final AppProps properties;
  private final MessageSource messageSource;
  private final QuestionReader questionReader;
  private Student student = new Student("", "");

  @Autowired
  public QuizStarter(TextPrinter textPrinter, AnswerReader answerReader, MessageSource messageSource, AppProps props,
      QuestionReader questionReader) {
    this.textPrinter = textPrinter;
    this.answerReader = answerReader;
    this.properties = props;
    this.messageSource = messageSource;
    this.questionReader = questionReader;
  }

  @ShellMethod(value = "Login command.", key = {"l", "login"})
  public String login(@ShellOption String name, @ShellOption String surname) {
    //textPrinter.print(messageSource.getMessage("greetings", null, properties.getLocale()) + name + " " + surname);
    student = new Student(name, surname);
    String greetings = messageSource.getMessage("greetings", null, properties.getLocale()) + ", ";
    return greetings + name + " " + surname;
  }

  @ShellMethod(value = "Starting quiz command.", key = {"s", "start"})
  public void start() {
    if(!isLoggedStudent()) {
      return;
    }
    List<Question> questions = questionReader.read();
    int countCorrectAnswers = askQuestions(questions);
    printResult(countCorrectAnswers, student);
    student = null;
  }

  private boolean isLoggedStudent() {
    return student != null;
  }

  private void printResult(int countCorrectAnswers, Student student) {
    String you = messageSource.getMessage("you", null, properties.getLocale());
    String correctAnswers = messageSource.getMessage("correctAnswers", null,properties.getLocale());
    String passed = messageSource.getMessage("passed", null,properties.getLocale());
    String failed = messageSource.getMessage("failed", null,properties.getLocale());
    textPrinter.print(student.getName() + " " + student.getSurname() + ", " + you + " " + countCorrectAnswers + " " + correctAnswers);
    textPrinter.print(countCorrectAnswers >= properties.getCountQuestionForPass() ? passed : failed);
  }

  private int askQuestions(List<Question> questions) {
    int correctAnswers = 0;
    for (Question q : questions) {
      textPrinter.print(ConverterQuestionToString.convert(q));
      String answer = answerReader.readAnswer();
      if(answer.equals(q.getCorrectAnswer())) {
        correctAnswers++;
      }
    }
    return correctAnswers;
  }
}
