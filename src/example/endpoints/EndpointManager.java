package example.endpoints;

import example.tokens.Token;

import java.util.ArrayList;
import java.util.List;

public class EndpointManager {
  private List<Endpoint> endpoints;

  private List<Endpoint> detectionEndpoints;

  public EndpointManager() {
    this.endpoints = new ArrayList<>();
    this.detectionEndpoints = new ArrayList<>();
  }

  public List<Endpoint> getEndpoints() {
    return endpoints;
  }

  public void addEndpoint(Endpoint endpoint) {
    endpoints.add(endpoint);
  }

  public List<Endpoint> getDetectionEndpoints() {
    return detectionEndpoints;
  }

  public void addDetectionEndpoint(Endpoint endpoint) {
    detectionEndpoints.add(endpoint);
  }


  public String[][] toStringArray() {
    String[][] result = new String[endpoints.size()][4];

    for (int i = 0; i < endpoints.size(); i++) {
      Endpoint endpoint = endpoints.get(i);
      result[i][0] = String.valueOf(endpoint.getId());
      result[i][1] = endpoint.getUrl();
      result[i][2] = endpoint.getMethod();
      result[i][3] = endpoint.getBodyContent();
    }

    return result;
  }

  public void deleteById(int id) {
    for (Endpoint endpoint : endpoints) {
      if (endpoint.getId() == id) {
        endpoints.remove(endpoint);
        return;
      }
    }
  }
}
