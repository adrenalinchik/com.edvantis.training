var dataTable;
var rowData;
var data;

function makeElementInvisible(element) {
    document.getElementById(element).style.display = 'none';
}

function makeElementVisible(element) {
    document.getElementById(element).style.display = 'block';
}
function makeAddButtonsInvisible() {
    makeElementInvisible('add_owner');
    makeElementInvisible('add_vehicle');
    makeElementInvisible('add_reservation');
}

function addOwnerButtonVisible() {
    makeElementInvisible('add_vehicle');
    makeElementInvisible('add_reservation');
    makeElementVisible('add_owner');
}


function addVehicleButtonVisible() {
    makeElementInvisible('add_owner');
    makeElementInvisible('add_reservation');
    makeElementVisible('add_vehicle');
}

function makeReservationButtonVisible() {
    makeElementInvisible('add_owner');
    makeElementInvisible('add_vehicle');
    makeElementVisible('add_reservation');
}

function closeOpenModal(modalLocator) {
    $(modalLocator).modal('toggle');
}

function populateOwnerDataModal(data) {
    $('#ownerModalHeader').text("Edit " + data.firstName + " " + data.lastName);
    $('#ownerInputFirstName').val(data.firstName);
    $('#ownerInputLastName').val(data.lastName);
    var gender = data.gender.toString();
    if (gender == "MALE") {
        document.getElementById("male").checked = true;
    }
    if (gender == "FEMALE") {
        document.getElementById("female").checked = true;
    }
    $('#dateInput').val(data.dob);
}

function updateOwnerTable() {
    dataTable.ajax.reload();
}

function getTableRowId() {
    return rowData.id.toString();
}

// function deleteOwner() {
//     $.ajax({
//         url: '/parking/api/owner/delete/' + rowData.id,
//         type: 'delete'
//     }).done(function () {
//         closeOpenModal('#deleteOwnerModal');
//         updateOwnerTable();
//     });
// }

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

function updateCreateOwnerAjax(type, url) {
    return $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        url: '/parking/api/owner/' + url,
        type: type,
        data: JSON.stringify(data)
    })
}

$(document).ready(function () {
    dataTable = $('#ownerTable').DataTable({
        "order": [[0, "asc"]],
        "ajax": {
            "url": "/parking/api/owners",
            "dataSrc": ""
        },
        "columns": [
            {"data": "id"},
            {"data": "firstName"},
            {"data": "lastName"},
            {
                "data": "gender",
                "defaultContent": ""
            },
            {
                "data": "dob",
                "defaultContent": "",
                "render": function (data) {
                    return (Math.floor((new Date() - new Date(data)) / (365.25 * 24 * 60 * 60 * 1000))).toString();
                }
            },
            {
                "data": "",
                "orderable": false,
                "defaultContent": "<button role='link' class='edit-owner-button btn-link'>edit</button>|  <a>delete</a>"
            }
        ]
    });
    $('#ownerTable tbody').on('click', 'a', function () {
        rowData = dataTable.row($(this).parents('tr')).data();
        closeOpenModal('#deleteOwnerModal');
        $('#name').text(rowData.firstName + "  " + rowData.lastName);
    });

    $('#ownerTable tbody').on('click', 'button', function () {
        rowData = dataTable.row($(this).parents('tr')).data();
        closeOpenModal('#ownerModal');
        populateOwnerDataModal(rowData);
    });

    $('input[name="date"]').datepicker({
        format: 'yyyy-mm-dd',
        todayHighlight: true,
        autoclose: true
    });

    $('#ownerForm').submit(function (event) {
        data = { //grab data from form inputs
            firstName: $('#ownerInputFirstName')[0].value,
            lastName: $('#ownerInputLastName')[0].value,
            gender: $('input[name="radio"]:checked')[0].value.toUpperCase(),
            dob: $('#dateInput')[0].value
        };
        event.preventDefault(); // prevent default page reload
        var ownerFormHeader = document.getElementById('ownerModalLabel').innerText;
        if (ownerFormHeader.indexOf('Create') > -1) {
            updateCreateOwnerAjax('post', 'createOwner').done(function () {
                closeOpenModal('#ownerModal');
                clearForm('#ownerForm');
                updateOwnerTable();
            });
        } else if (ownerFormHeader.indexOf('Edit') > -1) {
            data.id = getTableRowId();
            updateCreateOwnerAjax('put', 'updateOwner').done(function () {
                closeOpenModal('#ownerModal');
                updateOwnerTable();
                $('#ownerModalHeader').text("Create New Owner");
            });
        }
    });

    $('#closeForm').on('click', function () {
        $('#ownerModalHeader').text("Create New Owner");
    });

    $('#deleteOwnerButton').on('click', function () {
        $.ajax({
            url: '/parking/api/owner/delete/' + rowData.id,
            type: 'delete'
        }).done(function () {
            closeOpenModal('#deleteOwnerModal');
            updateOwnerTable();
        });
    });

    $('#add_owner').on('click', function () {
        clearForm('#ownerModal');
    });

    $('#homeTab').on('click', function () {
        makeAddButtonsInvisible();
    });
    $('#ownerTab').on('click', function () {
        addOwnerButtonVisible();
    });
    $('#vehicleTab').on('click', function () {
        addVehicleButtonVisible();
    });
    $('#reservationTab').on('click', function () {
        makeReservationButtonVisible();
    })
});

