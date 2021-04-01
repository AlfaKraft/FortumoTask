package com.FortumoTask.tests;

import com.FortumoTask.httpModels.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass(){
        httpParser = new HttpParser();
    }

    @Test
    void parserRequestMethodTest() {
        RequestHttp request;
        try {
            request = httpParser.parseHttpRequest(testCaseMethod());
            assertEquals(request.getMethod(), HttpMethod.POST);
        } catch (HttpParsingExeption httpParsingExeption) {
            httpParsingExeption.printStackTrace();
        }



    }
    @Test
    void parserRequestBadMethodTest() {
        try {
            RequestHttp request = httpParser.parseHttpRequest(testCaseBadMethod());
        } catch (HttpParsingExeption e){
            assertEquals(e.getErrorCode(), HttpStatusError.CLIENT_ERROR_400_BAD_REQUEST);
        }


    }
    @Test
    void parserRequestMessageEndIDTest() {
        try {
            RequestHttp request = httpParser.parseHttpRequest(testCaseMessageEndId());
            assertEquals(request.getHttpMessage().getMessage(), "end X");
        } catch (HttpParsingExeption e){
            fail(e);
        }

    }
    @Test
    void parserRequestMessageValueTest() {
        try {
            RequestHttp request = httpParser.parseHttpRequest(testCaseMessageValue());
            assertEquals(request.getHttpMessage().getMessage(), "500");
        } catch (HttpParsingExeption e){
            fail(e);
        }

    }
    @Test
    void parserRequestHeadersTest() {
        try {
            RequestHttp request = httpParser.parseHttpRequest(testCaseHeaders());

            ArrayList<String> headers = new ArrayList<>();
            headers.add("Host: localhost:8082");
            headers.add("User-Agent: curl/7.55.1");
            headers.add("Accept: */*");
            headers.add("Content-Length: 10");
            headers.add("Content-Type: application/x-www-form-urlencoded");

            assertEquals(request.getHeaders().getHeaders().toString(), headers.toString());
        } catch (HttpParsingExeption e){
            fail(e);
        }

    }

    private InputStream testCaseMethod() {
        String request = "POST / HTTP/1.1\r\n" +
                "Host: localhost:8082\r\n" +
                "User-Agent: curl/7.55.1\r\n" +
                "Accept: */*\r\n" +
                "Content-Length: 10\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n" +
                "end X";

        InputStream inputStream = new ByteArrayInputStream(request.getBytes());

        return inputStream;

    }

    private InputStream testCaseBadMethod() {
        String request = "post / HTTP/1.1\r\n" +
                "Host: localhost:8082\r\n" +
                "User-Agent: curl/7.55.1\r\n" +
                "Accept: */*\r\n" +
                "Content-Length: 10\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n" +
                "end X";

        InputStream inputStream = new ByteArrayInputStream(request.getBytes());

        return inputStream;

    }

    private InputStream testCaseMessageEndId() {
        String request = "POST / HTTP/1.1\r\n" +
                "Host: localhost:8082\r\n" +
                "User-Agent: curl/7.55.1\r\n" +
                "Accept: */*\r\n" +
                "Content-Length: 10\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n" +
                "end X";

        InputStream inputStream = new ByteArrayInputStream(request.getBytes());

        return inputStream;

    }
    private InputStream testCaseMessageValue() {
        String request = "POST / HTTP/1.1\r\n" +
                "Host: localhost:8082\r\n" +
                "User-Agent: curl/7.55.1\r\n" +
                "Accept: */*\r\n" +
                "Content-Length: 10\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n" +
                "500";

        InputStream inputStream = new ByteArrayInputStream(request.getBytes());

        return inputStream;

    }
    private InputStream testCaseHeaders() {
        String request = "POST / HTTP/1.1\r\n" +
                "Host: localhost:8082\r\n" +
                "User-Agent: curl/7.55.1\r\n" +
                "Accept: */*\r\n" +
                "Content-Length: 10\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n" +
                "500";

        InputStream inputStream = new ByteArrayInputStream(request.getBytes());

        return inputStream;

    }



}
