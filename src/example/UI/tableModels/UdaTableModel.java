package example.UI.tableModels;
import example.UI.Main;
import javax.swing.table.DefaultTableModel;

public class UdaTableModel extends DefaultTableModel {

  private final Main main;

  public UdaTableModel(Main main, String[] columnNames) {
    super(main.udaManager.toStringArray(), columnNames);
    this.main = main;
    // ID, URL, Method, role1, role2, role3, role4, role5....
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }
}
