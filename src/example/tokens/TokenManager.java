package example.tokens;

import java.util.ArrayList;
import java.util.List;

public class TokenManager {

  // for storing auth main.requests.tokens, owner:token e.g. admin:{token1}, user:{token2}
  private List<Token> tokenList;

  public TokenManager() {
    this.tokenList = new ArrayList<>();
  }

  public List<Token> getTokenList() {
    return tokenList;
  }

  public void addToken(Token token) {
    tokenList.add(token);
  }
}
