var dataTable;
var rowData;
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

    var date_input = $('input[name="date"]');
    date_input.datepicker({
        format: 'yyyy-mm-dd',
        todayHighlight: true,
        autoclose: true
    });

    $('#ownerForm').submit(function (event) {
        // debugger;
        var data = { //grab data from form inputs
            firstName: $('#ownerInputFirstName')[0].value,
            lastName: $('#ownerInputLastName')[0].value,
            gender: $('input[name="radio"]:checked')[0].value.toUpperCase(),
            dob: $('#dateInput')[0].value
        };
        event.preventDefault(); // prevent default page reload
        var ownerFormHeader = document.getElementById('ownerModalLabel').innerText;
        if (ownerFormHeader.indexOf('Create') > -1) {
            $.ajax({
                headers: {
                    'Content-Type': 'application/json'
                },
                url: '/parking/api/owners/createOwner',
                type: 'POST',
                data: JSON.stringify(data)
            }).done(function () {
                closeOpenModal('#ownerModal');
                clearForm('#ownerForm');
                updateOwnerTable();
            });
        } else if (ownerFormHeader.indexOf('Edit') > -1) {
            data.id = getTableRowId();
            $.ajax({
                headers: {
                    'Content-Type': 'application/json'
                },
                url: '/parking/api/owner/updateOwner',
                type: 'PUT',
                data: JSON.stringify(data)
            }).done(function (response) {
                console.log("Response:  " + response);
                closeOpenModal('#ownerModal');
                updateOwnerTable();
            });
        }
    });

});

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


function deleteOwner() {
    //debugger;
    $.ajax({
        url: '/parking/api/owner/delete/' + rowData.id,
        type: 'DELETE'
    }).done(function (response) {
        console.log("Response:  " + response);
        closeOpenModal('#deleteOwnerModal');
        updateOwnerTable();
    });
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

function closeOpenModal(modalLocator) {
    $(modalLocator).modal('toggle');
}

function makeAddButtonsInvisible() {
    addOwnerButtonInvisible();
    addVehicleButtonInvisible();
    makeReservationButtonInvisible();
}

function addOwnerButtonVisible() {
    addVehicleButtonInvisible();
    makeReservationButtonInvisible();
    document.getElementById("add_owner").style.display = 'block';

}

function addOwnerButtonInvisible() {
    document.getElementById("add_owner").style.display = 'none';
}

function addVehicleButtonVisible() {
    addOwnerButtonInvisible();
    makeReservationButtonInvisible();
    document.getElementById("add_vehicle").style.display = 'block';

}

function addVehicleButtonInvisible() {
    document.getElementById("add_vehicle").style.display = 'none';
}

function makeReservationButtonVisible() {
    addOwnerButtonInvisible();
    addVehicleButtonInvisible();
    document.getElementById("add_reservation").style.display = 'block';

}

function makeReservationButtonInvisible() {
    document.getElementById("add_reservation").style.display = 'none';
}
