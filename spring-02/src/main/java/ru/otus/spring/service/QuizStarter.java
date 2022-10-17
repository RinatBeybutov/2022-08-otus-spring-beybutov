package ru.otus.spring.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

@Service
public class QuizStarter {

  private final QuestionReader questionReader;
  private final Printer printer;
  private final AnswerReader answerReader;
  private final int passAnswers;

  @Autowired
  public QuizStarter(QuestionReader questionReader, Printer printer, AnswerReader answerReader,
      @Value("${countQForPass}") int passAnswers) {
    this.questionReader = questionReader;
    this.printer = printer;
    this.answerReader = answerReader;
    this.passAnswers = passAnswers;
  }

  public void start() {
    List<Question> questions = questionReader.read();
    String name = enterName();
    int countCorrectAnswers = askQuestions(questions);
    printResult(countCorrectAnswers, name);
  }

  private String enterName() {
    printer.print("Enter name and surname:");
    return answerReader.read();
  }

  private void printResult(int countCorrectAnswers, String name) {
    printer.print(name + ", you have " + countCorrectAnswers + " correct answers");
    printer.print(countCorrectAnswers >= passAnswers ? "You passed" : "You failed");
  }

  private int askQuestions(List<Question> questions) {
    int correctAnswers = 0;
    for (Question q : questions) {
      printer.print(q.toString());
      String answer = answerReader.read();
      if(answer.equals(q.getCorrectAnswer())) {
        correctAnswers++;
      }
    }
    return correctAnswers;
  }

  private void print(List<Question> questions) {
    questions.forEach(q -> printer.print(q.toString()));
  }
}
