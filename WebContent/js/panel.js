/**
 * Fonction qui va remettre par defaut le panneau de login en enlevant les textes
 */
function setLoginDefaultPanel(){
    $('#login_name').val("");
    $('#login_name').removeClass("touched");
    $('#login_password').val("");
    $('#login_password').removeClass("touched");
    $('#msg-err-login').text("");
    $('#login_connexion').prop("disabled", true)
}

/**
 * Fonction qui va remettre par défaut la page de l'inscription
 */
function setRegisterDefaultPanel(){
    //Il va d'abord mettre toutes les valeurs à vide
    $('#register_name').val("");
    $('#register_surname').val("");
    $('#register_login').val("");
    $('#register_email').val("");
    $('#register_password').val("");
    $('#register_repeat').val("");
    //Il va ensuite enlever toutes les classes ajouter
    $('#register_name').removeClass("touched");
    $('#register_surname').removeClass("touched");
    $('#register_login').removeClass("touched");
    $('#register_email').removeClass("touched");
    $('#register_password').removeClass("touched");
    $('#register_repeat').removeClass("touched");
    //Le message d'erreur sera vide
    $('#msg-err-register').text("");
    //Et il desactive le bouton pour s'inscrire
    $('#register_button_register').prop("disabled", true)
}

/**
 * Fonction qui va remttre par défaut la page du mot de passe perdu
 */
function setPasswordLostDefaultPanel(){
    $('#lost_password_input').val("");
    $('#lost_password_input').removeClass("touched");
    $('#msg_err_password_lost').text("")
    $('#password_lost_validate').prop("disabled", true);
}

/**
 * Fonction qui va remettre par défaut le panel des profils
 */
function setProfileDefaultPanel(){
    $('.profile_username').text("");
    $('#profile_lastname').text("");
    $('#profile_firstname').text("");
}

/**
 * Fonction qui va remettre par défaut le panel de notre profil (appeler lors de la déconnexion)
 */
function setMyProfileDefaultPanel(){
    $('#myprofile_username').text("");
    $('#myprofile_lastname').text("");
    $('#myprofile_firstname').text("");
}

function setSweetDefaultPanel(){
    $('.contenu-message').text("");
    $('.user-message').text("");
}

function addComment(comment_parent){
    
}