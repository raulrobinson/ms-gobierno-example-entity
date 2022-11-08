package co.com.telefonica.ws.ui.model.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Objeto en el que se modela el response body del servicio
 * 
 * @version 1.0.0
 * @author COEArquitectura@telefonica.com
 * @since 31/05/2021
 * */
@Getter
@Setter
@ToString
public class TelfEntityResponse implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String parametro1;
    private String parametro2;
    private String parametro3;
    private String parametro4;
   
}
