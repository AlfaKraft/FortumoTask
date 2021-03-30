public enum HttpStatusError {

    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
    CLIENT_ERROR_401_BAD_METHOD_NOT_ALLOWED(401, "Method not allowed");

    public final int STATUS_CODE;
    public final String MESSAGE;

    HttpStatusError(int STATUS_CODE, String MESSAGE) {
        this.STATUS_CODE = STATUS_CODE;
        this.MESSAGE = MESSAGE;
    }
}
