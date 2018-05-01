password_validation = function () {
    if ($('#password').val() === $('#c_password').val()) {
        $('#message').html('Password Confirmed').css('color', 'green');
        return true;
    } else {
        $('#message').html('Password Not Confirmed').css('color', 'red');
        return false;
    }
};

/**check password validation on register from */

$('#password, #c_password').on('keyup', password_validation);

/**register login event */
$("#login_form").submit(function (event) {
    event.preventDefault(); //prevent default action
    var result = login($(this));
    result.success(function (data) {
        console.log(data);
        if (data.code == -1) {
            $(".login_message").show();
        } else {
            var newBody = "<nav>" +
                "<div class='search-bar'><input type='text' name='search'></div>" +
                "</nav>" +
                "<div class='home-container'><div class='followed-list'></div>" +
                "<div class='sweets'></div><div class='profile'>" +
                "<ul>" +
                "<li><a href='' id='disconnect' data-key='" + data.key + "'>Logout</a></li></ul></div></div>";
            $("body").html(newBody);

            var f_users = get_following_users(data.key);
            f_users.success(function (followed_users) {
                if (followed_users.code == 200) {
                    for (var i in followed_users.users) {
                        var user = followed_users.users[i];
                        var userInfo = "<div class='followed-user'>" +
                            "<div class='username'>@" + user.f_username + "</div>" +
                            "<div class='name'>" + user.f_l_name + " " + user.f_f_name + "</div>" +
                            "</div>";
                        $(".followed-list").append(userInfo);
                    }

                }
            });

        }
    })
});


$("#register_form").submit(function (event) {
    event.preventDefault(); //prevent default action
    var result = register($(this));
});

animate_forms = function () {
    $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
};


$('.message a').click(animate_forms);

$("#disconnect").click(function () {
    var key = $(this).data("key");
    var result = logout(key);
    result.success(function () {
        location.reload();
    })
})


