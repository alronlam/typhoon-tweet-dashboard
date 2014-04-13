

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DAOFactory;
import database.DataManager;
import tweet.Tweet;
import tweet.TweetStatusListener;
import twitter4j.*;
import twitter4j.auth.AccessToken;

/**
 * Servlet implementation class TweetDashboardServlet
 */
@WebServlet("/TweetDashboardServlet")
public class TweetDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String HTML_START="<html><body>";
	public static final String HTML_END="</body></html>";
	       
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TweetDashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    boolean started = false;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!started){
			 TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		     twitterStream.addListener(new TweetStatusListener());
		     AccessToken accessToken = new AccessToken("461053984-aww1IbpSVcxUE2jN8VqsOkEw8IQeEMusx4IdPM9p", "WGsbat8P8flqKqyAymnWnTnAGI5hZkgdaQSE8XALs7ZEp");
		     twitterStream.setOAuthConsumer("fwbtkGf8N97yyUZyH5YzLw", "oQA5DunUy89Co5Hr7p4O2WmdzqiGTzssn2kMphKc8g");
		     twitterStream.setOAuthAccessToken(accessToken);
		      // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
		     twitterStream.sample();
	        started = true;
		}
		
		// TODO Auto-generated method stub
		 PrintWriter out = response.getWriter();
	     Date date = new Date();
	   
	     out.println(HTML_START);
	     out.println("<h1> tweets </h1>");	     
	     out.println(HTML_END);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
