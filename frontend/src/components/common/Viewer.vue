<template>
  <div>
    <div id="viewer" ref="viewer"></div>
  </div>
</template>

<script>
import { Editor } from "@toast-ui/editor";
import "@toast-ui/editor/dist/toastui-editor.css"; // 지워도 될지 Build Test
import "@toast-ui/editor/dist/toastui-editor-viewer.css";

export default {
  data() {
    return {
      viewer: null,
    }
  },
  mounted() {
    this.viewer = Editor.factory({
      el: document.querySelector("#viewer"),
      viewer: true,
      height: "500px",
    });
  },
  methods: {
    async setContent(content) {
      // this.viewer.setMarkdown(content);
      try {
        this.viewer.setMarkdown(content);
      } catch (error) {
        console.log(error);
      }
    },
  },
}
</script>

<style>

</style>