<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>element-starter</title>
        <link rel="stylesheet" type="text/css" href="/dist/cssnormalize.css">
        <link rel="stylesheet" href="/dist/element-ui.css">
        <script src="/dist/vue.js"></script>
        <script src="/dist/axios.js"></script>
        <script src="/dist/element-ui.js"></script>
        <script >
            window.ctxPath = "";
        </script>
        <style>
            html, body {
                height: 100%;
            }

            @font-face {
                font-family: element-icons;
                src: url('/dist/6f0a76321d30f3c8120915e57f7bd77e.ttf'),
                url('/dist/6f0a76321d30f3c8120915e57f7bd77e.ttf'); /* IE9+,可以是具体的实际链接 */
            }

            .el-header {
                background-color: #B3C0D1;
                color: #333;
                line-height: 60px;
            }

            .el-aside {
                color: #333;
            }

            #app {
                font-family: Helvetica, sans-serif;
                text-align: center;
            }
        </style>
    </head>

    <body>

        <div id="app"></div>

        <template id="myComponent">
            <el-container style="height: 100%; border: 1px solid #eee">
                <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
                    <el-menu :default-openeds="['1']">
                        <el-submenu index="1">
                            <template slot="title"><i class="el-icon-message"></i>后台</template>
                        </el-submenu>
                    </el-menu>
                </el-aside>

                <el-container>
                    <el-header style="text-align: right; font-size: 12px">
                        <el-dropdown>
                            <i class="el-icon-setting" style="margin-right: 15px"></i>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item>查看</el-dropdown-item>
                                <el-dropdown-item>新增</el-dropdown-item>
                                <el-dropdown-item>删除</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                        <span>王小虎</span>
                    </el-header>

                    <el-main>
                        <el-row>
                            <el-col :span="24">
                                <el-collapse>
                                    <el-collapse-item title="点击搜索" name="1">
                                        <el-form :inline="true" :model="search" class="demo-form-inline">
                                                    <el-form-item label="update_time">
                                                        <el-input v-model="search.updateTime"
                                                                  placeholder="updateTime"></el-input>
                                                    </el-form-item>
                                                    <el-form-item label="pid">
                                                        <el-input v-model="search.pid"
                                                                  placeholder="pid"></el-input>
                                                    </el-form-item>
                                                    <el-form-item label="id">
                                                        <el-input v-model="search.id"
                                                                  placeholder="id"></el-input>
                                                    </el-form-item>
                                                    <el-form-item label="create_time">
                                                        <el-input v-model="search.createTime"
                                                                  placeholder="createTime"></el-input>
                                                    </el-form-item>
                                                    <el-form-item label="spec_id">
                                                        <el-input v-model="search.specId"
                                                                  placeholder="specId"></el-input>
                                                    </el-form-item>
                                            <el-form-item>
                                                <el-button type="primary" @click="onSearch">查询</el-button>
                                            </el-form-item>
                                        </el-form>
                                    </el-collapse-item>
                                </el-collapse>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="24">
                                <div class="grid-content bg-purple-dark">
                                    <el-button type="primary" icon="el-icon-plus" @click="onInsert">新增</el-button>
                                    <el-button type="primary" icon="el-icon-edit" @click="onUpdate">编辑</el-button>
                                    <el-button type="primary" icon="el-icon-delete" @click="onDelete">删除</el-button>
                                    <el-button type="primary">上传<i class="el-icon-upload el-icon--right"></i></el-button>
                                    <el-button type="primary">下载excel<i class="el-icon-download el-icon--right"></i></el-button>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row style="margin-top: 14px;">
                            <el-col :span="24">
                                <el-table :data="tableData" border="true" stripe="true" @selection-change="onSelectTableData">
                                    <el-table-column
                                            type="selection"
                                            width="55">
                                    </el-table-column>
                                    <el-table-column label="update_time" index="0" >
                                        <template slot-scope="scope">
                                            <span style="margin-left: 10px">{{  scope.row.updateTime }}</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="pid" index="1" >
                                        <template slot-scope="scope">
                                            <span style="margin-left: 10px">{{  scope.row.pid }}</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="id" index="2" >
                                        <template slot-scope="scope">
                                            <span style="margin-left: 10px">{{  scope.row.id }}</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="create_time" index="3" >
                                        <template slot-scope="scope">
                                            <span style="margin-left: 10px">{{  scope.row.createTime }}</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="spec_id" index="4" >
                                        <template slot-scope="scope">
                                            <span style="margin-left: 10px">{{  scope.row.specId }}</span>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-col>

                        </el-row>

                        <el-row>
                            <el-pagination
                                    @size-change="handleSizeChange"
                                    @current-change="handleCurrentChange"
                                    :current-page="currentPage"
                                    background
                                    layout="prev, pager, next"
                                    :total="totalPage">
                            </el-pagination>
                        </el-row>

                    </el-main>
                </el-container>

                <el-dialog
                        title="新增数据"
                        :visible.sync="insertDialogVisible"
                        width="30%"
                        center>
                    <el-form ref="form" :model="form" label-width="80px">
                        <el-form-item label="update_time">
                            <el-input v-model="form.updateTime">{{form.updateTime}}</el-input>
                        </el-form-item>
                        <el-form-item label="pid">
                            <el-input v-model="form.pid">{{form.pid}}</el-input>
                        </el-form-item>
                        <el-form-item label="id">
                            <el-input v-model="form.id">{{form.id}}</el-input>
                        </el-form-item>
                        <el-form-item label="create_time">
                            <el-input v-model="form.createTime">{{form.createTime}}</el-input>
                        </el-form-item>
                        <el-form-item label="spec_id">
                            <el-input v-model="form.specId">{{form.specId}}</el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="onSubmit('insert')">立即创建</el-button>
                        </el-form-item>
                    </el-form>

                </el-dialog>

                <el-dialog
                        title="修改数据"
                        :visible.sync="updateDialogVisible"
                        width="30%"
                        center>
                    <el-form ref="form" :model="form" label-width="80px">
                        <el-form-item label="update_time">
                            <el-input v-model="form.updateTime">{{form.updateTime}}</el-input>
                        </el-form-item>
                        <el-form-item label="pid">
                            <el-input v-model="form.pid">{{form.pid}}</el-input>
                        </el-form-item>
                        <el-form-item label="id">
                            <el-input v-model="form.id">{{form.id}}</el-input>
                        </el-form-item>
                        <el-form-item label="create_time">
                            <el-input v-model="form.createTime">{{form.createTime}}</el-input>
                        </el-form-item>
                        <el-form-item label="spec_id">
                            <el-input v-model="form.specId">{{form.specId}}</el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="onSubmit('update')">立即修改</el-button>
                        </el-form-item>
                    </el-form>

                </el-dialog>

            </el-container>

        </template>

        <script>
            window.vm;
            window.service = {
                add: function (form) {
                    return axios.post(window.ctxPath+'/productSpecRel/add', form);
                },
                listPage: function (pageNo,pageSize,like) {
                    var paramString = "";
                    if(like!= undefined){
                        for (var x in like){
                            if (like.hasOwnProperty(x)){
                                paramString += "&"+ x + "="+ encodeURI(like[x])
                            }
                        }
                    }
                    return axios.get(window.ctxPath + '/productSpecRel/listPage?pageNo=' + pageNo + "&pageSize=" + pageSize + paramString);
                },
                update: function (form) {
                    return axios.post(window.ctxPath+'/productSpecRel/update', form);
                },
                delete: function (id) {
                    return axios.post(window.ctxPath+'/productSpecRel/remove?id=' + id);
                },
                deleteList: function (ids) {
                    return axios.post(window.ctxPath+'/productSpecRel/removeList', ids);
                },
                goTo: function (model) {
                    window.location.href = window.ctxPath+"/"+model+"/view?"   ;
                }
            };
            window.onload = function () {
                window.vm = new Vue({
                    el: '#app',
                    template: '#myComponent',
                    data() {
                        return {
                            tableData: [],
                            insertDialogVisible: false,
                            updateDialogVisible: false,
                            form: {},
                            selectionChanges: [],
                            defaultWarningButton:{
                                confirmButtonText: '确定',
                                cancelButtonText: '取消',
                                type: 'warning'
                            },
                            search:{},
                            totalPage: 0 ,
                            currentPage:1
                        }
                    },
                    created() {
                        const that = this;
                        window.service.listPage(1,10,that.search).then(function (response) {
                            that.tableData = response.data.data;
                            that.currentPage = response.data.pageNo;
                            that.totalPage = response.data.countSize;
                        });
                    },
                    mounted() {
                        const that = this;
                    },
                    methods: {
                        onInsert(){
                            var that = this;
                            that.form = {};
                            that.insertDialogVisible = true;
                        },
                        onUpdate() {
                            var that = this;
                            if(that.selectionChanges.length == 0){
                                return;
                            }
                            that.form = that.selectionChanges[0];
                            that.updateDialogVisible = true;
                        },
                        onClickMenu(model) {
                            window.service.goTo(model);
                        },
                        onSelectTableData(val) {
                            this.selectionChanges = val;
                        },
                        onRefreshWindow(message){
                            this.$message({
                                type: 'success',
                                message: message,
                                onClose: function () {
                                    window.location.reload(true);
                                }
                            });
                        },
                        onDelete() {
                            var changes = this.selectionChanges;
                            var that = this;
                            if (changes.length > 1) {
                                that.$confirm('此操作将永久删除, 是否继续?', '提示', that.defaultWarningButton).then(() => {
                                    var ids = new Array();
                                    for (var i = 0; i < changes.length; i++) {
                                        ids.push(changes[i].id);
                                    }
                                    window.service.deleteList(ids).then(function (response) {
                                        that.onRefreshWindow('删除成功!');
                                    });
                                })

                            } else if (changes == 0) {

                            } else {
                                that.$confirm('此操作将永久删除, 是否继续?', '提示', that.defaultWarningButton).then(() => {
                                    window.service.delete(changes[0].id).then(function (response) {
                                        that.onRefreshWindow('删除成功!');
                                    });

                                })
                            }
                        },
                        onSubmit(type) {
                            var that = this;
                            if (type == 'update') {
                                window.service.update(this.form).then(function (response) {
                                    that.updateDialogVisible = false;
                                    that.onRefreshWindow('修改成功!');
                                });
                            } else if (type == 'insert') {
                                window.service.add(this.form).then(function (response) {
                                    that.insertDialogVisible = false;
                                    that.onRefreshWindow('创建成功!');
                                });
                            }
                        },
                        onSearch(){
                            const that = this;
                            window.service.listPage(1, 10,that.search).then(function (response) {
                                that.tableData = response.data.data;
                                that.currentPage = response.data.pageNo;
                                that.totalPage = response.data.countSize;
                            });
                        },
                        handleSizeChange(val) {
                            console.log('每页 '+ val+' 条');
                        },
                        handleCurrentChange(val) {
                            console.log('当前页: ' + val);
                            const that = this;
                            window.service.listPage(val,10,that.search).then(function (response) {
                                that.tableData = response.data.data;
                                that.currentPage = response.data.pageNo;
                                that.totalPage = response.data.countSize;
                            });
                        },
                    }
                })
            }
        </script>
    </body>
</html>