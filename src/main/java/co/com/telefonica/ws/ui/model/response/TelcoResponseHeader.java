package co.com.telefonica.ws.ui.model.response;

import java.util.HashMap;
import java.util.Map;

public class TelcoResponseHeader {
    public static Object BadHeaders() {
        Map<String, Object> details = new HashMap<>();
        details.put("Authorization", "Parametro en el que se almacena el valor del header de authorization. Ej: Bearer ejYdiix...");
        details.put("system", "Nombre del sistema que realiza la solicitud. Ej: CoEArqSolucion");
        details.put("operation", "Identificador de la operación ofrecida por el servicio. Ej: Entity");
        details.put("execId", "Identificador único de la petición. Ej: 550e8400-e29b-41d4-a716-446655440001");
        details.put("timestamp", "Marca de tiempo correspondiente al envío del mensaje. Ej: 2021-08-18T19:22:18.532-05:00");
        details.put("msgType", "Indica el tipo de mensaje y está relacionado con el escenario de uso. Ej: Request");
        return details;
    }
}
