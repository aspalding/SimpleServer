package Responses;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ResponseTest {
    Response response;

    @Before
    public void setUp(){
        HashMap<String, String> hm = new HashMap<String, String>(){
            {
                put("Content-Type", "text/html");
            }
        };

        response = new Response(200, "OK", hm, "".getBytes());
    }

    @Test
    public void testStatusAndHeadersToString() throws Exception {
        assertEquals("HTTP/1.1 200 OK\nContent-Type: text/html\n", response.statusAndHeadersToString());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("HTTP/1.1 200 OK\nContent-Type: text/html\n\n", response.toString());
    }
}