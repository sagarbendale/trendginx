package com.trendginx.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.trendginx.bean.UserRequest;
import com.trendginx.db.connector.MySQLConnector;

public class UserRequestDAO
{
	
	
	
	


	public static synchronized UserRequest  getPendingUserRequest()
	{
		Connection dbConnection = MySQLConnector.getConnection();
		UserRequest userRequest = null;
		ResultSet resultSet=null;
		PreparedStatement preparedStatement=null;

		try
		{
			preparedStatement = dbConnection.prepareStatement("select req_id,req_user_email,req_twitter_hashtag from user_request where req_under_process=?");
			preparedStatement.setInt(1, 0);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
			{

				userRequest = new UserRequest();
				userRequest.setRequestID(resultSet.getInt("req_id"));
				userRequest.setTwitterHashTag(resultSet.getString("req_twitter_hashtag"));
				userRequest.setUser_email(resultSet.getString("req_user_email"));
				updateJobStatus(userRequest.getRequestID(),1);
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
		return userRequest;

	}
	
	public void updateTwitterJobStatus(int req,int status)
	{
		
		Connection dbConnection = MySQLConnector.getConnection();

		PreparedStatement preparedStatement=null;

		try
		{
			preparedStatement = dbConnection.prepareStatement("update user_request set req_twitter_job_status=? where req_id=?");
			preparedStatement.setInt(1, status);
			preparedStatement.setInt(2, req);
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
	
	public static synchronized void updateJobStatus(int req,int status)
	{
		Connection dbConnection = MySQLConnector.getConnection();

		PreparedStatement preparedStatement=null;

		try
		{
			preparedStatement = dbConnection.prepareStatement("update user_request set req_under_process=? where req_id=?");
			preparedStatement.setInt(1, status);
			preparedStatement.setInt(2, req);
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
	public static synchronized void updateJobState(int req,String status)
	{
		Connection dbConnection = MySQLConnector.getConnection();

		PreparedStatement preparedStatement=null;

		try
		{
			preparedStatement = dbConnection.prepareStatement("update user_request set req_state=? where req_id=?");
			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, req);
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
