package co.com.telefonica.ws.ui.model.response;

import co.com.telefonica.ws.util.TelcoSecurityUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
public class OkEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/* Código de estado de la solicitud. Ejemplo: "200", "404" */
	private String status;

	private String message;

	public OkEntity() {
		this.status = "200";
		this.message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";
	}
	
	/**
	 * Constructor de la clase
	 * @param status String
	 * @param message String
	 */
	public OkEntity(String status, String message) {
		this.status = status;
		this.message = TelcoSecurityUtils.blindParameter(message);
	}
}
