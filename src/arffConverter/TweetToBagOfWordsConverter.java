package arffConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import tweet.Tweet;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.stemmers.LovinsStemmer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
 
public class TweetToBagOfWordsConverter {
     
    //Converts the tweet ARFF into a bag of words ARFF
    public static void createBagOfWordsARFF(List<Tweet> tweets, String desiredOutputFileLocation) throws Exception{
    	if(tweets.size() > 0 && desiredOutputFileLocation != null && !desiredOutputFileLocation.trim().isEmpty()){
    		Instances rawDataset = createDataset(tweets); //pass the input file, delimiter, and number of desired entries
       		Instances wordVectorSet = convertToWordVector(rawDataset);
       		saveToArff(wordVectorSet, desiredOutputFileLocation);
       		
       		
       		wordVectorSet.setClassIndex(0); 
       	 try {
             //use the classifier that you want


            rawDataset.setClassIndex(0);
            
            StringToWordVector filter = new StringToWordVector();
 			filter.setAttributeIndices("last");
 			
 			FilteredClassifier classifier = new FilteredClassifier();
 			classifier.setFilter(filter);
 			classifier.setClassifier(new SMO());
 			
 			classifier.buildClassifier(rawDataset);
                    
             //Evaluating the classifer
             Evaluation eval = new Evaluation(rawDataset);
             eval.evaluateModel(classifier,rawDataset);
                    
             //printing the summary of training
             String strSummary = eval.toSummaryString();
             System.out.println("-----------TRAINING SUMMARY---------");
             System.out.println(strSummary);
             System.out.println("------------------------------------");
                    
             //Serializing the classsifier object in to a file
             weka.core.SerializationHelper.write("SMO.model",classifier);
           }catch(Exception ex) {
        	   ex.printStackTrace();
           }
    	}
    }
    
    /*Converts a dataset into a word vector (bag of words approach)*/
    private static Instances convertToWordVector(Instances dataset) throws Exception{
    	StringToWordVector stringToWordVectorFilter = new StringToWordVector(1000000);
		stringToWordVectorFilter.setInputFormat(dataset);
		stringToWordVectorFilter.setUseStoplist(true);
		
		stringToWordVectorFilter.setTFTransform(true); 
		stringToWordVectorFilter.setIDFTransform(true); 
		LovinsStemmer stemmer = new LovinsStemmer (); 
		stringToWordVectorFilter.setStemmer(stemmer); 
		stringToWordVectorFilter.setMinTermFreq(5); 
		stringToWordVectorFilter.setWordsToKeep(500); 

		return Filter.useFilter(dataset, stringToWordVectorFilter); 
    }
    
    /*Saves the dataset to a file. You can use this to save an Instances object to a file (like .ARFF) so you can use it for Weka)*/
    private static void saveToArff(Instances dataset, String targetLocation) throws Exception{
    	ArffSaver saver = new ArffSaver();
		saver.setInstances(dataset);
		saver.setFile(new File(targetLocation));
		saver.writeBatch();
    }
    
    /*
     * Format of a row in the input should be:
     * <Classification> <delimiter> <Tweet Content without the hashtags>
     * 
     * Example of a line in the input file:
     * rescuePH , "Need help here" 
     * 
     * Also, number of entries desired to be read is explicitly stated because i have no idea how to detect EOF with BufferedReader.
     * Will see if i can change this.
     */
    private static Instances createDataset(List<Tweet> tweets) throws Exception{
    	FastVector attributes = createTweetAttributes();
    
        // Create an empty training set
        Instances trainingSet = new Instances("Bag Of Words", attributes, tweets.size());      
         
        // Set class index
        trainingSet.setClassIndex(0); //set to zero because classification is assumed to be first (from createTweetAttributes())
         
        // Create the instance
  
        for(Tweet tweet: tweets){
        	if(tweet != null && tweet.getCategory() != null){
	        	Instance iExample = new Instance(2);
	        	//System.out.println(tweet.getCategory()+" -=- \""+tweet.getCleanText()+"\"");
	        	iExample.setValue(trainingSet.attribute(0) , tweet.getCategory().toString());
	        	iExample.setValue(trainingSet.attribute(1) , tweet.getCleanText());
	
	        	trainingSet.add(iExample);
        	}
        }
        
        return trainingSet;
    }
    
    // Creates the attributes for the arff. (ALWAYS makes classification attribute as first attribute (index 0) ) 
    private static FastVector createTweetAttributes(){
    	String[] classificationValues = {"reliefph" , "rescueph", "safenow", "floodph", "tracingph", "yolandaph"};
    	
    
        // Declare the class attribute along with its values
        FastVector fvClassVal = new FastVector(classificationValues.length);
        for(String classification: classificationValues)
        	fvClassVal.addElement(classification);
        Attribute classificationAttribute = new Attribute("theClass", fvClassVal);
         
    	// Text attribute of the tweet (content)
        Attribute textAttribute = new Attribute("text", (FastVector)null);

        // Declare the feature vector
        FastVector attributes = new FastVector(2);
        attributes.addElement(classificationAttribute);    
        attributes.addElement(textAttribute);    
        
        return attributes;
    }
}
