<#include "../header.ftl"/>

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

                    <el-col :span="24" style="font-size: 14px;">
                        <div style="padding: 4px;margin-bottom: 4px;line-height: 1; border-radius: 4px; border:1px solid #eeeeee;"> <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div></div>
                        <el-upload
                                ref="upload"
                                action="${proxyContext}${context}/image/uploadAvatar"
                                list-type="picture-card"
                                :on-preview="handlePictureCardPreview"
                                :on-remove="handleRemove"
                                multiple="true">
                            <i class="el-icon-plus"></i>
                        </el-upload>
                        <el-dialog :visible.sync="dialogVisible">
                            <img width="100%" :src="dialogImageUrl" alt="">
                        </el-dialog>

                    </el-col>
                </el-row>


            </el-main>
        </el-container>

    </el-container>

</template>

<script>
    window.vm;
    window.service = {
        
    };
    window.onload = function () {
        window.vm = new Vue({
            el: '#app',
            template: '#myComponent',
            data() {
                return {
                    dialogImageUrl: '',
                    dialogVisible: false
                }
            },
            created() {
                const that = this;
            },
            mounted() {
                const that = this;
            },
            methods: {
                submitUpload() {
                    this.$refs.upload.submit();
                },
                handleRemove(file, fileList) {
                    console.log(file, fileList);
                },
                handlePictureCardPreview(file) {
                    this.dialogImageUrl = file.url;
                    this.dialogVisible = true;
                }
            }
        })
    }
</script>
</body>
</html>