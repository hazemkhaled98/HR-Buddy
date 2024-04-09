package com.hrbuddy.rest.controllers;


import com.hrbuddy.rest.exceptions.UnauthorizedException;
import com.hrbuddy.rest.security.SecurityManager;
import com.hrbuddy.services.UserService;
import com.hrbuddy.services.dto.UserDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;


@Path("/users")
public class UserController {

    @POST
    @Path("/login")
    @Produces("application/json")
    @Consumes("application/json")
    public UserDTO login(UserDTO user){
        try{
            UserDTO loggedUser = UserService.login(user);
            String token = SecurityManager.generateToken(loggedUser.getRole());
            loggedUser.setToken(token);
            return loggedUser;
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException("Wrong Credentials. Email or password are incorrect");
        }
    }


    @POST
    @Path("/register")
    @Produces("application/json")
    @Consumes("application/json")
    public UserDTO register(UserDTO user){
        UserDTO registeredUser = UserService.register(user);
        String token = SecurityManager.generateToken(registeredUser.getRole());
        registeredUser.setToken(token);
        return registeredUser;
    }

}
