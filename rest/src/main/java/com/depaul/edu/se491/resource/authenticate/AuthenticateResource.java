package com.depaul.edu.se491.resource.authenticate;

import com.depaul.edu.se491.dao.user.UserEntity;
import com.depaul.edu.se491.global.AppConstants;
import com.depaul.edu.se491.resource.AuthenticationExceptionEntryPoint;
import com.depaul.edu.se491.resource.CreateAccountExceptionEntryPoint;
import com.depaul.edu.se491.resource.UserDisabledEntryPoint;
import com.depaul.edu.se491.resource.UserNotFoundEntryPoint;
import com.depaul.edu.se491.resource.user.User;
import com.depaul.edu.se491.resource.user.UserRole;
import com.depaul.edu.se491.service.email.EmailService;
import com.depaul.edu.se491.service.user.UserRoleService;
import com.depaul.edu.se491.service.user.UserService;
import com.depaul.edu.se491.utils.TokenUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tom Mitic on 3/9/15.
 */
@Component
@Path("/authenticate")
public class AuthenticateResource {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticateResource.class);

    @Context
    private HttpServletRequest httpRequest;

    @Context
    private HttpServletResponse httpResponse;

    @Autowired
    private UserNotFoundEntryPoint unfep;

    @Autowired
    private UserDisabledEntryPoint udep;

    @Autowired
    private AuthenticationExceptionEntryPoint aeep;

    @Autowired
    private CreateAccountExceptionEntryPoint caeep;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRoleService userRoleService;

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
        Authentication securityAuth = SecurityContextHolder.getContext().getAuthentication();
        Object p = securityAuth.getPrincipal();
        if (p instanceof String && ("anonymousUser").equals((String) p)) {
            throw new WebApplicationException(401);
        }
        UserDetails  details = (UserDetails) p;
        return new UserTransfer(details.getUsername(), this.createRoleMap(details), ((UserEntity) p).getUuid());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public TokenTransfer authenticate(
            @FormParam("username") String username,
            @FormParam("password") String password) throws Throwable {

        UserDetails details = this.userDetailsService.loadUserByUsername(username);

        if (details.getUsername() != null && details.getPassword() != null &&
                details.isAccountNonExpired() && details.isAccountNonLocked() &&
                details.isCredentialsNonExpired() && details.isEnabled()) {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            try {
                Authentication authenticate = this.auth.authenticate(authToken);
                SecurityContextHolder.getContext().setAuthentication(authenticate);

                /*
                 * Reload user as password of authentication principal will be null after authorization and
                 * password is needed for token generation
                 */
                details = this.userDetailsService.loadUserByUsername(username);
            } catch (AuthenticationException e) {
                logger.error(
                        String.format("User %s with password %s is unable to the authenticated, the underlying exception: %s",
                                username, password, e));
                aeep.commence(httpRequest, httpResponse, e);
            }
        } else if (details.getUsername() != null && details.getPassword() != null &&
                !details.isAccountNonExpired() && !details.isAccountNonLocked() &&
                !details.isCredentialsNonExpired() && !details.isEnabled()) {
            udep.commence(httpRequest, httpResponse,
                    new DisabledException(AppConstants.UNAUTHORIZED_USER_DISABLED));
        } else {
            unfep.commence(httpRequest, httpResponse,
                    new AuthenticationCredentialsNotFoundException(AppConstants.UNAUTHORIZED_USER_NOT_FOUND));
        }
        return new TokenTransfer(TokenUtils.createToken(details));
    }

    @POST
    @Path("/create_user")
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(
            @FormParam("uuid") String uuid,
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName) throws Throwable {
        User user = new User();
        user.setEmail(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUuid(uuid);
        user.setEnabled(true);

        User createdUser = new User();
        try {
            Long id = userService.createUser(user);

            UserRole userRole = new UserRole("ROLE_USER", username, id);
            userRoleService.addUserRole(userRole);

            createdUser = userService.getUserById(id);
        } catch (Exception e) {
            logger.error(
                    String.format("Unable to create user: %s", e));
            caeep.commence(httpRequest, httpResponse, e);
        }
        return createdUser;
    }
    
    @POST
    @Path("/get_user_email")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserEmail(
    		@FormParam("email") String email) throws Throwable{
    	User user = new User();
    	try{
    		 user = userService.getUserByEmail(email); 
    		 
    		 if(user!=null){  
    			 System.out.println(email);
    			 emailService.sendEmail(email, "https://chicagostatstracker.com/resetPassword?uuid=" +user.getUuid());
    		 }
    	} catch(Exception e) {
    		 logger.error(
                     String.format("Unable to find email: %s", e));
             caeep.commence(httpRequest, httpResponse, e);
    	}   	
    	return user;
    }
    
    @POST
    @Path("/reset_password")
    @Produces(MediaType.APPLICATION_JSON)
    public User restPassword(
    		@FormParam("uuid") String uuid,
    		@FormParam("question") String question, 
    		@FormParam("password") String password) throws Throwable{
    	User user = new User();
         try{
        	 user = userService.getUserByUuid(uuid);
        	 
        	 if(question.equals(user.getEmail())){
        		 user.setPassword(password);
        		 userService.updateUser(user);   
        	 }
        	 else{
        		  unfep.commence(httpRequest, httpResponse,
                          new AuthenticationCredentialsNotFoundException(AppConstants.UNAUTHORIZED_USER_NOT_FOUND));
        	 }   	
         }catch(Exception e) {
    		 logger.error(
                     String.format("Unable to find user_uuid: %s", e));
             caeep.commence(httpRequest, httpResponse, e);
    	}       	
    	return user;
    }
    @POST
    @Path("/update_user")
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(
    		@FormParam("uuid") String uuid,  
    		@FormParam("email") String email, 
    		@FormParam("first") String firstName, 
    		@FormParam("last") String lastName, 
    		@FormParam("oldpassword") String oldpwd,
    		@FormParam("password") String password) throws Throwable{
    	
 
    		User user = new User();  	 
        	user = userService.getUserByUuid(uuid); 
        	String username = user.getEmail();
	 
         	UserDetails details = this.userDetailsService.loadUserByUsername(user.getEmail());
         	
         	if (details.getUsername() != null && details.getPassword() != null) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, oldpwd);
                try {
                    Authentication authenticate = this.auth.authenticate(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authenticate);
  
                    user.setEmail(email);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setPassword(password);
                    
           		 	userService.updateUser(user);
                    
                } catch (AuthenticationException e) {
                    logger.error(
                            String.format("User %s with password %s is unable to the authenticated, the underlying exception: %s",
                                    username, password, e));
                    aeep.commence(httpRequest, httpResponse, e);
                }
            }
          	
    	return user;
    }

    @GET
    @Path("/get_user_uuid")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserByUuid(
            @QueryParam("uuid") String uuid
    ) {

        User userByUuid = userService.getUserByUuid(uuid);

        return Response.status(200)
                .entity(userByUuid)
                .header("Access-Control-Allow-Headers", "X-extra-header")
                .build();
    }
   
    private Map<String, Boolean> createRoleMap(UserDetails userDetails) {
        Map<String, Boolean> roles = new HashMap<>();
        for (GrantedAuthority authGrant : userDetails.getAuthorities()) {
            roles.put(authGrant.getAuthority(), Boolean.TRUE);
        }
        return roles;
    }
}