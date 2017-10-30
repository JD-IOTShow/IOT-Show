var arr1 = new Array();
var arr2 = new Array();
arr1.push('000/000/000');
arr2.push('000/000/000');
var turnover1 = $('.dowebok').flipTimer({
    direction: 'up',
    date: arr1
});
var turnover2 = $('.dowebok2').flipTimer({
    direction: 'up',
    date: arr2
});

$(document).ready(function(){
    setInterval(function(){
        $.ajax({url:"queryPlatStatusAll",success:function(result){
            var str = $.parseJSON(result).result.object[0].sgnlCnt;
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
            arr1.push(array.join(""));
            //arr.shift();
            // alert(JSON.stringify(turnover1));
            turnover1.data('flipTimer').calculateDate();
        }});
    },1000);

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
            arr2.push(array.join(""));
            //arr.shift();
            // alert(JSON.stringify(turnover1));
            turnover2.data('flipTimer').calculateDate();
        }});
    },1000);

    setInterval(function(){
        $.ajax({url:"commonCount",success:function(result){
            var commonProductCnt = $.parseJSON(result).result.object[0].commonProductCnt;
            var commonDeviceCnt = $.parseJSON(result).result.object[0].commonDeviceCnt;
            var commonAppCnt = $.parseJSON(result).result.object[0].commonAppCnt;
            var commonAbilityCnt = $.parseJSON(result).result.object[0].commonAbilityCnt;

            $('.data-list').find('li').eq(0).find('.num').text(commonProductCnt);
            $('.data-list').find('li').eq(1).find('.num').text(commonDeviceCnt);
            $('.data-list').find('li').eq(2).find('.num').text(commonAppCnt);
            $('.data-list').find('li').eq(3).find('.num').text(commonAbilityCnt);
        }});
    },1000);
});



var onlineChart = echarts.init(document.getElementById('online-rate'));
var succeedChart = echarts.init(document.getElementById('succeed-rate'));
$(document).ready(function() {
    //生成各个图表
    createPie();
    window.addEventListener("resize", function() {
        onlineChart.resize();
    });
});
function createPie() {
    function getData(color,percent) {
        return [
        {
            value: percent,
            itemStyle: {
                normal: {
                    color: color
                }
            }
        },
        {
            value: 1 - percent,
            itemStyle: {
                normal: {
                    color: 'transparent'
                }
            }
        }];
    }

    var percent = 0;
    var onlineOption = {
        backgroundColor: 'transparent',
        title: {
            text: (percent * 100) + '%',
            x: 'center',
            y: 'center',
            textStyle: {
                color: '#b4b7bb',
                fontWeight: '100',
                fontSize: 34,
            }
        },
        series: [
            {
                type: 'pie',
                radius: ['90%', '93.5%'], //图表内圈占整个div的比例
                silent: true,
                label: {
                    normal: {
                        show: false,
                    }
                },
                data: [{
                    value: .5,
                    itemStyle: {
                        normal: {
                            color: '#adadad',
                            opacity: .2
                        }
                    }
                }],
                animation: false
            },
            {
                name: 'main',
                type: 'pie',
                radius: ['97%', '99.5%'], //图表外圈占整个div的比例
                label: {
                    normal: {
                        show: false,
                    }
                },
                animationEasingUpdate: 'cubicInOut',
                animationDurationUpdate: 500
            }
        ]
    };
    onlineChart.setOption(onlineOption);
    succeedChart.setOption(onlineOption);

    setInterval(function(){
        $.ajax({url:"onlineRate",success:function(result){
            var percent = $.parseJSON(result).result.object[0].onlineRate;
            onlineChart.setOption({
                title: {
                    text: (percent * 100).toFixed(0) + '%'
                },
                series: [{
                    name: 'main',
                    data: getData('#e84161',percent)
                }]
            });
        }});
    },1000);

    setInterval(function() {
        $.ajax({url:"successRate",success:function(result){
            var percent = $.parseJSON(result).result.object[0].successRate;
            succeedChart.setOption({
                title: {
                    text: (percent * 100).toFixed(0) + '%'
                },
                series: [{
                    name: 'main',
                    data: getData('#38a7c2',percent)
                }]
            });
        }});
    }, 1000);
}
