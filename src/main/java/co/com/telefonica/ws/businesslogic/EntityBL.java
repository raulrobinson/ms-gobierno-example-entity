package co.com.telefonica.ws.businesslogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import co.com.telefonica.ws.dto.TelfEntityDTO;

/**
 * Clase en la que se gestiona la lógica de negocio del microservicio o la aplicación.
 * */
public class EntityBL {

	/**
	 * Colleccion para almacenar en cache los elementos ingresados.
	 */
	Map<String, TelfEntityDTO> collectionMap;
	
	private void nullPointerExceptionControll() {
		if (collectionMap == null) {
			collectionMap = new HashMap<>();
		}
	}

	/**
	 * Método para obtener un objeto del collectionMap a partir de la llave.
	 * 
	 * @param entityId
	 * @return
	 */
	public TelfEntityDTO GetEntityById(String entityId) {
		TelfEntityDTO returnValue = new TelfEntityDTO();
		
		nullPointerExceptionControll();

		if (collectionMap.containsKey(entityId)) {
			returnValue = collectionMap.get(entityId);

			return returnValue;
		} else {
			return null;
		}
	}

	/**
	 * Consultar todos los elmentos contenidos en el collectionMap
	 * 
	 * @return
	 */
	public List<TelfEntityDTO> GetAllEntities() {
		List<TelfEntityDTO> returnValue = new ArrayList<TelfEntityDTO>();
		
		nullPointerExceptionControll();

		returnValue = new ArrayList<>(collectionMap.values());

		return returnValue;
	}

	/**
	 * Método para insertar registros nuevos en la colección de objetos
	 * 
	 * @param entityToCreate
	 * @return
	 */
	public String CreateEntity(TelfEntityDTO entityToCreate) {
		UUID entityId = UUID.randomUUID();
		String returnValue = entityId.toString();
		
		// Validar las propiedades recibidas
		entityToCreate.validateUserName();
		entityToCreate.validateUserLastName();
		entityToCreate.validateUserEmail();
		entityToCreate.validateUserPassword();

		// Se ajusta así para poder mostrar las llaves al momento de hacer la consulta
		entityToCreate.setUserPassword(returnValue);

		nullPointerExceptionControll();

		collectionMap.put(returnValue, entityToCreate);

		return returnValue;
	}

	/**
	 * Método para realizar la actualización de la entidad
	 * 
	 * @param dataToUpdate
	 * @param entityId
	 * @return
	 */
	public String UpdateEntity(TelfEntityDTO dataToUpdate, String entityId) {
		TelfEntityDTO returnValue = new TelfEntityDTO();
		
		// Validar la entidad que se recibe
		dataToUpdate.validateUserName();
		dataToUpdate.validateUserLastName();
		dataToUpdate.validateUserEmail();
		
		nullPointerExceptionControll();
		
		if (collectionMap.containsKey(entityId)) {
			// Si encuentra la entidad, actualiza la información
			returnValue = collectionMap.get(entityId);
			returnValue.setUserName(dataToUpdate.getUserName());
			returnValue.setUserLastName(dataToUpdate.getUserLastName());
			returnValue.setUserEmail(dataToUpdate.getUserEmail());

			collectionMap.put(entityId, returnValue);

			return entityId;
		} else {
			// En caso de que no encuentre la entidad, retorna un arreglo vacio.
			return "";
		}
	}

	/**
	 * Eliminar una entidad de la colección de objetos a partir del Id del objeto.
	 * 
	 * @param entityId
	 * @return
	 */
	public boolean deleteEntity(String entityId) {
		nullPointerExceptionControll();
		
		if (collectionMap.containsKey(entityId)) {
			collectionMap.remove(entityId);
			return true;
		}

		return false;
	}
}
