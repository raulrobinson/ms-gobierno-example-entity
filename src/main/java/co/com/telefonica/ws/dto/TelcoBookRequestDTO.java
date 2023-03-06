package co.com.telefonica.ws.dto;

import javax.validation.constraints.NotEmpty;

public class TelcoBookRequestDTO {
    @NotEmpty
    private String title;
    @NotEmpty
    private String codebook;
    @NotEmpty
    private String description;
    private boolean published;
    public TelcoBookRequestDTO() { }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCodebook() {
        return codebook;
    }
    public void setCodebook(String codebook) {
        this.codebook = codebook;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isPublished() {
        return published;
    }
    public void setPublished(boolean published) {
        this.published = published;
    }
}
