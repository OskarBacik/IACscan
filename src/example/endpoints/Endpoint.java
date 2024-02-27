package example.endpoints;

import okhttp3.RequestBody;

public class Endpoint {

  private String url;
  private String method;
  private RequestBody body;

  public Endpoint(String url, String method, RequestBody body) {
    this.url = url;
    this.method = method;
    this.body = body;
  }

  public String getUrl() {
    return url;
  }

  public String getMethod() {
    return method;
  }

  public RequestBody getBody() {
    return body;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setMethod(String method) {
    this.method = method;
  }
}
