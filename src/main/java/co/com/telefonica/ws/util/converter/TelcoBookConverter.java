package co.com.telefonica.ws.util.converter;

import co.com.telefonica.ws.entity.TelcoBookEntity;
import co.com.telefonica.ws.ui.model.TelcoBookModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("telcoBookConverter")
public class TelcoBookConverter {

    public List<TelcoBookModel> listConverter(List<TelcoBookEntity> books) {
        List<TelcoBookModel> bookModels = new ArrayList<>();
        books.forEach(book -> bookModels.add(new TelcoBookModel(book)));
        return bookModels;
    }

    public TelcoBookModel converter(TelcoBookEntity book) {
        var bookModels = new TelcoBookModel();
        bookModels.setId(book.getId());
        bookModels.setCodebook(book.getCodebook());
        bookModels.setTitle(book.getTitle());
        bookModels.setDescription(book.getDescription());
        bookModels.setPublished(book.isPublished());
        return bookModels;
    }
    public TelcoBookEntity rollback(TelcoBookModel bookModels) {
        var book = new TelcoBookEntity();
        book.setId(bookModels.getId());
        book.setCodebook(bookModels.getCodebook());
        book.setTitle(bookModels.getTitle());
        book.setDescription(bookModels.getDescription());
        book.setPublished(bookModels.isPublished());
        return book;
    }
}
