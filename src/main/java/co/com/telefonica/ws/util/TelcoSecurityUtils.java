package co.com.telefonica.ws.util;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

/**
 * NO BORRAR
 * Clase de utilidades de Seguridad.
 *
 * @version 1.1.0
 * @author COEArquitectura@telefonica.com
 * @since 22/11/2022
 **/
public class TelcoSecurityUtils {
	
	private TelcoSecurityUtils() {}
	
	/**
	 * Función que se encarga de cubrir la vulnerabilidad de cross site scripting
	 * Comentario X para activar el flujo de DevOps
	 * @param headerValue
	 * @return sanitazedHeader
	 */
	public static String blindParameter(String headerValue) {
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
	    return policy.sanitize( headerValue );
	}

}
