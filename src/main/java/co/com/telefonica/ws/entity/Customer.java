package co.com.telefonica.ws.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * CLASS CUSTOMER ENTITY.
 *
 * @autor: COE-Arquitectura-Telefonica
 * @date: 17-02-2023
 * @version 3.0.0
 */
@Data
@Entity
@Builder
@Table(name="tbl_customers")
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The document number cannot be empty")
    @Size( min = 8 , max = 10, message = "The size of the document number is 10")
    @Column(name = "number_id" , unique = true ,length = 10, nullable = false)
    private String numberID;

    @NotEmpty(message = "The name cannot be empty")
    @Column(name="first_name", nullable=false)
    private String firstName;

    @NotEmpty(message = "The last name cannot be empty")
    @Column(name="last_name", nullable=false)
    private String lastName;

    @NotEmpty(message = "e-mail cannot be empty")
    @Email(message = "is not a well-formed email address")
    @Column(unique=true, nullable=false)
    private String email;

    @Column(name="photo_url")
    private String photoUrl;

    @NotNull(message = "Region cannot be empty")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Region region;

    private String state;
}
