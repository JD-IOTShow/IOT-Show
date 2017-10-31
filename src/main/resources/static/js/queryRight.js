function createActivityBar() {
    var arryX = [];
    var arryY = [];

    function getNewData() {
        var value;
        $.post("commonAppRankCntByDay",function(data){
                arryX = [];
                arryY = [];
                value = data.result.object;
                $.each(value,function (index,val) {
                    arryX.push(val.APP_CALL_CNT);
                    arryY.push(val.VC_APP_NAME);
                });
            activityChart.setOption({
                series: [{
                    "name": "总计",
                    "data": arryX
                }, {
                    name: "应用使用率",
                    "show": false,
                    "data": arryX
                }],
                yAxis: [{
                    "data": arryY
                }]
            });
            },"json"//设置了获取数据的类型，所以得到的数据格式为json类型的
        );
    }

    /*function getActivityData() {
        var appusage_data = [];
        var appusage = ["智能家具", "雪亮工程", "LNG点供", "脚手架", "售电"];
        while (appusage_data.length < 5) {
            var i = appusage_data.length;
            var k = Math.round(Math.random() * 10000);
            //if(!json[k]){
            //  json[k]=true;
            appusage_data.push({
                name: appusage[i],
                value: k
            });
            //}
        }
        return appusage_data;

    }*/

    var option = {
        "tooltip": {
            "trigger": "axis",
            "axisPointer": { // 坐标轴指示器，坐标轴触发有效
                "type": "shadow" // 默认为直线，可选为："line" | "shadow"
            }
        },
        "grid": {
            "left": "10%",
            "right": "0%",
            "top": "6%",
            "bottom": "5%",
            "containLabel": true
        },
        "animationDurationUpdate": 1200,
        "yAxis": [{
            "type": "category",
            "data": [],
            "axisLine": {
                "show": false
            },
            "axisTick": {
                "show": false,
                "alignWithLabel": true
            },
            "axisLabel": {
                "textStyle": {
                    "color": "#979797",
                    "fontSize": 16 * winTimes
                }
            }
        }],
        "xAxis": [{
            "type": "value",
            "axisLine": {
                "show": false
            },
            "axisTick": {
                "show": false
            },
            "axisLabel": {
                "show": false
            },
            "splitLine": {
                "show": false
            }
        }],

        "series": [{
            "name": "总计",
            "type": 'bar',
            "itemStyle": {
                "normal": {
                    "color": '#494949',
                    "opacity": .4,
                    "barBorderRadius": 4 * winTimes,
                }
            },
            "silent": true,
            "barWidth": 14 * winTimes,
            "barGap": '-100%', // Make series be overlap
            "data": []
        }, {
            "name": "应用使用率",
            "show": false,
            "type": "bar",
            "data": [],
            "barWidth": 14 * winTimes,
            "z": 10,
            "itemStyle": {
                "normal": {
                    "color": new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
                        "offset": 0,
                        "color": "#107cbe" // 0% 处的颜色
                    }, {
                        "offset": 1,
                        "color": "#4fc1c5" // 100% 处的颜色
                    }], false),
                    "barBorderRadius": 4 * winTimes,

                }
            }
        }]
    };
    getNewData();
    /*activityChart.setOption({
        series: [{
            "name": "总计",
            "data": arryX
        }, {
            name: "应用使用率",
            "show": false,
            "data": arryX
        }],
        yAxis: [{
            "data": arryY
        }]
    });*/
    activityChart.setOption(option);
    setInterval(function() {
        //每秒刷新一次跃度排行
        getNewData();
        //getActivityData();
    }, 300000);

}

function createCallTrendLine() {
    var arryX = [];
    var arryY = [];
    var biggest = 0;
    function getNewData() {
        var value;
        $.post("commonAblitiesCntByDay",function(data){
                arryX = [];
                arryY = [];
                value = data.result.object;
                $.each(value,function (index,val) {
                    if (biggest < parseInt(val.ABILITY_CALL_CNT)){
                        biggest = val.ABILITY_CALL_CNT;
                    }
                    arryX.push(val.DATE_CD);
                    arryY.push(val.ABILITY_CALL_CNT);
                });
                console.log(biggest);
            callTrendChart.setOption({
                series: [{
                    data: arryY
                }],
                xAxis:[{
                    data: arryX
                }]
            });
            },"json"//设置了获取数据的类型，所以得到的数据格式为json类型的
        );
    }

    /*function getActivityData() {
        var appusage = ['48', '43', '41', '40', '24', '53'];
        var k = Math.round(Math.random() * 100);
        appusage.push(k);
        return appusage;
    }*/

    var option = {
        backgroundColor: 'transparent',
        color: ['#68d9cc'],
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            left: "2%",
            right: "0%",
            top: "10%",
            bottom: "10%",
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: true,
            // offset: -5,
            axisTick: { show: false },
            splitNumber: 6,
            axisLine: { //网格线
                lineStyle: {
                    color: ['#41484f'],
                    opacity: .6
                }
            },
            axisLabel: {
                fontSize: 10
            },
            data: []
        },
        yAxis: {
            min: 0,
            max: 50000,
            interval: 10000,
            // offset: -5,
            axisTick: { show: false },
            axisLine: {
                lineStyle: {
                    color: ['#414850'],
                    opacity: .6
                }
            },
            axisLabel: {
                color: '#414850',
                fontSize: 12 * winTimes,
            },
            splitLine: { //网格线
                show: false
            }
        },
        series: [{
            name: '能力调用量每日趋势',
            type: 'line',
            smooth: true,
            symbolSize: 8 * winTimes,
            data: [],
            label: {
                normal: {
                    show: false,
                    position: 'top' //值显示
                }
            }
        } ]
    };
    getNewData();
    callTrendChart.setOption(option);
    setInterval(function() {
        //每秒刷新一次跃度排行
        getNewData();

        //getActivityData();
    }, 300000);

}
//设备数据量
function createDataTrendLine() {
    var arryX = [];
    var arryY = [];
    function getNewData() {
        var value;
        $.post("commonSgnlAllCntByDay",function(data){
            console.log(data)
                arryX = [];
                arryY = [];
                value = data.result.object;
                $.each(value,function (index,val) {
                    arryX.push(val.DATE_CD);
                    arryY.push(val.SGNL_CNT);
                });
            dataTrendChart.setOption({
                series: [{
                    data: arryY
                }],
                xAxis:[{
                    data:arryX
                }]

            });
            },"json"//设置了获取数据的类型，所以得到的数据格式为json类型的
        );
    }

    var option = {
        color: ['#979797'],
        tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '2%',
            right: '0%',
            bottom: '5%',
            top: '6%',
            containLabel: true
        },
        xAxis: [{
            type: 'category',
            show: true,
            // offset: -5,
            data: [],
            axisTick: { show: false },
            axisLine: { //坐标轴线
                lineStyle: {
                    color: ['#41484f']
                }
            },
        }],
        yAxis: [{
            type: 'value',
            axisTick: { show: false },
            // offset: -5,
            axisLine: { //坐标轴线
                lineStyle: {
                    color: ['#414850'],
                    opacity: .6
                }
            },
            axisLabel: {
                color: '#414850',
                fontSize: 12 * winTimes,
            },
            splitLine: {
                show: false
            }
        }],
        series: [{
            name: '设备数据量每日趋势',
            type: 'bar',
            barWidth: '30%',
            data: [],
            itemStyle: {
                normal: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0,
                        color: "#0ca7d3" // 0% 处的颜色
                    }, {
                        offset: 1,
                        color: "#77e9c9" // 100% 处的颜色
                    }], false),
                    barBorderRadius: 4 * winTimes,
                }
            }
        }]
    };
    getNewData();
    dataTrendChart.setOption(option);
    setInterval(function() {
        //每秒刷新一次跃度排行
        getNewData();

        //getActivityData();
    }, 300000);

}