package com.pluralsight.orderfulfillment.config;

import javax.inject.Inject;

import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pluralsight.orderfulfillment.order.OrderStatus;

/**
 * Class for configuring Camel.
 * 
 * CamelConfiguration class adds support for initialisation Camel Context as part of the 
 * Spring container. 
 * 
 * @author Charlie Cooper
 */
@Configuration
public class IntegrationConfig extends CamelConfiguration {

	   @Inject
	   private javax.sql.DataSource dataSource;

	   /**
	    * SQL Component instance used for routing orders from the orders database
	    * and updating the orders database.
	    * 
	    * @return
	    */
	   @Bean
	   public SqlComponent sql() {
	      SqlComponent sqlComponent = new SqlComponent();
	      sqlComponent.setDataSource(dataSource);
	      return sqlComponent;
	   }

	   /**
	    * Camel RouteBuilder for routing orders from the orders database. Routes any
	    * orders with status set to new, then updates the order status to be in
	    * process. The route sends the message exchange to a log component.
	    * 
	    * @return
	    */
	   @Bean
	   public org.apache.camel.builder.RouteBuilder newWebsiteOrderRoute() {
	      return new org.apache.camel.builder.RouteBuilder() {

	         @Override
	         public void configure() throws Exception {
	            // Send from the SQL component to the Log component.
	            from(
	                  "sql:"
	                        + "select id from orders.\"order\" where status = '" + OrderStatus.NEW.getCode() + "'"
	                        + "?" + "consumer.onConsume=update orders.\"order\" set status = '" + OrderStatus.PROCESSING.getCode()
	                        + "' where id = :#id").
                to("log:com.pluralsight.orderfulfillment.order?level=INFO");
	         }
	      };
	   }
}
