package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tweet.Category;
import tweet.Tweet;
import twitter4j.Status;

public class ClassifiedTweetsDataManager {
    DAOFactory factory;
    long lastInsertedID;
    public ClassifiedTweetsDataManager() {
        factory = DAOFactory.getInstance();
    }
    
    private static final String SQL_CREATE = 
            "INSERT INTO Tweets(id, text, datetime, username, userPicURL, category) " +
            " VALUES (?, ?, ?, ?, ?, ?) ";
    private static final String SQL_RETRIEVE = 
            "SELECT * " +
            "FROM Tweets ";
    public void insertTweet(Status tweet, Category category){
    	 Connection conn = null;
         PreparedStatement ps = null;
         try {
             conn = factory.getClassifiedConnection();
      
             Object[] values = {
                     tweet.getId(),
                     tweet.getText(),
                     tweet.getCreatedAt(),
                     tweet.getUser().getScreenName(),
                     tweet.getUser().getProfileImageURL(),
                     category.toString()
             };
                 ps = DAOUtil.prepareStatement(conn, SQL_CREATE, false, values);
                 ps.executeUpdate();
                 lastInsertedID = tweet.getId();
         }
         catch (SQLException e) {
             System.err.println(e.getMessage());
         }
         finally {
             DAOUtil.close(conn, ps);
         }
    }

    public Tweet getLastInsertedTweet(){
    		System.out.println("******");
    	  List<Tweet> result = new ArrayList<>();
          Connection conn = null;
          PreparedStatement ps = null;
          ResultSet rs = null;
          Object[] values = {};
          try {
              conn = factory.getClassifiedConnection();
              ps = DAOUtil.prepareStatement(conn, SQL_RETRIEVE+" ORDER BY DATETIME DESC ", false, values);
              
              rs = ps.executeQuery();
              
              while (rs.next()) {
            	  System.out.println("pumasok!");
                  Tweet tweet = map(rs);
                  result.add(tweet);
                  break;
              }
          }
          catch (SQLException e) {
              System.err.println(e.getMessage());
          }
          finally {
              DAOUtil.close(conn, ps, rs);
          }
          
          if(result.size() > 0)
        	  return result.get(0);
          return null;
    }
    
    private Tweet map(ResultSet rs) {
        Tweet result = null;
        try {
            result = new Tweet(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("text"),
                rs.getString("datetime"),
                (Double)null,
                (Double)null,
                rs.getString("userPicURL")
            );
            
            result.setFinalCategory(rs.getString("category"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
}
