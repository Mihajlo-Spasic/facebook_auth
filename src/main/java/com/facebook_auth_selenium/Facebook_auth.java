package com.facebook_auth_selenium;

import com.selenium_base.selenium_base_class;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.firefox.FirefoxProfile;
import java.time.Duration;
import java.util.Set;
import java.util.List;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class Facebook_auth extends selenium_base_class{

  public static String Browser = "firefox";
  public static String driver_path = "/usr/bin/geckodriver";
  public String[] browser_options = null; 
  
  public static WebDriver driver;
  public static String url;
  public static String email = null;
  public static String password = null;
  
  Facebook_auth(WebDriver driver, String url){
    this.driver = driver;
    this.url = url;
  }

  public void load_credentials(){

    Properties properties = new Properties();
    try (InputStream input = Facebook_auth.class.getClassLoader().getResourceAsStream("config.properties")) {
      if (input == null) {
          System.out.println("Sorry, unable to find config.properties");
        return;
      }
      properties.load(input);
      email = properties.getProperty("facebook.email");
      password = properties.getProperty("facebook.password");
      
    } catch (IOException ex) {
          ex.printStackTrace();
          
    };
  }

  public void load_credentials_env(){
    email = System.getenv("facebook.email");
    password = System.getenv("facebook.password");
  }

  private void click_button_humanized(String search_text, int timeout){
    WebElement button = driver.findElement(By.id(search_text));
    button.click();
    try {
      Thread.sleep(timeout);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
  }

  public void execute_auth(){
    driver.get(url);
    
    WebElement button_facebook_auth = driver.findElement(By.className("facebook"));
    String originalWindow = driver.getWindowHandle();
    button_facebook_auth.click();
    try {
      Thread.sleep(10000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    Set<String> allWindows = driver.getWindowHandles(); 
    
    for (String windowHandle : allWindows) {
      if (!windowHandle.equals(originalWindow)) {
        driver.switchTo().window(windowHandle);
        break;
        }
      }
    
    WebElement email_input = driver.findElement(By.id("email"));
    email_input.sendKeys(email);
    
    try {
      Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    WebElement password_input = driver.findElement(By.id("pass"));
    password_input.sendKeys(password);
    
    try {
      Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    WebElement continue_button = driver.findElement(By.id("loginbutton")); 
    continue_button.click();

    try {
      Thread.sleep(10000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    
    WebElement continue_as_button = driver.findElement(By.xpath("//span[contains(text(),'Continue as')]"));
    continue_as_button.click(); 
    
    try {
      Thread.sleep(10000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }

      driver.switchTo().window(originalWindow);
  }
}
