
<template>
    <el-tabs   type="border-card" >
        <el-tab-pane label="重新上传">
            <div>
                <el-upload
                        class="upload-demo"
                        action="${proxyContext}${context}/image/uploadAvatar"
                        :on-preview="handlePreview"
                        :on-remove="handleRemove"
                        :file-list="fileList2"
                        :on-success="uploadSuccess"
                        :disabled="disabledUpload"
                        multiple="false"
                        list-type="picture">
                    <el-button size="small" type="primary">点击上传</el-button>
                    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                </el-upload>
            </div>
        </el-tab-pane>
        <el-tab-pane label="从图库选择" >
            <div style="text-align: left;">
                <imagecenter @choose-image="chooseImage"></imagecenter>
            </div>
        </el-tab-pane>
    </el-tabs>
</template>

