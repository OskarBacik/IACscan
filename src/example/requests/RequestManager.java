package example.requests;

import example.endpoints.Endpoint;
import example.endpoints.EndpointManager;
import example.tokens.Token;
import example.tokens.TokenManager;

import java.util.ArrayList;
import java.util.Arrays;
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

  // Get all requests for a certain endpoint
  public List<Request> getRequestsByEndpointId(Integer endpointId) {
    List<Request> requestsList = new ArrayList<>();
    List<Request> allRequests = getRequests();
    for (Request request: allRequests) {
      if(request.getEndpoint().getId() == endpointId) {
        requestsList.add(request);
      }
    }
    return requestsList;
  }

  public String[][] overviewToStringArray(EndpointManager endpointManager, TokenManager tokenManager){
    // initialise rows to number of endpoints and columns to ID + URL + a request for each token
    List<Endpoint> allEndpoints = endpointManager.getEndpoints();
    List<Token> allTokens = tokenManager.getTokenList();
    String[][] result = new String[allEndpoints.size()][allTokens.size()+2];

    for(int i = 0; i < allEndpoints.size(); i++) {
      Endpoint currentEndpoint = allEndpoints.get(i); // current endpoint

      // first column = URL
      result[i][0] = String.valueOf(currentEndpoint.getId());
      result[i][1] = currentEndpoint.getUrl();

      // remaining columns correspond to request made to endpoint with each token
      List<Request> currentEndpointRequests = getRequestsByEndpointId(currentEndpoint.getId());
      for(int j = 0; j < currentEndpointRequests.size(); j++) {
        result[i][j+2] = String.valueOf(currentEndpointRequests.get(j).getResponse().code());
      }
    }
    return result;
  }
}
