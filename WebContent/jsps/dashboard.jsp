<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="twitter4j.*" %>
<%@ page import="twitter4j.auth.AccessToken" %>    
<%@ page import="tweet.*" %>    
<%@ page import="database.*" %> 
<%@ page import="sampler.*" %>
<%@ page import="classifier.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="height:100%">
<head>
<title>Typhoon Tweet Tracker</title>
</head>
<body style="height:100%">
<%   
	TweetSampler ts = TweetSampler.getInstance();
	ts.setStatusListener(new TweetStatusListener());
	boolean liveStream = true;
	if(liveStream){
		response.setIntHeader("Refresh", 60);
		ts.sample();
	}
	
	//check to see if there was a tweet id entered.
	String tweetIdString = request.getParameter("tweet_id");
	
	if(tweetIdString != null && !tweetIdString.trim().isEmpty()){
		long tweetId = Long.parseLong(tweetIdString);
		Status tweet = ts.getTweetFromId(tweetId);
		if(tweet != null){
			TweetClassifierFacade classifierFacade = new TweetClassifierFacade();
			tweet.Category category = classifierFacade.addToDBIfRelevant(tweet);

			String msg = "";
			if(category == null)
				msg = "Your tweet does not belong to any of the categories.";
			else
				msg = "Your tweet belongs to '"+category.getDescription()+"'.";
			//notification here regarding the classification status
			System.out.println(msg);
%>
			<script type="text/javascript" >
	        	alert("<%= msg %>");
	    	</script>
<%
		}
	}
%>

	<div class="pull-left" style= "width:100%; height: 5%; background-color:black;">
		<form style="float:left" action="dashboard.jsp" method="GET">
			<strong style="color:#ecf0f1; margin: 10px"> Classify this tweet: </strong> <input type="text" name="tweet_id" placeholder="Tweet ID" autofocus>
			<input type="submit" value="Classify" />
		</form>
	</div>
	<div style="width:100%; height:95%; background-color:#95a5a6">
		
<%
	ClassifiedTweetsDataManager dataManager = ClassifiedTweetsDataManager.getInstance();
	LinkedHashMap<tweet.Category, ArrayList<Tweet>> tweetMap = dataManager.getLatestTweetsInAllCategories(10);
	for(Map.Entry<tweet.Category, ArrayList<Tweet>> entry: tweetMap.entrySet()){
%>
		<div class = "categoryBox" style="border:5px solid; border-color:#bdc3c7; border-radius:20px; box-shadow: 5px 5px 5px #888888;margin: 5px; float:left; width:31%; height:47%; ;background-color:#ecf0f1;">
			<div style="height:10%; background-color:#2980b9; border-radius:20px;">
				 <h2 style="color:#ecf0f1;text-align:center; margin: 0 auto;"> <%= entry.getKey().getDescription() %> </h2>
			</div>
			<div style="height:90%; overflow-y:scroll; border-radius:20px;">
			<% for(Tweet currTweet: entry.getValue()){ %>
				<blockquote data-cards="hidden" height="10" class="twitter-tweet" lang="en"><p>currTweet.getText()</p><a href= <%= currTweet.getLink() %>>Link</a></blockquote>
				<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>
			 <% } %>
			</div>
		</div>
<% 
		//i++;
	}	
%>
	</div>
			
</body>
</html>