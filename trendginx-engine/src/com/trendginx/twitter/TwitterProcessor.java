package com.trendginx.twitter;

import java.sql.Timestamp;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import com.trendginx.bean.TwitterResult;
import com.trendginx.cfg.AppConfiguration;

public class TwitterProcessor
{
	private ConfigurationBuilder cb;
	static Logger logger = LoggerFactory.getLogger(TwitterProcessor.class);

	public TwitterProcessor()
	{

		cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(AppConfiguration.getTwt_consumer_key());
		cb.setOAuthConsumerSecret(AppConfiguration.getTwt_consumer_secret());
		cb.setOAuthAccessToken(AppConfiguration.getTwt_oauth_access_token());
		cb.setOAuthAccessTokenSecret(AppConfiguration.getTwt_oauth_access_token_secret());

	}

	public Timestamp parseTimestamp(Status t)
	{
		if(t.getCreatedAt()!=null)
		{
			return new Timestamp(t.getCreatedAt().getTime());
		}
		return null;
	}
	
	public String parseLat(Status t)
	{
	
		if(t.getGeoLocation()!=null)
		{
			return Double.toString(t.getGeoLocation().getLatitude());
		}
		return null;
	}
	public String parseLon(Status t)
	{
	
		if(t.getGeoLocation()!=null)
		{
			return Double.toString(t.getGeoLocation().getLongitude());
		}
		return null;
	}
	
	public int isSensitive(Status t)
	{
		if(t.isPossiblySensitive()==true)
		{
			return 1;
		}
		return 0;
	}
	public ArrayList<TwitterResult> getResult(int requestId,String hashtag,int limit)
	{
		ArrayList<TwitterResult> results = new ArrayList<TwitterResult>();
		try
		{
			
			Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			Query query = new Query(hashtag);
			int numberOfTweets = limit;
			long lastID = Long.MAX_VALUE;
			ArrayList<Status> tweets = new ArrayList<Status>();
			while (tweets.size() < numberOfTweets)
			{
				if (numberOfTweets - tweets.size() > 100)
					query.setCount(100);
				else
					query.setCount(numberOfTweets - tweets.size());
				try
				{
					QueryResult result = twitter.search(query);
					tweets.addAll(result.getTweets());
					logger.info("Gathered " + tweets.size() + " tweets" + "\n");
					for (Status t : tweets)
						if (t.getId() < lastID)
							lastID = t.getId();

				}

				catch (TwitterException te)
				{
					logger.error("Couldn't connect: " + te);
				}
				
				query.setMaxId(lastID - 1);
			}

			for (int i = 0; i < tweets.size(); i++)
			{
				Status t = (Status) tweets.get(i);
				
				TwitterResult result=new TwitterResult();
				result.setRequestId(requestId);
				result.setComment(t.getText());
				result.setFavCount(t.getFavoriteCount());
				result.setLat(parseLat(t));
				result.setLon(parseLon(t));
				result.setRetweetCount(t.getRetweetCount());
				result.setSens(isSensitive(t));
				result.setTimestamp(parseTimestamp(t));
				result.setUserName(t.getUser().getScreenName());
				
				results.add(result);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}
}