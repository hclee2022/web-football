<template>
  <div>
    <v-container>
      <v-card class="mx-auto" max-width="500" tile>
        <v-card-title>
          Welcome, Manager !
        </v-card-title>
        <br />
        <v-card-text>
          <h2>
            소개
          </h2>
          <br />
          국내, 해외 축구 데이터 분석 웹 사이트
          <br />
          <br />
          <h2>
            이용 가능한 서비스
          </h2>
          <br />
          <li>이적 상황</li>
          <li>대회</li>
          <li>마이 팀</li>
          <li>건의 사항</li>
          <li>자유게시판</li>
          <br />
        </v-card-text>
      </v-card>
    </v-container>
  </div>
</template>

<script>
export default {
  name: "Home",
  components: {},
}
</script>

<style scoped>
  a {
    text-decoration: none;
  }
</style>