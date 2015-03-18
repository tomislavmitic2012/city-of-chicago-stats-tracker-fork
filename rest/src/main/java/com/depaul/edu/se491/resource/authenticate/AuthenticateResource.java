package com.depaul.edu.se491.resource.authenticate;

import com.depaul.edu.se491.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tom Mitic on 3/9/15.
 */
@Component
@Path("/authenticate")
public class AuthenticateResource {

    @Autowired
    private UserDetailsService userService;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager auth;

    /**
     * Retrieves the currently logged in user.
     *
     * @return A transfer containing the username and the roles.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserTransfer getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object p = auth.getPrincipal();
        if (p instanceof String && ((String) p).equals("anonymousUser")) {
            throw new WebApplicationException(401);
        }
        UserDetails  details = (UserDetails) p;
        return new UserTransfer(details.getUsername(), this.createRoleMap(details));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public TokenTransfer authenticate(
            @FormParam("username") String username,
            @FormParam("password") String password) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = this.auth.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        /*
         * Reload user as password of authentication principal will be null after authorization and
         * password is needed for token generation
         */
        UserDetails details = this.userService.loadUserByUsername(username);

        return new TokenTransfer(TokenUtils.createToken(details));
    }

    private Map<String, Boolean> createRoleMap(UserDetails userDetails) {
        Map<String, Boolean> roles = new HashMap<>();
        for (GrantedAuthority auth : userDetails.getAuthorities()) {
            roles.put(auth.getAuthority(), Boolean.TRUE);
        }
        return roles;
    }
}