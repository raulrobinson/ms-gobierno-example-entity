package co.com.telefonica.ws.businesslogic.impl;

import co.com.telefonica.ws.businesslogic.LdapService;
import co.com.telefonica.ws.dto.ResponseDTO;
import co.com.telefonica.ws.dto.UserDTO;
import co.com.telefonica.ws.util.TelcoLdapServiceDirector;
import co.com.telefonica.ws.util.TelcoSecurityUtils;
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

        var userDN = escapeLDAPSearchFilter(user.getUsername());
        var passDN = escapeLDAPSearchFilter(user.getPassword());

        String regex = "\\w\\d*";

        if (userDN.matches(regex) || passDN.matches(regex)) {
            throw new IllegalArgumentException("Invalid input");
        }

        String searchFilter = escapeLDAPSearchFilter("userPrincipalName=" + userDN + ldapDomain);

        String [] ldapObjects = ldapObjectsForest.toArray(new String[0]);

        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(ldapObjects);

        InitialDirContext rescon = telcoLdapServiceDirector.newConnection(userDN + ldapDomain, user.getPassword());

        var lQry = escapeLDAPSearchFilter(ldapObjectsForest.get(5) + "," + ldapObjectsForest.get(6));

        if (lQry.matches(regex) || searchFilter.matches(regex)) {
            throw new IllegalArgumentException("Invalid input");
        }

        NamingEnumeration<SearchResult> users = rescon.search(lQry, searchFilter, controls);

        SearchResult result = null;

        if (users.hasMore() && authUser(userDN + ldapDomain, user.getPassword())) {
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
                    .message(TelcoSecurityUtils.blindParameter("Success"))
                    .build(), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .code(403)
                    .data(TelcoSecurityUtils.blindParameter("none"))
                    .message(TelcoSecurityUtils.blindParameter("403 Forbidden"))
                    .build(), HttpStatus.FORBIDDEN);
        }
    }

    public boolean authUser(String usernameDomain, String password) throws NamingException {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapProvider);
        env.put(Context.SECURITY_PRINCIPAL, usernameDomain);
        env.put(Context.SECURITY_CREDENTIALS, password);
        DirContext con = new InitialDirContext(env);
        con.close();
        return true;
    }

    public static String escapeLDAPSearchFilter(String filter) {
        StringBuilder sb = new StringBuilder(); // If using JDK >= 1.5 consider using StringBuilder
        for (int i = 0; i < filter.length(); i++) {
            char curChar = filter.charAt(i);
            switch (curChar) {
                case '\\':
                    sb.append("\\5c");
                    break;
                case '*':
                    sb.append("\\2a");
                    break;
                case '(':
                    sb.append("\\28");
                    break;
                case ')':
                    sb.append("\\29");
                    break;
                case '\u0000':
                    sb.append("\\00");
                    break;
                default:
                    sb.append(curChar);
            }
        }
        return sb.toString();
    }

}
