package co.com.telefonica.ws.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelcoBookDataDTO {
    private Long id;
    private String codebook;
    private String title;
    private String description;
    private boolean published;
}
