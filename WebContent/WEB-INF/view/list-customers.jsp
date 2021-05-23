<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="com.luv2code.springdemo.utils.SortingUtils" %>
<!DOCTYPE html>
<html>
<head>
		<title>List Customers</title>
		
		<!-- reference our style sheet -->
		
		<link type="text/css"
			rel="stylesheet"
			href="${pageContext.request.contextPath}/resources/css/style.css" />
</head>

<body>


	<div id="wrapper">
		<div id="header">
		<h2>CRM- Customer Relationship Manager</h2>
		</div>
	</div>
	
	<div id="container">
	
		<div id="content">
		
		<!-- put new button : Add customer -->
		
		<input type="button" value="Add customer"
				onclick="window.location.href='showFormForAdd' ; return false;"
				class="add-button"
		/>
		<!--  above class is actually the css style for pretty looking button -->
		
		<!-- add a search box -->
		<form:form action="search" method="GET">
		Search Customer:<input type="text" name="theSearchName" />
		
		<input type="submit" value="Search" class="add-button" />
		</form:form>
		
		
		
		<!-- add our html table -->
		
		<!-- construct a sort link for first name -->
		<c:url var="sortLinkFirstName" value="/customer/list">
		
			<c:param name="sort" value="<%=Integer.toString(SortingUtils.FIRST_NAME) %>" />
		
		</c:url>
		
		<!-- construct a sort link for last name -->
		<c:url var="sortLinkLastName" value="/customer/list">
		
			<c:param name="sort" value="<%=Integer.toString(SortingUtils.LAST_NAME) %>" />
		
		</c:url>
		
		<!-- construct a sort link for email -->
		<c:url var="sortLinkEmail" value="/customer/list">
		
			<c:param name="sort" value="<%=Integer.toString(SortingUtils.EMAIL) %>" />
		
		</c:url>
		
		
		
		<table>
			<tr>
				<th><a href="${sortLinkFirstName}">First Name</a></th>
				<th><a href="${sortLinkLastName}">Last Name</a></th>
				<th><a href="${sortLinkEmail}">Email</a></th>
				<th>Action</th>
				
			<!-- loop over and print our customers -->
			<c:forEach var="tempCustomer" items="${customers}">
				
				<!-- construct an "update" link with customer id -->
				
				<c:url var="updateLink" value="/customer/showFormForUpdate">
				
					<c:param name="customerId" value="${tempCustomer.id}"></c:param>
				
				</c:url>
				
				<!-- construct an "delete" link with customer id -->
				
				<c:url var="deleteLink" value="/customer/delete">
				
					<c:param name="customerId" value="${tempCustomer.id}"></c:param>
				
				</c:url>
				
				
				<tr>
					<td> ${tempCustomer.firstName}</td>
					<td> ${tempCustomer.lastName}</td>
					<td> ${tempCustomer.email}</td>
					<td>
						<!-- display the update link -->
						<a href="${updateLink}">Update</a>
						|
						<!-- display the delete link -->
						<a href="${deleteLink}"
						onclick="if(!(confirm('Are you sure you want to delete this customer?'))) return false">Delete</a>
					</td>
				
				</tr>
			
			</c:forEach>	
				
		
		</table>
		
		
		</div>
	</div>

</body>

</html>