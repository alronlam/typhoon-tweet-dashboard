<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="twitter4j.*" %>
<%@ page import="twitter4j.auth.AccessToken" %>    
<%@ page import="tweet.*" %>    
<%@ page import="database.*" %> 
<%@ page import="sampler.*" %>
<%   
	TweetSampler ts = TweetSampler.getInstance();
	ts.setStatusListener(new TweetStatusListener());
	ts.sample();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Auto Refresh Header Example</title>
</head>
<body>
<center>
<h1>Philippine Typhoon Tweet Dashboard</h1>
<%
   // Set refresh, autoload time as 10 seconds
   response.setIntHeader("Refresh", 10);
  
   ClassifiedTweetsDataManager dm = new ClassifiedTweetsDataManager();
  // Tweet last = dm.getLastInsertedTweet();
   
   //if(last != null){
	   
	ArrayList<ArrayList<Tweet>> tweets = dm.getLatestTweetsInAllCategories(5);
	for(ArrayList<Tweet> tweetList: tweets){
		
		String category = "";
		if(tweetList.size() > 0){
			category = tweet.Category.getCategory(tweetList.get(0).getFinalCategory()).getDescription();
	
%>
			<div class = "categoryBox">
				<h2> <%= category %></h2>
				<% for(Tweet currTweet: tweetList){ %>
					<div class = "tweetBox">
						<div style="">
							<img src = <%= currTweet.getUserPicURL() %> ></img>
							@<%= currTweet.getUsername() %>
						</div>
						<div style="">
							<%= currTweet.getText() %>
						</div>
					</div>
				 <% } %>
			</div>
<%  
		}
	}
%>	
</center>
</body>
</html>