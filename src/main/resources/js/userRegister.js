/*ajax example for registerPage*/
$("#registration-form").submit(function (e) {
    e.preventDefault();
    $.ajax({
        url: "user-registration",
        type: "POST",
        data: $('#registration-form').serialize(),
        success: function success(data) {
            alert(data)
        },
        error: function error(xhr) {
            alert(xhr.responseText);
        }

    })

})



