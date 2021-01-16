<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="java.time.LocalDateTime" %>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    <link rel="stylesheet" href="../css/stockBrowse.css">
</head>

<body class="bg-light">
    <!-- Create navigation for the website within a <header> tag -->
     <header>
       <nav class="navbar navbar-light bg-light">
       <a class="navbar-brand" href="#">
         <img class="img-fluid d-inline-block align-middle" width="50" height="50" src="../img/motherboard.svg">
         <span class="ml-1">BZM</span>
       </a> 
         <ul class="navbar-nav mr-auto flex-row ml-3">
          <li class="nav-item active">
            <a class="nav-link" href="/">Home <span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item">
            <a class="nav-link ml-3" href="/addStock">Add Stock</a>
          </li>
           <li class="nav-item ml-3 dropdown">
      		<a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
        		Browse Stock
      		</a>
      		<div class="dropdown-menu">
        		<a class="dropdown-item" href="/stockBrowse">By Company</a>
        		<a class="dropdown-item" href="/stockBrowse/stockBrowseGroup">By Stock Group</a>
        		<a class="dropdown-item" href="/stockBrowse/searchStockName">By Stock Name</a>
        		<a class="dropdown-item" href="/stockBrowse/all">Browse All Stocks</a>
      		</div>
    	</li>
            <sec:authorize access="hasRole('ADMIN')">
          <li class="nav-item ml-3 dropdown">
      		<a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
        		Admin Options
      		</a>
      		<div class="dropdown-menu">
        		<a class="dropdown-item" href="/admin">View Company Data</a>
        		<a class="dropdown-item" href="/addStockGroup">Add Stock Group</a>
        		<a class="dropdown-item" href="/addArticle">Add Article</a>
      		</div>
    	</li>
    	</sec:authorize>
        </ul>
        <div class="mr-5">
        <c:if test = "${pageContext.request.userPrincipal.name == null}">
         <form class="text-center" action="${contextPath}/login" method="POST">
            <input type="text" name="username" placeholder="Email">
            <input type="password" name="password" placeholder="Password">
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn btn-primary mb-1" type="submit" name="login-submit">Login</button>
            <a href="signup"><button class="btn btn-secondary mb-1" type="button">Signup</button></a>
            </form>
      </c:if>
      <c:if test = "${pageContext.request.userPrincipal.name != null}">
      	<form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <p class="d-inline-block">Welcome ${pageContext.request.userPrincipal.name} | </p>
        <a href="/profile" class="ml-2 d-inline-block btn btn-primary text-white">Profile</a>
        <a class="ml-2 d-inline-block btn btn-danger text-white" onclick="document.forms['logoutForm'].submit()">Logout</a>
      </c:if>
        </div>
      </nav>
     </header>
     <main class="container text-center">
          <div class="container mt-2">
          <div class="row font-weight-bold mt-4 text-center border-bottom">
          	<div class="col remove-padding border-right">
          		Stock Name
          	</div>
          	<div class="col remove-padding border-right">
          		Stock Description
          	</div>
          	<div class="col remove-padding border-right">
          		Price / Quantity
          	</div>
          	<div class="col remove-padding">
          		Contact Info
          	</div>
          </div>
           <c:forEach var="compStocks" items="${result}">
          	<div class="row mt-3 mb-3 border border-primary">
          		<div class="col border-right border-primary">
          			<div class="row border-bottom border-primary">
          				<div class="col text-center">
          					${compStocks.stockName}
          				</div>
          			</div>
          			<div class="row">
          				<img class="img-fluid img-thumbnail mx-auto" src="${compStocks.thumbnail}" alt="This is an image">  
          			</div>
          		</div>
          		<div class="col border-right border-primary">
          			${compStocks.stockDesc}
          		</div>
          		<div class="col remove-padding">
          			<p class="border-bottom border-primary">Price Per Item (in lvs.)</p>
          			<p class="border-bottom border-primary pb-3 remove-margins">${compStocks.pricePerItem}</p>
          			<div class="row remove-padding">
          				<div class="col">
          					<p class="border-primary border-bottom">Quantity</p>
          					<p>${compStocks.quantity}</p>
          				</div>
          			</div>
          		</div>	
          			<div class="col border-left border-primary">
          			<div class="row border-bottom border-primary">
          				<div class="col remove-padding">
          					<p class="border-bottom border-primary">Company Name</p>
          					<p>${compStocks.getCompanies().iterator().next().getCompanyName() }<p>
          				</div>
          			</div>
          			<div class="row border-bottom border-primary">
          				<div class="col remove-padding">
          					<p class="border-primary border-bottom">Phone</p>
          					<p><a href="tel:${compStocks.getCompanies().iterator().next().getPhoneNumber() }">${compStocks.getCompanies().iterator().next().getPhoneNumber() }</a></p>
          				</div>
          			</div>
          			<div class="row">
          				<div class="col remove-padding">
          					<p class="border-primary border-bottom">E-mail</p>
          					<p><a href="mailto:${compStocks.getCompanies().iterator().next().getEmail() }">${compStocks.getCompanies().iterator().next().getEmail() }</a></p>
          				</div>	
          			</div>
          			<div class="row">
          				<div class="col remove-padding">
          					<p class="border-primary border-bottom border-top">Reserve a stock</p>
          					<c:choose>
          						<c:when test="${ compStocks.getCompanies().iterator().next().getEmail() == pageContext.request.userPrincipal.name && (compStocks.getReserveDate() != null && compStocks.getReserveDate() != '' && !LocalDateTime.now().isAfter(compStocks.getReserveDate()))}">
          							<p class="text-danger">This is your stock! It is currently reserved!</p>
          						</c:when>
          						<c:when test="${ compStocks.getCompanies().iterator().next().getEmail() == pageContext.request.userPrincipal.name && (compStocks.getReserveDate() == null || compStocks.getReserveDate() == '' || LocalDateTime.now().isAfter(compStocks.getReserveDate())) }">
          							<p class="text-danger">This is your stock! It is currently NOT reserved!</p>
          						</c:when>
          						<c:when test="${compStocks.getReserveDate() != null && compStocks.getReserveDate() != '' && !LocalDateTime.now().isAfter(compStocks.getReserveDate()) }">
          							<p>Stock is reserved!</p>
          						</c:when>			
          						<c:when test="${compStocks.getReserveDate() == null || compStocks.getReserveDate() == '' || LocalDateTime.now().isAfter(compStocks.getReserveDate()) }">
          							<a href="/reserve?sId=${compStocks.getId()}" class="reserve mb-3 btn btn-primary">Reserve</a>
          						</c:when>
          					</c:choose>
          				</div>	
          			</div>
          		</div>
          	</div>
          </c:forEach>
          </div> 
    </main>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
    <script src="../js/ajaxReserve.js"></script>
</body>

</html>