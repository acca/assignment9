package it.unitn.dsantoro.a9;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Queue;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import it.unitn.dsantoro.a9.GroceryListService;
import it.unitn.dsantoro.a9.GroceryListServiceRemote;
import it.unitn.dsantoro.a9.model.GroceryList;

/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InitialContext initialContext;
	private GroceryListServiceRemote gListService;
	private HashSet<String> allowedOperations = new HashSet<String>();
	private static String MSG_ATTR = "msg";
	Queue<Message> msgQueue = new CircularFifoQueue<Message>(3);
	
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public void init() throws ServletException {
		super.init();
		
		initializeServlet();
		
		initializeRemoteService();
	}
		
	private void initializeRemoteService() {		
		System.out.println("Connecting to the remote list service...");		
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.remote.client.InitialContextFactory");
        jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");        
        jndiProps.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8081");
        //This property is important for remote resolving
        jndiProps.put("jboss.naming.client.ejb.context", true);
        //This propert is not important for remote resolving
        jndiProps.put("org.jboss.ejb.client.scoped.context", true);
    
        // username
        jndiProps.put(Context.SECURITY_PRINCIPAL, "user");
        // password
        jndiProps.put(Context.SECURITY_CREDENTIALS, "pw");
        
        try {
			initialContext = new InitialContext(jndiProps);
			gListService = (GroceryListServiceRemote) initialContext.lookup("java:grocery-backend/GroceryListService!it.unitn.dsantoro.a9.GroceryListServiceRemote");
			System.out.println("Succesfully connected.");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private void initializeServlet() {
		allowedOperations.add("showDashboard");
		allowedOperations.add("addList");
		allowedOperations.add("deleteList");		
	}

	
	private void addList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String listName = request.getParameter("listName");		
		if ( listName != null ) {
			if (!listName.isEmpty()) {
				gListService.addGroceryList(listName);
				message(request, Message.INFO, "List "+ listName +" has been created.");
			} 
			else {				
				message(request, Message.ERROR, "List name must be present");
			}		
		}
		response.sendRedirect(request.getHeader("referer"));
	}
	
	private void showDashboard(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Collection<GroceryList> gLists = gListService.findMyGroceryLists();
		if (!gLists.isEmpty()) {
			message(request, Message.INFO, "Retrieved "+ gLists.size() +" lists");			
		}
		else {
			message(request, Message.WARNING, "No grocery-list present, please create one.");			
		}
		request.setAttribute("userLists", gLists);
		response.sendRedirect(request.getHeader("referer"));
	}	
	
	private void message(HttpServletRequest request, String messageSeverity, String text) {
		msgQueue.add(new Message(messageSeverity, text));
		request.setAttribute(MSG_ATTR, msgQueue); 		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String operation = request.getParameter("op");
    	//String json = "";
    	//PrintWriter out = response.getWriter();

    	if (allowedOperations.contains(operation)) {    		
    		switch (operation) {
    		case "showDashboard":
    			showDashboard(request,response);	
    			break;
    		case "addList":
				addList(request,response);
    			break;
    		case "deleteList":
				deleteList(request,response);
    			break;
    		default:
    			throw new ServletException("Invalid URI");
    		}
    	}
    	else {
    		throw new ServletException("Invalid URI");
    	}	
	}

	
	
	private void deleteList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String listId = request.getParameter("listId");		
		if ( listId != null ) {
			if (!listId.isEmpty()) {
				gListService.delGroceryList(Long.parseLong(listId));
				message(request, Message.INFO, "List "+ listId +" has been deleted.");
			} 
			else {				
				message(request, Message.ERROR, "List id must be present");
			}		
		}
		response.sendRedirect(request.getHeader("referer"));		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		doGet(request, response);
	}

}
