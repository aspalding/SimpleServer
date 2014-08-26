import Responses.Router;

import java.net.ServerSocket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application{
    public static ServerSocket serverSocket;
    public static ExecutorService executorService;

    public static void main (String cat[]) throws Exception{
        new Application().setUp(cat);
        runLoop();
    }

    public void setUp(String cat[]) throws Exception {
        ArgsParser.parseArguments(Arrays.asList(cat));

        System.setProperty("user.dir", ArgsParser.root);
        serverSocket = new ServerSocket(ArgsParser.port, 8192);

        executorService = Executors.newFixedThreadPool(16);
    }

    public static void runLoop() throws Exception{
        while(!serverSocket.isClosed()){
            executorService.submit(new ServerWorker(serverSocket.accept()));
        }
    }

    public static void runLoop(Router router) throws Exception{
        while(!serverSocket.isClosed()){
            executorService.submit(new ServerWorker(serverSocket.accept(), router));
        }
    }
}
