package com.pluralsight.orderfulfillment.config;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Class for configuring Camel.
 * 
 * @author Charlie Cooper
 */
@Configuration
public class IntegrationConfig extends CamelConfiguration {

	@Inject
	private Environment environment;
	
	@Override
	public List<RouteBuilder> routes() {
		List<RouteBuilder> routeList = new ArrayList<>();
		
		routeList.add(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				// ?noop=true do not move or delete the file from its folder
				// file endpoint
				from("file://" + environment.getProperty("order.fulfillment.center.1.outbound.folder") + "?noop=true")
				.to("file://" + environment.getProperty("order.fulfillment.center.1.outbound.folder") + "/test");
			}
		});
		
		return routeList;
	}
}
