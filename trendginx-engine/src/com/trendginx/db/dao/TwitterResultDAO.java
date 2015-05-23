package com.trendginx.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.trendginx.bean.TwitterResult;
import com.trendginx.db.connector.MySQLConnector;

public class TwitterResultDAO
{

	private Connection dbConnection = null;
	private PreparedStatement preparedStatement = null;
	
	
	public void insertRecord(TwitterResult result)
	{
		
		dbConnection = MySQLConnector.getConnection();

		try
		{
			preparedStatement = dbConnection.prepareStatement("insert into twitter_result(req_id,twt_timestamp,twt_sentiment,twt_lat,twt_lon,twt_post,twt_retwt_count,twt_fav_count,twt_sens,twt_username) values(?,?,?,?,?,?,?,?,?,?)");
			preparedStatement.setInt(1,result.getRequestId() );
			preparedStatement.setTimestamp(2,result.getTimestamp() );
			preparedStatement.setString(3, result.getSentiment());
			preparedStatement.setString(4, result.getLat());
			preparedStatement.setString(5, result.getLon());
			preparedStatement.setString(6, result.getComment());
			preparedStatement.setInt(7, result.getRetweetCount());
			preparedStatement.setInt(8, result.getFavCount());
			preparedStatement.setInt(9, result.getSens());
			preparedStatement.setString(10, result.getUserName());
			preparedStatement.execute();

		
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
		

				if (preparedStatement != null)
				{
					preparedStatement.close();
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
	
	
}
