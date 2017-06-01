
var activeDataTable;
var inactiveDataTable;
var rowData;
var data;

function closeOpenModal(modalLocator) {
    $(modalLocator).modal('toggle');
}

function populateOwnerDataModal(data1) {
    $('#ownerModalHeader').text("Edit " + data1.firstName + " " + data1.lastName);
    $('#ownerInputFirstName').val(data1.firstName);
    $('#ownerInputLastName').val(data1.lastName);
    var gender = data1.gender.toString();
    if (gender == "MALE") {
        document.getElementById("male").checked = true;
    }
    if (gender == "FEMALE") {
        document.getElementById("female").checked = true;
    }
    $('#dateInput').val(data1.dob);
}

function updateActiveOwnerTable() {
    activeDataTable.ajax.reload();
}

function updateInactiveOwnerTable() {
    inactiveDataTable.ajax.reload();
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

function updateCreateOwnerAjax(type, url, data) {
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
    activeDataTable = $('#activeOwnerTable').DataTable({
        "order": [[0, "asc"]],
        "ajax": {
            "url": "/parking/api/owners/active",
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
                "defaultContent": "<button role='link' class='edit-owner-button btn-link'>edit</button>|  <a>make inactive</a>"
            }
        ]
    });
    inactiveDataTable = $('#inactiveOwnerTable').DataTable({
        "order": [[0, "asc"]],
        "ajax": {
            "url": "/parking/api/owners/inactive",
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
                "defaultContent": "<button role='link' class='edit-owner-button btn-link'>delete</button>|  <a>make active</a>"
            }
        ]
    });
    $('#inactiveOwnerTable tbody').on('click', 'button', function () {
        rowData = inactiveDataTable.row($(this).parents('tr')).data();
        closeOpenModal('#deleteOwnerModal');
        $('#name').text(rowData.firstName + "  " + rowData.lastName);
    });

    $('#activeOwnerTable tbody').on('click', 'button', function () {
        rowData = activeDataTable.row($(this).parents('tr')).data();
        closeOpenModal('#ownerModal');
        populateOwnerDataModal(rowData);
    });

    $('#activeOwnerTable tbody').on('click', 'a', function () {
        rowData = activeDataTable.row($(this).parents('tr')).data();
        rowData.state = 'INACTIVE';
        updateCreateOwnerAjax('put', 'updateOwner', rowData).done(function () {
            updateActiveOwnerTable();
            updateInactiveOwnerTable();
        });
    });

    $('#inactiveOwnerTable tbody').on('click', 'a', function () {
        rowData = inactiveDataTable.row($(this).parents('tr')).data();
        rowData.state = 'ACTIVE';
        updateCreateOwnerAjax('put', 'updateOwner', rowData).done(function () {
            updateActiveOwnerTable();
            updateInactiveOwnerTable();
        });
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
            updateCreateOwnerAjax('post', 'createOwner', data).done(function () {
                closeOpenModal('#ownerModal');
                clearForm('#ownerForm');
                updateActiveOwnerTable();
            });
        } else if (ownerFormHeader.indexOf('Edit') > -1) {
            data.id = getTableRowId();
            updateCreateOwnerAjax('put', 'updateOwner', data).done(function () {
                closeOpenModal('#ownerModal');
                updateActiveOwnerTable();
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
            updateInactiveOwnerTable();
        });
    });

    $('#add_owner').on('click', function () {
        clearForm('#ownerModal');
    });
});
