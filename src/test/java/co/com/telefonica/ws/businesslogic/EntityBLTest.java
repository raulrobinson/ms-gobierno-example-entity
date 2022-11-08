package co.com.telefonica.ws.businesslogic;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import co.com.telefonica.ws.dto.TelfEntityDTO;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EntityBLTest {
	
	private EntityBL businesslogic;
	
	@BeforeAll
	public void setupAll() {
		System.out.println("Mensaje de inicio de las pruebas");
	} 
	
	@BeforeEach
	public void setup() {
		this.businesslogic = new EntityBL();
	}
	
	/**
	 * Pruebas unitarias para obtner una entidad especifica
	 */
	@Test
	@DisplayName("Consultar una entidad cuando no se ha iniciado la coleción")
	public void shouldReturnEmptyWhenQueryingAnSpecificEntityWhitCollectionEmpty() {
		Assertions.assertTrue(this.businesslogic.GetEntityById("test")==null);
		Assertions.assertEquals(0, this.businesslogic.GetAllEntities().size());
	}
	
	@Test
	@DisplayName("Consultar exitosamente una entidad especifica")
	public void shouldReturnEspecificEntity() {
		// Crear las entidades
		TelfEntityDTO newEntity = new TelfEntityDTO("Jhon", "Doe", "jhon.doe@mail.com", "password1234");
		String entityKey = this.businesslogic.CreateEntity(newEntity);
		
		// Se actualiza el objeto para dejarlo igual al esperado
		newEntity.setUserPassword(entityKey);
		
		// Comprobar que se puedan consultar
		Assertions.assertNotNull(this.businesslogic.GetEntityById(entityKey));
		Assertions.assertEquals(newEntity, this.businesslogic.GetEntityById(entityKey));
	}
	
	/**
	 * Pruebas unitarias para obtener multiples entidades
	 */
	@Test
	@DisplayName("Consultar entidades cuando no se ha iniciado la colección")
	public void shouldReturnEmptyWhenQueryingAllEntities () {
		Assertions.assertTrue(this.businesslogic.GetAllEntities().isEmpty());
		Assertions.assertEquals(0, this.businesslogic.GetAllEntities().size());
	}
	
	@Test
	@DisplayName("Consultar exitosamente multiples entidades")
	public void shouldReturnMultipleEntities() {
		// Crear las entidades
		TelfEntityDTO newEntity = new TelfEntityDTO("Jhon", "Doe", "jhon.doe@mail.com", "password1234");
		this.businesslogic.CreateEntity(newEntity);
		newEntity = new TelfEntityDTO("Miles", "Morales", "miles.morales@mail.com", "password5678");
		this.businesslogic.CreateEntity(newEntity);
		
		// Comprobar que se puedan consultar
		Assertions.assertFalse(this.businesslogic.GetAllEntities().isEmpty());
		Assertions.assertEquals(2, this.businesslogic.GetAllEntities().size());
	}
	
	/**
	 * En adelante se van a realizar los test de la clase encargada de crear entidades
	 */
	@Test
	@DisplayName("Crear exitosamente una entidad")
	public void shouldCreateEntity() {
		TelfEntityDTO newEntity = new TelfEntityDTO("Jhon", "Doe", "jhon.doe@mail.com", "password1234");
		this.businesslogic.CreateEntity(newEntity);
		Assertions.assertFalse(this.businesslogic.GetAllEntities().isEmpty());
		Assertions.assertEquals(1, this.businesslogic.GetAllEntities().size());
	}
	
	@Test
	@DisplayName("Error al crear una entidad con nombre vacío")
	public void shouldthrowRunTimeExceptionWhenUserNameIsNull () {
		TelfEntityDTO newEntity = new TelfEntityDTO(null, "Doe", "jhon.doe@mail.com", "password1234");
		
		Assertions.assertThrows(RuntimeException.class, () -> {
			this.businesslogic.CreateEntity(newEntity);
		});
	}
	
	@Test
	@DisplayName("Error al crear una entidad con apellido vacío")
	public void shouldthrowRunTimeExceptionWhenloastnameIsNull () {
		TelfEntityDTO newEntity = new TelfEntityDTO("Jhon", null, "jhon.doe@mail.com", "password1234");
		
		Assertions.assertThrows(RuntimeException.class, () -> {
			this.businesslogic.CreateEntity(newEntity);
		});
	}
	
	@Test
	@DisplayName("Error al crear una entidad con correo en formato erroneo")
	public void shouldthrowRunTimeExceptionWhenEmailIsWrong () {
		TelfEntityDTO newEntity = new TelfEntityDTO("Jhon", "Doe", "jhon.doe_mail.com", "password1234");
		
		Assertions.assertThrows(RuntimeException.class, () -> {
			this.businesslogic.CreateEntity(newEntity);
		});
	}
	
	@Test
	@DisplayName("Error al crear una entidad con contraseña corta")
	public void shouldthrowRunTimeExceptionWhenPasswordSmall () {
		TelfEntityDTO newEntity = new TelfEntityDTO("Jhon", "Doe", "jhon.doe@mail.com", "1234567");
		
		Assertions.assertThrows(RuntimeException.class, () -> {
			this.businesslogic.CreateEntity(newEntity);
		});
	}
	
	@Test
	@DisplayName("Error al crear una entidad con contraseña larga")
	public void shouldthrowRunTimeExceptionWhenPasswordLarge () {
		TelfEntityDTO newEntity = new TelfEntityDTO("Jhon", "Doe", "jhon.doe@mail.com", "12345678901234567");
		
		Assertions.assertThrows(RuntimeException.class, () -> {
			this.businesslogic.CreateEntity(newEntity);
		});
	}
	
	@Test
	@DisplayName("Error al crear una entidad con contraseña vacia")
	public void shouldthrowRunTimeExceptionWhenPasswordEmpty () {
		TelfEntityDTO newEntity = new TelfEntityDTO("Jhon", "Doe", "jhon.doe@mail.com", null);
		
		Assertions.assertThrows(RuntimeException.class, () -> {
			this.businesslogic.CreateEntity(newEntity);
		});
	}
	
	/**
	 * En adelante se van a realizar los test de la clase encargada de actulizar entidades
	 */
	@Test
	@DisplayName("Actualizar exitosamente una entidad")
	public void shouldUpdateEntity() {
		// Crear la entidad inicial
		TelfEntityDTO newEntity = new TelfEntityDTO("Jhon", "Doe", "jhon.doe@mail.com", "password1234");
		String entityKey = this.businesslogic.CreateEntity(newEntity);
		newEntity = new TelfEntityDTO("Miles", "Morales", "miles.morales@mail.com", entityKey);
		
		Assertions.assertEquals(entityKey, this.businesslogic.UpdateEntity(newEntity, entityKey));
	}
	
	@Test
	@DisplayName("Error al actualizar una entidad inexistente")
	public void shouldReturnEmptyWhenUpdateNonExistEntity() {
		// Crear la entidad inicial
		TelfEntityDTO newEntity = new TelfEntityDTO("Jhon", "Doe", "jhon.doe@mail.com", "password1234");
		String entityKey = this.businesslogic.CreateEntity(newEntity);
		newEntity = new TelfEntityDTO("Miles", "Morales", "miles.morales@mail.com", entityKey);
		
		Assertions.assertEquals("", this.businesslogic.UpdateEntity(newEntity, "test-error"));
	}
	
	@Test
	@DisplayName("Error al actualizar una entidad con nombre vacío")
	public void shouldthrowRunTimeExceptionWhenUpdateAndUserNameIsNull () {
		TelfEntityDTO initialEntity = new TelfEntityDTO("Jhon", "Doe", "jhon.doe@mail.com", "password1234");
		String entityKey = this.businesslogic.CreateEntity(initialEntity);
		TelfEntityDTO newEntity = new TelfEntityDTO(null, "Morales", "miles.morales@mail.com", entityKey);
		
		Assertions.assertThrows(RuntimeException.class, () -> {
			this.businesslogic.UpdateEntity(newEntity, entityKey);
		});
	}
	
	@Test
	@DisplayName("Error al actualizar una entidad con apellido vacío")
	public void shouldthrowRunTimeExceptionWhenUpdateAndLastnameIsNull () {
		TelfEntityDTO initialEntity = new TelfEntityDTO("Jhon", "Doe", "jhon.doe@mail.com", "password1234");
		String entityKey = this.businesslogic.CreateEntity(initialEntity);
		TelfEntityDTO newEntity = new TelfEntityDTO("Jhon", null, "jhon.doe@mail.com", "password1234");
		
		Assertions.assertThrows(RuntimeException.class, () -> {
			this.businesslogic.UpdateEntity(newEntity, entityKey);
		});
	}
	
	@Test
	@DisplayName("Error al actualizar una entidad con correo en formato erroneo")
	public void shouldthrowRunTimeExceptionWhenUpdateAndEmailIsWrong () {
		TelfEntityDTO initialEntity = new TelfEntityDTO("Jhon", "Doe", "jhon.doe@mail.com", "password1234");
		String entityKey = this.businesslogic.CreateEntity(initialEntity);
		TelfEntityDTO newEntity = new TelfEntityDTO("Jhon", "Doe", "jhon.doe_mail.com", "password1234");
		
		Assertions.assertThrows(RuntimeException.class, () -> {
			this.businesslogic.UpdateEntity(newEntity, entityKey);
		});
	}
	
	
	@AfterEach
	public void tearDown() {
		String msg = "Aquí colocaría código que se ejecute después de cada prueba, si tuviera uno!!!";
		System.out.println(msg);
	}
	
	@AfterAll
	public void tearDownAll() {
		String msg = "Algo que ejecutar cuando termine de hacer todas las pruebas";
		System.out.println(msg);
	}
}
