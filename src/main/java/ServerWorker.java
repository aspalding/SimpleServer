import Requests.*;
import Responses.*;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;

public class ServerWorker implements Runnable {
    public Request clientRequest;
    public Socket connection;
    public Router router;

    public ServerWorker(Socket connection) {
        this.connection = connection;
        this.router = new SimpleRouter();
        process();
    }

    public ServerWorker(Socket connection, Router router) {
        this.connection = connection;
        this.router = router;
        process();
    }

    public void process() {
        try {
            this.clientRequest = new Request(
                    SocketIO.readFullRequest(connection.getInputStream())
            );
        } catch(Exception e) {
            this.clientRequest = null;
        }
    }

    public void run() {
        try {
            while (!connection.isClosed()) {
                if(clientRequest == null)
                    connection.close();
                else {
                    Response response = router.route(clientRequest);

                    SocketIO.writeResponse(
                            response.statusAndHeadersToString(),
                            response.body,
                            connection.getOutputStream()
                    );

                    connection.close();
                }
            }
        } catch(Exception e){
            //Intentionally empty.
        }
    }
}
