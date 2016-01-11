<%@page import="it.unitn.dsantoro.a9.Message"%>
<%@page import="java.util.*, it.unitn.dsantoro.a9.model.*"
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
	%>

	<div id="menu">
		<div>
			<form method="post" action="Controller?op=addList">
				<input name="listName" type="text" autofocus></input>
				<input type="submit" value="Create the list"></input>
			</form>
		</div>
		<div>
			<a href="home.jsp" onClick="alert('Not yet implemented');"><button>Logout</button></a>
		</div>
	</div>

	<div id="body">
		<div class="table">
			<%
				ArrayList<GroceryList> userLists = (ArrayList<GroceryList>) request.getAttribute("userLists");
				Iterator<GroceryList> li = userLists.iterator();				
				while (li.hasNext()) {
					GroceryList list = li.next();
			%>
			<div class="row">
				<div class="cell details">
					<a href="list.jsp?listId=<%=list.getId()%>">
						<div><%=list.toString()%></div>
					</a>
				</div>
				<div class="cell operations">
					<a href="Controller?op=deleteList&listId=<%=list.getId()%>">

						<button>Delete</button>

					</a>
				</div>
			</div>

			<%
				}
			%>
		</div>
	</div>
	<div id="messages">
		<p>Latests operations:</p>
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
	<div id="footer"></div>
</body>
</html>