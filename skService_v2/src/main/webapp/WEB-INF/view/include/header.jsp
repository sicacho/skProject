<%--
  Created by IntelliJ IDEA.
  User: khangtnse60992
  Date: 12/22/2014
  Time: 14:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="generator" content="Bootply"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="<c:url value="/resources/image/logo2.png" />" rel="shortcut icon" type="image/x-icon"/>
    <meta http-equiv="content-language" content="vi"/>
    <meta name="description" content="Website học anh văn với các chương trình mới nhất ,
                                        phương pháp học phản xạ đảm bảo giao tiếp được trong vòng 6 tháng "/>
    <meta name="keywords"
          content="học anh văn căn bản,học anh văn giao tiếp,học anh văn online free,học anh văn giành cho người đi làm"/>
    <meta name="robots" content="noodp,index,follow"/>
    <meta name='revisit-after' content='1 days'/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="<c:url value="/resources/css/jqpagination.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/demo.css" />" rel="stylesheet">
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <link href='http://fonts.googleapis.com/css?family=Lobster' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Bitter' rel='stylesheet' type='text/css'>

    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <script src="<c:url value="/resources/js/jquery-1.11.1.min.js"/>"></script>
    <link href="<c:url value="/resources/css/styles.css"/>" rel="stylesheet">
    <%--<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>--%>
    <%--<script src="<c:url value="/resources/js/bootstrap.js"/>"></script>--%>
    <%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>--%>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="<c:url value="/resources/js/jquery.jqpagination.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery.jqpagination.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootbox.min.js"/>"></script>

</head>
<body>
<div class="container-full">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<c:url value="/course/1"/>">Lazy English</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/">Home Page</a></li>
                    <li><a href="<c:url value="/course/1"/>">Course</a></li>
                    <li><a href="<c:url value="/course/create"/>">Create Course</a></li>
                    <c:if test="${sessionScope.user != null}">
                    <li>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-expanded="false">
                            Hello, ${sessionScope.user.username} <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="<c:url value="/course/${sessionScope.user.id}/1"/>">Your Course</a></li>
                            <li><a href="<c:url value="/logout"/>">Logout</a></li>
                        </ul>
                    </li>
                    </c:if>
                    <c:if test="${sessionScope.user == null}">
                        <li><a href="<c:url value="/login"/>">Login</a></li>
                        <li><a href="<c:url value="/register"/>">Register</a></li>
                    </c:if>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>

