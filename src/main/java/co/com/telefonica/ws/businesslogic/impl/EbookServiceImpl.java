package co.com.telefonica.ws.businesslogic.impl;

import co.com.telefonica.ws.businesslogic.EbookService;
import co.com.telefonica.ws.entity.Ebook;
import co.com.telefonica.ws.repository.EbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EbookServiceImpl implements EbookService {

    @Autowired
    EbookRepository ebookRepository;

    @Override
    public List<Ebook> getAllEbooks() {
        return ebookRepository.findAll();
    }

    @Override
    public List<Ebook> findByTitleContaining(String title) {
        return ebookRepository.findByTitleContaining(title);
    }

    @Override
    public Optional<Ebook> getEbookById(String id) {
        return ebookRepository.findById(id);
    }

    @Override
    public Ebook createEbook(Ebook ebook) {
        return ebookRepository.save(ebook);
    }

}
