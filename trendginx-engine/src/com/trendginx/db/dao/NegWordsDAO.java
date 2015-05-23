package com.trendginx.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.trendginx.bean.WordResult;
import com.trendginx.db.connector.MySQLConnector;

public class NegWordsDAO {
	private Connection dbConnection = null;
	private PreparedStatement preparedStatement = null;
	
	
	public void insertRecord(WordResult result)
	{
		
		dbConnection = MySQLConnector.getConnection();

		try
		{
			preparedStatement = dbConnection.prepareStatement("insert into top_neg(req_id,word,count) values(?,?,?)");
			preparedStatement.setInt(1,result.getReq_id() );
			preparedStatement.setString(2,result.getWord() );
			preparedStatement.setInt(3, result.getCount());
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
