package co.com.telefonica.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;


@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties
@ComponentScan
@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
/** 
 * 
 * Clase iniciadora de una aplicación Spring Boot
 * @version 1.0.0
 * @author COEArquitectura@telefonica.com
 * @since 19/05/2021
 */
public class AppWsApplication extends AbstractHealthIndicator {

	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		
		 builder.up()
         .withDetail("app", "App ws")
         .withDetail("sucess", "OK");
		 
	}	
	/**
	 * Metodo principal, corre aplicación en Spring Boot
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AppWsApplication.class, args);
	}	

	/**
	 * Metodo encargado de generar las trazas para hystrix
	 * @return ServletRegistrationBean
	 */
	@Bean(name = "hystrixRegistrationBean")
	public ServletRegistrationBean<HystrixMetricsStreamServlet> servletRegistrationBean() {
	    ServletRegistrationBean<HystrixMetricsStreamServlet> registration = new ServletRegistrationBean<>(
	            new HystrixMetricsStreamServlet(), "/hystrix.stream");
	    registration.setName("hystrixServlet");
	    registration.setLoadOnStartup(1);
	    return registration;
	}
	
}
