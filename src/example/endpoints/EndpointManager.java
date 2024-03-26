package example.endpoints;

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

  public Endpoint getEndpointById(int id) {
    List<Endpoint> endpointList = getEndpoints();
    for(Endpoint endpoint: endpointList) {
      if(endpoint.getId() == id) {
        return endpoint;
      }
    }
    return null;
  }

  public Endpoint getDetectionById(int id) {
    List<Endpoint> endpointList = getDetectionEndpoints();
    for(Endpoint endpoint: endpointList) {
      if(endpoint.getId() == id) {
        return endpoint;
      }
    }
    return null;
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

  // Delete endpoint from detectionEndpoints and add to endpoints
  public void moveDetectionToMain(int id){
    for (Endpoint endpoint : detectionEndpoints) {
      if (endpoint.getId() == id) {
        detectionEndpoints.remove(endpoint);
        endpoints.add(endpoint);
        return;
      }
    }
  }
}
