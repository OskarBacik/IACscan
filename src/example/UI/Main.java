package example.UI;

import example.endpoints.Endpoint;
import example.endpoints.EndpointManager;
import example.requests.RequestManager;
import example.tokens.Token;
import example.tokens.TokenManager;

import javax.swing.*;

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
  private JPanel ViewTokensPanel;
  private JLabel addTokensLabel;
  private JTextField addTokensName;
  private JTextField addTokensHeader;
  private JTextField addTokensValue;
  private JButton addTokensButton;
  private JTable viewTokensTable;
  private JLabel addTokensLabel1;
  private JLabel addTokensLabel2;
  private JLabel addTokensLabel3;
  private JSeparator endpointsSep;
  private JTextArea addEndpointsPost;
  private JLabel addEndpointsLabel1;
  private JTable table1;
  private JPanel EvaluatePanel;
  private JButton evaluateButton;
  private JProgressBar evaluateBar;

  public Main() {

    // Create logic managers
    endpointManager = new EndpointManager();
    tokenManager = new TokenManager();
    requestManager = new RequestManager();

    setContentPane(MainPanel);
    setTitle("BACscan");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(1920, 800);
    setLocationRelativeTo(null);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setVisible(true);

    // Add endpoint button
    addEndpointsButton.addActionListener(e -> {
      Endpoint newEndpoint = new Endpoint(addEndpointsUrl.getText(), (String) addEndpointsMethod.getSelectedItem(),null); // TODO: Fix to work with POST
      endpointManager.addEndpoint(newEndpoint);
      addEndpointsUrl.setText("");
    });

    // Add token button
    addTokensButton.addActionListener(e -> {
      Token newToken = new Token(addTokensName.getText(), addTokensHeader.getText(), addTokensValue.getText());
      tokenManager.addToken(newToken);
      addTokensName.setText("");
      addTokensHeader.setText("");
      addTokensValue.setText("");
    });

    // token table
    viewTokensTable = new JTable();
  }

  public static void main(String[] args) {
    new Main();
  }

  public void evaluate(RequestManager requestManager, EndpointManager endpointManager, TokenManager tokenManager) {
    // clear request manager

    // for endpoint in endpoints
    // for token in tokens
    // send request
    // add request to request manager

    // for endpoint in endpoints
    // send request with different HTTP method
    // if not in endpoints
    // flag
  }
}
