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

function get_profile(key, id_or_username) {
    var post_url = "http://localhost:8080/Twister/user/profile";
    var form_data = "key=" + key + "&id=" + id_or_username; //Encode form elements for submission
    return $.get(post_url, form_data);
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

function get_following_users(key) {
    var post_url = "http://localhost:8080/Twister/user/following";
    var form_data = "key=" + key;
    return $.get(post_url, form_data);
}

function search_user(key, username) {
    var post_url = "http://localhost:8080/Twister/user/search";
    var form_data = "key=" + key + "&username=" + username;
    return $.post(post_url, form_data);
}
function get_news_feed(key) {
    var post_url = "http://localhost:8080/Twister/sweet/newsfeed";
    var form_data = "key=" + key;
    return $.get(post_url, form_data);
}

function get_sweets(key, userId) {
    var post_url = "http://localhost:8080/Twister/sweet/user";
    var form_data = "key=" + key + "&id=" + userId;
    return $.get(post_url, form_data);
}
function add_sweet(key, sweet) {
    var post_url = "http://localhost:8080/Twister/sweet/add";
    var form_data = "key=" + key + "&sweet=" + sweet;
    return $.post(post_url, form_data);
}


function remove_sweet(key, sweetId) {
    var post_url = "http://localhost:8080/Twister/sweet/remove";
    var form_data = "key=" + key + "&sweetId=" + sweetId;
    return $.post(post_url, form_data);
}

function like_sweet(key, sweetId) {
    var post_url = "http://localhost:8080/Twister/sweet/like";
    var form_data = "like=true&key=" + key + "&sweetId=" + sweetId;
    return $.post(post_url, form_data);
}

function unlike_sweet(key, sweetId) {
    var post_url = "http://localhost:8080/Twister/sweet/like";
    var form_data = "like=false&key=" + key + "&sweetId=" + sweetId;
    return $.post(post_url, form_data);
}

function get_comments(key,sweetId) {
    var post_url = "http://localhost:8080/Twister/sweet/comment/get";
    var form_data = "key=" + key + "&sweetId=" + sweetId; //Encode form elements for submission
    return $.get(post_url, form_data);
}

function add_comment(key, sweetId, comment) {
    var post_url = "http://localhost:8080/Twister/sweet/comment/add";
    var form_data = "key=" + key + "&sweetId=" + sweetId + "&comment=" + comment; //Encode form elements for submission
    return $.post(post_url, form_data);
}

function remove_comment(key, sweetId, commentId) {
    var post_url = "http://localhost:8080/Twister/sweet/comment/remove";
    var form_data = "key=" + key + "&sweetId=" + sweetId + "&commentId=" + commentId; //Encode form elements for submission
    return $.post(post_url, form_data);
}

function like_comment(key, sweetId,commentId) {
    var post_url = "http://localhost:8080/Twister/sweet/comment/like";
    var form_data = "like=true&key=" + key + "&sweetId=" + sweetId + "&commentId=" + commentId; //Encode form elements for submission
    return $.post(post_url, form_data);
}
function unlike_comment(key, sweetId, commentId) {
    var post_url = "http://localhost:8080/Twister/sweet/comment/like";
    var form_data = "like=false&key=" + key + "&sweetId=" + sweetId + "&commentId=" + commentId; //Encode form elements for submission
    return $.post(post_url, form_data);
}