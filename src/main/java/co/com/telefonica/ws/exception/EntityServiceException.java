package co.com.telefonica.ws.exception;

/**
 * CLASS SERVICE EXCEPTION.
 *
 * @autor: COE-Arquitectura-Telefonica
 * @date: 17-02-2023
 * @version 3.0.0
 */
public class EntityServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public EntityServiceException(String message) {
		super(message);
	}
}
