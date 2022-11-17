package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест команд shell")
@SpringBootTest
public class ShellCommandsTest {

  @Autowired
  private Shell shell;

  private static final String COMMAND_LOGIN = "login";
  private static final String COMMAND_LOGIN_SHORT = "l";
  private static final String GREETING_PATTERN = "Hello, %s %s";
  private static final String CUSTOM_LOGIN_NAME = "Robert";
  private static final String CUSTOM_LOGIN_SURNAME = "BBB";
  private static final String COMMAND_LOGIN_PATTERN = "%s %s %s";

  @DisplayName(" ")
  @Test
  void loginCommandTest() {
    System.out.println();
    String res = (String) shell.evaluate(() -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN_SHORT, CUSTOM_LOGIN_NAME, CUSTOM_LOGIN_SURNAME ));
    assertThat(res).isEqualTo(String.format(GREETING_PATTERN, CUSTOM_LOGIN_NAME, CUSTOM_LOGIN_SURNAME));
  }



}
