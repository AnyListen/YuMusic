<template>
    <Menu theme="light" id="navLeft">
        <Menu-group title="发现音乐">
            <Menu-item name="mFind">
                <i class="fa fa-calendar"></i>今日推荐
            </Menu-item>
            <Menu-item name="mFM">
                <i class="fa fa-rss"></i></Icon>鱼声精选
            </Menu-item>
        </Menu-group>
        <Menu-group title="我的音乐">
            <Menu-item name="mPlay">
                <i class="fa fa-play"></i>正在播放
            </Menu-item>
            <Menu-item name="mHistory">
                <i class="fa fa-history" style="font-size: 17px;"></i>最近播放
            </Menu-item>
            <Menu-item name="mFav">
                <i class="fa fa-heart-o" style="font-weight: 800;"></i>我的收藏
            </Menu-item>
            <Menu-item name="mLocal">
                <i class="fa fa-desktop"></i>本地音乐
            </Menu-item>
            <Menu-item name="mDown">
                <i class="fa fa-download"></i>下载管理
            </Menu-item>
        </Menu-group>
        <Submenu name="3" class="playlist">
            <template slot="title">
                播放列表
            </template>
            <Menu-item name="3-1">
                <i class="fa fa-music"></i>播放列表1
            </Menu-item>
            <Menu-item name="3-2">
                <i class="fa fa-music"></i>播放列表2
            </Menu-item>
            <Menu-item name="3-3">
                <i class="fa fa-music"></i>播放列表2
            </Menu-item>
            <Menu-item name="3-4">
                <i class="fa fa-music"></i>播放列表2
            </Menu-item>
            <Menu-item name="3-5">
                <i class="fa fa-music"></i>播放列表2
            </Menu-item>
            <Menu-item name="3-6">
                <i class="fa fa-music"></i>播放列表2
            </Menu-item>
            <Menu-item name="3-7">
                <i class="fa fa-music"></i>播放列表2
            </Menu-item>
            <Menu-item name="3-8">
                <i class="fa fa-music"></i>播放列表2
            </Menu-item>
        </Submenu>
    </Menu>
</template>

<script>
    define(["vue"], function(Vue) {
        return Vue.component("v-left", {
            template: template,
            data: function() {
                return {
                    now: new Date()
                };
            }
        });
    });
</script>
