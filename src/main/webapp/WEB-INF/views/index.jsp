<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Parking management system</title>
    <link rel="icon" href="../../resources/img/parking_logo.png">
    <link href="<c:url value="../../resources/css/custom.css"/>" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.15/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
    <link href="<c:url value="../../resources/lib/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css"/>"
          rel="stylesheet">
</head>
<body>
<nav class="navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-left" href="#">
                <img src="../../resources/img/parking_logo.png" alt="Parking Logo" style="width:70px;height:50px;">
            </a>
        </div>
        <div class="collapse navbar-collapse" id="navBar">
            <ul class="nav navbar-nav">
                <li class="active"><a data-toggle="tab" id="dashboardTab" href="#dashboard">Dashboard</a></li>
                <li><a data-toggle="tab" id="reservationTab" href="#reservations">Reservations</a>
                <li><a data-toggle="tab" id="ownerTab" href="#owners">Owners</a></li>
                <li><a data-toggle="tab" id="vehicleTab" href="#vehicles">Vehicles</a></li>
                <li><a data-toggle="tab" id="parkingTab" href="#parkings">Parking</a>
                </li>
            </ul>
            <%--<ul class="nav navbar-nav navbar-right">--%>
            <%--<li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>--%>
            <%--</ul>--%>
        </div>
    </div>
</nav>

<div class="container-fluid text-center">
    <div class="row content">
        <div class="col-sm-2 sidenav"></div>
        <div class="col-sm-8 text-left">
            <h1><a href="<c:url value="/"/>">Parking Management System</a></h1>
            <div class="tab-content">
                <div id="dashboard" class="tab-pane fade in active">
                    <h2>Dashboard</h2>
                    <div class="col-lg-12">
                        <div class="panel panel-warning">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-money fa-fw"></i>Active Reservations for
                                    today</h3>
                            </div>
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-hover table-striped"
                                           id="dashboardReservationTable" width="100%">
                                        <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Start</th>
                                            <th>End</th>
                                            <th>Vehicle</th>
                                            <th>Parking</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="form-group"></div>
                                <div class="text-right">
                                    <a id="reservationLink" href="#">View All Reservations <i
                                            class="fa fa-arrow-circle-right"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-money fa-fw"></i>Show income from Owners</h3>
                            </div>
                            <div class="panel-body">
                                <form class="form-horizontal" role="form">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label requiredField"
                                               for="dashboardStartDateInput">
                                            Start date
                                            <span class="asteriskField">
                                           *
                                        </span>
                                        </label>
                                        <div id="dashboardDateStart" class="col-sm-10 ">
                                            <input class="form-control" data-format="dd/MM/yyyy HH:mm:ss"
                                                   id="dashboardStartDateInput"
                                                   placeholder="yyyy-mm-dd HH:mm:ss"
                                                   type="text" required/>
                                            <span class="add-on">
                                          <i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
                                        </span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label requiredField"
                                               for="dashboardEndDateInput">
                                            End date
                                            <span class="asteriskField">
                                                   *
                                                </span>
                                        </label>
                                        <div id="dashboardDateEnd" class="col-sm-10 ">
                                            <input class="form-control" data-format="dd/MM/yyyy HH:mm:ss"
                                                   id="dashboardEndDateInput" placeholder="yyyy-mm-dd HH:mm:ss"
                                                   type="text" disabled required/>
                                            <span class="add-on">
                                                    <i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
                                                </span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label" for="dashboardOwnerInput">
                                            Owner
                                        </label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control"
                                                   id="dashboardOwnerInput" placeholder="All owners"
                                                   list="dashboardOwnerList"
                                                   required disabled/>
                                            <datalist id="dashboardOwnerList">
                                            </datalist>
                                        </div>
                                    </div>
                                    <div class="col-lg-14">
                                        <input type="text" id="income_result" class="form-control"
                                               style="text-align:center;display:none;">
                                        <div class="form-group"></div>
                                    </div>
                                    <div class="col-lg-14">
                                        <button id="show_income" type="button"
                                                class="btn btn-primary btn-block">
                                            Show
                                        </button>
                                        <div class="form-group"></div>
                                    </div>
                                    <div class="text-right">
                                        <a href="#" id="dashboardOwnersLink">View All Owners<i
                                                class="fa fa-arrow-circle-right"></i></a>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-12">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-money fa-fw"></i>Last Activities</h3>
                            </div>
                            <div class="panel-body">
                                <table class="table table-striped table-condensed" id="dashboardActivityTable"
                                       cellspacing="0"
                                       width="100%">
                                    <thead>
                                    <tr>
                                        <th>Object</th>
                                        <th>ID</th>
                                        <th>Action</th>
                                        <th>When</th>
                                    </tr>
                                    </thead>
                                    <tr>
                                    </tr>
                                    <tbody id="activTableBody">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="owners" class="tab-pane fade">
                    <h2>Owners</h2>
                    <table class="table table-striped table-bordered" id="activeOwnerTable" cellspacing="0"
                           width="100%">
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
                    <h3>Inactive Owners</h3>
                    <table class="table table-striped table-bordered" id="inactiveOwnerTable" cellspacing="0"
                           width="100%">
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
                                    <button id="deleteOwnerButton" type="button" class="btn btn-default">
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
                    <table class="table table-striped table-bordered" id="activeVehicleTable" cellspacing="0"
                           width="100%">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Owner</th>
                            <th>Number</th>
                            <th>Model</th>
                            <th>Type</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                    </table>
                    <h3>Inactive Vehicles</h3>
                    <table class="table table-striped table-bordered" id="inactiveVehicleTable" cellspacing="0"
                           width="100%">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Owner</th>
                            <th>Number</th>
                            <th>Model</th>
                            <th>Type</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                    </table>
                    <div id="deleteVehicleModal" class="modal fade" role="dialog">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Delete <var id="number">number</var> vehicle</h4>
                                </div>
                                <div class="modal-body">
                                    <p>Are you sure you want to delete vehicle?</p>
                                </div>
                                <div class="modal-footer">
                                    <button id="deleteVehicleButton" type="button" class="btn btn-default">
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
                <div id="reservations" class="tab-pane fade">
                    <h2>Reservations</h2>
                    <table class="table table-striped table-bordered" id="activeReservationTable" cellspacing="0"
                           width="100%">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Start</th>
                            <th>End</th>
                            <th>Owner</th>
                            <th>Vehicle</th>
                            <th>Parking</th>
                            <th>Garage</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                    </table>
                    <h3>Inactive Reservations</h3>
                    <table class="table table-striped table-bordered" id="inactiveReservationTable" cellspacing="0"
                           width="100%">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Start</th>
                            <th>End</th>
                            <th>Owner</th>
                            <th>Vehicle</th>
                            <th>Parking</th>
                            <th>Garage</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                    </table>
                    <div id="deleteReservationModal" class="modal fade" role="dialog">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Delete reservation <var id="reservId">id</var></h4>
                                </div>
                                <div class="modal-body">
                                    <p>Are you sure you want to delete reservation?</p>
                                </div>
                                <div class="modal-footer">
                                    <button id="deleteReservationButton" type="button" class="btn btn-default">
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
                <div id="parkings" class="tab-pane fade">
                    <h2>Parking</h2>
                    <table class="table table-striped table-bordered" id="activeParkingTable" cellspacing="0"
                           width="100%">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Address</th>
                            <th>Garages quantity</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                    </table>
                    <h3>Inactive Parking</h3>
                    <table class="table table-striped table-bordered" id="inactiveParkingTable" cellspacing="0"
                           width="100%">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Address</th>
                            <th>Garages quantity</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                    </table>
                    <div id="deleteParkingModal" class="modal fade" role="dialog">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Delete parking <var id="parkingId">id</var>
                                    </h4>
                                </div>
                                <div class="modal-body">
                                    <p>Are you sure you want to delete parking?</p>
                                </div>
                                <div class="modal-footer">
                                    <button id="deleteParkingButton" type="button" class="btn btn-default">
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
            </div>
        </div>
        <div class="col-sm-2 sidenav">
            <button id="add_owner" type="button" class="btn btn-primary btn-block btn-custom"
                    data-toggle="modal"
                    data-target="#ownerModal"
                    style="display:none;">
                Add Owner
            </button>
            <!-- Create Owner Modal -->
            <div class="modal fade text-left" id="ownerModal" tabindex="-1" role="dialog"
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
                                    <button type="button" id="closeForm" class="btn btn-default"
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
            <button id="add_vehicle" type="button" class="btn btn-primary btn-block btn-custom"
                    data-toggle="modal"
                    data-target="#vehicleModal"
                    style="display:none;">
                Add Vehicle
            </button>
            <!-- Create Vehicle Modal -->
            <div class="modal fade text-left" id="vehicleModal" tabindex="-1" role="dialog"
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
                            <h4 class="modal-title" id="vehicleModalLabel">
                                <var id="vehicleModalHeader">Create New Vehicle</var>
                            </h4>
                        </div>
                        <!-- Vehicle Modal Body -->
                        <div class="modal-body">
                            <form class="form-horizontal" role="form" id="vehicleForm">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label requiredField" for="vehicleOwnerInput">
                                        Owner
                                        <span class="asteriskField">
                                           *
                                        </span>
                                    </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control"
                                               id="vehicleOwnerInput" placeholder="Select owner.." list="ownerList"
                                               required/>
                                        <datalist id="ownerList">
                                        </datalist>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label requiredField" for="vehicleNumberInput">
                                        Number
                                        <span class="asteriskField">
                                           *
                                        </span>
                                    </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control"
                                               id="vehicleNumberInput" placeholder="Enter vehicle number" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label requiredField" for="vehicleModelInput">
                                        Model
                                    </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control"
                                               id="vehicleModelInput" placeholder="Enter vehicle model"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label requiredField" for="vehicleModelInput">
                                        Type
                                    </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control"
                                               id="vehicleType" placeholder="Select vehicle type.."
                                               list="vehicleTypeList"/>
                                        <datalist id="vehicleTypeList">
                                            <option value="ELECTRO">
                                            <option value="HIBRID">
                                            <option value="GASOLINE">
                                            <option value="DIESEL">
                                        </datalist>
                                    </div>
                                </div>
                                <!--vehicle Modal Footer -->
                                <div class="modal-footer">
                                    <button type="button" id="closeVehicleForm" class="btn btn-default"
                                            data-dismiss="modal">
                                        Close
                                    </button>
                                    <button type="submit" id="submitVehicleForm" class="btn btn-primary">
                                        Submit
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <button id="add_reservation" type="button" class="btn btn-primary btn-block btn-custom" data-toggle="modal"
                    data-target="#reservationModal" style="display:none;">
                Make Reservation
            </button>
            <!-- Create Reservation Modal -->
            <div class="modal fade text-left" id="reservationModal" tabindex="-1" role="dialog"
                 aria-labelledby="reservationModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <!-- Modal Header -->
                        <div class="modal-header">
                            <button type="button" class="close"
                                    data-dismiss="modal">
                                <span aria-hidden="true">&times;</span>
                                <span class="sr-only">Close</span>
                            </button>
                            <h4 class="modal-title" id="reservationModalLabel">
                                <var id="reservationModalHeader">Make New Reservation</var>
                            </h4>
                        </div>
                        <!-- Reservation Modal Body -->
                        <div class="modal-body">
                            <form class="form-horizontal" role="form" id="reservationForm">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label requiredField" for="startDateInput">
                                        Start date
                                        <span class="asteriskField">
                                           *
                                        </span>
                                    </label>
                                    <div id="datetimepickerStart" class="col-sm-10">
                                        <input class="form-control" data-format="dd/MM/yyyy HH:mm:ss"
                                               id="startDateInput" name="startDate" placeholder="yyyy-mm-dd HH:mm:ss"
                                               type="text" required/>
                                        <span class="add-on">
                                          <i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
                                        </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label requiredField" for="endDateInput">
                                        End date
                                        <span class="asteriskField">
                                           *
                                        </span>
                                    </label>
                                    <div id="datetimepickerEnd" class="col-sm-10 ">
                                        <input class="form-control" data-format="dd/MM/yyyy HH:mm:ss"
                                               id="endDateInput" name="startDate" placeholder="yyyy-mm-dd HH:mm:ss"
                                               type="text" required disabled/>
                                        <span class="add-on">
                                          <i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
                                        </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label requiredField" for="reservParkingInput">
                                        Parking
                                        <span class="asteriskField">
                                           *
                                        </span>
                                    </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control"
                                               id="reservParkingInput" placeholder="Select parking.."
                                               list="reservParkingList"
                                               required/>
                                        <datalist id="reservParkingList">
                                        </datalist>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label requiredField" for="reservGarageInput">
                                        Garage
                                        <span class="asteriskField">
                                           *
                                        </span>
                                    </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control"
                                               id="reservGarageInput" placeholder="Select garage.."
                                               list="reservGarageList"
                                               required/>
                                        <datalist id="reservGarageList">
                                        </datalist>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label requiredField" for="reservOwnerInput">
                                        Owner
                                        <span class="asteriskField">
                                           *
                                        </span>
                                    </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control"
                                               id="reservOwnerInput" placeholder="Select owner.." list="reservOwnerList"
                                               required/>
                                        <datalist id="reservOwnerList">
                                        </datalist>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label requiredField" for="reservVehicleInput">
                                        Vehicle
                                        <span class="asteriskField">
                                           *
                                        </span>
                                    </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control"
                                               id="reservVehicleInput" placeholder="Select vehicle.."
                                               list="reservVehicleList" disabled
                                               required/>
                                        <datalist id="reservVehicleList">
                                        </datalist>
                                    </div>
                                </div>
                                <!--reservation Modal Footer -->
                                <div class="modal-footer">
                                    <button type="button" id="closeReservationForm" class="btn btn-default"
                                            data-dismiss="modal">
                                        Close
                                    </button>
                                    <button type="submit" id="submitReservationForm" class="btn btn-primary">
                                        Submit
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <button id="add_parking" type="button" class="btn btn-primary btn-block btn-custom"
                    data-toggle="modal"
                    data-target="#parkingModal"
                    style="display:none;">
                Add Parking
            </button>
            <!-- Create Parking Modal -->
            <div class="modal fade text-left" id="parkingModal" tabindex="-1" role="dialog"
                 aria-labelledby="parkingModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <!-- Modal Header -->
                        <div class="modal-header">
                            <button type="button" class="close"
                                    data-dismiss="modal">
                                <span aria-hidden="true">&times;</span>
                                <span class="sr-only">Close</span>
                            </button>
                            <h4 class="modal-title" id="parkingModalLabel">
                                <var id="parkingModalHeader">Create New Parking</var>
                            </h4>
                        </div>
                        <!-- Parking Modal Body -->
                        <div class="modal-body">
                            <form class="form-horizontal" role="form" id="parkingForm">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label requiredField" for="parkingAddressInput">
                                        Address
                                        <span class="asteriskField">
                                           *
                                        </span>
                                    </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control"
                                               id="parkingAddressInput" placeholder="Enter parking address" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label requiredField" for="parkingGaragesInput">
                                        Garages quantity
                                        <span class="asteriskField">
                                           *
                                        </span>
                                    </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control"
                                               id="parkingGaragesInput"
                                               placeholder="How many garages are on this parking?" required/>
                                    </div>
                                </div>
                                <!--parking Modal Footer -->
                                <div class="modal-footer">
                                    <button type="button" id="closeParkingForm" class="btn btn-default"
                                            data-dismiss="modal">
                                        Close
                                    </button>
                                    <button type="submit" id="submitParkingForm" class="btn btn-primary">
                                        Submit
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<footer class="container-fluid text-center">
    <h4>
        <a class="nav-link" id="dashboard_footer_link">dashboard &nbsp;&nbsp;</a>
        <a class="nav-link" id="reservation_footer_link">reservations &nbsp;&nbsp;</a>
        <a class="nav-link" id="owner_footer_link">owners &nbsp;&nbsp;</a>
        <a class="nav-link" id="vehicle_footer_link">vehicles &nbsp;&nbsp;</a>
        <a class="nav-link" id="parking_footer_link">parking</a>
    </h4>
</footer>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.loadingoverlay/latest/loadingoverlay.min.js"></script>
<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="<c:url value="../../resources/js/general.js"/>"></script>
<script type="text/javascript" src="<c:url value="../../resources/js/owner.js"/>"></script>
<script type="text/javascript" src="<c:url value="../../resources/js/vehicle.js"/>"></script>
<script type="text/javascript" src="<c:url value="../../resources/js/reservation.js"/>"></script>
<script type="text/javascript" src="<c:url value="../../resources/js/dashboard.js"/>"></script>
<script type="text/javascript" src="<c:url value="../../resources/js/parking.js"/>"></script>
<script type="text/javascript"
        src="<c:url value="../../resources/lib/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"/>"></script>
</body>
</html>