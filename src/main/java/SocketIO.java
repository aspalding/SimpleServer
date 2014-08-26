import java.io.*;

public class SocketIO{
    public static String readFullRequest(InputStream input) throws Exception {
        String content = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        do {
            content += (char) reader.read();
        } while (reader.ready());
        return content;
    }

    public static void writeResponse(String responseHeader, byte[] responseBody, OutputStream output) throws Exception {
        BufferedOutputStream out = new BufferedOutputStream(output);
        out.write(responseHeader.getBytes());
        out.write("\n".getBytes());
        out.write(responseBody);
        out.close();
    }

}