package com.trendginx.analysis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.trendginx.db.connector.MySQLConnector;

public class TopPositiveWords
{

	private Connection dbConnection = null;
	private PreparedStatement topStatement = null;
	ArrayList<String> word = new ArrayList<String>();
	ArrayList<Integer> cnt = new ArrayList<Integer>();
	
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
			topStatement = dbConnection.prepareStatement("SELECT * FROM trendginx.top_pos where req_id = ? order by count desc limit ?");
			topStatement.setInt(1,req_id );
			topStatement.setInt(2,limit );
			ResultSet topResult = topStatement.executeQuery();
			while(topResult.next())
			{
				word.add(topResult.getString(2));
				cnt.add(topResult.getInt(3));
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
		JSONArray positive = new JSONArray();
		JSONObject twt = new JSONObject();
		JSONObject jsResult = new JSONObject();
		for (int index = 0; index<cnt.size(); index++)
		{
			twt.put("word", word.get(index));
			twt.put("count", cnt.get(index));
			positive.add(twt);
		}
		jsResult.put("positivetweets", positive);
		return jsResult;
	}

}
