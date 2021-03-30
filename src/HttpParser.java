import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpParser {

    private static final int SP = 0x20; //32
    private static final int CR = 0x0D; //13
    private static final int LF = 0x0A; //10
    private static final int QUOTE = 0x27; // '



    public RequestHttp parseHttpRequest(InputStream inputStream){
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        RequestHttp request = new RequestHttp();

        try {
            parseRequestLine(inputStreamReader, request);
        } catch (IOException | HttpParsingExeption e) {
            e.printStackTrace();
        }
        try {
            parseHeaders(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            parseMessageBody(inputStreamReader, request);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
        
        
    }

    private void parseMessageBody(InputStreamReader inputStreamReader, RequestHttp requestHttp) throws IOException {
        MessageHttp messageHttp = new MessageHttp();
        int _byte;
        _byte = inputStreamReader.read();
        if (_byte == QUOTE){
            StringBuilder message = new StringBuilder();
            while (true){
                _byte = inputStreamReader.read();
                if (_byte == QUOTE){
                    break;
                }
                else {
                    message.append((char) _byte);
                }
            }
            messageHttp.setMessage(message.toString());
            requestHttp.setMessage(messageHttp);

        }
        else {
            StringBuilder message = new StringBuilder();
            message.append((char) _byte);
            while (inputStreamReader.ready()){
                _byte = inputStreamReader.read();
                message.append((char) _byte);
            }
            messageHttp.setMessage(message.toString());
            requestHttp.setMessage(messageHttp);
        }



    }

    private void parseHeaders(InputStreamReader inputStreamReader) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        boolean HeaderParsed = false;

        int _byte;
        while ((_byte = inputStreamReader.read()) >= 0) {
            if(_byte == CR){
                _byte = inputStreamReader.read();
                if (_byte == LF){
                    System.out.println(stringBuilder.toString());
                    _byte = inputStreamReader.read();
                    if (_byte == CR){
                        _byte = inputStreamReader.read();
                        if(_byte == LF){
                            return;
                        }
                    }
                    else {
                        stringBuilder.delete(0, stringBuilder.length());
                        stringBuilder.append((char) _byte);
                    }
                }
            }
            else {
                stringBuilder.append((char) _byte);
            }
        }


    }

    private void parseRequestLine(InputStreamReader inputStreamReader, RequestHttp request) throws IOException, HttpParsingExeption {
        StringBuilder stringBuilder = new StringBuilder();

        boolean methodParsed = false;
        boolean requestTargetParsed = false;

        int _byte;
        while ((_byte = inputStreamReader.read())>=0){
            if(_byte == CR){
                _byte = inputStreamReader.read();
                if (_byte == LF){

                    System.out.println("Request line VERSION to process : " + stringBuilder.toString());
                    if (!methodParsed || !requestTargetParsed){
                        throw new HttpParsingExeption((HttpStatusError.CLIENT_ERROR_400_BAD_REQUEST));
                    }
                    return;
                }
            }

            if (_byte == SP){
                if (!methodParsed){
                    System.out.println("Request line METHOD to process : " + stringBuilder.toString());
                    request.setMethod(stringBuilder.toString());
                    methodParsed = true;
                }
                else if (!requestTargetParsed){
                    System.out.println("Request line REQ TARGET to process : " + stringBuilder.toString());
                    requestTargetParsed = true;
                }
                else  {
                    throw new HttpParsingExeption(HttpStatusError.CLIENT_ERROR_400_BAD_REQUEST);
                }

                stringBuilder.delete(0, stringBuilder.length());

            }
            else {
                stringBuilder.append((char) _byte);
                if (!methodParsed){
                    if (stringBuilder.length() > HttpMethod.MAX_LENGTH){
                        throw new HttpParsingExeption(HttpStatusError.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                }
            }
        }

    }
}
