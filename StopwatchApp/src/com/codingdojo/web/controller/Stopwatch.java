package com.codingdojo.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

import com.codingdojo.web.models.Timer;

/**
 * Servlet implementation class Stopwatch
 */
@WebServlet("/Stopwatch")
public class Stopwatch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Stopwatch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Timer base = new Timer();
		HttpSession session = request.getSession();
		
//		Start instance
		if (request.getParameter("startNow") != null) {
			base.start();
			long started = base.getCurrent();
			String formated = base.formatTime(started);
			String run = base.calcTime();
			request.setAttribute("startTime", formated);
			request.setAttribute("runningTime", run);
		}
		
//		Stop instance
		if (request.getAttribute("stopNow") == "true") {
			base.stop();
			long stopped = base.getCurrent();
			String newStart = (String)session.getAttribute("startTime");
			String newStop = base.formatTime(stopped);
			String newTotal = base.calcTime();
			ArrayList<HashMap<String, String>> history = (ArrayList<HashMap<String, String>>)session.getAttribute("history");
			if (history == null) {
				history = new ArrayList<HashMap<String, String>>();
			}
			HashMap<String, String> newRecord = new HashMap<String, String>();
			if (newStart != null) {
				newRecord.put("Start", newStart);
			}
			if (newStop != null) {
				newRecord.put("Stop", newStop);
			}
			if (newTotal != null) {
				newRecord.put("Total", newTotal);
			}
			if (!newRecord.isEmpty()) {
				history.add(newRecord);
			}
			if (!history.isEmpty()) {
				session.setAttribute("history", history);
				request.setAttribute("history", history);
			}
			request.setAttribute("startTime", newStart);
			session.setAttribute("stopTime", newStop);
			request.setAttribute("stopTime", newStop);
			session.setAttribute("running", false);
			request.setAttribute("running", false);
		}
		
//		Reset instance
		if (request.getAttribute("resetNow") == "true") {
			session.setAttribute("startTime", null);
			session.setAttribute("running", false);
		}
		
		long current = base.getCurrent();
		request.setAttribute("currentTime", base.formatTime(current));
		response.encodeRedirectURL("http://localhost:8080/Stopwatch/Stopwatch");
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/showStopwatch.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
