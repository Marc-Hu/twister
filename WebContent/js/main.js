$(document).ready(function() {

    /**
     * Lorsque l'utilisateur écrit dans le champ d'ajout d'un nouveau commentaire
     */
    $('.add_comment').keyup(function(){
        // console.log($(this).val());
        var new_comment=$(this).val();
        if(new_comment.length!=0){
            $(this).parent().find("input").prop("disabled", false);
        }else{
            $(this).parent().find("input").prop("disabled", true);
        }
    });

    /**
     * Lorsque l'utilisateur appuie sur le bouton pour ajouter un nouveau commentaire
     */
    $(".add_comment_button").click(function(){
        console.log("Ajout d'un nouveau commentaire");
    });
});

/**
 * Fonction qui va ajouter les sweets dans la page main selon les sweets dans la liste
 * @param list liste des sweet reçu de la BDD
 */
function fillSweet(list){
    var user = "";
    $('.list-item').empty();
    list.forEach(function(e){
        console.log(e);
        var text = $('<div class="list-item-top">\n' +
            '    <div class="list-item-top-span">\n' +
            '        <span class="user-message"></span>\n' +
            '    </div>\n' +
            '    <div class="list-item-top-span">\n' +
            '        <span class="date-message"></span>\n' +
            '    </div>\n' +
            '</div>\n' +
            '<div class="list-item-mid">\n' +
            '    <p class="contenu-message"></p>\n' +
            '</div>\n' +
            '<div>\n' +
            '    <span class="show-com" >+ Commentaires</span>\n' +
            '    <div class="commentaire-item">\n' +
            '        <div class="commentaire-item-top">\n' +
            '            <div class="list-item-top-span">\n' +
            '                <span class="user-message"></span>\n' +
            '            </div>\n' +
            '            <div class="list-item-top-span">\n' +
            '                <span class="date-message"></span>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '        <div class="commentaire-item-bottom">\n' +
            '            <p class="contenu-message"></p>\n' +
            '        </div>\n' +
            '    </div>\n' +
            '</div>\n' +
            '<div>\n' +
            '    <span>Ajouter un commentaire</span>\n' +
            '    <div class="div_new_comment">\n' +
            '        <textarea rows="1" placeholder="Nouveau commentaire" class="add_comment" value="">\n' +
            '        </textarea>\n' +
            '        <input type="submit" value="Ajouter" class="add_comment_button" disabled>\n' +
            '    </div>\n' +
            '</div>' +
            '<br>');
        //Condition pour éviter d'appeler l'API pour rien
        if(user.id!=""){
            if(user.id!=e.userId){
                // console.log("test");
                user = getProfileById(e.userId) //Récupération du profil par rapport à l'id
            }
        }
        text.find('.contenu-message').first().text(e.sweet); //Ajout du contenu du sweet
        text.find('.user-message').first().text(user.username); //Ajout du username du sweet
        // console.log("test")
        $('.list-item').append(text)
    })
}