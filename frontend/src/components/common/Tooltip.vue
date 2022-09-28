<template>
  <div>
    <v-tooltip v-bind="$attrs">
      <template v-slot:activator="{ props }">
        <v-icon v-bind="props" style="padding:0px 10px;">
          {{ iconName }}
        </v-icon>
      </template>
      <span>
        {{ title }}<br />{{ content }}
      </span>
    </v-tooltip>
  </div>
</template>

<script>
export default {
  props: {
    iconName: String,
    title: String,
    content: String,
  },
}
</script>

<style>

</style>