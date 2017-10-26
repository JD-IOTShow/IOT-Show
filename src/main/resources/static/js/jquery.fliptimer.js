(function($) {

  /**
   * @class flipTimer
   * @constructor
   *
   * @param element {HTMLElement} the element flipTimer is called on
   */
  
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
        if (_this.days < 0 && _this.hours < 0 && _this.minutes < 0 && _this.seconds < 0) {
          number_array = [0,0];
        } else if (_this.days > 99) {
          number_array = [0,0];
        } else {
          //number_array = String((value / 10).toFixed(1)).split('.');
          number_array = value;
          // console.log('num:'+number_array);
        }

        maxDigit = 9;

        // append two divs to contain two sets of digits for each subject
        $(subject).append('<div class="digit-set"></div><div class="digit-set"></div><div class="digit-set"></div>');

        // for each digit-set in the subject
        $(subject).find('.digit-set').each(function(el) {
          // if first digit, then use digit max
          max = (el == 0) ? maxDigit : 9;

          // generate the right number of digits
          for(i=0; i<=max; i++) {
            // append the digit template
            $(this).append(_this.options.digitTemplate);
            // select the current digit and apply the number to it
            currentDigit = $(this).find('.digit')[i];
            $(currentDigit).find('.digit-wrap').append(i);

            // if the current number matches the value then apply active class
            if (i == number_array[el]) {
              $(currentDigit).addClass('active');
            } else if (number_array[el] != 0 && ((i + 1) == number_array[el])) {
              // if the current number is one less than active but not zero
              $(currentDigit).addClass('previous');
            } else if (number_array[el] == 0 && i == max) {
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
      var time = 1;
      clearInterval(this.timer);
      this.timer = setInterval(function() {
        // increase/decrease seconds
        if(time<_this.initDate.length){
          dataArr = _this.initDate[time].split("/");
          _this.seconds = dataArr[2];
          _this.minutes = dataArr[1];
          _this.hours = dataArr[0];
          time++;
        //(_this.options.direction == 'down') ? _this.seconds-- : _this.seconds++;
        if (_this.options.seconds) _this.increaseDigit(_this.options.seconds,_this.seconds);
        _this.increaseDigit(_this.options.minutes,_this.minutes);
        _this.increaseDigit(_this.options.hours,_this.hours);
      
        }
      },2000);
    },

    /**
     * Changes classes on the digits to increase the number
     *
     * @method increaseDigit
     * @param target {HTMLElement} the element to increase digit for
     */
    increaseDigit: function(target,val) {
      var digitSets = new Array(), _this = this;

      // find all digit-sets related to digit type
      $(target).find('.digit-set').each(function() {
        digitSets.push(this);
      });
      ///--console.log(val);
      // increase individual digit
      increase(digitSets[0],val[0]);
      increase(digitSets[1],val[1]);
      increase(digitSets[2],val[2]);
      /**
       * Increases individual digit in a digit-set
       *
       * @param el {HTMLElement} the digit-set being increased
       */
      function increase(el,sonval) {


        var current = $(el).find('.active'),
            previous = $(el).find('.previous');

        previous.removeClass('previous');
        current.removeClass('active');
        ///--console.log('第'+sonval);
        var activeObj = $(el).find('.digit').eq(sonval);
        ///--console.log(activeObj);
        if (activeObj.prev().length == 0) {
          $(el).find('.digit').eq(8).addClass('previous');
        }else{
          activeObj.prev().addClass('previous');
        }
        activeObj.addClass('active');
        
      }
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
