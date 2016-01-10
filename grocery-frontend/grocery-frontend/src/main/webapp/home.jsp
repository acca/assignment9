<%@page import="it.unitn.dsantoro.a9.Message"%>
<%@ page import="java.util.*, it.unitn.dsantoro.a9.model.*"
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body>
	<jsp:include page="/Controller">
		<jsp:param name="op" value="showDashboard" />
	</jsp:include>
	<%
		//response.sendRedirect(request.getContextPath() + "/Controller?op=getUserLists");
		ArrayList<GroceryList> s = (ArrayList<GroceryList>) request.getAttribute("userLists");
	%>


	<div id="error">
	<%
		Queue<Message> msgQueue = (Queue<Message>) request.getAttribute("msg");
		Iterator<Message> mi = msgQueue.iterator();
		while (mi.hasNext()) {
			Message msg = mi.next();
	%>
	<%=msg.toString()%>
	<%
		}
	%>
	</div>

	<jsp:element name="div" id="menu">
		<form method="post" action="Controller?op=addList">
		<input name="listName" type="text"></input>
		<input type="submit" value="Create the list"></input>	
		</form>
	</jsp:element>

	<div id="body">
		<table>
			<tr>
				<th>List id</th>
				<th>List name</th>
				<th>Products</th>
				<th>Operations</th>
			</tr>
		</table>
		<%=s%>
	</div>

	<jsp:element name="div" id="footer"></jsp:element>






</body>
</html>