var arr = new Array();
arr.push('000/000/000');
var turnover1 = $('.dowebok').flipTimer({
    direction: 'up',
    date: arr
});
$(document).ready(function(){
    setTimeout(function(){
        $.ajax({url:"commonAbilityCallCnt",success:function(result){
            // alert($.parseJSON(result).result.object[0].ABILITY_CALL_CNT);
            arr.push('222/333/111');
            // alert(JSON.stringify(turnover1));
            turnover1.data('flipTimer').calculateDate();
        }});
    },1000);
});