<#include "../header.ftl"/>
<style>
    .card-image{
        width: 100%;
        height:300px;
        display: block;
        background-position: center;
        background-size: cover;
        background-repeat: no-repeat;
    }
</style>
</head>

<body>

<div id="app"></div>

<template id="myComponent">
    <el-container style="height: 100%; border: 1px solid #eee">
    <#include "../leftbar.ftl"/>

        <el-container>
        <#include "../nav.ftl"/>

            <el-main>
                <el-row>
                    <el-col :span="4" v-for="(o, index) in images" style="margin-bottom: 14px;margin-right: 14px;">
                        <el-card shadow="hover"
                                 :body-style="{ padding: '0px' }">
                            <div   style="background-image:url(${proxyContext}${context}/image/getAvatar?avatarName={{o}})" class="card-image"></div>
                            <div style="padding: 14px">
                                <div style="height: 64px;overflow: hidden;word-break: break-all;text-overflow: ellipsis;">
                                    <span>{{o}}</span>
                                </div>
                                <div class="bottom clearfix" style='text-align:left;'>
                                    <el-button type="text" class="button" style='padding: 0;' @click="removeImage(o)">删除
                                    </el-button>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
            </el-main>
        </el-container>

    </el-container>

</template>

<script>
    window.vm;
    window.service = {};
    window.onload = function () {
        window.vm = new Vue({
            el: '#app',
            template: '#myComponent',
            data() {
                return {
                    currentDate: new Date(),
                    images: []
                }
            },
            created() {
                const that = this;
                axios.get('${proxyContext}${context}/image/listAllAvatarNames').then(function (response) {
                    that.images = response.data.data;
                });
            },
            mounted() {
                const that = this;
            },
            methods: {
                removeImage(image) {
                    console.log(image);
                    var that = this;
                    axios.post(window.ctxPath + '/image/deleteImage?imageName=' + image).then(function (response) {
                        if(response.data.data){
                            that.$message.success('删除成功');
                            axios.get('${proxyContext}${context}/image/listAllAvatarNames').then(function (response) {
                                that.images = response.data.data;
                            });
                        }
                    });
                }
            }
        })
    }
</script>
</body>
</html>