package co.com.telefonica.ws.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Component;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * NO BORRAR
 *
 * Clase encargada de incluir SWAGGER_2.0 en la aplicación para autodescripción
 * del API.
 *
 * @version 1.1.0
 * @author COEArquitectura@telefonica.com
 * @since 27/12/2021
 */
@Component
public class SpringFoxConfig {

	@Value("${controller.properties.base-path}")
	private String uriBasePattern;

	@Autowired
	private SwaggerProperties swaggerProperties;

	@Bean
	/**
	 * Metodo que permite generar la configuración para SWAGGER 2.0
	 *
	 * @return Docket
	 */
	public Docket api() {
		String regexUri = "/" + this.uriBasePattern + ".*";

		return new Docket(DocumentationType.OAS_30)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex(regexUri))
				//.paths(PathSelectors.any())
				.build().apiInfo(apiInfo())
				.globalOperationParameters(this.commonParameters());
	}

	/**
	 * Metodo en el que se definen los headers que se van a mostrar en la documentación de todas las operaciones
	 * que se van a implementar en el microservicio
	 * @return
	 */
	private List<Parameter> commonParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();
		String headerType = "string";
		String paramType = "header";

		parameters.add(new ParameterBuilder()
				.name("authorization")
				.description("Token de autorización que se usó para pasar por el API_GW")
				.modelRef(new ModelRef(headerType))
				.parameterType(paramType)
				.required(true)
				.defaultValue("token-api-gw")
				.build());

		parameters.add(new ParameterBuilder()
				.name("system")
				.description("Nombre del sistema que realiza la solicitud")
				.modelRef(new ModelRef(headerType))
				.parameterType(paramType)
				.required(true)
				.defaultValue("FakeApplication")
				.build());

		parameters.add(new ParameterBuilder()
				.name("operation")
				.description("URI de la operación que se está consumiento")
				.modelRef(new ModelRef(headerType))
				.parameterType(paramType)
				.required(true)
				.defaultValue(this.uriBasePattern)
				.build());

		parameters.add(new ParameterBuilder()
				.name("execId")
				.description("Identificador de la transacción en formato UUID en forma canónica con 32 dígitos hexadecimales, de la forma 8-4-4-4-12 para un total de 36 caracteres")
				.modelRef(new ModelRef(headerType))
				.parameterType(paramType)
				.required(true)
				.defaultValue("CA761232-ED42-11CE-BACD-00AA0057B223")
				.build());

		parameters.add(new ParameterBuilder()
				.name("timestamp")
				.description(" Marca de tiempo correspondiente al envío del mensaje. El formato es el ISO_OFFSET_DATE_TIME")
				.modelRef(new ModelRef(headerType))
				.parameterType(paramType)
				.required(true)
				.defaultValue("2021-08-18T19:22:18.532-05:00")
				.build());

		parameters.add(new ParameterBuilder()
				.name("msgType")
				.description("Indica el tipo de mensaje y está relacionado con el escenario de uso y modo de interacción.")
				.modelRef(new ModelRef(headerType))
				.parameterType(paramType)
				.required(true)
				.defaultValue("Request")
				.build());

		parameters.add(new ParameterBuilder()
				.name("Host")
				.description("Indica el host del consumidor. Este es el responsable de generar los errores 400 al consumir el servicio")
				.modelRef(new ModelRef(headerType))
				.parameterType(paramType)
				.required(true)
				.defaultValue("localhost")
				.build());

		return parameters;
	}

	/**
	 * Metodo que retorna la configuración de API Info
	 *
	 * @return ApiInfo
	 */
	private ApiInfo apiInfo() {

		return new ApiInfo(
				this.swaggerProperties.getProjectName(),
				this.swaggerProperties.getProjectShortDescription(),
				this.swaggerProperties.getProjectTosMsg(),
				this.swaggerProperties.getProjectTosLink(),
				new Contact(
						this.swaggerProperties.getDeveloperName(),
						this.swaggerProperties.getOrganizationUrl(),
						this.swaggerProperties.getDeveloperMail()),
				this.swaggerProperties.getProjectLicenceMsg(),
				this.swaggerProperties.getProjectLicenceLink(),
				Collections.emptyList()
		);
	}
}