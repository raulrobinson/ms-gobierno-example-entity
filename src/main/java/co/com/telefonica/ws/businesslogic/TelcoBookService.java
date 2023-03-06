package co.com.telefonica.ws.businesslogic;

import co.com.telefonica.ws.dto.TelcoBookRequestDTO;
import co.com.telefonica.ws.entity.TelcoBookEntity;

import java.util.List;

public interface TelcoBookService {
    List<TelcoBookEntity> findBookAll();
    TelcoBookEntity createBook(TelcoBookRequestDTO book);
    TelcoBookEntity getBookById(Long id);
    TelcoBookEntity getByCodebook(String codebook);
    TelcoBookEntity updateBook(Long id, TelcoBookRequestDTO book);
    TelcoBookEntity deleteById(Long id);
}
