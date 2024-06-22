<template>
  <!-- 原始代码，现在应用了CSS类以及进行了组件化 -->
  <div class="body1 device-info-container">
    <span class="device-info">{{ label }}：{{ value }}</span>
    <span class="device-info">{{ rateLabel }}：{{ rateValue }}</span>
    <span class="device-info">{{ waterLabel }}：{{ waterValue }}</span>
  </div>
</template>

<style>
.device-info-container {
  display: flex;
  align-items: center;
}

.device-info {
  display: inline-block;
  height: 30px;
  line-height: 30px;
  margin-right: 10px;
}
</style>

<script>
export default {
  props: {
    label: String,
    value: [Number, String],
    rateLabel: String,
    rateValue: [Number, String],
    waterLabel: String,
    waterValue: [Number, String]
  },
  computed: {
    formattedValue() {
      return parseFloat(this.value).toLocaleString()
    },
    formattedRateValue() {
      return parseFloat(this.rateValue).toFixed(2) + '%'
    }
  }
}
</script>
