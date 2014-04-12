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
    
    public ClassifiedTweetsDataManager() {
        factory = DAOFactory.getInstance();
    }
    
    private static final String SQL_CREATE = 
            "INSERT INTO Tweet(id, text, date, username, userPicURL, category) " +
            " VALUES (?, ?, ?, ?, ?, ?) ";
    
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
         }
         catch (SQLException e) {
             System.err.println(e.getMessage());
         }
         finally {
             DAOUtil.close(conn, ps);
         }
    }
    
}
