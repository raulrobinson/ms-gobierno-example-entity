package co.com.telefonica.ws.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
/**
 * NO BORRAR
 * 
 * Clase encargada de mapear las operaciones para fallback
 * 
 * @autor: COE-Arquitectura-Telefonica
 * @date: 19-10-2020
 * @version 1.0.0
 */
public class LogServiceDelegate {
	
	@Value("${log.endPointConnectTimeout}")
	private static int httpConnectTimeout;
	
	@Value("${log.endPointReadTimeout}")
	private static int httpReadTimeout;

	
	private static final String  LOG_FALLBACK ="Error Fallback"; 
	
	 @Bean
	 public RestTemplate restTemplate() {
		  return new RestTemplate(getClientHttpRequestFactory());
	 }
	 
	 /**
	  * Contiene la configuración de Timeout de lectura y conexión
	  * @return clientHttpRequestFactory
	  */
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
	    
		clientHttpRequestFactory.setConnectTimeout(httpConnectTimeout);
	    clientHttpRequestFactory.setReadTimeout(httpReadTimeout);
	    
	    return clientHttpRequestFactory;
	}
	
	/**
	 * Contiene la configuración para fakllback para comando Histrix	
	 * @param <T>
	 * @param type
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "writeLogFallback")
	public <T> ResponseEntity<T> writeLog(String type ) {
		
			ResponseEntity<T> response = null;			
	
			if(type.equals("info"))					
				log.error(LOG_FALLBACK, HttpMethod.POST, type, LogServiceDelegate.class);
			
			
			return response;
					
		}
		/**
		 * Escribe el log en caso de fallo, dependiendo el tipo de excepción generada se atrapa y se imprime en los logs
		 * @param <T>
		 * @param throwable
		 * @return
		 */
		public <T> ResponseEntity<T> writeLogFallback(Throwable throwable) {
			
			if (throwable instanceof com.netflix.hystrix.exception.HystrixBadRequestException) {
				
				log.error(LOG_FALLBACK, HttpMethod.POST, "Bad Request", LogServiceDelegate.class);
				
			}else if (throwable instanceof com.netflix.hystrix.exception.HystrixRuntimeException) {
				log.error(LOG_FALLBACK, HttpMethod.POST, "Runtime Exception", LogServiceDelegate.class);
				
			}else  if (throwable instanceof com.netflix.hystrix.exception.HystrixTimeoutException) {
				log.error(LOG_FALLBACK, HttpMethod.POST, "Timeout", LogServiceDelegate.class);
				
			}else if(throwable instanceof com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix) {
				log.error(LOG_FALLBACK, HttpMethod.POST, "Wrapped", LogServiceDelegate.class);
			
			}						
			
			return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT) ;
		}

}
