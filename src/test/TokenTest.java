package test;

import example.tokens.Token;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TokenTest {

  private Token token;

  @Before
  public void setUp() {
    token = new Token("admin", "JWT", "token123");
  }

  @Test
  public void testConstructor() {
    assertEquals("admin", token.getLabel());
    assertEquals("JWT", token.getHeaderName());
    assertEquals("token123", token.getValue());
  }

  @Test
  public void testGetters() {
    assertEquals("admin", token.getLabel());
    assertEquals("JWT", token.getHeaderName());
    assertEquals("token123", token.getValue());
  }

  @Test
  public void testEditToken() {
    token.editToken("user", "Bearer", "newToken456");
    assertEquals("user", token.getLabel());
    assertEquals("Bearer", token.getHeaderName());
    assertEquals("newToken456", token.getValue());
  }
}
