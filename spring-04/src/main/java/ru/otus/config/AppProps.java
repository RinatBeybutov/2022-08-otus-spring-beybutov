package ru.otus.config;

import java.util.Locale;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class AppProps {

  private int countQuestionForPass;
  private Locale locale;
  private String engLocalizationFile;
  private String ruLocalizationFile;

  public String getEngLocalizationFile() {
    return engLocalizationFile;
  }

  public void setEngLocalizationFile(String engLocalizationFile) {
    this.engLocalizationFile = engLocalizationFile;
  }

  public String getRuLocalizationFile() {
    return ruLocalizationFile;
  }

  public void setRuLocalizationFile(String ruLocalizationFile) {
    this.ruLocalizationFile = ruLocalizationFile;
  }

  public static final int COUNT_QUESTIONS = 5;

  public int getCountQuestionForPass() {
    return countQuestionForPass;
  }

  public void setCountQuestionForPass(int countQuestionForPass) {
    this.countQuestionForPass = countQuestionForPass;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }
}
