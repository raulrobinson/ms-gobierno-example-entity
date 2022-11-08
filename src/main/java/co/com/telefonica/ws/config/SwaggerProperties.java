package co.com.telefonica.ws.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

/**
 * NO BORRAR
 * 
 * Clase empleada para mapear las propiedades registradas en el application.properties
 * referentes a la documentación autogenerada.
 * 
 * @version 1.0.0
 * @author COEArquitectura@telefonica.com
 * @since 27/12/2021
 * */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor()
@Configuration
@ConfigurationProperties(prefix = "swagger.properties")
public class SwaggerProperties {

	/**
	 * Nombre del microservicio o del proyecto
	 */
	private String projectName;

	/**
	 * Una corta descripción de las funcionalidades del proyecto
	 */
	private String projectShortDescription;

	/**
	 * Tu nombre, o el nombre del desarrollador
	 */
	private String developerName;

	/**
	 * Correo electrónico del desarrollador... preferiblemente el corporativo
	 */
	private String developerMail;

	/**
	 * mensaje visible de los terminos de servicio del microservicio
	 */
	private String projectTosMsg;

	/**
	 * Enlace en el que se puedan leer los terminos de servicio del microservicio
	 */
	private String projectTosLink;

	/**
	 * Mensaje con el que se va a mostrar la licencia del microservicio
	 */
	private String projectLicenceMsg;

	/**
	 * Link en el que se pueda leer la licencia bajo la cual se rige el uso del
	 * microservicio
	 */
	private String projectLicenceLink;

	/**
	 * URL de la empresa para la cual se desarrolla el microservicio
	 */
	private String organizationUrl;
}
