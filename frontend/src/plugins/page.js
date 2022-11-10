import router from "@/router";

export default {
    /**
     * @param {Object} app
     * @param {Object} options
     */
    
    install: (app) => {
        /**
         * Move Page
         * @param {*} url
         */
        const movePage = (url) => {
            if (router.currentRoute.value.path != url) {
                router.push(url);
            }
        }
        app.config.globalProperties.movePage = movePage;

        /**
         * Move Previous Page
         * 
         */
        const prevPage = () => {
            window.history.length > 1 ? router.go(-1) : router.push("/")
        }
        app.config.globalProperties.prevPage = prevPage;

        /**
         * Refresh the page
         * 
         */
        const refresh = () => {
            router.go(router.currentRoute);
        }
        app.config.globalProperties.refresh = refresh;
    }
}