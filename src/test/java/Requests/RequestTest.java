package Requests;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class RequestTest {
    Request request;

    @Before
    public void setUp() throws Exception {
        String req = "GET /src/TestMedia/ HTTP/1.1\r\n" +
                "Host: localhost:4000\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n\r\n" +
                "body=notnil";

        request = new Request(req);
    }


    @Test
    public void testRequestMethod() throws Exception {
        String itShould = "GET";
        assertEquals(itShould, request.method);
    }

    @Test
    public void testRequestPath() throws Exception {
        String itShould = "/src/TestMedia/";
        assert request.path.contains(itShould);
    }

    @Test
    public void testHeaders() throws Exception{
        HashMap<String, String> ht = new HashMap<String, String>(){
            {
                put("Host", "localhost:4000");
                put("Connection", "keep-alive");
                put("Cache-Control", "max-age=0");
            }
        };

        assertEquals(ht, request.headers);
    }

    @Test
    public void testRequestBody() throws Exception {
        String itShould = "body=notnil";
        assertEquals(itShould, request.body);
    }


    @Test
    public void testRequesNoBody() throws Exception {
        String reqNoBody = "GET /src/TestMedia/ HTTP/1.1\r\n" +
                "Host: localhost:5000\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n\r\n";

        Request requestNoBody = new Request(reqNoBody);

        HashMap<String, String> ht = new HashMap<String, String>(){
            {
                put("Host", "localhost:5000");
                put("Connection", "keep-alive");
                put("Cache-Control", "max-age=0");
            }
        };

        assertEquals(ht, requestNoBody.headers);
    }

    @Test
    public void testRequesBasicAuthHeaders() throws Exception {
        String authReq = "GET /logs HTTP/1.1\r\n" +
                "Authorization: Basic YWRtaW46aHVudGVyMg==\r\n";

        Request authRequest = new Request(authReq);

        assertEquals("Basic YWRtaW46aHVudGVyMg==", authRequest.headers.get("Authorization"));
    }

    @Test
    public void testRequestWithRange() throws Exception{
        String rangeReq = "GET /partial_content.txt HTTP/1.1\r\n" +
                "Range: bytes=0-4\r\n" +
                "Connection: close\r\n" +
                "Host: localhost:5000";

        Request rangeRequest = new Request(rangeReq);

        assertEquals("bytes=0-4", rangeRequest.headers.get("Range"));
    }

}