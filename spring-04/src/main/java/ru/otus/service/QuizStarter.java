package ru.otus.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.AppProps;
import ru.otus.domain.Question;
import ru.otus.domain.Student;

@Service
public class QuizStarter {

  private final TextPrinter textPrinter;
  private final AnswerReader answerReader;
  private final AppProps properties;
  private final MessageSource messageSource;
  private final QuestionReader questionReader;

  @Autowired
  public QuizStarter(TextPrinter textPrinter, AnswerReader answerReader, MessageSource messageSource, AppProps props,
      QuestionReader questionReader) {
    this.textPrinter = textPrinter;
    this.answerReader = answerReader;
    this.properties = props;
    this.messageSource = messageSource;
    this.questionReader = questionReader;
  }

  public void start() {
    List<Question> questions = questionReader.read();
    Student student = getStudent();
    int countCorrectAnswers = askQuestions(questions);
    printResult(countCorrectAnswers, student);
  }

  private Student getStudent() {
    textPrinter.print(messageSource.getMessage("greetings", null, properties.getLocale()));
    String enteredString = answerReader.readAnswer();
    String[] studentAttributes = enteredString.split(" ");
    return new Student(studentAttributes[0], studentAttributes[1]);
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
