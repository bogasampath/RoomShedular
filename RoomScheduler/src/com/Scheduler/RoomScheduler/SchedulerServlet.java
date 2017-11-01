package com.Scheduler.RoomScheduler;

import java.io.IOException;
import javax.servlet.http.*;

import java.util.*;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.jdo.Query;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;



@SuppressWarnings("serial")
public class SchedulerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		resp.setContentType("text/html");

		UserService us = UserServiceFactory.getUserService();
		User u = us.getCurrentUser();
		String login_url = us.createLoginURL("/");
		String logout_url = us.createLogoutURL("/");

		req.setAttribute("user", u);
		req.setAttribute("login_url", login_url);
		req.setAttribute("logout_url", logout_url);
		
		if(u != null){
		PersistenceManagerFactory pmf = PMF.get();
		PersistenceManager pm = pmf.getPersistenceManager();
		UserRoom ur =null;
		//Key k = KeyFactory.createKey("UserRoom", ur.roomNum());
		Query q = pm.newQuery(UserRoom.class, "room_num");
		q.compile();
		@SuppressWarnings("unchecked")
		List<UserRoom> results = (List<UserRoom>) q.execute();
		pm.close();
		resp.getWriter().println("<h3 id=\"book\">Booking Details:</h3>");
		resp.getWriter().println("<html><table id=\"table\"><tr>");
		resp.getWriter().println("<th id=\"r\">Room No.</th>");
		resp.getWriter().println("<th id=\"n\">Name</th>");
		resp.getWriter().println("<th id=\"t\">Time</th>");

		for(UserRoom p : results) {
			int x=p.roomNum();
			req.setAttribute("d", x);
			resp.getWriter().println("</tr><tr><td>"+p.roomNum()+"</td><td>"+p.name()+"</td><td>"+p.time()+
					"</td>");
		}
		resp.getWriter().println("</tr></table>");

		int roomDetails=0;
			try{
			roomDetails = Integer.parseInt(req.getParameter("getRoom"));
			}catch(Exception e){}
			pm = pmf.getPersistenceManager();
			UserRoom ur1 =null;

			Query q1 = pm.newQuery(BookingDetails.class,"roomNum=="+roomDetails);
			q1.compile();
			@SuppressWarnings("unchecked")
			List<BookingDetails> results1 = (List<BookingDetails>)q1.execute();
			pm.close();
			resp.getWriter().println("<h3 id=\"bookdetails\">Booking Details of room:"+roomDetails+"</h3>");
			resp.getWriter().println("<html><table id=\"table1\"><tr>");
			resp.getWriter().println("<th id=\"n1\">Name</th>");
			resp.getWriter().println("<th id=\"t1\">Time</th>");

			for(BookingDetails p : results1) {

				resp.getWriter().println("</tr><tr><td>"+p.name()+"</td><td>"+p.time()+"</td>");
			}
			resp.getWriter().println("</tr></table>");
		}
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/root.jsp");
		rd.forward(req, resp);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// get the new Room from the form
		resp.setContentType("text/html");

		int room_num = 0;
		String allocate = null;
		String name = null;
		String time = null;
		try{
			room_num = Integer.parseInt(req.getParameter("room_num"));
			name = req.getParameter("name");
			allocate = req.getParameter("allocate");
			time = req.getParameter("date").substring(0, 24);

		} catch(NumberFormatException e) {

			resp.sendRedirect("/");
		}

		// update the data in the datastore and then redirect to /
		PersistenceManagerFactory pmf = PMF.get();
		PersistenceManager pm = pmf.getPersistenceManager();
		UserRoom rooms;
		Key room_key = KeyFactory.createKey("UserRoom", room_num);
		String status = "Already Booked";
		String status1 = "Booked";
		try {
			rooms = pm.getObjectById(UserRoom.class, room_key);
			resp.getWriter().println(status);
			resp.sendRedirect("/");
		} catch (Exception e) {
			// will only fail if the datastore goes down as this is already in the datastore
			rooms = new UserRoom();
			rooms.setRoom_num(room_num);
			rooms.setRoomNum(room_key);
			rooms.setName(name);
			rooms.setAllocate(allocate);
			rooms.setTime(time);
			pm.makePersistent(rooms);
			resp.getWriter().println(status1);
		}
		System.out.println("room: "+room_num+" "+name+" "+allocate+" "+time);
		pm.close();

		pm = pmf.getPersistenceManager();
		UserRoom temp = pm.getObjectById(UserRoom.class, room_key);
		BookingDetails b = new BookingDetails();
		b.setParent(temp);
		b.setRoomNum(room_num);
		b.setName(name); 
		b.setTime(time);

		temp.allocateRoom(b);
		pm.makePersistent(b); 
		pm.makePersistent(temp);
		pm.close();

		resp.sendRedirect("/");
	}
}
