<template>
    <div id="jp_container">
        <div id="uiTime" class="jp-progress" @click="seekPercent($event)">
            <div class="jp-seek-bar" ref="timeBuffer"></div>
            <div class="jp-play-bar" ref="timeBar"></div>
        </div>
        <div id="uiFooter">
            <div class="play-info" style="float: left;">
                <div style="display: inline-block">
                    <img style="width: 65px;height: 65px;" :src="crtSong.SmallPic"/>
                </div>
                <div style="display: inline-block;height: 65px;vertical-align: top;padding-left: 10px;">
                    <div style="line-height: 18px"><span>{{current}}</span></div>
                    <div style="font-size: 16px;color: #333F48;line-height: 26px;">{{crtSong.SongName}}</div>
                    <div style="font-size: 12px;color: #333F48;line-height: 20px;">{{crtSong.ArtistName}}</div>
                </div>
            </div>

            <div class="play-control">
                <div style="line-height: 18px;display:inline-block;position: absolute;right: 15px;">
                    <span>{{end}}</span>
                </div>

                <div class="ic-list">
                    <i class="iconfont icon-yinleliebiao btn-playlist"></i>
                    <span style="font-size: 12px;margin-left: -7px;">25</span>
                </div>

                <span style="margin: 0 6px;" @click="qualityMode++;qualityMode%=3">
                    <i :class="qualityStr"></i>
                </span>

                <span @click="crtPlayMode++;crtPlayMode%=3">
                    <i :class="playModeStr"></i>
                </span>
                <span @click="volume > 0 ? (volume = 0) : (volume = lastVolume);">
                    <i :class="volumeStr"></i>
                </span>
                <div style="display: inline-block;width: 120px;height: 65px;vertical-align: middle;">
                    <Slider v-model="volume" :tip-format="nullFormat" style="padding: 10px 16px 11px 2px;"></Slider>
                </div>
            </div>
            <div class="player-controls">
                <a class="jp-previous" @click="prevSong">
                    <i class="ivu-icon ivu-icon-ios-skipbackward"></i>
                </a>
                <a class="jp-play" :style="{display: !play ? 'inline-block' : 'none'}" @click="playMusic">
                    <i class="ivu-icon ivu-icon-play" style="padding-left:5px;"></i>
                </a>
                <a class="jp-pause" :style="{display: play ? 'inline-block' : 'none'}" @click="pauseMusic">
                    <i class="ivu-icon ivu-icon-ios-pause"></i>
                </a>
                <a class="jp-next" @click="nextSong(true)">
                    <i class="ivu-icon ivu-icon-ios-skipforward"></i>
                </a>
            </div>
        </div>
        <audio @canplay="audioInit" id="player" ref="player"
               preload="auto" @progress="initBuffer"
               @ended="ended" @error="errorLoad" style="display: none" :src="playLink" controls></audio>
    </div>

</template>

<script>
    define(["vue","vue-resource"], function(Vue,VueResource) {
        Vue.use(VueResource);

        return Vue.component("v-footer", {
            template: template,
            properties:{

            },
            data: function() {
                return {
                    myPlaylist: [],
                    playModeStr: "ivu-icon ivu-icon-arrow-swap",
                    progress: 0,
                    volume: 98,
                    lastVolume:98,
                    volumeStr: "ivu-icon ivu-icon-android-volume-up",
                    qualityStr: "iconfont icon-hq16",
                    qualityMode: 1,
                    playIndex: -1,
                    crtPlayMode: 0,
                    play: false,
                    current: '00:00',
                    end: '00:00',
                    max: 100,
                    update: '',
                    drag: false,
                    listShow: false,
                    playLink: "",
                    crtSong: {
                        SongId: "34183385",
                        SongName: "The road",
                        SongSubName: null,
                        ArtistId: "1074042",
                        ArtistName: "Noicybino",
                        ArtistSubName: "",
                        AlbumId: "3276152",
                        AlbumName: "L.A.N.E",
                        AlbumSubName: null,
                        AlbumArtist: "Noicybino",
                        SongLink: "http://music.163.com/#/song?id=34183385",
                        Length: 223,
                        BitRate: "320K",
                        FlacUrl: "",
                        ApeUrl: "",
                        WavUrl: "",
                        SqUrl: "http://itwusun.com/files/music/wy_320_34183385.mp3?sign=d2c2a69cfbcec60dbf63dfc85ddf78ee",
                        HqUrl: "http://itwusun.com/files/music/wy_160_34183385.mp3?sign=9416f53709c91b99e766ccb56e3c6b14",
                        LqUrl: "http://itwusun.com/files/music/wy_128_34183385.mp3?sign=0cbf6ce8df2976c6ad29bcec7b61a587",
                        CopyUrl: "http://itwusun.com/files/music/wy_320_34183385.mp3?sign=d2c2a69cfbcec60dbf63dfc85ddf78ee",
                        SmallPic: "http://p4.music.126.net/vlzoqJCwXGIqtZTD0uDRhQ==/17885755649212522.jpg?param=100y100",
                        PicUrl: "http://p4.music.126.net/vlzoqJCwXGIqtZTD0uDRhQ==/17885755649212522.jpg",
                        LrcUrl: "http://itwusun.com/files/music/wy_320_34183385.lrc?sign=93a7e10af1114c987eeef9596b3be0c8",
                        TrcUrl: "http://itwusun.com/files/music/wy_320_34183385.trc?sign=2846a934e45eab5c97eb1f825c349904",
                        KrcUrl: "http://itwusun.com/files/music/wy_320_34183385.krc?sign=067c125b69311abee7f9c7bcaabb6809",
                        Disc: 1,
                        TrackNum: 1,
                        Type: "wy"
                    },
                    search:{
                        keyword: "Rune Foshaug",
                        size: 50,
                        page: 1,
                        type: "xm"
                    }
                };
            },
            mounted: function() {
                this.initPlayer();
                let url = "https://itwusun.com/api/music/search?sign=50d4dc8552623f10422f030ffb5ffd0d&k=" + encodeURIComponent(this.search.keyword) + "&s=" + this.search.size + "&p=" + this.search.page + "&t=" + this.search.type;
                this.$http.jsonp(url).then(function (resp) {
                    this.myPlaylist = resp.data;
                    if(this.myPlaylist.length > 0){
                        this.playIndex = 0;
                    }
                }).catch(function (resp) {
                    console.log(resp.data);
                });
            },
            methods: {
                nullFormat : function () {
                    return null;
                },
                initPlayer: function() {
                    this.$refs.timeBar.style.width = 0;
                    this.$refs.timeBuffer.style.width = 0;
                },
                initBuffer: function () {
                    let timeRanges = this.$refs.player.buffered;
                    if(timeRanges.length > 0){
                        this.$refs.timeBuffer.style.width = timeRanges.end(0) * 100.0 / this.$refs.player.duration + "%";
                    }
                },
                audioInit: function () {
                    let duration = this.$refs.player.duration;
                    this.end = Vue.options.filters.timeToStr(duration);
                    this.max = duration;
                },
                getCurrent: function () {
                    let currentTime = this.$refs.player.currentTime;
                    this.current = Vue.options.filters.timeToStr(currentTime);
                    this.progress = Number.parseInt(currentTime, 10);
                    this.$refs.timeBar.style.width = currentTime*100.0/this.max + "%";
                },
                playMusic: function () {
                    if (this.playLink.toLowerCase().indexOf("http") < 0) {
                        return;
                    }
                    this.update = setInterval(this.getCurrent, 1000 / 200);
                    this.$refs.player.play();
                    this.play = true;
                },
                pauseMusic: function () {
                    clearInterval(this.update);
                    this.$refs.player.pause();
                    this.play = false;
                },
                ended: function () {
                    clearInterval(this.update);
                    this.play = false;
                    this.progress = 0;
                    this.nextSong(false);
                },
                errorLoad: function () {
                    this.$refs.player.pause();
                    this.play = false;
                    console.log("播放失败!");
                },
                setShow: function (show) {
                    this.listShow = show;
                },
                seekPercent: function (e) {
                    let clientX = e.clientX;
                    let clientWidth = e.currentTarget.clientWidth;
                    this.$refs.player.currentTime = clientX * this.max / clientWidth;
                    this.$refs.timeBar.style.width = clientX / clientWidth + "%";
                },
                nextSong: function (b) {
                    let len = this.myPlaylist.length;
                    if (len <= 0) {
                        return;
                    }
                    switch (this.crtPlayMode) {
                        case 0:
                            this.playIndex++;
                            if (this.playIndex >= len) {
                                this.playIndex = 0;
                            }
                            break;
                        case 1:
                            this.playIndex = Math.floor(Math.random() * len);
                            break;
                        case 2:
                            if (b) {
                                this.playIndex++;
                                if (this.playIndex >= len) {
                                    this.playIndex = 0;
                                }
                            }
                            break;
                    }
                    this.play = true;
                },
                prevSong: function () {
                    let len = this.myPlaylist.length;
                    if (len <= 0) {
                        return;
                    }
                    switch (this.crtPlayMode) {
                        case 0:
                        case 2:
                            this.playIndex--;
                            if (this.playIndex <= -1) {
                                this.playIndex = len-1;
                            }
                            break;
                        case 1:
                            this.playIndex = Math.floor(Math.random() * len);
                            break;
                    }
                    this.play = true;
                }
            },
            watch: {
                progress: function (newValue, oldValue) {
                    if (Math.abs(newValue - oldValue) > 1) {
                        this.current = Vue.options.filters.timeToStr(newValue);
                    }
                },
                volume: function (newValue, oldValue) {
                    this.lastVolume = oldValue;
                    if(newValue === 0){
                        this.volumeStr = "ivu-icon ivu-icon-android-volume-off";
                    }
                    if(oldValue === 0 && newValue > 0){
                        this.volumeStr = "ivu-icon ivu-icon-android-volume-up";
                    }
                    this.$refs.player.volume = newValue / 100;
                },
                playIndex:function (newValue, oldValue) {
                    if(newValue !== oldValue){
                        if(newValue >=0 && this.myPlaylist.length-1){
                            this.$refs.player.pause();
                            this.crtSong = this.myPlaylist[newValue];
                            if(this.play){
                                setTimeout(this.playMusic,1000);
                            }
                        }
                    }
                },
                crtSong: function (newVal) {
                    let tmpLink = newVal.CopyUrl;
                    if(newVal.SqUrl.indexOf('http') >= 0) {
                        tmpLink = newVal.SqUrl;
                    }
                    else if(newVal.HqUrl.indexOf('http') >= 0) {
                        tmpLink = newVal.HqUrl;
                    }
                    this.playLink = tmpLink;
                },
                crtPlayMode: function (newVal) {
                    if(newVal === 0){
                        this.playModeStr = "ivu-icon ivu-icon-arrow-swap";
                    }
                    else if(newVal === 1){
                        this.playModeStr = "ivu-icon ivu-icon-shuffle";
                    }
                    else if(newVal === 2){
                        this.playModeStr = "ivu-icon ivu-icon-loop";
                    }
                },
                qualityMode: function (newVal) {
                    if(newVal === 0){
                        this.qualityStr = "iconfont icon-LQ_";
                    }
                    else if(newVal === 1){
                        this.qualityStr = "iconfont icon-hq16";
                    }
                    else if(newVal === 2){
                        this.qualityStr = "iconfont icon-sq16";
                    }
                }
            }
        });
    });
</script>
