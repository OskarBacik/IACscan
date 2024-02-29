package example.UI;

import javax.swing.table.DefaultTableModel;

// custom table structure for endpoints
class EndpointsTableModel extends DefaultTableModel {
  private final Main main;

  public EndpointsTableModel(Main main) {
    super(main.endpointManager.toStringArray(), new String[]{"ID", "URL", "Method", "Body"});
    this.main = main;
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }
}
