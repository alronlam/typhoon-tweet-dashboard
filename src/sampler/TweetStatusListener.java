package sampler;

import java.util.ArrayList;

import classifier.SMOClassifier;
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
	public TweetStatusListener(){
		binaryClassifiers = new ArrayList<SMOClassifier>();
		for(Category category: Category.values())
			binaryClassifiers.add(new SMOClassifier(category.getName()));
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
		// TODO Auto-generated method stub
		//do the classification here. if it fit any of the official categories, place in the db
        //System.out.println(status.getUser().getName() + " : " + status.getText());

    	ClassifiedTweetsDataManager dm = new ClassifiedTweetsDataManager();
        for(SMOClassifier classifier: binaryClassifiers){
        	Category category = classifier.classify(status);
            if(category!= null){
            	//insert in db
            	dm.insertTweet(status, category);
            	//System.out.println(category.getDescription());
            	break; // only one category for now
            }
        }
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
