package com.trendginx.analysis;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.trendginx.db.connector.MySQLConnector;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

public class SentimentsByWeek
{
	private Connection dbConnection = null;
	private PreparedStatement posStatement = null;
	private PreparedStatement negStatement = null;
	private PreparedStatement neutStatement = null;
	ArrayList<String> weekstart = new ArrayList<String>();
	ArrayList<String> weekend = new ArrayList<String>();
	ArrayList<Integer> pos = new ArrayList<Integer>();
	ArrayList<Integer> neg = new ArrayList<Integer>();
	ArrayList<Integer> neut = new ArrayList<Integer>();

	public String getAnalysis(int req_id)
	{
		//min, max dates available
		Date maxdate = getMaxDate(req_id);
		Date mindate = getMinDate(req_id);
		
		//converting to local date
		LocalDate refdate = maxdate.toLocalDate();
		LocalDate lowdate = mindate.toLocalDate();

		//Constructing fields for JSON object
		LocalDate monday = refdate.with(previousOrSame(MONDAY));
		LocalDate sunday = refdate.with(nextOrSame(SUNDAY));
		weekstart.add(monday.toString());
		weekend.add(sunday.toString());
		getRecords(monday,sunday,req_id);
		refdate = refdate.minusWeeks(1);

		while(refdate.isAfter(lowdate))
		{
			monday = refdate.with(previousOrSame(MONDAY));
			sunday = refdate.with(nextOrSame(SUNDAY));
			weekstart.add(monday.toString());
			weekend.add(sunday.toString());
			getRecords(monday,sunday,req_id);
			refdate = refdate.minusWeeks(1);
		}	

		//creating json object
		JSONObject res = getJSON();
		return res.toString();
	}

	private Date getMaxDate(int req_id) {
		dbConnection = MySQLConnector.getConnection();
		Date max = null;
		PreparedStatement maxStatement = null;
		try
		{
			maxStatement = dbConnection.prepareStatement("SELECT twt_timestamp FROM trendginx.twitter_result where req_id="+req_id+" order by twt_timestamp desc limit 1");
			ResultSet posResult = maxStatement.executeQuery();
			if(posResult.next())
			{
				max = posResult.getDate(1);
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
				if (maxStatement!= null)
				{
					maxStatement.close();
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
		return max;
	}

	private Date getMinDate(int req_id) {
		dbConnection = MySQLConnector.getConnection();
		Date min = null;
		PreparedStatement minStatement = null;
		try
		{
			minStatement = dbConnection.prepareStatement("SELECT twt_timestamp FROM trendginx.twitter_result where req_id="+req_id+" order by twt_timestamp asc limit 1");
			ResultSet posResult = minStatement.executeQuery();
			if(posResult.next())
			{
				min = posResult.getDate(1);
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
				if (minStatement!= null)
				{
					minStatement.close();
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
		return min;
	}

	public void getRecords(LocalDate monday, LocalDate sunday, int req_id)
	{
		dbConnection = MySQLConnector.getConnection();
		try
		{
			posStatement = dbConnection.prepareStatement("select COUNT(*) from twitter_result where req_id="+req_id+" AND twt_sentiment like 'positive' AND twt_timestamp BETWEEN '"+monday+"' AND '"+sunday+"';");
			negStatement = dbConnection.prepareStatement("select COUNT(*) from twitter_result where req_id="+req_id+" AND twt_sentiment like 'negative' AND twt_timestamp BETWEEN '"+monday+"' AND '"+sunday+"';");
			neutStatement = dbConnection.prepareStatement("select COUNT(*) from twitter_result where req_id="+req_id+" AND twt_sentiment like 'neutral' AND twt_timestamp BETWEEN '"+monday+"' AND '"+sunday+"';");
			ResultSet posResult = posStatement.executeQuery();
			ResultSet negResult = negStatement.executeQuery();
			ResultSet neutResult = neutStatement.executeQuery();

			if(posResult.next())
			{
				pos.add(posResult.getInt(1));
			}
			if(negResult.next())
			{
				neg.add(negResult.getInt(1));
			}
			if(neutResult.next())
			{
				neut.add(neutResult.getInt(1));
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
				if (posStatement != null ||negStatement !=null || neutStatement !=null)
				{
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
		JSONArray sentimentsbyweek = new JSONArray();
		JSONObject sentiment = new JSONObject();
		for(int index=0; index<pos.size();index++)
		{
			sentiment.put("weekstartdate",weekstart.get(index) );
			sentiment.put("weekenddate",weekend.get(index) );
			sentiment.put("positive",pos.get(index) );
			sentiment.put("negative",neg.get(index) );
			sentiment.put("neutral",neut.get(index) );
			sentimentsbyweek.add(sentiment);
		}
		jsResult.put("sentimentsbyweek", sentimentsbyweek);
		return jsResult;
	}

}
