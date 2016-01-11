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
		<div>
			<a href="home.jsp" onClick="alert('Not yet implemented');"><button>Logout</button></a>
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
			<div id=#list>
				<%
					Iterator<Product> pi = list.getProducts(true).iterator();
					while (pi.hasNext()) {
						Product prod = pi.next();
				%>
				<div class="row">
					<div class="cell details">

						<div class="name">
							<a href="product.jsp?prodId=<%=prod.getId()%>"> <%=prod.getName()%>
						</div>
						</a>
						<div class="detail">
							Id:
							<%=prod.getId()%>, Quantity:
							<%=prod.getQuantity()%>, Price:
							<%=prod.getPricePerUnit()%></div>
					</div>
					<div class="cell operations">
						<a href="Controller?op=deleteProd&prodId=<%=prod.getId()%>">
							<button>Delete</button>
						</a> <a href="Controller?op=changeStatus&prodId=<%=prod.getId()%>">

							<button>In cart</button>
						</a>
						<a href="Controller?op=incProd&prodId=<%=prod.getId()%>">
						<button>Increase</button>
						</a>
						<a href="Controller?op=decProd&prodId=<%=prod.getId()%>">
						<button>Decrease</button>
						</a>
						</a>
					</div>
				</div>

				<%
					}
				%>
			</div>
			<details id=#cart open> <summary> Product in my
			cart </summary> <%
 	pi = list.getProducts(false).iterator();
 	while (pi.hasNext()) {
 		Product prod = pi.next();
 				%>
			<div class="row">
				<div class="cell details">

					<div class="name">
						<a href="product.jsp?prodId=<%=prod.getId()%>"> <%=prod.getName()%>
					</div>
					</a>
					<div class="detail">
						Id:
						<%=prod.getId()%>, Quantity:
						<%=prod.getQuantity()%>, Price:
						<%=prod.getPricePerUnit()%></div>
				</div>
				<div class="cell operations">
					<a href="Controller?op=deleteProd&prodId=<%=prod.getId()%>">
						<button>Delete</button> <a
						href="Controller?op=changeStatus&prodId=<%=prod.getId()%>">
							<button>In list</button>
					</a>
					</a>
				</div>
			</div>
			<%
				}
			%> </details>
		</div>
		<div id="messages">
			<p>Latests operations:</p>
			<div id="logs">
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
		</div>
		<div id="footer"></div>
</body>
</html>