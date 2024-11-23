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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
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
  
  public static class CustomRemoteWebDriver extends RemoteWebDriver {
        public CustomRemoteWebDriver(URL remoteAddress, DesiredCapabilities capabilities) {
            super(remoteAddress, capabilities);
        }

        public void setSessionId(String sessionId) {
            super.setSessionId(sessionId);
        }
    }

    public static void main(String[] args) {
        try {
            // Step 1: Manually define the WebDriver server URL
            String webdriverServerUrl = "http://127.0.0.1:4444/wd/hub";

            // Step 2: Create a new driver and start a session
            FirefoxOptions options = new FirefoxOptions();
            WebDriver tempDriver = new RemoteWebDriver(new URL(webdriverServerUrl), options);

            // Step 3: Retrieve session ID and print it
            String sessionId = ((RemoteWebDriver) tempDriver).getSessionId().toString();
            System.out.println("Original Session ID: " + sessionId);

            // Step 4: Quit the temporary driver
            tempDriver.quit();

            // Step 5: Reconnect to the same session
            DesiredCapabilities capabilities = new DesiredCapabilities();
            CustomRemoteWebDriver reconnectedDriver = new CustomRemoteWebDriver(new URL(webdriverServerUrl), capabilities);
            reconnectedDriver.setSessionId(sessionId);

            // Step 6: Perform actions with the reconnected driver
            reconnectedDriver.get("http://www.example.com");
            System.out.println("Title after reconnection: " + reconnectedDriver.getTitle());

            // Step 7: Quit the reconnected driver
            reconnectedDriver.quit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    String kp_url = "https://www.kupujemprodajem.com/login";
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
    

    //Temporary WebDriver for session params
    WebDriver temp_driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), options);
    //WebDriver driver = new FirefoxDriver(options);
    String sessionId = ((RemoteWebDriver) temp_driver).getSessionId().toString();
    URL remote_url = new URL(kp_url);
    
    //temp_driver.quit();
    
    WebDriver hijacked_driver = new RemoteWebDriver(remote_url, options);
    ((RemoteWebDriver) hijacked_driver).setSessionId(sessionId);
    
    hijacked_driver.get(kp_url);
    
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

