(function($){

$("#registration-form").submit(function (e) {
    $.ajax({
        url: "registerUser",
        type: "POST",
        data: $("#registration-form").serialize(),
        success: function success() {
            debugger;
            alert("success");
        },
        error: function error(xhr) {
            debugger;
            alert("error");
        }

    })

})
})(jQuery);