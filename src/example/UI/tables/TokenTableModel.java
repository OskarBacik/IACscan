package example.UI.tables;

import example.UI.Main;

import javax.swing.table.DefaultTableModel;

// custom table structure for tokens
public class TokenTableModel extends DefaultTableModel {
  private final Main main;

  public TokenTableModel(Main main) {
    super(main.tokenManager.toStringArray(), new String[]{"ID", "Label", "Header Name", "Value"});
    this.main = main;
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }
}
