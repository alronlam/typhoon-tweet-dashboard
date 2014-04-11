package tweet;

/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
public class Miner {

    public static final String CONSUMER_KEY = "fwbtkGf8N97yyUZyH5YzLw";
    public static final String CONSUMER_SECRET = "oQA5DunUy89Co5Hr7p4O2WmdzqiGTzssn2kMphKc8g";
    public static final String OAUTH_ACCESS_TOKEN = "461053984-aww1IbpSVcxUE2jN8VqsOkEw8IQeEMusx4IdPM9p";
    public static final String OAUTH_ACCESS_TOKEN_SECRET = "WGsbat8P8flqKqyAymnWnTnAGI5hZkgdaQSE8XALs7ZEp";
    
    public static void main(String[] args) {
        Miner miner = new Miner();
        miner.mine("ReliefPH");
    }
    
    
    /**
     * Usage: java twitter4j.examples.search.SearchTweets [query]
     *
     * @param toSearch  
     */
    public List<Status> mine(String toSearch) {
        List<Status> results = new ArrayList<Status>();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(CONSUMER_KEY)
          .setOAuthConsumerSecret(CONSUMER_SECRET)
          .setOAuthAccessToken(OAUTH_ACCESS_TOKEN)
          .setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
 
        try {           
            Query query = new Query(toSearch);
            query.setCount(100);
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                results.addAll(result.getTweets());
                for (Status tweet : tweets) {
                    System.out.println(tweet.getId() + " @ " + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }
            } while ((query = result.nextQuery()) != null);
            
        } 
        catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return results;
    }
}
