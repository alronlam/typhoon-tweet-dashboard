package sampler;

import java.util.ArrayList;

import classifier.SMOClassifier;
import classifier.TweetClassifierFacade;
import database.ClassifiedTweetsDataManager;
import database.DataManager;
import tweet.Category;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class TweetStatusListener implements StatusListener {
	private SMOClassifier classifier;
	private ArrayList<SMOClassifier> binaryClassifiers;
	
	private TweetClassifierFacade classifierFacade;
	private ClassifiedTweetsDataManager dataManager;
	
	public TweetStatusListener(){
		binaryClassifiers = new ArrayList<SMOClassifier>();
		for(Category category: Category.values())
			binaryClassifiers.add(new SMOClassifier(category.getName()));
		
		classifierFacade = new TweetClassifierFacade();
		dataManager = new ClassifiedTweetsDataManager();
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
		classifierFacade.addToDBIfRelevant(status);
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
