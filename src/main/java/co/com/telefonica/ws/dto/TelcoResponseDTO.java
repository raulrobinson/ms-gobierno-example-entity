package co.com.telefonica.ws.dto;

import lombok.Builder;
import lombok.Data;

/**
 * ************************************
 * **** NO BORRAR, NO MODIFICAR!!! ****
 * ************************************
 * RESPONSE DTO TELEFONICA - MOVISTAR.
 * Author: Microservices Governance
 * By: Arquitectura - @Telefonica 2023
 * Version: 2.0.0
 */
@Data
@Builder
public class TelcoResponseDTO {
    private Long code;
    private String message;
    private Object serviceResponse;
}
