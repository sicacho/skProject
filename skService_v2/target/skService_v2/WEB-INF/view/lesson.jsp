<%--
  Created by IntelliJ IDEA.
  User: khangtnse60992
  Date: 12/25/2014
  Time: 11:07 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="include/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>Lazy English-Học tiếng anh dành cho người bận rộn</title>
<c:if test="${lesson == null}">
    <div class="row text-center">
        <h4 class="CourseTitle">There are no lesson in this Course</h4>
        <c:if test="${sessionScope.user !=null and sessionScope.user.id == course.userId.id}">
            <form action="<c:url value="/lesson/create/${course.id}"/>" method="get">
                <input type="hidden" name="id" value=""/>
                <button type="submit" class="btn btn-success ">
                    <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> Add Lesson Now
                </button>
            </form>
        </c:if>
    </div>
</c:if>
<c:if test="${lesson != null}">
    <div class="row">
        <!--<div class="well">-->

        <div class="col-md-9 col-md-offset-2 col-sm-10">
            <div class="list-group-item boxLesson">
                <div class="col-md-12" style="margin-top: 3%">
                    <h4 class="CourseTitle">${lesson.courseId.courseName}</h4>

                    <p class="list-group-item-text"
                       style="text-align: justify;margin-top: 10px;">${lesson.courseId.detail}
                    </p>

                    <p class="author" style="float: right;margin-top: 7px;">
                        <span>— by </span>${lesson.courseId.userId.username}</p>
                </div>


            </div>
        </div>

        <div class="col-md-9 col-md-offset-2 col-sm-10" style="margin-top: 20px">
            <div class="boxLesson" style="max-height: 80%;display: block;">
                <c:forEach var="number" items="${listNumber}">
                <span class="fa-stack fa-lg AnotherPage page-link-${number}">
                    <i class="fa fa-circle fa-stack-2x"></i>
                    <a href="<c:url value="/lesson/${lesson.courseId.id}/${number}"/>"><i class="fa fa-inverse fa-stack-1x">${number}</i></a>
                </span>
                </c:forEach>
            </div>
        </div>

        <div class="col-md-9 col-md-offset-2 col-sm-10" style="margin-top: 20px">
            <div style="padding-left: 25px;display: flex">
                <h4 class="CourseTitle">${lesson.lessonName}</h4>
                <c:if test="${sessionScope.user != null and sessionScope.user.id == lesson.courseId.userId.id}">
                <a href="<c:url value="/lesson/update/${lesson.id}"/>" class="btn btn-primary btn-lg" style="margin-left: 10px;">Update</a>
                <a href="<c:url value="/lesson/create/${lesson.courseId.id}"/>" class="btn btn-success btn-lg" style="margin-left: 10px;"><span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>Add More Lesson</a>
                </c:if>
            </div>
        </div>
        <div class="col-md-9 col-md-offset-2 col-sm-10">
            <c:forEach var="lessonDetail" items="${lesson.listLessonDetail}">
                <div class="boxLesson">
                    <c:if test="${lessonDetail.type == 0}">
                    <div style="max-width: 80%;width: 80%">
                        <h4 class="sentenceTrans">${lessonDetail.sentence}</h4>
                        <h5 class="sentence">${lessonDetail.sentenceTrans}</h5>
                    </div>
                        <div class="col-md-2 social">
                            <a class="link facebook"><i class="fa fa-book"></i></a>
                        </div>
                    </c:if>
                    <c:if test="${lessonDetail.type == 1}">
                        <div style="max-width: 80%;width: 80%">
                            <h4 class="sentence">${lessonDetail.sentence}</h4>
                            <h5 class="sentenceTrans">${lessonDetail.sentenceTrans}</h5>
                        </div>
                        <div class="col-md-2 social">
                            <a class="link google-plus"><i class="fa fa-question"></i></a>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </div>
</c:if>
</div>
<script>
    $(document).ready(function () {
        $('.page-link-${thisPage}').removeClass('AnotherPage').addClass('CurrentPage');
    });
</script>
<%@include file="include/footer.jsp" %>
