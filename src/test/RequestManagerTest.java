package test;

import example.endpoints.Endpoint;
import example.requests.Request;
import example.requests.RequestManager;
import example.tokens.Token;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RequestManagerTest {

  private RequestManager requestManager;
  private Endpoint endpoint1;
  private Endpoint endpoint2;
  private Token token1;
  private Token token2;

  @Before
  public void setUp() {
    requestManager = new RequestManager();
    endpoint1 = new Endpoint("http://example.com/api1", "GET", "", "application/json", new ArrayList<>());
    endpoint2 = new Endpoint("http://example.com/api2", "POST", "{\"key\":\"value\"}", "application/json", new ArrayList<>());
    token1 = new Token("admin", "JWT", "token1");
    token2 = new Token("user", "Bearer", "token2");
  }

  @Test
  public void testGetRequests() throws IOException {
    Request request1 = new Request(endpoint1, token1);
    Request request2 = new Request(endpoint2, token2);

    requestManager.addRequest(request1);
    requestManager.addRequest(request2);

    List<Request> requests = requestManager.getRequests();

    assertEquals(2, requests.size());
    assertTrue(requests.contains(request1));
    assertTrue(requests.contains(request2));
  }

  @Test
  public void testGetDetectionRequests() throws IOException {
    Request request1 = new Request(endpoint1, token1);
    Request request2 = new Request(endpoint2, token2);

    requestManager.addDetectionRequest(request1);
    requestManager.addDetectionRequest(request2);

    List<Request> detectionRequests = requestManager.getDetectionRequests();

    assertEquals(2, detectionRequests.size());
    assertTrue(detectionRequests.contains(request1));
    assertTrue(detectionRequests.contains(request2));
  }

  @Test
  public void testClearList() throws IOException {
    Request request1 = new Request(endpoint1, token1);
    Request request2 = new Request(endpoint2, token2);

    requestManager.addRequest(request1);
    requestManager.addRequest(request2);

    assertFalse(requestManager.getRequests().isEmpty());
    requestManager.clearList();
    assertTrue(requestManager.getRequests().isEmpty());
  }

  @Test
  public void testClearDetectionList() throws IOException {
    Request request1 = new Request(endpoint1, token1);
    Request request2 = new Request(endpoint2, token2);

    requestManager.addDetectionRequest(request1);
    requestManager.addDetectionRequest(request2);

    assertFalse(requestManager.getDetectionRequests().isEmpty());
    requestManager.clearDetectionList();
    assertTrue(requestManager.getDetectionRequests().isEmpty());
  }

  @Test
  public void testGetRequestsById() throws IOException {
    Request request1 = new Request(endpoint1, token1);
    Request request2 = new Request(endpoint2, token2);

    requestManager.addRequest(request1);
    requestManager.addRequest(request2);

    assertEquals(request1, requestManager.getById(request1.getId()));
    assertEquals(request2, requestManager.getById(request2.getId()));
  }

  @Test
  public void TestGetDetectionRequestsById() throws IOException {
    Request request1 = new Request(endpoint1, token1);
    Request request2 = new Request(endpoint2, token2);

    requestManager.addDetectionRequest(request1);
    requestManager.addDetectionRequest(request2);

    assertEquals(request1, requestManager.getDetectionById(request1.getId()));
    assertEquals(request2, requestManager.getDetectionById(request2.getId()));
  }

  @Test
  public void testGetRequestsByEndpointId() throws IOException {
    Request request1 = new Request(endpoint1, token1);
    Request request2 = new Request(endpoint2, token2);
    Request request3 = new Request(endpoint1, token1);

    requestManager.addRequest(request1);
    requestManager.addRequest(request2);
    requestManager.addRequest(request3);

    List<Request> requestsForEndpoint1 = requestManager.getRequestsByEndpointId(endpoint1.getId());
    assertEquals(2, requestsForEndpoint1.size());
    assertTrue(requestsForEndpoint1.contains(request1));
    assertTrue(requestsForEndpoint1.contains(request3));

    List<Request> requestsForEndpoint2 = requestManager.getRequestsByEndpointId(endpoint2.getId());
    assertEquals(1, requestsForEndpoint2.size());
    assertTrue(requestsForEndpoint2.contains(request2));
  }

}
