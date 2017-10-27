$(document).ready(function(){
    $("#button1").click(function(){
        $.ajax({url:"commonAbilityCallCnt",success:function(result){
            $("#div1").html(result);
        }});
    });
});