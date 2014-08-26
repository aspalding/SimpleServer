package Responses;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Response {
    public final Integer status;
    public final String reason;
    public final HashMap<String, String> headers;
    public final byte[] body;

    public Response(Integer status, String reason, HashMap<String, String> headers, byte[] body){
        this.status = status;
        this.reason = reason;
        this.headers = headers;
        this.body = body;
    }

    public Response(Integer status, String reason, HashMap<String, String> headers, String body){
        this.status = status;
        this.reason = reason;
        this.headers = headers;
        this.body = body.getBytes();
    }

    public String statusAndHeadersToString(){
        String response = "HTTP/1.1 " + status + " " + reason + "\n";
        List<Object> headerKeys = Arrays.asList(headers.keySet().toArray());

        for(Object key: headerKeys){
            String headerKey = (String) key;
            response += headerKey + ": " + headers.get(headerKey) + "\n";
        }

        return response;
    }

    public String toString(){
        String response = "HTTP/1.1 " + status + " " + reason + "\n";
        List<Object> headerKeys = Arrays.asList(headers.keySet().toArray());

        for(Object key: headerKeys){
            String headerKey = (String) key;
            response += headerKey + ": " + headers.get(headerKey) + "\n";
        }

        response += "\n" + new String(body);

        return response;
    }

}
