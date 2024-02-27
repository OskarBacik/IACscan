package example.endpoints;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Endpoint {

  private static int idCounter = 0;
  private int id;

  private String url;
  private String method;
  private RequestBody body;
  private String bodyContent;
  private String contentType;

  public Endpoint(String url, String method, String bodyContent, String contentType) {
    this.id = createId();
    this.url = url;
    this.method = method;
    this.bodyContent = bodyContent;
    this.contentType = contentType;
    this.body = RequestBody.create(bodyContent, MediaType.parse(contentType));
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

  public void setUrl(String url) {
    this.url = url;
  }

  public void setMethod(String method) {
    this.method = method;
  }
}
