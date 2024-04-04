package example.requests;

import example.endpoints.Endpoint;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import java.io.IOException;
import java.net.SocketTimeoutException;

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

    okhttp3.Request request;

    // unauthenticated request
    if(token.getValue().equals("")) {
      if ("GET".equals(endpoint.getMethod())) { // GET request
        request = new okhttp3.Request.Builder()
                .url(endpoint.getUrl())
                .build();
      } else if ("DELETE".equals(endpoint.getMethod())) { // DELETE request
        request = new okhttp3.Request.Builder()
                .url(endpoint.getUrl())
                .delete()
                .build();
      } else { // All other methods
        request = new okhttp3.Request.Builder()
                .url(endpoint.getUrl())
                .method(endpoint.getMethod(), endpoint.getBody())
                .addHeader("Content-Type", endpoint.getContentType())
                .build();
      }
    }
    // authenticated request
    else {
      if ("GET".equals(endpoint.getMethod())) { // GET request
        request = new okhttp3.Request.Builder()
                .url(endpoint.getUrl())
                .addHeader(token.getHeaderName(), token.getValue())
                .build();
      } else if ("DELETE".equals(endpoint.getMethod())) { // DELETE request
        request = new okhttp3.Request.Builder()
                .url(endpoint.getUrl())
                .delete()
                .addHeader(token.getHeaderName(), token.getValue())
                .build();
      } else { // All other methods
        request = new okhttp3.Request.Builder()
                .url(endpoint.getUrl())
                .method(endpoint.getMethod(), endpoint.getBody())
                .addHeader(token.getHeaderName(), token.getValue())
                .addHeader("Content-Type", endpoint.getContentType())
                .build();
      }
    }

    // add headers to request
    for (String header : endpoint.getHeaders()) {
      String[] headerParts = header.split(":");
      request = request.newBuilder().addHeader(headerParts[0], headerParts[1]).build();
    }


    this.request = request;
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
