package co.com.telefonica.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para facilitar el movimiento de información entre las diferentes capas del proyecto
 * 
 * @version 1.0.0
 * @author COEArquitectura@telefonica.com
 * @since 19/05/2021
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor()
public class TelfEntityDTO {

	/**
	 * Nombre de usuario
	 */
	private String userName;
	
	/**
	 * Apellidos del usuario
	 */
	private String userLastName;
	
	/**
	 * Correo electrónico del usuario
	 */
	private String userEmail;
	
	/**
	 * contraseña del usuario
	 */
	private String userPassword;
	
	/**
	 * Validaciones del nombre de usuario.
	 */
	public void validateUserName() {
		if (this.userName.isBlank())
			throw new RuntimeException("User name cannot be null or empty");
	}
	
	/**
	 * Validaciones en la propiedad del apellido
	 */
	public void validateUserLastName() {
		if (this.userLastName.isBlank())
			throw new RuntimeException("user lastname cannot be null or empty");
	}
	
	/**
	 * Validaciones en la propiedad del correo electrónico
	 */
	public void validateUserEmail() {
		if (!this.userEmail.contains("@"))
			throw new RuntimeException("the email is not on the proper format");
	}
	
	/**
	 * Validaciones en la propiedad del password
	 */
	public void validateUserPassword() {
		if (this.userPassword.isBlank())
			throw new RuntimeException("Password cannot be null or empty");
		
		if (this.userPassword.length() < 8)
			throw new RuntimeException("Password is too small");
		
		if (this.userPassword.length() > 16)
			throw new RuntimeException("Password is too large");
	}
}
