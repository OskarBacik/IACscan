package example.uda;

import java.util.ArrayList;
import java.util.List;

public class UdaManager {
  private List<Uda> udaList;

  public UdaManager() {
    this.udaList = new ArrayList<>();
  }

  public List<Uda> getUdaList() {
    return udaList;
  }

  public void addUda(Uda uda) {
    udaList.add(uda);
  }

  public String[][] toStringArray() {

    if (udaList.isEmpty()) {
      return new String[0][0];
    }

    String[][] result = new String[udaList.size()][udaList.get(0).getPolicy().size() + 3];

    for (int i = 0; i < udaList.size(); i++) {
      Uda uda = udaList.get(i);
      result[i][0] = String.valueOf(uda.getId());
      result[i][1] = String.valueOf(uda.getEndpoint().getUrl());
      result[i][2] = String.valueOf(uda.getEndpoint().getMethod());
      for (int j = 0; j < uda.getPolicy().size(); j++) {
        result[i][j + 3] = String.valueOf(uda.getPolicy().get(j));
      }
    }

    return result;
  }

  public void clearList() {
    udaList.clear();
  }

  public Uda getUdaById(int id) {
    for (Uda uda : udaList) {
      if (uda.getId() == id) {
        return uda;
      }
    }
    return null;
  }
}
