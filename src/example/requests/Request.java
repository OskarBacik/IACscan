package example.requests;

import example.endpoints.Endpoint;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Objects;

import example.tokens.Token;

public class Request {

  private static int idCounter = 0;
  private int id;
  private Endpoint endpoint;
  private Token token;
  private okhttp3.Request request;
  private Response response;
  private String responseBodyString;

  public Request(Endpoint endpoint, Token token) throws IOException {

    this.id = createId();
    this.endpoint = endpoint;
    this.token = token;

    OkHttpClient client = new OkHttpClient();

    // create request builder
    okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder().url(endpoint.getUrl());

    // add token for authenticated request
    if (!Objects.equals(token.getValue(), "")) {
      requestBuilder.addHeader(token.getHeaderName(), token.getValue());
    }
    // Set method for GET and DELETE
    if ("GET".equals(endpoint.getMethod())) {
      requestBuilder.get();
    } else if ("DELETE".equals(endpoint.getMethod())) {
      requestBuilder.delete();
    } else {
      // All other methods, include Request Body and Content Type
      requestBuilder.method(endpoint.getMethod(), endpoint.getBody())
              .addHeader("Content-Type", endpoint.getContentType());
    }

    // add other headers to request
    for (String header : endpoint.getHeaders()) {
      try {
        String[] headerParts = header.split(":");
        requestBuilder.addHeader(headerParts[0], headerParts[1]).build();
      }
      catch (Exception e) {
        System.out.println("Error adding header: " + header);
      }
    }

    // build and execute request, collecting response
    this.request = requestBuilder.build();
    Response response = client.newCall(request).execute();

    // TODO: unsuccessful request management

    // set body variable
    this.response = response;
    if (response.body() == null) {
      this.responseBodyString = "";
    }
    else {
      this.responseBodyString = response.body().string();
    }

    response.close();
  }

  private synchronized int createId() {
    return ++idCounter;
  }

  public int getId() {
    return id;
  }

  public void resetId() {
    idCounter = 0;
  }

  public Endpoint getEndpoint() {
    return endpoint;
  }

  public Token getToken() {
    return token;
  }

  public okhttp3.Request getRequest() {
    return request;
  }

  public Response getResponse() {
    return response;
  }

  public String getResponseBodyString() {
    return responseBodyString;
  }

  public String getCustomRequestText() {
    return request.method() + " " + request.url() +
            "\n" + request.headers() + "\n" + this.getEndpoint().getBodyContent();
  }

  public String getCustomResponseText() {
    return response.protocol().name() + " " + response.code() + " " + response.message() +
            "\n" + response.headers().toString() + "\n\n" + this.getResponseBodyString();
  }

  public int getContentLength() {
    String contentLength = this.response.header("Content-Length");
    if (contentLength == null) {
      return 0;
    }
    return Integer.parseInt(contentLength);
  }
}
