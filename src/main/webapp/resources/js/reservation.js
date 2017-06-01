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

function contains(arr, id) {
    for (var i = 0; i < arr.length; i++) {
        var ownerId = arr[i].id;
        if (ownerId === id) {
            return true;
        }
    }
    return false;
}

function getOwnerName(id) {
    var owner;
    if (contains(owners, id)) {
        for (var i = 0; i < owners.length; i++) {
            if (owners[i].id === id) {
                owner = owners[i].firstName + " " + owners[i].lastName;
            }
        }
    } else {
        $.ajax({
            url: '/parking/api/owner/' + id,
        }).done(function (obj) {
            owners.push(obj);
            owner = obj.firstName + " " + obj.lastName;
        })
    }

    return owner;
}

$(document).ready(function () {
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
                "render": function (id, type, full, meta) {
                    
                    var name = getOwnerName(id);
                    return name;
                }
            },
            {
                "data": "parkingId",
                "defaultContent": "",
                // "render": function (data) {
                //     return data.firstName + " " + data.lastName;
                // }
            },
            {
                "data": "garageId",
                "defaultContent": "",
                // "render": function (data) {
                //     return data.firstName + " " + data.lastName;
                // }
            },
            {
                "data": "",
                "orderable": false,
                "defaultContent": "<button role='link' class='edit-owner-button btn-link'>Edit</button>|  <a>Inactivate</a>"
            }
        ]
    });
    updateActiveReservationTable();
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
                    var owner;
                    $.ajax({
                        url: '/parking/api/owner/' + id,
                    }).done(function (obj) {
                        owner = obj.firstName + " " + obj.lastName;
                    });
                    return owner;
                }
            },
            {
                "data": "parkingId",
                "defaultContent": "",
                // "render": function (data) {
                //     return data.firstName + " " + data.lastName;
                // }
            },
            {
                "data": "garageId",
                "defaultContent": "",
                // "render": function (data) {
                //     return data.firstName + " " + data.lastName;
                // }
            },
            {
                "data": "",
                "orderable": false,
                "defaultContent": "<button role='link' class='edit-owner-button btn-link'>Edit</button>|  <a>Inactivate</a>"
            }
        ]
    });
    updateInactiveReservationTable();
    $('#inactiveReservationTable tbody').on('click', 'button', function () {
        rowData = inactiveReservationDataTable.row($(this).parents('tr')).data();
        closeOpenModal('#deleteReservationModal');
        $('#number').text(rowData.number);
    });

    $('#activeReservationTable tbody').on('click', 'button', function () {
        rowData = activeReservationDataTable.row($(this).parents('tr')).data();
        closeOpenModal('#reservationModal');
        //getOwnersToDataList();
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

    $('#inactiveReservationTable tbody').on('click', 'a', function () {
        rowData = inactiveReservationDataTable.row($(this).parents('tr')).data();
        rowData.state = 'ACTIVE';
        updateCreateReservationAjax('put', 'updateReservation', rowData).done(function () {
            updateActiveReservationTable();
            updateInactiveReservationTable();
        });
    });
});