package test;

import example.endpoints.Endpoint;
import example.uda.Uda;
import example.uda.UdaManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UdaManagerTest {

  private UdaManager udaManager;
  Endpoint endpoint1;
  Endpoint endpoint2;
  Uda uda1;
  Uda uda2;

  @Before
  public void setUp() {
    udaManager = new UdaManager();
    endpoint1 = new Endpoint("http://example.com/api1", "GET","", "application/json", new ArrayList<>());
    endpoint2 = new Endpoint("http://example.com/api2", "POST","{\"key\":\"value\"}", "application/json", new ArrayList<>());
    uda1 = new Uda(endpoint1, new ArrayList<>());
    uda2 = new Uda(endpoint2, new ArrayList<>());
  }

  @Test
  public void testGetUdas() {
    udaManager.addUda(uda1);
    udaManager.addUda(uda2);

    List<Uda> udas = udaManager.getUdaList();

    assertEquals(2, udas.size());
    assertTrue(udas.contains(uda1));
    assertTrue(udas.contains(uda2));
  }

  @Test
  public void testGetUdaById() {
    udaManager.addUda(uda1);
    udaManager.addUda(uda2);

    assertEquals(uda1, udaManager.getUdaById(uda1.getId()));
    assertEquals(endpoint1, udaManager.getUdaById(uda1.getId()).getEndpoint());
    assertEquals(uda2, udaManager.getUdaById(uda2.getId()));
    assertEquals(endpoint2, udaManager.getUdaById(uda2.getId()).getEndpoint());
  }

  @Test
  public void testClearList() {
    udaManager.addUda(uda1);
    udaManager.addUda(uda2);

    assertFalse(udaManager.getUdaList().isEmpty());
    udaManager.clearList();
    assertTrue(udaManager.getUdaList().isEmpty());
  }

}
