// Tooltip
Vue.directive("tooltip", {
  bind: function(el, binding) {
    var $this = $(el);
    var title = binding.value;

    if (!fn.empty(title))
        $this.attr("title", title);

    var placement = Object.keys(binding.modifiers)[0];

    $this.tooltip({
      placement: placement || "top",
    });
  },
})

// Select2
Vue.component("select2", {
  props: ["options", "value"],
  template: "<select> <slot></slot> </select>",
  mounted: function() {
    var vm = this;
    var select2 = $(this.$el);

    // select2.prepend("<option>");
    
    select2
      .css({width: "100%"})
      // init select2
      .select2({ data: this.options })
      .val(this.value)
      .trigger('change')
      // emit event on change.
      .on('change', function() {
        vm.$emit('input', this.value);
      })
  },
  watch: {
    value: function(value) {
      $(this.$el).val(value).trigger('change');
    },
    options: function(options) {
      $(this.$el).select2({ data: options })
    },
  },
  destroyed: function() {
    $(this.$el).off().select2('destroy')
  }
});

// Number Format
Vue.component("num-format", {
	props: ["value", "decimals", "thousand_sep", "decimal_sep"],
  template: '<input v-on:keyup="onKeyup" />',
  mounted: function() {
    var thousand_sep = this.thousand_sep || ".";
    var decimal_sep = this.decimal_sep || ",";

    $(this.$el).inputmask("currency", {
      rightAlign: false,
      prefix: "",
      groupSeparator: thousand_sep,
      radixPoint: decimal_sep,
      digits: this.decimals || 0,
      removeMaskOnSubmit: true,
      autoGroup: true,
      onUnMask: function(maskedValue, unmaskedValue) {
        return unmaskedValue
          .replace(new RegExp("\\"+thousand_sep, "g"), "")
          .replace(new RegExp("\\"+decimal_sep, "g"), thousand_sep)
      },
    })
  },
  methods: {
    onKeyup: function() {
      this.$emit('input', $(this.$el).inputmask('unmaskedvalue'));
    }
  },
  watch: {
    value: function(value) {
      $(this.$el).val(value)
    },
  }
});

