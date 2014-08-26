package Responses.Routes;

import Requests.Request;
import Responses.Route;
import org.junit.*;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class FileDirectoryRouteTest {
    Request request, rangeRequest, putRequest, postRequest, directoryRequest;
    FileDirectoryRoute route, rangeRoute, putRoute, postRoute, directoryRoute;
    static Path tempFile;



    public static void createTempFile() throws Exception {
        tempFile = Files.createTempFile(Paths.get(System.getProperty("user.dir")), "temp", ".html");
        tempFile.toFile().deleteOnExit();
        List<String> lines = Arrays.asList("Line1", "Line2");
        Files.write(tempFile, lines, Charset.defaultCharset(), StandardOpenOption.WRITE);
    }

    @BeforeClass
    public static void beforeSetUp() throws Exception {
        createTempFile();
    }

    @Before
    public void setUp() throws Exception {
        request = new Request(
                "GET /" + tempFile.getFileName().toString() + " HTTP/1.1\r\n" +
                        "Host: localhost:4000\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Cache-Control: max-age=0\r\n\r\n"
        );
        route = new FileDirectoryRoute(request);

        rangeRequest = new Request(
                "GET /" + tempFile.getFileName().toString() + " HTTP/1.1\r\n" +
                        "Host: localhost:4000\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Range: bytes=0-4\r\n" +
                        "Cache-Control: max-age=0\r\n\r\n" +
                        "body=notnil"
        );
        rangeRoute = new FileDirectoryRoute(rangeRequest);

        putRequest = new Request(
                "PUT /" + tempFile.getFileName().toString() + " HTTP/1.1\r\n" +
                        "Host: localhost:4000\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Cache-Control: max-age=0\r\n\r\n"
        );
        putRoute = new FileDirectoryRoute(putRequest);

        postRequest = new Request(
                "POST /" + tempFile.getFileName().toString() + " HTTP/1.1\r\n" +
                        "Host: localhost:4000\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Cache-Control: max-age=0\r\n\r\n"
        );
        postRoute = new FileDirectoryRoute(postRequest);

        directoryRequest = new Request(
                "GET / HTTP/1.1\r\n" +
                        "Host: localhost:4000\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Cache-Control: max-age=0\r\n\r\n"
        );
        directoryRoute = new FileDirectoryRoute(directoryRequest);
    }

    @Test
    public void testRespond() throws Exception {
        assert route.respond().status == 200;
        assert rangeRoute.respond().status == 206;
        assert putRoute.respond().status == 200;
        assert postRoute.respond().status == 200;

        Request request = new Request(
                "OPTION / HTTP/1.1\r\n" +
                        "Host: localhost:4000\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Cache-Control: max-age=0\r\n\r\n"
        );
        Route route = new FileDirectoryRoute(request);
        assert route.respond().status == 405;
    }

    @After
    public void tearDown() throws Exception {
        //Files.delete(tempFile);
    }

    @Test
    public void testGet() throws Exception {
        createTempFile();
        assert route.get().status == 200;

    }

    @Test
    public void testPut() throws Exception {
        assert route.put() != null;
    }

    @Test
    public void testPost() throws Exception {
        assert route.post() != null;
    }

    @Test
    public void testGenerateHeaders() throws Exception {
        assert route.generateHeaders().containsValue("text/html");
    }

    @Test
    public void testGenerateBody() throws Exception {
        assert new String(directoryRoute.generateBody()).contains("li");
    }

    @Test
    public void testFileToBytes() throws Exception {
        assert route.fileToBytes(request.path).length != 0;
    }

    @Test
    public void testFileToBytesWithRange() throws Exception {
        assert route.fileToBytes(request.path, 4).length == 4;
    }

    @Test
    public void testSadFileToBytes() throws Exception {
        Request request = new Request(
                "GET /virus.exe HTTP/1.1\r\n" +
                        "Host: localhost:4000\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Cache-Control: max-age=0\r\n\r\n" +
                        "body=notnil"
        );
        FileDirectoryRoute route = new FileDirectoryRoute(request);
        assert route.fileToBytes(request.path) == null;
        assert route.fileToBytes(request.path, 4) == null;
    }
}