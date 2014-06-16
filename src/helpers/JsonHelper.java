package helpers;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

import tweet.Category;
import tweet.Tweet;

public class JsonHelper {

	   public static JsonArray toJsonArray(LinkedHashMap<Category, ArrayList<Tweet>> tweetMap){
		   JsonArray json = new JsonArray();
		   
		   for(Category category : tweetMap.keySet()){
			
			   ArrayList<Tweet> tweets = tweetMap.get(category);
			   
			   JsonArray tweetArray = new JsonArray();
			   for(Tweet tweet: tweets)
				   tweetArray.add(tweet.toJsonObject());
			   
			   JsonObject categoryJson = new JsonObject();
			   categoryJson.putString("pk", category.getName());
			   categoryJson.putString("description", category.getDescription());
			   categoryJson.putArray("tweets", tweetArray);
			   
			   json.add(categoryJson);
		   }
		   
		   return json;
	   }
	
}
