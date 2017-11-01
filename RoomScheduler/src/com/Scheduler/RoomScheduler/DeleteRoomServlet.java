package com.Scheduler.RoomScheduler;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.*;
import javax.servlet.ServletException;
import javax.jdo.*;
import javax.annotation.*;

@SuppressWarnings("serial")
public class DeleteRoomServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// set the response type to be html text
		resp.setContentType("text/html");

		UserService us = UserServiceFactory.getUserService();
		User u = us.getCurrentUser();
		String login_url = us.createLoginURL("/");
		String logout_url = us.createLogoutURL("/");

		// get access to the user. if they do not exist in the datastore then
		// store a default version of them. of course we have to check that a user has logged in first
		req.setAttribute("user", u);
		req.setAttribute("login_url", login_url);
		req.setAttribute("logout_url", logout_url);
		
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/root.jsp");
		rd.forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {

		int room =0;
		try{
			room = Integer.parseInt( req.getParameter("deleteRoom"));
			
			PersistenceManagerFactory pmf = PMF.get();
			PersistenceManager pm = pmf.getPersistenceManager();
			UserRoom ur =null;
			
			Query q = pm.newQuery(UserRoom.class, "roomNum == "+room);
			q.compile();
			q.deletePersistentAll();
			pm.makePersistent(ur);
			pm.close();
			resp.sendRedirect("/");
				
		}catch(NumberFormatException e){
			resp.sendRedirect("/");
		}

		System.out.println(room);
	}
}
