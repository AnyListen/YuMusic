<template>
    <div id="jp_container">
        <div id="uiTime" class="jp-progress" @click="seekPercent($event)">
            <div class="jp-seek-bar" ref="timeBuffer"></div>
            <div class="jp-play-bar" ref="timeBar"></div>
        </div>
        <div id="uiFooter">
            <div class="play-info" style="float: left;">
                <div style="display: inline-block">
                    <img style="width: 65px;height: 65px;" src="http://pic.xiami.net/images/album/img58/613758/6137581427613758.jpg"/>
                </div>
                <div style="display: inline-block;height: 65px;vertical-align: top;padding-left: 10px;">
                    <div style="line-height: 18px"><span>{{current}}</span></div>
                    <div style="font-size: 16px;color: #333F48;line-height: 26px;">Little Star</div>
                    <div style="font-size: 12px;color: #333F48;line-height: 20px;">Rune Foshaug</div>
                </div>
            </div>

            <div class="play-control" style="float: right;">
                <div style="line-height: 18px;display:inline-block;position: absolute;right: 10px;">
                    <span>{{end}}</span>
                </div>
            </div>

            <div class="player-controls">
                <a class="jp-previous">
                    <i class="ivu-icon ivu-icon-ios-skipbackward"></i>
                </a>
                <a class="jp-play" :style="{display: !play ? 'inline-block' : 'none'}" @click="playMusic">
                    <i class="ivu-icon ivu-icon-play" style="padding-left:5px;"></i>
                </a>
                <a class="jp-pause" :style="{display: play ? 'inline-block' : 'none'}" @click="pauseMusic">
                    <i class="ivu-icon ivu-icon-ios-pause"></i>
                </a>
                <a class="jp-next">
                    <i class="ivu-icon ivu-icon-ios-skipforward"></i>
                </a>
            </div>
        </div>
        <audio @canplay="audioInit" id="player" ref="player"
               preload="auto" @progress="initBuffer"
               @ended="ended" @error="errorLoad" style="display: none" :src="mp3Url" controls></audio>
    </div>

</template>

<script>
    define(["vue"], function(Vue) {
        return Vue.component("v-footer", {
            template: template,
            data: function() {
                return {
                    myPlaylist: null,
                    progress: 0,
                    volume: 100,
                    play: false,
                    current: '00:00',
                    end: '00:00',
                    max: 100,
                    update: '',
                    drag: false,
                    listShow: false,
                    mp3Url: "http://itwusun.com/files/music/xm_320_1773660193.mp3?sign=7a1a65ae28a5b4b358b4ad5314f0ae64"
                };
            },
            mounted: function() {
                this.initPlayer();
            },
            methods: {
                initPlayer: function() {
                    this.$refs.timeBar.style.width = 0;
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
                    if (!this.mp3Url) {
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
                },
                errorLoad: function () {
                    this.play = false;
                },
                setShow: function (show) {
                    this.listShow = show;
                },
                seekPercent: function (e) {
                    let clientX = e.clientX;
                    let clientWidth = e.currentTarget.clientWidth;
                    this.$refs.player.currentTime = clientX * this.max / clientWidth;
                    this.$refs.timeBar.style.width = clientX / clientWidth + "%";
                }
            },
            watch: {
                progress: function (newValue, oldValue) {
                    if (Math.abs(newValue - oldValue) > 1) {
                        this.current = Vue.options.filters.timeToStr(newValue);
                    }
                },
                volume: function (newValue) {
                    this.$refs.player.volume = newValue / 100;
                }
            }
        });
    });
</script>
