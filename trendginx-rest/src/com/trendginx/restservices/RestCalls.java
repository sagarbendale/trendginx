package com.trendginx.restservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.trendginx.analysis.GlobalDistribution;
import com.trendginx.analysis.LatestTweets;
import com.trendginx.analysis.OverallSentiments;
import com.trendginx.analysis.PercSensTweets;
import com.trendginx.analysis.SentimentsByWeek;
import com.trendginx.analysis.TopFavTweets;
import com.trendginx.analysis.TopNegativeWords;
import com.trendginx.analysis.TopPositiveWords;
import com.trendginx.analysis.TopReTweets;
import com.trendginx.analysis.TopSenTweets;
import com.trendginx.analysis.TopUsers;
import com.trendginx.analysis.TweetRate;
import com.trendginx.analysis.TweetRateLastDay;
import com.trendginx.analysis.TweetRateLastHour;
import com.trendginx.analysis.TweetRateLastMonth;
import com.trendginx.analysis.TweetRateLastWeek;
import com.trendginx.analysis.UserRequestDAO;

@Path("/")
public class RestCalls {

	@GET
	@Path("/getGlobalDistribution/{req_id}/{limit}")
	@Produces(MediaType.APPLICATION_JSON)

	public String getGlobalDistribution(@PathParam("req_id") Integer req_id,@PathParam("limit") Integer limit) {
		GlobalDistribution globalDistribution=new GlobalDistribution();
		String output=globalDistribution.getAnalysis(limit, req_id);
		return output;
	}
	
	@GET
	@Path("/getLatestTweets/{req_id}/{limit}")
	@Produces(MediaType.APPLICATION_JSON)

	public String getLatestTweets(@PathParam("req_id") Integer req_id,@PathParam("limit") Integer limit) {
		LatestTweets latestTweets=new LatestTweets();
		String output= latestTweets.getAnalysis(limit, req_id);
		return output;
	}
	
	@GET
	@Path("/getOverallSentiments/{req_id}")
	@Produces(MediaType.APPLICATION_JSON)

	public String getOverallSentiments(@PathParam("req_id") Integer req_id) {
		OverallSentiments overallSentiments=new OverallSentiments();
		String output= overallSentiments.getAnalysis(req_id);
		return output;
	}
	
	@GET
	@Path("/getPercSensTweets/{req_id}")
	@Produces(MediaType.APPLICATION_JSON)

	public String getPercSensTweets(@PathParam("req_id") Integer req_id) {
		PercSensTweets percSensTweets=new PercSensTweets();
		String output= percSensTweets.getAnalysis(req_id);
		return output;
	}
	
	@GET
	@Path("/getSentimentsByWeek/{req_id}")
	@Produces(MediaType.APPLICATION_JSON)

	public String getSentimentsByWeek(@PathParam("req_id") Integer req_id) {
		SentimentsByWeek sentimentsByWeek=new SentimentsByWeek();
		String output= sentimentsByWeek.getAnalysis(req_id);
		return output;
	}
	
	@GET
	@Path("/getTopFavTweets/{req_id}/{limit}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public String getTopFavTweets(@PathParam("req_id") Integer req_id,@PathParam("limit") Integer limit) {
		TopFavTweets topFavTweets=new TopFavTweets();
		String output= topFavTweets.getAnalysis(limit, req_id);
		return output;
	}

	@GET
	@Path("/getTopNegativeWords/{req_id}/{limit}")
	@Produces(MediaType.APPLICATION_JSON)

	public String getTopNegativeWords(@PathParam("req_id") Integer req_id,@PathParam("limit") Integer limit) {
		TopNegativeWords topNegativeWords=new TopNegativeWords();
		String output= topNegativeWords.getAnalysis(limit, req_id);
		return output;
	}
	
	@GET
	@Path("/getTopPositiveWords/{req_id}/{limit}")
	@Produces(MediaType.APPLICATION_JSON)

	public String getTopPositiveWords(@PathParam("req_id") Integer req_id,@PathParam("limit") Integer limit) {
		TopPositiveWords topPositiveWords=new TopPositiveWords();
		String output= topPositiveWords.getAnalysis(limit, req_id);
		return output;
	}
	
	@GET
	@Path("/getTopReTweets/{req_id}/{limit}")
	@Produces(MediaType.APPLICATION_JSON)

	public String getTopReTweets(@PathParam("req_id") Integer req_id,@PathParam("limit") Integer limit) {
		TopReTweets topReTweets=new TopReTweets();
		String output= topReTweets.getAnalysis(limit, req_id);
		return output;
	}
	
	@GET
	@Path("/getTopSenTweets/{req_id}/{limit}")
	@Produces(MediaType.APPLICATION_JSON)

	public String getTopSenTweets(@PathParam("req_id") Integer req_id,@PathParam("limit") Integer limit) {
		TopSenTweets topSenTweets=new TopSenTweets();
		String output=topSenTweets.getAnalysis(limit, req_id);
		return output;
	}
	
	@GET
	@Path("/getTopUsers/{req_id}/{limit}")
	@Produces(MediaType.APPLICATION_JSON)

	public String getTopUsers(@PathParam("req_id") Integer req_id,@PathParam("limit") Integer limit) {
		TopUsers topUsers=new TopUsers();
		String output=topUsers.getAnalysis(limit, req_id);
		return output;
	}
	
	@GET
	@Path("/getTweetRateLastDay/{req_id}")
	@Produces(MediaType.APPLICATION_JSON)

	public String getTweetRateLastDay(@PathParam("req_id") Integer req_id) {
		TweetRateLastDay tweetRateLastDay=new TweetRateLastDay();
		String output=tweetRateLastDay.getAnalysis(req_id);
		return output;
	}
	
	@GET
	@Path("/getTweetRateLastHour/{req_id}")
	@Produces(MediaType.APPLICATION_JSON)

	public String getTweetRateLastHour(@PathParam("req_id") Integer req_id) {
		TweetRateLastHour tweetRateLastHour=new TweetRateLastHour();
		String output=tweetRateLastHour.getAnalysis(req_id);
		return output;
	}
	
	@GET
	@Path("/getTweetRateLastMonth/{req_id}")
	@Produces(MediaType.APPLICATION_JSON)

	public String getTweetRateLastMonth(@PathParam("req_id") Integer req_id) {
		TweetRateLastMonth tweetRateLastMonth=new TweetRateLastMonth();
		String output=tweetRateLastMonth.getAnalysis(req_id);
		return output;
	}
	
	@GET
	@Path("/getTweetRateLastWeek/{req_id}")
	@Produces(MediaType.APPLICATION_JSON)

	public String getTweetRateLastWeek(@PathParam("req_id") Integer req_id) {
		TweetRateLastWeek tweetRateLastWeek=new TweetRateLastWeek();
		String output=tweetRateLastWeek.getAnalysis(req_id);
		return output;
	}
	
	@GET
	@Path("/getTweetRate/{req_id}")
	@Produces(MediaType.APPLICATION_JSON)

	public String getTweetRate(@PathParam("req_id") Integer req_id) {
		TweetRate tweetRate=new TweetRate();
		String output=tweetRate.getAnalysis(req_id);
		return output;
	}
	
	@GET
	@Path("/submitRequest/{hashtag}/{email}")
	@Produces(MediaType.TEXT_HTML)

	public String submitRequest(@PathParam("hashtag") String hashtag,@PathParam("email") String email) 
	{
		UserRequestDAO userdao=new UserRequestDAO();
		userdao.insertRequest(hashtag, email);
		return "Request Submitted";
	}
	

}


