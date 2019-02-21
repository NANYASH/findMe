var userId = window.location.pathname.split('/').pop();

function logout() {
    $.ajax({
        url: "/logout",
        type: "GET",
        success: function success() {
            window.location.replace("/");
        },
        error: function error(xhr) {
            alert(xhr.responseText);
        }
    })
}

$("#login-form").submit(function (e) {
    e.preventDefault();
    $.ajax({
        url: "/login",
        type: "GET",
        data: {email: $("#email").val(), password: $("#password").val()},
        success: function success(data) {
            window.location.replace("/user/" + data.toString());
        },
        error: function error(xhr) {
            alert(xhr.responseText);
        }
    });
});

function add() {
    $.ajax({
        url: "/addRelationship",
        type: "POST",
        data: {userToId: userId.toString()},
        success: function success() {
            window.location.reload(true)
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
            window.location.reload(true)
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
            window.location.reload(true)
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
            window.location.reload(true)
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
            window.location.reload(true)
        },
        error: function error(xhr) {
            alert(xhr.responseText);
        }
    })
}

$("#create-post").submit(function (e) {
    e.preventDefault();

    $.ajax({
        url: "/add-post",
        type: "POST",
        data: $('#create-post').serialize()+'&userPageId=' + userId,
        success: function success() {
            window.location.reload(true);
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
});

$("#filter-post").submit(function (e) {
    e.preventDefault();
    $.ajax({
        url: "/user/" + userId,
        type: "GET",
        data: $('#filter-post').serialize()+'&userPostedId='+ $("#friendId option:selected").val()+'&yFriends='+$('#byFriends').val(),
        success: function (result) {
            $(function () {
                $("body").html(result);
            });
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
});

$("#byFriends").on('change', function () {
    if ($(this).is(':checked')) {
        $(this).attr('value', 'true');
    } else {
        $(this).attr('value', 'false');
    }
});

$(function () {
    $('#byFriends').change(function () {
        var disabled = $(this).is(':checked')
        $('#friendId').prop('disabled', disabled);
    });
});

$(function () {
    $('#friendId').change(function () {
        var disabled = this.value != 'Select user'
        $('#byFriends').prop('disabled', disabled);
    });
});

$(function () {
    $('#friendId,#byFriends').change(function () {
        var disabled = $("#friendId").is(":not(:disabled)") && $("#byFriends").is(":not(:disabled)");
        $('#filter-button').prop('disabled', disabled);
    });
});