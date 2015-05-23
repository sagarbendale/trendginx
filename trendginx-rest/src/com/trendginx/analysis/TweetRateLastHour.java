package com.trendginx.analysis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import com.trendginx.db.connector.MySQLConnector;

public class TweetRateLastHour
{
	private Connection dbConnection = null;
	private PreparedStatement topStatement = null;
	private int cnt = 0;

	public String getAnalysis(int req_id)
	{	
		getRecords(req_id);
		JSONObject res = getJSON();
		return res.toString();
	}

	public int getCnt(int req_id) {
		getRecords(req_id);
		return cnt;
	}

	public void getRecords(int req_id)
	{
		dbConnection = MySQLConnector.getConnection();
		try
		{
			topStatement = dbConnection.prepareStatement("select * from trendginx.twitter_result where req_id="+req_id+" AND twt_timestamp BETWEEN (SELECT (DATE_SUB(NOW(), INTERVAL 2 hour))) AND (SELECT (DATE_SUB(NOW(), INTERVAL 1 hour)))");
			ResultSet topResult = topStatement.executeQuery();
			while(topResult.next())
			{
				cnt = topResult.getInt(1);
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
		JSONObject jsResult = new JSONObject();
		jsResult.put("tweetcount", cnt);
		return jsResult;
	}
}
