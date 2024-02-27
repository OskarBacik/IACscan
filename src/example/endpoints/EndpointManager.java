package example.endpoints;

import java.util.ArrayList;
import java.util.List;

public class EndpointManager {
  private List<Endpoint> endpoints;

  public EndpointManager() {
    this.endpoints = new ArrayList<>();
  }

  public List<Endpoint> getEndpoints() {
    return endpoints;
  }

  public void addEndpoint(Endpoint endpoint) {
    endpoints.add(endpoint);
  }
}
