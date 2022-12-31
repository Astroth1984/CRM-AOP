<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
	<title>List Customers</title>
	<!-- Reference our style sheet -->
	<link type="text/css" 
		  rel="stylesheet" 
		  href="${pageContext.request.contextPath}/resources/css/style.css" 
	/>
	
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>CRM - Customer Relationship Manager</h2>
		</div>
	</div>
	
	<!--  add a search box -->
    <form:form action="search" method="GET">
        Search customer: <input type="text" name="theSearchName" />
        
        <input type="submit" value="Search" class="add-button" />
    </form:form>
	
	<div id="container">
		<div id="content">
		
			<!-- Button to Add customer -->
			<input 
				type="button" 
				value="Add Customer" 
				onclick="window.location.href='showFormForAdd'; return false;" 
				class="add-button" 
			/>
			<!-- html table -->
			<table>
			
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email Name</th>
					<th>Action</th>
				</tr>
				
				<!-- Loop and print customers -->
				<c:forEach var="tempC" items="${customers}">
				
				
				<!-- Construct an "Update" Link with custumerId -->
				<c:url var="updateLink" value="/customer/showFormForUpdate">
					<c:param name="customerId" value="${tempC.id }"></c:param>
				</c:url>
				
				<!-- Construct an "delete" Link with custumerId -->
				<c:url var="deleteLink" value="/customer/delete">
					<c:param name="customerId" value="${tempC.id }"></c:param>
				</c:url>
				
					<tr>
						<td>${tempC.firstName}</td>
						<td>${tempC.lastName}</td>
						<td>${tempC.email}</td>
						<td>
							<!-- Display the update link -->
							<a href="${updateLink}">Update</a>
							|
							<!-- Display the delete link -->
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