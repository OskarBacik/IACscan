package example.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Custom colour renderer for Overview Table
class OverviewTableColourRenderer extends DefaultTableCellRenderer {
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    int responseCode;
    List<Integer> greenList = Arrays.asList(200,201,202);
    List<Integer> orangeList = Arrays.asList(300,301,302,304,307,308);
    List<Integer> redList = Arrays.asList(400,401,402,403);

    // colour code each cell based on the response code
    Object cell = table.getValueAt(row, column);
    try {
      responseCode = Integer.parseInt((String) cell);
    } catch (NumberFormatException exception) {
      responseCode = 0;
    }
    if (greenList.contains(responseCode)) {
      cellComponent.setBackground(Color.GREEN);
    } else if (redList.contains(responseCode)) {
      cellComponent.setBackground(Color.RED);
    } else if (orangeList.contains(responseCode)) {
      cellComponent.setBackground(Color.ORANGE);
    } else if (responseCode == 404) {
      cellComponent.setBackground(Color.GRAY);
    } else {
      cellComponent.setBackground(table.getBackground());
    }

    return cellComponent;
  }
}
