package co.com.telefonica.ws.ui.controller;

import co.com.telefonica.ws.businesslogic.LdapService;
import co.com.telefonica.ws.dto.ResponseDTO;
import co.com.telefonica.ws.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NamingException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${controller.properties.base-path}", produces = MediaType.APPLICATION_JSON_VALUE)
public class LdapController {

    private final LdapService ldapService;

    @PostMapping
    public ResponseEntity<ResponseDTO> accessUserByUserName(
            @RequestBody UserDTO user) throws NamingException {
        return ldapService.accessUserByUserName(user);
    }

}
