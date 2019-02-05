function login(email,password){
    $.ajax({
        url: "/login",
        type: "GET",
        data: {email: $("#email").val(), password: $("#password").val()},
        success: function success() {
            alert("User is logged.")
        },
        error: function error(xhr) {
            alert(xhr.responseText);
        }
    });
}