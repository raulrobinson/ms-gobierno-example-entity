package co.com.telefonica.ws.ui.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase encargada de almacenar en un objeto serializable de transporte, del body de entrada
 * 
 * @version 1.1.0
 * @author COEArquitectura@telefonica.com
 * @since 24/03/2022
 */
@Getter
@Setter
public class TelfEntityRequest {
	
	
	/**
	 *  Reemplazar parameter por el nombre de la variable , esto afecta los gets y sets
	 */
	@NotNull(message="${entity.parameter1NotNull}")
	@ApiModelProperty(notes="Nombre de usuario", required=true, example="Nombre", position=0)
	private String parameter1;
	
	/**
	 *  Reemplazar parameter por el nombre de la variable , esto afecta los gets y sets
	 */
	@NotNull(message="${entity.parameter2NotNull}")
	@ApiModelProperty(notes="Apellidos del usuario", required=true, example="Apellidos", position=1)
	private String parameter2;
	
	/**
	 * Reemplazar parameter por el nombre de la variable , esto afecta los gets y sets
	 */
	@Email
	@NotNull(message="${entity.parameter3NotNull}")
	@ApiModelProperty(notes="Correo electrónico del usuario", required=true, example="usuario@mail.com", position=2)
	private String parameter3;
	
	/**
	 *  Reemplazar parameter por el nombre de la variable , esto afecta los gets y sets
	 */
	@NotNull(message="${entity.parameter4NotNull}")
	@Size(min=8,max=16, message="${entity.parameter4Size}")
	@ApiModelProperty(notes="password del usuario o llave del registro", required=true, example="password", position=3)
	private String parameter4;
		
}
