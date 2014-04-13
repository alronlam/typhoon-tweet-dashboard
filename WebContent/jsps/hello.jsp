<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="twitter4j.*" %>
<%@ page import="twitter4j.auth.AccessToken" %>    
<%@ page import="tweet.*" %>    
<%@ page import="database.*" %>
<%   TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
     twitterStream.addListener(new TweetStatusListener());
     AccessToken accessToken = new AccessToken("461053984-aww1IbpSVcxUE2jN8VqsOkEw8IQeEMusx4IdPM9p", "WGsbat8P8flqKqyAymnWnTnAGI5hZkgdaQSE8XALs7ZEp");
     twitterStream.setOAuthConsumer("fwbtkGf8N97yyUZyH5YzLw", "oQA5DunUy89Co5Hr7p4O2WmdzqiGTzssn2kMphKc8g");
     twitterStream.setOAuthAccessToken(accessToken);
	 twitterStream.sample();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Auto Refresh Header Example</title>
</head>
<body>
<center>
<h2>Auto Refresh Header Example</h2>
<%
   // Set refresh, autoload time as 10 seconds
   response.setIntHeader("Refresh", 10);
  
   ClassifiedTweetsDataManager dm = new ClassifiedTweetsDataManager();
   Tweet last = dm.getLastInsertedTweet();
   
   if(last != null){
%>

	<div>
		<div style="background-color: black">
			User Info
			<img src = <%= last.getUserPicURL() %> ></img>
			<%= last.getUsername() %>
		</div>
		<div style="background-color: red">
			<%= tweet.Category.getCategory(last.getFinalCategory()).getDescription()+": " %>
			<%= last.getText() %>
		</div>
	</div>
<% } %>


</center>
</body>
</html>