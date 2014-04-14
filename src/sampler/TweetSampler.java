package sampler;

import database.ClassifiedTweetsDataManager;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;

public class TweetSampler {

	private static TweetSampler instance;

	public static TweetSampler getInstance(){
		if(instance == null)
			instance = new TweetSampler();
		return instance;
	}
	
	private static final String ACCESS_TOKEN = "461053984-aww1IbpSVcxUE2jN8VqsOkEw8IQeEMusx4IdPM9p";
	private static final String ACCESS_SECRET = "WGsbat8P8flqKqyAymnWnTnAGI5hZkgdaQSE8XALs7ZEp";
	private static final String CONSUMER_TOKEN = "fwbtkGf8N97yyUZyH5YzLw";
	private static final String CONSUMER_SECRET = "oQA5DunUy89Co5Hr7p4O2WmdzqiGTzssn2kMphKc8g";
	private AccessToken accessToken;
	private Twitter twitter;
	private TwitterStream twitterStream;
	private boolean isSampling;
	
	private TweetSampler(){
		isSampling = false;
		accessToken = new AccessToken(ACCESS_TOKEN, ACCESS_SECRET);
		twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.setOAuthConsumer(CONSUMER_TOKEN, CONSUMER_SECRET);
		twitterStream.setOAuthAccessToken(accessToken);
		
		
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_TOKEN, CONSUMER_SECRET);
		twitter.setOAuthAccessToken(accessToken);
	}
	
	public void setStatusListener(TweetStatusListener statusListener){
		twitterStream.clearListeners();
		twitterStream.addListener(statusListener);
	}
	
	public void sample(){
		if(!isSampling){
			twitterStream.sample();
			isSampling = true;
		}
	}

	public Status getTweetFromId(long tweetID){
	    try {
	        return twitter.showStatus(tweetID);
	    } catch (TwitterException e) {
	        e.printStackTrace();
	        
	        //delete from db, meaning tweetID may have been deleted or does not exist at all.
	       ClassifiedTweetsDataManager.getInstance().deleteTweet(tweetID);
	    }
	    return null;
	}
	
	public boolean isTweetValid(long tweetID){
		try {
			Status status = twitter.showStatus(tweetID);

			if(status != null)
				return true;
		} catch (TwitterException e) {
			return false;
		}
		return false;
	}
	
}
