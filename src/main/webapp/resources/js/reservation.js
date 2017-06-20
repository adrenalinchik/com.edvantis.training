var activeReservationDataTable;
var inactiveReservationDataTable;
var rowData;
var data;
var owners = [];

function updateActiveReservationTable() {
    activeReservationDataTable.ajax.reload();
}

function updateInactiveReservationTable() {
    inactiveReservationDataTable.ajax.reload();
}

function updateCreateReservationAjax(type, url, data) {
    return $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        url: '/parking/api/reservation/' + url,
        type: type,
        data: JSON.stringify(data)
    })
}

function getOwner(id) {
    for (var i = 0; i < owners.length; i++) {
        if (owners[i].id === id) {
            return owners[i];
        }
    }
}

function getOwners() {
    $.ajax({
        url: '/parking/api/owners/',
    }).done(function (obj) {
        owners = obj;
    });
}

function getOwnerVehiclesToDataList(dataList, id) {
    $('#reservVehicleList').empty();
    var vehicleList = document.getElementById(dataList);
    $.ajax({
        url: '/parking/api/vehicles/owner/' + id,
    }).done(function (vehicles) {
        vehicles.map(function (vehicle) {
            var vehicleOption = document.createElement('option');
            vehicleOption.value = vehicle.number;
            vehicleList.appendChild(vehicleOption);
        })
    });
}

function getParkingsToDataList(dataList) {
    $('#reservParkingList').empty();
    var parkingList = document.getElementById(dataList);
    $.ajax({
        url: '/parking/api/parkings',
    }).done(function (parkings) {
        parkings.map(function (parking) {
            var parkingOption = document.createElement('option');
            parkingOption.value = parking.id;
            parkingList.appendChild(parkingOption);
        })
    });
}

function getGaragesToDataList(dataList, start, end, parkingId) {
    $('#reservGarageList').empty();
    var garageList = document.getElementById(dataList);
    var url = '/parking/api/reservation/availableGarages/parking/' + parkingId + '?from=' + start + '&to=' + end;
    $.ajax({
        url: url,
    }).done(function (garages) {
        garages.map(function (garage) {
            var garageOption = document.createElement('option');
            garageOption.value = garage.id;
            garageList.appendChild(garageOption);
        })
    });
}

function getOwnersToDataList(dataList) {
    $('#reservOwnerList').empty();
    var ownerList = document.getElementById(dataList);
    $.ajax({
        url: '/parking/api/owners/active',
    }).done(function (activeOwners) {
        owners = activeOwners;
        activeOwners.map(function (owner) {
            var option = document.createElement('option');
            option.value = owner.firstName + " " + owner.lastName;
            ownerList.appendChild(option);
        })
    });
}

function getOwnerId(ownerInput) {
    var id;
    var ownerName = ownerInput.split(" ");
    for (var i = 0; i < owners.length; i++) {
        var first = owners[i].firstName;
        var last = owners[i].lastName;
        if (first === ownerName[0] && last === ownerName[1]) {
            id = owners[i].id;
        }
    }
    return id;
}

function getStartDateValue() {
    let startDate = $('#startDateInput')[0].value;
    return 0 < startDate.length && startDate.length < 19 ? startDate + ":00" : startDate;
}
function getEndDateValue() {
    let endDate = $('#endDateInput')[0].value;
    return 0 < endDate.length && endDate.length < 19 ? endDate + ":00" : endDate;
}

$(document).ready(function () {
    getOwners();
    activeReservationDataTable = $('#activeReservationTable').DataTable({
        "order": [[0, "asc"]],
        "ajax": {
            "url": "/parking/api/reservations/active",
            "dataSrc": "",
            "deferRender": true
        },
        "columns": [
            {"data": "id"},
            {"data": "begin"},
            {"data": "end"},
            {
                "data": "ownerId",
                "defaultContent": "",
                "render": function (id) {
                    var owner = getOwner(id);
                    return owner === undefined ? "inactive" : owner.firstName + " " + owner.lastName;
                }
            },
            {"data": "vehicleNumber"},
            {
                "data": "parkingId",
                "defaultContent": "",

            },
            {
                "data": "garageId",
                "defaultContent": "",

            },
            {
                "data": "",
                "orderable": false,
                "defaultContent": "<button role='link' class='edit-owner-button btn-link'>Edit</button>|  <a>Inactivate</a>"
            }
        ]
    });

    inactiveReservationDataTable = $('#inactiveReservationTable').DataTable({
        "order": [[0, "asc"]],
        "ajax": {
            "url": "/parking/api/reservations/inactive",
            "dataSrc": ""
        },
        "columns": [
            {"data": "id"},
            {"data": "begin"},
            {"data": "end"},
            {
                "data": "ownerId",
                "defaultContent": "",
                "render": function (id) {
                    var owner = getOwner(id);
                    return owner === undefined ? "inactive" : owner.firstName + " " + owner.lastName;
                }
            },
            {
                "data": "vehicleNumber",
                "defaultContent": "",

            },
            {
                "data": "parkingId",
                "defaultContent": "",

            },
            {
                "data": "garageId",
                "defaultContent": "",

            },
            {
                "data": "",
                "orderable": false,
                "defaultContent": "<button role='link' class='edit-owner-button btn-link'>Delete</button>"
            }
        ]
    });

    $('#inactiveReservationTable tbody').on('click', 'button', function () {
        rowData = inactiveReservationDataTable.row($(this).parents('tr')).data();
        closeOpenModal('#deleteReservationModal');
        $('#reservId').text(rowData.id);
    });

    $('#activeReservationTable tbody').on('click', 'button', function () {
        rowData = activeReservationDataTable.row($(this).parents('tr')).data();
        closeOpenModal('#reservationModal');
        populateReservationDataModal(rowData);
    });

    $('#activeReservationTable tbody').on('click', 'a', function () {
        rowData = activeReservationDataTable.row($(this).parents('tr')).data();
        rowData.state = 'INACTIVE';
        updateCreateReservationAjax('put', 'updateReservation', rowData).done(function () {
            updateActiveReservationTable();
            updateInactiveReservationTable();
        });
    });

    $('#startDateInput').datetimepicker({
        showSeconds: true,
        dateFormat: "yyyy-mm-dd",
        timeFormat: "HH:mm:ss",
        todayHighlight: true,
        autoclose: true,
    });
    $('#endDateInput').datetimepicker({
        showSecond: true,
        dateFormat: "yyyy-mm-dd",
        timeFormat: "HH:mm:ss",
        todayHighlight: true,
        autoclose: true
    });

    $('#startDateInput').change(function () {
        var startdateInput = $('#startDateInput');
        var endDateInput = $('#endDateInput');
        if (startdateInput[0].value.length > 0) {
            endDateInput.prop('disabled', false);
        } else {
            endDateInput.val('');
            endDateInput.prop('disabled', true);
        }
    });
    $('#endDateInput').change(function () {
        var reservOwnerInput = $('#reservOwnerInput');
        var reservParkingInput = $('#reservParkingInput');
        var endDateInput = $('#endDateInput');
        if (endDateInput[0].value.length > 0) {
            getParkingsToDataList('reservParkingList');
            getOwnersToDataList('reservOwnerList');
            reservOwnerInput.prop('disabled', false);
            reservParkingInput.prop('disabled', false);
            reservParkingInput.val('');
        } else {
            reservOwnerInput.val('');
            reservOwnerInput.prop('disabled', true);
            reservParkingInput.val('');
            reservParkingInput.prop('disabled', true);
        }
    });

    $('#reservParkingInput').change(function () {
        let parkingInput = $('#reservParkingInput');
        let garageInput = $('#reservGarageInput');
        let parkingId = parkingInput[0].value;
        let startDate = getStartDateValue();
        let endDate = getEndDateValue();
        if (parkingId.length > 0) {
            getGaragesToDataList('reservGarageList', startDate, endDate, parkingId);
            garageInput.prop('disabled', false);
            garageInput.val('');
        } else {
            garageInput.val('');
            garageInput.prop('disabled', true);
        }
    });

    $('#reservOwnerInput').change(function () {
        let ownerInput = $('#reservOwnerInput');
        let vehicleInput = $('#reservVehicleInput');
        let value = ownerInput[0].value;
        let id = getOwnerId(ownerInput[0].value);
        if (value.length > 0 && id !== undefined) {
            getOwnerVehiclesToDataList('reservVehicleList', id);
            vehicleInput.prop('disabled', false);
            vehicleInput.val('');
        } else {
            vehicleInput.val('');
            vehicleInput.prop('disabled', true);
        }
    });


    $('#add_reservation').on('click', function () {
        clearForm('#reservationForm');
        $('#reservGarageInput').prop('disabled', true);
        $('#reservParkingInput').prop('disabled', true);
        $('#reservVehicleInput').prop('disabled', true);
        $('#reservOwnerInput').prop('disabled', true);
        $('#endDateInput').prop('disabled', true);
    });

    function populateReservationDataModal(data) {
        $('#reservationModalHeader').text("Edit " + rowData.id + " reservation");
        getOwnersToDataList('reservOwnerList');
        getParkingsToDataList('reservParkingList');
        getGaragesToDataList('reservGarageList', data.begin, data.end, data.parkingId);
        getOwnerVehiclesToDataList('reservVehicleList', data.ownerId);
        $('#startDateInput').val(data.begin);
        $('#endDateInput').val(data.end);
        $('#reservParkingInput').val(data.parkingId);
        $.ajax({
            url: '/parking/api/owner/' + data.ownerId,
        }).done(function (owner) {
            owner.state === 'INACTIVE' ? $('#reservOwnerInput').val("") : $('#reservOwnerInput').val(owner.firstName + " " + owner.lastName);
        });
        $('#reservGarageInput').val(data.garageId);
        $('#reservVehicleInput').val(data.vehicleNumber);
        $('#reservGarageInput').prop('disabled', false);
        $('#reservParkingInput').prop('disabled', false);
        $('#reservVehicleInput').prop('disabled', false);
        $('#reservOwnerInput').prop('disabled', false);
        $('#endDateInput').prop('disabled', false);
    }


    $('#reservationForm').submit(function (event) {
        data = { //grab data from form inputs
            begin: getStartDateValue(),
            end: getEndDateValue(),
            parkingId: $('#reservParkingInput')[0].value,
            ownerId: getOwnerId($('#reservOwnerInput')[0].value),
            garageId: $('#reservGarageInput')[0].value,
            vehicleNumber: $('#reservVehicleInput')[0].value
        };
        event.preventDefault(); // prevent default page reload
        var reservFormHeader = document.getElementById('reservationModalLabel').innerText;
        if (reservFormHeader.indexOf('Make') > -1) {
            updateCreateReservationAjax('post', 'addReservation', data).done(function (reserv) {
                closeOpenModal('#reservationModal');
                clearForm('#reservationForm');
                updateActiveReservationTable();
                addActivityRowDashboard('reservation', reserv.id, 'created');
            });
        } else if (reservFormHeader.indexOf('Edit') > -1) {
            data.id = getTableRowId();
            updateCreateReservationAjax('put', 'updateReservation', data).done(function (reserv) {
                closeOpenModal('#reservationModal');
                updateActiveReservationTable();
                $('#reservationModalHeader').text("Make New Reservation");
                addActivityRowDashboard('reservation', reserv.id, 'updated');
            });
        }
    });
    $('#reservationModal').on('hidden.bs.modal', function () {
        $('#reservationModalHeader').text("Make New Reservation");
        $('#reservOwnerList').empty();
        $('#reservVehicleList').empty();
    });

    $('#deleteReservationButton').on('click', function () {
        $.ajax({
            url: '/parking/api/reservation/delete/' + rowData.id,
            type: 'delete'
        }).done(function () {
            closeOpenModal('#deleteReservationModal');
            updateInactiveReservationTable();
            addActivityRowDashboard('reservation', rowData.id, 'deleted');
        });
    });

});