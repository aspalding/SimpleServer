package Responses;

import Requests.Request;
import Responses.Routes.*;

import java.io.File;
import java.util.HashMap;

public class SimpleRouter implements Router{
    public Response route(Request request){
        Response response;

        if(isFileDirectory(request.path)){
            response = new FileDirectoryRoute(request).respond();
        } else {
                response = generateFourOFour();
        }

        return response;
    }

    public static boolean isFileDirectory(String path){
        return new File(path).exists();
    }

    public static Response generateFourOFour(){
        return new Response(404, "Not Found", new HashMap<>(), "Page Not Found.");
    }

}
