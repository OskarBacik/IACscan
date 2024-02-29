package example.UI;

import example.endpoints.Endpoint;
import example.endpoints.EndpointManager;
import example.requests.Request;
import example.requests.RequestManager;
import example.tokens.Token;
import example.tokens.TokenManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends JFrame{
  private JPanel MainPanel;
  EndpointManager endpointManager;
  TokenManager tokenManager;
  RequestManager requestManager;
  private JTabbedPane MainTabbedPane;
  private JPanel EndpointsPanel;
  private JPanel TokensPanel;
  private JPanel AddEndpointsPanel;
  private JComboBox addEndpointsMethod;
  private JTextField addEndpointsUrl;
  private JButton addEndpointsButton;
  private JLabel addEndpointsLabel;
  private JPanel AddTokensPanel;
  private JTextField addTokensName;
  private JTextField addTokensHeader;
  private JTextArea addTokensToken;
  private JButton addTokensButton;
  private JTable viewTokensTable;
  private JLabel addTokensLabel1;
  private JLabel addTokensLabel2;
  private JLabel addTokensLabel3;
  private JTextArea addEndpointsPost;
  private JLabel addEndpointsLabel1;
  private JTable viewEndpointsTable;
  private JPanel EvaluatePanel;
  private JButton evaluateButton;
  private JProgressBar evaluateBar;
  private JScrollPane ViewTokensPanel;
  private JButton deleteTokenButton;
  private JScrollPane ViewEndpointsPanel;
  private JLabel addEndpointsLabel2;
  private JTextField addEndpointsContentType;
  private JPanel addEndpointsBodyPanel;
  private JButton deleteEndpointsButton;
  private JScrollPane ViewEvaluatePanel;
  private JTable viewEvaluateTable;
  private JPanel OverviewPanel;
  private JTable viewOverviewTable;
  private JScrollPane ViewOverviewPanel;
  private JPanel DetailsEvaluatePanel;
  private JLabel evaluateRequestLabel;
  private JLabel evaluateResponseLabel;
  private JTextArea evaluateRequestText;
  private JTextArea evaluateResponseText;
  private JButton overviewTableRefreshButton;
  private JScrollPane EvaluateRequestPanel;
  private JScrollPane EvaluateResponsePanel;
  private JPanel OverviewDetailsPanel;
  private JLabel OverviewRequestLabel;
  private JLabel OverviewResponseLabel;
  private JScrollPane OverviewResponsePanel;
  private JScrollPane OverviewRequestPanel;
  private JTextArea overviewRequestText;
  private JTextArea overviewResponseText;
  private JLabel OverviewLabel;

  public Main() {

    // Create logic managers
    endpointManager = new EndpointManager();
    tokenManager = new TokenManager();
    requestManager = new RequestManager();

    // Main frame settings
    setContentPane(MainPanel);
    setTitle("BACscan");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(1920, 800);
    setLocationRelativeTo(null);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setVisible(true);


    // ENDPOINTS

    // Example data - TODO: Remove
    endpointManager.addEndpoint(new Endpoint("https://google.com", "GET",
            "", "application/json"));
    endpointManager.addEndpoint(new Endpoint("https://google.com/doesntexist", "GET",
            "", "application/json"));
    endpointManager.addEndpoint(new Endpoint("https://mail.google.com/mail/u/0/#inbox", "GET",
            "", "application/json"));

    // Initialise table
    EndpointsTableModel endpointsTableModel = new EndpointsTableModel(this);
    viewEndpointsTable.setModel(endpointsTableModel);
    addEndpointsBodyPanel.setVisible(false);

    // hide body panel if method is GET or DELETE
    addEndpointsMethod.addActionListener(e -> {
      String selectedMethod = (String) addEndpointsMethod.getSelectedItem();
      if ("GET".equals(selectedMethod) || "DELETE".equals(selectedMethod)) {
        addEndpointsBodyPanel.setVisible(false);
      } else {
        addEndpointsBodyPanel.setVisible(true);
      }
    });

    // Add endpoint button
    addEndpointsButton.addActionListener(e -> {
      Endpoint newEndpoint = new Endpoint(addEndpointsUrl.getText(), (String) addEndpointsMethod.getSelectedItem(),
              addEndpointsPost.getText(), addEndpointsContentType.getText());
      endpointManager.addEndpoint(newEndpoint);
      addEndpointsUrl.setText("");
      endpointsTableModel.setDataVector(endpointManager.toStringArray(), new String[]{"ID", "URL", "Method", "Body"});
      endpointsTableModel.fireTableDataChanged();
    });

    // Delete endpoint button
    deleteEndpointsButton.addActionListener(e -> {
      if(viewEndpointsTable.getSelectedRow() != -1) { // no row selected = error
        endpointManager.deleteById(Integer.parseInt((String) viewEndpointsTable.getValueAt(viewEndpointsTable.getSelectedRow(),0)));
        endpointsTableModel.setDataVector(endpointManager.toStringArray(), new String[]{"ID", "URL", "Method", "Body"});
        endpointsTableModel.fireTableDataChanged();
      }
    });


    // TOKENS

    // Unauthenticated example token
    tokenManager.addToken(new Token("Unauthenticated", "", ""));
    // Example data - TODO: Remove
    tokenManager.addToken(new Token("admin", "Jwt", "token1"));
    tokenManager.addToken(new Token("user", "Jwt", "token2"));

    // Initialise table
    TokenTableModel tokenTableModel = new TokenTableModel(this);
    viewTokensTable.setModel(tokenTableModel);

    // Add token button
    addTokensButton.addActionListener(e -> {
      Token newToken = new Token(addTokensName.getText(), addTokensHeader.getText(), addTokensToken.getText());
      tokenManager.addToken(newToken);
      addTokensName.setText("");
      addTokensHeader.setText("");
      addTokensToken.setText("");
      tokenTableModel.setDataVector(tokenManager.toStringArray(), new String[]{"ID", "Label", "Header Name", "Value"});
      tokenTableModel.fireTableDataChanged();
    });

    // Delete token button
    deleteTokenButton.addActionListener(e -> {
      if(viewTokensTable.getSelectedRow() != -1) { // no row selected = error
        tokenManager.deleteById(Integer.parseInt((String) viewTokensTable.getValueAt(viewTokensTable.getSelectedRow(),0)));
        tokenTableModel.setDataVector(tokenManager.toStringArray(), new String[]{"ID", "Label", "Header Name", "Value"});
        tokenTableModel.fireTableDataChanged();
      }
    });


    // EVALUATE

    // Initialise table
    EvaluateTableModel evaluateTableModel = new EvaluateTableModel(this);
    viewEvaluateTable.setModel(evaluateTableModel);

    // Start button logic
    evaluateButton.addActionListener(e -> {
      try {
        sendRequests(requestManager, endpointManager, tokenManager, evaluateTableModel, evaluateBar);
        System.out.println("done");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    // Display row selection in evaluate info panel
    viewEvaluateTable.getSelectionModel().addListSelectionListener(e -> {
      Integer selectedId = Integer.parseInt((String) viewEvaluateTable.getValueAt(viewEvaluateTable.getSelectedRow(), 0));
      evaluateRequestText.setText(requestManager.getById(selectedId).getRequest().toString()); // TODO: reformat
      evaluateResponseText.setText(requestManager.getById(selectedId).getResponse().toString()); // TODO: get response body
    });


    // OVERVIEW

    // Initialise table
    String[] overviewColumnNames = getOverviewColumnNames();
    OverviewTableModel overviewTableModel = new OverviewTableModel(this, overviewColumnNames);
    viewOverviewTable.setModel(overviewTableModel);

    // Refresh button logic
    overviewTableRefreshButton.addActionListener(e -> {
      // refresh table content
      requestManager.overviewToStringArray(endpointManager, tokenManager);
      overviewTableModel.setDataVector(requestManager.overviewToStringArray(endpointManager, tokenManager), getOverviewColumnNames());
      overviewTableModel.fireTableDataChanged();
      // refresh cell colour for all columns except ID and URL columns
      for (int columnIndex = 0; columnIndex < viewOverviewTable.getColumnCount(); columnIndex++) {
        if(columnIndex>1) {
          viewOverviewTable.getColumnModel().getColumn(columnIndex).setCellRenderer(new OverviewTableColourRenderer());
        }
      }
      viewOverviewTable.repaint();
    });

    // Display cell selection in overview info panel
    viewOverviewTable.getSelectionModel().addListSelectionListener(e -> {
      Integer selectedEndpointId = Integer.parseInt((String) viewOverviewTable.getValueAt(viewOverviewTable.getSelectedRow(), 0));
      Integer selectedColumn = viewOverviewTable.getSelectedColumn();
      Request selectedRequest = requestManager.getRequestsByEndpointId(selectedEndpointId).get(selectedColumn-2);
      overviewRequestText.setText(selectedRequest.getRequest().toString()); // TODO: reformat
      overviewResponseText.setText(selectedRequest.getResponse().toString()); // TODO: get response body
    });

  }

  public static void main(String[] args) {
    new Main();
  }

  // Send requests to each endpoint with each token and collect responses
  public void sendRequests(RequestManager requestManager, EndpointManager endpointManager, TokenManager tokenManager,
                           EvaluateTableModel evaluateTableModel, JProgressBar evaluateBar) throws IOException {

    // clear request manager
    requestManager.clearList();

    // create progress bar
    Integer totalEndpoints = endpointManager.getEndpoints().size()*tokenManager.getTokenList().size();
    Integer progress = 0;
    evaluateBar.setMaximum(totalEndpoints); // TODO: fix progress bar

    // send a request to each endpoint with each token
    for(Endpoint endpoint: endpointManager.getEndpoints()) {
      for(Token token: tokenManager.getTokenList()) {
        requestManager.addRequest(new Request(endpoint,token));
        evaluateTableModel.setDataVector(requestManager.toStringArray(), new String[]{"ID", "URL", "Token", "Response code"});
        evaluateTableModel.fireTableDataChanged();
        progress += 1;
        evaluateBar.setValue(progress);
        evaluateBar.setStringPainted(true);
        evaluateBar.repaint();
      }
    }
    // for endpoint in endpoints
    // send request with different HTTP method
    // if not in endpoints
    // flag
  }

  // Get column names for Overview table
  public String[] getOverviewColumnNames() {
    List<String> columnNames = new ArrayList<>();
    columnNames.add("Endpoint ID");
    columnNames.add("URL");
    for(Token token: tokenManager.getTokenList()) {
      columnNames.add(token.getLabel());
    }
    return columnNames.toArray(new String[0]);
  }

  // Custom colour renderer for Overview Table
  static class OverviewTableColourRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

      Integer responseCode;
      // colour code each cell based on the response code TODO: add more response codes
      Object cell = table.getValueAt(row,column);
      try {
        responseCode = Integer.parseInt((String) cell);
      } catch (NumberFormatException exception) {
        responseCode = 0;
      }
      if (responseCode == 200) {
        cellComponent.setBackground(Color.GREEN);
      } else if (responseCode == 403) {
        cellComponent.setBackground(Color.RED);
      } else if (responseCode == 404) {
        cellComponent.setBackground(Color.GRAY);
      } else {
        cellComponent.setBackground(table.getBackground());
      }

      return cellComponent;
    }
  }
}
