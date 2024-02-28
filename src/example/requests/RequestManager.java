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

  public void clearList() {
    this.requests = new ArrayList<>();
  }

  public String[][] toStringArray() {
    String[][] result = new String[requests.size()][4];

    for (int i = 0; i < requests.size(); i++) {
      Request request = requests.get(i);
      result[i][0] = String.valueOf(request.getId());
      result[i][1] = String.valueOf(request.getEndpoint().getUrl());
      result[i][2] = request.getToken().getLabel();
      result[i][3] = String.valueOf(request.getResponse().code());
    }

    return result;
  }

  public Request getById(int id) {
    return requests.get(id-1);
  }
}
