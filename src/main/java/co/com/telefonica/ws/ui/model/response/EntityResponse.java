package co.com.telefonica.ws.ui.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
public class EntityResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private String parameterOne;
    private String parameterTwo;
    private String parameterThree;
    private String parameterFour;
}
