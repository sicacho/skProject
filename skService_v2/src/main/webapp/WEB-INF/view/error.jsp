<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="include/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<title>Lazy English-404 not found</title>

<div class="row">
    <div class="col-lg-12 text-center v-center text-primary">

        <h1 style="color: #000000">Lazy English</h1>

        <p style="color: #000000">Học tiếng anh theo cách dễ dàng nhất</p>
    </div>
</div>
<div class="col-lg-12 text-center v-center text-primary">
    <c:if test="${error  != null}">
        <h3 style="color: #000000">${error}</h3>
    </c:if>
    <c:if test="${error ==null}">
        <h3 style="color: #000000">Something wrong , please try again</h3>
    </c:if>
</div>
</div>
<%@include file="include/footer.jsp" %>
