package com.trendginx.analysis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.trendginx.db.connector.MySQLConnector;

public class TopUsers
{

	private Connection dbConnection = null;
	private PreparedStatement topStatement = null;
	ArrayList<String> usrs = new ArrayList<String>();
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
			topStatement = dbConnection.prepareStatement("SELECT twt_username,cnt  FROM "
					+ "(select twt_username, count(*) as cnt from trendginx.twitter_result "
					+ "where req_id=? "
					+ "group by twt_username "
					+ "order by count(*) desc) "
					+ "as A LIMIT ?");
			topStatement.setInt(1,req_id );
			topStatement.setInt(2,limit );
			ResultSet topResult = topStatement.executeQuery();
			while(topResult.next())
			{
				usrs.add(topResult.getString(1));
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
		JSONArray users = new JSONArray();
		JSONObject user = new JSONObject();
		JSONObject jsResult = new JSONObject();
		for (int index = 0; index<cnt.size(); index++)
		{
			user.put("username",usrs.get(index));
			user.put("count", cnt.get(index));
			users.add(user);
		}
		jsResult.put("users", users);
		return jsResult;
	}
}
