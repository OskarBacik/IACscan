package example.requests;

import example.endpoints.Endpoint;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;
import example.tokens.Token;

public class Request {
  private Endpoint endpoint;
  private Token token;
  private Response response;

  public Request(Endpoint endpoint, Token token) throws IOException {

    this.endpoint = endpoint;
    this.token = token;

    OkHttpClient client = new OkHttpClient();

    okhttp3.Request request = new okhttp3.Request.Builder()
            .url(endpoint.getUrl())
            .method(endpoint.getMethod(), endpoint.getBody())
            .addHeader("accept", "application/json")
            .build();

    Response response = client.newCall(request).execute();
    this.response = response;

    //test
    assert response.body() != null;
    System.out.println(response.body().string());
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
