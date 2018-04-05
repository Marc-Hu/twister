$(document).ready(function() {

    /**
     * Appuie sur une touche dans un des champs de la page pour s'enregistrer
     */
    $('.register_input').keyup(function(){
        $('#register_button_register').prop("disabled", true) //Boutton S'inscrire est disable
        // Récupération des valeurs des champs
        var name = $('.register_input')[0].value;
        var surname = $('.register_input')[1].value
        var login = $('.register_input')[2].value
        var email = $('.register_input')[3].value
        var password = $('.register_input')[4].value
        var repeat = $('.register_input')[5].value
        //On vérifie si tous les champs sont valides
        var field_valid = verifField(name, surname, login, email, password, repeat)
        // console.log(surname+" "+name+" "+login+" "+email+" "+password+" "+repeat)
        $(this).addClass("touched") //On ajoute une classe sur le input courant
        if(name.length==0 && $('#register_name').hasClass("touched")){ //Si il n'y a pas de prénom
            $('#msg-err-register').text("Prénom obligatoire")
        }else if (surname.length==0 && $('#register_surname').hasClass("touched")){//Si il n'y a pas de nom
            $('#msg-err-register').text("Nom de famille obligatoire")
        }else if (login.length==0 && $('#register_login').hasClass("touched")){ //Si il n'y a pas de login
            $('#msg-err-register').text("Login obligatoire")
        }else if (email.length==0 && $('#register_email').hasClass("touched")){//Si il n'y a pas d'email
            $('#msg-err-register').text("Email obligatoire")
        }else if (password.length==0 && $('#register_password').hasClass("touched")){//Si il n'y a pas de mot de passe
            $('#msg-err-register').text("Mot de passe obligatoire")
        }else if (repeat.length==0 && $('#register_repeat').hasClass("touched")){//Si il n'y a pas de mot de passe répété
            $('#msg-err-register').text("Répéter le mot de passe obligatoire")
        }else if (password!=repeat){ //Si les deux mots de passe sont différent
            $('#msg-err-register').text("Les deux mots de passe doivent être identitque")
        }else if (field_valid){ //Si le formaulaire est valide
            // console.log("form good")
            $('#msg-err-register').text("")
            $('#register_button_register').prop("disabled", false)
        }else if (!verifEmail(email) && $('#register_email').hasClass("touched")){ //Si l'email ne correspond pas à un email valide
            $('#msg-err-register').text("Email invalide")
        }else{ //Sinon
            $('#msg-err-register').text("")
        }
    });

    /**
     * Click sur le bouton s'enregistrer de la page register
     */
    $('#register_button_register').click(function(e){
        //Récupération des données
        var name = $('.register_input')[0].value;
        var surname = $('.register_input')[1].value;
        var login = $('.register_input')[2].value;
        var email = $('.register_input')[3].value;
        var password = $('.register_input')[4].value;
        var repeat = $('.register_input')[5].value;
        //On va créer un utilisateur
        createUser(name, surname, login, email, password, repeat);
    });

});

var regex_email = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

// Fonction qui va vérifier si tous les champs sont complété
function verifField(name, surname, login, email, password, repeat){
    return name.length!=0 && surname.length!=0 && login.length!=0 && email.length!=0 && password.length!=0 && repeat.length!=0 && password==repeat && regex_email.test(email.toLowerCase());
}

//Fonction qui va vérifier si l'email dans le champs est un email valide
function verifEmail(email){
    return regex_email.test(email);
}

/**
 * Fonction qui va mettre les données dans la page du profil de la personne connecté
 */
function getMyProfile(){
    getProfile();
}