package test;

import example.tokens.Token;
import example.tokens.TokenManager;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TokenManagerTest {

  private TokenManager tokenManager;

  @Before
  public void setUp() {
    tokenManager = new TokenManager();
  }

  @Test
  public void testGetTokens() {
    Token token1 = new Token("admin", "JWT", "token1");
    Token token2 = new Token("user", "Bearer", "token2");

    tokenManager.addToken(token1);
    tokenManager.addToken(token2);

    List<Token> tokens = tokenManager.getTokenList();

    assertEquals(2, tokens.size());
    assertTrue(tokens.contains(token1));
    assertTrue(tokens.contains(token2));
  }

  @Test
  public void testGetById() {
    Token token1 = new Token("admin", "JWT", "token1");
    Token token2 = new Token("user", "Bearer", "token2");

    tokenManager.addToken(token1);
    tokenManager.addToken(token2);

    assertEquals(token1, tokenManager.getTokenById(token1.getId()));
    assertEquals(token2, tokenManager.getTokenById(token2.getId()));
  }

  @Test
  public void testDeleteById() {
    Token token1 = new Token("admin", "JWT", "token1");
    Token token2 = new Token("user", "Bearer", "token2");

    tokenManager.addToken(token1);
    tokenManager.addToken(token2);

    tokenManager.deleteById(token1.getId());
    assertNull(tokenManager.getTokenById(token1.getId()));
    assertNotNull(tokenManager.getTokenById(token2.getId()));
  }

}
