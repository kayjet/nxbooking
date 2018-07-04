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
                    <el-col :span="24">
                        <el-collapse>
                            <el-collapse-item title="点击搜索" name="1">
                                <el-form :inline="true" :model="search" class="demo-form-inline">
                                    <el-form-item label="商店id">
                                        <el-input v-model="search.fkShopId"
                                                  placeholder="fkShopId"></el-input>
                                    </el-form-item>
                                    <el-form-item label="用户id">
                                        <el-input v-model="search.fkUserId"
                                                  placeholder="fkUserId"></el-input>
                                    </el-form-item>
                                    <br/>
                                    <el-form-item label="创建时间">
                                        <el-date-picker
                                                v-model="search.createTimeSearch"
                                                value-format="yyyy-MM-dd HH:mm:ss.0"
                                                type="daterange"
                                                range-separator="至"
                                                start-placeholder="开始日期"
                                                end-placeholder="结束日期">
                                        </el-date-picker>
                                    </el-form-item>
                                    <el-form-item label="修改时间">
                                        <el-date-picker
                                                v-model="search.updateTimeSearch"
                                                value-format="yyyy-MM-dd HH:mm:ss.0"
                                                type="daterange"
                                                range-separator="至"
                                                start-placeholder="开始日期"
                                                end-placeholder="结束日期">
                                        </el-date-picker>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button type="primary" @click="onSearch">查询</el-button>
                                    </el-form-item>
                                </el-form>
                            </el-collapse-item>
                        </el-collapse>
                    </el-col>
                </el-row>

                <el-row style="margin-top: 14px;">
                    <el-col :span="24">
                        <div class="grid-content bg-purple-dark">
                            <el-button type="primary" icon="el-icon-delete" @click="onDelete">删除</el-button>
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
                          <#--  <el-table-column label="id" index="2">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.id }}</span>
                                </template>
                            </el-table-column>-->
                           <#-- <el-table-column label="用户id" index="4">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.fkUserId }}</span>
                                </template>
                            </el-table-column>-->
                            <el-table-column label="收藏用户" index="4">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.userList[0].nickName }}</span>
                                </template>
                            </el-table-column>
                          <#--  <el-table-column label="商店id" index="0">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.fkShopId }}</span>
                                </template>
                            </el-table-column>-->
                            <el-table-column label="收藏商店名" index="0">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.shopList[0].name }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="修改时间" index="1">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.updateTime | formatDate}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="创建时间" index="3">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.createTime | formatDate }}</span>
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
                <el-form-item label="商店id">
                    <el-input v-model="form.fkShopId">{{form.fkShopId}}</el-input>
                </el-form-item>
                <el-form-item label="修改时间">
                    <el-input v-model="form.updateTime">{{form.updateTime}}</el-input>
                </el-form-item>
                <el-form-item label="id">
                    <el-input v-model="form.id">{{form.id}}</el-input>
                </el-form-item>
                <el-form-item label="创建时间">
                    <el-input v-model="form.createTime">{{form.createTime}}</el-input>
                </el-form-item>
                <el-form-item label="用户id">
                    <el-input v-model="form.fkUserId">{{form.fkUserId}}</el-input>
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
                <el-form-item label="商店id">
                    <el-input v-model="form.fkShopId">{{form.fkShopId}}</el-input>
                </el-form-item>
                <el-form-item label="修改时间">
                    <el-input v-model="form.updateTime">{{form.updateTime}}</el-input>
                </el-form-item>
                <el-form-item label="id">
                    <el-input v-model="form.id">{{form.id}}</el-input>
                </el-form-item>
                <el-form-item label="创建时间">
                    <el-input v-model="form.createTime">{{form.createTime}}</el-input>
                </el-form-item>
                <el-form-item label="用户id">
                    <el-input v-model="form.fkUserId">{{form.fkUserId}}</el-input>
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
            return axios.post(window.ctxPath + '/userFavShopRel/add', form);
        },
        listPage: function (pageNo, pageSize, like) {
            var paramString = "";
            if (like != undefined) {
                for (var x in like) {
                    if (like.hasOwnProperty(x)) {
                        paramString += "&" + x + "=" + encodeURI(like[x])
                    }
                }
            }
            return axios.get(window.ctxPath + '/userFavShopRel/listPage?pageNo=' + pageNo + "&pageSize=" + pageSize + paramString);
        },
        update: function (form) {
            return axios.post(window.ctxPath + '/userFavShopRel/update', form);
        },
        delete: function (id) {
            return axios.post(window.ctxPath + '/userFavShopRel/remove?id=' + id);
        },
        deleteList: function (ids) {
            return axios.post(window.ctxPath + '/userFavShopRel/removeList', ids);
        },
        goTo: function (model) {
            window.location.href = window.ctxPath + "/" + model + "/view?";
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
                    defaultWarningButton: {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    },
                    search: {},
                    totalPage: 0,
                    currentPage: 1
                }
            },
            created() {
                const that = this;
                window.service.listPage(1, 10, that.search).then(function (response) {
                    that.tableData = response.data.data;
                    that.currentPage = response.data.pageNo;
                    that.totalPage = response.data.countSize;
                });
            },
            mounted() {
                const that = this;
            },
            methods: {
                onInsert() {
                    var that = this;
                    that.form = {};
                    that.insertDialogVisible = true;
                },
                onUpdate() {
                    var that = this;
                    that.form = that.selectionChanges[0];
                    that.updateDialogVisible = true;
                },
                onClickMenu(model) {
                    window.service.goTo(model);
                },
                onSelectTableData(val) {
                    this.selectionChanges = val;
                },
                onRefreshWindow(message) {
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
                onSearch() {
                    const that = this;
                    window.service.listPage(1, 10, that.search).then(function (response) {
                        that.tableData = response.data.data;
                        that.currentPage = response.data.pageNo;
                        that.totalPage = response.data.countSize;
                    });
                },
                handleSizeChange(val) {
                    console.log('每页 ' + val + ' 条');
                },
                handleCurrentChange(val) {
                    console.log('当前页: ' + val);
                    const that = this;
                    window.service.listPage(val, 10, that.search).then(function (response) {
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