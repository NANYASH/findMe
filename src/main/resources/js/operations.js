var userId = window.location.pathname.split('/').pop();

function logout() {
    $.ajax({
        url: "/logout",
        type: "GET",
        success: function success() {
            alert("User is logged out.")
        },
        error: function error(xhr) {
            alert(xhr.responseText);
        }
    })
}

function add(inputId) {
    $.ajax({
        url: "/addRelationship",
        type: "POST",
        data: {userToId: userId.toString()},
        success: function success() {
            alert("User is requested to be a friend.")
        },
        error: function error(xhr) {
            alert(xhr.responseText);
        }
    })
}

function remove(inputId) {
    $.ajax({
        url: "/updateRelationship",
        type: "POST",
        data: {userFromId: inputId.toString(), status: "DELETED"},
        success: function success() {
            alert("User is deleted from friends.")
        },
        error: function error(xhr) {
            alert(xhr.responseText);
        }
    })
}

function accept(inputId) {
    $.ajax({
        url: "/updateRelationship",
        type: "POST",
        data: {userFromId: inputId.toString(), status: "ACCEPTED"},
        success: function success() {
            alert("User is successfully accepted to friends.")
        },
        error: function error(xhr) {
            alert(xhr.responseText);
        }
    })
}

function reject(inputId) {
    $.ajax({
        url: "/updateRelationship",
        type: "POST",
        data: {userFromId: inputId.toString(), status: "REJECTED"},
        success: function success() {
            alert("User is rejected.")
        },
        error: function error(xhr) {
            alert(xhr.responseText);
        }
    })
}

function cancel(inputId) {
    $.ajax({
        url: "/updateRelationship",
        type: "POST",
        data: {userFromId: inputId.toString(), status: "CANCELED"},
        success: function success() {
            alert("Request is canceled.")
        },
        error: function error(xhr) {
            alert(xhr.responseText);
        }
    })
}