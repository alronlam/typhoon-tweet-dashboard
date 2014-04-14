<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="twitter4j.*" %>
<%@ page import="twitter4j.auth.AccessToken" %>    
<%@ page import="tweet.*" %>    
<%@ page import="database.*" %> 
<%@ page import="sampler.*" %>
<%   
	response.setIntHeader("Refresh", 30);
	TweetSampler ts = TweetSampler.getInstance();
	ts.setStatusListener(new TweetStatusListener());
	ts.sample();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="height:100%">
<head>
<title>Typhoon Tweet Tracker</title>
</head>
<body style="height:100%">
	<div style="width:100%; height:100%; background-color:#95a5a6">
<%
   //response.setIntHeader("Refresh", 30);
   	ClassifiedTweetsDataManager dm = new ClassifiedTweetsDataManager();
	LinkedHashMap<tweet.Category, ArrayList<Tweet>> tweetMap = dm.getLatestTweetsInAllCategories(5);
	//int i =0;
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