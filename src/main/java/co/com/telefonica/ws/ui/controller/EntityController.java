package co.com.telefonica.ws.ui.controller;

import co.com.telefonica.ws.businesslogic.EbookService;
import co.com.telefonica.ws.dto.EbookDTO;
import co.com.telefonica.ws.entity.Ebook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "${controller.properties.base-path}", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntityController {

	@Autowired
	EbookService ebookService;

	@GetMapping("/e-books")
	public ResponseEntity<List<Ebook>> getAllEbooks(
			@RequestParam(required = false) String title) {
		try {
			List<Ebook> tutorials = new ArrayList<>();
			if (title == null)
				tutorials.addAll(ebookService.getAllEbooks());
			else
				tutorials.addAll(ebookService.findByTitleContaining(title));

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/e-books/{id}")
	public ResponseEntity<Ebook> getEbookById(
			@PathVariable("id") String id) {
		Optional<Ebook> ebookData = ebookService.getEbookById(id);
		return ebookData.map(ebook -> new ResponseEntity<>(ebook, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping("/e-books")
	public ResponseEntity<Ebook> createEbook(
			@RequestBody EbookDTO ebook) {
		try {
			var ebookRes = ebookService.createEbook(new Ebook(ebook.getTitle(), ebook.getDescription(), false));
			return new ResponseEntity<>(ebookRes, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/e-books/{id}")
	public ResponseEntity<Ebook> updateEbook(
			@PathVariable("id") String id,
			@RequestBody EbookDTO ebook) {
		Optional<Ebook> ebookData = ebookService.getEbookById(id);
		if (ebookData.isPresent()) {
			Ebook ebookReq = ebookData.get();
			ebookReq.setTitle(ebook.getTitle());
			ebookReq.setDescription(ebook.getDescription());
			ebookReq.setPublished(ebook.isPublished());
			return new ResponseEntity<>(ebookService.createEbook(ebookReq), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
