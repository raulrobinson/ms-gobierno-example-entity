package co.com.telefonica.ws.ui.controller;

import co.com.telefonica.ws.businesslogic.EbookService;
import co.com.telefonica.ws.dto.EbookDTO;
import co.com.telefonica.ws.dto.ResponseDTO;
import co.com.telefonica.ws.entity.Ebook;
import lombok.RequiredArgsConstructor;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${controller.properties.base-path}", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntityController {

	@Autowired
	EbookService ebookService;

	@GetMapping("/e-books/{id}")
	public ResponseEntity<ResponseDTO> getEbookById(
			@PathVariable("id") String id)
	{
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		Optional<Ebook> ebookData = ebookService.getEbookById(id);

		if (ebookData.isEmpty())
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		var res = ResponseDTO.builder()
				.title(policy.sanitize(ebookData.map(Ebook::getTitle).orElse(null)))
				.description(policy.sanitize(ebookData.map(Ebook::getDescription).orElse(null)))
				.published(Boolean.TRUE.equals(ebookData.map(Ebook::isPublished).orElse(null)))
				.build();

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping("/e-books")
	public ResponseEntity<ResponseDTO> createEbook(
			@RequestBody EbookDTO ebook)
	{
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		var ebookData = ebookService.createEbook(new Ebook(ebook.getTitle(), ebook.getDescription(), false));

		if (ebookData == null)
		{
			return ResponseEntity.badRequest().build();
		}

		return new ResponseEntity<>(ResponseDTO.builder()
				.title(policy.sanitize(ebookData.getTitle()))
				.description(policy.sanitize(ebookData.getDescription()))
				.published(Boolean.parseBoolean((policy.sanitize(String.valueOf(ebookData.isPublished())))))
				.build(), HttpStatus.CREATED);
	}

	@PutMapping("/e-books/{id}")
	public ResponseEntity<ResponseDTO> updateEbook(
			@PathVariable("id") String id,
			@RequestBody EbookDTO ebook)
	{
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		Optional<Ebook> ebookData = ebookService.getEbookById(id);

		if (ebookData.isPresent())
		{
			Ebook ebookReq = ebookData.get();
			ebookReq.setTitle(ebook.getTitle());
			ebookReq.setDescription(ebook.getDescription());
			ebookReq.setPublished(ebook.isPublished());

			if (ebookService.createEbook(ebookReq) != null)
			{
				return new ResponseEntity<>(ResponseDTO.builder()
						.title(policy.sanitize(ebookReq.getTitle()))
						.description(policy.sanitize(ebookReq.getDescription()))
						.published(Boolean.parseBoolean((policy.sanitize(String.valueOf(ebookReq.isPublished())))))
						.build(), HttpStatus.CREATED);
			}

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
