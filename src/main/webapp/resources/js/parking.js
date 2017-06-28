var activeParkingDataTable;
var inactiveParkingDataTable;

function populateParkingDataModal(data) {
    $('#parkingModalHeader').text("Edit " + data.id + " parking");
    $('#parkingAddressInput').val(data.address);
    $('#parkingGaragesInput').val(data.garagesNumber);
}


function updateCreateParkingAjax(type, url, data) {
    $.LoadingOverlay("show");
    return $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        url: '/parking/api/parking/' + url,
        type: type,
        data: JSON.stringify(data)
    })
}

function updateActiveParkingTable() {
    activeParkingDataTable.ajax.reload();
}

function updateInactiveParkingTable() {
    inactiveParkingDataTable.ajax.reload();
}

$(document).ready(function () {
    activeParkingDataTable = $('#activeParkingTable').DataTable({
        "order": [[0, "asc"]],
        "ajax": {
            "url": "/parking/api/parking/active",
            "dataSrc": ""
        },
        "columns": [
            {"data": "id"},
            {
                "data": "address",
                "defaultContent": "",
            },
            {
                "data": "garagesNumber",
                "defaultContent": ""
            },
            {
                "data": "",
                "orderable": false,
                "defaultContent": "<button role='link' class='edit-owner-button btn-link'>Edit</button>|  <a>Inactivate</a>"
            }
        ]
    });
    inactiveParkingDataTable = $('#inactiveParkingTable').DataTable({
        "order": [[0, "asc"]],
        "ajax": {
            "url": "/parking/api/parking/inactive",
            "dataSrc": ""
        },
        "columns": [
            {"data": "id"},
            {
                "data": "address",
                "defaultContent": "",
            },
            {
                "data": "garagesNumber",
                "defaultContent": ""
            },
            {
                "data": "",
                "orderable": false,
                "defaultContent": "<button role='link' class='edit-owner-button btn-link'>Delete</button>|  <a>Activate</a>"
            }
        ]
    });

    $('#inactiveParkingTable tbody').on('click', 'button', function () {
        rowData = inactiveParkingDataTable.row($(this).parents('tr')).data();
        closeOpenModal('#deleteParkingModal');
        $('#parkingId').text(rowData.id);
    });

    $('#activeParkingTable tbody').on('click', 'button', function () {
        rowData = activeParkingDataTable.row($(this).parents('tr')).data();
        closeOpenModal('#parkingModal');
        populateParkingDataModal(rowData);
    });

    $('#activeParkingTable tbody').on('click', 'a', function () {
        rowData = activeParkingDataTable.row($(this).parents('tr')).data();
        rowData.state = 'INACTIVE';
        updateCreateParkingAjax('put', 'update', rowData).done(function () {
            $.LoadingOverlay("hide");
            updateActiveParkingTable();
            updateInactiveParkingTable();
        });
    });

    $('#inactiveParkingTable tbody').on('click', 'a', function () {
        rowData = inactiveParkingDataTable.row($(this).parents('tr')).data();
        rowData.state = 'ACTIVE';
        updateCreateParkingAjax('put', 'update', rowData).done(function () {
            $.LoadingOverlay("hide");
            updateActiveParkingTable();
            updateInactiveParkingTable();
        });
    });

    $('#deleteParkingButton').on('click', function () {
        $.LoadingOverlay("show");
        $.ajax({
            url: '/parking/api/parking/delete/' + rowData.id,
            type: 'delete'
        }).done(function () {
            $.LoadingOverlay("hide");
            closeOpenModal('#deleteParkingModal');
            updateInactiveParkingTable();
            addActivityRowDashboard('PARKING', rowData.id, 'DELETED');
        });
    });

    $('#add_parking').on('click', function () {
        $('#parkingModalHeader').text("Create New Parking");
        clearForm('#parkingModal');
    });

    $('#parkingForm').submit(function (event) {
        data = {
            address: $('#parkingAddressInput')[0].value,
            garagesNumber: $('#parkingGaragesInput')[0].value
        };
        event.preventDefault();
        var parkingFormHeader = document.getElementById('parkingModalLabel').innerText;
        if (parkingFormHeader.indexOf('Create') > -1) {
            updateCreateParkingAjax('post', 'create', data).done(function (parking) {
                $.LoadingOverlay("hide");
                closeOpenModal('#parkingModal');
                clearForm('#parkingForm');
                updateActiveParkingTable();
                addActivityRowDashboard('PARKING', parking.id, 'CREATED');

            });
        } else if (parkingFormHeader.indexOf('Edit') > -1) {
            data.id = getTableRowId();
            updateCreateParkingAjax('put', 'update', data).done(function (parking) {
                $.LoadingOverlay("hide");
                closeOpenModal('#parkingModal');
                updateActiveParkingTable();
                $('#parkingModalHeader').text("Create New Parking");
                addActivityRowDashboard('PARKING', parking.id, 'UPDATED');

            });
        }
    });

});
