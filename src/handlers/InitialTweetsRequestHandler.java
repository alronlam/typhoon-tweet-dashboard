package handlers;

import helpers.JsonHelper;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

import database.ClassifiedTweetsDataManager;

public class InitialTweetsRequestHandler implements Handler<Message<JsonObject>> {

	@Override
	public void handle(Message<JsonObject> msg) {
		JsonArray initialCategoriesJson = JsonHelper.toJsonArray(ClassifiedTweetsDataManager.getInstance().getLatestTweetsInAllCategories(5));
		JsonObject reply = new JsonObject();
		reply.putArray("categoryArray",	initialCategoriesJson);
		msg.reply(initialCategoriesJson);
	}

}
