# YuMusic
A Music Player Build with JavaFX WebView, iView, RequireJs

## 更新说明

### 2017-09-??
- 完成搜索；
- 完成playlist；
- 完善播放栏。


### 2017-09-04
- 使用RequireJs进行重构整体项目；实现组件的简单分离；
- 移除jPlayer，使用H5原生audio进行播放；
- 修复javaFX跨站加载问题。

## 开发经验
1. RequireJs与iview集成的时候，需要注意iview源码'factory(require("vue"))',因此对于VUEJS的定义需要是小写，详见'app.js'；
2. javaFX的webview类似浏览器，因此不接受xhr的跨域请求，因此在[require-vuejs]('https://github.com/edgardleal/require-vuejs')代码中异步请求.vue文件时候会报错，解决方法是在js中调用java代码；
3. 在使用vue的$refs的时候，如果组件想调用自己模板的dom元素，则该元素除了加ref标签之外，还必须满足的条件是：**不能处于模板的根节点**；