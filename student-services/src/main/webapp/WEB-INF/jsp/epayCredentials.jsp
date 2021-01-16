<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    <link rel="stylesheet" href="css/addStock.css">
</head>

<body class="bg-light">
    <!-- Create navigation for the website within a <header> tag -->
     <header>
       <nav class="navbar navbar-light bg-light">
       <a class="navbar-brand" href="#">
         <img class="img-fluid d-inline-block align-middle" width="50" height="50" src="img/motherboard.svg">
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
     <main class="container">
     <h1 class="text-center">Provide your ePay merchant credentials</h1>
     	<div class="row">
        <div class="col text-center mt-2">
          <form:form method="POST" action="/addEpayCredentials" modelAttribute="epayCred">
                    <label class="d-block mt-2" for="wOne">Enter your merchant ID</label>
                    <form:input class="form-control" name="merchantId"  type="text" path="merchantId"/>
                    <p id="merchantIdMessage"></p>
                    <label class="d-block mt-2" for="userSecret">Enter your user secret</label>
                    <form:input class="form-control" name="userSecret" type="text" path="merchantSecret"/>
                    <p id="userSecretMessage"></p>
                    <br>
                    <input class="btn btn-primary mt-2" name="epayCredSubmit" type="submit" value="Submit"/>
          </form:form>
          <div class="col mt-2">
             ${status} 
             <c:if test="${not empty param.epayCredSubmit}">
             	<p class="mt-2">${message}</p>
             </c:if>
          </div>
        </div>
     	</div>
     </main>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
</body>

</html>