package co.com.telefonica.ws.businesslogic;

import co.com.telefonica.ws.dto.ResponseDTO;
import co.com.telefonica.ws.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import javax.naming.NamingException;

public interface LdapService {
    ResponseEntity<ResponseDTO> accessUserByUserName(UserDTO user) throws NamingException;
}
