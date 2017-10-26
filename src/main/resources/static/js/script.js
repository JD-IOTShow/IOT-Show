$(function() {

    // Cache some selectors

    var clock = $('#clock');
    // Map digits to their names (this will be an array)
    var digit_to_name = 'zero one two three four five six seven eight nine'.split(' ');

    // This object will hold the digit elements
    var digits = {};

    // Positions for the hours, minutes, and seconds
    var positions = [
        'y1', 'y2', 'y3', 'y4', '年', 'M1', 'M2', '月', 'd1', 'd2', '日', 'h1', 'h2', ':', 'm1', 'm2', ':', 's1', 's2'
    ];

    var digit_holder = clock.find('.digits');

    $.each(positions, function() {

        if (this == ':') {
            digit_holder.append('<div class="dots">');
        } else if (this == '年' || this == '月' || this == '日') {
            digit_holder.append('<div class="text">' + this + '</div>');
        } else {

            var pos = $('<div>');

            for (var i = 1; i < 8; i++) {
                pos.append('<span class="d' + i + '">');
            }

            // Set the digits as key:value pairs in the digits object
            digits[this] = pos;

            // Add the digit elements to the page
            digit_holder.append(pos);
        }

    });

    (function update_time() {

        var now = moment().format("YYYYMMDDHHmmss");
        // console.log(now);
        digits.y1.attr('class', digit_to_name[now[0]]);
        digits.y2.attr('class', digit_to_name[now[1]]);
        digits.y3.attr('class', digit_to_name[now[2]]);
        digits.y4.attr('class', digit_to_name[now[3]]);
        digits.M1.attr('class', digit_to_name[now[4]]);
        digits.M2.attr('class', digit_to_name[now[5]]);
        digits.d1.attr('class', digit_to_name[now[6]]);
        digits.d2.attr('class', digit_to_name[now[7]]);
        digits.h1.attr('class', digit_to_name[now[8]]);
        digits.h2.attr('class', digit_to_name[now[9]]);
        digits.m1.attr('class', digit_to_name[now[10]]);
        digits.m2.attr('class', digit_to_name[now[11]]);
        digits.s1.attr('class', digit_to_name[now[12]]);
        digits.s2.attr('class', digit_to_name[now[13]]);
        setTimeout(update_time, 1000);

    })();

});