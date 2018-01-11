var chart = {
  init: function(data) {
    $("#PivotChart").ejPivotChart({
      dataSource: {
        data: data,
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
        tooltip: { visible: true },
      },
      size: { height: "450px", width: "95%" },
      primaryYAxis: { title: { text: "Value" } },
      legend: { visible: true, rowCount: 2 },
      load: "loadTheme",
    });

    $("#chart-type").change(function() {
      var type = $(this).val();
      var chart = $('#PivotChart').data("ejPivotChart");

      console.log(type);
      chart.model.type = type;
      chart.model.commonSeriesOptions.type = type;
      chart.model.commonSeriesOptions.explode = false;
      chart.model.commonSeriesOptions.marker = {
        shape: ej.PivotChart.SymbolShapes.Circle,
        size: { height: 12, width: 12 },
        border: { width: 3, color: 'white' },
        connectorLine: { height: 30, type: "line" },
        visible: true,
      };
    }).trigger("change");
  },
};
