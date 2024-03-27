package example.tokens;

public class Token {

  private static int idCounter = 0;
  private int id;
  private String label; // e.g. user, admin
  private String headerName; // e.g. JWT or Cookie
  private String value; // token

  public Token(String label, String headerName, String value) {
    this.id = createId();
    this.label = label;
    this.headerName = headerName;
    this.value = value;
  }

  public void editToken(String label, String headerName, String value){
    this.label = label;
    this.headerName = headerName;
    this.value = value;
  }

  private synchronized int createId() {
    return ++idCounter;
  }

  public int getId() {
    return id;
  }

  public String getLabel() {
    return label;
  }

  public String getHeaderName() {
    return headerName;
  }

  public String getValue() {
    return value;
  }
}
