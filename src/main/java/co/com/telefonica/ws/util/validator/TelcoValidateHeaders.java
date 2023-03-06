package co.com.telefonica.ws.util.validator;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

@Slf4j
@Getter
@Setter
public class TelcoValidateHeaders {

    private static final int TAM_SOURCE_FIELD = 40;
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final String REGEX_FORMAT = "[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}";

    public boolean validateHeaders(HttpHeaders headers) {
        return !validateHeadersContent(headers) ||
               !validateTimeStamp(headers) ||
               !validateExecId(headers);
    }

    public boolean validateHeadersContent(HttpHeaders headers) {
        return headers.containsKey("Authorization") &&
               headers.containsKey("system") &&
               headers.containsKey("operation") &&
               headers.containsKey("execId") &&
               headers.containsKey("timestamp") &&
               headers.containsKey("msgType");
    }

    public boolean validateTimeStamp(HttpHeaders headers) {
        TelcoDateValidator validator = new TelcoDateValidatorTimestamp(DATE_FORMAT);
        return validator.isValid(headers.getFirst("timestamp"));
    }

    public boolean validateExecId(HttpHeaders headers) {
        return !validateRegExExecId(headers.getFirst("execId"));
    }

    public static boolean validateRegExExecId(String source) {
        return (!Pattern.matches(REGEX_FORMAT, source));
    }

    public String getTimestampValue() {
        ZoneId zoneIdCo = ZoneId.of("America/Bogota");
        ZonedDateTime now = ZonedDateTime.now(zoneIdCo);
        DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return now.truncatedTo(ChronoUnit.MILLIS).format(dtf);
    }

    public static boolean validateField(String source) {
        return !(source == null || source.isEmpty() || source.length() > TAM_SOURCE_FIELD);
    }
}
