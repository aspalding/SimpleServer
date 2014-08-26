import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;

public class SocketIOTest {

    @Test
    public void testReadRequestWithHeadersAndBody() throws Exception {
        String text = "This is not a well formed request.\n" +
                      "But it has multiple lines.\n\n" +
                      "With a body like this.";

        InputStream input = new ByteArrayInputStream(text.getBytes());
        assert SocketIO.readFullRequest(input).contains(text);
    }

    @Test
    public void testWriteResponse() throws Exception {
        OutputStream output = new ByteArrayOutputStream();
        output.write("status and headers\n".getBytes());
        output.write("body".getBytes());

        OutputStream written = new ByteArrayOutputStream();
        SocketIO.writeResponse("status and headers", "body".getBytes(), written);

        assertEquals(output.toString(), written.toString());
    }
}