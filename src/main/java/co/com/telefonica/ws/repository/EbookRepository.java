package co.com.telefonica.ws.repository;

import java.util.List;

import co.com.telefonica.ws.entity.Ebook;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EbookRepository extends MongoRepository<Ebook, String>
{
    List<Ebook> findByTitleContaining(String title);
    List<Ebook> findByPublished(boolean published);
}
