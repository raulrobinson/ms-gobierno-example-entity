package co.com.telefonica.ws.ui.controller;

import co.com.telefonica.ws.businesslogic.TelcoBookService;
import co.com.telefonica.ws.dto.TelcoBookRequestDTO;
import co.com.telefonica.ws.dto.TelcoResponseDTO;
import co.com.telefonica.ws.entity.TelcoBookEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BASE CONTROLLER.
 * Author: Microservices Governance
 * By: Arquitectura - @Telefonica 2023
 * Version: 2.0.0
 */
@Slf4j
@RestController
@RequestMapping(path = "${controller.properties.base-path}", produces = MediaType.APPLICATION_JSON_VALUE)
public class TelcoBookController {

	private final TelcoBookService telcoBookService;

	public TelcoBookController(TelcoBookService telcoBookService) {
		this.telcoBookService = telcoBookService;
	}

	/**
	 * GET ALL BOOKS.
	 * @return TelcoResponseDTO
	 */
	@GetMapping("/books")
	public ResponseEntity<TelcoResponseDTO> getAllBooks() {
		List<TelcoBookEntity> telcoBookEntities = telcoBookService.findBookAll();
		if (telcoBookEntities.size() == 0) {
			return new ResponseEntity<>(TelcoResponseDTO.builder()
					.code(404L)
					.message("404 Not Found")
					.serviceResponse(null)
					.build(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(TelcoResponseDTO.builder()
				.code(200L)
				.message("200 OK")
				.serviceResponse(telcoBookEntities)
				.build(), HttpStatus.OK);
	}

	/**
	 * GET BOOK BY CODEBOOK.
	 * @param codebook String
	 * @return TelcoResponseDTO
	 */
	@GetMapping("/books/codebook")
	public ResponseEntity<TelcoResponseDTO> getBookByCodebook(
			@RequestParam("codebook") String codebook
	) {
		TelcoBookEntity telcoBookEntityData = telcoBookService.getByCodebook(codebook);
		if (telcoBookEntityData == null) {
			return new ResponseEntity<>(TelcoResponseDTO.builder()
					.code(404L)
					.message("404 Not Found")
					.serviceResponse(null)
					.build(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(TelcoResponseDTO.builder()
				.code(200L)
				.message("200 OK")
				.serviceResponse(telcoBookEntityData)
				.build(), HttpStatus.OK);
	}

	/**
	 * GET BOOK BY ID.
	 * @param id Long
	 * @return TelcoResponseDTO
	 */
	@GetMapping("/books/id")
	public ResponseEntity<TelcoResponseDTO> findById(
			@RequestParam(value = "id", required = true) Long id
	) {
		TelcoBookEntity telcoBookEntity = telcoBookService.getBookById(id);
		if (telcoBookEntity == null) {
			return new ResponseEntity<>(TelcoResponseDTO.builder()
					.code(404L)
					.message("404 Not Found")
					.serviceResponse(null)
					.build(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(TelcoResponseDTO.builder()
				.code(200L)
				.message("200 OK")
				.serviceResponse(telcoBookEntity)
				.build(), HttpStatus.OK);
	}

	/**
	 * CREATE BOOK.
	 * @param book TelcoBookRequestDTO
	 * @return TelcoResponseDTO
	 */
	@PostMapping("/books")
	public ResponseEntity<TelcoResponseDTO> createBook(
			@RequestBody TelcoBookRequestDTO book
	) {
		TelcoBookEntity req = telcoBookService.createBook(book);
		return new ResponseEntity<>(TelcoResponseDTO.builder()
				.code(200L)
				.message("200 OK")
				.serviceResponse(req)
				.build(), HttpStatus.OK);
	}

	/**
	 * UPDATE BOOK.
	 * @param id Long
	 * @param book TelcoBookRequestDTO
	 * @return TelcoResponseDTO
	 */
	@PutMapping("/books")
	public ResponseEntity<TelcoResponseDTO> updateBook(
			@RequestParam(value = "id", required = true) Long id,
			@RequestBody TelcoBookRequestDTO book
	) {
		TelcoBookEntity req = telcoBookService.updateBook(id, book);
		return new ResponseEntity<>(TelcoResponseDTO.builder()
				.code(200L)
				.message("200 OK")
				.serviceResponse(req)
				.build(), HttpStatus.OK);
	}

	/**
	 * DELETE BOOK.
	 * @param id Long
	 * @return TelcoResponseDTO
	 */
	@DeleteMapping("/books")
	public ResponseEntity<TelcoResponseDTO> deleteBook(
			@RequestParam(value = "id", required = true) Long id
	) {
		TelcoBookEntity req = telcoBookService.deleteById(id);
		return new ResponseEntity<>(TelcoResponseDTO.builder()
				.code(200L)
				.message("200 OK")
				.serviceResponse(req)
				.build(), HttpStatus.OK);
	}

}
