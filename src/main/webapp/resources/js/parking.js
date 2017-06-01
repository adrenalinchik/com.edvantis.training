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

$(document).ready(function () {
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

