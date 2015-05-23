package com.trendginx.bean;

public class UserRequest
{
	private int requestID;
	private String user_email;
	public int getRequestID()
	{
		return requestID;
	}
	public void setRequestID(int requestID)
	{
		this.requestID = requestID;
	}
	public String getUser_email()
	{
		return user_email;
	}
	public void setUser_email(String user_email)
	{
		this.user_email = user_email;
	}
	public String getTwitterHashTag()
	{
		return twitterHashTag;
	}
	public void setTwitterHashTag(String twitterHashTag)
	{
		this.twitterHashTag = twitterHashTag;
	}

	private String twitterHashTag;
}
