package com.flipfit.resources;

import com.flipfit.core.User;
import com.flipfit.core.Role;
import com.flipfit.service.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    // DTO for user registration
    public static class RegisterUserRequest {
        public String name;
        public String email;
        public String password;
        public Role role;
    }

    // DTO for user login
    public static class LoginRequest {
        public String email;
        public String password;
    }

    @POST
    @Path("/register")
    public Response registerUser(RegisterUserRequest request) {
        if (request.name == null || request.email == null || request.password == null || request.role == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Name, email, password, and role are required.").build();
        }

        Optional<User> existingUser = userService.findUserByEmail(request.email);
        if (existingUser.isPresent()) {
            return Response.status(Response.Status.CONFLICT).entity("User with this email already exists.").build();
        }

        Optional<User> registeredUser = userService.registerUser(request.name, request.email, request.password, request.role);
        if (registeredUser.isPresent()) {
            return Response.status(Response.Status.CREATED).entity(registeredUser.get()).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to register user.").build();
        }
    }

    @POST
    @Path("/signup")
    public Response signupUser(RegisterUserRequest request) {
        return registerUser(request);
    }

    @POST
    @Path("/login")
    public Response loginUser(LoginRequest request) {
        if (request.email == null || request.password == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Email and password are required.").build();
        }

        boolean loginSuccess = userService.loginUser(request.email, request.password);

        return Response.ok(loginSuccess).build();
    }


    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") String id) {
        return userService.findUserById(id)
                .map(user -> Response.ok(user).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("User not found.").build());
    }

    @GET
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    // TODO: Add login endpoint, update user, delete user, etc.
}
