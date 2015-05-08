<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="include/header.jsp" %>
<title>Lazy English-Học tiếng anh dành cho người bận rộn</title>
<div class="container">
    <div class="row">
        <div class="control-group" id="fields">
            <c:if test="${tbllesson==null}">
                <a href="<c:url value="/course/update/${courseId}"/>" class="sentenceTrans">Back to Control page</a>
            </c:if>
            <c:if test="${tbllesson!=null}">
                <a href="<c:url value="/course/update/${tbllesson.courseId.id}"/>" class="sentenceTrans">Back to Control page</a>
            </c:if>
            <h2><label class="control-label" for="fields">Add Lesson</label></h2>

            <div class="controls">
                <c:if test="${tbllesson==null}">
                    <form role="form" autocomplete="off" action="<c:url value="/lesson/create"/>" method="post">
                        <input type="hidden" name="courseId" value="${courseId}"/>

                        <div class="form-horizontal" style="margin-left: 15px">
                            <c:if test="${error!=null}">
                                <div class="form-group">
                                    <div class="alert alert-warning alert-dismissible col-md-3" role="alert">
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span></button>
                                        <strong>Error!</strong> ${error}
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label class="control-label" style="float: left"> Name </label>

                                <div class="col-md-3">
                                    <input id="name" name="name" value="" type="text"
                                           placeholder="Enter your Lesson's name"
                                           class="form-control input-md" required="true" maxlength="128" style="margin-left: 15px;">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label" style="float: left"> Number </label>

                                <div class="col-md-3">
                                    <input id="number" name="number" value="" type="number"
                                           placeholder="Enter your Lesson's number" class="form-control input-md"
                                           required="true" min="1" max="999">
                                </div>
                            </div>

                        </div>
                        <legend>Add Vocabulary or Question/answer</legend>
                        <div class="lessondetail">
                            <small>Press
                                <button type="submit" class="btn btn-success btn-add">
                                    <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> Add more
                                </button>
                                to add another form field
                            </small>
                            <div class="voca" style="margin-top: 10px">
                                <div class="form-horizontal form-group">
                                    <label class="control-label vocalabel" style="float: left;text-align: center">Vocabulary</label>

                                    <div class="col-md-3">
                                        <input class="form-control voca " placeholder="Hello" required="true" name="voca" type="text"
                                               value="">
                                    </div>
                                </div>
                                <div class="form-horizontal form-group">
                                    <label class="control-label vocatranslabel" style="float: left;text-align: center">Meaning</label>

                                    <div class="col-md-3">
                                        <input class="form-control vocatrans" required="true" placeholder="Xin chào" name="vocatrans"
                                               type="text"
                                               value="">
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <select class="form-control type" name="type">
                                        <option value="0">vocabulary</option>
                                        <option value="1">Question/Answer</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-danger btn-remove">
                                    <span class="glyphicon glyphicon-minus" aria-hidden="true"></span> Remove
                                </button>
                            </div>
                        </div>
                        <div style="display: flex;margin-top: 10px">

                            <button type="submit" class="btn btn-success btn-lg">
                                <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> Save
                            </button>
                        </div>
                    </form>
                    <br>
                </c:if>
                <!--update-->
                <c:if test="${tbllesson!=null}">
                    <form role="form" autocomplete="off" action="<c:url value="/lesson/update"/>" method="post">
                        <input type="hidden" name="courseId" value="${tbllesson.courseId.id}"/>
                        <input type="hidden" name="lessonId" value="${tbllesson.id}"/>

                        <div class="form-horizontal" style="margin-left: 15px">
                            <c:if test="${error!=null}">
                                <div class="form-group">
                                    <div class="alert alert-warning alert-dismissible col-md-3" role="alert">
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span></button>
                                        <strong>Error!</strong> ${error}
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label class="control-label" style="float: left"> Name </label>

                                <div class="col-md-3">
                                    <input id="name" name="name" value="${tbllesson.lessonName}" type="text"
                                           placeholder="Enter your Lesson's name"
                                           class="form-control input-md" required="" style="margin-left: 15px;">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label" style="float: left"> Number </label>

                                <div class="col-md-3">
                                    <input id="number" name="number" value="${tbllesson.lessonNumber}" type="number"
                                           placeholder="Enter your Lesson's number" class="form-control input-md"

                                           required="true" min="1" max="999"
                                           >
                                </div>
                            </div>

                        </div>
                        <legend>Add Vocabulary or Question/answer</legend>
                        <div class="lessondetail">
                            <small>Press
                                <button type="submit" class="btn btn-success btn-add">
                                    <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> Add more
                                </button>
                                to add another form field
                            </small>
                            <c:forEach var="lessonDetail" items="${tbllesson.listLessonDetail}">
                                <div class="voca" style="margin-top: 10px">
                                    <input type="hidden" name="lessonDetailId" value="${lessonDetail.id}"/>

                                    <div class="form-horizontal form-group">
                                        <c:if test="${lessonDetail.type == 0}">
                                            <label class="control-label vocalabel"
                                                   style="float: left;text-align: center">Vocabulary</label>

                                            <div class="col-md-3">
                                                <input class="form-control voca " placeholder="Hello" required="true" name="voca"
                                                       type="text" value="${lessonDetail.sentence}">
                                            </div>
                                        </c:if>
                                        <c:if test="${lessonDetail.type == 1}">
                                            <label class="control-label vocalabel"
                                                   style="float: left;text-align: center">Question</label>

                                            <div class="col-md-3">
                                                <input class="form-control voca " required="true"  placeholder="What's your name"
                                                       name="voca"
                                                       type="text" value="${lessonDetail.sentence}">
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="form-horizontal form-group">
                                        <c:if test="${lessonDetail.type == 0}">
                                            <label class="control-label vocatranslabel"
                                                   style="float: left;text-align: center">Meaning</label>

                                            <div class="col-md-3">
                                                <input class="form-control vocatrans" required="true" placeholder="Xin chào"
                                                       name="vocatrans" type="text"
                                                       value="${lessonDetail.sentenceTrans}">
                                            </div>
                                        </c:if>
                                        <c:if test="${lessonDetail.type == 1}">
                                            <label class="control-label vocatranslabel"
                                                   style="float: left;text-align: center">Answer</label>

                                            <div class="col-md-3">
                                                <input class="form-control vocatrans" required="true" placeholder="My name is cloud"
                                                       name="vocatrans" type="text"
                                                       value="${lessonDetail.sentenceTrans}">
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="col-md-2">
                                        <select class="form-control type" name="type">
                                            <c:if test="${lessonDetail.type == 0}">
                                                <option value="0" selected="true">vocabulary</option>
                                                <option value="1">Question/Answer</option>
                                            </c:if>
                                            <c:if test="${lessonDetail.type == 1}">
                                                <option value="0">vocabulary</option>
                                                <option value="1" selected="true">Question/Answer</option>
                                            </c:if>
                                        </select>
                                    </div>
                                    <button type="submit" class="btn btn-danger btn-remove">
                                        <span class="glyphicon glyphicon-minus" aria-hidden="true"></span> Remove
                                    </button>
                                </div>
                            </c:forEach>

                        </div>
                        <div style="display: flex;margin-top: 10px">

                        <button type="submit" class="btn btn-success btn-lg">
                            <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> Save
                        </button>
                        </div>
                    </form>
                    <br>
                </c:if>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $(document).on('click', '.btn-add', function (e) {
            e.preventDefault();

            var controlForm = $('.lessondetail:first'),
                    currentEntry = $('.voca:first'),
                    newEntry = $(currentEntry.clone()).appendTo(controlForm);

            newEntry.find('input').val('');
            newEntry.find('.voca').attr("placeholder", "hello");
            newEntry.find('.vocatrans').attr("placeholder", "Xin chào");
            newEntry.find('.vocalabel').html('Vocabulary')
            newEntry.find('.vocatranslabel').html('Meaning')
        }).on('click', '.btn-remove', function (e) {
            if ($('.voca').length > 2) {
                $(this).parents('.voca:first').remove();
            }

            e.preventDefault();
            return false;
        }).on('change', '.type', function (e) {
            e.preventDefault();
            var typeValue = $(this).val();
            if (typeValue == '0') {
                currentEntry = $(this).parents('.voca')
                currentEntry.find('.voca').attr("placeholder", "hello");
                currentEntry.find('.vocalabel').html('Vocabulary')
                currentEntry.find('.vocatrans').attr("placeholder", "Xin chào");
                currentEntry.find('.vocatranslabel').html('Meaning')
            } else {
                currentEntry = $(this).parents('.voca')
                currentEntry.find('.voca').attr("placeholder", "What's your name ?");
                currentEntry.find('.vocalabel').html('Question')
                currentEntry.find('.vocatrans').attr("placeholder", "My name is cloud");
                currentEntry.find('.vocatranslabel').html('Answer')
            }
        });
    });


</script>