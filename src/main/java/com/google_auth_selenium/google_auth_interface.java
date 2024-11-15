package com.google_auth_selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public interface google_auth_interface{
  
  public void setDriverLocation(String path);
  
  public static FirefoxOptions setOptions();
  public static FirefoxOptions setOptions(String[] args);
  
  public static WebDriver setDriver();
  public static WebDriver setDriver(FirefoxOptions options);
 
  default static void auth_by_redirect() {};
  default static void auth_by_redirect(WebDriver driver,String redirect_url) {
    driver.get(redirect_url);
    auth_by_main(driver);
  };
  
  default static void auth_by_main() {};
  default static void auth_by_main(WebDriver driver) {};
    
} 
