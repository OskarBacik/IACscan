package example.UI.tableModels;

import example.UI.Main;

import javax.swing.table.DefaultTableModel;

public class OverviewTableModel extends DefaultTableModel {
  private final Main main;
  private String[] columnNames;

  public OverviewTableModel(Main main, String[] columnNames) {
    super(main.requestManager.toStringArrayEvaluate(), columnNames);
    this.main = main;
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }
}
