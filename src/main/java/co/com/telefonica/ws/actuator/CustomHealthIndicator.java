/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.com.telefonica.ws.actuator;


import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

/**
 * NO BORRAR
 * 
 * Clase encargada de exponer una operación que siempre retorna un código de estado
 * 200, sin aplicar ningún tipo de lógica. Dicha operación es usada por la plataforma
 * de contenerización para validar el estado del microservicio, y comprobar si 
 * presenta algún tipo de bloqueo.
 * 
 * @version 1.0.0
 * @author COEArquitectura@telefonica.com
 * @since 19/05/2021
 * */
@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {

	/**
	 * Responder un 200 Ok, sin resolver ninguna lógica en el medio.
	 * 
	 * @param builder
	 * @throws Exception
	 * */
	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		
		 builder.up()
         .withDetail("app", "App ws")
         .withDetail("sucess", "OK")
         .withDetail("", "");
		 
	}	
}
