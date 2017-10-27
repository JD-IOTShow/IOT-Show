var arr = new Array();
arr.push('000/000/000');
var turnover1 = $('.dowebok').flipTimer({
    direction: 'up',
    date: arr
});
$(document).ready(function(){
    setInterval(function(){
        $.ajax({url:"commonAbilityCallCnt",success:function(result){
            var str = $.parseJSON(result).result.object[0].ABILITY_CALL_CNT;
            var array = [];
            var i=str.length-1;
            var j = 9;
            while(j>0){
                var char;
                if(i>=0){
                    char = str.charAt(i);
                    array.unshift(char);
                    if(array.length==3||array.length==7){
                        array.unshift('/');
                    }
                }else{
                    array.unshift('0');
                }
                i--;
                j--;
            }
            //alert(array.join(""));
            //arr.shift();
            arr.push(array.join(""));
            //arr.shift();
            // alert(JSON.stringify(turnover1));
            turnover1.data('flipTimer').calculateDate();
        }});
    },1000);
});