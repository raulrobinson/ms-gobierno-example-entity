package co.com.telefonica.ws.ui.model;

import co.com.telefonica.ws.entity.TelcoBookEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelcoBookModel {
    private Long id;
    private String codebook;
    private String title;
    private String description;
    private boolean published;

    public TelcoBookModel(TelcoBookEntity telcoBookEntity) {
        this.id = telcoBookEntity.getId();
        this.codebook = telcoBookEntity.getCodebook();
        this.title = telcoBookEntity.getTitle();
        this.description = telcoBookEntity.getDescription();
        this.published = telcoBookEntity.isPublished();
    }
}
