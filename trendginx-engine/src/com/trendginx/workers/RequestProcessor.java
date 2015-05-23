package com.trendginx.workers;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trendginx.bean.EmailDetails;
import com.trendginx.bean.TwitterResult;
import com.trendginx.bean.UserRequest;
import com.trendginx.cfg.AppConfiguration;
import com.trendginx.db.dao.TwitterResultDAO;
import com.trendginx.db.dao.UserRequestDAO;
import com.trendginx.email.SendMailSSL;
import com.trendginx.nlp.NLP;
import com.trendginx.nlp.TweetMiner;
import com.trendginx.twitter.TwitterProcessor;

public class RequestProcessor implements Runnable
{

	static Logger logger = LoggerFactory.getLogger(RequestProcessor.class);

	public void run()
	{
		UserRequest userRequest = null;
		TwitterResultDAO twtDAO= null;
		TwitterProcessor twtProcessor = null;
		NLP nlp= null;
		ArrayList<TwitterResult> twtResult=null;
		try
		{
			while (true)
			{
				userRequest = UserRequestDAO.getPendingUserRequest();

				if (userRequest != null)
				{
					twtDAO = new TwitterResultDAO();
					twtProcessor = new TwitterProcessor();
					nlp = new NLP();
					UserRequestDAO.updateJobState(userRequest.getRequestID(), "Loading Tweets");
					logger.info("Loading Tweets for Request "+userRequest.getRequestID());
					twtResult = twtProcessor.getResult(userRequest.getRequestID(),userRequest.getTwitterHashTag(), AppConfiguration.getTwtLimit());
					logger.info("NLP for Request "+userRequest.getRequestID());
					UserRequestDAO.updateJobState(userRequest.getRequestID(), "Performing NLP on Tweets");
					twtResult = nlp.process(twtResult);
					UserRequestDAO.updateJobState(userRequest.getRequestID(), "Inserting Result");
					for (TwitterResult twt : twtResult)
					{
						logger.info("Inserting Processed Tweet for Request "+userRequest.getRequestID()+" "+twt.toString());
						twtDAO.insertRecord(twt);
					}
					
					new UserRequestDAO().updateTwitterJobStatus(userRequest.getRequestID(), 1);
					ArrayList<String> ignoreList=new ArrayList<String>();
					ignoreList.add(userRequest.getTwitterHashTag());
					TweetMiner tweetMiner =new TweetMiner();
					UserRequestDAO.updateJobState(userRequest.getRequestID(), "Performing Data Mining on Tweets");
					tweetMiner.proceess(userRequest.getRequestID(), ignoreList);	
					EmailDetails email=new EmailDetails();
					email.setFrom("TrendGinX@gmail.com");
					email.setTo(userRequest.getUser_email());
					email.setSubject("TrendGinX- Your Request has been completed");
					email.setBody("Thank you for using trendGinX. Please click on  the link to open dashboard : "+AppConfiguration.getUrl()+"?request="+userRequest.getRequestID());
					SendMailSSL smail=new SendMailSSL();
					smail.send(AppConfiguration.getGmailUsername(), AppConfiguration.getGmailPassword(), email);
					UserRequestDAO.updateJobState(userRequest.getRequestID(), "Request Completed.");
					UserRequestDAO.updateJobStatus(userRequest.getRequestID(), 2);
				}
				
				Thread.sleep(2000);
			}
		}
		catch (Exception e)
		{
			logger.error(e.toString());
		}
	}

}
