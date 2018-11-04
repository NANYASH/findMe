
$("#registration-form").submit(function (e) {
    $.ajax({
        url: "registerUser",
        type: "POST",
        data: $("#registration-form").serialize(),
        success: function success() {
            alert("success");
        },
        error: function error(xhr) {
            alert("error");
        }

    })

})
