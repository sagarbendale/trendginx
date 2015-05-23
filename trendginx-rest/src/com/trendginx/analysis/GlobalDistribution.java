package com.trendginx.analysis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.trendginx.db.connector.MySQLConnector;
import com.trendginx.util.LocationGenerator;

public class GlobalDistribution {

	private Connection dbConnection = null;
	private PreparedStatement topStatement = null;
	//HashMap<String, Integer> map = new HashMap<String, Integer>();
	ArrayList<String> lat = new ArrayList<String>();
	ArrayList<String> sent = new ArrayList<String>();
	ArrayList<String> lon = new ArrayList<String>();
	
	public String getAnalysis(int limit, int req_id)
	{	
		getRecords(limit, req_id);
		JSONObject res = getJSON();
		return res.toString();
	}

	public void getRecords(int limit, int req_id)
	{
		String latitude = null;
		String longitude = null;
		dbConnection = MySQLConnector.getConnection();
		try
		{
			topStatement = dbConnection.prepareStatement("SELECT twt_lat, twt_lon, twt_sentiment from trendginx.twitter_result WHERE req_id=? limit ?");
			topStatement.setInt(1,req_id );
			topStatement.setInt(2,limit );
			ResultSet topResult = topStatement.executeQuery();
			while(topResult.next())
			{
				latitude = topResult.getString(1);
				longitude = topResult.getString(2);
				if (latitude!=null&&longitude!=null)
				{
					lat.add(latitude);
					lon.add(longitude);
				}
				else
				{
					
					setRandomLocation();
				}
				
				sent.add(topResult.getString(3));
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

	private void setRandomLocation() {
		
		LocationGenerator random = new LocationGenerator();
		lat.add(random.getLat());
		lon.add(random.getLon());
		
	}

	public JSONObject getJSON()
	{
		JSONArray tweets = new JSONArray();
		JSONObject twt = new JSONObject();
		JSONObject jsResult = new JSONObject();
		for (int index = 0; index<sent.size(); index++)
		{
			twt.put("lat", lat.get(index));
			twt.put("lon", lon.get(index));
			twt.put("sentiment", sent.get(index));
			tweets.add(twt);
		}
		jsResult.put("tweets", tweets);
		return jsResult;
	}
}
