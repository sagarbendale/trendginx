package com.trendginx.twitter;

import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Test
{
	public static void main(String[] args) throws Exception
	{

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("5GMWrJCwx3E8QTvh1FY8jHlpW").setOAuthConsumerSecret("qC3OxcssQcozdJX47IC9rUenKWfpXGDWQ24VgtvcQDL107LoNM").setOAuthAccessToken("2774039850-yJfBlyPXX4pF4lOa45nJyQvRknDhJoUjS1514s8").setOAuthAccessTokenSecret("ceSLf11rtR3VUGcB693ZCaA77asbQAJX5mY3x0onLG5Kb");

		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		Query query = new Query("#world");
		int numberOfTweets = 5000;
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
				System.out.println("Gathered " + tweets.size() + " tweets" + "\n");
				for (Status t : tweets)
					if (t.getId() < lastID)
						lastID = t.getId();

			}

			catch (TwitterException te)
			{
				System.out.println("Couldn't connect: " + te);
			}
			;
			query.setMaxId(lastID - 1);
		}

		for (int i = 0; i < tweets.size(); i++)
		{
			Status t = (Status) tweets.get(i);

			// GeoLocation loc = t.getGeoLocation();

			String user = t.getUser().getScreenName();
			String msg = t.getText();
			
			// String time = "";
			// if (loc!=null) {
			// Double lat = t.getGeoLocation().getLatitude();
			// Double lon = t.getGeoLocation().getLongitude();*/
			System.out.println(i + " USER: " + user + " wrote: " + msg + "\n");
		}
		// else
		// System.out.println(i + " USER: " + user + " wrote: " + msg+"\n");
	}
}