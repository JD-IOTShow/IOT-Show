var timer_array_transport_map = [];
var timer_array_heat_map = [];
var heatMapResult;
var transportMapResult;
$(function() {
    //生成两个地图
    handleHeatMap();
    $('.tab-header').delegate('a', 'click', function() {
        //$(this).addClass('active').siblings().removeClass('active');
        $('#mapRmodynamic').addClass('active');
        $('#sonMap').removeClass('active');
        if ($(this).hasClass('rmodynamic')) {
            if(heatMapResult) {
                var dataArray = $.parseJSON(heatMapResult).result.object;
                changeMap(dataArray);
                $('.rmodynamic').addClass('active');
                $('.transmission').removeClass('active');
            }
            handleHeatMap();
        } else {
            if(transportMapResult) {
                var dataArray = $.parseJSON(transportMapResult).result.object;
                changeTransMap(dataArray);
                $('.rmodynamic').removeClass('active');
                $('.transmission').addClass('active');
            }
            handleTransportMap();
        }
    });

    //地图轮播
    var timer_array = [];
    var timer = setInterval(function() {
        var ind = $('.tab-header .active').index();
        // console.log(ind);
        if (ind == 0) {
            handleTransportMap();
        } else {
            handleHeatMap();
        }
        //$('.tab-header .active').removeClass('active').siblings().addClass('active');
    }, 60000);
    timer_array.push(timer);
    //hover事件完成悬停
    $('.tab-modual').hover(function() {
        while(timer_array.length>0){
            clearInterval(timer_array.pop());
        }
        //clearInterval(timer);
    }, function() {
        timer = setInterval(function() {
            var ind = $('.tab-header .active').index();
            // console.log(ind);
            if (ind == 0) {
                handleTransportMap();
            } else {
                handleHeatMap();
            }
            //$('.tab-header .active').removeClass('active').siblings().addClass('active');
        }, 20000);
        timer_array.push(timer);
    })

})

function handleHeatMap() {
    $.ajax({url:"heatMap",success:function(result){
        var dataArray = $.parseJSON(result).result.object;
        changeMap(dataArray);
        $('.rmodynamic').addClass('active');
        $('.transmission').removeClass('active');
        heatMapResult = result;
    }});
    while(timer_array_transport_map.length>0){
        clearInterval(timer_array_transport_map.pop());
    }
    var timer = setInterval(function () {
        $.ajax({url:"heatMap",success:function(result){
            var dataArray = $.parseJSON(result).result.object;
            changeMap(dataArray);
            $('.rmodynamic').addClass('active');
            $('.transmission').removeClass('active');
        }});
    },20000);
    timer_array_heat_map.push(timer);
}

// $(document).ready(function(){
//     setInterval(handleHeatMap,5000);
// });

var mapChart = echarts.init(document.getElementById('mapRmodynamic'));
//设备热力图
function changeMap(dataArray) {
    var convertData = function() {
        dataArray.sort(function(a, b) {
            return b.deviceCount - a.deviceCount;
        });
        var res = [];
        for (var i = 0; i < dataArray.length; i++) {
            res.push({
                name: dataArray[i].cityName,
                value: dataArray[i].coordinate.concat(dataArray[i].deviceCount)
            });
        }
        return res;
    };

    var mapOption = {
        tooltip: {
            trigger: 'item',
            formatter: function (params) {
                console.log(JSON.stringify(params));
                return params.data.name
                    + '<div style="border-bottom: 1px solid rgba(255,255,255,.3); '
                    + 'font-size: 14px;padding-bottom: 7px;margin-bottom: 7px"></div>'
                    + '设备数：<font style="color:#fe9601">'+ params.data.value[2] + '</font> (个)';
            },
        },
        visualMap: {
            min: 0,
            //max: convertData()[0].value[2]?convertData()[0].value[2]:1000,
            max:1000,
            calculable: true,
            inRange: {
                color: ['#0993ce', '#6d6f9f', '#eb355e'],
                symbolSize: [5, 15]
            },
            controller: {
                inRange: {
                    symbolSize: [10, 10]
                }
            },
            textStyle: {
                color: '#fff'
            }
        },
        geo: {
            map: 'china',
            label: {
                emphasis: {
                    show: false
                }
            },
            roam: false,
            itemStyle: {
                normal: {
                    areaColor: 'transparent',
                    borderColor: '#63e0e3'
                },
                emphasis: {
                    areaColor: '#061c2f'
                }
            },
            left: 0,
            right: 0,
            layoutCenter: ['100%', '100%'],
            // 如果宽高比大于 1 则宽度为 100，如果小于 1 则高度为 100，保证了不超过 100x100 的区域
        },
        series: [
            {
                type: 'lines',
                zlevel: 2,
                show: false
            },
            {
                name: 'data',
                type: 'scatter',
                coordinateSystem: 'geo',
                data: convertData(),
                symbolSize: function(val) {
                    return val[2] / 1000;
                },
                label: {
                    normal: {
                        formatter: function (params) {
                            //console.log(JSON.stringify(params));
                            return "设备数："+params.data.value[2]+"(个)";
                        },
                        position: 'right',
                        show: false,
                        textStyle : {
                            color: '#fe9601',
                            decoration: 'none',
                            fontFamily: 'Verdana, sans-serif',
                            fontSize: 18,
                            //fontStyle: 'italic',
                            fontWeight: 'bold'
                        }
                    },
                    emphasis: {
                        show: false
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#ddb926'
                    }
                }
            },
            {
                name: 'Top 3',
                type: 'effectScatter',
                coordinateSystem: 'geo',
                data: convertData().slice(0, 3),
                symbolSize: function(val) {
                    return val[2] / 1000;
                },
                showEffectOn: 'render',
                rippleEffect: {
                    brushType: 'stroke'
                },
                hoverAnimation: true,
                label: {
                    normal: {
                        formatter: function (params) {
                            //console.log(JSON.stringify(params));
                            return params.data.value[2];
                        },
                        position: 'right',
                        show: false,
                        // textStyle : {
                        //     color: 'yellow',
                        //     decoration: 'none',
                        //     fontFamily: 'Verdana, sans-serif',
                        //     fontSize: 25,
                        //     fontStyle: 'italic',
                        //     fontWeight: 'bold'
                        // }
                    },
                    emphasis: {
                        show: false
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#eb355e',
                        shadowBlur: 10,
                        shadowColor: '#333'
                    }
                },
                zlevel: 1
            }
        ]
    };
    mapChart.setOption(mapOption, true);
    window.addEventListener("resize", function() {
        mapChart.resize();
    });
}

var Province = "";
mapChart.on('click', function(params) {
    var mapChart = echarts.init(document.getElementById('sonMap'));
    ///--console.log(params);
    $('#mapRmodynamic').removeClass('active');
    $('#sonMap').addClass('active');
    while(timer_array_heat_map.length>0){
        clearInterval(timer_array_heat_map.pop());
    }
    while(timer_array_transport_map.length>0){
        clearInterval(timer_array_transport_map.pop());
    }
    //$.ajax({url:"heatMap",success:function(result) {
        var dataArray = $.parseJSON(heatMapResult).result.object;
        var convertData = function() {
            dataArray.sort(function(a, b) {
                return b.deviceCount - a.deviceCount;
            });
            var res = [];
            for (var i = 0; i < dataArray.length; i++) {
                res.push({
                    name: dataArray[i].cityName,
                    value: dataArray[i].coordinate.concat(dataArray[i].deviceCount)
                });
            }
            return res;
        };

        Province = params.name;
        if (params.componentType == 'geo' || Province == '北京' || Province == '天津' || Province == '上海' || Province == '重庆') {
            var mapOption = {
                tooltip: {
                    trigger: 'item',
                    formatter: function (params) {
                        console.log(JSON.stringify(params));
                        return params.data.name
                            + '<div style="border-bottom: 1px solid rgba(255,255,255,.3); '
                            + 'font-size: 14px;padding-bottom: 7px;margin-bottom: 7px"></div>'
                            + '设备数：<font style="color:#fe9601">'+ params.data.value[2] + '</font> (个)';
                    },
                },
                textStyle: {
                    color: '#f2f3f5'
                },
                visualMap: {
                    min: 0,
                    max: 1000,
                    calculable: true,
                    inRange: {
                        color: ['#0993ce', '#6d6f9f', '#eb355e'],
                        symbolSize: [5, 15]
                    },
                    controller: {
                        inRange: {
                            symbolSize: [10, 10]
                        }
                    },
                    textStyle: {
                        color: '#fff'
                    }
                },
                geo: {
                    map: Province,
                    //type: 'map',
                    //mapType: Province,
                    label: {
                        emphasis: {
                            show: false
                        }
                    },
                    roam: false,
                    itemStyle: {
                        normal: {
                            areaColor: 'transparent',
                            borderColor: '#63e0e3'
                        },
                        emphasis: {
                            areaColor: '#061c2f'
                        }
                    },
                    left: 0,
                    right: 0,
                    layoutCenter: ['100%', '100%'],
                    // 如果宽高比大于 1 则宽度为 100，如果小于 1 则高度为 100，保证了不超过 100x100 的区域
                },
                series: [
                    // {
                    //     name: '中国',
                    //     type: 'map',
                    //     mapType: Province,
                    //     selectedMode: 'single',
                    //     label: {
                    //         normal: {
                    //             show: false,
                    //             color: '#fff'
                    //         }
                    //     },
                    //     itemStyle: {
                    //         normal: {
                    //             areaColor: 'transparent',
                    //             borderColor: '#63e0e3',
                    //             color: 'red',
                    //         },
                    //         emphasis: {
                    //             areaColor: '#061c2f',
                    //             color: '#fff',
                    //         }
                    //     },
                    //     data: []
                    // },
                    {
                        name: 'data',
                        type: 'scatter',
                        coordinateSystem: 'geo',
                        data: convertData(),
                        symbolSize: function (val) {
                            return val[2] / 10;
                        },
                        label: {
                            normal: {
                                formatter: '{b}',
                                position: 'right',
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        itemStyle: {
                            normal: {
                                color: '#ddb926'
                            }
                        }
                    }
                ]
            };
        } else {
            alert('请选择所属省份');
            $('#mapRmodynamic').addClass('active');
            $('#sonMap').removeClass('active');
            return;
        }
        mapChart.on('click', function (params) {
            $('#mapRmodynamic').addClass('active');
            $('#sonMap').removeClass('active');
            var ind = $('.tab-header .active').index();
            ///--(ind);
            if (ind == 1) {
                handleTransportMap();
            } else {
                handleHeatMap();
            }
        });
        mapChart.setOption(mapOption, true);
    //}});
    window.addEventListener("resize", function() {
        mapChart.resize();
    });
});


//数据传输地图
function changeTransMap(dataArray) {
    function formtGCData() {
        var tGeoDt = [];
        for(var i=0; i<dataArray.length; i++){
            tGeoDt.push({
                coords: [dataArray[i][0].coordinate, dataArray[i][1].coordinate]
            });
        }
        return tGeoDt;
    }

    var convertData = function() {
        var res = [];
        var keyValue = {};
        for(var i=0; i<dataArray.length; i++){
            keyValue[dataArray[i][0].cityName] = dataArray[i][0].coordinate.concat(dataArray[i][0].deviceCount);
            keyValue[dataArray[i][1].cityName] = dataArray[i][1].coordinate.concat(dataArray[i][1].deviceCount);
        }
        for (var key in keyValue){
            res.push({
                name: key,
                value: keyValue[key]
            });
        }
        return res;
    };

    function formtVData() {
        var tGeoDt = [];
        var keyValue = {};
        for(var i=0; i<dataArray.length; i++){
            keyValue[dataArray[i][1].cityName] = dataArray[i][1].coordinate;
        }
        for (var key in keyValue){
            tGeoDt.push({
                name: key,
                value: keyValue[key],
                symbolSize: 12,
                itemStyle: {
                    normal: {
                        color: '#e84161',
                        borderColor: '#000'
                    }
                }
            });
        }
        return tGeoDt;
    }

    var planePath = 'circle';
    var mapOption = {
        tooltip: {
            trigger: 'item',
            formatter: function (params) {
                console.log(JSON.stringify(params));
                if(params.componentSubType=='lines'){
                    return "";
                }else if (params.componentSubType=='scatter') {
                    return params.data.name
                        + '<div style="border-bottom: 1px solid rgba(255,255,255,.3); '
                        + 'font-size: 14px;padding-bottom: 7px;margin-bottom: 7px"></div>'
                        + '数据量：<font style="color:#fe9601">' + params.data.value[2] + '</font>';
                }
            },
        },
        geo: {
            map: 'china',
            label: {
                emphasis: {
                    show: false
                }
            },
            roam: false,
            itemStyle: {
                normal: {
                    areaColor: 'transparent',
                    borderColor: '#63e0e3'
                },
                emphasis: {
                    areaColor: '#061c2f'
                }
            },
            left: 0,
            right: 0,
            layoutCenter: ['100%', '100%'],
            // 如果宽高比大于 1 则宽度为 100，如果小于 1 则高度为 100，保证了不超过 100x100 的区域
        },
        series: [
        {
            type: 'lines',
            zlevel: 2,
            effect: {
                show: true,
                period: 6,
                trailLength: 0.1,
                color: '#68d9cc',
                symbol: planePath,
                symbolSize: 8 * winTimes
            },
            lineStyle: {
                normal: {
                    color: '#68d9cc',
                    width: 1,
                    opacity: 0.4,
                    curveness: 0.2
                }
            },
            data: formtGCData()
        }, {
            name: 'data',
            type: 'scatter',
            coordinateSystem: 'geo',
            data: convertData(),
            symbolSize: function(val) {
                return val[2] / 100;
            },
            // label: {
            //     normal: {
            //         formatter: '{b}',
            //         position: 'right',
            //         show: false
            //     },
            //     emphasis: {
            //         show: true
            //     }
            // },
            itemStyle: {
                normal: {
                    color: '#eb355e'
                }
            }
        }, {
            //城市小圆点
            name: 'guidata',
            type: 'effectScatter',
            coordinateSystem: 'geo',
            zlevel: 2,
            rippleEffect: {
                period: 4,
                scale: 2.5,
                brushType: 'stroke'
            },
            data: formtVData(),
            //小圆点大小
            symbolSize: 20,
            showEffectOn: 'render',
            rippleEffect: {
                brushType: 'stroke'
            },
            hoverAnimation: true,
            label: { //控制小圆点旁边的原点不展示
                normal: {
                    formatter: '{b}',
                    position: 'right',
                    show: true
                }
            }
        }]
    };
    mapChart.setOption(mapOption, true);
    window.addEventListener("resize", function() {
        mapChart.resize();
    });
}

function handleTransportMap() {
    $.ajax({url:"transportMap",success:function(result){
        var dataArray = $.parseJSON(result).result.object;
        changeTransMap(dataArray);
        $('.rmodynamic').removeClass('active');
        $('.transmission').addClass('active');
        transportMapResult = result;
    }});
    while(timer_array_heat_map.length>0){
        clearInterval(timer_array_heat_map.pop());
    }
    var timer = setInterval(function () {
        $.ajax({url:"transportMap",success:function(result){
            var dataArray = $.parseJSON(result).result.object;
            changeTransMap(dataArray);
            $('.rmodynamic').removeClass('active');
            $('.transmission').addClass('active');
        }});
    },20000);
    timer_array_transport_map.push(timer);
}

// $(document).ready(function(){
//     setInterval(handleTransportMap,500000);
// });