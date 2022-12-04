const methods = {
    paramsCompetitions(params) {
        this.$router.push({
            name: "Competitions",
            params: params,
        });
    },
    paramsTeam(params) {
        this.$router.push({
            name: "Team",
            params: params,
        });
    },
    paramsPlayer(params) {
        this.$router.push({
            name: "Player",
            params: params,
        });
    },
}

export default {
    install(app) {
        app.config.globalProperties.$paramsCompetitions = methods.paramsCompetitions;
        app.config.globalProperties.$paramsTeam = methods.paramsTeam;
        app.config.globalProperties.$paramsPlayer = methods.paramsPlayer;
    }
}