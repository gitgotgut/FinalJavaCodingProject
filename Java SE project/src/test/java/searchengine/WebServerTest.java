package searchengine;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.BindException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class WebServerTest {
    WebServer server = null;

    @BeforeAll
    void setUp() {
        try {
            var rnd = new Random();
            while (server == null) {
                try {
                    int port = rnd.nextInt(60000) + 1024;
                    PageLoader pageLoader = new PageLoader("data/test-file.txt"); // Adjust filename as necessary
                    ScoringMethod scoringMethod = new TermFrequencyScore();
                    server = new WebServer(port, pageLoader, scoringMethod);
                    server.startServer();
                } catch (BindException e) {
                    // port in use. Try again
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    void tearDown() {
        server.server.stop(0);
        server = null;
    }


    /**
     * Test case to verify that the lookupWebServer method returns the correct result for a non-existing query.
     * It sends a request to the server with a non-existing query and checks if the response contains the expected message.
     * It uses assertEquals to compare the expected JSON response with the actual responced attained from the server.
     */
    @Test
    void lookupWebServer_nonExistingQuery_NoResult() {
        String baseURL = String.format("http://localhost:%d/search?q=", server.server.getAddress().getPort());
         assertEquals("[{\"message\": \"No web page contains the query word.\"}]",
                httpGet(baseURL+ "flower1" ));

         
    }

    /**
     * Test case to verify that the startServer method starts the server successfully.
     * It checks if the port is between 1024 and 61023 to ensure that the server is running on a valid port.
     * @throws Exception If the port is not between 1024 and 61023 or if the port is null.
     * @return true if the port is between 1024 and 61023.
     */

    @Test
    void startServer_validPortRange_serverRunning(){

    int port = server.server.getAddress().getPort();
    assertTrue(port>= 1024 && port <= 61023);   

    }

    private String httpGet(String url) {
        var uri = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder().uri(uri).GET().build();
        try {
            return client.send(request, BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}