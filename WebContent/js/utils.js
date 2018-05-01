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
                "<div class='home-container'><div class='following-list'></div>" +
                "<div class='sweets'></div><div class='profile'>" +
                "<ul>" +
                "<li><a href='' id='disconnect' data-key='" + data.key + "'>Logout</a></li></ul></div></div>";
            $("body").html(newBody);

            var f_users = get_following_users(data.key);
            f_users.success(function (following_users) {
                if (following_users.code == 200) {
                    for (var i in following_users.users) {
                        var user = following_users.users[i];
                        var userInfo = "<div class='following-user' data-user_id='" + user.f_id + "' data-key='"+
                             data.key + "'>" +
                            "<div class='username'>@" + user.f_username + "</div>" +
                            "<div class='name'>" + user.f_l_name + " " + user.f_f_name + "</div> " +
                            "</div>";
                        $(".following-list").append(userInfo);
                    }
                    initEventFollowingList();
                }
            });

            var news_feed = get_news_feed(data.key);
            news_feed.success(function (resp) {
                if (resp.code == 200) {
                    var $sweets = $(".sweets");
                    $sweets.html("<h3>NewsFeed</h3>")
                    for (var i in resp.sweets) {
                        var sweet = resp.sweets[i];
                        var sweetHtml = "<div class='sweet'>" +
                            "<div class='user-info'><img src='#'><span class='name'>" + sweet.l_name + " " +
                            sweet.f_name +
                            "</span> <span class='username'>@" + sweet.username + "</span> </div>" +
                            "<div class='body'>" + sweet.sweet + "</div>" +
                            "<div class='reactions'><span class='likes'>" + sweet.likes.length + "♥</span></div>" +
                            "</div>";

                        $sweets.append(sweetHtml);
                    }
                }
            })

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
    result.success(function (response) {
        if (response.code == 200) {
            location.reload();
        }
    });
});


function initEventFollowingList(){
    $(document).ready(function () {
        $(".following-user").on("click", function (e) {
            var f_id = $(this).data("user_id");
            var key = $(this).data("key");
            var name = $(this).find(".name").text();
            var result = get_sweets(key, f_id);
            result.success(function (data) {
                if (data.code == 200) {
                    var $sweets = $(".sweets");
                    $sweets.html("<h3>" + name +"</h3>");
                    for (var i in data.sweets) {
                        var sweet = data.sweets[i];
                        console.log(sweet);
                        var sweetHtml = "<div class='sweet'>" +
                            "<div class='user-info'><img src='#'><span class='name'>" + sweet.l_name + " " +
                            sweet.f_name +
                            "</span> <span class='username'>@" + sweet.username + "</span> </div>" +
                            "<div class='body'>" + sweet.sweet + "</div>" +
                            "<div class='reactions'><span class='likes'>" + sweet.likes.length + "♥</span></div>" +
                            "</div>";
                        console.log(sweetHtml);
                        $sweets.append(sweetHtml);
                    }
                }
            });
        });
    });

}
