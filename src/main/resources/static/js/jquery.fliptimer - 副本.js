(function($) {

  /**
   * @class flipTimer
   * @constructor
   *
   * @param element {HTMLElement} the element flipTimer is called on
   */
  var time = 1;

  var flipTimer = function(element, options) {
    this.element = element;

    // ensures the HTMLElement has a class of 'flipTimer'
    if (!this.element.hasClass('flipTimer')) {
      this.element.addClass('flipTimer');
    }

    // attach users options to instance
    this.userOptions = options;

    // attach default options to instance
    this.defaultOptions = flipTimer.defaults;

    // merge default options with user options and attach to instance
    this.options = $.extend({}, this.defaultOptions, this.userOptions);

    // detects if the seconds digits should be used
    if (this.element.find('.seconds').length > 0) {
      this.options.seconds = this.element.find('.seconds')[0];
    }

    // detects if the minutes digits should be used
    if (this.element.find('.minutes').length > 0) {
      this.options.minutes = this.element.find('.minutes')[0];
    }

    // detects if the hours digits should be used
    if (this.element.find('.hours').length > 0) {
      this.options.hours = this.element.find('.hours')[0];
    }

    // detects if the days digits should be used
    if (this.element.find('.days').length > 0) {
      this.options.days = this.element.find('.days')[0];
    }

    // store the date/time when initialised
    this.initDate = this.options.date;

    this.maxData = this.options.maxdata;

    // make the date into a javascript date
    //this.options.date = new Date(this.options.date);

    // untested
    this.calculateDate();
  };

  flipTimer.defaults = {
    seconds: false,
    minutes: false,
    hours: false,
    days: false,
    direction: 'up',
    callback: null,
    digitTemplate: '' +
      '<div class="digit">' +
      '  <div class="digit-top">' +
      '    <span class="digit-wrap"></span>' +
      '  </div>' +
      '  <div class="shadow-top"></div>' +
      '  <div class="digit-bottom">' +
      '    <span class="digit-wrap"></span>' +
      '  </div>' +
      '  <div class="shadow-bottom"></div>' +
      '</div>'
  };

  flipTimer.prototype = {
    /**
     * Calculates the difference in date for the timer
     *
     * @method calculateDate
     */
    calculateDate: function() {
      var dateDiff;

      // calculates the difference in dates
      // if (this.options.direction == 'down') {
      //   dateDiff = this.options.date - this.initDate;
      // } else if (this.options.direction == 'up') {
      //   dateDiff = this.initDate - this.options.date;
      // }
      // console.log(this.initDate);
      // sets the date/time on the instance
      dataArr = this.initDate[0].split("/");
      this.seconds = dataArr[2];
      this.minutes = dataArr[1];
      this.hours = dataArr[0];
      //console(hours+','+minutes+','+seconds);
      //this.days = Math.floor(dateDiff/1000/60/60/24);

      // render the html for the plugin
      this.render();
    },

    /**
     * Dictates what needs rendering for the plugin
     *
     * @method render
     */
    render: function() {
      // if using seconds, populate it
      if (this.options.seconds) {
        this.renderDigits(this.options.seconds, this.seconds);
      }
      // if using minutes, populate it
      if (this.options.minutes) {
        this.renderDigits(this.options.minutes, this.minutes);
      }
      // if using hours, populate it
      if (this.options.hours) {
        this.renderDigits(this.options.hours, this.hours);
      }
      // if using days, populate it
      if (this.options.days) {
        this.renderDigits(this.options.days, this.days);
      }

      this.startTimer();
    },

    /**
     * Renders the digits for a given subject
     *
     * @method renderDigits
     * @param subject {HTMLElement} the element to generate digits for
     */
    renderDigits: function(subject, value) {
      var i, x, max, maxDigit, currentDigit, _this = this, number_array;

      // if digits are not already rendered...
      if ($(subject).find('.digit').length == 0) {
        // split the value into two individual digits
        // unless time has ran out

        if (_this.hours < 0 && _this.minutes < 0 && _this.seconds < 0) {
          number_array = [0,0];
        } else {
          //number_array = String((value / 10).toFixed(1)).split('.');
          number_array = value;
          // console.log('num:'+number_array);
        }
        ///--console.log(_this.hours+','+_this.minutes+','+_this.seconds);
        ///--console.log('num:'+number_array);

        // set maximum digits for seconds/minutes/hours
        // if (subject == _this.options.seconds || subject == _this.options.minutes) {
        //   // minutes and seconds max digit
        //   maxDigit = 5;
        // } else if (subject == _this.options.hours) {
        //   // hours max digit
        //   maxDigit = 2;
        // } else {
        //   // everything else digit max
        //   maxDigit = 9;
        // }
        maxDigit = 9;

        // append two divs to contain two sets of digits for each subject
        $(subject).append('<div class="digit-set"></div><div class="digit-set"></div><div class="digit-set"></div>');

        // for each digit-set in the subject
        $(subject).find('.digit-set').each(function(el) {
          // if first digit, then use digit max
          max = (el == 0) ? maxDigit : 9;
          ///--console.log('max:'+max);
          // generate the right number of digits
          for(i=0; i<=max; i++) {
            // append the digit template
            $(this).append(_this.options.digitTemplate);

            // if direction is down then make numbers decline
            x = (_this.options.direction == 'down') ? max - i : i;

            // select the current digit and apply the number to it
            currentDigit = $(this).find('.digit')[i];
            $(currentDigit).find('.digit-wrap').append(i);

            // if the current number matches the value then apply active class
            if (x == number_array[el]) {
              $(currentDigit).addClass('active');
            } else if (number_array[el] != 0 && ((x + 1) == number_array[el])) {
              // if the current number is one less than active but not zero
              $(currentDigit).addClass('previous');
            } else if (number_array[el] == 0 && x == max) {
              // if the current number is zero then apply previous to max
              $(currentDigit).addClass('previous');
            }
          }
        });
      }
    },

    /**
     * Start a timer with an interval of 1 second
     *
     * @method startTimer
     */

    startTimer: function() {
      var _this = this;
      

      clearInterval(_this.timer);
      _this.timer = setInterval(function() {
        //遍历假数据
        if(time<_this.initDate.length){
          dataArr = _this.initDate[time].split("/");
          _this.seconds = dataArr[2];
          _this.minutes = dataArr[1];
          _this.hours = dataArr[0];
          time++;
          ///--console.log(_this.seconds[2]);
          // var h1 = $('.hours').find('.digit').eq(_this.hours[0]);
          // var h2 = $('.hours').find('.digit').eq(_this.hours[1]);
          // var h3 = $('.hours').find('.digit').eq(_this.hours[2]);
          // var m1 = $('.minutes').find('.digit').eq(_this.minutes[0]);
          // var m2 = $('.minutes').find('.digit').eq(_this.minutes[1]);
          // var m3 = $('.minutes').find('.digit').eq(_this.minutes[2]);
          // var s1 = $('.seconds').find('.digit').eq(_this.seconds[0]);
          // var s2 = $('.seconds').find('.digit').eq(_this.seconds[1]);
          var s3 = $('.seconds').find('.digit-set').eq(2).find('.digit').eq(_this.seconds[2]);
          // h1.addClass('active').sibling().removeClass('active');
          // h2.addClass('active').sibling().removeClass('active');
          // h3.addClass('active').sibling().removeClass('active');
          // m1.addClass('active').sibling().removeClass('active');
          // m2.addClass('active').sibling().removeClass('active');
          // m3.addClass('active').sibling().removeClass('active');
          // s1.addClass('active').sibling().removeClass('active');
          // s2.addClass('active').sibling().removeClass('active');
          s3.prev().addClass('previous');
          s3.addClass('active').siblings().removeClass('active');
        }else{
          clearInterval(_this.timer);
        }
        ///--console.log(_this.hours+','+_this.minutes+','+_this.seconds);
        // if timer runs out stop the timer
        // if (_this.days <= 0 && _this.hours <= 0 && _this.minutes <= 0 && _this.seconds <= 0) {
        //   // execute callback if one exists
        //   if (_this.options.callback) {
        //     _this.options.callback();
        //   }

        //   clearInterval(_this.timer);
        //   return;
        // }

      },2500);
    },

    /**
     * Changes classes on the digits to increase the number
     *
     * @method increaseDigit
     * @param target {HTMLElement} the element to increase digit for
     */
    increaseDigit: function(target) {

      // var digitSets = new Array(), _this = this;

      // // find all digit-sets related to digit type
      // $(target).find('.digit-set').each(function() {
      //   digitSets.push(this);
      // });

      // // increase individual digit
      // increase(digitSets[digitSets.length - 1]);

      // /**
      //  * Increases individual digit in a digit-set
      //  *
      //  * @param el {HTMLElement} the digit-set being increased
      //  */
      // function increase(el) {
      //   var current = $(el).find('.active'),
      //       previous = $(el).find('.previous'),
      //       index = $.inArray(el, digitSets);

      //   previous.removeClass('previous');
      //   current.removeClass('active').addClass('previous');

      //   if (current.next().length == 0) {
          
      //       // increase to first digit in set
      //       $(el).find('.digit:first-child').addClass('active');
      //     if (index != 0) {
      //       // increase digit of sibling digit-set
      //       // console.log('index'+index);
      //       increase(digitSets[index - 1]);
      //     }
      //   } else {
      //     if (_this.options.direction == "up"
      //         && target == _this.options.hours
      //         && _this.hours == 999) {
      //       // if the hours digit reaches 24 it should make 0 active
      //       $(el).find('.digit:first-child').addClass('active');
      //       increase(digitSets[index - 1]);
      //     } else {
      //       // increase the next digit
      //       current.next().addClass('active');
      //     }
      //   }
      // }
    }
  };

  $.fn.flipTimer = function(options) {
    return this.each(function() {
      if (!$(this).data('flipTimer')) {
        $(this).data('flipTimer', new flipTimer($(this), options));
      }
    });
  };
})(jQuery);
