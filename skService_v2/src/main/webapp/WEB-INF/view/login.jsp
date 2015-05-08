<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="include/header.jsp" %>
<title>Lazy English-Học tiếng anh dành cho người bận rộn</title>

    <div class="row">
        <div class="col-lg-12 text-center v-center text-primary">

            <h1>Lazy English</h1>

            <p>Học tiếng anh theo cách dễ dàng nhất</p>

            <div class="col-md-4 col-md-offset-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please sign in</h3>
                    </div>

                    <div class="panel-body">
                        <form accept-charset="UTF-8" role="form" action="<c:url value="/login"/>" method="post">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Username" name="username" type="text">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password"
                                           value="">
                                </div>

                                <div class="checkbox">
                                    <label style="margin-right: 270px">
                                        <input name="remember" type="checkbox" value="Remember Me"> Remember Me
                                    </label>
                                </div>
                                <c:if test="${error != null}">
                                    <div class="form-group alert alert-error">
                                            <a class="close" data-dismiss="alert" href="#">×</a>${error}
                                    </div>
                                </c:if>
                                <input class="btn btn-lg btn-success btn-block" type="submit" value="Login">
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="include/footer.jsp" %>