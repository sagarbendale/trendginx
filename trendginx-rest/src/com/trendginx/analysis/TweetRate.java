package com.trendginx.analysis;


import net.sf.json.JSONObject;

public class TweetRate {
	private int rateDayLast = 0;
	private int rateHourLast = 0;
	private int rateMonthLast = 0;
	private int rateWeekLast = 0;
	private int rateDay = 0;
	private int rateHour = 0;
	private int rateMonth = 0;
	private int rateWeek = 0;
	private int rateDayProj = 0;
	private int rateHourProj = 0;
	private int rateMonthProj = 0;
	private int rateWeekProj = 0;

	public String getAnalysis(int req_id)
	{	
		getRecords(req_id);
		getLastRecords(req_id);
		getProjected();
		JSONObject res = getJSON();
		return res.toString();
	}

	private void getRecords(int req_id) {
		TweetRateCurrentDay twcd = new TweetRateCurrentDay();
		TweetRateCurrentHour twch = new TweetRateCurrentHour();
		TweetRateCurrentWeek twcw = new TweetRateCurrentWeek();
		TweetRateCurrentMonth twcm = new TweetRateCurrentMonth();
		rateDay = twcd.getCnt(req_id);
		rateHour = twch.getCnt(req_id);
		rateMonth = twcm.getCnt(req_id);
		rateWeek = twcw.getCnt(req_id);
	}

	private void getLastRecords(int req_id) {
		TweetRateLastDay twld = new TweetRateLastDay();
		TweetRateLastHour twlh = new TweetRateLastHour();
		TweetRateLastMonth twlm = new TweetRateLastMonth();
		TweetRateLastWeek twlw = new TweetRateLastWeek();
		rateDayLast = twld.getCnt(req_id);
		rateHourLast = twlh.getCnt(req_id);
		rateMonthLast = twlm.getCnt(req_id);
		rateWeekLast = twlw.getCnt(req_id);
	}

	private void getProjected()
	{
		rateDayProj = getPrediction(rateDay, rateDayLast);
		rateHourProj = getPrediction(rateHour, rateHourLast);
		rateMonthProj = getPrediction(rateMonth, rateMonthLast);
		rateWeekProj = getPrediction(rateWeek, rateWeekLast);

	}

	public int getPrediction(int current_value, int last_value) {
		int change_rate=(current_value-last_value);
		int change_constant=current_value-change_rate;
		int predicted_value=(change_rate*2)+change_constant;
		if(predicted_value<0)
			return 0;
		return predicted_value;
	}

	private JSONObject getJSON() {
		JSONObject jsResult = new JSONObject();
		jsResult.put("ratelastday", rateDayLast);
		jsResult.put("ratelasthour", rateHourLast);
		jsResult.put("ratelastmonth", rateMonthLast);
		jsResult.put("ratelastweek", rateWeekLast);
		jsResult.put("ratecurrentday", rateDay);
		jsResult.put("ratecurrenthour", rateHour);
		jsResult.put("ratecurrentmonth", rateMonth);
		jsResult.put("ratecurrentweek", rateWeek);
		jsResult.put("ratenextday", rateDayProj);
		jsResult.put("ratenexthour", rateHourProj);
		jsResult.put("ratenextmonth", rateMonthProj);
		jsResult.put("ratenextweek", rateWeekProj);
		return jsResult;
	}

}
