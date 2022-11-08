package co.com.telefonica.ws.util;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class SecurityUtils {
	
	private SecurityUtils() {}
	
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
