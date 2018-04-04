$(document).ready(function () {

    /**
     * Lorsque l'utilisateur écrit dans le champ d'ajout d'un nouveau commentaire
     */
    $('.add_comment').keyup(function () {
        // console.log($(this).val());
        var new_comment = $(this).val();
        if (new_comment.length != 0) {
            $(this).parent().find("input").prop("disabled", false);
        } else {
            $(this).parent().find("input").prop("disabled", true);
        }
    });
});

var list_comment = new Array(0); //Variable qui contiendra les commentaires récupérer lors de l'appel du servlet
/**
 * Fonction qui va ajouter les sweets dans la page main selon les sweets dans la liste
 * @param list liste des sweet reçu de la BDD
 */
function fillSweet(list) {
    list_comment = list;
    var user = "";
    var id = "";
    $('.list-item').empty();
    // console.log("test1", list.length);
    list.forEach(function (e) {
        // console.log(e._id.$oid);
        id = e._id.$oid;
        var text = $('<div class="sweets" id="' + id + '"><div class="list-item-top">\n' +
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
            '    </div>\n' +
            '</div>\n' +
            '<div>\n' +
            '    <span>Ajouter un commentaire</span>\n' +
            '    <div class="div_new_comment">\n' +
            '        <textarea rows="1" placeholder="Nouveau commentaire" class="add_comment" value="">\n' +
            '        </textarea>\n' +
            '        <input type="submit" value="Ajouter" class="add_comment_button">\n' +
            '    </div>\n' +
            '</div></div>' +
            '<br>');
        //Condition pour éviter d'appeler l'API pour rien
        if (user.id != "") {
            if (user.id != e.userId) {
                // console.log("test");
                user = getProfileById(e.userId) //Récupération du profil par rapport à l'id
            }
        }
        text.find('.contenu-message').first().text(e.sweet); //Ajout du contenu du sweet
        text.find('.user-message').first().text(user.username); //Ajout du username du sweet
        // console.log("test")
        $('.list-item').append(text)
    })
    setComment();
}

/**
 * Fonction pour ajouter les commentaires dans chaque sweet
 */
function setComment() {
    setListenerToSweet(); //Ajout des listener sur les sweets
    // console.log($('.sweets'));
    var i = 0;
    var user = "test";
    list_comment.forEach(function (e) {
        e['comments'].forEach(function (r) {
            var text =
                $('<div class="commentaire-item-top">\n' +
                    '   <div class="list-item-top-span">\n' +
                    '       <span class="user-message"></span>\n' +
                    '   </div>\n' +
                    '   <div class="list-item-top-span">\n' +
                    '       <span class="date-message"></span>\n' +
                    '   </div>\n' +
                    '</div>\n' +
                    '<div class="commentaire-item-bottom">\n' +
                    '    <p class="contenu-message"></p>\n' +
                    '</div>\n');
            if (user.id != "") {
                if (user.id != e.userId) {
                    console.log(e);
                    user = getProfileById(e.userId) //Récupération du profil par rapport à l'id
                }
            }
            text.find('.user-message').text(user.username);
            text.find('.date-message').text(r.date.$date);
            text.find('.contenu-message').text(r.comment);
            // console.log(text)
            $('#' + e._id.$oid).find('.commentaire-item').append(text);
        })
        i++;
    });
    $('.commentaire-item').hide();
}