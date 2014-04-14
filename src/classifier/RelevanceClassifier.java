package classifier;

import tweet.Category;
import twitter4j.Status;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class RelevanceClassifier {

	final String RELEVANCE_MODEL = "relevance.model";
	
	private Classifier relevanceSMOClassifier;
	
	public RelevanceClassifier(){
		relevanceSMOClassifier = loadSMOClassifier();
	}
	
	
	public boolean isRelevant(Status status){
		//use the weka classifier here
		if(relevanceSMOClassifier != null){
			// Create the attributes, class and text 
			FastVector fvNominalVal = new FastVector(2); 
			fvNominalVal.addElement("relevant"); 
			fvNominalVal.addElement("irrelevant"); 
			
			Attribute attribute1 = new Attribute("class", fvNominalVal); 
			Attribute attribute2 = new Attribute("text",(FastVector) null); 
			
			// Create list of instances with one element 
			FastVector fvWekaAttributes = new FastVector(2); 
			fvWekaAttributes.addElement(attribute1); 
			fvWekaAttributes.addElement(attribute2); 
			Instances instances = new Instances("Test relation", fvWekaAttributes, 1); 
			
			// Set class index 
			instances.setClassIndex(0); 
			
			// Create and add the instance 
			Instance instance = new Instance(2); 
			instance.setValue(attribute2, status.getText()); 
			
			// instance.setValue((Attribute)fvWekaAttributes.elementAt(1), text); 
			instances.add(instance);
			try{
				double pred = relevanceSMOClassifier.classifyInstance(instances.instance(0)); 
				String category = instances.classAttribute().value((int) pred);
				if(category.equals("relevant"))
					return true;
			}catch(Exception e){e.printStackTrace();}
		}
		return false;
	}
	
	private Classifier loadSMOClassifier(){
		//load the trained classifer from the file - desrialization
		try{
			return (Classifier)weka.core.SerializationHelper.read("C:/Users/asus/workspace/Typhoon Tweet Dashboard/data/"+RELEVANCE_MODEL);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
