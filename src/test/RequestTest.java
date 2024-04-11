package test;

import example.endpoints.Endpoint;
import example.requests.Request;
import example.tokens.Token;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RequestTest {

  private MockWebServer server;
  private Token token;
  private String url;

  @Before
  public void setUp() throws IOException {
    server = new MockWebServer();
    server.start();
    token = new Token("user", "JWT", "token123");
    url = server.url("/").toString();

  }

  @After
  public void shutDown() throws IOException {
    server.shutdown();
  }

  @Test
  public void testGetRequest() throws IOException {
    Endpoint endpoint = new Endpoint(url, "GET", "", "application/json", new ArrayList<>());
    server.enqueue(new MockResponse().setResponseCode(200).setBody("Mock response"));
    Request request = new Request(endpoint, token);

    assertEquals(endpoint, request.getEndpoint());
    assertEquals(token, request.getToken());
    assertNotNull(request.getRequest());
    assertNotNull(request.getResponse());
    assertEquals("Mock response", request.getResponseBodyString());
    assertEquals(13, request.getContentLength());
    assertEquals(200, request.getResponse().code());
  }

  @Test
  public void testPostRequest() throws IOException {
    Endpoint endpoint = new Endpoint(url, "POST", "{\"key\":\"value\"}", "application/json", new ArrayList<>());
    server.enqueue(new MockResponse().setResponseCode(201).setBody("Created"));
    Request request = new Request(endpoint, token);

    assertEquals(endpoint, request.getEndpoint());
    assertEquals(token, request.getToken());
    assertNotNull(request.getRequest());
    assertNotNull(request.getResponse());
    assertEquals("Created", request.getResponseBodyString());
    assertEquals(7, request.getContentLength());
    assertEquals(201, request.getResponse().code());
  }
}
