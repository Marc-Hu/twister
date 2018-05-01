/*
* login method
* */


function login(login_form) {
    var post_url = login_form.attr("action"); //get form action url
    var form_data = login_form.serialize(); //Encode form elements for submission

    return $.post(post_url, form_data);
}


function register(register_from) {
    if (!password_validation())
        return null;

    var post_url = register_from.attr("action"); //get form action url
    var form_data = register_from.serialize(); //Encode form elements for submission

    return $.post(post_url, form_data);

}

function logout(key) {
    var post_url = "http://localhost:8080/Twister/user/logout";
    var form_data = "key=" + key; //Encode form elements for submission
    return $.post(post_url, form_data);

}

function follow(key, followedId) {
    var post_url = "http://localhost:8080/Twister/user/follow";
    var form_data = "follow=true&key=" + key + "&followedId=" + followedId; //Encode form elements for submission
    return $.post(post_url, form_data);

}

function unfollow(key, followedId) {
    var post_url = "http://localhost:8080/Twister/user/follow";
    var form_data = "follow=false&key=" + key + "&followedId=" + followedId; //Encode form elements for submission
    return $.post(post_url, form_data);

}

function get_followed_users(key) {
    var post_url = "http://localhost:8080/Twister/user/listFollowed";
    var form_data = "key=" + key;
    return $.post(post_url, form_data);


}

function search_user(key, username) {
    var post_url = "http://localhost:8080/Twister/user/listUser";
    var form_data = "key=" + key + "&username=" + username;
    return $.post(post_url, form_data);
}

function sweet(key, sweet) {
    var post_url = "http://localhost:8080/Twister/sweet/";
    var form_data = "key=" + key + "&sweet=" + sweet;
    return $.post(post_url, form_data);

}

function add_comment(key, sweetId, comment) {
    var post_url = "http://localhost:8080/Twister/sweet/addComment";
    var form_data = "key=" + key + "&sweetId=" + sweetId + "&comment=" + comment; //Encode form elements for submission
    return $.post(post_url, form_data);

}

