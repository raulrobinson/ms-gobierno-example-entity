package co.com.telefonica.ws.ui.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EntityController.class)
class EntityControllerTest {

	@Autowired
	private MockMvc mvc;
	@Value("${controller.properties.base-path}")
	private String operationName;
	private static final String authorization = "authorization";
	private static final String system = "system";
	private static final String operation = "operation";
	private static final String execId = "execId";
	private static final String timestamp = "timestamp";
	private static final String msgType = "msgType";
	private static final String host = "Host";
	
	private static final String authorizationValue = "authorizationTest";
	private static final String systemValue = "CoEArqSolucion";
	private static final String execIdValue = "CA761232-ED42-11CE-BACD-00AA0057B223";
	private static final String timestampValue = "2021-08-18T19:22:18.532-05:00";
	private static final String msgTypeValue = "Request";
	private static final String hostValue = "localhost";

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Validar flujo Ok en la operación de GET /operacion/all
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetEntityOk() throws Exception {
		String uriPath = "/" + operationName + "/all";

		mvc.perform(MockMvcRequestBuilders.get(uriPath)
				.header(authorization, authorizationValue)
				.header(system, systemValue)
				.header(operation, operationName)
				.header(execId, execIdValue)
				.header(timestamp, timestampValue)
				.header(msgType, msgTypeValue)
				.header(host, hostValue)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Exito-200"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("get entity called was called"))
				.andExpect(status().isOk());
	}
	
	/**
	 * flujos de fallo en la validación de los headers de la 
	 * operación GET /operacion/all
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetEntityErrorTimestamp() throws Exception {
		String uriPath = "/" + operationName + "/all";

		/* Adicionalmente retorna en el body un campo timestamp en el que retornará
		 * la fecha en el formato apropiado  
		*/
		mvc.perform(MockMvcRequestBuilders.get(uriPath)
				.header(authorization, authorizationValue)
				.header(system, systemValue)
				.header(operation, operationName)
				.header(execId, execIdValue)
				.header(timestamp, "bad date")
				.header(msgType, msgTypeValue)
				.header(host, hostValue)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ERROR: ERROR-400"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void testGetEntityErrorTimestamp2() throws Exception {
		String uriPath = "/" + operationName + "/all";

		/* Adicionalmente retorna en el body un campo timestamp en el que retornará
		 * la fecha en el formato apropiado  
		*/
		mvc.perform(MockMvcRequestBuilders.get(uriPath)
				.header(authorization, authorizationValue)
				.header(system, systemValue)
				.header(operation, operationName)
				.header(execId, execIdValue)
				.header(timestamp, "2020-10-02")
				.header(msgType, msgTypeValue)
				.header(host, hostValue)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ERROR: ERROR-400"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void testGetEntityErrorExecId() throws Exception {
		String uriPath = "/" + operationName + "/all";

		/* Adicionalmente retorna en el body un campo timestamp en el que retornará
		 * la fecha en el formato apropiado  
		*/
		mvc.perform(MockMvcRequestBuilders.get(uriPath)
				.header(authorization, authorizationValue)
				.header(system, systemValue)
				.header(operation, operationName)
				.header(execId, "bad exec Id")
				.header(timestamp, timestampValue)
				.header(msgType, msgTypeValue)
				.header(host, hostValue)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ERROR: ERROR-400"))
				.andExpect(status().isBadRequest());
	}
}

