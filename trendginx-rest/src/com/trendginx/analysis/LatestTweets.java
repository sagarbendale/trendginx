package com.trendginx.analysis;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.trendginx.db.connector.MySQLConnector;

public class LatestTweets
{
	private Connection dbConnection = null;
	private PreparedStatement topStatement = null;
	ArrayList<String> usrs = new ArrayList<String>();
	ArrayList<String> tweet = new ArrayList<String>();
	ArrayList<Date> timestmp = new ArrayList<Date>();
	
	public String getAnalysis(int limit, int req_id)
	{	
		getRecords(limit, req_id);
		JSONObject res = getJSON();
		return res.toString();
	}

	public void getRecords(int limit, int req_id)
	{
		dbConnection = MySQLConnector.getConnection();
		try
		{
			topStatement = dbConnection.prepareStatement("SELECT twt_timestamp, twt_username, twt_post from trendginx.twitter_result where req_id=? limit ?");
			topStatement.setInt(1,req_id );
			topStatement.setInt(2,limit );
			ResultSet topResult = topStatement.executeQuery();
			while(topResult.next())
			{
				timestmp.add(topResult.getDate(1));
				usrs.add(topResult.getString(2));
				tweet.add(topResult.getString(3));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (topStatement!= null)
				{
					topStatement.close();
				}

				if (dbConnection != null)
				{
					dbConnection.close();
				}

			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public JSONObject getJSON()
	{
		JSONArray tweets = new JSONArray();
		JSONObject twt = new JSONObject();
		JSONObject jsResult = new JSONObject();
		for (int index = 0; index<tweet.size(); index++)
		{
			Date dt = timestmp.get(index);
			twt.put("tweet", tweet.get(index));
			twt.put("username", usrs.get(index));
			twt.put("timestamp", dt.getTime());
			tweets.add(twt);
		}
		jsResult.put("tweets", tweets);
		return jsResult;
	}
}
