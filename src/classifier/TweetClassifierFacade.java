package classifier;

import java.util.ArrayList;

import database.ClassifiedTweetsDataManager;
import tweet.Category;
import twitter4j.Status;

public class TweetClassifierFacade {

	private SMOClassifier classifier;
	private ArrayList<SMOClassifier> binaryClassifiers;
	
	public TweetClassifierFacade(){
		binaryClassifiers = new ArrayList<SMOClassifier>();
		for(Category category: Category.values())
			binaryClassifiers.add(new SMOClassifier(category.getName()));
	}
	
	public Category classify(Status status){
		ClassifiedTweetsDataManager dm = new ClassifiedTweetsDataManager();
        for(SMOClassifier classifier: binaryClassifiers){
        	Category category = classifier.classify(status);
            if(category!= null){
            	return category;
            }
        }
		return null;
	}
	
	public void addToDBIfRelevant(Status status){
    	Category category = classify(status);
    	if(category != null){
    		ClassifiedTweetsDataManager dataManager = new ClassifiedTweetsDataManager();
    		dataManager.insertTweet(status, category);
    	}
	}
}
