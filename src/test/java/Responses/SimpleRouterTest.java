package Responses;

import Requests.Request;
import org.junit.Test;

public class SimpleRouterTest {

    @Test
    public void testRoute() throws Exception {
        Request fileDirectoryRequest = new Request(
                "GET / HTTP/1.1\r\n" +
                        "Host: localhost:4000\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Cache-Control: max-age=0\r\n\r\n"
        );
        assert new SimpleRouter().route(fileDirectoryRequest).status != 404;

        Request fourOFour = new Request(
                "GET /C: HTTP/1.1\r\n" +
                        "Host: localhost:4000\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Cache-Control: max-age=0\r\n\r\n"
        );
        assert new SimpleRouter().route(fourOFour) != null;
    }

    @Test
    public void testIsFileDirectory() throws Exception {
        assert SimpleRouter.isFileDirectory(System.getProperty("user.dir") + "/");
    }

    @Test
    public void testGenerateFourOFour() throws Exception {
        assert SimpleRouter.generateFourOFour().status == 404;
    }

}