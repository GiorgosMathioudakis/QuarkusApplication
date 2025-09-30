package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/movies")
public class MovieResource {

    public static List<String> movies = new ArrayList<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMovies() {
        return Response.ok(movies).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
    public Integer countMovies() {
        return movies.size();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addMovie(String movie) {
        movies.add(movie);
        return Response.status(Response.Status.CREATED).build();
    }



    @DELETE
    @Path("/{movie}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response deleteMovie(
            @PathParam("movie") String movie) {

        movies.remove(movie);
        return Response.status(Response.Status.NO_CONTENT).build();

    }
}
