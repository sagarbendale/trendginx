package com.trendginx.analysis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.trendginx.db.connector.MySQLConnector;

public class UserRequestDAO
{
	public void  insertRequest(String hashtag,String email)
	{
		Connection dbConnection = MySQLConnector.getConnection();
		PreparedStatement preparedStatement=null;

		try
		{
			preparedStatement = dbConnection.prepareStatement("insert into user_request(req_twitter_hashtag,req_user_email) values (?,?)");
			preparedStatement.setString(1, hashtag);
			preparedStatement.setString(2, email);
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
