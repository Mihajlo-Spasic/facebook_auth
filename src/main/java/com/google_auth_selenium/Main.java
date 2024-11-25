package com.google_auth_selenium;

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

public class Main extends selenium_base_class{
  public static String Browser = "firefox";
  public static String driver_path = "/usr/bin/geckodriver";
  public String[] browser_options = null; 

  public static WebDriver driver;
  
  

  public static void main(String[] args){
    
    String email = null;
    String password = null;

    Properties properties = new Properties();

    try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
      if (input == null) {
        System.out.println("Sorry, unable to find config.properties");
        return;
      }
      properties.load(input);
      email = properties.getProperty("facebook.email");
      password = properties.getProperty("facebook.password");
      } catch (IOException ex) {
          ex.printStackTrace();
        }
    String url = "https://www.kupujemprodajem.com/login";
    //Main base = new Main();
    //WebDriver driver = base.setUp(base.Browser, base.driver_path, base.browser_options); 
    //driver.get(url);
    String profilePath = "/home/spale/.mozilla/firefox/hljh1xxy.default";
    System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
    FirefoxProfile profile = new FirefoxProfile(new java.io.File(profilePath));
    FirefoxOptions options = new FirefoxOptions();
    
    options.addPreference("dom.webdriver.enabled", false);
    options.addPreference("useAutomationExtension", false);
    options.setCapability("moz:firefoxOptions", options);
    profile.setPreference("toolkit.telemetry.reportingpolicy.firstRun", false);
    options.setProfile(profile);
    
    WebDriver driver = new FirefoxDriver(options);
    driver.get(url);
    
    WebElement button_google_auth= driver.findElement(By.className("facebook"));
    String originalWindow = driver.getWindowHandle();
    button_google_auth.click();
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
    
    System.out.println("Current URL: " + driver.getCurrentUrl());
    

    WebElement email_input= driver.findElement(By.id("email"));
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
  }
}

