package co.com.telefonica.ws.businesslogic;

import co.com.telefonica.ws.entity.Ebook;

import java.util.List;
import java.util.Optional;

public interface EbookService {
    List<Ebook> findByTitleContaining(String title);
    List<Ebook> getAllEbooks();
    Ebook createEbook(Ebook ebook);
    Optional<Ebook> getEbookById(String id);
}
