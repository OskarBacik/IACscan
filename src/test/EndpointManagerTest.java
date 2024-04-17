package test;

import example.endpoints.Endpoint;
import example.endpoints.EndpointManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EndpointManagerTest {

  private EndpointManager endpointManager;

  @Before
  public void setUp() {
    endpointManager = new EndpointManager();
  }

  @Test
  public void testGetEndpoints() {
    Endpoint endpoint1 = new Endpoint("http://example.com/api1", "GET", "", "application/json", new ArrayList<>());
    Endpoint endpoint2 = new Endpoint("http://example.com/api2", "POST", "{\"key\":\"value\"}", "application/json", new ArrayList<>());

    endpointManager.addEndpoint(endpoint1);
    endpointManager.addEndpoint(endpoint2);

    List<Endpoint> endpoints = endpointManager.getEndpoints();

    assertEquals(2, endpoints.size());
    assertTrue(endpoints.contains(endpoint1));
    assertTrue(endpoints.contains(endpoint2));
  }

  @Test
  public void testGetDetectionEndpoints() {
    Endpoint endpoint1 = new Endpoint("http://example.com/api1", "GET", "", "application/json", new ArrayList<>());
    Endpoint endpoint2 = new Endpoint("http://example.com/api2", "POST", "{\"key\":\"value\"}", "application/json", new ArrayList<>());

    endpointManager.addDetectionEndpoint(endpoint1);
    endpointManager.addDetectionEndpoint(endpoint2);

    List<Endpoint> detectionEndpoints = endpointManager.getDetectionEndpoints();

    assertEquals(2, detectionEndpoints.size());
    assertTrue(detectionEndpoints.contains(endpoint1));
    assertTrue(detectionEndpoints.contains(endpoint2));
  }

  @Test
  public void testGetEndpointById() {
    Endpoint endpoint1 = new Endpoint("http://example.com/api1", "GET", "", "application/json", new ArrayList<>());
    Endpoint endpoint2 = new Endpoint("http://example.com/api2", "POST", "{\"key\":\"value\"}", "application/json", new ArrayList<>());

    endpointManager.addEndpoint(endpoint1);
    endpointManager.addEndpoint(endpoint2);

    assertEquals(endpoint1, endpointManager.getEndpointById(endpoint1.getId()));
    assertEquals(endpoint2, endpointManager.getEndpointById(endpoint2.getId()));
  }

  @Test
  public void testGetDetectionById() {
    Endpoint endpoint1 = new Endpoint("http://example.com/api1", "GET", "", "application/json", new ArrayList<>());
    Endpoint endpoint2 = new Endpoint("http://example.com/api2", "POST", "{\"key\":\"value\"}", "application/json", new ArrayList<>());

    endpointManager.addDetectionEndpoint(endpoint1);
    endpointManager.addDetectionEndpoint(endpoint2);

    assertEquals(endpoint1, endpointManager.getDetectionById(endpoint1.getId()));
    assertEquals(endpoint2, endpointManager.getDetectionById(endpoint2.getId()));
  }

  @Test
  public void testDeleteById() {
    Endpoint endpoint1 = new Endpoint("http://example.com/api1", "GET", "", "application/json", new ArrayList<>());
    Endpoint endpoint2 = new Endpoint("http://example.com/api2", "POST", "{\"key\":\"value\"}", "application/json", new ArrayList<>());

    endpointManager.addEndpoint(endpoint1);
    endpointManager.addEndpoint(endpoint2);

    endpointManager.deleteById(endpoint1.getId());
    assertNull(endpointManager.getEndpointById(endpoint1.getId()));
    assertNotNull(endpointManager.getEndpointById(endpoint2.getId()));
  }

  @Test
  public void testMoveDetectionToMain() {
    Endpoint endpoint1 = new Endpoint("http://example.com/api1", "GET", "", "application/json", new ArrayList<>());
    Endpoint endpoint2 = new Endpoint("http://example.com/api2", "POST", "{\"key\":\"value\"}", "application/json", new ArrayList<>());

    endpointManager.addDetectionEndpoint(endpoint1);
    endpointManager.addDetectionEndpoint(endpoint2);

    endpointManager.moveDetectionToMain(endpoint1.getId());
    assertNull(endpointManager.getDetectionById(endpoint1.getId()));
    assertNotNull(endpointManager.getEndpointById(endpoint1.getId()));
  }

}
