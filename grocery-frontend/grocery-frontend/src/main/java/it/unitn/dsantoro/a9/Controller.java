package it.unitn.dsantoro.a9;

import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		System.out.println("Testing methods on the remote list");
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
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        gListService.addGroceryList("lista da remote web application " + this);
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Collection<GroceryList> gLists = gListService.findMyGroceryLists();
		if (gLists != null) {
			request.setAttribute("userLists", gLists);	
		}
		else {
			request.setAttribute("errorMsg", "No grocery-list present, please create one.");
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
