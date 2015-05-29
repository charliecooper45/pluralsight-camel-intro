package com.pluralsight.orderfulfillment.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Main application configuration for the order fulfillment processor.
 * 
 * @author Michael Hoffman, Pluralsight
 * 
 */
@Configuration
@ComponentScan(basePackages = "com.pluralsight.orderfulfillment")
@PropertySource("classpath:order-fulfillment.properties")
public class Application {

}
