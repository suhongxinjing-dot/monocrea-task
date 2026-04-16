package jp.co.monocrea.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.List;

import jp.co.monocrea.entity.User;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    /**
     * ユーザー全件取得
     */
    @GET
    public List<User> getAll() {
        return User.listAll();
    }

    /**
     * ユーザー1件取得
     */
    @GET
    @Path("/{id}")
    public User getOne(@PathParam("id") Long id) {
        User user = User.findById(id);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }

    /**
     * ユーザー新規作成
     */
    @POST
    @Transactional
    public User create(User user) {
        user.id = null;
        user.persist();
        return user;
    }

    /**
     * ユーザー更新
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public User update(@PathParam("id") Long id, User input) {
        User entity = User.findById(id);

        if (entity == null) {
            throw new NotFoundException("User not found");
        }

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
        boolean deleted = User.deleteById(id);

        if (!deleted) {
            throw new NotFoundException("User not found");
        }
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