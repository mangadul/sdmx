lib.grid = {
  reload: function() {
    $("#PivotGrid").ejPivotGrid();
  },
  init: function() {
    var filters = [];
    var sample = lib.data[0];

    for (var key in sample.extAttributes) {
      var fieldName = sample.extAttributes[key];
      filters.push({fieldName: fieldName, fieldCaption: fieldName});
    }

    $("#PivotGrid").ejPivotGrid({
      dataSource: {
        data: lib.data,
        rows: [
          {fieldName: "variable", fieldCaption: "Variable"},
        ],
        columns: [
          {fieldName: "year", fieldCaption: "Year"},
          {fieldName: "month", fieldCaption: "Month"},
        ],
        values: [
          {fieldName: "value",},
        ],
        filters: filters,
        catalog: "[[ ${dataFlow.name} ]]",
        cube: "",
      },
      frozenHeaderSettings: {enableFrozenHeaders: true},
      enableToolTip: false,
      enableGroupingBar: true,
    });
  },
};