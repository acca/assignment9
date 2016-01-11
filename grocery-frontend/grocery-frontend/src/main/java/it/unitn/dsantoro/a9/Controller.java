package it.unitn.dsantoro.a9;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Queue;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.commons.collections4.queue.CircularFifoQueue;

import it.unitn.dsantoro.a9.GroceryListService;
import it.unitn.dsantoro.a9.GroceryListServiceRemote;
import it.unitn.dsantoro.a9.model.GroceryList;
import it.unitn.dsantoro.a9.model.Product;

/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InitialContext initialContext;
	private GroceryListServiceRemote gListService;
	private HashSet<String> allowedOperations = new HashSet<String>();
	private static String MSG_ATTR = "msg";
	private HashMap<HttpSession,GroceryListServiceRemote> users = null;	
	
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
		if (users == null) users = new HashMap<HttpSession,GroceryListServiceRemote>();
		initializeServlet();
		
		//initializeRemoteService();
	}
		
	private void initializeRemoteService(HttpSession userSession) {		
		System.out.println("Connecting with session id: "+userSession.getId()+" to the remote list service...");		
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
		allowedOperations.add("showList");
		allowedOperations.add("showProduct");
		allowedOperations.add("addProduct");
		allowedOperations.add("changeStatus");
		allowedOperations.add("deleteProduct");
		allowedOperations.add("decProd");
		allowedOperations.add("incProd");
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
		ArrayDeque<Message> msgQueue = (ArrayDeque<Message>) request.getSession(true).getAttribute("msgQueue");
		if (msgQueue == null) {
			msgQueue = new ArrayDeque<Message>();
			request.getSession(true).setAttribute("msgQueue", msgQueue);
		}
		msgQueue.addFirst(new Message(messageSeverity, text));		
		request.setAttribute(MSG_ATTR, msgQueue);
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
	
	private void showList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		GroceryList list = (GroceryList) request.getSession(true).getAttribute("currentList");

		String listId = request.getParameter("listId");
		boolean error = false;

		list = gListService.findGroceryList(Long.parseLong(listId));
		if (list != null) {

			message(request, Message.INFO, "Showing "+list.getProducts().size()+" products from " + list.getName());
			request.getSession(true).setAttribute("currentList", list);
			if (list.getProducts().size() == 0) {
				message(request, Message.WARNING, "No product present, please create one");
			}
			else {
				message(request, Message.INFO, "Retrieved "+ list.getProducts().size() + " products");
			}
		}
		else {
			message(request, Message.ERROR, "Not found list with id: " + listId);
			error = true;			
		}
		if (error) { 
			response.sendRedirect("home.jsp");
		}
		else {
			request.setAttribute("list", list);
			response.sendRedirect(request.getHeader("referer"));	
		}
		
	}
	
	private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String productName = request.getParameter("productName");
		if ( productName != null ) {
			if (!productName.isEmpty()) {
				GroceryList list = (GroceryList) request.getSession(true).getAttribute("currentList");
				gListService.addProduct(new Product(productName), list.getId());				
				message(request, Message.INFO, "Product "+ productName +" has been created in "+list.getName()+" list.");
			} 
			else {				
				message(request, Message.ERROR, "Product name must be present");
			}		
		}
		response.sendRedirect(request.getHeader("referer"));
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		checkUser(session);
		
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
			case "showList":
				showList(request,response);
				break;
			case "addProduct":
				addProduct(request, response);
				break;				
			case "deleteProduct":
				deleteProduct(request, response);
				break;
			case "changeStatus":
				changeStatusProduct(request, response);
				break;
			case "incProd":
				incProduct(request, response);
				break;
			case "decProd":
				decProduct(request, response);
				break;
			default:
				throw new ServletException("Invalid URI");
			}
		}
		else {
			throw new ServletException("Invalid URI");
		}	
	}
	private void decProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String prodId = request.getParameter("prodId");
		if ( prodId != null ) {
			if (!prodId.isEmpty()) {
				GroceryList gl = (GroceryList) request.getSession(true).getAttribute("currentList");
				Product p = gl.getProduct(Long.parseLong(prodId));
				int q = p.getQuantity();
				if (q > 0) {					
					p.setQuantity(q - 1);
					gListService.updateProduct(p);
					message(request, Message.INFO, "Prod "+ prodId +" quantity has been decreased from " + q + " to " + (q-1) + ".");
				}
				else {
					message(request, Message.WARNING, "Minimum quantity is 1");	
				}
		}
			else {
				message(request, Message.ERROR, "Prod id must be present");
			}
		response.sendRedirect(request.getHeader("referer"));
	}
	}
	private void incProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String prodId = request.getParameter("prodId");
		if ( prodId != null ) {
			if (!prodId.isEmpty()) {
				GroceryList gl = (GroceryList) request.getSession(true).getAttribute("currentList");
				Product p = gl.getProduct(Long.parseLong(prodId));
				int q = p.getQuantity();
				if (q < 5) {
					p.setQuantity(q + 1);
					gListService.updateProduct(p);
					message(request, Message.INFO, "Prod "+ prodId +" quantity has been increased from " + q + " to " + (q+1) + ".");
				}
				else {
					message(request, Message.WARNING, "Maximum quantity is 5");	
				}
		}
			else {
				message(request, Message.ERROR, "Prod id must be present");
			}
		response.sendRedirect(request.getHeader("referer"));
	}	
	}
	private void changeStatusProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String prodId = request.getParameter("prodId");
		if ( prodId != null ) {
			if (!prodId.isEmpty()) {
				GroceryList gl = (GroceryList) request.getSession(true).getAttribute("currentList");
				Product p = gl.getProduct(Long.parseLong(prodId));
				String newStatus = "";
				if (p.isInList()) {
					gListService.markProductAsBought(p.getId(), gl.getId());
					newStatus = "cart";
				}
				else {
					gListService.markProductToBuy(p.getId(), gl.getId());
					newStatus = "list";
				}

				message(request, Message.INFO, "Prod "+ prodId +" has been moved to " + newStatus + ".");
			} 
			else {
				message(request, Message.ERROR, "Prod id must be present");
			}
		}
		response.sendRedirect(request.getHeader("referer"));
	}
	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}
	private void checkUser(HttpSession userSession) {
		if (users.containsKey(userSession) ) {
			this.gListService = users.get(userSession);
		}
		else {
			initializeRemoteService(userSession);
			//this.hangman = new Hangman();
			users.put(userSession, gListService);
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		doGet(request, response);
	}

}
