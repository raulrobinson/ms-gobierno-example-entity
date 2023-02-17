package co.com.telefonica.ws.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CLASS REGION ENTITY.
 *
 * @autor: COE-Arquitectura-Telefonica
 * @date: 17-02-2023
 * @version 3.0.0
 */
@Data
@Entity
@Builder
@Table(name = "tbl_regions")
@NoArgsConstructor
@AllArgsConstructor
public class Region implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
}
