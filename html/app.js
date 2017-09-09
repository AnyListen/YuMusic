
requirejs.config({
    paths: {
        "vue": "./lib/vue/vue.min",
        'iview': './lib/iview/iview.min',
        'vue-router': './lib/vue/vue-router.min',
        "vue-resource": './lib/vue/vue-resource.min',
        "vuejs": "./lib/require/require-vuejs.min",
        "base64": './lib/base64/base64.min'
    },
    shim: {
        "vue": {exports: "Vue"},
        "vue-resource": ["vue"],
    }
});

require(["vue", "iview", "vue-router"], function(Vue,iview,VueRouter){
    Vue.use(iview);
    Vue.use(VueRouter);

    Vue.filter('durationToTime', value => {
        let min = Number.parseInt(value / (60 * 1000), 10) + '';
        let seconds = Number.parseInt((value / 1000) % 60, 10) + '';
        min = min.length === 1 ? ('0' + min) : min;
        seconds = seconds.length === 1 ? ('0' + seconds) : seconds;
        return min + ':' + seconds;
    });

    Vue.filter('playCount', value => {
        value += '';
        if (value.length >= 6) {
            return value.substr(0, value.length - 4) + '万';
        }
        return value;
    });

    Vue.filter('unix2Time', time => {
        let date = new Date(time);
        let year = date.getFullYear();
        let month = date.getMonth();
        let day = date.getDate();
        return year + '-' + month + '-' + day;
    });

    Vue.filter('splitTags', tags => {
        if (typeof tags === 'object') {
            return tags.join('/');
        }
        return '';
    });

    Vue.filter('timeToStr', time => {
        let min = Number.parseInt(time / 60, 10) + '';
        let seconds = Number.parseInt(time % 60, 10) + '';
        min = min.length === 1 ? ('0' + min) : min;
        seconds = seconds.length === 1 ? ('0' + seconds) : seconds;
        return min + ':' + seconds;
    });

    let asyncComp = function(componentName) {
        return function(resolve) {
            require([componentName], resolve);
        };
    };

    let router = new VueRouter({routes: [
        { path: "/home" , component: asyncComp("vuejs!/demo/home")},
        { path: "/html" , component: asyncComp("vuejs!/demo/component.html")},
        { path: "/vue"  , component: asyncComp("vuejs!/demo/component")},
        { path: "/async", component: asyncComp("vuejs!/demo/async")},
    ]});

    new Vue({
        data: {

        },
        mounted:function(){

        },
        router: router,
        el: "#ui-shadow-box",
        components:{
            'v-header': asyncComp("vuejs!./views/header"),
            'v-left': asyncComp("vuejs!./views/left"),
            'v-footer': asyncComp("vuejs!./views/footer")
        }
    });
});