/** create an html sweet element
 * @param sweet is a json object
 * */
function createHtmlSweet(sweet) {
    console.log(sweet.pic)
    var sweetHtml = "<div class='sweet'>" +
        "<div class='user-info'><img src='" + "http://localhost:8080/data/photos/" + sweet.pic + "'><span class='name'>" + sweet.l_name + " " +
        sweet.f_name +
        "</span> <span class='username'>@" + sweet.username + "</span> </div>" +
        "<div class='body'>" + sweet.sweet + "</div>" +
        "<div class='reactions'> " +
        "<span class='likes'  data-idsweet='" + sweet._id.$oid + "' data-liked='false' >" + sweet.likes.length + " ♥</span></div>" +
        "<div class='reactions'> " +
        "<span class='delete'  data-idsweet='" + sweet._id.$oid + "' data-liked='false' >Delete Sweet</span></div>" +
        "<div class='comments'> <span class='show-comments' data-idsweet='" + sweet._id.$oid + "'  id='" + sweet._id.$div + "'>show comments</span>" +
        "</div> " +
        "<div class='add-comment' ><input type='text'> <button  data-idsweet='" + sweet._id.$oid + "'>comment</button></div>" +
        "</div>";

    return sweetHtml;
}

/**create an html comment element
 * @param comment a json object
 * */

function createHtmlComment(comment) {
    var commentHtml = "<div class='comment'><div class='user-info'><img src='" + "http://localhost:8080/data/photos/" + comment.pic + "'>" +
        "<span class='name'>" + comment.l_name + " " + comment.f_name + "</span>" +
        "<span class='username'>@" + comment.username + "</span></div> " +
        "<div class='body'>" + comment.comment + "</div></div>";

    return commentHtml;

}

/**
 * create user info html element
 * @param user jsob obejet
 * */

function createHtmlUserInfo(user) {
    var userinfoHtml = "<div class='following-user' data-user_id='" + user._id + "' data-key='" +getCookie("key") + "'>" +
        "<div class='username'>@" + user.username + "</div>" +
        "<div class='name'>" + user.l_name + " " + user.f_name + "</div> " +
        "</div>";
    return userinfoHtml;
}

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
    var username = $(this).find("input[name=username]").val();
    var result = login($(this));
    result.success(function (data) {
        console.log(data);
        if (data.code == -1) {
            $(".login_message").show();
        } else {
            setCookie("key", data.key, 1);
            setCookie("username", username, 1);
            var newBody = "<nav>" +
                "<div class='search-bar'><input type='text' name='search'><div class='search-result'></div>" +
                "</div> " +
                "</nav>" +
                "<div class='home-container'><div class='following-list'></div>" +
                "<div class='sweets'>" +
                "</div><div class='profile'>" +
                "<div class='informations'></div> " +
                "<ul>" +
                "<li><a href='#' id='disconnect' data-key='" + data.key + "'>Logout</a></li></ul>" +
                "Select a file to upload: <br />" +
                "<form   data-key='" + data.key + "' id='upload_form' action = 'http://localhost:8080/Twister/user/pic' method = 'post' enctype = 'multipart/form-data'>" +
                "<input type = 'file' name = 'file' s size = '50' accept='images/*'/>" +
                "         <br />" +
                "         <input type = 'submit' value = 'Upload File' />" +
                "      </form>" +
                "</div></div>";

            $("body").html(newBody);
            initDiconnect();
            initSearch();

            var user_infos = get_profile(getCookie("key"), getCookie("username"));
            user_infos.success(function (response) {
                var $infos = $(".informations");
                $infos.append("<div class='image'><img src='" + "http://localhost:8080/data/photos/" + response.pic + "'></div>");
                $infos.append("<div class='name'> " + response.l_name + " " + response.f_name + "</div>");
                $infos.append("<div class='username'>@" + response.username + "</div>");
            });


            var f_users = get_following_users(getCookie("key"));
            f_users.success(function (following_users) {
                if (following_users.code == 200) {
                    for (var i in following_users.users) {
                        var user = following_users.users[i];
                        var userInfoHtml = createHtmlUserInfo(user);
                        $(".following-list").append(userInfoHtml);
                    }
                    initEventFollowingList();
                }
            });

            var news_feed = get_news_feed(data.key);
            news_feed.success(function (resp) {
                if (resp.code == 200) {
                    var $sweets = $(".sweets");
                    $sweets.html("<h3>NewsFeed</h3>");
                    $sweets.append("<div class='add-sweet'><input type='text'><button>sweet</button></div> ");
                    for (var i in resp.sweets) {
                        var sweet = resp.sweets[i];
                        var sweetHtml = createHtmlSweet(sweet);
                        $sweets.append(sweetHtml);
                    }
                    initShowComments();
                    initAddComment();
                    initAddSweet();
                    initLike();
                    initDelete();
                }
            })

        }
    })
});


$("#register_form").submit(function (event) {
    event.preventDefault(); //prevent default action
    var result = register($(this));
    result.success(function (response) {
        if (response.code == 200) {
            location.reload();
        }
    })
});

animate_forms = function () {
    $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
};


$('.message a').click(animate_forms);

function initDiconnect() {
    $(document).ready(function () {
        $("#disconnect").on('click', function (e) {
            e.preventDefault();
            var key = $(this).data("key");
            var result = logout(key);
            result.success(function (response) {
                if (response.code == 200) {
                    location.reload();
                }
            });
        });
    });
    $("#upload_form").submit(function (event) {
        event.preventDefault(); //prevent default action
        var post_url = $(this).attr("action"); //get form action url
        var request_method = $(this).attr("method"); //get form GET/POST method
        var form_data = new FormData(this); //Creates new FormData object

        form_data.append("key", $(this).data("key"));

        console.log(form_data);
        $.ajax({
            url: post_url,
            type: request_method,
            data: form_data,
            contentType: false,
            cache: false,
            processData: false
        }).done(function (response) { //
            // $("#server-results").html(response);
        });
    });
}


function initEventFollowingList() {
    $(document).ready(function () {
        $(".following-user").on("click", function (e) {
            var f_id = $(this).data("user_id");
            var key = $(this).data("key");
            var name = $(this).find(".name").text();
            var result = get_sweets(key, f_id);
            result.success(function (data) {
                if (data.code == 200) {
                    var $sweets = $(".sweets");
                    $sweets.html("<h3>" + name + "</h3>");
                    $sweets.append("<div class='add-sweet'><input type='text'><button>sweet</button></div> ");
                    for (var i in data.sweets) {
                        var sweet = data.sweets[i];
                        console.log(sweet);
                        var sweetHtml = createHtmlSweet(sweet)
                        console.log(sweetHtml);
                        $sweets.append(sweetHtml);
                    }
                    initShowComments();
                    initAddComment();
                    initAddSweet();
                    initLike();
                    initDelete();

                }
            });
        });
    });

}

function initShowComments() {
    $(document).ready(function () {
        $(".show-comments").on('click', function (e) {
            e.preventDefault();
            var comments = $(this).parent(".comments");
            var idSweet = $(this).data("idsweet");
            $(this).nextAll().remove();
            var result = get_comments(getCookie("key"), idSweet);
            result.success(function (response) {
                if (response.code == 200) {
                    for (var i in response.comments) {
                        var comment = response.comments[i];

                        var commentHtml = createHtmlComment(comment);
                        comments.append(commentHtml);
                    }
                }
            });
        });
    });
}

function initAddComment() {
    $(document).ready(function () {
        $(".add-comment button").on('click', function () {

            var idSweet = $(this).data("idsweet");

            var comment = $(".add-comment input").val();
            console.log(idSweet + " " + comment);
            var result = add_comment(getCookie("key"), idSweet, comment);
            result.success(function (response) {
                console.log(response);
                if (response.code == 200) {
                    $("#" + idSweet).trigger("click");
                    $(".add-comment input").val('');
                } else {

                }
            });
        });
    });
}

function initAddSweet() {
    $(document).ready(function () {
        $(".add-sweet button").on('click', function () {
            var result = add_sweet(getCookie("key"), $(".add-sweet input").val());
            result.success(function (response) {
                if (response.code == 200) {
                    $(".add-sweet").after(createHtmlSweet(response));
                    $(".add-sweet input").val('');
                    initShowComments();
                    initAddComment();
                    initAddSweet();
                    initLike();
                    initDelete();

                } else {

                }
            });
        });
    })

}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}


function initSearch() {
    $(document).ready(function () {
        $(".search-bar input").on("keyup", function () {
            var value = $(this).val();
            $(".search-result").html("");

//TODO hna tu fai appel la fonction search ki tjib data dir boucle w afficher b hadi la ligne li ltaht wela dir append
            var result = search_user(getCookie("key"), value);
            result.success(function (resp) {
                if (resp.code == 200) {
                    var $search_result = $(".search-result");
                    for (var i in resp.users) {
                        var user = resp.users[i];
                        var userHtml = "<div class='user'>" + user.f_name + " " + user.l_name + "<button data-iduser='" + user.id + "'>Follow</button></div><br/>"
                        $search_result.append(userHtml);

                    }
                    initFollow();

                }

            });



        })
    })
}

function initFollow() {
    $(document).ready(function () {
        $(".search-result .user button").on('click', function () {
            var iduser = $(this).data("iduser");
            var result = follow(getCookie("key"), iduser);
            console.log(iduser)
            result.success(function (res) {
                console.log(res);
            })
        });
    });

}

function initLike() {
    $(document).ready(function () {
        $(".likes").on('click', function () {
            var idSweet = $(this).data("idsweet");
            var liked = $(this).data("liked");
            console.log(liked)
            if (liked == "true") {
                var result = unlike_sweet(getCookie("key"), idSweet);
                $(this).data("liked", "false");
                var like = parseInt($(this).html()) - 1;
                $(this).html(like.toString() + " ♥")
            } else {
                var result = like_sweet(getCookie("key"), idSweet);
                $(this).data("liked", "true");
                var like = parseInt($(this).html()) + 1;
                $(this).html(like.toString() + " ♥")
            }
        });
    });
}


function initDelete() {
    $(document).ready(function () {
        $(".delete").on('click', function () {
            var idSweet = $(this).data("idsweet");
            var result = remove_sweet(getCookie("key"),idSweet);
            var parent =  $(this).parent().parent();
            result.success(function (res) {
                console.log(res)
                if(res.code == 200)
                    parent.remove();


            })
        });
    });
}


