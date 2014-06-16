package server;
import handlers.HttpRequestHandler;
import handlers.InitialTweetsRequestHandler;
import helpers.Constants;

import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.sockjs.SockJSServer;
import org.vertx.java.platform.Verticle;

import sampler.TweetSampler;
import sampler.TweetStatusListener;

public class TweetDashboardServer extends Verticle{
	
	@Override
	public void start(){
		HttpServer server = vertx.createHttpServer();
		server.setMaxWebSocketFrameSize(Integer.MAX_VALUE);
		server.requestHandler(new HttpRequestHandler(vertx));
		
		JsonArray permitted = new JsonArray();
		permitted.add(new JsonObject()); // Let everything through
		JsonObject config = new JsonObject();
		config.putString("prefix", "/eventbus");
		
		ServerHook hook = new ServerHook(container.logger());
		
		SockJSServer sockJSServer = vertx.createSockJSServer(server);
		sockJSServer.setHook(hook);
		sockJSServer.bridge(config, permitted, permitted);
		
		registerHandlers();
		
		server.listen(8080);
		
		//start the sampling
//		TweetSampler ts = TweetSampler.getInstance();
//		ts.setStatusListener(new TweetStatusListener(vertx));
//		ts.sample();
	}
	
	private void registerHandlers(){
		vertx.eventBus().registerHandler(Constants.REQUEST_TWEETS_ADDRESS, new InitialTweetsRequestHandler());
	}
}