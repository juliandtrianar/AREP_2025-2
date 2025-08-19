package edu.escuelaing.arep.app;


import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
public class HTMLBuilderTest {

    @Test
    public void testBuildHttpMovieData() {
        String movieJSON = "{\"title\":\"Inception\",\"year\":2010,\"genre\":\"Sci-Fi\"}";
        String result = HTMLBuilder.httpMovieData(movieJSON);
        assertTrue(result.contains("Inception"));
        assertTrue(result.contains("2010"));
        assertTrue(result.contains("Sci-Fi"));
    }

    @Test
    public void testBuildHttpError() {
        String title = "NonExistentMovie";
        String result = HTMLBuilder.httpMovieError(title);
        assertTrue(result.contains("Not Found"));
        assertTrue(result.contains(title));
    }

    @Test
    public void testGetContentTypeForJS() {
        String contentType = HTMLBuilder.getContentType("js");
        assertEquals("Content-Type: text/javascript\r\n", contentType);
    }

    @Test
    public void testGetContentTypeForHTML() {
        String contentType = HTMLBuilder.getContentType("html");
        assertEquals("Content-Type: text/html\r\n", contentType);
    }

    @Test
    public void testGetContentTypeForCSS() {
        String contentType = HTMLBuilder.getContentType("css");
        assertEquals("Content-Type: text/css\r\n", contentType);
    }

    @Test
    public void testGetContentTypeForPNG() {
        String contentType = HTMLBuilder.getContentType("png");
        assertEquals("Content-Type: image/png\r\n", contentType);
    }

    @Test
    public void testGetContentTypeForJPG() {
        String contentType = HTMLBuilder.getContentType("jpg");
        assertEquals("Content-Type: image/jpg\r\n", contentType);
    }

    @Test
    public void testBuildHttpOkHeader() {
        String contentType = "Content-Type: text/html\r\n";
        String httpOkHeader = HTMLBuilder.httpOkHeader(contentType);
        assertTrue(httpOkHeader.contains("200 OK"));
        assertTrue(httpOkHeader.contains(contentType));
    }

    @Test
    public void testBuildHttpNotFoundError() {
        String httpError = HTMLBuilder.httpError();
        assertTrue(httpError.contains("400 Not Found"));
    }


}
