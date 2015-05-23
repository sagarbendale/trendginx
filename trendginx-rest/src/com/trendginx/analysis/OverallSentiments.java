package com.trendginx.analysis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import com.trendginx.db.connector.MySQLConnector;

public class OverallSentiments
{
	private Connection dbConnection = null;
	private PreparedStatement totStatement = null;
	private PreparedStatement posStatement = null;
	private PreparedStatement negStatement = null;
	private PreparedStatement neutStatement = null;
	private int tot = 0;
	private int pos = 0;
	private int neg = 0;
	private int neut = 0;

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
			posStatement = dbConnection.prepareStatement("select COUNT(*) from twitter_result where twt_sentiment like 'positive' AND req_id="+req_id);
			negStatement = dbConnection.prepareStatement("select COUNT(*) from twitter_result where twt_sentiment like 'negative' AND req_id="+req_id);
			neutStatement = dbConnection.prepareStatement("select COUNT(*) from twitter_result where twt_sentiment like 'neutral' AND req_id="+req_id);
			ResultSet totResult = totStatement.executeQuery();
			ResultSet posResult = posStatement.executeQuery();
			ResultSet negResult = negStatement.executeQuery();
			ResultSet neutResult = neutStatement.executeQuery();
			if(totResult.next())
			{
				tot = totResult.getInt(1);
			}
			if(posResult.next())
			{
				pos = posResult.getInt(1);
			}
			if(negResult.next())
			{
				neg = negResult.getInt(1);
			}
			if(neutResult.next())
			{
				neut = neutResult.getInt(1);
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
				if (totStatement!= null || posStatement != null ||negStatement !=null || neutStatement !=null)
				{
					totStatement.close();
					posStatement.close();
					negStatement.close();
					neutStatement.close();
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
		jsResult.put("positive",pos );
		jsResult.put("negative",neg );
		jsResult.put("neutral",neut );
		return jsResult;
	}

}
