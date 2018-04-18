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
                        <span>选择门店：</span>
                        <el-dropdown @command="handleCommand">
                          <span class="el-dropdown-link" style="margin-left: 14px;margin-right: 14px;">
                            {{selectedShop?selectedShop.name:"下拉菜单"}}<i class="el-icon-arrow-down el-icon--right"></i>
                          </span>
                            <el-dropdown-menu slot="dropdown">
                            <#list shops as shop>
                                <el-dropdown-item
                                        command="${shop.id},${shop.name},<#if shop.payRate??>${shop.payRate}</#if>">${shop.name}</el-dropdown-item>
                            </#list>
                            </el-dropdown-menu>
                        </el-dropdown>

                    </el-col>
                </el-row>

                <el-row style="margin-top: 14px;">

                    <el-col :span="24">
                        <el-collapse>
                            <el-collapse-item title="点击搜索" name="1">
                                <el-form :inline="true" :model="search" class="demo-form-inline">
                                    <el-form-item label="订单编号">
                                        <el-input v-model="search.orderNo"
                                                  placeholder="orderNo"></el-input>
                                    </el-form-item>
                                    <el-form-item label="订单状态">
                                    <#--<el-input v-model="search.orderStatus"-->
                                    <#--placeholder="orderStatus"></el-input>-->
                                        <el-dropdown @command="handleOrderStatus">
                                                  <span class="el-dropdown-link"
                                                        style="margin-left: 14px;margin-right: 14px;">
                                                    {{search.orderStatus | orderStauts}}<i
                                                          class="el-icon-arrow-down el-icon--right"></i>
                                                  </span>
                                            <el-dropdown-menu slot="dropdown">
                                                <el-dropdown-item command="1">
                                                    付款成功
                                                </el-dropdown-item>
                                                <el-dropdown-item command="2">
                                                    等待支付
                                                </el-dropdown-item>
                                                <el-dropdown-item command="3">
                                                    取消
                                                </el-dropdown-item>
                                            </el-dropdown-menu>
                                        </el-dropdown>
                                    </el-form-item>
                                    <el-form-item label="订单类型">
                                        <#--<el-input v-model="search.orderType"-->
                                                  <#--placeholder="orderType"></el-input>-->
                                            <el-dropdown @command="handleOrderType">
                                                  <span class="el-dropdown-link"
                                                        style="margin-left: 14px;margin-right: 14px;">
                                                    {{search.orderType | orderType}}<i
                                                          class="el-icon-arrow-down el-icon--right"></i>
                                                  </span>
                                                <el-dropdown-menu slot="dropdown">
                                                    <el-dropdown-item command="1">
                                                        即时
                                                    </el-dropdown-item>
                                                    <el-dropdown-item command="2">
                                                        预约
                                                    </el-dropdown-item>
                                                </el-dropdown-menu>
                                            </el-dropdown>
                                    </el-form-item>
                                    <el-form-item label="联系方式">
                                        <el-input v-model="search.concatPhone"
                                                  placeholder="concatPhone"></el-input>
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
                                        <el-button type="primary" @click="onDownloadExcel">下载excel<i
                                                class="el-icon-download el-icon--right"></i></el-button>
                                    </el-form-item>
                                </el-form>
                            </el-collapse-item>
                        </el-collapse>
                    </el-col>
                </el-row>

                <el-row style="margin-top: 14px;">
                    <el-col :span="24">
                        <div class="grid-content bg-purple-dark">
                        <#--<el-button type="primary" icon="el-icon-plus" @click="onInsert">新增</el-button>
                        <el-button type="primary" icon="el-icon-edit" @click="onUpdate">编辑</el-button>-->
                            <#--<el-button type="primary" icon="el-icon-delete" @click="onDelete">删除</el-button>-->
                        <#--<el-button type="primary">上传<i class="el-icon-upload el-icon--right"></i></el-button>-->
                        </div>
                    </el-col>
                </el-row>
                <el-row style="margin-top: 14px;">
                    <el-col :span="24">
                        <el-table :data="tableData" border="true" stripe="true" @selection-change="onSelectTableData">
                        <#--<el-table-column-->
                        <#--type="selection"-->
                        <#--width="55">-->
                        <#--</el-table-column>-->

                        <#--<el-table-column label="id" index="5">-->
                        <#--<template slot-scope="scope">-->
                        <#--<span style="margin-left: 10px">{{  scope.row.id }}</span>-->
                        <#--</template>-->
                        <#--</el-table-column>-->
                            <el-table-column label="订单编号" index="0">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.orderNo }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="订单状态" index="1">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.orderStatus | orderStauts}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="订单总价" index="3">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.totalPrice }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="费率%" index="9">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px" v-if="selectedShop">{{selectedShop.payRate}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="分成" index="10">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px" v-if="selectedShop">{{  (scope.row.totalPrice * selectedShop.payRate)/100 }}</span>
                                </template>
                            </el-table-column>

                            <el-table-column label="微信订单号" index="11">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.transactionId }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="订单类型" index="7">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.orderType | orderType}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="预约时间" index="6">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.orderTime }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="联系方式" index="8">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.concatPhone }}</span>
                                </template>
                            </el-table-column>

                            <el-table-column label="创建时间" index="4">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.createTime | formatDate }}</span>
                                </template>
                            </el-table-column>
                        <#--<el-table-column label="修改时间" index="2">-->
                        <#--<template slot-scope="scope">-->
                        <#--<span style="margin-left: 10px">{{  scope.row.updateTime | formatDate}}</span>-->
                        <#--</template>-->
                        <#--</el-table-column>-->
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
                <el-form-item label="订单编号">
                    <el-input v-model="form.orderNo">{{form.orderNo}}</el-input>
                </el-form-item>
                <el-form-item label="订单状态">
                    <el-input v-model="form.orderStatus">{{form.orderStatus | orderStauts}}</el-input>
                </el-form-item>
                <el-form-item label="修改时间">
                    <el-input v-model="form.updateTime">{{form.updateTime}}</el-input>
                </el-form-item>
                <el-form-item label="订单总价">
                    <el-input v-model="form.totalPrice">{{form.totalPrice}}</el-input>
                </el-form-item>
                <el-form-item label="创建时间">
                    <el-input v-model="form.createTime">{{form.createTime}}</el-input>
                </el-form-item>
                <el-form-item label="id">
                    <el-input v-model="form.id">{{form.id}}</el-input>
                </el-form-item>
                <el-form-item label="预约时间">
                    <el-input v-model="form.orderTime">{{form.orderTime}}</el-input>
                </el-form-item>
                <el-form-item label="订单类型">
                    <el-input v-model="form.orderType">{{form.orderType}}</el-input>
                </el-form-item>
                <el-form-item label="联系方式">
                    <el-input v-model="form.concatPhone">{{form.concatPhone}}</el-input>
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
                <el-form-item label="订单编号">
                    <el-input v-model="form.orderNo">{{form.orderNo}}</el-input>
                </el-form-item>
                <el-form-item label="订单状态">
                    <el-input v-model="form.orderStatus">{{form.orderStatus}}</el-input>
                </el-form-item>
                <el-form-item label="修改时间">
                    <el-input v-model="form.updateTime">{{form.updateTime}}</el-input>
                </el-form-item>
                <el-form-item label="订单总价">
                    <el-input v-model="form.totalPrice">{{form.totalPrice}}</el-input>
                </el-form-item>
                <el-form-item label="创建时间">
                    <el-input v-model="form.createTime">{{form.createTime}}</el-input>
                </el-form-item>
                <el-form-item label="id">
                    <el-input v-model="form.id">{{form.id}}</el-input>
                </el-form-item>
                <el-form-item label="预约时间">
                    <el-input v-model="form.orderTime">{{form.orderTime}}</el-input>
                </el-form-item>
                <el-form-item label="订单类型">
                    <el-input v-model="form.orderType">{{form.orderType}}</el-input>
                </el-form-item>
                <el-form-item label="联系方式">
                    <el-input v-model="form.concatPhone">{{form.concatPhone}}</el-input>
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
            return axios.post(window.ctxPath + '/order/add', form);
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
            return axios.get(window.ctxPath + '/order/listPage?pageNo=' + pageNo + "&pageSize=" + pageSize + paramString);
        },
        downloadExcel: function (like) {
            var paramString = "";
            if (like != undefined) {
                for (var x in like) {
                    if (like.hasOwnProperty(x)) {
                        paramString += "&" + x + "=" + encodeURI(like[x])
                    }
                }
            }
            console.log("paramString", paramString);
            return window.ctxPath + '/order/exportExcel?' + paramString;
        },
        update: function (form) {
            return axios.post(window.ctxPath + '/order/update', form);
        },
        delete: function (id) {
            return axios.post(window.ctxPath + '/order/remove?id=' + id);
        },
        deleteList: function (ids) {
            return axios.post(window.ctxPath + '/order/removeList', ids);
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
                    currentPage: 1,
                    selectedShop: null,
                }
            },
            created() {
                const that = this;
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
                    if (that.selectionChanges.length == 0) {
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
                onDownloadExcel() {
                    const that = this;
                    alert(1);
                    window.open(window.service.downloadExcel(that.search));
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
                handleOrderStatus(val){
                    const that = this;
                    Vue.set(this.search,"orderStatus",val);
                },
                handleOrderType(val){
                    const that = this;
                    Vue.set(this.search,"orderType",val);
                },
                handleCommand(val) {
                    var vars = val.split(",");
                    const that = this;
                    this.selectedShop = {
                        id: vars[0],
                        name: vars[1],
                        payRate: vars[2]
                    };
                    that.search.shopId = this.selectedShop.id;
                    window.service.listPage(1, 10, that.search).then(function (response) {
                        that.tableData = response.data.data;
                        that.currentPage = response.data.pageNo;
                        that.totalPage = response.data.countSize;
                    });
                    console.log("val", vars);
                },
            }
        })
    }
</script>
</body>
</html>