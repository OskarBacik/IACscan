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

  public String[][] toStringArray() {
    String[][] result = new String[tokenList.size()][4];

    for (int i = 0; i < tokenList.size(); i++) {
      Token token = tokenList.get(i);
      result[i][0] = String.valueOf(token.getId());
      result[i][1] = token.getLabel();
      result[i][2] = token.getHeaderName();
      result[i][3] = token.getValue();
    }

    return result;
  }

  public Token getTokenById(int id){
    List<Token> tokens = getTokenList();
    for(Token token:tokens){
      if(token.getId() == id){
       return token;
      }
    }
    return null;
  }

  public void deleteById(int id) {
    for (Token token : tokenList) {
      if (token.getId() == id) {
        tokenList.remove(token);
        return;
      }
    }
  }
}
