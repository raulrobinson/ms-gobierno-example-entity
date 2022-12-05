package co.com.telefonica.ws.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private String title;
    private String description;
    private boolean published;
}
