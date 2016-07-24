<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
    <jsp:include page="fragments/bodyHeader.jsp"/>
    <div class="jumbotron">
        <div class="container">
            <div class="shadow">
                <h3><fmt:message key="meals.title"/></h3>
                <div class="view-box">
                    <form class="form-horizontal" method="post" action="meals" id="filterForm">
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="startDate">From Date:</label>
                            <div class="col-sm-2">
                                <input class="form-control" type="date" name="startDate" id="startDate" value="${startDate}">
                            </div>
                            <label class="control-label col-sm-2" for="endDate">To Date:</label>
                            <div class="col-sm-2">
                                <input class="form-control" type="date" name="endDate" id="endDate" value="${endDate}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="startTime">From Time:</label>
                            <div class="col-sm-2">
                                <input class="form-control" type="time" name="startTime" id="startTime" value="${startTime}">
                            </div>
                            <label class="control-label col-sm-2" for="endTime">To Time:</label>
                            <div class="col-sm-2">
                                <input class="form-control" type="time" name="endTime" id="endTime" value="${endTime}">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-8">
                                <button class="btn btn-primary pull-right" type="submit">
                                    <fmt:message key="meals.filter"/>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
                <a class="btn btn-sm btn-info" id="add"><fmt:message key="meals.add"/></a>
                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Description</th>
                        <th>Calories</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <c:forEach items="${mealList}" var="meal">
                        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
                        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                            <td>
                                    <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                    <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                    ${fn:formatDateTime(meal.dateTime)}
                            </td>
                            <td>${meal.description}</td>
                            <td>${meal.calories}</td>
                            <td><a class="btn btn-xs btn-primary edit" id="${meal.id}">Edit</a></td>
                            <td><a class="btn btn-xs btn-danger delete" id="${meal.id}">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
    <jsp:include page="fragments/footer.jsp"/>

    <div class="modal fade" id="editRow">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h2 class="modal-title"><fmt:message key="${meal.isNew() ? 'meals.add' : 'meals.edit'}"/></h2>
                </div>
                <div class="modal-body">
                    <%--<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.UserMeal" scope="request"/>--%>
                    <form class="form-horizontal" method="post" id="detailsForm">
                        <input type="text" hidden="hidden" id="id" name="id" value="${meal.id}">
                        <div class="form-group">
                            <label for="dateTime" class="control-label col-xs-3">Date</label>
                            <div class="col-xs-9">
                                <input type="datetime-local" class="form-control" id="dateTime" name="dateTime" placeholder="Date"
                                       value="${meal.dateTime}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="description" class="control-label col-xs-3">Description</label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="description" name="description" placeholder="Description"
                                       value="${meal.description}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="calories" class="control-label col-xs-3">Calories</label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="calories" name="description" placeholder="2000"
                                       value="${meal.calories}">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-offset-3 col-xs-9">
                                <button type="submit" class="btn btn-primary">Save</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript" src="webjars/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript">

    var ajaxUrl = 'ajax/profile/meals/';
    var datatableApi;

    $(function () {
        datatableApi = $('#datatable').DataTable({
            "paging": false,
            "info": false,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        });
        $('#filterForm').submit(function () {
            updateTable();
            return false;
        });
        makeEditable();
    });

    function updateTable() {
        $.ajax({
            type: "POST",
            url: ajaxUrl + 'filter',
            data: $('#filterForm').serialize(),
            success: function (data) {
                updateTableByData(data);
            }
        });
        return false;
    }
</script>
</html>
