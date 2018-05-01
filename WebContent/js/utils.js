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
            setCookie("key", data.key, 1);
            var newBody = "<nav>" +
                "<div class='search-bar'><input type='text' name='search'></div>" +
                "</nav>" +
                "<div class='home-container'><div class='following-list'></div>" +
                "<div class='sweets'>" +
                "</div><div class='profile'>" +
                "<ul>" +
                "<li><a href='#' id='disconnect' data-key='" + data.key + "'>Logout</a></li></ul>" +
                " <h3>File Upload:</h3>" +
                "Select a file to upload: <br />" +
                "<form   data-key='" + data.key + "' id='upload_form' action = 'http://localhost:8080/Twister/user/pic' method = 'post' enctype = 'multipart/form-data'>" +
                "<input type = 'file' name = 'file' s size = '50' accept='images/*'/>" +
                "         <br />" +
                "         <input type = 'submit' value = 'Upload File' />" +
                "      </form>" +
                "</div></div>";

            $("body").html(newBody);
            initDiconnect();

            var f_users = get_following_users(data.key);
            f_users.success(function (following_users) {
                if (following_users.code == 200) {
                    for (var i in following_users.users) {
                        var user = following_users.users[i];
                        var userInfo = "<div class='following-user' data-user_id='" + user.f_id + "' data-key='" +
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
                    $sweets.html("<h3>NewsFeed</h3>");
                    $sweets.append("<div class='add-sweet'><input type='text'><button>sweet</button></div> ");
                    for (var i in resp.sweets) {
                        var sweet = resp.sweets[i];
                        var sweetHtml = "<div class='sweet'>" +
                            "<div class='user-info'><img src='#'><span class='name'>" + sweet.l_name + " " +
                            sweet.f_name +
                            "</span> <span class='username'>@" + sweet.username + "</span> </div>" +
                            "<div class='body'>" + sweet.sweet + "</div>" +
                            "<div class='reactions'> " +
                            "<span class='likes'>" + sweet.likes.length + "♥</span></div>" +
                            "<div class='comments'> <span class='show-comments' data-idsweet='" +
                             sweet._id.$oid + "'>show comments</span>" +
                            "</div> " +
                            "<div class='add-comment'><input type='text'> <button>comment</button></div>"+
                        "</div>";
                        $sweets.append(sweetHtml);
                    }
                    initShowComments();
                    initAddComment();
                    initAddSweet();

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
    $("#upload_form").submit(function(event){
        event.preventDefault(); //prevent default action
        var post_url = $(this).attr("action"); //get form action url
        var request_method = $(this).attr("method"); //get form GET/POST method
        var form_data = new FormData(this); //Creates new FormData object

        form_data.append("key",$(this).data("key"));

        console.log(form_data);
        $.ajax({
            url : post_url,
            type: request_method,
            data : form_data,
            contentType: false,
            cache: false,
            processData:false
        }).done(function(response){ //
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
                        var sweetHtml = "<div class='sweet'>" +
                            "<div class='user-info'><img src='#'><span class='name'>" + sweet.l_name + " " +
                            sweet.f_name +
                            "</span> <span class='username'>@" + sweet.username + "</span> </div>" +
                            "<div class='body'>" + sweet.sweet + "</div>" +
                            "<div class='reactions'><span class='likes'>" + sweet.likes.length + "♥</span></div>" +
                            "<div class='comments'> <span class='show-comments' data-idsweet='" +
                            sweet._id.$oid + "'>show comments</span> </div>" +
                            "<div class='add-comment'><input type='text'><button>comment</button></div>"
                            "</div>";
                        console.log(sweetHtml);
                        $sweets.append(sweetHtml);
                    }
                    initShowComments();
                    initAddComment();
                    initAddSweet();
                }
            });
        });
    });

}

function initShowComments() {
    $(document).ready(function () {
        $(".show-comments").on('click', function (e) {
            e.preventDefault();
            $(this).html('');
            var comments = $(this).parent(".comments");
            var idSweet = $(this).data("idsweet");
            var result = get_comments(getCookie("key"), idSweet);
            result.success(function (response) {
                if (response.code == 200) {
                    for (var i in response.comments){
                        var comment = response.comments[i];

                        var commentHtml = "<div class='comment'><div class='user-info'><img src='#'>" +
                            "<span class='name'>" + comment.l_name + " " + comment.f_name + "</span>" +
                            "<span class='username'>@" + comment.username + "</span></div> " +
                            "<div class='body'>" + comment.comment + "</div></div>";
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
            //TODO add comment
            console.log("add comment");
        });
    });
}

function initAddSweet() {
    $(document).ready(function () {
        $(".add-sweet button").on('click', function () {
            //TODO add sweet
            console.log("add sweet");
        });
    })

}
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
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
