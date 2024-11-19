package com.google_auth_selenium;

import com.selenium_base.selenium_base_class;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.firefox.FirefoxProfile;
import java.time.Duration;
import java.util.List;

public class Main extends selenium_base_class{
  public static String Browser = "firefox";
  public static String driver_path = "/usr/bin/geckodriver";
  public String[] browser_options = null; 

  public static WebDriver driver;
  
  //Choose what account (email/pass from .env) to use when authenticating 
  //public static WebDriver auth_by_login(String email, WebDriver driver){
    //will remove later
  static String email_test = "mihajlosanspasic@gmail.com";
    
  //};
  

  public static void main(String[] args){
    
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
    
    WebElement button_google_auth= driver.findElement(By.className("google"));
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

    String email = "mihajlosanspasic@gmail.com";
    try{
       List<WebElement> inputs = driver.findElements(By.tagName("input"));

            boolean emailEntered = false;

            for (WebElement input : inputs) {
                if (input.isDisplayed() && input.isEnabled()) {
                    String type = input.getAttribute("type");

                    if (type != null && (type.equals("text") || type.equals("email") || type.equals("search"))) {
                        input.sendKeys(email);
                        System.out.println("Email entered in a field.");
                        emailEntered = true;
                        break;
                    }
                }
            }

    } catch(Exception e){
      System.out.println("ERROR MESSAGE: "+ e.getMessage());
    }



  }
}

