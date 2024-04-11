package example.uda;

import example.endpoints.Endpoint;

import java.util.List;

public class Uda {

  // ID is the same as the endpoint ID
  private int id;
  private Endpoint endpoint;
  private List<Boolean> policy;

  public Uda(Endpoint endpoint, List<Boolean> policy) {
    this.id = endpoint.getId();
    this.endpoint = endpoint;
    this.policy = policy;
  }

  public int getId() {
    return id;
  }

  public Endpoint getEndpoint() {
    return endpoint;
  }

  public List<Boolean> getPolicy() {
    return policy;
  }

  public void setEndpoint(Endpoint endpoint) {
    this.endpoint = endpoint;
  }

  public void setPolicy(List<Boolean> policy) {
    this.policy = policy;
  }
}
