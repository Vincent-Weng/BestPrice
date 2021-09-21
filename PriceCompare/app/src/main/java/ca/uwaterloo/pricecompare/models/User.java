package ca.uwaterloo.pricecompare.models;

import java.util.List;

public class User {
  private List<String> recentlyViewed;
  private String messageToken;

  public User() {}

  public User(List<String> recentlyViewed, String messageToken) {
    this.recentlyViewed = recentlyViewed;
    this.messageToken = messageToken;
  }

  public List<String> getRecentlyViewed() {
    return recentlyViewed;
  }

  public void setRecentlyViewed(List<String> recentlyViewed) {
    this.recentlyViewed = recentlyViewed;
  }

  public String getMessageToken() {
    return messageToken;
  }

  public void setMessageToken(String messageToken) {
    this.messageToken = messageToken;
  }
}
