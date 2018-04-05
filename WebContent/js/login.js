$(document).ready(function() {

    /**
     * Lorsqu'une touche est frappé dans la formulaire de login
     */
    $('.login_input').keyup(function(){
        $('#login_connexion').prop("disabled", true) //Boutton Se connecter est disable
        var login = $('.login_input')[0].value; //Récup des valeurs de login
        var password = $('.login_input')[1].value //Et de password
        $(this).addClass("touched") //On ajoute une classe touched pour dire si le input est touché
        if(login.length==0 && $('#login_name').hasClass("touched")){ //Si c'est le cas et que la longueur de login est 0
            $('#msg-err-login').text("Nom d'utilisateur obligatoire") //On affiche le message d'erreur
        }else if (password.length==0 && $('#login_password').hasClass("touched")){ //Pareil pour password
            $('#msg-err-login').text("Mot de passe obligatoire")
            //Sinon si tout est là, alors on enlève le disable du boutton pour se connecter
        }else if(login.length!=0 && password.length!=0){
            $('#msg-err-login').text("")
            $('#login_connexion').prop("disabled", false)
            console.log($('#login_connexion').prop("disabled"));
        }else {
            $('#msg-err-login').text("")
        }
    });

    /**
     * Lorsqu'on clique sur un bouton qui nous amène sur la page de login
     */
    $('.login_page').click(function(e) {
        removeUserKey()
        setLoginDefaultPanel()
        showLogin()
    });

    /**
     * Lorsqu'une touche est pressé par l'utilisateur dans le champs email de la page mot de passe perdu
     */
    $('#lost_password_input').keyup(function(){
        // console.log("Appuie sur une touche")
        $('#password_lost_validate').prop("disabled", true);
        var email = $(this).val();
        $(this).addClass("touched");
        // console.log(email)
        if( email.length==0 && $(this).hasClass("touched")){
            $('#msg_err_password_lost').text("Email obligatoire")
        }else if (!verifEmail(email)){
            $('#msg_err_password_lost').text("Email invalide")
        }else{
            $('#msg_err_password_lost').text("")
            $('#password_lost_validate').prop("disabled", false);
        }
    })

    /**
     * Lorsqu'on clique sur le bouton envoyer de la page du mot de passe perdu
     */
    $('#password_lost_validate').click(function(e){
        // console.log("L'email est valide!!")
        var email = $('#lost_password_input').val(); //récup val
        var result = passwordLost(email); //Envoie requete au serveur
        if(result){ //Si c'est bon
            alert("Un email a été envoyé à l'adresse mail indiqué.");
            $('#password_lost').hide();
            showLogin();
        }else{
            $('#msg_err_password_lost').text("L'email n'existe pas dans notre base")
        }
    });
});