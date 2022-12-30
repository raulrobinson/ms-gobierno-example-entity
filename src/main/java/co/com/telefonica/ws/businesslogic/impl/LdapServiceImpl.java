package co.com.telefonica.ws.businesslogic.impl;

import co.com.telefonica.ws.businesslogic.LdapService;
import co.com.telefonica.ws.dto.ResponseDTO;
import co.com.telefonica.ws.dto.UserDTO;
import co.com.telefonica.ws.util.TelcoLdapServiceDirector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
public class LdapServiceImpl implements LdapService {

    @Value("${ldap.provider.url}")
    private String ldapProvider;

    @Value("${ldap.domain.telco}")
    private String ldapDomain;

    @Value("${ldap.objects.forest}")
    private ArrayList<String> ldapObjectsForest;

    private final TelcoLdapServiceDirector telcoLdapServiceDirector;

    private DirContext connection;

    @Override
    public ResponseEntity<ResponseDTO> accessUserByUserName(UserDTO user) throws NamingException {

        String usernameDomain = user.getUsername() + ldapDomain;
        String searchFilter = "userPrincipalName="+usernameDomain+"";
        String [] ldapObjects = ldapObjectsForest.toArray(new String[0]);

        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(ldapObjects);

        InitialDirContext rescon = telcoLdapServiceDirector.newConnection(usernameDomain, user.getPassword());

        NamingEnumeration<SearchResult> users = rescon.search("dc=nh,dc=inet", searchFilter, controls);

        SearchResult result = null;

        if (users.hasMore() && authUser(usernameDomain, user.getPassword())) {
            result = users.next();
            Attributes attr = result.getAttributes();

            var groups = new ArrayList<>();
            for (var i = 0; i < attr.get(ldapObjectsForest.get(2)).size(); i++) {
                groups.add(attr.get(ldapObjectsForest.get(2)).get(i).toString());
            }

            var titleGroups = new ArrayList<>();
            for (var i = 0; i < attr.get(ldapObjectsForest.get(2)).size(); i++) {
                String uno = attr.get(ldapObjectsForest.get(2)).get(i).toString();
                String[] parts = uno.split(",|;\\s|CN=|OU=");
                String parte = parts[1];
                titleGroups.add(parte);
            }

            var userData = new HashMap<String, Object >();
            userData.put("name", attr.get("displayName").get(0).toString());
            userData.put("memberOf", groups);
            userData.put("company", "Colombia Telecomunicaciones");
            userData.put(ldapObjectsForest.get(3), attr.get(ldapObjectsForest.get(3)).get(0).toString());

            var props = new HashMap<String, Object>();
            props.put("groups", titleGroups);
            props.put("user", userData);

            return new ResponseEntity<>(ResponseDTO.builder()
                    .code(200)
                    .data(props)
                    .message("Success")
                    .build(), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .code(403)
                    .data("none")
                    .message("403 Forbidden")
                    .build(), HttpStatus.FORBIDDEN);
        }
    }

    public boolean authUser(String usernameDomain, String password) {
        try {
            Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, ldapProvider);
            env.put(Context.SECURITY_PRINCIPAL, usernameDomain);
            env.put(Context.SECURITY_CREDENTIALS, password);
            DirContext con = new InitialDirContext(env);
            con.close();
            return true;
        }catch (Exception e) {
            return false;
        }
    }

}
