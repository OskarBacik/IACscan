package example.UI.tableModels;

import example.UI.Main;

import javax.swing.table.DefaultTableModel;

public class EvaluateTableModel extends DefaultTableModel {
  private final Main main;

  public EvaluateTableModel(Main main) {
    super(main.requestManager.toStringArrayEvaluate(), new String[]{"ID", "Method", "URL", "Token", "Response code"});
    this.main = main;
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }
}
