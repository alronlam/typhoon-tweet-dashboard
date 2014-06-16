package handlers;

import helpers.JsonHelper;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

import database.ClassifiedTweetsDataManager;
import tweet.Category;

public class HttpRequestHandler implements Handler<HttpServerRequest>{
	Vertx vertx;
	
	public HttpRequestHandler(Vertx vertx){
		this.vertx = vertx;
	}
	
	@Override
	public void handle(final HttpServerRequest request) {
		if(request.path().equals("/")){
			request.response().sendFile("WebContent/index.html");
		}
		else{
        	request.response().sendFile("WebContent/" + request.path());
		}
		
		//System.out.println("Got a request with path: "+request.path());
//		if(request.path().equals("/")){
//			request.response().sendFile("WebContent/home.html");
//		}else if(request.path().matches("/board/[a-zA-Z0-9_]*")){
//			if(request.method() == "POST"){
//				final HttpServerRequest req= request;
//				request.bodyHandler(new Handler<Buffer>(){
//					@Override
//					public void handle(Buffer buff) {
//						if(buff.toString().matches("pisara=pisara_admin")){
//							if(activeUsers.contains((req.path().substring(7)))){
//								req.response().sendFile("WebContent/home.html");
//							}
//							else{
//								req.response().putHeader("Set-Cookie", buff.toString() + ";Path=" + req.path()).sendFile("WebContent/index.html");
//							}
//						}
//						else req.response().putHeader("Set-Cookie", buff.toString() + ";Path=" + req.path()).sendFile("WebContent/index.html");
//					}
//				});
//			}
//			else request.response().sendFile("WebContent/index.html");
//		}else if(request.path().matches("/home/[a-zA-Z0-9_]*")){
//			request.response().sendFile("WebContent/home.html");
//		}
//		else if(request.path().matches("/admin")){
//			request.response().sendFile("WebContent/admin.html");
//		}
//		else{
//        	request.response().sendFile("WebContent" + request.path().replace("/board/", "/").replace("/home/", "/"));
//        }
	}

}
