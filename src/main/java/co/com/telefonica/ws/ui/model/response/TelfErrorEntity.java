package co.com.telefonica.ws.ui.model.response;

import co.com.telefonica.ws.util.UtilHeader;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Response Body para manejar errores.
 * Se define esta clase como referencia, pero se debe modificar la estructura de la 
 * clase en función del diseño de integración o de las necesidades de la aplicación.
 * 
 * @version 1.0.0
 * @author COEArquitectura@telefonica.com
 * @since 19/05/2021
 * */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor()
public class TelfErrorEntity {
	/**
	 * Fecha y hora en la que se registra el error en formato YYYY-MM-DDThh:mm:ss[Z|(+|-)hh:mm]
	 */
	private String timestamp;
	
	/**
	 * Mensaje diciente con el que se describe el error.
	 */
	private String message;
	
	/**
	 * Método constructor en el que solo se recibe el mensaje de error que se desea mostrar
	 * y se establece la fecha y hora en la que se genera el error
	 * @param message
	 */
	public TelfErrorEntity(String message) {
		this.timestamp = new UtilHeader().getTimestampValue();
		this.message = "ERROR: " + message;
	}

}
