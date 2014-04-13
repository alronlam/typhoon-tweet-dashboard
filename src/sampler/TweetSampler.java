package sampler;

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
	private static final String CUSTOMER_TOKEN = "fwbtkGf8N97yyUZyH5YzLw";
	private static final String CUSTOMER_SECRET = "oQA5DunUy89Co5Hr7p4O2WmdzqiGTzssn2kMphKc8g";
	private AccessToken accessToken;
	private TwitterStream twitterStream;
	private boolean isSampling;
	
	private TweetSampler(){
		isSampling = false;
		accessToken = new AccessToken(ACCESS_TOKEN, ACCESS_SECRET);
		twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.setOAuthConsumer(CUSTOMER_TOKEN, CUSTOMER_SECRET);
		twitterStream.setOAuthAccessToken(accessToken);
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

}
