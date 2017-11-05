<template>
    <div id="ui-sys-bar" onmousedown="if(event.button !== 2){ iMusic.dragEnable() }" onmouseup="iMusic.dragDisable()" ondblclick="iMusic.maxForm()">
        <div style="display: inline-block; cursor: pointer;height: 58px;">
            <img src="./img/logo.png" class="fish-logo" />
        </div>

        <div id="ui-sys-commands">
            <span onclick="iMusic.minForm();" onmousedown="event.stopPropagation()">
                <i class="ivu-icon ivu-icon-minus-round"></i>
            </span>
            <span onclick="iMusic.maxForm()" onmousedown="event.stopPropagation()">
                <i class="fa fa-square-o"></i>
            </span>
            <span onclick="iMusic.closeForm()" onmousedown="event.stopPropagation()">
                <i class="ivu-icon ivu-icon-close-round"></i>
            </span>
        </div>
    </div>
</template>

<script>
    define(["vue"], function(Vue) {
        return Vue.component("v-header", {
            template: template,
            data: function() {
                return {
                    now: new Date()
                };
            }
        });
    });
</script>
