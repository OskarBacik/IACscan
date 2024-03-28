package example.UI.colourRenderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class DetectionTableColourRenderer extends DefaultTableCellRenderer {

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    int responseCode;
    java.util.List<Integer> greenList = Arrays.asList(404,405);
    List<Integer> redList = Arrays.asList(200,201,202,204,304);

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
    } else {
      cellComponent.setBackground(Color.ORANGE);
    }

    return cellComponent;
  }
}
