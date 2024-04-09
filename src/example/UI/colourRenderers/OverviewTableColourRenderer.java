package example.UI.colourRenderers;

import example.endpoints.Endpoint;
import example.endpoints.EndpointManager;
import example.requests.Request;
import example.requests.RequestManager;
import example.uda.Uda;
import example.uda.UdaManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Custom colour renderer for Overview Table
public class OverviewTableColourRenderer extends DefaultTableCellRenderer {

  private EndpointManager endpointManager;
  private RequestManager requestManager;
  private UdaManager udaManager;

  public OverviewTableColourRenderer(EndpointManager endpointManager, RequestManager requestManager, UdaManager udaManager) {
    this.endpointManager = endpointManager;
    this.requestManager = requestManager;
    this.udaManager = udaManager;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                 int row, int column) {
    Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    int responseCode;
    List<Integer> greenList = Arrays.asList(200, 201, 202, 204);
    List<Integer> orangeList = Arrays.asList(300, 301, 302, 304, 307, 308);
    List<Integer> forbiddenList = Arrays.asList(401, 403);

    // colour code each cell based on the response code
    Object cell = table.getValueAt(row, column);

    int endpointId = Integer.parseInt((String) table.getValueAt(row, 0));


    // get endpoint, request and UDA objects for selected cell
    Endpoint endpoint = endpointManager.getEndpointById(endpointId);
    Request request = requestManager.getRequestsByEndpointId(endpoint.getId()).get(column - 2);
    Uda uda = udaManager.getUdaById(endpointId);
    boolean udaPolicy = uda.getPolicy().get(column - 2);

    // get content length of permitted request
    int permittedContentLength = -1;
    for (int i = 0; i < uda.getPolicy().size(); i++) {
      if (uda.getPolicy().get(i)) { // if UDA is set as permitted
        permittedContentLength = requestManager.getRequestsByEndpointId(endpoint.getId()).get(i).getContentLength();
        break;
      }
    }

    // parse response code
    try {
      responseCode = Integer.parseInt((String) cell);
    } catch (NumberFormatException exception) {
      responseCode = 0;
    }

    // Set to green if UDA is set as permitted
    if (udaPolicy) {
      cellComponent.setBackground(Color.GREEN);
    }
    else { // if UDA is set as forbidden
      if (forbiddenList.contains(responseCode)) { // if response code is forbidden
        cellComponent.setBackground(Color.GREEN);
      }
      else {
        if (request.getContentLength() == permittedContentLength) { // if content lengths are the same
          cellComponent.setBackground(Color.RED);
        }
        else {
          cellComponent.setBackground(Color.ORANGE); // if content lengths are different
        }
      }
    }

    return cellComponent;





    /* OLD CODE

    if (greenList.contains(responseCode)) {
      cellComponent.setBackground(Color.GREEN);
    } else if (redList.contains(responseCode)) {
      cellComponent.setBackground(Color.RED);
    } else if (orangeList.contains(responseCode)) {
      cellComponent.setBackground(Color.ORANGE);
    } else if (responseCode == 404) {
      cellComponent.setBackground(Color.GRAY);
    } else {
      cellComponent.setBackground(table.getBackground());
    }

    return cellComponent;
    */

  }

}
