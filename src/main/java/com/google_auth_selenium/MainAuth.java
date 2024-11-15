package com.google_auth_selenium;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class MainAuth implements google_auth_interface{
  
  private void setDriverLocation(String path){
    if (webBrowser == "firefox")
      System.setProperty("webdriver.gecko.driver", path);
    else
      System.setProperty("webdriver.chrome.driver", path);
  }

  public static WebDriver setDriver(){
    return new FirefoxDriver();  
  } 
  
  public static WebDriver setDriver(FirefoxOptions options){
    return new FirefoxDriver(options);
  }
  
  public static FirefoxOptions setOptions(){
    return new FirefoxOptions(); 
  }
  
  public static FirefoxOptions setOptions(String[] args){
    
    FirefoxOptions options = new FirefoxOptions();

    for (String option: args){
      if (option.charAt(0) == "-")
        options.addArguments(option);
      else
        options.addArguments("-" + option);
    }
    return options;
  }
  
  

  public static void  main(String[] args){

  }
}
