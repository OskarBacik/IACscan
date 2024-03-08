package example.UI;

import example.endpoints.Endpoint;
import example.endpoints.EndpointManager;
import example.requests.Request;
import example.requests.RequestManager;
import example.tokens.Token;
import example.tokens.TokenManager;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
  private JPanel DetectionPanel;
  private JButton detectionStartButton;
  private JScrollPane ViewDetectionPanel;
  private JTable viewDetectionTable;
  private JButton detectionAddButton;
  private JTextArea addEndpointsHeaders;
  private JLabel addEndpointsLabel3;
  private JTabbedPane AddEndpointsTabs;
  private JPanel AddEndpointsCurlPanel;
  private JLabel addEndpointsCurlLabel;
  private JTextArea addEndpointsCurlText;
  private JButton addEndpointsCurlButton;
  private JScrollPane addEndpointsCurlScroll;
  private JComboBox detectionTokenBox;
  private JLabel detectionTokenLabel;
  private JButton detectionTokenRefresh;

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

    /*
    // Example data - TODO: Remove
    endpointManager.addEndpoint(new Endpoint("https://google.com", "GET",
            "", "application/json"));
    endpointManager.addEndpoint(new Endpoint("https://google.com/doesntexist", "GET",
            "", "application/json"));
    endpointManager.addEndpoint(new Endpoint("https://mail.google.com/mail/u/0/#inbox", "GET",
            "", "application/json"));
     */
    endpointManager.addEndpoint(new Endpoint("https://api.sandbox.billit.be/v1/documents/31571", "GET", "", "application/json"));

    // Initialise table
    EndpointsTableModel endpointsTableModel = new EndpointsTableModel(this);
    viewEndpointsTable.setModel(endpointsTableModel);
    addEndpointsBodyPanel.setVisible(false);

    // hide body panel if method is GET or DELETE
    addEndpointsMethod.addActionListener(e -> {
      String selectedMethod = (String) addEndpointsMethod.getSelectedItem();
      addEndpointsBodyPanel.setVisible(!"GET".equals(selectedMethod) && !"DELETE".equals(selectedMethod));
    });

    // Add endpoint button TODO: add headers
    addEndpointsButton.addActionListener(e -> {
      Endpoint newEndpoint = new Endpoint(addEndpointsUrl.getText(), (String) addEndpointsMethod.getSelectedItem(),
              addEndpointsPost.getText(), addEndpointsContentType.getText());
      endpointManager.addEndpoint(newEndpoint);
      addEndpointsUrl.setText("");
      endpointsTableModel.setDataVector(endpointManager.toStringArray(), new String[]{"ID", "URL", "Method", "Body"});
      endpointsTableModel.fireTableDataChanged();
    });

    // Add endpoint curl button
    addEndpointsCurlButton.addActionListener(e -> {
      String curlCommand = addEndpointsCurlText.getText(); // TODO: Finish
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

    /*
    // Unauthenticated example token
    tokenManager.addToken(new Token("Unauthenticated", "", ""));
    // Example data - TODO: Remove
    tokenManager.addToken(new Token("admin", "Jwt", "token1"));
    tokenManager.addToken(new Token("user", "Jwt", "token2"));*/
    tokenManager.addToken(new Token("me", "Apikey", "7108397b-6055-497f-970c-b3168387a27c"));

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
      viewEvaluateTable.clearSelection();
      viewOverviewTable.clearSelection();
      try {
        sendRequests(requestManager, endpointManager, tokenManager, evaluateBar);
        evaluateTableModel.setDataVector(requestManager.toStringArrayEvaluate(), new String[]{"ID", "Method", "URL", "Token", "Response code"});
        evaluateTableModel.fireTableDataChanged();
          newOverviewTable();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    // Display row selection in evaluate info panel
    viewEvaluateTable.getSelectionModel().addListSelectionListener(e -> {
      if (viewEvaluateTable.getSelectedRow() > -1) { // ignore if selected row is < 0
        int selectedId = Integer.parseInt((String) viewEvaluateTable.getValueAt(viewEvaluateTable.getSelectedRow(), 0));
        Request selectedRequest = requestManager.getById(selectedId);

        // display custom request formatting in info panel
        evaluateRequestText.setText(selectedRequest.getCustomRequestText());
        evaluateResponseText.setText(selectedRequest.getCustomResponseText());
      }
    });


    // OVERVIEW

    // Initialise table
    String[] overviewColumnNames = getOverviewColumnNames();
    OverviewTableModel overviewTableModel = new OverviewTableModel(this, overviewColumnNames);
    viewOverviewTable.setModel(overviewTableModel);

    // Display cell selection in overview info panel
    viewOverviewTable.getSelectionModel().addListSelectionListener(e -> {
      if (viewOverviewTable.getSelectedColumn() > 1) { // ignore selection of endpoint ID and URL columns
        Integer selectedEndpointId = Integer.parseInt((String) viewOverviewTable.getValueAt(viewOverviewTable.getSelectedRow(), 0));
        int selectedColumn = viewOverviewTable.getSelectedColumn();
        Request selectedRequest = requestManager.getRequestsByEndpointId(selectedEndpointId).get(selectedColumn-2);

        // display custom request formatting in info panel
        overviewRequestText.setText(selectedRequest.getCustomRequestText());
        overviewResponseText.setText(selectedRequest.getCustomResponseText());
      }
    });


    // DETECTION

    // reusing evaluate table model due to same layout
    EvaluateTableModel detectionTableModel = new EvaluateTableModel(this);
    viewDetectionTable.setModel(detectionTableModel);

    // add tokens to token selection box
    detectionTokenRefresh.addActionListener(e -> {
      detectionTokenBox.removeAllItems();
      for(Token token: tokenManager.getTokenList()) {
        detectionTokenBox.addItem(token.getLabel());
      }
    });

    // start TODO: add colour renderer to 404 and 405
    detectionStartButton.addActionListener(e -> {
      try {
        detectMethods();
        detectionTableModel.setDataVector(requestManager.toStringArrayDetection(), new String[]{"ID", "Method", "URL", "Token", "Response code"});
        detectionTableModel.fireTableDataChanged();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    // add endpoint TODO: finish
    detectionAddButton.addActionListener(e -> {
      int selectedDetectionId = (int) viewDetectionTable.getValueAt(viewDetectionTable.getSelectedRow(),0);
    });

    AddEndpointsPanel.addComponentListener(new ComponentAdapter() {
    });
  }

  public static void main(String[] args) {
    new Main();
  }

  // Send requests to each endpoint with each token and collect responses
  public void sendRequests(RequestManager requestManager, EndpointManager endpointManager, TokenManager tokenManager,
                           JProgressBar evaluateBar) throws IOException {

    // clear request manager
    if(requestManager.getRequests().size() > 0) {
      requestManager.getRequests().get(0).resetId();
      requestManager.clearList();
    }

    // create progress bar
    int totalEndpoints = endpointManager.getEndpoints().size()*tokenManager.getTokenList().size();
    int progress = 0;
    evaluateBar.setMaximum(totalEndpoints); // TODO: fix progress bar

    // send a request to each endpoint with each token
    for(Endpoint endpoint: endpointManager.getEndpoints()) {
      for(Token token: tokenManager.getTokenList()) {
        requestManager.addRequest(new Request(endpoint,token));
        progress += 1;
        evaluateBar.setValue(progress);
        evaluateBar.setStringPainted(true);
        evaluateBar.repaint();
      }
    }
  }

  // Send requests to each endpoint with new HTTP methods
  public void detectMethods() throws IOException {
    List<Endpoint> endpointList = endpointManager.getEndpoints();
    List<String> methods = Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");

    // check if token is selected, return if none selected
    if(detectionTokenBox.getSelectedIndex() == -1) {
      return;
    }
    Token selectedToken = tokenManager.getTokenList().get(detectionTokenBox.getSelectedIndex());

    // clear detection manager
    if(requestManager.getDetectionRequests().size() > 0){
      requestManager.clearDetectionList();
    }

    // check for the existence of new methods for each endpoint
    for(Endpoint endpoint: endpointManager.getEndpoints()) {
      for(String method: methods){

        // ignore if method is the same as specified in Endpoint object
        if(!Objects.equals(endpoint.getMethod(), method)) {

          // create a new detection endpoint with different method
          Endpoint newEndpoint = new Endpoint(endpoint.getUrl(), method, endpoint.getBodyContent(), endpoint.getContentType());
          endpointManager.addDetectionEndpoint(newEndpoint);

          // create and send request
          requestManager.addDetectionRequest(new Request(newEndpoint, selectedToken));
        }
      }
    }
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

  // Create or refresh table with new possible rows/columns
  public void newOverviewTable() {
    String[] overviewColumnNames = getOverviewColumnNames();
    OverviewTableModel overviewTableModel = new OverviewTableModel(this, overviewColumnNames);
    viewOverviewTable.setModel(overviewTableModel);
    // refresh table content
    overviewTableModel.setDataVector(requestManager.toStringArrayOverview(endpointManager, tokenManager), getOverviewColumnNames());
    overviewTableModel.fireTableDataChanged();
    // refresh cell colour for all columns except ID and URL columns
    for (int columnIndex = 0; columnIndex < viewOverviewTable.getColumnCount(); columnIndex++) {
      if(columnIndex>1) {
        viewOverviewTable.getColumnModel().getColumn(columnIndex).setCellRenderer(new OverviewTableColourRenderer());
      }
    }
    viewOverviewTable.repaint();
  }


}
