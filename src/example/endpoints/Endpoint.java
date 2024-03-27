package example.endpoints;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.List;

public class Endpoint {

  private static int idCounter = 0;
  private int id;
  private String url;
  private String method;
  private RequestBody body;
  private String bodyContent;
  private String contentType;
  private List<String> headers;

  public Endpoint(String url, String method, String bodyContent, String contentType, List<String> headers) {
    this.id = createId();
    this.url = url;
    this.method = method;
    this.bodyContent = bodyContent;
    this.contentType = contentType;
    this.body = RequestBody.create(bodyContent, MediaType.parse(contentType));
    this.headers = headers;
  }

  public void editEndpoint(String url, String method, String bodyContent, String contentType, List<String> headers) {
    this.url = url;
    this.method = method;
    this.bodyContent = bodyContent;
    this.contentType = contentType;
    this.body = RequestBody.create(bodyContent, MediaType.parse(contentType));
    this.headers = headers;
  }

  private synchronized int createId() {
    return ++idCounter;
  }

  public int getId() {
    return id;
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

  public String getBodyContent() {
    return bodyContent;
  }

  public String getContentType() {
    return contentType;
  }

  public List<String> getHeaders() {
    return headers;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setMethod(String method) {
    this.method = method;
  }
}
