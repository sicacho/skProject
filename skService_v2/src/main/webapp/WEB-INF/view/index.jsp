<%--
  Created by IntelliJ IDEA.
  User: khangtnse60992
  Date: 12/22/2014
  Time: 14:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="include/header.jsp" %>
<title>Lazy English-Học tiếng anh dành cho người bận rộn</title>

<div class="row">
    <div class="col-lg-12 text-center v-center text-primary">

        <h1 class="title">Lazy English</h1>

        <p class="Subtitle">Học tiếng anh theo cách dễ dàng nhất</p>
        <form class="col-lg-12" action="<c:url value="/course/search"/>">
            <div class="input-group" style="max-width:340px;text-align:center;margin:0 auto;">

                <input class="form-control input-lg" title="" placeholder="Nhập course bạn muốn tìm kiếm"
                       type="text" name="keyword">
                    <span class="input-group-btn"><button class="btn btn-lg btn-primary" type="submit">OK
                    </button></span>
            </div>
        </form>
    </div>
    <!-- /row -->
    <br><br>

    <%--<div class="table-responsive col-lg-12" style="margin-top: 28px">--%>

    <%--<table class="table table-bordered v-center text-center"--%>
    <%--style="max-width:600px;text-align:center;margin:0 auto;color: #000000">--%>

    <%--<thead>--%>
    <%--<tr>--%>
    <%--<th>Name</th>--%>
    <%--<th>Author</th>--%>
    <%--<th>Detail</th>--%>
    <%--<th></th>--%>
    <%--</tr>--%>
    <%--</thead>--%>
    <%--<tbody>--%>
    <%--<c:forEach var="course" items="${courses}" varStatus="count">--%>
    <%--<tr>--%>
    <%--<td>${course.courseName}</td>--%>
    <%--<td>${course.userId.username}</td>--%>
    <%--<td>--%>
    <%--<button type="button" class="btn btn-info" data-toggle="collapse"--%>
    <%--data-target="#demo-${count.index}">Show More--%>
    <%--</button>--%>
    <%--<div id="demo-${count.index}" class="collapse">--%>
    <%--${course.detail}--%>
    <%--</div>--%>
    <%--</td>--%>
    <%--<td><a class="btn btn-info" href="<c:url value="/lesson/${course.id}"/>">Start Study</a></td>--%>
    <%--</tr>--%>
    <%--</c:forEach>--%>
    <%--</tbody>--%>
    <%--</table>--%>
    <%--</div>--%>
    <c:if test="${courses == null}">
    <div class="col-md-9 col-md-offset-2 col-sm-10">
        <h4>${error}</h4>
    </div>
    </c:if>
    <c:if test="${courses != null}">
        <div class="col-md-9 col-md-offset-2 col-sm-10">


            <c:forEach var="course" items="${courses}" varStatus="count">
                <div class="list-group-item box">
                    <div class="col-md-8" style="margin-top: 3%">
                        <h4 class="list-group-item-heading">${course.courseName} </h4>

                        <p class="list-group-item-text" style="overflow: hidden;display: block;height: 6.6em;
                    ;text-align: justify;margin-top: 10px;">${course.detail}</p>

                        <p class="author" style="float: right;margin-top: 7px;">
                            <span>— by</span> ${course.userId.username}
                        </p>
                    </div>
                    <div class="col-md-3 text-center">
                        <h2> ${fn:length(course.lesson)}
                            <small> Lesson</small>
                        </h2>
                        <a href="<c:url value="/lesson/${course.id}"/>" class="btn btn-primary btn-lg ">Study Now</a>
                        <c:if test="${sessionScope.user == null}">
                        <div style="margin-top: 10px">
                            <a href="<c:url value="/course/create"/>" class="btn btn-info btn-lg">Create your course</a>
                        </div>
                        </c:if>
                        <c:if test="${sessionScope.user !=null and sessionScope.user.id == course.userId.id}">
                            <div style="margin-top: 10px">
                                <a href="<c:url value="/course/update/${course.id}"/>" class="btn btn-info">Update</a>

                                <form action="<c:url value="/course/delete"/>" style="display: -webkit-inline-box;" method="post">
                                    <input type="hidden" name="urlPath" class="urlPath" value=""/>
                                    <input type="hidden" name="courseId" class="" value="${course.id}"/>
                                    <button type="submit" class="btn btn-danger btn-remove">Delete</button>
                                </form>
                            </div>
                        </c:if>
                    </div>
                    <div class="col-md-1 text-center social" style="margin-top: 3%">

                        <a href="" class="link facebook" target="_parent"><i
                                class="fa fa-facebook-square"></i></a>
                        <a href="" class="link twitter" target="_parent"><i
                                class="fa fa-twitter"></i></a>
                        <a href="" class="link google-plus"
                           target="_parent"><i
                                class="fa fa-google-plus-square"></i></a>

                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="col-lg-12 text-center ">
            <div class="gigantic pagination">
                <a href="#" class="first" data-action="first">&laquo;</a>
                <a href="#" class="previous" data-action="previous">&lsaquo;</a>
                <input type="text" readonly="readonly"/>
                <a href="#" class="next" data-action="next">&rsaquo;</a>
                <a href="#" class="last" data-action="last">&rsaquo;</a>
            </div>
        </div>
    </c:if>
</div>
<script>
    $(document).ready(function () {
        $('.urlPath').val($(location).attr('href'));
        var urls = $(location).attr('pathname').split("/")
        var urlPath = "";
        for(i = 1 ; i < urls.length-1;i++) {
            urlPath = urlPath + "/" + urls[i];
        }
        var origin = $(location).attr('origin');
        $('.pagination').jqPagination({
            link_string: origin + urlPath+'/{page_number}',
            max_page: ${maxpage},
            current_page : ${currentPage},
            paged: function (page) {
                window.location = origin + urlPath+'/'+page;
            }
        });
        $(document).on('click', '.btn-remove', function (e) {
            e.preventDefault();
            var temp = this;
            bootbox.confirm("Are you sure?", function(result) {
                if(result==true) {
                    $(temp).closest("form").submit();
                } else {

                }
            });
        });
    });
</script>
<%@include file="include/footer.jsp" %>
