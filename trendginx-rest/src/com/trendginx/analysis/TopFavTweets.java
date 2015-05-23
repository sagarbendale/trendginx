package com.trendginx.analysis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.trendginx.db.connector.MySQLConnector;

public class TopFavTweets
{
	private Connection dbConnection = null;
	private PreparedStatement topStatement = null;
	ArrayList<Integer> cnt = new ArrayList<Integer>();
	ArrayList<String> tweet = new ArrayList<String>();
	
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
			topStatement = dbConnection.prepareStatement("SELECT twt_post, twt_fav_count from trendginx.twitter_result "
					+ "WHERE req_id=? "
					+ "order by twt_fav_count desc limit ?");
			topStatement.setInt(1,req_id );
			topStatement.setInt(2,limit );
			ResultSet topResult = topStatement.executeQuery();
			while(topResult.next())
			{
				tweet.add(topResult.getString(1));
				cnt.add(topResult.getInt(2));
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
		JSONArray favtweets = new JSONArray();
		JSONObject twt = new JSONObject();
		JSONObject jsResult = new JSONObject();
		for (int index = 0; index<cnt.size(); index++)
		{
			twt.put("tweet", tweet.get(index));
			twt.put("favcount", cnt.get(index));
			favtweets.add(twt);
		}
		jsResult.put("favtweets", favtweets);
		return jsResult;
	}
}
