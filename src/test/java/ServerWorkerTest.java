
import Responses.SimpleRouter;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class ServerWorkerTest {

    @Test
    public void testRunValid() throws Exception {
        final String request = "GET /src/TestMedia/ HTTP/1.1\n" +
                "Host: localhost:4000\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
                "Accept-Language: en-US,en;q=0.5\n" +
                "Accept-Encoding: gzip, deflate\n" +
                "Connection: keep-alive\n" +
                "Cache-Control: max-age=0\n";

        Socket mockSocket = new Socket(){
            boolean closedState = false;

            @Override
            public boolean isClosed(){
                return closedState;
            }
            @Override
            public InputStream getInputStream(){
                return new ByteArrayInputStream(request.getBytes());
            }

            public OutputStream getOutputStream(){

                return new ByteArrayOutputStream();
            }

            @Override
            public void close(){
                closedState = true;
            }
        };

        ServerWorker worker = new ServerWorker(mockSocket);
        worker.run();
        assert mockSocket.isClosed();
    }

}