package Responses;

import Requests.Request;

public interface Router {
    public Response route(Request request);
}
