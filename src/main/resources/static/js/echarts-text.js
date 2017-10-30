//判断是多大的屏幕
var winTimes = 1;
// var onlineChart = echarts.init(document.getElementById('online-rate'));
// var succeedChart = echarts.init(document.getElementById('succeed-rate'));
var activityChart = echarts.init(document.getElementById('activity-rate'));
var callTrendChart = echarts.init(document.getElementById('call-rate-trend'));
var dataTrendChart = echarts.init(document.getElementById('data-rate-trend'));


$(document).ready(function() {
    //生成各个图表
    // createPie();
    createActivityBar();
    createCallTrendLine();
    createDataTrendLine();

    window.addEventListener("resize", function() {
        //onlineChart.resize();
        //succeedChart.resize();
        activityChart.resize();
        callTrendChart.resize();
        dataTrendChart.resize();
    });
});

// function createPie() {
//     var percent = 0.7;
//
//
//     function getData(color) {
//         return [{
//             value: percent,
//             itemStyle: {
//                 normal: {
//                     color: color
//                 }
//             }
//         }, {
//             value: 1 - percent,
//             itemStyle: {
//                 normal: {
//                     color: 'transparent'
//                 }
//             }
//         }];
//     }
//
//     var onlineOption = {
//         backgroundColor: 'transparent',
//
//         title: {
//             text: (percent * 100) + '%',
//             x: 'center',
//             y: 'center',
//             textStyle: {
//                 color: '#b4b7bb',
//                 fontWeight: '100',
//                 fontSize: 34,
//             }
//         },
//         series: [{
//                 type: 'pie',
//                 radius: ['90%', '93.5%'], //图表内圈占整个div的比例
//                 silent: true,
//                 label: {
//                     normal: {
//                         show: false,
//                     }
//                 },
//                 data: [{
//                     value: .5,
//                     itemStyle: {
//                         normal: {
//                             color: '#adadad',
//                             opacity: .2
//                         }
//                     }
//                 }],
//
//                 animation: false
//             },
//
//             {
//                 name: 'main',
//                 type: 'pie',
//                 radius: ['97%', '99.5%'], //图表外圈占整个div的比例
//                 label: {
//                     normal: {
//                         show: false,
//                     }
//                 },
//                 animationEasingUpdate: 'cubicInOut',
//                 animationDurationUpdate: 500
//             }
//         ]
//     };
//     onlineChart.setOption(onlineOption);
//     succeedChart.setOption(onlineOption);
//
//     setInterval(function() {
//         percent = +Math.random();
//         onlineChart.setOption({
//             title: {
//                 text: (percent * 100).toFixed(0) + '%'
//             },
//             series: [{
//                 name: 'main',
//                 data: getData('#e84161')
//             }]
//         });
//     }, 1000);
//
//     setInterval(function() {
//         percent = +Math.random();
//         succeedChart.setOption({
//             title: {
//                 text: (percent * 100).toFixed(0) + '%'
//             },
//             series: [{
//                 name: 'main',
//                 data: getData('#38a7c2')
//             }]
//         });
//     }, 1000);
//}



function createActivityBar() {


    function getActivityData() {
        var appusage_data = [];
        var appusage = ["智能家具", "雪亮工程", "LNG点供", "脚手架", "售电"];
        while (appusage_data.length < 5) {
            var i = appusage_data.length;
            var k = Math.round(Math.random() * 100);
            //if(!json[k]){
            //  json[k]=true;
            appusage_data.push({
                name: appusage[i],
                value: k
            });
            //}
        }
        return appusage_data;
    }

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
            "data": ["智能家具", "雪亮工程", "LNG点供", "脚手架", "售电"],
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
            "data": [100, 100, 100, 100, 100]
        }, {
            "name": "应用使用率",
            "type": "bar",
            "data": getActivityData(),
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
    activityChart.setOption(option);
    setInterval(function() {
        //每秒刷新一次跃度排行
        activityChart.setOption({
            series: [{
                "name": "总计",
                "data": [100, 100, 100, 100, 100]
            }, {
                name: "应用使用率",
                "data": getActivityData(),
            }]
        });
        getActivityData();
    }, 1000);
}

function createCallTrendLine() {

    function getActivityData() {
        var appusage = ['48', '43', '41', '40', '24', '53'];
        var k = Math.round(Math.random() * 100);
        appusage.push(k);
        return appusage;
    }

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
            data: [{
                value: '20171001',
                // 突出周一
                textStyle: {
                    fontSize: 8 * winTimes
                }
            }, {
                value: '20171002',
                // 突出周一
                textStyle: {
                    fontSize: 8 * winTimes
                }
            }, {
                value: '20171003',
                // 突出周一
                textStyle: {
                    fontSize: 8 * winTimes
                }
            }, {
                value: '20171004',
                // 突出周一
                textStyle: {
                    fontSize: 8 * winTimes
                }
            }, {
                value: '20171005',
                // 突出周一
                textStyle: {
                    fontSize: 8 * winTimes
                }
            }, {
                value: '20171006',
                // 突出周一
                textStyle: {
                    fontSize: 8 * winTimes
                }
            }, {
                value: '20171007',
                // 突出周一
                textStyle: {
                    fontSize: 8 * winTimes
                }
            }]
        },
        yAxis: {
            min: 0,
            max: 100,
            interval: 20,
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
            symbolSize: 8 ** winTimes,
            data: ['48', '43', '41', '40', '24', '53', '47'],
            label: {
                normal: {
                    show: false,
                    position: 'top' //值显示
                }
            }
        }, ]
    };
    setInterval(function() {
        //每秒刷新一次跃度排行
        callTrendChart.setOption({
            series: [{
                data: getActivityData()
            }]
        });
        getActivityData();
    }, 1000);
    callTrendChart.setOption(option);
}

function createDataTrendLine() {
    function getActivityData() {
        var appusage = [10, 52, 60, 34, 90, 30, 20, 45, 88, 16, 54, 55, 66, 68, 48, 36, 25, 75, 48];
        var k = Math.round(Math.random() * 100);
        appusage.push(k);
        return appusage;
    }
    function getNewData() {
        var value;
        $.post("127.0.0.1:8000",function(data){

            },
            "json");
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
            data: [10, 52, 60, 34, 90, 30, 20, 45, 88, 16, 54, 55, 66, 68, 48, 36, 25, 75, 48, 50],
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
            data: [10, 52, 60, 34, 90, 30, 20, 45, 88, 16, 54, 55, 66, 68, 48, 36, 25, 75, 48, 50],
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
    setInterval(function() {
        //每秒刷新一次跃度排行
        dataTrendChart.setOption({
            series: [{
                data: getActivityData()
            }],
            xAxis:[{
                data:[10, 52, 60, 34, 90, 30, 20, 45, 88, 16, 54, 55, 66, 68, 48, 36, 25, 75, 48, 50]
            }]

        });
        getActivityData();
    }, 10000);
    dataTrendChart.setOption(option);
}

//左边产品总数定时变换
function changeUlnum(){
    var arr = [];
    while (arr.length < 4) {
        var i = arr.length;
        var k = Math.round(Math.random() * 100);
        //if(!json[k]){
        //  json[k]=true;
        arr.push(k);
        //}
    }
    for(var i in arr){
        $('.data-list').find('li').eq(i).find('.num').text(arr[i]);
    }
}
// setInterval(function() {
//         changeUlnum();
//     }, 1000);