package com.example.phunmasterdetail.util;

public class ScheduleItem {

	private String end_date;
	private String start_date;

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public ScheduleItem() {

	}

	public ScheduleItem(String startDate, String endDate) {
		start_date = startDate;
		end_date = endDate;
	}

}