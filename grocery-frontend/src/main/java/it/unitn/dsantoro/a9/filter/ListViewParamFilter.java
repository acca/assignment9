package it.unitn.dsantoro.a9.filter;

import java.io.IOException;
import java.util.ArrayDeque;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unitn.dsantoro.a9.Message;

public class ListViewParamFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
			String listId = (String) request.getParameter("listId");
			if ( (listId == null) || (listId.isEmpty()) ) {
				System.out.println("List id not present, redirecting to dashboard.");
				HttpServletRequest httpRequest = (HttpServletRequest) request;
				ArrayDeque<Message> msgQueue = (ArrayDeque<Message>) httpRequest.getSession(true).getAttribute("msgQueue");
				if (msgQueue == null) {
					msgQueue = new ArrayDeque<Message>();
					httpRequest.getSession(true).setAttribute("msgQueue", msgQueue);						
				}
				msgQueue.addFirst(new Message(Message.ERROR, "Filter redirect: from list.jsp to home.jsp, see logs for details"));						
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.sendRedirect("home.jsp");
			}
			else {
				chain.doFilter(request, response);
			}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
