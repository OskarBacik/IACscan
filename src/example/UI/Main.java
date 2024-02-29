package example.UI;

import example.endpoints.Endpoint;
import example.endpoints.EndpointManager;
import example.requests.Request;
import example.requests.RequestManager;
import example.tokens.Token;
import example.tokens.TokenManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    EndpointsTableModel endpointsTableModel = new EndpointsTableModel();
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

    tokenManager.addToken(new Token("Unauthenticated", "", ""));
    // Example data - TODO: Remove
    tokenManager.addToken(new Token("admin", "Jwt", "token1"));
    tokenManager.addToken(new Token("user", "Jwt", "token2"));

    TokenTableModel tokenTableModel = new TokenTableModel();
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

    EvaluateTableModel evaluateTableModel = new EvaluateTableModel();
    viewEvaluateTable.setModel(evaluateTableModel);

    // Start button logic
    evaluateButton.addActionListener(e -> {
      try {
        evaluate(requestManager, endpointManager, tokenManager, evaluateTableModel, evaluateBar);
        System.out.println("done");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    // Display row selection in info panel
    viewEvaluateTable.getSelectionModel().addListSelectionListener(e -> {
      Integer selectedId = Integer.parseInt((String) viewEvaluateTable.getValueAt(viewEvaluateTable.getSelectedRow(), 0));
      evaluateRequestText.setText(requestManager.getById(selectedId).getRequest().toString()); // TODO: reformat
      evaluateResponseText.setText(requestManager.getById(selectedId).getResponse().toString()); // TODO: get response body
    });

    // OVERVIEW
    String[] overviewColumnNames = getOverviewColumnNames();
    OverviewTableModel overviewTableModel = new OverviewTableModel(overviewColumnNames);
    viewOverviewTable.setModel(overviewTableModel);

    overviewTableRefreshButton.addActionListener(e -> {
      requestManager.overviewToStringArray(endpointManager, tokenManager);
      overviewTableModel.setDataVector(requestManager.overviewToStringArray(endpointManager, tokenManager), getOverviewColumnNames());
      overviewTableModel.fireTableDataChanged();
    });

  }

  public static void main(String[] args) {
    new Main();
  }

  // custom table structure for tokens
  class TokenTableModel extends DefaultTableModel {
    public TokenTableModel() {
      super(tokenManager.toStringArray(), new String[]{"ID", "Label", "Header Name", "Value"});
    }
    @Override
    public boolean isCellEditable(int row, int column) {
      return false;
    }
  }

  // custom table structure for endpoints
  class EndpointsTableModel extends DefaultTableModel {
    public EndpointsTableModel() {
      super(endpointManager.toStringArray(), new String[]{"ID", "URL", "Method", "Body"});
    }
    @Override
    public boolean isCellEditable(int row, int column) {
      return false;
    }
  }

  class EvaluateTableModel extends DefaultTableModel {
    public EvaluateTableModel() {
      super(requestManager.toStringArray(), new String[]{"ID", "URL", "Token", "Response code"});
    }
    @Override
    public boolean isCellEditable(int row, int column) {
      return false;
    }
  }

  class OverviewTableModel extends DefaultTableModel {
    private String[] columnNames;
    public OverviewTableModel(String[] columnNames) {
      super(requestManager.toStringArray(), columnNames);
    }
    @Override
    public boolean isCellEditable(int row, int column) {
      return false;
    }
  }

  public void evaluate(RequestManager requestManager, EndpointManager endpointManager, TokenManager tokenManager,
                       EvaluateTableModel evaluateTableModel, JProgressBar evaluateBar) throws IOException {

    requestManager.clearList();

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
      }
    }

    // for endpoint in endpoints
    // send request with different HTTP method
    // if not in endpoints
    // flag
  }

  public String[] getOverviewColumnNames() {
    List<String> columnNames = new ArrayList<>();
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
      // Call the superclass method to get the default rendering
      Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

      // Customize the rendering based on the cell value
      if (value instanceof Integer) {
        int age = (Integer) value;
        // Change the background color based on the age value
        if (age < 25) {
          cellComponent.setBackground(Color.GREEN);
        } else if (age > 30) {
          cellComponent.setBackground(Color.RED);
        } else {
          // Default background color for other ages
          cellComponent.setBackground(table.getBackground());
        }
      }

      return cellComponent;
    }
  }
}
