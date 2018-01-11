var variable = "Bank Notes and Coins in Circulation"; // #dev

Date.prototype.subMonths = function (month) {
  var dat = new Date(this.valueOf());
  dat = new Date(dat.getFullYear(), dat.getMonth() - month, dat.getDate());

  return dat;
};

var chart = {
  startDate: new Date(),
  endDate: new Date(),
  rangeNavigator: {},
  buttonFlag: true,
  previousFocusedButton: "1Y",
  data: {},

  settings: {
    axis: {min: 0, max: 0, interval: 0},
  },

  init: function(data) {
    chart.readData(data);
    chart.render(chart.data[variable]);
  },

  readData: function(data) {
    for (var i in data) {
      var item = data[i];
      var point = {
        XValue: new Date(item.year, item.month-1, 1),
        YValue: item.value,
      };

      if (!fn.isset(chart.data[item.variable])) {
        chart.data[item.variable] = [];
      }

      if (point.XValue < chart.startDate || typeof chart.startDate === "undefined") {
        chart.startDate = point.XValue;
      }

      if (point.XValue > chart.endDate || typeof chart.endDate === "undefined") {
        chart.endDate = point.XValue;
      }

      if (point.YValue > chart.settings.axis.max) {
        chart.settings.axis.max = point.YValue;
      }

      chart.data[item.variable].push(point);
    }

    chart.settings.axis.max *= 3/2;
    chart.settings.axis.interval = parseInt("2" + (new Array(new String(chart.settings.axis.max).length-1).join("0")));
  },

  render: function(data) {
    $("#container").ejChart({
      primaryXAxis: {
        valueType: 'datetimeCategory',
        majorGridLines: { visible: false },
        labelIntersectAction: "trim",
        intervalType: 'months',
      },

      //Initializing Primary Y Axis
      primaryYAxis: {
        range: chart.settings.axis,
        majorGridLines: { visible: false },
        labelFormat: "{value}",
      },

      commonSeriesOptions: { type: "area", enableAnimation: true },

      //Initializing Series
      series: [
        {
          name: 'India',
          xName: "XValue", yName: "YValue",
          fill: "rgba(124,181,236,0.75)",
          border: { color: "rgba(124,181,236,1)", width: 2 }
        }
      ],
      load: function(sender) {
        loadTheme(sender);
        var chart = $("#container").ejChart("instance");
        sender.model.series[0].dataSource = data;
      },
      trackToolTip: function(sender) {
        var seriesIndex = sender.data.serIndex;
        var pointIndex = sender.data.pointIndex;
        var point = sender.model.series[seriesIndex].points[pointIndex];
        sender.data.currentText = point.x.toDateString() + "<br/>"
          + "Value: " + point.y.toFixed(2);
      },
      title: { text: variable },
      crosshair: {
        visible: true, type: "trackball", line: { color: 'transparent' },
      },
      isResponsive: true,
      size: { height: "300" },
      legend: { visible: false },
    });

    $("#1M, #3M, #6M, #YTD, #1Y, #All")
      .click(function(e) {
        var value = $(this).attr("value");

        $("#" + chart.previousFocusedButton).removeClass("button-hit");
        $("#" + value).addClass("button-hit")
        chart.previousFocusedButton = value;

        var rangeobj = $("#scrollcontent").data("ejRangeNavigator");
        var dateEnd = rangeobj.model.selectedRangeSettings.end;

        eDate = new Date(dateEnd);

        if (value == "1M")
          chart.startDate = eDate.subMonths(1);
        else if (value == "3M")
          chart.startDate = eDate.subMonths(3);
        else if (value == "6M")
          chart.startDate = eDate.subMonths(6);
        else if (value == "YTD") {
          var currentYTD = new Date().subMonths(new Date().getMonth());
          chart.startDate = new Date(currentYTD.getFullYear(), 0, 1);
          rangeobj.model.selectedRangeSettings.end = chart.endDate.toString();
        }
        else if (value == "1Y")
          chart.startDate = eDate.subMonths(12);
        else if (value == "All") {
          rangeobj.model.selectedRangeSettings.end = chart.endDate.toString();
          chart.startDate = new Date(eDate.getFullYear(), eDate.getMonth(), (eDate.getDate() - 2553));
        }
        datePicker1.setModel({ value: chart.startDate });
        datePicker2.setModel({ value: chart.endDate });
        rangeobj.model.selectedRangeSettings.start = chart.startDate.toString();
        chart.buttonFlag = true;
        rangeobj.redraw();
      })
      .ejButton({
        height: 18,
        width: 35,
        showRoundedCorner: true,
      });

    $("#datepick, #datepick2").ejDatePicker({
      value: chart.startDate,
      minDate: chart.startDate,
      maxDate: chart.endDate,
      select: function(sender) {
        $("#" + chart.previousFocusedButton).removeClass("button-hit");
        var rangeobj = $("#scrollcontent").data("ejRangeNavigator");
        rangeobj.model.selectedRangeSettings.start = sender.value;
        rangeobj.redraw();
      },
    });

    $("#scrollcontent").ejRangeNavigator({
      enableDeferredUpdate: true,
      sizeSettings: { height: "75" },
      selectedRangeSettings: {
        start: new Date(chart.endDate.getFullYear(), chart.endDate.getMonth() - 12, chart.endDate.getDate()).toLocaleString(),
        end: chart.endDate.toLocaleString()
      },
      labelSettings: {
        higherLevel: {
          visible: true,
          labelPlacement: "inside",
        },
        lowerLevel: {
          visible: false,
          intervalType: 'quarters',
        },
      },
      valueAxisSettings: {
        visble: true,
        axisLine: { visible: true },
        range: chart.settings.axis,
      },
      navigatorStyleSettings: {
        thumbColor: "#ffffff",
        unselectedRegionColor: "#999999",
        unselectedRegionOpacity: 0.3,
        selectedRegionColor: "#0f4f4f4",
      },
      isResponsive: true,
      series: [{
        name: 'Product A',
        enableAnimation: false,
        type: 'line',
        fill: '#357cd2',
        width: 1,
      }],
      loaded: function(sender) {
        var theme = window.themestyle + window.themecolor + window.themevarient;
        if (theme) {
          switch (theme) {
            case "flatazurelight":
              theme = "azurelight";
              break;
            case "flatlimelight":
              theme = "limelight";
              break;
            case "flatsaffronlight":
              theme = "saffronlight";
              break;
            case "gradientazurelight":
              theme = "gradientazure";
              break;
            case "gradientlimelight":
              theme = "gradientlime";
              break;
            case "gradientsaffronlight":
              theme = "gradientsaffron";
              break;
            case "flatazuredark":
              theme = "azuredark";
              break;
            case "flatlimedark":
              theme = "limedark";
              break;
            case "flatsaffrondark":
              theme = "saffrondark";
              break;
            case "gradientazuredark":
              theme = "gradientazuredark";
              break;
            case "gradientlimedark":
              theme = "gradientlimedark";
              break;
            case "gradientsaffrondark":
              theme = "gradientsaffrondark";
              break;
            case "flathigh-contrast-01dark":
              theme = "highcontrast01";
              break;
            case "flathigh-contrast-02dark":
              theme = "highcontrast02";
              break;
            case "flatmateriallight":
              theme = "material";
              break;
            case "flatoffice-365light":
              theme = "office";
              break;
            default:
              theme = "flatlight";
              break;
          }
          if (theme == "azuredark"
            || theme == "limedark"
            || theme == "saffrondark"
            || theme == "gradientazuredark"
            || theme == "gradientlimedark"
            || theme == "gradientsaffrondark"
            || theme == "highcontrast01"
            || theme == "highcontrast02") {
            sender.model.labelSettings.higherLevel.style.font.color = "white";
            var buttonFont = document.getElementsByClassName("monthButton");

            for (var i = 0; i < buttonFont.length; i++)
              buttonFont[i].style.color = "white";

            sender.model.navigatorStyleSettings.unselectedRegionOpacity = 0.4;
            sender.model.navigatorStyleSettings.opacity = 0.08;
          }
          else {
            sender.model.labelSettings.higherLevel.style.font.color = "black";
            var buttonFont = document.getElementsByClassName("monthButton");

            for (var i = 0; i < buttonFont.length; i++)
              buttonFont[i].style.color = "black";
          }

          sender.model.navigatorStyleSettings.thumbColor = "#ffffff";
          sender.model.navigatorStyleSettings.unselectedRegionColor = "#999999";
          sender.model.navigatorStyleSettings.selectedRegionColor = "#0f4f4f4";
        }
      },
      load: function(sender) {
        datePicker1 = $("#datepick").data("ejDatePicker");
        datePicker2 = $("#datepick2").data("ejDatePicker");;
        chart.rangeNavigator = $("#scrollcontent").ejRangeNavigator("instance");
        sender.model.series[0].dataSource = data;
        sender.model.series[0].xName = "XValue";
        sender.model.series[0].yName = "YValue";
      },
      rangeChanged: function(sender) {
        var chartobj = $("#container").data("ejChart");

        if (chartobj != null) {
          chartobj.model.primaryXAxis.zoomPosition = sender.zoomPosition;
          chartobj.model.primaryXAxis.zoomFactor = sender.zoomFactor;
          datePicker1.setModel({ value: sender.selectedRangeSettings.start });
          datePicker2.setModel({ value: sender.selectedRangeSettings.end });

          if (!chart.buttonFlag) {
            $("#" + chart.previousFocusedButton).removeClass("button-hit");
            chart.buttonFlag = true;
          }
        }

        $("#container").ejChart("redraw");
      },
    });

    $("#scrollcontent").mousedown(function (event) {
      chart.buttonFlag = false;
    });
  },
};