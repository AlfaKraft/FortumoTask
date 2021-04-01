package com.FortumoTask.httpModels;

import java.util.ArrayList;

public class Http_Headers {
    ArrayList<String> headers;

    public Http_Headers() {
        this.headers = new ArrayList<>();

    }

    public void addTo(String header){
        headers.add(header);
    }

    public ArrayList<String> getHeaders() {
        return headers;
    }
}
