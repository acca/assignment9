<%@page import="org.omg.PortableInterceptor.ForwardRequest"%>
<%@page import="it.unitn.dsantoro.a9.Message"%>
<%@page import="java.util.*, it.unitn.dsantoro.a9.model.*"
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List detail</title>
<link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body>
	<jsp:include page="/Controller">
		<jsp:param name="op" value="showList" />
	</jsp:include>

	<div id="menu">
		<div>
			<form method="post" action="Controller?op=addProduct">
				<input name="productName" type="text" autofocus> </input> <input
					type="submit" value="Add product"></input>
			</form>
		</div>
		<div>
			<a href="home.jsp"><button>Dashboard</button></a>
		</div>
	</div>

	<div id="body">
		<div class="table">
			<%
				GroceryList list = (GroceryList) request.getAttribute("list");
			%>
			<p>
				Operating on list: <b><%=list.getName()%></b>
			</p>
			<%
				Iterator<Product> pi = list.getProducts().iterator();
				while (pi.hasNext()) {
					Product prod = pi.next();
			%>
			<div class="row">
				<div class="cell details">
					<a href="product.jsp?prodId=<%=prod.getId()%>">
						<div><%=prod.toString()%></div>
					</a>
				</div>
				<div class="cell operations">
					<a href="Controller?op=deleteProd&prodId=<%=prod.getId()%>">

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