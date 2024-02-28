package example.requests;

import example.endpoints.Endpoint;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import java.io.IOException;
import example.tokens.Token;

public class Request {

  private static int idCounter = 0;
  private int id;
  private Endpoint endpoint;
  private Token token;
  private Response response;

  public Request(Endpoint endpoint, Token token) throws IOException {

    this.id = createId();
    this.endpoint = endpoint;
    this.token = token;

    OkHttpClient client = new OkHttpClient();

    okhttp3.Request request;

    if ("GET".equals(endpoint.getMethod())) { // GET request
      request = new okhttp3.Request.Builder()
              .url(endpoint.getUrl())
              .addHeader(token.getHeaderName(), token.getValue())
              .build();
    } else if ("DELETE".equals(endpoint.getMethod())) { // DELETE request
      request = new okhttp3.Request.Builder()
              .url(endpoint.getUrl())
              .delete()
              .addHeader("accept", "application/json")
              .addHeader(token.getHeaderName(), token.getValue())
              .build();
    } else { // All other methods
      request = new okhttp3.Request.Builder()
              .url(endpoint.getUrl())
              .method(endpoint.getMethod(), endpoint.getBody())
              .addHeader("accept", "application/json")
              .addHeader(token.getHeaderName(), token.getValue())
              .build();
    }


    Response response = client.newCall(request).execute();
    this.response = response;
    response.close();
  }

  private synchronized int createId() {
    return ++idCounter;
  }

  public int getId() {
    return id;
  }

  public Endpoint getEndpoint() {
    return endpoint;
  }

  public Token getToken() {
    return token;
  }

  public Response getResponse() {
    return response;
  }
}
