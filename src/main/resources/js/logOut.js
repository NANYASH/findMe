$("#logout").click(function () {
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
})
