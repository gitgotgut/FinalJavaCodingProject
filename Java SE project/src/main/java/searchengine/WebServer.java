package searchengine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
 
 /**
  * This class provides a simple HTTP server that can be used to serve web pages.
  *
  */
 public class WebServer {
    static final int PORT = 8080;
    static final int BACKLOG = 0;
    static final Charset CHARSET = StandardCharsets.UTF_8;

    HttpServer server;
    private PageLoader pageLoader;
    private ScoringMethod scoringMethod;
    /**
     * Constructs a new WebServer object with the specified port, pageLoader, and scoringMethod.
     *
     * @param  port           the port number to listen on
     * @param  pageLoader     the PageLoader object to use for loading pages
     * @param  scoringMethod  the ScoringMethod object to use for scoring search results
     * @throws IOException    if an I/O error occurs while creating the server
     */
    public WebServer(int port, PageLoader pageLoader, ScoringMethod scoringMethod) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), BACKLOG);
        this.pageLoader = pageLoader;
        this.scoringMethod = scoringMethod;
        setupRoutes();
    }

    /**
     * Responds to an HTTP request with a specified code, mime type, and response body.
     *
     * @param io        the HTTP exchange object representing the request and response
     * @param code      the HTTP response code
     * @param mime      the mime type of the response
     * @param response  the response as a byte array
     */
    public static void respond(HttpExchange io, int code, String mime, byte[] response) {
        try {
            io.getResponseHeaders()
                    .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
            io.sendResponseHeaders(200, response.length);
            io.getResponseBody().write(response);
        } catch (Exception e) {
        } finally {
            io.close();
        }
    }

    /**
     * Sets up the routes for the server.
     *
     */
    private void setupRoutes() {
        server.createContext("/", io -> respond(io, 200, "text/html", FileHandler.getFile("web/index.html")));
        server.createContext("/search", io -> SearchHandler.searchResults(io, pageLoader, scoringMethod));
        server.createContext(
                "/favicon.ico", io -> respond(io, 200, "image/x-icon", FileHandler.getFile("web/favicon.ico")));
        server.createContext(
                "/code.js", io -> respond(io, 200, "application/javascript", FileHandler.getFile("web/code.js")));
        server.createContext(
                "/style.css", io -> respond(io, 200, "text/css", FileHandler.getFile("web/style.css")));
    }

    /**
     * Starts the server and prints the server's address.
     *
     */
    public void startServer() {
        server.start();
        String msg = "WebServer running on http://localhost:" + server.getAddress().getPort();
        System.out.println("╭" + "─".repeat(msg.length()) + "╮");
        System.out.println("│" + msg + "│");
        System.out.println("╰" + "─".repeat(msg.length()) + "╯");
    }

    /**
     * The main entry point for the Java program.
     *
     * @param  args  arguments passed to the program through the command line 
     * @throws IOException  incase an I/O error occurs while reading the file
     */
    public static void main(final String... args) throws IOException {
        String filename = Files.readString(Paths.get("config.txt")).strip();
        PageLoader pageLoader = new PageLoader(filename);
        ScoringMethod scoringMethod = new TermFrequencyScore();

        // SearchHandler searchHandler = new SearchHandler(scoringMethod);
        // String searchTerm = "apple banana OR pie";
        // searchHandler.printSearchResultsWithScores(searchTerm);
    
        WebServer webServer = new WebServer(PORT, pageLoader, scoringMethod);
        webServer.startServer();

    }
}