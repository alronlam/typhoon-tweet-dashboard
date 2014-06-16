package sampler;

import helpers.Constants;

import java.util.ArrayList;

import org.vertx.java.core.Vertx;

import classifier.BinarySMOClassifier;
import classifier.TweetClassifierFacade;
import database.ClassifiedTweetsDataManager;
import database.DataManager;
import tweet.Category;
import tweet.Tweet;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class TweetStatusListener implements StatusListener {
	private ArrayList<BinarySMOClassifier> binaryClassifiers;
	
	private TweetClassifierFacade classifierFacade;
	
	private Vertx vertx;
	
	public TweetStatusListener(Vertx vertx){
		binaryClassifiers = new ArrayList<BinarySMOClassifier>();
		for(Category category: Category.values())
			binaryClassifiers.add(new BinarySMOClassifier(category.getName()));
		
		classifierFacade = new TweetClassifierFacade();
		
		this.vertx = vertx;
	}
	
	@Override
	public void onException(Exception arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStallWarning(StallWarning arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatus(Status status) {
		//do the classification here. if it fit any of the official categories, place in the db
		Category category;
		if((category = classifierFacade.addToDBIfRelevant(status))!= null){
			Tweet tweet = new Tweet(status);
			tweet.setFinalCategory(category.getName());
			System.out.println("Classified "+status.getText()+" under "+category.getName());
			vertx.eventBus().publish(Constants.BROADCAST_NEW_TWEET_ADDRESS, tweet.toJsonObject());
		}
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
