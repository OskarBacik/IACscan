package example.requests;

import java.util.ArrayList;
import java.util.List;

public class RequestManager {

  private List<Request> requests;

  public RequestManager(){
    this.requests = new ArrayList<>();
  }

  public List<Request> getRequests() {
    return requests;
  }

  public void addRequest(Request request) {
    requests.add(request);
  }
}
