package handlers;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

import sampler.TweetSampler;
import tweet.Category;
import tweet.Tweet;
import twitter4j.Status;
import classifier.TweetClassifierFacade;

public class ClassifyTweetRequestHandler  implements Handler<Message<JsonObject>> {

	@Override
	public void handle(Message<JsonObject> msg) {
		String tweetIDString = msg.body().getString("id");
		if(tweetIDString == null){
			msg.reply(false);
			return;
		}
		long tweetID = Long.parseLong(tweetIDString);
		Status status = TweetSampler.getInstance().getTweetFromId(tweetID);
		TweetClassifierFacade facade = new TweetClassifierFacade();
		Category category = facade.addToDBIfRelevant(status);
		
		if(category == null){
			msg.reply(false);
			return;
		}
		
		
		JsonObject tweetJson = new Tweet(status).toJsonObject();
		JsonObject categoryJson = new JsonObject();
		categoryJson.putString("pk", category.getName());
		categoryJson.putString("description", category.getDescription());

		JsonObject replyJson = new JsonObject();
		replyJson.putObject("category", categoryJson);
		replyJson.putObject("tweet", tweetJson);
		
		msg.reply(replyJson);
	}

}
