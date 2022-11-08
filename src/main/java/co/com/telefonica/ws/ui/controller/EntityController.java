package co.com.telefonica.ws.ui.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.telefonica.ws.service.LogServiceDelegate;
import co.com.telefonica.ws.ui.model.header.TelfHeaderInEntity;
import co.com.telefonica.ws.ui.model.request.TelfEntityRequest;
import co.com.telefonica.ws.ui.model.response.TelfEntityResponse;
import co.com.telefonica.ws.ui.model.response.TelfErrorEntity;
import co.com.telefonica.ws.ui.model.response.TelfOkEntity;
import co.com.telefonica.ws.util.SecurityUtils;
import co.com.telefonica.ws.util.UtilHeader;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase encargada de mapear las operaciones verbos del servicio REST.
 * 
 * @autor: COE-Arquitectura-Telefonica
 * @date: 19-10-2020
 * @version 2.0.0
 */

@RestController
@RequestMapping(path = "${controller.properties.base-path}", produces = MediaType.APPLICATION_JSON_VALUE) 
@Slf4j
@Service
@Validated
public class EntityController {

	// Colleccion para almacenar en cache los elementos ingresados.
	Map<String, TelfEntityResponse> collectionMap;
	// Declara un encargado de generar los logs
	@Autowired
	LogServiceDelegate logServiceDelegate;

	@Value("${error.headers}")
	private static int errorHeader;

	private static final String ERROR = "Error ";
	private static final String ERROR400 = "ERROR-400";
	private static final String EXITO200 = "Exito-200";

	TelfHeaderInEntity user;

	UtilHeader utilHeader;

	/**
	 * Accept: application/xml
	 * 
	 * @method Este metodo es encargado de mapear la operacion GET
	 *         /operacion/{parameter}?parameter=parameter
	 **/
	@GetMapping(path = "/{parameter}")
	@ApiOperation(value = "Consultar por ID", notes = "Esta operación consulta una entidad por ID")
	public ResponseEntity<Object> getEntityById(@RequestHeader HttpHeaders headers, @PathVariable String parameter) {
		String msgBody = "";
		TelfOkEntity bodyResp = null;
		TelfEntityResponse returnValue = null;
		String blindParam = SecurityUtils.blindParameter(parameter);
		TelfErrorEntity errorMsg = null;

		try {
			new UtilHeader().processHeaderOut(headers);

		} catch (IOException exception) {
			log.error(ERROR, errorHeader, exception);

			msgBody = "Error al validar los headers";
			bodyResp = new TelfOkEntity(ERROR400, msgBody);

			return ResponseEntity.badRequest().headers(new UtilHeader().buildHeaderOut()).body(bodyResp);
		}

		if (collectionMap == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(new UtilHeader().buildHeaderOut())
					.build();

		if (collectionMap.containsKey(blindParam)) {
			returnValue = collectionMap.get(blindParam);

			return ResponseEntity.ok().headers(new UtilHeader().buildHeaderOut()).body(returnValue);

		} else {

			log.error(ERROR, "Parametro : " + blindParam + " NOT FOUND");
			errorMsg = new TelfErrorEntity("No se encontraron registros asociados al Id: " + blindParam);

			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(new UtilHeader().buildHeaderOut())
					.body(errorMsg);

		}

	}

	/**
	 * @method Este metodo es encargado de mapear la operacion GET
	 *         /operacion?page=10&limit=5&sort=test
	 **/
	@GetMapping()
	@ApiOperation(value = "Consulta con filtros", notes = "Esta operación consulta una entidad utilizando diferentes filtros")
	public ResponseEntity<TelfOkEntity> getEntityByFilters(@RequestHeader HttpHeaders headers,
			@RequestParam(value = "page", defaultValue = "-1") int page,
			@RequestParam(value = "limit", defaultValue = "-1") int limit,
			@RequestParam(value = "sort", required = false) String sort) {

		String msgBody = "";
		TelfOkEntity bodyResp = null;

		String blindParamSort = SecurityUtils.blindParameter(sort);
		try {
			new UtilHeader().processHeaderOut(headers);
		} catch (IOException exception) {
			msgBody = "get parametros called was called with fail in header";
			log.error(ERROR400, ERROR400, exception);
			bodyResp = new TelfOkEntity(ERROR400, msgBody);

			return ResponseEntity.badRequest().headers(new UtilHeader().buildHeaderOut()).body(bodyResp);
		}

		if (page == -1 || limit == -1) {
			msgBody = "get parametros called was called";
			bodyResp = new TelfOkEntity(ERROR400, msgBody);

			return ResponseEntity.badRequest().headers(new UtilHeader().buildHeaderOut()).body(bodyResp);
		} else {
			msgBody = "get parametros called was called page=" + page + " and limit=" + limit + " and sort="
					+ blindParamSort;
			bodyResp = new TelfOkEntity(EXITO200, msgBody);

			return ResponseEntity.ok().headers(new UtilHeader().buildHeaderOut()).body(bodyResp);
		}
	}

	/**
	 * @method Este metodo es encargado de mapear la operacion GET /operacion/all
	 **/
	@GetMapping(path = "/all")
	@ApiOperation(value = "Consultar todo", notes = "Esta operación consulta todas las entidades")
	public ResponseEntity<Object> getAllEntities(@RequestHeader HttpHeaders headers) {
		String msgBody = "";
		TelfOkEntity bodyResp = null;
		try {
			new UtilHeader().processHeaderOut(headers);
		} catch (IOException exception) {
			msgBody = ERROR400;
			log.error(ERROR, msgBody, exception);
			TelfErrorEntity errorResponse = new TelfErrorEntity(msgBody);

			return ResponseEntity.badRequest().headers(new UtilHeader().buildHeaderOut()).body(errorResponse);
		}

		bodyResp = new TelfOkEntity(EXITO200, "get entity called was called");

		return ResponseEntity.ok().headers(new UtilHeader().buildHeaderOut()).body(bodyResp);
	}

	/**
	 * @method Este metodo es encargado de mapear la operacion POST /operacion
	 **/
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Crear entidad", notes = "Aquí pondría una descripción... si tuviera una!!!")
	public ResponseEntity<Object> createEntity(@RequestHeader HttpHeaders headers,
			@ApiParam("Información de la entidad para que se vea en swagger") @RequestBody TelfEntityRequest entityDetails) {
		TelfOkEntity bodyResp = null;

		try {
			new UtilHeader().processHeaderOut(headers);
		} catch (IOException exception) {

			log.error(ERROR, "Bad Request: Los parametros del Header no Cumplen con la especificación Técnica",
					exception);

			return ResponseEntity.badRequest().headers(new UtilHeader().buildHeaderOut())
					.body(new TelfEntityResponse());
		}

		TelfEntityResponse returnValue = new TelfEntityResponse();
		UUID value = UUID.randomUUID();
		returnValue.setParametro1(entityDetails.getParameter1());
		returnValue.setParametro2(entityDetails.getParameter2());
		returnValue.setParametro3(entityDetails.getParameter3());
		returnValue.setParametro4(value.toString());

		if (collectionMap == null)
			collectionMap = new HashMap<>();
		collectionMap.put(value.toString(), returnValue);

		bodyResp = new TelfOkEntity(EXITO200, "Se ha insertado el registro con valor: " + value);

		return ResponseEntity.ok().headers(new UtilHeader().buildHeaderOut()).body(bodyResp);

	}

	/**
	 * @method Este metodo es encargado de mapear la operacion PUT
	 *         /operacion/{parametro1}
	 **/
	@PutMapping(path = "/{entityId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Actualizar entidad", notes = "Esta operación realiza una actualización de la entidad")
	public ResponseEntity<Object> updateEntity(@RequestHeader HttpHeaders headers, @PathVariable String entityId,
			@Valid @RequestBody TelfEntityRequest entityDetails) {

		TelfOkEntity bodyResp = null;
		String validatedId = SecurityUtils.blindParameter(entityId);

		try {
			// Se valida y procesa el header.
			new UtilHeader().processHeader(headers);
		} catch (IOException exception) {
			log.error(ERROR, errorHeader, exception);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		TelfEntityResponse returnValue = null;
		if (collectionMap.containsKey(validatedId)) {
			returnValue = collectionMap.get(validatedId);

			if (entityDetails.getParameter1() != null)
				returnValue.setParametro1(entityDetails.getParameter1());
			if (entityDetails.getParameter2() != null)
				returnValue.setParametro2(entityDetails.getParameter2());

			collectionMap.put(validatedId, returnValue);

			bodyResp = new TelfOkEntity(EXITO200, "Se ha actualizado el registro con valor: " + validatedId);

			return ResponseEntity.ok().headers(new UtilHeader().buildHeaderOut()).body(bodyResp);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * @method Este metodo es encargado de mapear la operacion DELETE
	 *         /operacion/{parametro1}
	 **/
	@DeleteMapping(path = "/{entityId}")
	@ApiOperation(value = "Eliminar entidad", notes = "Hasta la vista... Baby")
	public ResponseEntity<TelfEntityResponse> deleteEntity(@RequestHeader HttpHeaders headers,
			@PathVariable String entityId) {

		String validatedId = SecurityUtils.blindParameter(entityId);
		try {
			// Se valida y procesa el header.
			new UtilHeader().processHeader(headers);
		} catch (IOException exception) {
			log.error(ERROR, errorHeader, exception);
			return ResponseEntity.badRequest().headers(new UtilHeader().buildHeaderOut()).build();
		}

		if (collectionMap.containsKey(validatedId)) {
			return new ResponseEntity<>(collectionMap.remove(validatedId), HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().headers(new UtilHeader().buildHeaderOut()).build();
		}

	}

}
