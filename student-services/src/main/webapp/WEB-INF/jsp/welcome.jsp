<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Nunito:400,800,900" rel="stylesheet">
    <link rel="stylesheet" href="css/main.css">
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
        <c:if test = "${pageContext.request.userPrincipal.name == null}">
         <form action="${contextPath}/login" method="POST">
            <input class="form-control" type="text" name="username" placeholder="Email">
            <input class="form-control" type="password" name="password" placeholder="Password">
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
     <main class="bg-light">
     	<div class="container">
        <div class="row">
          <div class="col">
          <div id="carousel-example-generic" class="carousel slide bg-dark" data-ride="carousel">
    <ol class="carousel-indicators">
      <li data-target="#carousel-example-generic" data-slide-to="0" class=""></li>
      <li data-target="#carousel-example-generic" data-slide-to="1" class=""></li>
      <li data-target="#carousel-example-generic" data-slide-to="2" class="active"></li>
    </ol>
    <div class="carousel-inner">
      <div class="carousel-item">
        <img class="d-block w-100 h-100" alt="First slide [800x400]" src="img/carousel/armatura.gif">
        <div class="carousel-caption d-none d-md-block">
          <h5>First slide label</h5>
          <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
        </div>
      </div>
      <div class="carousel-item">
        <img class="d-block w-100 h-100"  alt="Second slide [800x400]" src="img/carousel/cement.jpg" >
        <div class="carousel-caption d-none d-md-block">
          <h5>Second slide label</h5>
          <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
        </div>
      </div>
      <div class="carousel-item active">
        <img class="d-block w-100 h-100"  alt="Third slide [800x400]" src="img/carousel/circuitry.jpg" >
        <div class="carousel-caption d-none d-md-block">
          <h5>Third slide label</h5>
          <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur.</p>
        </div>
      </div>
    </div>
    <a class="carousel-control-prev" href="#carousel-example-generic" role="button" data-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#carousel-example-generic" role="button" data-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>
        </div>
     	</div>
     	<hr class="mt-5 border-top border-info">
     	<c:forEach var="article" items="${articles}">
     	<div class="post-preview">
            <h2 class="post-title">
              ${article.articleTitle}
            </h2>
            <div class="post-subtitle">
              <span id="articleText">${article.articleText}</span>
              <a href="/viewArticle?aId=${article.id}&uId=${article.poster.getId()}">Read More...</a>
            </div>
        </div>
        </c:forEach>
     </main>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/hammer.js/2.0.8/hammer.js"></script>
    <script src="js/welcome.js"></script>
 	<script src="js/hammer.js"></script>
</body>

</html>