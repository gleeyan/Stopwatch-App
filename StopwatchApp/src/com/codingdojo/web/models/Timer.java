package com.codingdojo.web.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
//	Attributes
	private long startTime;
	private long stopTime;
	private boolean running;
	
//	Constructor
	public Timer() {
		this.startTime = 0;
		this.stopTime = 0;
		this.running = false;
	}
	
//	Getters
	public long getCurrent() {
		return System.currentTimeMillis();
	}
	public long getStart() {
		return this.startTime;
	}
	public long getStop() {
		return this.stopTime;
	}
	private boolean getRunning() {
		return this.running;
	}
	private int getMinutes(long total) {
		return (this.getSeconds(total) / 60);
	}
	private int getSeconds(long total) {
		return (int)(Math.floor(total / 1000) % 60);
	}
	public int getMilliseconds(long total) {
		return (int)(total % 1000);
	}
	
//	Setters
	public void setStart(long start) {
		this.startTime = start;
	}
	public void start() {
		this.startTime = System.currentTimeMillis();
		this.running = true;
	}
	public void stop() {
		this.stopTime = System.currentTimeMillis();
		this.running = false;
	}
	
//	Methods
	public long getElapsedTimeMilli() {
		long elapsed;
		if (this.getRunning()) {
			elapsed = (System.currentTimeMillis() - this.getStart());
		} else {
			elapsed = (this.getStop() - this.getStart());
		}
		return elapsed;
	}
	public String calcTime() {
		long total = this.getElapsedTimeMilli();
		int min = this.getMinutes(total);
		int sec = this.getSeconds(total);
		int mil = this.getMilliseconds(total);
		String result = "";
		if (min < 10) {
			result += "0" + min + "m:";
		} else {
			result += min + "m:";
		}
		result += sec + "s:";
		result += mil + "ml";
		return result;
	}
	public String formatTime(long millis) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
		Date result = new Date(millis);
		return sdf.format(result);
	}
}
