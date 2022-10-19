<template>
  <v-container>
    <v-card elevation="10" outlined width="100%" class="mx-auto">
      <v-card-title>
        <span class="mr-2">
          Detail
        </span>
      </v-card-title>
      <v-card-text>
        <v-row>
          <v-col>
            <v-text-field label="Title" readonly :model-value="title" />
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-text-field label="Writer" readonly :model-value="writer" />
          </v-col>
          <v-col>
            <v-text-field label="Register Time" readonly dense :model-value="regDttm" />
          </v-col>
          <v-col>
            <v-text-field label="View" readonly dense :model-value="view" />
          </v-col>
        </v-row>
        Content
        <br />
        <div style="height: 300px;">
          <Viewer ref="viewer" />
          <br />
        </div>
        Reply ({{ replyCount }})
        <br />
        <v-table dense>
          <tbody>
            <tr v-for="(reply, index) in replies" :key="index">
              <td style="width:110px; padding:0;" v-if="!isMobile()">
                <v-icon small>
                  mdi-account
                </v-icon>
                {{ reply.writer }}
              </td>
              <td style="width:40px; padding:0;" v-else>
                <Tooltip
                  bottom
                  iconName="mdi-account"
                  title="작성자"
                  :content="reply.writer" />
              </td>
              <td style="padding:0;">
                {{ reply.content }}
              </td>
              <td style="width:140px; padding: 0;" v-if="!isMobile()">
                {{ reply.regDttm }}
              </td>
              <td style="width:40px; padding: 0;" v-else>
                <Tooltip 
                  bottom
                  iconName="mdi-clock-outline"
                  title="작성 일시"
                  :content="reply.regDttm" />
              </td>
              <td style="width:30px; padding: 0 10px;">
                <v-dialog v-model="replyEditDialog">
                  <template v-slot:activator="{ props }">
                    <Button
                      @click="defEditReplyNo(reply.replyNo, reply.content)"
                      v-bind="props"
                      color="grey"
                      icon
                      size="x-small"
                      iconName="mdi-pencil" >
                    </Button>
                  </template>
                  <v-card elevation="10" outlined width="50%" class="mx-auto">
                    <v-card-title>
                      <span class="text-h5">
                        Edit Reply
                      </span>
                    </v-card-title>
                    <v-card-text>
                      <v-text-field label="Comment" v-model="replyComment" />
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer />
                      <v-btn
                        color="green darken-1"
                        text
                        @click="replyEditDialog = false">

                        Disagree
                      </v-btn>
                      <v-btn
                        color="green darken-1"
                        text
                        @click="replyEdit()">

                        Agree
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </td>
              <td style="width:30px; padding: 0;">
                <v-dialog v-model="replyDelDialog" width="600px">
                  <template v-slot:activator="{ props }">
                    <Button
                      @click="defDelReplyNo(reply.replyNo)"
                      v-bind="props"
                      color="red"
                      icon
                      size="x-small"
                      iconName="mdi-close" >
                    </Button>
                  </template>
                  <v-card>
                    <v-card-title>
                      <span class="text-h5">
                        Confirm Reply Delete
                      </span>
                    </v-card-title>
                    <v-card-text>
                      Are You Sure You Want to Delete It ?
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer />
                      <v-btn
                        color="green darken-1"
                        text
                        @click="replyDelDialog = false">

                        Disagree
                      </v-btn>
                      <v-btn
                        color="green darken-1"
                        text
                        @click="replyDel()">

                        Agree
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </td>
            </tr>
          </tbody>
        </v-table>
        <v-divider />
        <v-row>
          <v-col cols="12" md="11" style="padding:0px 12px;">
            <v-textarea 
              clearable
              clear-icon="mdi-close-circle"
              rows="2"
              no-resize
              full-width
              v-model="comment" />
          </v-col>
          <v-col md="1" align-self="center" style="padding: 0px 10px;">
            <Button 
              @click="replySave"
              color="indigo"
              rounded
              small
              iconName="mdi-pencil"
              btnName="Save" >
            </Button>
          </v-col>
        </v-row>
      </v-card-text>
      <v-card-actions>
        <v-spacer />
        <Button 
          @click="movePage('/edit?docNo=' + docNo)"
          color="warning"
          rounded
          small
          iconName="mdi-pencil"
          btnName="Edit" >
        </Button>
        <v-dialog v-model="boardDelDialog" width="600px">
          <template v-slot:activator="{ props }">
            <Button
              v-bind="props"
              color="error"
              rounded
              small
              iconName="mdi-delete-forever"
              btnName="Delete" >
            </Button>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">
                Confirm Delete
              </span>
            </v-card-title>
            <v-card-text>
              Are You Sure You Want to Delete It ?
            </v-card-text>
            <v-card-actions>
              <v-spacer />
              <v-btn
                color="green darken-1"
                text
                @click="boardDelDialog =  false">

                Disagree
              </v-btn>
              <v-btn
                color="green darken-1"
                text
                @click="del()">

                Agree
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <Button 
          @click="movePage('/list')"
          color="grey darken-1"
          rounded
          small
          iconName="mdi-arrow-left"
          btnName="Back" >
        </Button>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import Viewer from "@/components/common/Viewer";
import Tooltip from "@/components/common/Tooltip";
import btnMixins from "@/mixins/btnMixins";

import {
  getBoardDetailAPI,
  deleteBoardAPI,
  insertReplyAPI,
  getReplyListAPI,
  deleteReplyAPI,
  updateReplyAPI,
} from "@/api/index";

export default {
  mixins: [btnMixins],
  components: {
    Viewer,
    Tooltip,
  },
  data() {
    return {
      docNo: 0,
			title: "",
			writer: "",
			regDttm: "",
			view: 0,
			replyCount: 0,
			replies: null,
      comment: "",
      boardDelDialog: false,
      replyDelDialog: false,
      delReplyNo: null,
      replyEditDialog: false,
      editReplyNo: null,
      replyComment: "",
    }
  },
  mounted() {
    getBoardDetailAPI({
      params: {
        docNo: this.$route.query.docNo,
      },
    })
      .then(response => {
        this.docNo = response.data.docNo;
        this.title = response.data.title;
        this.$refs.viewer.setContent(response.data.content);
        this.writer = response.data.writer;
        this.regDttm = response.data.regDttm;
        this.view = response.data.view;
        this.replyCount = response.data.reply;
      })
      .catch(error => {
        console.log(error);
      })

    getReplyListAPI({
      params: {
        docNo: this.$route.query.docNo,
      },
    })
      .then(response => {
        this.replies = response.data;
      })
      .catch(error => {
        console.log(error);
      })
  },
  methods: {
    async del() {
      this.boardDelDialog = false;

      deleteBoardAPI({
        params: {
          docNo: this.docNo,
        },
      })
        .then(response => {
          if (response.data > 0) {
            // this.$store.commit("SET_SNACKBAR", {
            //   show: true,
						// 	msg: 'Delete Complete',
						//   color: 'error',
            // })
            this.movePage("/list");
          }
        })
        .catch(error => {
          console.log(error);
        })
    },
    replySave() {
      if (this.comment !== null) {
        insertReplyAPI({
          params: {
            docNo: this.docNo,
            comment: this.comment,
          },
        })
          .then(response => {
            if (response.data > 0) {
              this.refresh();
						}
          })
          .catch(error => {
            console.log(error);
          })
      }
    },
    async replyEdit() {
      this.replyEditDialog = false;

      updateReplyAPI({
        params: {
          replyNo: this.editReplyNo,
					docNo: this.docNo,
					comment: this.replyComment,
				},
      })
        .then(response => {
          if (response.data > 0) {
						this.refresh()
					}
        })
        .catch(error => {
          console.log(error);
        })
    },
    async replyDel() {
      this.replyDelDialog = false;

      deleteReplyAPI({
				params: {
					replyNo: this.delReplyNo,
					docNo: this.docNo,
				},
      })
        .then(response => {
          if (response.data > 0) {
            this.refresh()
            this.delReplyNo = null;
					}
        })
        .catch(error => {
          console.log(error);
        })
    },
    async defDelReplyNo(replyNo) {
      this.delReplyNo = replyNo;
    },
    async defEditReplyNo(replyNo, content) {
      this.editReplyNo = replyNo;
      this.replyComment = content;
    },
  },
}
</script>

<style>

</style>