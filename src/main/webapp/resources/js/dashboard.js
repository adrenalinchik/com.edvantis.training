var reservationDataTable;

function getOwnersToDataList(dataList) {
    $('#dashboardOwnerList').empty();
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

function getDashboardStartDateValue() {
    let startDate = $('#dashboardStartDateInput')[0].value;
    return 0 < startDate.length && startDate.length < 19 ? startDate + ":00" : startDate;
}
function getDashboardEndDateValue() {
    let endDate = $('#dashboardEndDateInput')[0].value;
    return 0 < endDate.length && endDate.length < 19 ? endDate + ":00" : endDate;
}

function getActivitiesToTable() {
    $.LoadingOverlay("show");
    $.ajax({
        url: '/parking/api/activities'
    }).done(function (activities) {
        activities.map(function (activity) {
            let markup = "<tr><td>" + activity.objectType + "</td><td>" + activity.objectId + "</td><td>" + activity.actionType + "</td><td>" + activity.createdDate + "</td></tr>";
            $("#dashboardActivityTable").append(markup);
            switch (activity.actionType) {
                case "CREATED":
                    $('#dashboardActivityTable tr:last').css("background", "hsla(120, 60%, 70%, 0.3)");
                    break;
                case "UPDATED":
                    $('#dashboardActivityTable tr:last').css("background", "hsla(290, 60%, 70%, 0.3)");
                    break;
                case "DELETED":
                    $('#dashboardActivityTable tr:last').css("background", "hsla(356, 90%, 50%, 0.3)");
                    break;
            }
        });
        $.LoadingOverlay("hide");
    })
}

function updateActivitiesTable() {
    deleteOldActivities();
    location.reload();
}

function deleteOldActivities() {
    $.ajax({
        url: '/parking/api/activities/delete',
        type: 'delete'
    })
}

$(document).ready(function () {
    let date = new Date();
    let dd = date.getDate();
    let mm = date.getMonth() + 1;
    let yyyy = date.getFullYear();
    reservationDataTable = $('#dashboardReservationTable').DataTable({
        "order": [[0, "asc"]],
        "ajax": {
            "url": "/parking/api/reservationsByDate?date=" + yyyy + "-" + mm + "-" + dd + " 00:00:00",
            "dataSrc": "",
            "deferRender": true
        },
        "columns": [
            {"data": "id"},
            {"data": "begin"},
            {"data": "end"},
            {"data": "vehicleNumber"},
            {"data": "parkingId"}
        ]
    });
    getActivitiesToTable();

    $('#dashboardTab').click(function () {
        updateActivitiesTable();
        reservationDataTable.ajax.reload();
    });

    $('#reservationLink').click(function () {
        $('#reservationTab').click();
    });

    $('#dashboardOwnersLink').click(function () {
        $('#ownerTab').click();
    });

    $('#dashboardStartDateInput').datetimepicker({
        showSeconds: true,
        dateFormat: "yyyy-mm-dd",
        timeFormat: "HH:mm:ss",
        todayHighlight: true,
        autoclose: true,
    });

    $('#dashboardEndDateInput').datetimepicker({
        showSecond: true,
        dateFormat: "yyyy-mm-dd",
        timeFormat: "HH:mm:ss",
        todayHighlight: true,
        autoclose: true
    });

    $('#dashboardStartDateInput').change(function () {
        var startDateInput = $('#dashboardStartDateInput');
        var endDateInput = $('#dashboardEndDateInput');
        if (startDateInput[0].value.length > 0) {
            endDateInput.prop('disabled', false);
        } else {
            endDateInput.val('');
            endDateInput.prop('disabled', true);
        }
    });

    $('#dashboardEndDateInput').change(function () {
        var reservOwnerInput = $('#dashboardOwnerInput');
        var endDateInput = $('#dashboardEndDateInput');
        if (endDateInput[0].value.length > 0) {
            getOwnersToDataList('dashboardOwnerList');
            reservOwnerInput.prop('disabled', false);
        } else {
            reservOwnerInput.val('');
            reservOwnerInput.prop('disabled', true);
        }
    });

    $('#show_income').click(function (event) {
        event.preventDefault();
        let ownerValue = $('#dashboardOwnerInput')[0].value;
        if (ownerValue.length > 1) {
            $.LoadingOverlay("show");
            $.ajax({
                url: '/parking/api/owners/' + getOwnerId(ownerValue) + "?from=" + getDashboardStartDateValue() + "&to=" + getDashboardEndDateValue(),
            }).done(function (result) {
                $.LoadingOverlay("hide");
                $('#income_result').val("$" + result);
                document.getElementById('income_result').style.display = 'block';
            });
        } else if (getDashboardEndDateValue().length > 2 && getDashboardStartDateValue().length > 2) {
            $.LoadingOverlay("show");
            $.ajax({
                url: '/parking/api/owners/profit' + "?from=" + getDashboardStartDateValue() + "&to=" + getDashboardEndDateValue(),
            }).done(function (result) {
                $.LoadingOverlay("hide");
                $('#income_result').val("For All Owners:  $" + result);
                document.getElementById('income_result').style.display = 'block';
            });
        }
    });
});