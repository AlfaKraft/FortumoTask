package com.FortumoTask.httpModels;

public class RequestHttp {
    private HttpMethod method;
    private Http_Headers headers;
    private MessageHttp message;



    public void setMessage(MessageHttp message) {
        this.message = message;
    }


    public RequestHttp() {
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(String methodName) throws HttpParsingExeption {
        for (HttpMethod method: HttpMethod.values()){
            if (methodName.equals(method.name())){
                this.method = method;
                return;
            }
        }
        throw new HttpParsingExeption(
                HttpStatusError.CLIENT_ERROR_400_BAD_REQUEST
        );
    }

    public MessageHttp getHttpMessage() {
        return message;
    }


    public Http_Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Http_Headers headers) {
        this.headers = headers;
    }
}
