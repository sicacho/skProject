<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="include/header.jsp" %>
<title>Lazy English-Học tiếng anh dành cho người bận rộn</title>

<c:if test="${course != null}">
    <div class="col-md-6 col-md-offset-1 col-sm-10">
        <form class="form-horizontal" action="<c:url value="/course/update"/>" method="post">
            <fieldset>
                <!-- Form Name -->
                <legend>Update your course</legend>
                <input type="hidden" name="id" value="${course.id}"/>
                <!-- Text input-->
                <c:if test="${error!=null}">

                <div class="form-group">
                    <div class="alert alert-danger col-md-2" role="alert">${error}</div>
                </div>
                </c:if>
                <div class="form-group">
                    <label class="col-md-2 control-label" for="name">Name</label>

                    <div class="col-md-4">
                        <input id="name" name="name" value="${course.courseName}" type="text"
                               pattern="[a-zA-Z0-9]{4,16}"
                               placeholder="Enter your course's name" class="form-control input-md" required="true" maxlength="128">

                    </div>
                </div>

                <!-- Textarea -->
                <div class="form-group">
                    <label class="col-md-2 control-label" for="detail">description</label>

                    <div class="col-md-4">
                        <textarea class="form-control" id="detail" name="detail" style="width: 458px;height: 134px"
                                  placeholder="Something about your course , how , when , why to study ..... " required="true" >${course.detail}</textarea>
                    </div>
                </div>

                <!-- Button -->
                <div class="form-group">
                    <div class="form-group">
                    <label class="col-md-2 control-label" for=""></label>
                    <div class="col-md-2" style="margin-left: 10px;">
                        <button id="" name="" class="btn btn-primary">Save</button>
                    </div>
                </div>
                </div>

            </fieldset>
        </form>
        <legend>Lessons</legend>
        <div>
            <small><a href="<c:url value="/lesson/create/${course.id}"/>" class="btn btn-success btn-add">
                <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> Add Lesson
            </a></small>
        </div>
        <table class="flat-table"
               style="text-align:justify;margin:0 auto;margin-bottom: 50px;margin-top: 20px">
            <thead>
            <tr>
                <th class="thStyle">Name</th>
                <th class="thStyle">Number</th>
                <th class="thStyle">Action</th>
            </tr>
            </thead>
            <c:forEach var="lesson" items="${course.lesson}">
            <tr>
                <td class="tdStyle"><a href="">${lesson.lessonName}</a></td>
                <td class="tdStyle">${lesson.lessonNumber}</td>
                <td class="tdStyle" style="display: flex"><a href="<c:url value="/lesson/update/${lesson.id}"/>" class="btn btn-info">Update</a>
                    <form action="<c:url value="/lesson/delete"/>" method="post" class="form-delete">
                    <input type="hidden" name="lessonId" value="${lesson.id}"/>
                    <input type="hidden" name="courseId" value="${course.id}"/>
                        <button type="submit" class="btn btn-danger btn-remove">Delete</button></form>
                </td>

            </tr>
            </c:forEach>

        </table>
    </div>
</c:if>
<c:if test="${course == null}">
    <div class="col-md-6 col-md-offset-3 col-sm-10">
        <form class="form-horizontal" action="<c:url value="/course/create/${course.id}"/>" method="post">
            <fieldset>

                <!-- Form Name -->
                <legend>Create your course</legend>
                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-2 control-label" for="name">Name</label>

                    <div class="col-md-4">
                        <input id="name" name="name" value="" type="text" placeholder="Enter your course's name"
                               class="form-control input-md" required="true" maxlength="128">

                    </div>
                </div>

                <!-- Textarea -->
                <div class="form-group">
                    <label class="col-md-2 control-label" for="detail">description</label>

                    <div class="col-md-4">
                        <textarea class="form-control" id="detail" name="detail"
                                  placeholder="Something about your course , how , when , why to study ..... " required="true"></textarea>
                    </div>
                </div>

                <!-- Button -->
                <div class="form-group">
                    <div class="form-group">
                        <label class="col-md-2 control-label" for=""></label>

                        <div class="col-md-2" style="text-align: left;margin-left: 10px;">
                            <button id="" name="" class="btn btn-primary">Create</button>
                        </div>
                    </div>
                </div>

            </fieldset>
        </form>
    </div>
</c:if>
<script>
    $(function () {
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