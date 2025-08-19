package edu.escuelaing.arep.app;


import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
public class HttpServerTest {


    @Test
    public void testGetMovieInformationValidTitle() {
        String uriStr = "/movies?title=Inception";
        APIController movieAPI = new APIController();
        HttpServer httpServer = new HttpServer();
        String result = httpServer.getMovieInformation(uriStr);
        assertTrue(result.contains("Inception"));
        assertTrue(result.contains("2010"));
        assertTrue(result.contains("Sci-Fi"));
    }

    @Test
    public void testGetMovieInformationInvalidTitle() {
        String uriStr = "/movies?title=NonExistentMovie";
        APIController movieAPI = new APIController();
        HttpServer httpServer = new HttpServer();
        String result = httpServer.getMovieInformation(uriStr);
        System.out.println(result);
        assertTrue(result.contains("Not Found"));
        assertTrue(result.contains("nonexistentmovie movie not found"));
    }
}
