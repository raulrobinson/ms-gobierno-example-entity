package co.com.telefonica.ws.ui.model.response;

import java.io.Serializable;

import co.com.telefonica.ws.util.SecurityUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase encargada de montar un objeto basico en json para dar respuesta a una petición 
 * con un mensaje de texto plano.
 * 
 * @version 1.0.0
 * @author COEArquitectura@telefonica.com
 * @since 20/08/2021
 */
@Getter
@Setter
@ToString
public class TelfOkEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Código de estado de la solicitud. Ejemplo: "200", "404"
	 */
	private String status;
	
	/**
	 * Mensaje en texto plano que se quiera transmitir
	 */
	private String message;
	
	/**
	 * Constructor de la clase
	 */
	public TelfOkEntity() {
		this.status = "200";
		this.message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";
	}
	
	/**
	 * Constructor de la clase
	 * @param status
	 * @param message
	 */
	public TelfOkEntity(String status, String message) {
		this.status = status;
		this.message = SecurityUtils.blindParameter(message);
	}
}
