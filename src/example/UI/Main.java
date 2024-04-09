package example.UI;

import example.UI.colourRenderers.DetectionTableColourRenderer;
import example.UI.colourRenderers.OverviewTableColourRenderer;
import example.UI.tableModels.*;
import example.endpoints.Endpoint;
import example.endpoints.EndpointManager;
import example.requests.Request;
import example.requests.RequestManager;
import example.tokens.Token;
import example.tokens.TokenManager;
import example.uda.Uda;
import example.uda.UdaManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main extends JFrame{
  private JPanel MainPanel;
  public EndpointManager endpointManager;
  public TokenManager tokenManager;
  public RequestManager requestManager;
  public UdaManager udaManager;
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
  private JButton detectionTokenRefresh;
  private JButton addEndpointsEditButton;
  private JButton addTokensEditButton;
  private JLabel EndpointsErrorLabel;
  private JLabel TokensErrorLabel;
  private JLabel detectionErrorLabel;
  private JPanel UDAPanel;
  private JScrollPane ViewUDAPanel;
  private JTable viewUDATable;
  private JButton refreshUDAButton;
  private JLabel UDAlabel;
  private JCheckBox detectionGet;
  private JCheckBox detectionPost;
  private JCheckBox detectionPatch;
  private JCheckBox detectionDelete;
  private JCheckBox detectionOptions;
  private JCheckBox detectionPut;
  private JPanel DetectionDetailsPanel;
  private JScrollPane DetectionResponsePanel;
  private JScrollPane DetectionRequestPanel;
  private JLabel detectionResponseLabel;
  private JLabel detectionRequestLabel;
  private JTextArea detectionRequestText;
  private JTextArea detectionResponseText;
  private JPanel DetectionMethodPanel;
  private JLabel detectionTokenLabel;

  public Main() {

    // Create logic managers
    endpointManager = new EndpointManager();
    tokenManager = new TokenManager();
    requestManager = new RequestManager();
    udaManager = new UdaManager();


    // Main frame settings
    setContentPane(MainPanel);
    setTitle("IACscan");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(1920, 800);
    setLocationRelativeTo(null);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setVisible(true);


    /* ENDPOINTS */ // TODO: Remove body from table and add tags

    /*
    // Example data - TODO: Remove

    endpointManager.addEndpoint(new Endpoint("https://google.com/doesntexist", "GET",
            "", "application/json"));
    endpointManager.addEndpoint(new Endpoint("https://mail.google.com/mail/u/0/#inbox", "GET",
            "", "application/json"));
     */
    //endpointManager.addEndpoint(new Endpoint("https://google.com", "GET", "", "application/json", new ArrayList<>()));
    endpointManager.addEndpoint(new Endpoint("https://api.sandbox.billit.be/v1/documents", "GET", "",
            "application/json", new ArrayList<>()));

    // Initialise table
    EndpointsTableModel endpointsTableModel = new EndpointsTableModel(this);
    viewEndpointsTable.setModel(endpointsTableModel);
    addEndpointsBodyPanel.setVisible(false);

    // hide body panel if method is GET or DELETE
    addEndpointsMethod.addActionListener(e -> {
      String selectedMethod = (String) addEndpointsMethod.getSelectedItem();
      addEndpointsBodyPanel.setVisible(!"GET".equals(selectedMethod) && !"DELETE".equals(selectedMethod));
    });

    // Add endpoint button
    addEndpointsButton.addActionListener(e -> {
      // parse headers into list
      List<String> headers = new ArrayList<>(Arrays.asList(addEndpointsHeaders.getText().split("\n")));
      System.out.println(headers);

      // create new endpoint
      Endpoint newEndpoint = new Endpoint(addEndpointsUrl.getText(), (String) addEndpointsMethod.getSelectedItem(),
              addEndpointsPost.getText(), addEndpointsContentType.getText(), headers);
      endpointManager.addEndpoint(newEndpoint);

      refreshUdaObjects(); // TODO: temp fix

      // update table and fields
      addEndpointsUrl.setText("");
      addEndpointsPost.setText("");
      endpointsTableModel.setDataVector(endpointManager.toStringArray(), new String[]{"ID", "URL", "Method", "Body"});
      endpointsTableModel.fireTableDataChanged();
      endpointsErrorMessage("Endpoint added successfully", Color.green);
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
      else{
        endpointsErrorMessage("Please select an endpoint to delete", Color.red);
      }
    });

    // view selected endpoint
    viewEndpointsTable.getSelectionModel().addListSelectionListener(e -> {
      // ignore if not selected
      if (viewEndpointsTable.getSelectedRow() > -1){

        // get selected endpoint
        int selectedEndpointId = Integer.parseInt((String) viewEndpointsTable.getValueAt(viewEndpointsTable.getSelectedRow(),0));
        Endpoint selectedEndpoint = endpointManager.getEndpointById(selectedEndpointId);

        // get method from selected endpoint
        List<String> methodsList = Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS");
        for (int i = 0 ; i < methodsList.size(); i++){
          if(Objects.equals(selectedEndpoint.getMethod(), methodsList.get(i))) {
            addEndpointsMethod.setSelectedIndex(i);
          }
        }

        // get remaining fields
        addEndpointsUrl.setText(selectedEndpoint.getUrl());
        addEndpointsPost.setText(selectedEndpoint.getBodyContent());
        addEndpointsContentType.setText(selectedEndpoint.getContentType());
        addEndpointsHeaders.setText(String.join("\n", selectedEndpoint.getHeaders()));
      }
    });

    // edit selected endpoint
    addEndpointsEditButton.addActionListener(e -> {
      if (viewEndpointsTable.getSelectedRow() > -1) { // if endpoint is selected
        // get selected endpoint
        int selectedEndpointId = Integer.parseInt((String) viewEndpointsTable.getValueAt(viewEndpointsTable.getSelectedRow(), 0));
        Endpoint selectedEndpoint = endpointManager.getEndpointById(selectedEndpointId);

        // parse headers into list
        List<String> headers = new ArrayList<>(Arrays.asList(addEndpointsHeaders.getText().split("\n")));

        // overwrite contents
        selectedEndpoint.editEndpoint(addEndpointsUrl.getText(), (String) addEndpointsMethod.getSelectedItem(),
                addEndpointsPost.getText(), addEndpointsContentType.getText(), headers);

        // update table and fields
        endpointsTableModel.setDataVector(endpointManager.toStringArray(), new String[]{"ID", "URL", "Method", "Body"});
        endpointsTableModel.fireTableDataChanged();
        refreshUdaObjects(); // TODO: temp fix
        addEndpointsUrl.setText("");
        addEndpointsPost.setText("");
        endpointsErrorMessage("Endpoint edited successfully", Color.green);
      }
      else{
        endpointsErrorMessage("Please select an endpoint to edit", Color.red);
      }
    });




    /* TOKENS */

    // Unauthenticated example token
    tokenManager.addToken(new Token("Unauthenticated", "Authorization", ""));

    /*
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
      refreshTokenTable(tokenTableModel);
      tokensErrorMessage("Token added successfully", Color.green);
    });

    // Delete token button
    deleteTokenButton.addActionListener(e -> {
      if(viewTokensTable.getSelectedRow() != -1) { // no row selected = error
        tokenManager.deleteById(Integer.parseInt((String) viewTokensTable.getValueAt(viewTokensTable.getSelectedRow(),0)));
        refreshTokenTable(tokenTableModel);
      }
      else{
        tokensErrorMessage("Please select a token to delete", Color.red);
      }
    });

    // view selected token
    viewTokensTable.getSelectionModel().addListSelectionListener(e -> {
      if (viewTokensTable.getSelectedRow() > -1){ // ignore if row not selected
        int selectedTokenId = Integer.parseInt((String) viewTokensTable.getValueAt(viewTokensTable.getSelectedRow(), 0));
        Token selectedToken = tokenManager.getTokenById(selectedTokenId);

        // update visible text areas
        addTokensName.setText(selectedToken.getLabel());
        addTokensHeader.setText(selectedToken.getHeaderName());
        addTokensToken.setText(selectedToken.getValue());
      }
    });

    // edit selected token
    addTokensEditButton.addActionListener(e -> {
      if (viewTokensTable.getSelectedRow() > -1){
        int selectedTokenId = Integer.parseInt((String) viewTokensTable.getValueAt(viewTokensTable.getSelectedRow(), 0));
        Token selectedToken = tokenManager.getTokenById(selectedTokenId);
        selectedToken.editToken(addTokensName.getText(),addTokensHeader.getText(),addTokensToken.getText());
        refreshTokenTable(tokenTableModel);
        tokensErrorMessage("Token edited successfully", Color.green);
      }
      else{
        tokensErrorMessage("Please select a token to edit", Color.red);
      }
    });


    /* UDA */

    // Initialise table
    refreshUdaObjects();
    UdaTableModel udaTableModel = new UdaTableModel(this, getUdaColumnNames());
    viewUDATable.setModel(udaTableModel);

    // Refresh UDA table button
    refreshUDAButton.addActionListener(e -> {
      refreshUdaObjects();
      refreshUdaTable(udaTableModel);
    });

    // Change UDA policy on selection
    viewUDATable.getSelectionModel().addListSelectionListener(e -> {
      if (viewUDATable.getSelectedRow() > -1) { // ignore if selected row is < 0
        int selectedEndpointId = Integer.parseInt((String) viewUDATable.getValueAt(viewUDATable.getSelectedRow(), 0));
        Uda selectedUda = udaManager.getUdaById(selectedEndpointId);

        if (viewUDATable.getSelectedColumn() > 2) {
          int selectedTokenIndex = viewUDATable.getSelectedColumn() - 3;
          selectedUda.getPolicy().set(selectedTokenIndex, !selectedUda.getPolicy().get(selectedTokenIndex));
          refreshUdaTable(udaTableModel);
        }
        viewUDATable.clearSelection();
      }
    });



    /* EVALUATE */

    // Initialise table
    EvaluateTableModel evaluateTableModel = new EvaluateTableModel(this);
    viewEvaluateTable.setModel(evaluateTableModel);

    // Start button logic
    evaluateButton.addActionListener(e -> {
      viewEvaluateTable.clearSelection();
      viewOverviewTable.clearSelection();
      try {
        sendRequests(requestManager, endpointManager, tokenManager, evaluateBar);
        refreshEvaluateTable(evaluateTableModel);
        refreshOverviewTable();
      } catch (IOException ex) {
        System.out.println("exception");
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
        viewEvaluateTable.clearSelection(); // TEST
      }
    });


    /* OVERVIEW */

    // Initialise table
    String[] overviewColumnNames = getOverviewColumnNames();
    OverviewTableModel overviewTableModel = new OverviewTableModel(this, overviewColumnNames);
    viewOverviewTable.setModel(overviewTableModel);

    // Display cell selection in overview info panel
    viewOverviewTable.getSelectionModel().addListSelectionListener(e -> {
      if (viewOverviewTable.getSelectedColumn() > 1) { // ignore selection of endpoint ID and URL columns
        if(viewOverviewTable.getSelectedRow() > -1) {
          Integer selectedEndpointId = Integer.parseInt((String) viewOverviewTable.getValueAt(viewOverviewTable.getSelectedRow(), 0));
          int selectedColumn = viewOverviewTable.getSelectedColumn();
          Request selectedRequest = requestManager.getRequestsByEndpointId(selectedEndpointId).get(selectedColumn-2);

          // display custom request formatting in info panel
          overviewRequestText.setText(selectedRequest.getCustomRequestText());
          overviewResponseText.setText(selectedRequest.getCustomResponseText());
          viewOverviewTable.clearSelection(); // TEST
        }
      }
    });


    /* DETECTION */

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

    // start
    detectionStartButton.addActionListener(e -> {
      try {
        detectMethods();
        refreshDetectionTable(detectionTableModel);
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    // add endpoint
    detectionAddButton.addActionListener(e -> {
      if(viewDetectionTable.getSelectedRow() != -1) {
        // get endpoint and move to endpoints list
        int selectedDetectionId = Integer.parseInt((String) viewDetectionTable.getValueAt(viewDetectionTable.getSelectedRow(),0));
        Request selectedRequest = requestManager.getDetectionById(selectedDetectionId);
        if(selectedRequest != null) {
          endpointManager.moveDetectionToMain(selectedRequest.getEndpoint().getId());
        }

        // refresh tables
        endpointsTableModel.setDataVector(endpointManager.toStringArray(), new String[]{"ID", "URL", "Method", "Body"});
        endpointsTableModel.fireTableDataChanged();
        refreshDetectionTable(detectionTableModel);
        refreshUdaObjects(); // TODO: temp fix
        detectionErrorMessage("Endpoint added successfully", Color.green);
      }
      else{
        detectionErrorMessage("Please select an endpoint to add", Color.red);
      }
    });

    // Display row selection in evaluate info panel
    viewDetectionTable.getSelectionModel().addListSelectionListener(e -> {
      if (viewDetectionTable.getSelectedRow() > -1) { // ignore if selected row is < 0
        int selectedId = Integer.parseInt((String) viewDetectionTable.getValueAt(viewDetectionTable.getSelectedRow(), 0));
        Request selectedRequest = requestManager.getDetectionById(selectedId);

        // display custom request formatting in info panel
        detectionRequestText.setText(selectedRequest.getCustomRequestText());
        detectionResponseText.setText(selectedRequest.getCustomResponseText());
      }
    });
  }

  public static void main(String[] args) {
    new Main();
  }

  /* Request logic */

  // Send requests to each endpoint with each token and collect responses
  public void sendRequests(RequestManager requestManager, EndpointManager endpointManager, TokenManager tokenManager,
                           JProgressBar evaluateBar) throws IOException {

    // clear request manager
    if(requestManager.getRequests().size() > 0) {
      requestManager.getRequests().get(0).resetId();
      requestManager.clearList();
    }

    // send a request to each endpoint with each token
    for(Endpoint endpoint: endpointManager.getEndpoints()) {
      for(Token token: tokenManager.getTokenList()) {
        requestManager.addRequest(new Request(endpoint,token));
      }
    }
  }

  // Send requests to each endpoint with new HTTP methods
  public void detectMethods() throws IOException {

    // Pass selected methods into arraylist
    List<String> methods = new ArrayList<>();
    if(detectionGet.isSelected()) {
      methods.add("GET");
    }
    if(detectionPost.isSelected()) {
      methods.add("POST");
    }
    if(detectionPut.isSelected()) {
      methods.add("PUT");
    }
    if(detectionPatch.isSelected()) {
      methods.add("PATCH");
    }
    if(detectionDelete.isSelected()) {
      methods.add("DELETE");
    }
    if(detectionOptions.isSelected()) {
      methods.add("OPTIONS");
    }

    // check if token is selected, return if none selected
    if(detectionTokenBox.getSelectedIndex() == -1) {
      detectionErrorMessage("Please select a token", Color.red);
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
          Endpoint newEndpoint = new Endpoint(endpoint.getUrl(), method, endpoint.getBodyContent(),
                  endpoint.getContentType(), endpoint.getHeaders());
          endpointManager.addDetectionEndpoint(newEndpoint);

          // create and send request
          requestManager.addDetectionRequest(new Request(newEndpoint, selectedToken));
        }
      }
    }
  }

  /* Get column names for tables */

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

  // Get column names for UDA table
  public String[] getUdaColumnNames() {
    List<String> columnNames = new ArrayList<>();
    columnNames.add("Endpoint ID");
    columnNames.add("URL");
    columnNames.add("Method");
    for(Token token: tokenManager.getTokenList()) {
      columnNames.add(token.getLabel());
    }
    return columnNames.toArray(new String[0]);
  }

  // Instantiate Uda objects
  public void refreshUdaObjects(){
    udaManager.clearList();
    for (Endpoint endpoint: endpointManager.getEndpoints()) {
      List<Boolean> policy = new ArrayList<>();
      for (Token token: tokenManager.getTokenList()) {
        policy.add(false);
      }
      Uda uda = new Uda(endpoint, policy);
      udaManager.addUda(uda);
    }
  }

  /* Refresh tables */

  // Create or refresh table with new possible rows/columns
  public void refreshOverviewTable() {
    String[] overviewColumnNames = getOverviewColumnNames();
    OverviewTableModel overviewTableModel = new OverviewTableModel(this, overviewColumnNames);
    viewOverviewTable.setModel(overviewTableModel);
    // refresh table content
    overviewTableModel.setDataVector(requestManager.toStringArrayOverview(endpointManager, tokenManager), getOverviewColumnNames());
    overviewTableModel.fireTableDataChanged();
    // refresh cell colour for all columns except ID and URL columns
    for (int columnIndex = 0; columnIndex < viewOverviewTable.getColumnCount(); columnIndex++) {
      if(columnIndex>1) {
        viewOverviewTable.getColumnModel().getColumn(columnIndex).setCellRenderer(new OverviewTableColourRenderer(
                endpointManager, requestManager, udaManager));
      }
    }
    viewOverviewTable.repaint();
  }

  // Refresh detection table
  public void refreshDetectionTable(EvaluateTableModel detectionTableModel) {
    // refresh table
    detectionTableModel.setDataVector(requestManager.toStringArrayDetection(), new String[]{"ID", "Method", "URL", "Token", "Response code"});
    detectionTableModel.fireTableDataChanged();
    // set cell colour for response code column
    for(int columnIndex = 0; columnIndex < viewDetectionTable.getColumnCount(); columnIndex++) {
      if(columnIndex>3) {
        viewDetectionTable.getColumnModel().getColumn(columnIndex).setCellRenderer(new DetectionTableColourRenderer());
      }
    }
    viewDetectionTable.repaint();
  }

  // Refresh token table
  public void refreshTokenTable(TokenTableModel tokenTableModel) {
    tokenTableModel.setDataVector(tokenManager.toStringArray(), new String[]{"ID", "Label", "Header Name", "Value"});
    tokenTableModel.fireTableDataChanged();
  }

  // Refresh evaluate table
  public void refreshEvaluateTable(EvaluateTableModel evaluateTableModel) {
    evaluateTableModel.setDataVector(requestManager.toStringArrayEvaluate(), new String[]{"ID", "Method", "URL", "Token", "Response code"});
    evaluateTableModel.fireTableDataChanged();
  }

  // Refresh UDA table
  public void refreshUdaTable(UdaTableModel udaTableModel) {
    udaTableModel.setDataVector(udaManager.toStringArray(), getUdaColumnNames());
    udaTableModel.fireTableDataChanged();
  }

  /* Error messages */

  // Set error message for endpoints page
  public void endpointsErrorMessage(String message, Color colour) {
    EndpointsErrorLabel.setText(message);
    EndpointsErrorLabel.setForeground(colour);
  }

  // Set error message for tokens page
  public void tokensErrorMessage(String message, Color colour) {
    TokensErrorLabel.setText(message);
    TokensErrorLabel.setForeground(colour);
  }

  // Set error message for detection page
  public void detectionErrorMessage(String message, Color colour) {
    detectionErrorLabel.setText(message);
    detectionErrorLabel.setForeground(colour);
  }


}
