package co.com.telefonica.ws.util;

import co.com.telefonica.ws.entity.TelcoBookEntity;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import java.util.ArrayList;
import java.util.List;

public class TelcoSecurityUtils {
	
	public TelcoSecurityUtils() {}
	
	/**
	 * Función que se encarga de cubrir la vulnerabilidad de cross site scripting
	 * Comentario X para activar el flujo de DevOps
	 * @param headerValue String
	 * @return sanitazedHeader
	 */
	public static String blindParameter(String headerValue) {
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
	    return policy.sanitize( headerValue );
	}

	/** BLIND BOOK ENTITY */
	public TelcoBookEntity blindBook(TelcoBookEntity telcoBookEntity) {
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		var book = new TelcoBookEntity();
		book.setId(Long.valueOf(policy.sanitize(String.valueOf(telcoBookEntity.getId()))));
		book.setTitle(policy.sanitize(telcoBookEntity.getTitle()));
		book.setCodebook(policy.sanitize(telcoBookEntity.getCodebook()));
		book.setDescription(policy.sanitize(telcoBookEntity.getDescription()));
		return book;
	}

	/** BLIND LIST BOOKS ENTITY */
	public List<TelcoBookEntity> blindBooksList(List<TelcoBookEntity> telcoBookEntityList) {
		List<TelcoBookEntity> bookList = new ArrayList<>();
		for (TelcoBookEntity s : telcoBookEntityList){
			bookList.add(blindBook(s));
		}
		return bookList;
	}

}
