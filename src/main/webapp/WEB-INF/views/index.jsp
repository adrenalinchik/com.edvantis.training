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
    <script type="text/javascript" src="<c:url value="../../resources/js/owner.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../resources/js/vehicle.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../../resources/js/reservation.js"/>"></script>
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
                <li class="active"><a data-toggle="tab" id="homeTab" href="#home">Home</a></li>
                <li><a data-toggle="tab" id="ownerTab" href="#owners">Owners</a></li>
                <li><a data-toggle="tab" id="vehicleTab" href="#vehicles">Vehicles</a></li>
                <li><a data-toggle="tab" id="reservationTab" href="#reservations">Reservations</a>
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
                                    <h4 class="modal-title">Delete <var id="id">id</var> vehicle</h4>
                                </div>
                                <div class="modal-body">
                                    <p>Are you sure you want to delete vehicle?</p>
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
            </div>
        </div>
        <div class="col-sm-2 sidenav">
            <button id="add_owner" type="button" class="btn btn-primary btn-block"
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
            <button id="add_vehicle" type="button" class="btn btn-primary btn-block"
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
            <button id="add_reservation" type="button" class="btn btn-primary btn-block" style="display:none;">
                Make Reservation
            </button>
        </div>
    </div>
</div>
<footer class="container-fluid text-center">
    <p>Footer Text</p>
</footer>
</body>
</html>