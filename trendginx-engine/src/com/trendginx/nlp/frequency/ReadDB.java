package com.trendginx.nlp.frequency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.trendginx.db.connector.MySQLConnector;




public class ReadDB {
	Connection dbConnection = MySQLConnector.getConnection();
	ResultSet resultSet=null;
	PreparedStatement preparedStatement=null;
	ArrayList<String> tweets=null;
	
	public ArrayList<String> readNegativeTweetFromReqID(int id)
	{
		try
		{
			preparedStatement = dbConnection.prepareStatement("select twt_post,(twt_retwt_count+twt_fav_count) AS total_count from twitter_result where req_id=? and twt_sentiment='Negative'");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			tweets=new ArrayList<String>();
			while (resultSet.next())
			{
				tweets.add(resultSet.getString(1)+"-->count"+resultSet.getInt(2));
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
				if (resultSet != null)
				{
					resultSet.close();
				}

				if (preparedStatement != null)
				{
					preparedStatement.close();
				}

			}
			catch (SQLException e)
			{

				e.printStackTrace();
			}
		}
		return tweets;

		
	}
	
	public ArrayList<String> readPositiveTweetFromReqID(int id)
	{
		

		try
		{
			preparedStatement = dbConnection.prepareStatement("select twt_post,(twt_retwt_count+twt_fav_count) AS total_count from twitter_result where req_id=? and twt_sentiment='Positive'");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			tweets=new ArrayList<String>();
			while (resultSet.next())
			{
				tweets.add(resultSet.getString(1)+"-->count"+resultSet.getInt(2));
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
				if (resultSet != null)
				{
					resultSet.close();
				}

				if (preparedStatement != null)
				{
					preparedStatement.close();
				}

			}
			catch (SQLException e)
			{

				e.printStackTrace();
			}
		}
		return tweets;
	}
}
