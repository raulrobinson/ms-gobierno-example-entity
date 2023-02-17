package co.com.telefonica.ws.dto;

import lombok.*;

/**
 * CLASS RESPONSE DTO.
 *
 * @autor: COE-Arquitectura-Telefonica
 * @date: 17-02-2023
 * @version 3.0.0
 */
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor()
public class ResponseDTO {
    private int code;
    private String message;
}
