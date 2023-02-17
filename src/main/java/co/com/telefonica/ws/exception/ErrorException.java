package co.com.telefonica.ws.exception;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * CLASS EXCEPTION.
 *
 * @autor: COE-Arquitectura-Telefonica
 * @date: 17-02-2023
 * @version 3.0.0
 */
@Getter @Setter @Builder
public class ErrorException {
    private String code ;
    private List<Map<String, String >> messages ;
}
