package co.com.telefonica.ws.ui.controller;

import co.com.telefonica.ws.businesslogic.TelcoBookService;
import co.com.telefonica.ws.dto.TelcoBookRequestDTO;
import co.com.telefonica.ws.dto.TelcoBookResponseDTO;
import co.com.telefonica.ws.entity.TelcoBookEntity;
import co.com.telefonica.ws.ui.model.response.TelcoResponseHeader;
import co.com.telefonica.ws.util.validator.TelcoValidateHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "${controller.properties.base-path}", produces = MediaType.APPLICATION_JSON_VALUE)
public class TelcoBookController {

	private final TelcoBookService telcoBookService;

	public TelcoBookController(TelcoBookService telcoBookService) {
		this.telcoBookService = telcoBookService;
	}

	@GetMapping("/books")
	public ResponseEntity<TelcoBookResponseDTO> getAllBooks(
			@RequestHeader HttpHeaders headers) {
		try {
			if (new TelcoValidateHeaders().validateHeaders(headers)) {
				return new ResponseEntity<>(TelcoBookResponseDTO.builder()
						.code(503L)
						.message("Error bad headers..")
						.data(TelcoResponseHeader.BadHeaders())
						.build(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			List<TelcoBookEntity> telcoBookEntities = telcoBookService.findBookAll();
			if (telcoBookEntities == null) {
				return new ResponseEntity<>(TelcoBookResponseDTO.builder()
						.code(200L)
						.message("404 Not Found")
						.data(null)
						.build(), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(TelcoBookResponseDTO.builder()
					.code(200L)
					.message("200 OK")
					.data(telcoBookEntities)
					.build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(TelcoBookResponseDTO.builder()
					.code(503L)
					.message("503 Server Internal Error")
					.data(null)
					.build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/books/codebook")
	public ResponseEntity<TelcoBookResponseDTO> getBookByCodebook(
			@RequestHeader HttpHeaders headers,
			@RequestParam("codebook") String codebook) {
		try {
			if (new TelcoValidateHeaders().validateHeaders(headers)) {
				return new ResponseEntity<>(TelcoBookResponseDTO.builder()
						.code(503L)
						.message("Error bad headers..")
						.data(TelcoResponseHeader.BadHeaders())
						.build(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			TelcoBookEntity telcoBookEntityData = telcoBookService.getByCodebook(codebook);
			if (telcoBookEntityData == null)
				return new ResponseEntity<>(TelcoBookResponseDTO.builder()
						.code(404L)
						.message("404 Not Found")
						.data(null)
						.build(), HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(TelcoBookResponseDTO.builder()
					.code(200L)
					.message("200 OK")
					.data(telcoBookEntityData)
					.build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(TelcoBookResponseDTO.builder()
					.code(503L)
					.message("503 Server Internal Error")
					.data(null)
					.build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/books/id")
	public ResponseEntity<TelcoBookResponseDTO> findById(
			@RequestHeader HttpHeaders headers,
			@RequestParam(value = "id", required = true) Long id) {
		try {
			if (new TelcoValidateHeaders().validateHeaders(headers)) {
				return new ResponseEntity<>(TelcoBookResponseDTO.builder()
						.code(503L)
						.message("Error bad headers..")
						.data(TelcoResponseHeader.BadHeaders())
						.build(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			TelcoBookEntity telcoBookEntity = telcoBookService.getBookById(id);
			if (telcoBookEntity == null) {
				return new ResponseEntity<>(TelcoBookResponseDTO.builder()
						.code(404L)
						.message("404 Not Found")
						.data(null)
						.build(), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(TelcoBookResponseDTO.builder()
					.code(200L)
					.message("200 OK")
					.data(telcoBookEntity)
					.build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(TelcoBookResponseDTO.builder()
					.code(503L)
					.message("503 Server Internal Error")
					.data(null)
					.build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/books")
	public ResponseEntity<TelcoBookResponseDTO> createBook(
			@RequestHeader HttpHeaders headers,
			@RequestBody TelcoBookRequestDTO book) {
		try {
			if (new TelcoValidateHeaders().validateHeaders(headers)) {
				return new ResponseEntity<>(TelcoBookResponseDTO.builder()
						.code(503L)
						.message("Error bad headers..")
						.data(TelcoResponseHeader.BadHeaders())
						.build(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			TelcoBookEntity req = telcoBookService.createBook(book);
			return new ResponseEntity<>(TelcoBookResponseDTO.builder()
					.code(200L)
					.message("200 OK")
					.data(req)
					.build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(TelcoBookResponseDTO.builder()
					.code(503L)
					.message("503 Server Internal Error")
					.data(null)
					.build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/books")
	public ResponseEntity<TelcoBookResponseDTO> updateBook(
			@RequestHeader HttpHeaders headers,
			@RequestParam(value = "id", required = true) Long id,
			@RequestBody TelcoBookRequestDTO book) {
		try {
			if (new TelcoValidateHeaders().validateHeaders(headers)) {
				return new ResponseEntity<>(TelcoBookResponseDTO.builder()
						.code(503L)
						.message("Error bad headers..")
						.data(TelcoResponseHeader.BadHeaders())
						.build(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			TelcoBookEntity req = telcoBookService.updateBook(id, book);
			return new ResponseEntity<>(TelcoBookResponseDTO.builder()
					.code(200L)
					.message("200 OK")
					.data(req)
					.build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(TelcoBookResponseDTO.builder()
					.code(404L)
					.message("404 Not Found")
					.data(null)
					.build(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/books")
	public ResponseEntity<TelcoBookResponseDTO> deleteBook(
			@RequestHeader HttpHeaders headers,
			@RequestParam(value = "id", required = true) Long id) {
		try {
			if (new TelcoValidateHeaders().validateHeaders(headers)) {
				return new ResponseEntity<>(TelcoBookResponseDTO.builder()
						.code(503L)
						.message("Error bad headers..")
						.data(TelcoResponseHeader.BadHeaders())
						.build(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			TelcoBookEntity req = telcoBookService.deleteById(id);
			return new ResponseEntity<>(TelcoBookResponseDTO.builder()
					.code(200L)
					.message("200 OK")
					.data(req)
					.build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(TelcoBookResponseDTO.builder()
					.code(404L)
					.message("404 Not Found")
					.data(null)
					.build(), HttpStatus.NOT_FOUND);
		}
	}

}
