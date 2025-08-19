package edu.escuelaing.arep.app;


import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The `HttpServer` class represents a simple HTTP server that listens on port 35000 and handles incoming HTTP requests.
 * It utilizes a basic API for retrieving movie information and responds with HTML content based on the request URI.
 *
 * It supports requests related to movie information and provides a default HTML response.
 */
public class HttpServer
{
    /**
     * Represents the API controller for fetching movie information.
     */
    private static MovieAPI myMoviesAPI = new APIController();


    /**
     * The main method that serves as the entry point for the HTTP server.
     * This method sets up a server socket on port 35000 and continuously listens for incoming client connections.
     * When a client connection is accepted, it spawns a new thread to handle the client request.
     * @param args The command line arguments passed to the program.
     * @throws Exception If an error occurs during the execution of the server.
     */
    public static void main( String[] args ) throws Exception
    {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
                handleClientRequest(clientSocket);
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
        serverSocket.close();
    }

    /**
     * Handles incoming client requests on the server.
     * Reads client requests, processes the request, and sends a response back to the client.
     * @param clientSocket The client socket through which communication with the server occurs.
     * @throws IOException If an I/O error occurs while reading from or writing to the client socket.
     * @throws URISyntaxException If an error occurs while parsing the URI of the request.
     */
    private static void handleClientRequest(Socket clientSocket) throws IOException, URISyntaxException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));
        String inputLine, outputLine = null;
        boolean firstLine = true;
        String uriStr = "";
        while ((inputLine = in.readLine()) != null) {
            if(firstLine){
                uriStr = inputLine.split(" ")[1];
                firstLine = false;
            }
            System.out.println("Received: " + inputLine);
            if (!in.ready()) {
                break;
            }
        }
        URI requestURI = new URI(uriStr);
        // Llamado al API
        if(uriStr.contains("movies?name=")) {
            uriStr = URLDecoder.decode(uriStr, "UTF-8");
            outputLine = getMovieInformation(uriStr);
        }else{
            try {
                if(uriStr.contains("png") || uriStr.contains("jpg")){
                    handleImageRequest(requestURI, clientSocket.getOutputStream());
                }else{
                    outputLine = httpResponseFile(requestURI);
                }
            }catch (Exception e){
                e.printStackTrace();
                outputLine = HTMLBuilder.httpError();
            }
        }
        out.println(outputLine);
        out.println();
        out.close();
        in.close();
        clientSocket.close();
    }

    /**
     * Generates an HTTP response for a requested file.
     * This method reads the content of the requested file from the server's filesystem,
     * constructs an HTTP response header with the appropriate content type, and returns
     * the HTTP response as a string.
     * @param requestedURI The URI of the requested file.
     * @return The HTTP response string containing the content of the requested file.
     * @throws IOException If an I/O error occurs while reading the file or constructing the response.
     */
    public static String httpResponseFile(URI requestedURI) throws IOException{
        String path = requestedURI.getPath();
        if (!path.contains(".")) {
            path = "/index.html";
        }
        Path file = Paths.get("target/classes/public" + path);
        String extension = path.substring(path.lastIndexOf('.') + 1);
        String contentType = HTMLBuilder.getContentType(extension);
        String outputLine = HTMLBuilder.httpOkHeader(contentType);
        Charset charset = Charset.forName("UTF-8");
        BufferedReader reader = Files.newBufferedReader(file, charset);
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            outputLine = outputLine + line;
        }
        return  outputLine;
    }

    /**
     * Handles an image request by reading the requested image file from the server's filesystem
     * and writing it to the provided output stream along with an HTTP response header.
     * @param requestedURI The URI of the requested image file.
     * @param out The output stream to which the image content and HTTP response header will be written.
     * @throws IOException If an I/O error occurs while reading the image file or writing to the output stream.
     */
    public static void handleImageRequest(URI requestedURI, OutputStream out) throws IOException {
        String path = requestedURI.getPath();
        String extension = path.substring(path.lastIndexOf('.') + 1);
        String contentType = HTMLBuilder.getContentType(extension);
        String outputLine = HTMLBuilder.httpOkHeader(contentType);
        Path file = Paths.get("target/classes/public" + path);
        byte[] fileArray;
        fileArray = Files.readAllBytes(file);
        out.write(outputLine.getBytes());
        out.write(fileArray, 0, fileArray.length);
        out.close();
    }

    /**
     * Retrieves movie information based on the provided URI and returns the corresponding HTML response.
     * @param uriStr The URI containing the movie title information.
     * @return The HTML response containing movie data or an error message.
     */
    public static String getMovieInformation(String uriStr ){
        String title = uriStr.split("=")[1].toLowerCase();
        try {
            String movieJSON = myMoviesAPI.connectToMoviesAPI(title);
            if(movieJSON != null) {
                return HTMLBuilder.httpMovieData(movieJSON);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return HTMLBuilder.httpMovieError(title);
    }

}

