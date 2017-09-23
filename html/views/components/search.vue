<template>
    <div>
        <p class="search-info">
            搜索<b style="color: #FE5E00">"{{keyword}}"</b>
        </p>
        <Tabs type="card">
            <TabPane label="虾米"></TabPane>
            <TabPane label="网易"></TabPane>
            <TabPane label="QQ"></TabPane>
            <TabPane label="百度"></TabPane>
            <TabPane label="酷狗"></TabPane>
            <TabPane label="酷我"></TabPane>
        </Tabs>

        <v-playlist :songs="searchResult"></v-playlist>
    </div>
</template>

<script>
    define(["vue"], function(Vue) {

        let asyncComp = function(componentName) {
            return function(resolve) {
                require([componentName], resolve);
            };
        };

        return Vue.component("v-search", {
            template: template,
            data: function() {
                return {
                    keyword: this.$route.params.keyword,
                    size: this.$route.params.size,
                    page: this.$route.params.page,
                    type: this.$route.params.type,
                    searchResult: []
                };
            },
            mounted: function() {
                let url = "https://itwusun.com/api/music/search?sign=50d4dc8552623f10422f030ffb5ffd0d&k=" + encodeURIComponent(this.keyword) + "&s=" + this.size + "&p=" + this.page + "&t=" + this.type;
                this.$http.jsonp(url).then(function (resp) {
                    this.searchResult = resp.data;
//                    if(this.searchResult.length > 0){
//                        this.playIndex = 0;
//                    }
                }).catch(function (resp) {
                    console.log(resp.data);
                });
            },
            components:{
                'v-playlist' : asyncComp("vuejs!./views/components/playlist")
            }
        });
    });
</script>
