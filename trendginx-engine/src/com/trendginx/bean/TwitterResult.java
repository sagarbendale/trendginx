package com.trendginx.bean;

import java.sql.Timestamp;

public class TwitterResult
{

	@Override
	public String toString()
	{
		return "TwitterResult [requestId=" + requestId + ", comment=" + comment + ", timestamp=" + timestamp + ", sentiment=" + sentiment + ", lat=" + lat + ", lon=" + lon + "]";
	}
	private int requestId;
	private String comment;
	public String getComment()
	{
		return comment;
	}
	public TwitterResult()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public TwitterResult(int requestId, String comment, Timestamp timestamp, String lat, String lon)
	{
		super();
		this.requestId = requestId;
		this.comment = comment;
		this.timestamp = timestamp;
		this.lat = lat;
		this.lon = lon;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	private Timestamp timestamp;
	private String sentiment;
	private String lat;
	private String lon;
	private int retweetCount;
	private int favCount;
	private int sens;
	private String userName;
	public int getRetweetCount()
	{
		return retweetCount;
	}
	public void setRetweetCount(int retweetCount)
	{
		this.retweetCount = retweetCount;
	}
	public int getFavCount()
	{
		return favCount;
	}
	public void setFavCount(int favCount)
	{
		this.favCount = favCount;
	}

	public int getSens()
	{
		return sens;
	}
	public void setSens(int sens)
	{
		this.sens = sens;
	}

	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public int getRequestId()
	{
		return requestId;
	}
	public void setRequestId(int requestId)
	{
		this.requestId = requestId;
	}
	public Timestamp getTimestamp()
	{
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp)
	{
		this.timestamp = timestamp;
	}
	public String getSentiment()
	{
		return sentiment;
	}
	public void setSentiment(String sentiment)
	{
		this.sentiment = sentiment;
	}
	public String getLat()
	{
		return lat;
	}
	public void setLat(String lat)
	{
		this.lat = lat;
	}
	public String getLon()
	{
		return lon;
	}
	public void setLon(String lon)
	{
		this.lon = lon;
	}
}
