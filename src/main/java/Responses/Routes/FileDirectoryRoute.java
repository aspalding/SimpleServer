package Responses.Routes;

import Requests.Request;
import Responses.Response;
import Responses.Route;
import Responses.Views.FolderView;

import java.io.File;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;


public class FileDirectoryRoute implements Route {
    public Request request;
    public String contentType;
    public int range;

    public FileDirectoryRoute(Request request) {
        this.request = request;

        if(new File(this.request.path).isFile())
            contentType = URLConnection.getFileNameMap().getContentTypeFor(request.path);
        else
            contentType = "text/html";

        if(this.request.headers.containsKey("Range"))
            this.range = Integer.parseInt(request.headers.get("Range").split("-")[1]);
    }

    public Response respond(){
        switch (request.method) {
            case "GET":
                return get();
            case "PUT":
                return put();
            case "POST":
                return post();
            default:
                return new Response(405, "Method Not Allowed", new HashMap<>(), "");
        }
    }

    public Response get(){
        if(request.headers.containsKey("Range"))
            return new Response(206, "Partial Content", generateHeaders(), generateBody());
        else
            return new Response(200, "OK", generateHeaders(), generateBody());
    }

    public Response put(){
        return new Response(200, "OK", generateHeaders(), generateBody());
    }

    public Response post(){
        return new Response(200, "OK", generateHeaders(), generateBody());
    }

    public HashMap<String, String> generateHeaders(){
        return new HashMap<String, String>() {
            {
                put("Content-Type", contentType);
            }
        };
    }

    public byte[] generateBody() {
        if(this.request.headers.containsKey("Range"))
            return fileToBytes(request.path, range);
        else if(new File(this.request.path).isFile())
            return fileToBytes(request.path);
        else
            return new FolderView(request.path).buildView();
    }


    public byte[] fileToBytes(String path) {
        try{
            return Files.readAllBytes(Paths.get(path));
        } catch(Exception e){
            return null;
        }
    }

    public byte[] fileToBytes(String path, int range) {
        try{
            byte[] content = Files.readAllBytes(Paths.get(path));
            return Arrays.copyOf(content, range);
        } catch(Exception e){
            return null;
        }
    }

}
