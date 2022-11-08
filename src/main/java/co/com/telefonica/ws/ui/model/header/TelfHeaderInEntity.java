package co.com.telefonica.ws.ui.model.header;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.http.HttpHeaders;

import co.com.telefonica.ws.util.SecurityUtils;

/**
 * Clase encargada de almacenar en un objeto serializable de transporte los
 * datos del header de entrada.
 * 
 * @version 1.0.0
 * @author COEArquitectura@telefonica.com
 * @since 19/05/2021
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TelfHeaderInEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Parametro en el que se almacena el valor del header de authorization.
	 */
	private String paramAu;
	
	/**
	 * Nombre del sistema que realiza la solicitud
	 */
	private String system;
	
	/**
	 * Identificador de la operación ofrecida por el servicio
	 * Ejemplo: "EscribirSolicitudPC"
	 */
	private String operation;
	
	/**
	 * Identificador único de la petición
	 * Formato: UUID en forma canónica con 32 dígitos hexadecimales, 
	 * mostrados en cinco grupos separados por guiones, 
	 * de la forma 8-4-4-4-12 para un total de 36 caracteres 
	 * (32 dígitos y 4 guiones medios).
	 */
	private String execId;
	
	/**
	 * Marca de tiempo correspondiente al envío del mensaje. El formato es el 
	 * ISO_OFFSET_DATE_TIME que se representa de la siguiente forma:
	 * YYYY-MM-DDThh:mm:ss[Z|(+|-)hh:mm]
	 */
	private String timestamp;
	
	/**
	 * Indica el tipo de mensaje y está relacionado con el escenario de 
	 * uso y modo de interacción.
	 * Ejemplo: REQUEST
	 */
	private String msgType;

	/**
	 * Constructor que recibe un objeto headers y lo transforma a DTO.
	 * 
	 * @param headers, información que viene del HTTP headers
	 */
	public TelfHeaderInEntity(HttpHeaders headers) {
		super();
		this.paramAu = SecurityUtils.blindParameter(headers.getFirst("authorization"));
		this.system = SecurityUtils.blindParameter(headers.getFirst("system"));
		this.operation = SecurityUtils.blindParameter(headers.getFirst("operation"));
		this.execId = SecurityUtils.blindParameter(headers.getFirst("execId"));
		this.timestamp = SecurityUtils.blindParameter(headers.getFirst("timestamp"));
		this.msgType = SecurityUtils.blindParameter(headers.getFirst("msgType"));
	}

}
