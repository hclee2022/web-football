<template>
  <v-container>
    <v-card elevation="10" outline width="100%" class="mx-auto">
      <v-card-title>
        <span class="mr-2">
          Write
        </span>
      </v-card-title>
      <v-card-text>
        <v-text-field
          label="Title"
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
              color="success"
              rounded
              small
              iconName="mdi-content-save-all"
              btnName="Save">
            </Button>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">
                Confirm Write
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
                @click="save()">
                Agree
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <Button
          @click="movePage('/list')"
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
import { insertBoardAPI } from "@/api/index";
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
    title: "",
  }),
  methods: {
    async save() {
      this.dialog = false;

      var content = this.$refs.editor.getContent();
      var title = this.title;
      
      insertBoardAPI({
        params: {
          title: title,
          content: content,
        },
      })
        .then(response => {
          if (response.data > 0) {
            // this.$store.commit("SET_SNACKBAR", {
            //   show: true,
            //   msg: "Insert Complete",
            //   color: "success",
            // })
            this.movePage("/detail?docNo=" + response.data);
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