package sicredi.votacao.configuration;

import org.springdoc.core.configuration.SpringDocConfiguration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Desafio Votação", version = "1.0.0", contact = @Contact(name = "Nei Guilherme da Silva", email = "guiuser1999@gmail.com")))
public class SwaggerConfig extends SpringDocConfiguration {

}
