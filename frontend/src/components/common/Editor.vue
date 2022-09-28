<template>
  <div class="toast-edit" id="editor" ref="editor"></div>
</template>

<script>
import { Editor } from "@toast-ui/editor";
// import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";

export default {
  data: () => ({
    editor: null,
    initialValue: "",
  }),
  mounted() {
    this.editor = new Editor({
      el: document.querySelector("#editor"),
      height: "500px",
      initialEditType: "wysiwyg",
      previewStyle: "vertical",
      language: "ko",
      initialValue: this.initialValue,
    });
  },
  methods: {
    getContent() {
      return this.editor.getMarkdown();
    },
    setContent(content) {
      this.editor.setMarkdown(content);
      // this.editor.setMarkdown(content, true);
    },
    editContent(value) {
      this.initialValue = value;
      this.editor.destroy();

      this.editor = new Editor({
        el: document.querySelector("#editor"),
        height: "500px",
        initialEditType: "wysiwyg",
        previewStyle: "vertical",
        language: "ko",
        initialValue: this.initialValue,
      });
    },
  },
}
</script>

<style>

</style>