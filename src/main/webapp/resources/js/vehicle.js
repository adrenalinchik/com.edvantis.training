/**
 * Created by taras.fihurnyak on 5/26/2017.
 */
var activeVehicleDataTable;
var inactiveVehicleDataTable;
var rowData;
var data;
var activeOwners = [];


function populateVehicleDataModal(data) {
    $('#vehicleModalHeader').text("Edit " + data.number + " vehicle");
    $('#vehicleOwnerInput').val(data.owner.firstName + " " + data.owner.lastName);
    $('#vehicleNumberInput').val(data.number);
    $('#vehicleModelInput').val(data.model);
    $('#vehicleType').val(data.carType);
}

function updateActiveVehicleTable() {
    activeVehicleDataTable.ajax.reload();
}

function updateInactiveVehicleTable() {
    inactiveVehicleDataTable.ajax.reload();
}

function getTableRowId() {
    return rowData.id.toString();
}

function clearForm(form) {
    $(':input', form).each(function () {
        var type = this.type;
        var tag = this.tagName.toLowerCase(); // normalize case
        if (type == 'text' || type == 'password' || tag == 'textarea')
            this.value = "";
        else if (type == 'checkbox' || type == 'radio')
            this.checked = false;
        else if (tag == 'select')
            this.selectedIndex = -1;
    });
}

function updateCreateVehicleAjax(type, url, data) {
    return $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        url: '/parking/api/vehicle/' + url,
        type: type,
        data: JSON.stringify(data)
    })
}

$(document).ready(function () {
    activeVehicleDataTable = $('#activeVehicleTable').DataTable({
        "order": [[0, "asc"]],
        "ajax": {
            "url": "/parking/api/vehicles/active",
            "dataSrc": ""
        },
        "columns": [
            {"data": "id"},
            {
                "data": "owner",
                "defaultContent": "",
                "render": function (data) {
                    return data.firstName + " " + data.lastName;
                }
            },
            {"data": "number"},
            {"data": "model"},
            {
                "data": "carType",
                "defaultContent": ""
            },
            {
                "data": "",
                "orderable": false,
                "defaultContent": "<button role='link' class='edit-owner-button btn-link'>Edit</button>|  <a>Inactivate</a>"
            }
        ]
    });
    inactiveVehicleDataTable = $('#inactiveVehicleTable').DataTable({
        "order": [[0, "asc"]],
        "ajax": {
            "url": "/parking/api/vehicles/inactive",
            "dataSrc": ""
        },
        "columns": [
            {"data": "id"},
            {
                "data": "owner",
                "defaultContent": "",
                "render": function (data) {
                    return data.firstName + " " + data.lastName;
                }
            },
            {"data": "number"},
            {"data": "model"},
            {
                "data": "carType",
                "defaultContent": ""
            },
            {
                "data": "",
                "orderable": false,
                "defaultContent": "<button role='link' class='edit-owner-button btn-link'>Delete</button>|  <a>Activate</a>"
            }
        ]
    });

    $('#inactiveVehicleTable tbody').on('click', 'button', function () {
        rowData = inactiveVehicleDataTable.row($(this).parents('tr')).data();
        closeOpenModal('#deleteVehicleModal');
        $('#number').text(rowData.number);
    });

    $('#activeVehicleTable tbody').on('click', 'button', function () {
        rowData = activeVehicleDataTable.row($(this).parents('tr')).data();
        closeOpenModal('#vehicleModal');
        getOwnersToDataList('ownerList');
        populateVehicleDataModal(rowData);
    });

    $('#activeVehicleTable tbody').on('click', 'a', function () {
        rowData = activeVehicleDataTable.row($(this).parents('tr')).data();
        rowData.state = 'INACTIVE';
        updateCreateVehicleAjax('put', 'updateVehicle', rowData).done(function () {
            updateActiveVehicleTable();
            updateInactiveVehicleTable();
        });
    });

    $('#inactiveVehicleTable tbody').on('click', 'a', function () {
        rowData = inactiveVehicleDataTable.row($(this).parents('tr')).data();
        rowData.state = 'ACTIVE';
        updateCreateVehicleAjax('put', 'updateVehicle', rowData).done(function () {
            updateActiveVehicleTable();
            updateInactiveVehicleTable();
        });
    });

    $('#add_vehicle').on('click', function () {
        clearForm('#vehicleModal');
        getOwnersToDataList('ownerList');
    });

    $('#vehicleModal').on('hidden.bs.modal', function () {
        $('#vehicleModalHeader').text("Create New Vehicle");
        $('#ownerList').empty();
    });

    $('#vehicleForm').submit(function (event) {
        data = { //grab data from form inputs
            owner: {"id": getOwnerId($('#vehicleOwnerInput')[0].value)},
            model: $('#vehicleNumberInput')[0].value,
            number: $('#vehicleModelInput')[0].value,
            carType: $('#vehicleType')[0].value
        };
        event.preventDefault(); // prevent default page reload
        var vehicleFormHeader = document.getElementById('vehicleModalLabel').innerText;
        if (vehicleFormHeader.indexOf('Create') > -1) {
            updateCreateVehicleAjax('post', 'createVehicle', data).done(function (vehicle) {
                closeOpenModal('#vehicleModal');
                clearForm('#vehicleForm');
                updateActiveVehicleTable();
                addActivityRowDashboard('vehicle', vehicle.id, 'created');

            });
        } else if (vehicleFormHeader.indexOf('Edit') > -1) {
            data.id = getTableRowId();
            updateCreateVehicleAjax('put', 'updateVehicle', data).done(function (vehicle) {
                closeOpenModal('#vehicleModal');
                updateActiveVehicleTable();
                $('#vehicleModalHeader').text("Create New Vehicle");
                addActivityRowDashboard('vehicle', vehicle.id, 'updated');

            });
        }
    });

    $('#deleteVehicleButton').on('click', function () {
        $.ajax({
            url: '/parking/api/vehicle/delete/' + rowData.id,
            type: 'delete'
        }).done(function () {
            closeOpenModal('#deleteVehicleModal');
            updateInactiveVehicleTable();
            addActivityRowDashboard('vehicle', rowData.id, 'deleted');
        });
    });
});