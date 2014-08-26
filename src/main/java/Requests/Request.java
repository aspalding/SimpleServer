package Requests;

import java.util.HashMap;
import java.util.StringTokenizer;

public class Request {
    private StringTokenizer st;
    public HashMap<String, String> headers;
    public String request, method, path, body;


    public Request(String request){
        this.request = request;
        String[] splitBodyFromRequest = request.split("\r\n\r\n");

        this.headers = new HashMap<String, String>();
        this.st = new StringTokenizer(splitBodyFromRequest[0]);

        this.body = (splitBodyFromRequest.length == 2) ? splitBodyFromRequest[1] : "";

        this.method = tokenizeMethod();
        this.path = tokenizePath();
        this.headers = tokenizeHeaders();
    }

    public String tokenizeMethod(){
        return st.nextToken();
    }

    public String tokenizePath(){
        return System.getProperty("user.dir") + st.nextToken();
    }

    public HashMap<String, String> tokenizeHeaders(){
        st.nextToken(); //Discard HTTP Version.
        HashMap<String, String> ht = new HashMap<String, String>();

        String key, value;
        do {
            key = st.nextToken(":");
            value = st.nextToken("\r\n");

            value = value.substring(2, value.length());

            ht.put(key.trim() , value.trim());
        } while(st.hasMoreTokens());

        return ht;
    }
}