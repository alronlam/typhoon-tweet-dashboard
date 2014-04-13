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
<center> <h1>Philippine Typhoon Tweet Dashboard</h1></center>
<%
   //response.setIntHeader("Refresh", 30);
  
   	ClassifiedTweetsDataManager dm = new ClassifiedTweetsDataManager();
	LinkedHashMap<tweet.Category, ArrayList<Tweet>> tweetMap = dm.getLatestTweetsInAllCategories(5);
	int i =0;
	
%>
	<div style=" height: calc(100% - 300px);
    width:100%;
    background:green;
    overflow-y: scroll;">
<%
	for(Map.Entry<tweet.Category, ArrayList<Tweet>> entry: tweetMap.entrySet()){
		
		if(i==3){
%>
			<div style="display:block; margin: 0 auto; text-align:center">
<%		}
%>
				<div class = "categoryBox" style="display:inline-block;margin:20px;overflow-y:scroll;background-color:gray;">
					<h2> <%= entry.getKey().getDescription() %></h2>
					<% for(Tweet currTweet: entry.getValue()){ %>
						<blockquote style= "float:left" class="twitter-tweet" width ="600" height ="100" lang="en"><p>currTweet.getText()</p><a href= <%= currTweet.getLink() %>>Link</a></blockquote>
						<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>
					 <% } %>
				</div>
				
<%		if(i==3){ 
%>
			</div>
<%		}
%>		
<%  	i++;
	}
%>
	</div>
			
</body>
</html>