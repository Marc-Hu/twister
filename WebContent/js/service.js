
/**
 * Fonction qui va appeler l'API pour se connecter
 * @param username
 * @param password
 * @returns Object JSON
 */
function connection(username, password){
    var post_url = "http://localhost:8080/Twister/user/login" //get form action url
    var form_data = "username=" + username + "&password=" + password;
    $.post( post_url, form_data, function( response ) {
        // console.log(response)
        response = JSON.parse(response);
        if (response.hasOwnProperty('key')){
            console.log("success connected");
            // console.log(data);
            localStorage.setItem("user-key", response.key); //Si l'utilisateur existe on ajoute la clé dans le localStorage
            localStorage.setItem("user-username", username);
            getMyProfile();
            $('#login').hide();
            showMainPage();
            // showMainPage();
        }else{
            // alert(response.message);
            $('#msg-err-login').text("Nom d'utilisateur ou mot de passe erroné");
        }
    });
}

/**
 * Fonction qui va enlever la clé de l'utilisateur dans le localstorage
 */
function removeUserKey(){
    localStorage.removeItem("user-key");
    localStorage.removeItem("user-username");
    localStorage.removeItem("user_id");
}

/**
 * Fonction qui va appeler l'API pour créer un nouvel utilisateur
 * @param name
 * @param surname
 * @param login
 * @param email
 * @param password
 * @param repeat
 * @returns object JSON
 */
function createUser(name, surname, login, email, password, repeat){

    var post_url = "http://localhost:8080/Twister/user/create" //get form action url
    var form_data = "f_name=" + name + "&l_name=" + surname+"&username="+login+"&password="+password;
    $.post( post_url, form_data, function( response ) {
        // console.log(response)
        response = JSON.parse(response);
        if(response.code==200){//Si la réponse est bonne (par la suite on mettra une condition sur le code de retour
            console.log("success register")
            connection(login, password);
            $('#register').hide();
        }else{ //Sinon
            $('#msg-err-register').text("L'utilisateur existe déjà")// Sera modifié par la suite car on à le message de retour de l'API
        }
    });
}

/**
 * A faire si il y a le temps; Fonction qui récupère le mot de passe d'une personne
 * @param email
 * @returns {boolean}
 */
function passwordLost(email){
    console.log(email);
    //Appel à l'API pour retrouver le mdp
    return true;
}

/**
 * Fonction va va déconnecter l'utilisateur en appelant l'API concerné
 * @returns {any}
 */
function logout(){
    var key = localStorage.getItem("user-key");
    var url = "http://localhost:8080/Twister/user/logout?key="+key;
    $.getJSON(url, function(data, status){
        // console.log(1);
    }).done(function(data){
        // console.log(2, data);
        if (data.code==200){
            console.log("success logout");
            setMyProfileDefaultPanel();//On va mettre les panels par défaut
            setProfileDefaultPanel();
            setDefaultMain();
        }else{
            console.log("error");
        }
    });
}

/**
 * Fonction qui va récupérer les données du profil de l'utilisateur connecté
 * @returns {any}
 */
function getProfile(){
    var username = localStorage.getItem("user-username");
    var url = "http://localhost:8080/Twister/user/profile?username="+username+"&key="+localStorage.getItem('user-key');
    $.getJSON(url, function(data, status){
        // console.log(1);
    }).done(function(data){
        // console.log(2, data);
        if (data.hasOwnProperty("username")){
            console.log("success get profile")
            $('#profile_username_board').text(data.username);
            $('#myprofile_username').text(data.username);
            $('#myprofile_lastname').text(data.lastname);
            $('#myprofile_firstname').text(data.firstname);
            localStorage.setItem("user_id", data.id);
            showMainPage();
            getListFollowed();
        }else{
            console.log("error");
        }
    });
}

/**
 * Fonction qui va récupérer un profil selon son username
 * @param username
 * @returns {any}
 */
function getProfileByUsername(username){
    var url = "http://localhost:8080/Twister/user/profile?username="+username+"&key="+localStorage.getItem('user-key');
    $.getJSON(url, function(data, status){
        // console.log(1, "getProfileByUsername");
    }).done(function(data){
        // console.log(2, data);
        if(data.hasOwnProperty("firstname")){ //Si l'utilisateur existe
            console.log("Get profile by username success")
            current_user=data; //On met dans une var globale l'utilisateur
            setUserProfile(data);//On va mettre à jour la page des profils
            hideAll(); //On cache tout
            $('#profile').show(); //Et on ne montre que la page concerné
            $('#middlediv').show();
            $('#log').show();
        }else{
            console.log("error");
        }
    });
}

/**
 * Fonction qui va récupérer le profile d'une personne selon son username (login) pour la barre de recherche
 * @param username
 */
function getProfileByUsernameForSearch(username){
    var url = "http://localhost:8080/Twister/user/profile?username="+username+"&key="+localStorage.getItem("user-key");
    $.getJSON(url, function(data, status){
        // console.log(1, "getProfileByUsername");
    }).done(function(data){
        // console.log(2, data);
        if(data.hasOwnProperty("firstname")){ //Si l'utilisateur existe
            current_user=data; //On met dans une var globale l'utilisateur
            setUserProfile(data);//On va mettre à jour la page des profils
            hideAll(); //On cache tout
            $('#profile').show(); //Et on ne montre que la page concerné
            $('#middlediv').show();
            $('#log').show();
        }else{
            console.log("error");
        }
    });
}

/**
 * Fonction qui va renvoyer si il y a une personne qui est actuellement connecté ou non
 * @returns {boolean}
 */
function isLogged(){
    // console.log(localStorage.getItem("user-key"))
    return !!localStorage.getItem("user-key") // Ne fonctioone pas sur IE en file://, fonctionne sur les site HTTP
}

/**
 * Fonction qui va récupérer la liste des users selon un nom précis
 * @param username
 * @returns {any}
 */
function getUserList(username){
    var url = "http://localhost:8080/Twister/user/list?username="+username+"&key="+localStorage.getItem("user-key");
    $.getJSON(url, function(data, status){
        // console.log(1, "getUserList");
    }).done(function(data){
        // console.log(2, data);
        if(data.list.length!=0){ //Si l'utilisateur existe
            addListToSearchBar(data.list); //On cherche tous les users qui ont cette valeur dans leur username
        }else{
            console.log("No user");
        }
    });
}

/**
 * Fonction qui va permettre à l'utilisateur connecté de follow une autre personne
 * @param from_key key de la personne connecté
 * @param to_id id de la personne à follow
 * @returns {any}
 */
function follow(from_key, to_id){
    var url = "http://localhost:8080/Twister/user/follow?key="+from_key+"&followedId="+to_id;
    $.getJSON(url, function(data, status){
        // console.log(1, "getUserList");
    }).done(function(data){
        // console.log(2, data);
        if(data.code==200){ //Si c'est bon
            alert("Vous suivez désormais "+current_user.username);
            showUnfollow();
            getListFollowed();
        }else{ //Sinon
            alert("Vous suivez déjà cette personne.");
        }
    });
}

/**
 * Fonction qui va unfollow une personne qui a été follow
 * @param from_key key de la personne connecté
 * @param to_id id de la personne à unfollow
 * @returns {any}
 */
function unfollow(from_key, to_id){
    var url = "http://localhost:8080/Twister/user/unfollow?key="+from_key+"&followedId="+to_id;
    $.getJSON(url, function(data, status){
        console.log(1, "unfollow");
    }).done(function(data){
        // console.log(2, data);
        if(data.code==200){
            alert("Vous ne suivez plus cette personne");
            showFollow();
            getListFollowed()
        }else{
            alert("Vous ne pouvez pas faire ça.");
        }
    });
}

/**
 * Fonction qui permet de récupérer la liste des personne qu'on follow
 */
function getListFollowed(){
    var url = "http://localhost:8080/Twister/user/listFollowed?id="+localStorage.getItem("user_id")+"&key="+localStorage.getItem("user-key");
    // console.log(url)
    $.getJSON(url, function(data, status){
        console.log(1, "getListFollowed");
    }).done(function(data){
        console.log(2, data);
        if(data.list.length!=0){
            // console.log("test");
            getSweet(data.list, true);
        }else{
            getSweet([], true);
            console.log("error or empty list")
        }
        list_follow=data.list;
        setListFollowed();
        setListenerTofollow();
    });
}

/**
 * Fonction qui va ajouter un sweet
 * @param sweet
 */
function addSweet(sweet){
    var key = localStorage.getItem("user-key");
    var url = "http://localhost:8080/Twister/sweet?key="+key+"&sweet="+sweet;
    $.getJSON(url, function(data, status){
        // console.log(1, "addSweet");
    }).done(function(data){
        // console.log(data);
        if(data.code==200){
            $('#new-message').val("");
            getSweet(list_follow)
        }
    });
}

/**
 * Récupère les sweet selon l'id d'un utilisateur
 * @param id
 */
function getSweetById(id){
    var url = "http://localhost:8080/Twister/sweet/getById?id="+id+"&key="+localStorage.getItem("user-key");
    $.getJSON(url, function(data, status){
        // console.log(1, "getSweetById");
    }).done(function(data){
        // console.log(2, data);
        if(data.code==200){
            // console.log(3, data.list);
            // $('#new-message').val("");
            fillSweet(data.list);
        }
    });
}

/**
 * Fonction qui récupère le profil d'une personne selon son id
 * @param id Id du profil à récupérer
 * @returns {any}
 */
function getProfileById(id){
    var url = "http://localhost:8080/Twister/user/getProfileById?id="+id+"&key="+localStorage.getItem('user-key');

    var req = new XMLHttpRequest();
    var response = null;
    req.open('GET', url, false);
    req.send(null);
    if (req.status === 200) {
        // console.log("Réponse reçue:", JSON.parse(req.responseText));
        response=JSON.parse(req.responseText);
    } else {
        // console.log("Status de la réponse: %d (%s)", req.status, req.statusText, req.responseText);
        response=JSON.parse(req.statusText);
    }
    // console.log(response)
    return response;
}

/**
 * Fonction pour récupérer tous les sweets de l'utilisateur et de ces follow
 * @param list liste des followed
 * @param forinit boolean if it's for initialisation
 */
function getSweet(list, forinit){
    var url = "http://localhost:8080/Twister/sweet/getSweet?key="+localStorage.getItem("user-key")+"&";
    var i = 0;
    // console.log(1, list)
    list.forEach(function(e){
        // console.log(e);
        url=url+"user_"+i+"="+e.followed_id+"&";
        i++;
    })
    while(localStorage.getItem("user_id")==null){
        console.log("wait id")
    }
    url=url+"user_"+i+"="+localStorage.getItem("user_id");
    console.log(url);
    $.getJSON(url, function(data, status){
        console.log(1, "getSweet");
    }).done(function(data){
        // console.log(2, data);
        if(data.code==200){
            default_list_comment=data.list;
            // console.log(3, data.list);
            max_val_display_sweet=10; // On remet la variable à 10
            if (forinit){
                fillSweet(data.list, true); //On appelle la fonction qui va afficher les sweets sur la page main
            }else{
                addSweetToBoard(data.list);
            }
            // $('#new-message').val("");
        }
    });
}

function addComment(sweet_id, comment){
    var post_url = "http://localhost:8080/Twister/sweet/addComment" //get form action url
    var form_data = "key=" + localStorage.getItem("user-key") + "&sweetId=" + sweet_id + "&commentMessage=" + comment;
    $.post( post_url, form_data, function( response ) {
        // console.log(response)
        if (response.code == 200) {
            // console.log(3, data.list);
            // $('#new-message').val("");
            date=new Date();
            var text =
                $('<div class="commentaire-item-top">\n' +
                    '   <div class="list-item-top-span">\n' +
                    '       <span class="user-message">'+localStorage.getItem('user-username')+'</span>\n' +
                    '   </div>\n' +
                    '   <div class="list-item-top-span">\n' +
                    '       <span class="date-message">'+date.getHours()+':'+date.getMinutes()+' '+date.toLocaleDateString()+'</span>\n' +
                    '   </div>\n' +
                    '</div>\n' +
                    '<div class="commentaire-item-bottom">\n' +
                    '    <p class="contenu-message">'+comment+'</p>\n' +
                    '</div>\n');
            $('#'+sweet_id).find('.commentaire-item').append(text);
        }
    });
}