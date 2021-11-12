aa=false;
function  test() {
    if (document.registerform.farenName.value.length > 6 || document.registerform.farenName.value.length == 0) {
        $("#farenNameError").show();
        return false;
    } else {
        aa=true;
        $("#farenNameError").hide()
        $("#farenXing").hide();
    }
}
function test1() {
    var res =/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    var number = document.registerform.farenidNumber.value;
    if (number.length<=0|| res.test(number)==false) {
        $("#farenIDError").show();
        aa=false;
        return false;
    } else {
        aa=true;
        $("#farenIDError").hide()
        $("#farenXing1").hide();
    }
}
function test2() {
    if (document.registerform.creditCode.value.length<=10){
        $("#creditCodeError").show();
        aa=false;
        return false;
    }else{
        aa=true;

        $("#creditCodeError").hide();
        $("#farenXing2").hide();
    }
    
}
function  test3() {
    if (document.registerform.companyName.value.length<=0){
            $("#companyNameError").show();
        aa=false;
            return false;

    }else{
        aa=true;

        $("#companyNameError").hide();
        $("#farenXing3").hide();
    }
    
}
function  test4() {
    if(document.registerform.password.value.length<=0){
        $("#passwordError").show();
        aa=false;
        return false;

    }else {
        aa=true;

        $("#passwordError").hide();
        $("#farenXing4").hide()
    }
}
function test5() {

    var password = document.registerform.password.value;
    var password1 = document.registerform.password1.value;
    if(password!=password1){
        $("#password1Error").show();
        aa=false;
        return false;

    }else {
        aa=true;
        $("#password1Error").hide();
        $("#farenXing5").hide()
    }

}
function test6() {
    var phone = document.registerform.phoneNumber.value;
    var  tel = /^[1][3,4,5,7,8][0-9]{9}$/;
    if(phone.length<=0||tel.test(phone)==false){
        $("#phoneNumberError").show();
        aa=false;
        return false;


    }else {
        aa=true;
        $("#phoneNumberError").hide();
        $("#farenXing6").hide();
    }
    
}
function  tijiao() {
    debugger
    if(aa==false||document.registerform.farenidNumber.value.length<=0||document.registerform.creditCode.value.length<=0
        ||document.registerform.password1.value.length<=0|| document.registerform.password.value.length<=0||document.registerform.companyName.value.length<=0
        ||document.registerform.farenName.value.length<=0||document.registerform.phoneNumber.value.length<=0){
        return false;
    }else {
        return true;
    }
    
}
