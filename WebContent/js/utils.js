password_validation = function () {
    if ($('#password').val() === $('#c_password').val()) {
        $('#message').html('Password Confirmed').css('color', 'green');
        return true;
    } else{
        $('#message').html('Password Not Confirmed').css('color', 'red');
        return false;
    }
};

/**check password validation on register from */

$('#password, #c_password').on('keyup', password_validation);

/**register login event */
$("#login_form").submit(function(event){
    event.preventDefault(); //prevent default action
    var result = login($(this));
});


$("#register_form").submit(function(event){
    event.preventDefault(); //prevent default action
    var result = register();
});

animate_forms = function () {
    $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
};


$('.message a').click(animate_forms);
