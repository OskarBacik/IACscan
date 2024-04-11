package test;

import example.endpoints.Endpoint;
import example.uda.Uda;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UdaTest {

  private Uda uda;
  private Endpoint endpoint;
  private List<Boolean> policy;

  @Before
  public void setUp() {
    endpoint = new Endpoint("http://example.com", "GET", "", "application/json", Arrays.asList("Header1: Value1"));
    policy = Arrays.asList(true, false, true);
    uda = new Uda(endpoint, policy);
  }

  @Test
  public void testConstructor() {
    assertNotNull(uda);
  }

  @Test
  public void testGetters() {
    assertEquals(endpoint.getId(), uda.getId());
    assertEquals(endpoint, uda.getEndpoint());
    assertEquals(policy, uda.getPolicy());
  }

  @Test
  public void testSetters() {
    Endpoint newEndpoint = new Endpoint("http://example.com", "POST", "", "application/json", Arrays.asList("Header1: Value1"));
    List<Boolean> newPolicy = Arrays.asList(false, true, false);
    uda.setEndpoint(newEndpoint);
    uda.setPolicy(newPolicy);
    assertEquals(newEndpoint, uda.getEndpoint());
    assertEquals(newPolicy, uda.getPolicy());
  }
}
