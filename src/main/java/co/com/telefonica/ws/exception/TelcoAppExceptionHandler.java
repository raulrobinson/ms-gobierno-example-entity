package co.com.telefonica.ws.exception;

import co.com.telefonica.ws.ui.model.response.TelcoErrorEntity;
import co.com.telefonica.ws.util.TelcoUtilHeader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TelcoAppExceptionHandler extends ResponseEntityExceptionHandler  {

	/**
	 * **********************
	 * **** NO BORRAR!!! ****
	 * **********************
	 * 
	 * 	This also can be changed by an especific exception like NullPointerException.class , if you want to handle more than one 
	 *	exception use it like this  	@ExceptionHandler(value= {Exception.class,UserServiceException.class}) and also you have to change
	 *	the entry parameter for superclass Exception
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value= {Exception.class})
	public ResponseEntity<Object> handleAnyException(Exception ex,WebRequest request){
		String errorMessage=ex.getLocalizedMessage();
		if (errorMessage==null)errorMessage=ex.toString();
		TelcoErrorEntity message=new TelcoErrorEntity(new TelcoUtilHeader().getTimestampValue(),errorMessage);
		
		return new ResponseEntity<>(
				message,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * This also can be changed by an especific exception like NullPointerException
	 * @param ex EntityServiceException
	 * @param request WebRequest
	 * @return
	 */
	@ExceptionHandler(value= {TelcoEntityServiceException.class})
	public ResponseEntity<Object> handleAnyException(TelcoEntityServiceException ex, WebRequest request) {
		String errorMessage=ex.getLocalizedMessage();
		if (errorMessage==null)errorMessage=ex.toString();
		TelcoErrorEntity message=new TelcoErrorEntity(new TelcoUtilHeader().getTimestampValue(),errorMessage);
		
		return new ResponseEntity<>(
				message,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}
