package com.trendginx.util;



import java.util.Properties;
import java.io.*;

public class PropertyFileParser 
{
	String PROP_FILE;
	public PropertyFileParser(String filename)
	{
		PROP_FILE=filename;
	}
	
	public String getValue(String propKey) 
	{
		String propValue=null;
		Properties prop=null;
		try 
		{
			prop = new Properties();
			prop.load(new FileInputStream(PROP_FILE));
			propValue = prop.getProperty(propKey);
			


		} 
		catch (Exception e) 
		{
			
			
		}
		finally
		{
			prop=null;
		}
		return propValue;
	}
	public void WriteProperty(String key,String value)
	{
		Properties pro =null;
		File f=null;
		try 
		{
			  pro = new Properties();
			  f = new File(PROP_FILE);
			  FileInputStream in = new FileInputStream(f);
			  pro.load(in);
			  pro.setProperty(key, value);
			  pro.store(new FileOutputStream(PROP_FILE),null);
		} 
		catch (FileNotFoundException e) 
		{
		} 
		catch (IOException e) 
		{
			
		}
		finally
		{
			pro=null;
		}
	}
}
