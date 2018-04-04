$(document).ready(function () {

    init()

    /**
     * Dans la page de login, click sur le bouton "Pas encore inscrit"
     */
    $('#notregister').click(function (e) {
        hideAll();
        $('#register').show()
    });

    /**
     * Appuie sur le bouton se connecter dans la page login
     */
    $('#login_connexion').click(function (e) {
        // console.log($('#login_name'))
        var login = $('.login_input')[0].value;
        var password = $('.login_input')[1].value;
        // console.log(login+" "+password)
        var connected = connection(login, password);
        console.log(connected)
        if (connected) {
            getMyProfile()
            $('#login').hide();
            showMainPage()
        } else {
            $('#msg-err-login').text("Nom d'utilisateur ou mot de passe erroné");
        }
    });

    /**
     * Clique sur le bouton annuler de la page register
     */
    $('.button_cancel').click(function (e) {
        $('#register').hide();
        showLogin()
    });

    /**
     * Clique sur le boutton d'oublie de mot de passe
     */
    $('#lost_password').click(function (e) {
        setPasswordLostDefaultPanel();
        $('#login').hide();
        $('#password_lost').show();
    });

    // Fonction qui sera appelé lorsqu'on clique sur la barre de recherche
    $('#bar-recherche').click(function (e) {
        console.log("Appuie dans la barre de recherche");
        $(this).css({width: "85%", transition: "width 1s"});
        $(this).css({"margin-left": "10%", transition: "margin-left 1s"});
    });

    /**
     * Dès qu'il y a une touche qui est appuyé dans la barre de recherche
     */
    $('#bar-recherche').keyup(function (e) {
        var val = $('#bar-recherche')[0].value; //Récup la valeur dans la barre
        // console.log(val);
        $('#browsers').empty();
        if (val.length != 0) { //Si la valeur n'est pas vide
            getUserList(val)
        }
    });

    /**
     * Clique sur le bouton de recherche
     */
    $('#search-logo').click(function () {
        var username = $('#bar-recherche')[0].value; //Récup la valeur dans la barre de recherche
        getProfileByUsernameForSearch(username);
    });

    //Si on clique quelque part dans la page
    $("body").click(function (e) {
        // console.log(e.target.attributes[0])
        if (e.target.attributes[0] == undefined || e.target.attributes[0].value != "bar-recherche" && e.target.attributes[0].value != "browsers") {
            var bar_recherche = parseInt($('#bar-recherche').css("width"));
            var parent_bar_recherche = parseInt($('#bar-recherche').parent().css("width")) / 2;
            // console.log("Valeur du width de la barre de recherche : "+bar_recherche+" Valeur width parent :"+parent_bar_recherche);
            if (bar_recherche > parent_bar_recherche) {
                $('#bar-recherche').css({width: "35%"});
                $('#bar-recherche').css({"margin-left": "60%", transition: "margin-left 0s"});
            }
        }
    });

    /**
     * Clique sur déconnexion
     */
    $('#logout').click(function (e) {
        logout();
        setMyProfileDefaultPanel();//On va mettre les panels par défaut
        setProfileDefaultPanel();
        setSweetDefaultPanel();
        // console.log(res);
    });

    /**
     * Si on clique sur son nom
     */
    $('#user').click(function () {
        hideAll();
        $('#myprofile').show(); //La page mon profil apparaît
        $('#middlediv').show();
        $('#log').show();
    });

    /**
     * Clique sur le logo ou le nom du site
     */
    $('#home_page').click(function () {
        hideAll();
        showMainPage(); //Reviens sur la page principale
    });

    /**
     * Clique sur le bouton suivre sur un profil
     */
    $('#follow').click(function () {
        var key = localStorage.getItem("user-key"); //récupère la clé
        var follow_id = current_user.id; //Récupère l'id du profil courant
        // console.log(key, follow_id);
        // console.log(follow(key, follow_id));
        follow(key, follow_id); //Appel du service
    });

    /**
     * Clique sur ne plus suivre dans un profil; même procédure que pour follow
     */
    $('#unfollow').click(function () {
        var key = localStorage.getItem("user-key");
        var follow_id = current_user.id;
        unfollow(key, follow_id);
    });

    $('#new-message-button').click(function () {
        // console.log($('#new-message').val());
        var sweet = $('#new-message').val();
        addSweet(sweet)
    });

});
var list_follow = new Array(0);
var current_user = null;

/**
 * Fonction qui va initialiser
 */
function init() {
    hideAll();
    if (!isLogged()) {
        // $('#login').hide()
        showLogin();
    } else {
        $('#login').hide();
        showMainPage();
        getMyProfile();//Récupère et rempli la page mon profil
    }
}

/**
 * Fonction qui va afficher la page de login et cacher le reste
 */
function showLogin() {
    hideAll();
    $('#login').show();
}

/**
 * Fonction qui va afficher la page principale
 */
function showMainPage() {
    $('#middlediv').show();
    $('#log').show();
    $('.maindiv').show();
    getListFollowed();
}

/**
 * Fonction qui va tous cacher
 */
function hideAll() {
    $('#register').hide();
    $('#middlediv').hide();
    $('#log').hide();
    $('.maindiv').hide();
    $('#password_lost').hide();
    $('#profile').hide();
    $('#myprofile').hide();
    $('#login').hide();
    $('.commentaire-item').hide();
}

/**
 * Fonction qui va récupérer tous les usernames qui correspondent à la valeur dans la barre
 * de recherche et l'ajoute dans la datalist de la barre de recherche
 * @param list Liste des usernames qui correspondent à la recherche
 */
function addListToSearchBar(list) {
    var item = null;
    list.forEach(function (e) {
        item = "<option value=\"" + e.username + "\">";
        $('#browsers').append(item);
    });
}

/**
 * Fonction qui va mettre le texte pour le nom de l'utilisateur et ses données
 * @param user
 */
function setUserProfile(user) {
    var found = false;
    $('.profile_username').text(user.username);
    $('#profile_lastname').text(user.lastname);
    $('#profile_firstname').text(user.firstname);
    list_follow.forEach(function (e) {
        // console.log(e);
        if (e.followed_username == user.username) {
            found = !found;
            console.log("test");
            showUnfollow();
        }
    });
    if (!found) {
        showFollow();
    }
}

/**
 * Fonction qui va prendre une liste de follow en paramètre
 * Il va ajouter dans la page principal, tous les follow de la personne connecté
 * @param list
 */
function setListFollowed() {
    $('#list_followed').empty();
    var item = null;
    list_follow.forEach(function (e) {
        item = "<div class='followed_list_main' ><p><span class='followed_username'>" + e.followed_username +
            "</span><img id='userId_" + e.followed_id + "' class='unfollow_main' src=\"photos/cancel.png\" title='Ne plus suivre' height=\"30\" width=\"30\"></p><div>"
        $('#list_followed').append(item);
    })
}

/**
 * Fonction qui va afficher le bouton 'Suivre' dans la page des profiles
 */
function showFollow() {
    $('#follow').show();
    $('#unfollow').hide();
}

/**
 * Fonction qui va afficher le bouton 'ne plus suivre' dans la page des profiles
 */
function showUnfollow() {
    $('#follow').hide();
    $('#unfollow').show();
}

/**
 * Des listeners qu'on va ajouter quand on va ajouter la liste des follow dans la page main
 */
function setListenerTofollow() {
    $('.unfollow_main').click(function (e) {
        // console.log("testttt")
        var username = $(this).parent().first().text();
        // console.log($(this).parent().first().text());
        var response = prompt("Etes-vous sûr de ne plus suivre " + username + '? [Oui/Non]', "");
        // console.log(person);
        if (response != null && response.toLowerCase() == "oui") {
            var key = localStorage.getItem("user-key"); //récupère la clé
            var followed_id = $(this).attr('id').split("_")[1]; //Récupère l'id du followed grâce à son id qui est userId_X
            // console.log(followed_id);
            unfollow(key, followed_id); //Appel du service
            getListFollowed();
        }
    });
    $(".followed_username").click(function (e) {
        // console.log($(this).text());
        var username = $(this).text();
        getProfileByUsername(username);
    });

}

function setListenerToSweet() {
    $('.add_comment').val("");//Bug des textarea, par défaut il y a plein d'espace et on ne voit pas le placeholder
    $('#new-message').val("");//Pareil que celui au dessus
    /**
     * Clique sur le bouton pour voir ou réduire les commentaires d'un post
     */
    $('.show-com').click(function (e) {
        console.log("Appuie sur le bouton commentaire");
        if ($(this).parent().find('.commentaire-item').first().is(":visible")) {
            $(this).parent().find('.commentaire-item').first().hide();
            $(this).text('+ Commentaires');
        } else {
            $(this).parent().find('.commentaire-item').first().show();
            $(this).text('- Commentaires');
        }
    })

    $('.add_comment_button').mousedown(function (e) {
        var sweet_id = $(this).parent().parent().parent().attr('id');
        var message = $(this).parent().find('.add_comment').val();
        console.log(sweet_id, message);
        if (message.length != 0) {
            // console.log("not empty")
            addComment(sweet_id, message)
        }
    });
}