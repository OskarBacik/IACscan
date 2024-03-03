package example.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

// Custom colour renderer for Overview Table
class OverviewTableColourRenderer extends DefaultTableCellRenderer {
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    Integer responseCode;
    // colour code each cell based on the response code TODO: add more response codes
    Object cell = table.getValueAt(row, column);
    try {
      responseCode = Integer.parseInt((String) cell);
    } catch (NumberFormatException exception) {
      responseCode = 0;
    }
    if (responseCode == 200) {
      cellComponent.setBackground(Color.GREEN);
    } else if (responseCode == 403) {
      cellComponent.setBackground(Color.RED);
    } else if (responseCode == 404) {
      cellComponent.setBackground(Color.GRAY);
    } else {
      cellComponent.setBackground(table.getBackground());
    }

    return cellComponent;
  }
}
