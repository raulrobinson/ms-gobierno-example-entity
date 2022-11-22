package co.com.telefonica.ws.ui.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase encargada de mapear las operaciones verbos del servicio REST.
 *
 * @version 1.1.0
 * @author COEArquitectura@telefonica.com
 * @since 22/11/2022
 */
@Slf4j
@Service
@Validated
@RestController
@RequestMapping(path = "${controller.properties.base-path}", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntityController {

	/**
	 * Accept: application/xml
	 * 
	 * @method Este metodo es encargado de mapear la operacion GET
	 *         /operacion/{parameter}?parameter=parameter
	 **/
	@GetMapping(path = "/{parameter}")
	@ApiOperation(value = "Consultar por ID", notes = "Esta operación consulta una entidad por ID")
	public ResponseEntity<Object> getEntityById(
			@RequestHeader HttpHeaders headers,
			@PathVariable String parameter) {
		return null;
	}

	/**
	 * @method Este metodo es encargado de mapear la operacion GET
	 *         /operacion?page=10&limit=5&sort=test
	 **/
	@GetMapping()
	@ApiOperation(value = "Consulta con filtros", notes = "Esta operación consulta una entidad utilizando diferentes filtros")
	public ResponseEntity<Object> getEntityByFilters(
			@RequestHeader HttpHeaders headers,
			@RequestParam(value = "page", defaultValue = "-1") int page,
			@RequestParam(value = "limit", defaultValue = "-1") int limit,
			@RequestParam(value = "sort", required = false) String sort) {
		return null;
	}

	/**
	 * @method Este metodo es encargado de mapear la operacion GET /operacion/all
	 **/
	@GetMapping(path = "/all")
	@ApiOperation(value = "Consultar todo", notes = "Esta operación consulta todas las entidades")
	public ResponseEntity<Object> getAllEntities(
			@RequestHeader HttpHeaders headers) {
		return null;
	}

	/**
	 * @method Este metodo es encargado de mapear la operacion POST /operacion
	 **/
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Crear entidad", notes = "Aquí pondría una descripción... si tuviera una!!!")
	public ResponseEntity<Object> createEntity(
			@RequestHeader HttpHeaders headers,
			@ApiParam("Información de la entidad para que se vea en swagger")
			@RequestBody Object entityDetails) {
		return null;
	}

	/**
	 * @method Este metodo es encargado de mapear la operacion PUT
	 *         /operacion/{parametro1}
	 **/
	@PutMapping(path = "/{entityId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Actualizar entidad", notes = "Esta operación realiza una actualización de la entidad")
	public ResponseEntity<Object> updateEntity(
			@RequestHeader HttpHeaders headers,
			@PathVariable String entityId,
			@Valid @RequestBody Object entityDetails) {
		return null;
	}

	/**
	 * @method Este metodo es encargado de mapear la operacion DELETE
	 *         /operacion/{parametro1}
	 **/
	@DeleteMapping(path = "/{entityId}")
	@ApiOperation(value = "Eliminar entidad", notes = "Hasta la vista... Baby")
	public ResponseEntity<Object> deleteEntity(
			@RequestHeader HttpHeaders headers,
			@PathVariable String entityId) {
		return null;
	}

}
