$(document).ready(function() {

    init()

    /**
     * Dans la page de login, click sur le bouton "Pas encore inscrit"
     */
    $('#notregister').click(function(e){
        hideAll();
        $('#register').show()
    });

    /**
     * Appuie sur le bouton se connecter dans la page login
     */
    $('#login_connexion').click(function(e){
         console.log($('#login_name'))
        var login = $('.login_input')[0].value; //On récupère les username et mdp
        var password = $('.login_input')[1].value;
         console.log(login+" "+password)
        connection(login, password); //Et on se connecte au serveur
    });

    /**
     * Clique sur le bouton annuler de la page register
     */
    $('.button_cancel').click(function(e){
        $('#register').hide();
        showLogin()
    });

    /**
     * Clique sur le boutton d'oublie de mot de passe
     */
    $('#lost_password').click(function(e){
        setPasswordLostDefaultPanel();
        $('#login').hide();
        $('#password_lost').show();
    });

    // Fonction qui sera appelé lorsqu'on clique sur la barre de recherche
    $('#bar-recherche').click(function(e){
       console.log("Appuie dans la barre de recherche");
       $(this).css({width: "85%", transition: "width 1s"});
       $(this).css({"margin-left": "10%", transition: "margin-left 1s"});
    });

    /**
     * Dès qu'il y a une touche qui est appuyé dans la barre de recherche
     */
    $('#bar-recherche').keyup(function(e){
        var val = $('#bar-recherche')[0].value; //Récup la valeur dans la barre
        // console.log(val);
        $('#browsers').empty();
        if($('#search_by_user').is(':checked') && val.length!=0) { //Si la valeur n'est pas vide
            getUserList(val)
        }
    });

    /**
     * Clique sur le bouton de recherche
     */
    $('#search-logo').click(function(){
        // console.log($('#search_by_user').is(':checked'));
        if($('#search_by_user').is(':checked')){
            var username = $('#bar-recherche')[0].value; //Récup la valeur dans la barre de recherche
            getProfileByUsernameForSearch(username);
        }else{
            var message = $('#bar-recherche')[0].value;
            console.log(message)
            displaySearchedTwiste(message);
        }
    });

    //Si on clique quelque part dans la page
    $("body").click(function(e){
        // console.log(e.target.attributes[0])
        if(e.target.attributes[0]==undefined || e.target.attributes[0].value!="bar-recherche" && e.target.attributes[0].value!="browsers"){
            var bar_recherche=parseInt($('#bar-recherche').css("width"));
            var parent_bar_recherche=parseInt($('#bar-recherche').parent().css("width"))/2;
            // console.log("Valeur du width de la barre de recherche : "+bar_recherche+" Valeur width parent :"+parent_bar_recherche);
            if(bar_recherche>parent_bar_recherche){
                $('#bar-recherche').css({width: "35%"});
                $('#bar-recherche').css({"margin-left": "60%", transition: "margin-left 0s"});
            }
        }
    });

    /**
     * Clique sur déconnexion
     */
    $('#logout').click(function(e){
        logout();
        setMyProfileDefaultPanel();//On va mettre les panels par défaut
        setProfileDefaultPanel();
        setSweetDefaultPanel();
        // console.log(res);
    });

    /**
     * Si on clique sur son nom
     */
    $('#user').click(function(){
       hideAll();
        $('#myprofile').show(); //La page mon profil apparaît
        $('#middlediv').show();
        $('#log').show();
    });

    /**
     * Clique sur le logo ou le nom du site
     */
    $('#home_page').click(function(){
       hideAll();
       showMainPage(); //Reviens sur la page principale
    });

    /**
     * Clique sur le bouton suivre sur un profil
     */
    $('#follow').click(function(){
        var key = localStorage.getItem("user-key"); //récupère la clé
        var follow_id = current_user.id; //Récupère l'id du profil courant
        // console.log(localStorage.getItem("user_id"), follow_id);
        if(localStorage.getItem("user_id") != follow_id){
            follow(key, follow_id); //Appel du service
        }else{
            alert("Vous ne pouvez pas vous suivre vous même.")
        }
    });

    /**
     * Clique sur ne plus suivre dans un profil; même procédure que pour follow
     */
    $('#unfollow').click(function(){
        var key = localStorage.getItem("user-key");
        var follow_id = current_user.id;
        unfollow(key, follow_id);
    });

    /**
     * Clique sur l'ajout d'un nouveau message (Parent)
     */
    $('#new-message-button').click(function(){
        // console.log($('#new-message').val());
        var sweet = $('#new-message').val();
        addSweet(sweet)
    });

    /**
     * Click sur la reinitialisation des messages
     */
    $('#reset_search_btn').click(function(){
        $('#reset_search_btn').hide();
        fillSweet(default_list_comment, true);
        list_comment=default_list_comment;
    });

});
var list_follow=new Array(0);
var current_user=null;

/**
 * Fonction qui va initialiser
 */
function init(){
    hideAll();
    if(!isLogged()){
        // $('#login').hide()
        showLogin();
    }else{
        $('#login').hide();
        showMainPage();
        getMyProfile();//Récupère et rempli la page mon profil
        $('#new-message').val("");
    }
}

/**
 * Fonction qui va afficher la page de login et cacher le reste
 */
function showLogin(){
    hideAll();
    $('#login').show();
}

/**
 * Fonction qui va afficher la page principale
 */
function showMainPage(){
    $('#middlediv').show();
    $('#log').show();
    $('.maindiv').show();
}

/**
 * Fonction qui va tous cacher
 */
function hideAll(){
    $('#register').hide();
    $('#middlediv').hide();
    $('#log').hide();
    $('.maindiv').hide();
    $('#password_lost').hide();
    $('#profile').hide();
    $('#myprofile').hide();
    $('#login').hide();
    $('.commentaire-item').hide();
    $('#reset_search_btn').hide(); //Boutton pour réinitialiser la recherche est caché à l'initialisation!
}

/**
 * Fonction qui va récupérer tous les usernames qui correspondent à la valeur dans la barre
 * de recherche et l'ajoute dans la datalist de la barre de recherche
 * @param list Liste des usernames qui correspondent à la recherche
 */
function addListToSearchBar(list){
    var item = null;
    list.forEach(function(e){
        item="<option value=\""+e.username+"\">";
        $('#browsers').append(item);
    });
}

/**
 * Fonction qui va mettre le texte pour le nom de l'utilisateur et ses données
 * @param user
 */
function setUserProfile(user){
    var found=false;
    $('.profile_username').text(user.username);
    $('#profile_lastname').text(user.lastname);
    $('#profile_firstname').text(user.firstname);
    list_follow.forEach(function(e){
        // console.log(e);
        if(e.followed_username==user.username){
            found=!found;
            console.log("test");
            showUnfollow();
        }
    });
    if (!found){
        showFollow();
    }
}

/**
 * Fonction qui va prendre une liste de follow en paramètre
 * Il va ajouter dans la page principal, tous les follow de la personne connecté
 * @param list
 */
function setListFollowed(){
    console.log("test1")
    $('#list_followed').empty()
    var item=null;
    list_follow.forEach(function(e){
        item="<div class='followed_list_main' ><p><span class='followed_username'>"+e.followed_username+
            "</span><img id='userId_"+e.followed_id+"' class='unfollow_main' src=\"photos/cancel.png\" title='Ne plus suivre' height=\"30\" width=\"30\"></p><div>"
        $('#list_followed').append(item);
    })
}

/**
 * Fonction qui va afficher le bouton 'Suivre' dans la page des profiles
 */
function showFollow(){
    $('#follow').show();
    $('#unfollow').hide();
}

/**
 * Fonction qui va afficher le bouton 'ne plus suivre' dans la page des profiles
 */
function showUnfollow(){
    $('#follow').hide();
    $('#unfollow').show();
}

/**
 * Des listeners qu'on va ajouter quand on va ajouter la liste des follow dans la page main
 */
function setListenerTofollow(){
    $('.unfollow_main').click(function(e){
        // console.log("testttt")
        var username=$(this).parent().first().text();
        // console.log($(this).parent().first().text());
        var response = prompt("Etes-vous sûr de ne plus suivre "+username+'? [Oui/Non]', "");
        // console.log(person);
        if(response!=null && response.toLowerCase()=="oui"){
            var key = localStorage.getItem("user-key"); //récupère la clé
            var followed_id = $(this).attr('id').split("_")[1]; //Récupère l'id du followed grâce à son id qui est userId_X
            unfollow(key, followed_id); //Appel du service
        }
    });
    $(".followed_username").click(function(e){
        // console.log($(this).text());
        var username = $(this).text();
        getProfileByUsername(username);
    });

}

function setListenerToSweet(id){
    $('.add_comment').val("");//Bug des textarea, par défaut il y a plein d'espace et on ne voit pas le placeholder
    $('#new-message').val("");//Pareil que celui au dessus
    /**
     * Clique sur le bouton pour voir ou réduire les commentaires d'un post
     */
    $('#'+id+' .show-com').click(function(e) {
        console.log("Appuie sur le bouton commentaire");
        if($(this).parent().find('.commentaire-item').first().is(":visible")){
            $(this).parent().find('.commentaire-item').first().hide();
            $(this).text('+ Commentaires');
        } else {
            $(this).parent().find('.commentaire-item').first().show();
            $(this).text('- Commentaires');
        }
    })

    $('#'+id+' .add_comment_button').mousedown(function(e){
        var sweet_id = $(this).parent().parent().parent().attr('id');
        var message = $(this).parent().find('.add_comment').val();
        // console.log(sweet_id, message);
        if(message.length!=0){
            // console.log("not empty")
            addComment(sweet_id, message)
        }
    });
}

/**
 * Fonction qui va ajouter un listener sur le bouton afficher plus
 */
function setListenerShowMore(){
    $('#show_more').click(function(){
        console.log("show more clicked!!");
        $(this).remove();//On enlève le bouton courant
        max_val_display_sweet+=10; //On incrément la valeur maximal d'affichage des sweets
        fillSweet(list_comment, false); //On appel la fonction qui va ajouter 10 sweet dans le fil de l'actualité
        //Argument false pour dire qu'on est pas à l'initialisation et donc on de supprime pas les anciens message
        //Mais on les ajoute!
    })
}

/**
 * Fonction qui va afficher les Twists selon une recherche
 * @param text
 */
function displaySearchedTwiste(text){
    if(text.length==0){ //Si le texte est vide
        fillSweet(default_list_comment, true); //On remet tous les messages qu'il y avait
        $('#reset_search_btn').hide(); // On cache le boutton de reinitialisation de la recherche
        list_comment=default_list_comment;
        return;
    }
    var searched_list=new Array();
    list_comment.forEach(function(value){
        // console.log(value.sweet.match(text))
        if(value.sweet.match(text)!=null){ //On regarde qui le sweet contient au moins une fois la valeur du text
            searched_list.push(value); // Si c'est le cas alors on ajoute le sweet dans la list
        }
    });
    fillSweet(searched_list, true); //Et on met tous les messages dans le board
    $('#reset_search_btn').show(); //On affiche le boutton de reinitialisation de la recherche
}