package com.trendginx.analysis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import com.trendginx.db.connector.MySQLConnector;

public class PercSensTweets
{
	private Connection dbConnection = null;
	private PreparedStatement totStatement = null;
	private PreparedStatement sensStatement = null;
	private int tot = 0;
	private int sens = 0;

	public String getAnalysis(int req_id)
	{	
		getRecords(req_id);
		JSONObject res = getJSON();
		return res.toString();
	}

	public void getRecords(int req_id)
	{
		dbConnection = MySQLConnector.getConnection();
		try
		{
			totStatement = dbConnection.prepareStatement("select COUNT(*) from twitter_result WHERE req_id="+req_id);
			sensStatement = dbConnection.prepareStatement("select COUNT(*) from twitter_result where twt_sens=1 AND req_id="+req_id);
			ResultSet totResult = totStatement.executeQuery();
			ResultSet sensResult = sensStatement.executeQuery();
			if(totResult.next())
			{
				tot = totResult.getInt(1);
			}
			if(sensResult.next())
			{
				sens = sensResult.getInt(1);
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
				if (totStatement!= null || sensStatement != null)
				{
					totStatement.close();
					sensStatement.close();
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
		jsResult.put("totaltweets",tot );
		jsResult.put("positive",sens );
		return jsResult;
	}
}
