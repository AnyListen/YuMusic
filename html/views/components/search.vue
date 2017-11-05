<template>
    <div id="search-content">
        <p class="search-info">
            搜索<b style="color: #FE5E00">"{{keyword}}"</b>
        </p>
        <Tabs type="card" @on-click="changeSearch">
            <TabPane label="虾米" name="xm"></TabPane>
            <TabPane label="网易" name="wy"></TabPane>
            <TabPane label="QQ" name="qq"></TabPane>
            <TabPane label="百度" name="bd"></TabPane>
            <TabPane label="酷狗" name="kg"></TabPane>
            <TabPane label="酷我" name="kw"></TabPane>
        </Tabs>

        <v-playlist :songs="searchResult" v-show="!isSearching"></v-playlist>

        <div class="demo-spin-col" v-show="isSearching">
            <Spin fix>
                <Icon type="load-c" size="24" class="demo-spin-icon-load"></Icon>
                <div>加载中</div>
            </Spin>
        </div>
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
                    isSearching: false,
                    keyword: this.$route.params.keyword,
                    size: this.$route.params.size,
                    page: this.$route.params.page,
                    type: this.$route.params.type,
                    searchResult: []
                };
            },
            mounted: function() {
                this.searchSong();
            },
            components:{
                'v-playlist' : asyncComp("vuejs!./views/components/playlist")
            },
            methods:{
                searchSong: function () {
                    this.isSearching = true;
                    let url = "https://itwusun.com/api/music/search?sign=50d4dc8552623f10422f030ffb5ffd0d&k=" + encodeURIComponent(this.keyword) + "&s=" + this.size + "&p=" + this.page + "&t=" + this.type;
                    this.$http.jsonp(url).then(function (resp) {
                        this.searchResult = resp.data;
                        this.isSearching = false;
//                    if(this.searchResult.length > 0){
//                        this.playIndex = 0;
//                    }

                    }).catch(function (resp) {
                        console.log(resp.data);
                        this.isSearching = false;
                    });
                },
                changeSearch : function (name) {
                    this.type = name;
                    this.searchSong();
                }
            }
        });
    });
</script>
