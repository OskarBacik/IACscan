package test;

import example.endpoints.Endpoint;
import org.junit.Before;
import org.junit.Test;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EndpointTest {

  private Endpoint endpoint;

  @Before
  public void setUp() {
    List<String> headers = new ArrayList<>();
    headers.add("Header1: Value1");
    headers.add("Header2: Value2");
    String url = "http://example.com/api";
    String method = "GET";
    String bodyContent = "{}";
    String contentType = "application/json; charset=utf-8";
    endpoint = new Endpoint(url, method, bodyContent, contentType, headers);
  }

  @Test
  public void testConstructor() {
    assertNotNull(endpoint);
  }

  @Test
  public void testGetters() {
    assertEquals("http://example.com/api", endpoint.getUrl());
    assertEquals("GET", endpoint.getMethod());
    assertEquals("{}", endpoint.getBodyContent());
    assertEquals("application/json; charset=utf-8", endpoint.getContentType());
    List<String> headers = endpoint.getHeaders();
    assertEquals(2, headers.size());
    assertEquals("Header1: Value1", headers.get(0));
    assertEquals("Header2: Value2", headers.get(1));
  }

  @Test
  public void testRequestBody() {
    RequestBody requestBody = endpoint.getBody();
    assertNotNull(requestBody);
    assertEquals(MediaType.parse("application/json; charset=utf-8"), requestBody.contentType());
  }

  @Test
  public void testEditEndpoint() {
    endpoint.editEndpoint("http://example.com/api/v2", "POST", "{\"key\":\"value\"}", "application/json; charset=utf-8", new ArrayList<>());
    assertEquals("http://example.com/api/v2", endpoint.getUrl());
    assertEquals("POST", endpoint.getMethod());
    assertEquals("{\"key\":\"value\"}", endpoint.getBodyContent());
    assertEquals("application/json; charset=utf-8", endpoint.getContentType());
    assertEquals(0, endpoint.getHeaders().size());
  }
}