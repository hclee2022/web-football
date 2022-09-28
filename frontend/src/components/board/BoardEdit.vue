<template>
  <v-container>
    <v-card elevation="10" outline width="100%" class="mx-auto">
      <v-card-title>
        <span class="mr-2">
          Edit
        </span>
      </v-card-title>
      <v-card-text>
        <v-text-field
          label="Edit"
          :rules="rules"
          :counter="100"
          v-model="title">
        </v-text-field>
        <Editor ref="editor" />
      </v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-dialog v-model="dialog" width="600px">
          <template v-slot:activator="{ props }">
            <Button
              v-bind="props"
              color="warning"
              rounded
              small
              iconName="mdi-pencil"
              btnName="Edit">
            </Button>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">
                Confirm Edit
              </span>
            </v-card-title>
            <v-card-text>
              Do You Want to Save It ?
            </v-card-text>
            <v-card-actions>
              <v-spacer />
              <v-btn
                color="green darken-1"
                text
                @click="dialog = false">
                
                Disagree
              </v-btn>
              <v-btn
                color="green darken-1"
                text
                @click="edit()">
                Agree
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <Button
          @click="movePage('/detail?docNo=' + docNo)"
          color="light-green lighten-1"
          rounded
          small
          iconName="mdi-arrow-left"
          btnName="Back">
        </Button>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import Editor from "@/components/common/Editor.vue";
import { getBoardDetailAPI ,updateBoardAPI } from "@/api/index";
import Button from "@/components/common/Button.vue";

export default {
  components: {
    Editor,
    Button,
  },
  data: () => ({
    dialog: false,
    rules: [
      value => !!value || "Required.",
      value => (value && value.length >= 3) || "Min 3 Characters",
    ],
    docNo: "",
    title: "",
    writer: "",
  }),
  mounted() {
    getBoardDetailAPI({
      params: {
        docNo: this.$route.query.docNo,
      },
    })
      .then(response => {
        this.docNo = response.data.docNo;
        this.title = response.data.title;
        this.$refs.editor.editContent(response.data.content);
        // this.writer = response.data.writer;
      })
      .catch(error => {
        console.log(error);
      })
  },
  methods: {
    async edit() {
      this.dialog = false;

      var docNo = this.docNo;
      var title = this.title;
      var content = this.$refs.editor.getContent();

      updateBoardAPI({
        params: {
          docNo: docNo,
          title: title,
          content: content,
        },
      })
        .then(response => {
          if (response.data > 0) {
            // this.$store.commit("SET_SNACKBAR", {
            //   show: true,
            //   msg: "Edit Complete",
            //   color: "warning",
            // })
            this.movePage("/detail?docNo=" + docNo);
          }
        })
        .catch(error => {
          console.log(error);
        })
    },
  },
}
</script>

<style>

</style>