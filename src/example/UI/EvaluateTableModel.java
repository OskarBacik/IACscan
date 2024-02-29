package example.UI;

import javax.swing.table.DefaultTableModel;

class EvaluateTableModel extends DefaultTableModel {
  private final Main main;

  public EvaluateTableModel(Main main) {
    super(main.requestManager.toStringArray(), new String[]{"ID", "URL", "Token", "Response code"});
    this.main = main;
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }
}
