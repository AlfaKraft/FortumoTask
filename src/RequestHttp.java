public class RequestHttp {
    private HttpMethod method;
    private String requestTarget;
    private String httpVersion;
    private String header;
    private MessageHttp message;



    public void setMessage(MessageHttp message) {
        this.message = message;
    }

    public void setRequestTarget(String requestTarget) {
        this.requestTarget = requestTarget;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
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
}
