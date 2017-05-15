<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>parking-management-system</title>
    <link href="<c:url value="../../resources/css/custom.css"/>" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.15/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap.min.js"></script>
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
    <script type="text/javascript" src="<c:url value="../../resources/js/parking.js"/>"></script>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Logo</a>
        </div>
        <div class="collapse navbar-collapse" id="navBar">
            <ul class="nav navbar-nav">
                <li class="active"><a data-toggle="tab" onclick="makeAddButtonsInvisible();" href="#home">Home</a></li>
                <li><a data-toggle="tab" onclick="addOwnerButtonVisible();" href="#owners">Owners</a></li>
                <li><a data-toggle="tab" onclick="addVehicleButtonVisible();" href="#vehicles">Vehicles</a></li>
                <li><a data-toggle="tab" onclick="makeReservationButtonVisible();" href="#reservations">Reservations</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container-fluid text-center">
    <div class="row content">
        <div class="col-sm-2 sidenav"></div>
        <div class="col-sm-8 text-left">
            <h1><a href="<c:url value="/"/>">Parking Management System</a></h1>
            <p>Recommended: Using a Web Developer tool such a Firebug to inspect the client/server interaction</p>
            <div class="tab-content">
                <div id="home" class="tab-pane fade in active">
                    <h2>Home</h2>
                </div>
                <div id="owners" class="tab-pane fade">
                    <h2>Owners</h2>
                    <table class="table table-striped table-bordered" id="ownerTable" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Gender</th>
                            <th>Age</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                    </table>
                    <div id="deleteOwnerModal" class="modal fade" role="dialog">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Delete <var id="name">name</var></h4>
                                </div>
                                <div class="modal-body">
                                    <p>Are you really want to delete owner?</p>
                                </div>
                                <div class="modal-footer">
                                    <button id="deleteOwnerButton" type="button" class="btn btn-default"
                                            onclick="deleteOwner();">
                                        Yes
                                    </button>
                                    <button type="button" class="btn btn-primary" data-dismiss="modal">
                                        No
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="vehicles" class="tab-pane fade">
                    <h2>Vehicles</h2>
                    <p>
                        In this section you can see all vehicles in the all parkings. Also you can filter vehicles
                        by owner,
                        parking, vehicle type.
                    </p>
                    <ul>

                    </ul>
                </div>
                <div id="reservations" class="tab-pane fade">
                    <h2>Reservations</h2>
                    <p>
                        In this section you can make reservation for specific owner.
                    </p>
                    <ul>

                    </ul>
                </div>
            </div>
        </div>
        <div class="col-sm-2 sidenav">
            <button id="add_owner" type="button" onclick="clearForm('#ownerModal');" class="btn btn-primary btn-block"
                    data-toggle="modal"
                    data-target="#ownerModal"
                    style="display:none;">
                Add Owner
            </button>
            <!-- Create Owner Modal -->
            <div class="modal fade" id="ownerModal" tabindex="-1" role="dialog"
                 aria-labelledby="ownerModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <!-- Modal Header -->
                        <div class="modal-header">
                            <button type="button" class="close"
                                    data-dismiss="modal">
                                <span aria-hidden="true">&times;</span>
                                <span class="sr-only">Close</span>
                            </button>
                            <h4 class="modal-title" id="ownerModalLabel">
                                <var id="ownerModalHeader">Create New Owner</var>
                            </h4>
                        </div>
                        <!-- Modal Body -->
                        <div class="modal-body">
                            <form class="form-horizontal" role="form" id="ownerForm">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label requiredField" for="ownerInputFirstName">
                                        First Name
                                        <span class="asteriskField">
                                           *
                                        </span>
                                    </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control"
                                               id="ownerInputFirstName" placeholder="Enter first name" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label requiredField" for="ownerInputLastName">
                                        Last Name
                                        <span class="asteriskField">
                                           *
                                        </span>
                                    </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control"
                                               id="ownerInputLastName" placeholder="Enter last name" required/>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label class="col-sm-2 control-label requiredField">
                                        Gender
                                        <span class="asteriskField">
                                           *
                                        </span>
                                    </label>
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <div class="radio">
                                            <label class="radio">
                                                <input name="radio" type="radio" id="male" value="Male"/>
                                                Male
                                            </label>
                                        </div>
                                        <div class="radio">
                                            <label class="radio">
                                                <input name="radio" type="radio" id="female" value="Female"/>
                                                Female
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="dateInput">
                                        Date of Birth
                                    </label>
                                    <div class="col-sm-10">
                                        <input class="form-control" id="dateInput" name="date" placeholder="yyyy-mm-dd"
                                               type="text"/>
                                    </div>
                                </div>
                                <!-- Modal Footer -->
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default"
                                            data-dismiss="modal">
                                        Close
                                    </button>
                                    <button type="submit" id="submitForm" class="btn btn-primary">
                                        Submit
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <button id="add_vehicle" type="button" class="btn btn-primary btn-block" style="display:none;">
                Add Vehicle
            </button>
            <button id="add_reservation" type="button" class="btn btn-primary btn-block" style="display:none;">
                Make Reservation
            </button>
        </div>
    </div>
</div>

<footer class="container-fluid text-center">
    <p>Footer Text</p>
</footer>
<script>
    <%--var dataTable;--%>
    <%--var rowData;--%>
    <%--$(document).ready(function () {--%>
        <%--dataTable = $('#ownerTable').DataTable({--%>
            <%--"order": [[0, "asc"]],--%>
            <%--"ajax": {--%>
                <%--"url": "/parking/api/owners",--%>
                <%--"dataSrc": ""--%>
            <%--},--%>
            <%--"columns": [--%>
                <%--{"data": "id"},--%>
                <%--{"data": "firstName"},--%>
                <%--{"data": "lastName"},--%>
                <%--{--%>
                    <%--"data": "gender",--%>
                    <%--"defaultContent": ""--%>
                <%--},--%>
                <%--{--%>
                    <%--"data": "dob",--%>
                    <%--"defaultContent": "",--%>
                    <%--"render": function (data) {--%>
                        <%--return (Math.floor((new Date() - new Date(data)) / (365.25 * 24 * 60 * 60 * 1000))).toString();--%>
                    <%--}--%>
                <%--},--%>
                <%--{--%>
                    <%--"data": "",--%>
                    <%--"orderable": false,--%>
                    <%--"defaultContent": "<button role='link' class='edit-owner-button btn-link'>edit</button>|  <a>delete</a>"--%>
                <%--}--%>
            <%--]--%>
        <%--});--%>
        <%--$('#ownerTable tbody').on('click', 'a', function () {--%>
            <%--rowData = dataTable.row($(this).parents('tr')).data();--%>
            <%--closeOpenModal('#deleteOwnerModal');--%>
            <%--$('#name').text(rowData.firstName + "  " + rowData.lastName);--%>
        <%--});--%>

        <%--$('#ownerTable tbody').on('click', 'button', function () {--%>
            <%--rowData = dataTable.row($(this).parents('tr')).data();--%>
            <%--closeOpenModal('#ownerModal');--%>
            <%--populateOwnerDataModal(rowData);--%>
        <%--});--%>
    <%--});--%>

    <%--function populateOwnerDataModal(data) {--%>
        <%--$('#ownerModalHeader').text("Edit " + data.firstName + " " + data.lastName);--%>
        <%--$('#ownerInputFirstName').val(data.firstName);--%>
        <%--$('#ownerInputLastName').val(data.lastName);--%>
        <%--var gender = data.gender.toString();--%>
        <%--if (gender == "MALE") {--%>
            <%--document.getElementById("male").checked = true;--%>
        <%--}--%>
        <%--if (gender == "FEMALE") {--%>
            <%--document.getElementById("female").checked = true;--%>
        <%--}--%>
        <%--$('#dateInput').val(data.dob);--%>
    <%--}--%>

    <%--function deleteOwner() {--%>
        <%--//debugger;--%>
        <%--$.ajax({--%>
            <%--url: '/parking/api/owner/delete/' + rowData.id,--%>
            <%--type: 'DELETE'--%>
        <%--}).done(function (response) {--%>
            <%--console.log("Response:  " + response);--%>
            <%--closeOpenModal('#deleteOwnerModal');--%>
            <%--updateOwnerTable();--%>
        <%--});--%>
    <%--}--%>

    <%--function updateOwnerTable() {--%>
        <%--dataTable.ajax.reload();--%>
    <%--}--%>
    <%--function getTableRowId() {--%>
        <%--return rowData.id.toString();--%>
    <%--}--%>
<%--</script>--%>

<%--<script type="text/javascript">--%>
    <%--$(document).ready(function () {--%>
        <%--var date_input = $('input[name="date"]');--%>
        <%--date_input.datepicker({--%>
            <%--format: 'yyyy-mm-dd',--%>
            <%--todayHighlight: true,--%>
            <%--autoclose: true--%>
        <%--})--%>
    <%--});--%>

    <%--$('#ownerForm').submit(function (event) {--%>
        <%--var data = { //grab data from form inputs--%>
            <%--firstName: $('#ownerInputFirstName')[0].value,--%>
            <%--lastName: $('#ownerInputLastName')[0].value,--%>
            <%--gender: $('input[name="radio"]:checked')[0].value.toUpperCase(),--%>
            <%--dob: $('#dateInput')[0].value--%>
        <%--};--%>
        <%--event.preventDefault(); // prevent default page reload--%>
        <%--var ownerFormHeader = document.getElementById('ownerModalLabel').innerText;--%>
        <%--if (ownerFormHeader.indexOf('Create') > -1) {--%>
            <%--$.ajax({--%>
                <%--headers: {--%>
                    <%--'Content-Type': 'application/json'--%>
                <%--},--%>
                <%--url: '/parking/api/owners/createOwner',--%>
                <%--type: 'POST',--%>
                <%--data: JSON.stringify(data)--%>
            <%--}).done(function () {--%>
                <%--closeOpenModal('#ownerModal');--%>
                <%--clearForm('#ownerForm');--%>
                <%--updateOwnerTable();--%>
            <%--});--%>
        <%--}else if(ownerFormHeader.indexOf('Edit') > -1){--%>
            <%--data.id = getTableRowId();--%>
            <%--$.ajax({--%>
                <%--headers: {--%>
                    <%--'Content-Type': 'application/json'--%>
                <%--},--%>
                <%--url: '/parking/api/owner/updateOwner',--%>
                <%--type: 'PUT',--%>
                <%--data: JSON.stringify(data)--%>
            <%--}).done(function (response) {--%>
                <%--console.log("Response:  " + response);--%>
                <%--closeOpenModal('#ownerModal');--%>
                <%--updateOwnerTable();--%>
            <%--});--%>
        <%--}--%>
    <%--});--%>

    <%--function clearForm(form) {--%>
        <%--$(':input', form).each(function () {--%>
            <%--var type = this.type;--%>
            <%--var tag = this.tagName.toLowerCase(); // normalize case--%>
            <%--if (type == 'text' || type == 'password' || tag == 'textarea')--%>
                <%--this.value = "";--%>
            <%--else if (type == 'checkbox' || type == 'radio')--%>
                <%--this.checked = false;--%>
            <%--else if (tag == 'select')--%>
                <%--this.selectedIndex = -1;--%>
        <%--});--%>
    <%--}--%>
    <%--function closeOpenModal(modalLocator) {--%>
        <%--$(modalLocator).modal('toggle');--%>
    <%--}--%>
<%--</script>--%>

<%--<script type="text/javascript">--%>
    <%--function makeAddButtonsInvisible() {--%>
        <%--addOwnerButtonInvisible();--%>
        <%--addVehicleButtonInvisible();--%>
        <%--makeReservationButtonInvisible();--%>
    <%--}--%>
    <%--function addOwnerButtonVisible() {--%>
        <%--addVehicleButtonInvisible();--%>
        <%--makeReservationButtonInvisible();--%>
        <%--document.getElementById("add_owner").style.display = 'block';--%>

    <%--}--%>
    <%--function addOwnerButtonInvisible() {--%>
        <%--document.getElementById("add_owner").style.display = 'none';--%>
    <%--}--%>
    <%--function addVehicleButtonVisible() {--%>
        <%--addOwnerButtonInvisible();--%>
        <%--makeReservationButtonInvisible();--%>
        <%--document.getElementById("add_vehicle").style.display = 'block';--%>

    <%--}--%>
    <%--function addVehicleButtonInvisible() {--%>
        <%--document.getElementById("add_vehicle").style.display = 'none';--%>
    <%--}--%>
    <%--function makeReservationButtonVisible() {--%>
        <%--addOwnerButtonInvisible();--%>
        <%--addVehicleButtonInvisible();--%>
        <%--document.getElementById("add_reservation").style.display = 'block';--%>

    <%--}--%>
    <%--function makeReservationButtonInvisible() {--%>
        <%--document.getElementById("add_reservation").style.display = 'none';--%>
    <%--}--%>
</script>
</body>
</html>