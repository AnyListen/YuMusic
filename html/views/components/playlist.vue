<template>
    <Table class="playlist" border :columns="columns" :data="songs" size="small" :show-header="showHeader"
           @on-row-dblclick="playSong"></Table>
</template>

<script>
    define(["vue"], function(Vue) {
        return Vue.component("v-playlist", {
            template: template,
            props:{
                songs : []
            },
            data: function() {
                return {
                    showHeader: false,
                    columns:[
                        {
                            type: 'selection',
                            width: 60,
                            align: 'center'
                        },
                        {
                            title: '曲名',
                            key: 'SongName',
                            ellipsis: true
                        },
                        {
                            title: '艺术家',
                            key: 'ArtistName',
                            ellipsis: true
                        },
                        {
                            title: '专辑',
                            key: 'AlbumName',
                            ellipsis: true
                        }
                    ]
                };
            },
            methods: {
                playSong: function (song, index) {
                    this.$root.$emit('play-song', song);
                }
            }
        });
    });
</script>
