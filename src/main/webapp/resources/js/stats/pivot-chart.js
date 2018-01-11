var ddlTarget, chartTarget;

lib.chart = {
  ele: $("#PivotChart"),
  reload: function() {
    lib.chart.ele.data("ejPivotChart").refreshControl();
  },
  init: function() {
    $("#sampleProperties").ejPropertiesPanel();

    lib.chart.ele.ejPivotChart({
      dataSource: {
        data: lib.data,
        rows: [
          {fieldName: "year", fieldCaption: "Year"},
        ],
        columns: [
          {fieldName: "variable", fieldCaption: "Variable"},
        ],
        values: [
          {fieldName: "value"},
        ],
      },
      isResponsive: true,
      zooming : { enableScrollbar : true},
      commonSeriesOptions: {
        enableAnimation: true,
        type: ej.PivotChart.ChartTypes.Column,
        tooltip: { visible: true }
      },
      size: { height: "450px", width: "95%" },
      primaryYAxis: { title: { text: "Amount" } },
      legend: { visible: true, rowCount: 2 },
      load: "loadTheme",
      beforeExport: function(args) {
        // args._csrf = fn.csrf;
      },
      // enableCanvasRendering: true,
      // exportSettings: { type: "png", mode: "client", fileName: "ChartSnapshot" }
    });

    $('#chartType').ejDropDownList({
      dataSource: ["Column", "Line", "Scatter", ],
      width: "110px", selectedIndices: [0], height: "22px"
    });

    ddlTarget = $('#chartType').data("ejDropDownList");

    $("#chartType").ejDropDownList("option", "change", function(args) {
      chartTarget.model.type = args.text.toLowerCase();
      chartTarget.model.commonSeriesOptions.type = args.text.toLowerCase();

      if (jQuery.inArray(chartTarget.model.type, [
        "line", "spline", "area", "splinearea", "stepline", "steparea", "stackingarea", "scatter"
      ]) > -1) {
        chartTarget.model.commonSeriesOptions.marker = {
          shape: ej.PivotChart.SymbolShapes.Circle,
          size: { height: 12, width: 12 },
          visible: true, connectorLine: { height: 30, type: "line" },
          border: { width: 3, color: 'white' }
        };
      }
      else if (jQuery.inArray(chartTarget.model.type, ["funnel", "pyramid"]) > -1) {
        chartTarget.model.commonSeriesOptions.marker = {
          dataLabel: {
            visible: true,
            shape: 'none',
            font: { color: 'white', size: '12px', fontWeight: 'lighter' }
          }
        }
      }
      else chartTarget.model.commonSeriesOptions.marker = { visible: false };

      if (chartTarget.model.type == "pyramid" || chartTarget.model.type == "funnel")
        chartTarget.model.legend.rowCount = 1;
      else
        chartTarget.model.legend.rowCount = 2;

      if (jQuery.inArray(chartTarget.model.type, ["pie", "doughnut"]) > -1)
        chartTarget.model.commonSeriesOptions.explode = true;
      else
        chartTarget.model.commonSeriesOptions.explode = false;

      chartTarget.renderControlSuccess({
        "JsonRecords": JSON.stringify(chartTarget.getJSONRecords()),
        "OlapReport": chartTarget.getOlapReport(),
      });
    });

    chartTarget = lib.chart.ele.data("ejPivotChart");

    $("[data-export]").click(function() {
      var $this = $(this);
      var type = $this.data("export");
      var chartObj = lib.chart.ele.data("ejPivotChart");

      chartObj.exportPivotChart(
        fn.url("stats/chart-export/"+ type +"?_csrf="+ fn.csrf),
        "chart"
      );
    })
  },
}
