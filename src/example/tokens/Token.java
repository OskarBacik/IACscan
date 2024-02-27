package example.tokens;

public class Token {

  private String label; // e.g. user, admin
  private String headerName; // e.g. JWT or Cookie
  private String value; // token

  public Token(String label, String headerName, String value) {
    this.label = label;
    this.headerName = headerName;
    this.value = value;
  }
}
