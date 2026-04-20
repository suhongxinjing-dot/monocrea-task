package jp.co.monocrea.resources;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.List;

import jp.co.monocrea.entity.User;
import jp.co.monocrea.service.UserService;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    /**
     * ユーザー全件取得
     */
    @GET
    public List<User> getAll() {
        return userService.getAll();
    }

    /**
     * ユーザー1件取得
     */
    @GET
    @Path("/{id}")
    public User getOne(@PathParam("id") Long id) {
        User user = userService.getById(id);

        return user;
    }

    /**
     * ユーザー新規作成
     */
    @POST
    @Transactional
    public User create(User user) {
        user.id = null;
        userService.create(user);
        return user;
    }

    /**
     * ユーザー更新
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public User update(@PathParam("id") Long id, User input) {
        User entity = userService.getById(id);

        entity.name = input.name;

        return entity;
    }

    /**
     * ユーザー削除
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        userService.delete(id);
    }

    /**
     * CORS対応（フロント接続用）
     */
    @Provider
    public static class CorsFilter implements ContainerResponseFilter {

        @Override
        public void filter(ContainerRequestContext requestContext,
                           ContainerResponseContext responseContext) throws IOException {

            responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
            responseContext.getHeaders().add("Access-Control-Allow-Headers",
                    "origin, content-type, accept, authorization");
            responseContext.getHeaders().add("Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE, OPTIONS");
        }
    }

    /**
     * preflight対応（PUT/DELETEで必須）
     */
    @OPTIONS
    @Path("{any:.*}")
    public void options() {
        // no body needed
    }
}