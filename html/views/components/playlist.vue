<template>
    <Table border :columns="columns" :data="songs" size="small" :show-header="showHeader"></Table>
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
                            key: 'SongName'
                        },
                        {
                            title: '艺术家',
                            key: 'ArtistName'
                        },
                        {
                            title: '专辑',
                            key: 'AlbumArtist'
                        }
                    ]
                };
            }
        });
    });
</script>
