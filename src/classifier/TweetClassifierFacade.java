package classifier;

import java.util.ArrayList;

import database.ClassifiedTweetsDataManager;
import tweet.Category;
import twitter4j.Status;

public class TweetClassifierFacade {

	private BinarySMOClassifier classifier;
	private ArrayList<BinarySMOClassifier> binaryClassifiers;
		
	public TweetClassifierFacade(){
		binaryClassifiers = new ArrayList<BinarySMOClassifier>();
		for(Category category: Category.values())
			binaryClassifiers.add(new BinarySMOClassifier(category.getName()));
	}
	
	public Category classify(Status status){
        for(BinarySMOClassifier classifier: binaryClassifiers){
        	Category category = classifier.classify(status);
            if(category!= null){
            	return category;
            }
        }
		return null;
	}
	
	public Category classifyWithRelevance(Status status){
		RelevanceClassifier relevanceClassifier = new RelevanceClassifier();
		if(relevanceClassifier.isRelevant(status)){
			Category category = new CategoryClassifier().determineCategory(status);
			if(category != null){
				return category;
			}
		}
		return null;
	}
	
	public Category addToDBIfRelevant(Status status){
    	//Category category = classify(status);
		System.out.println("went in here");
    	Category category = classifyWithRelevance(status);
		if(category != null){
    		ClassifiedTweetsDataManager.getInstance().insertTweet(status, category);
    		return category;
    	}
    	return null;
	}
}
