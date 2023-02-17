package co.com.telefonica.ws.dto;

import co.com.telefonica.ws.entity.Region;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * CLASS REQUEST DTO.
 *
 * @autor: COE-Arquitectura-Telefonica
 * @date: 17-02-2023
 * @version 3.0.0
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor()
public class RequestDTO {
    private Long id;
    @NotEmpty(message = "The document number cannot be empty")
    private String numberID;
    @NotEmpty(message = "The name cannot be empty")
    private String firstName;
    @NotEmpty(message = "The last name cannot be empty")
    private String lastName;
    @NotEmpty(message = "E-mail cannot be empty")
    private String email;
    private String photoUrl;
    @NotNull(message = "Region cannot be empty")
    private Region region;
    private String state;
}
