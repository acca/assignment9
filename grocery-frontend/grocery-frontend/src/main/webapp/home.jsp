<%@ page import="java.util.*, it.unitn.dsantoro.a9.model.*" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body>
	<jsp:include page="/Controller" />
		<%
			//response.sendRedirect(request.getContextPath() + "/Controller?op=getUserLists");
			ArrayList<GroceryList> s = (ArrayList<GroceryList>) request.getAttribute("userLists");
			String e = (String) request.getAttribute("errorMsg");
		%>
	  
	<jsp:element name="div" id="error"><%=e%></jsp:element>
	
	<jsp:element name="div" id="menu">
		<form method="post" action="Controller?op=addList">
		<input name="listName" type="text"></input>
		<input type="submit" value="Create the list"></input>	
		</form>		
		<a href=""><button>Other operation</button></a>			
	</jsp:element>
	<div id="body"><%=s%>
	<table>
		<tr>
			<th>List id</th>
			<th>List name</th>
			<th>Products</th>
			<th>Operations</th>
		</tr>
		</table>
	</div>
	
	<jsp:element name="div" id="footer"></jsp:element>
	
	
	  
		
	
		
</body>
</html>