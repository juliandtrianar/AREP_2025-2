package edu.escuelaing.arep.app;


import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
public class APIControllerTest {

    @Test
    public void testConnectToMoviesAPI() throws IOException {
        String expectedResponse = "{\"Title\":\"Inception\",";
        APIController ApiController = new APIController();
        String actualResponse = ApiController.connectToMoviesAPI("Inception");
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void testConnectToMoviesAPIMovieNotExist() throws IOException {
        APIController ApiController = new APIController();
        String actualResponse = ApiController.connectToMoviesAPI("NonExistentMovie");
        assertNull(actualResponse);
    }

    @Test
    public void testCacheBehavior() throws IOException {
        APIController apiController = new APIController();
        assertEquals(0, APIController.getCache().size());
        String response = apiController.connectToMoviesAPI("Wish");
        assertEquals(1, APIController.getCache().size());
    }

    @Test
    public void testCacheBehaviorNotMovieFound() throws IOException {
        APIController apiController = new APIController();
        String response = apiController.connectToMoviesAPI("Wish");
        assertEquals(1, APIController.getCache().size());
        String actualResponse = apiController.connectToMoviesAPI("NonExistentMovie");
        assertNull(actualResponse);
        assertEquals(1, APIController.getCache().size());
    }



}
