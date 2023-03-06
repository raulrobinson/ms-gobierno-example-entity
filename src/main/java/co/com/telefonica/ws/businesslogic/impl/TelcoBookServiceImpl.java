package co.com.telefonica.ws.businesslogic.impl;

import co.com.telefonica.ws.businesslogic.TelcoBookService;
import co.com.telefonica.ws.dto.TelcoBookRequestDTO;
import co.com.telefonica.ws.entity.TelcoBookEntity;
import co.com.telefonica.ws.repository.TelcoBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TelcoBookServiceImpl implements TelcoBookService {

    private final TelcoBookRepository telcoBookRepository;

    public TelcoBookServiceImpl(TelcoBookRepository telcoBookRepository) {
        this.telcoBookRepository = telcoBookRepository;
    }

    @Override
    public List<TelcoBookEntity> findBookAll() {
        return telcoBookRepository.findAll();
    }

    @Override
    public TelcoBookEntity createBook(TelcoBookRequestDTO book) {
        TelcoBookEntity telcoBookEntityDB = telcoBookRepository.findByCodebook(book.getCodebook());
        if (telcoBookEntityDB != null) {
            return TelcoBookEntity.builder()
                    .id(telcoBookEntityDB.getId())
                    .codebook(book.getCodebook())
                    .title(telcoBookEntityDB.getTitle())
                    .description(telcoBookEntityDB.getDescription())
                    .published(telcoBookEntityDB.isPublished())
                    .build();
        }
        TelcoBookEntity telcoBookEntitySave = TelcoBookEntity.builder()
                .codebook(book.getCodebook())
                .title(book.getTitle())
                .description(book.getDescription())
                .published(book.isPublished())
                .build();
        telcoBookEntityDB = telcoBookRepository.save(telcoBookEntitySave);
        return telcoBookEntityDB;
    }

    @Override
    public TelcoBookEntity updateBook(Long id, TelcoBookRequestDTO book) {
        TelcoBookEntity telcoBookEntityDB = telcoBookRepository.getById(id);
        if (telcoBookEntityDB == null) {
            return null;
        }
        TelcoBookEntity telcoBookEntityUpdate = TelcoBookEntity.builder()
                .id(telcoBookEntityDB.getId())
                .codebook(book.getCodebook())
                .title(book.getTitle())
                .description(book.getDescription())
                .published(book.isPublished())
                .build();
        telcoBookRepository.save(telcoBookEntityUpdate);
        return telcoBookEntityUpdate;
    }

    @Override
    public TelcoBookEntity deleteById(Long id) {
        TelcoBookEntity telcoBookEntityDelete = getBookById(id);
        if (telcoBookRepository.existsById(id)) {
            telcoBookRepository.deleteById(id);
        }
        return telcoBookEntityDelete;
    }

    @Override
    public TelcoBookEntity getBookById(Long id) {
        return telcoBookRepository.findById(id).orElse(null);
    }

    @Override
    public TelcoBookEntity getByCodebook(String codebook) {
        return telcoBookRepository.findByCodebook(codebook);
    }

}
