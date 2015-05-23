package com.trendginx.analysis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.trendginx.db.connector.MySQLConnector;

public class TopSenTweets
{
	private Connection dbConnection = null;
	private PreparedStatement topStatement = null;
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
			topStatement = dbConnection.prepareStatement("SELECT twt_post from trendginx.twitter_result where req_id=? AND twt_sens=1 limit ?");
			topStatement.setInt(1,req_id);
			topStatement.setInt(2,limit );
			ResultSet topResult = topStatement.executeQuery();
			while(topResult.next())
			{
				tweet.add(topResult.getString(1));
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
		JSONArray sentweets = new JSONArray();
		JSONObject twt = new JSONObject();
		JSONObject jsResult = new JSONObject();
		for (int index = 0; index<tweet.size(); index++)
		{
			twt.put("tweet", tweet.get(index));
			sentweets.add(twt);
		}
		jsResult.put("sentweets", sentweets);
		return jsResult;
	}
}
