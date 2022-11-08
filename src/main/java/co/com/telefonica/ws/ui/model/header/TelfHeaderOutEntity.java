package co.com.telefonica.ws.ui.model.header;

import java.io.Serializable;

import co.com.telefonica.ws.util.UtilHeader;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
/**
 * Clase encargada de almacenar en un objeto serializable de transporte los
 * datos del header de salida.
 * 
 * @version 1.0.0
 * @author COEArquitectura@telefonica.com
 * @since 18/08/2021
 */
public class TelfHeaderOutEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Indica el momento en el que se da respuesta. El formato que se maneja es:
	 * CCYY-MM-DDThh:mm:ss.s[Z|(+|-)hh:mm]
	 * Ejemplo: "2014-01-31T09:30:47.233+01:00"
	 */
	private String timestamp;   
	
	/**
	 * Indica el tipo de interacción del microservicio, por ejemplo Response
	 */
	private String msgType;
	
	
	
	/**
	 * 
	 * Constructor de la clase
	 * 
	 * @param destination
	 * @param execId
	 */
	public TelfHeaderOutEntity() {
		super();

		this.timestamp = new UtilHeader().getTimestampValue();
		this.msgType = "Response";
	}
	
	
	public void updateTimeStamp() {
		this.timestamp = new UtilHeader().getTimestampValue();
	}
}
