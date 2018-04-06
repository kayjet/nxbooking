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

                <el-row style="margin-top: 14px;">

                    <el-col :span="24">
                        <div>WebSocket Page</div>
                        <div id="message"></div>
                        <div>
                            <input id="text" />
                            <button id="send" onclick="send()">发送</button>
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

                            <el-table-column label="id" index="5">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.id }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="订单编号" index="0">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.orderNo }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="订单状态" index="1">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.orderStatus }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="订单总价" index="3">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.totalPrice }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="订单类型" index="7">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.orderType }}</span>
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
                            <el-table-column label="修改时间" index="2">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.updateTime | formatDate}}</span>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-col>

                </el-row>

            </el-main>
        </el-container>


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
                    websocket:null
                }
            },
            created() {
                const that = this;
//                that.websocket = new WebSocket("ws://localhost:8080/background/springws/websocket.ws");
//                var websocket = that.websocket;
//
//                //连接发生错误的回调方法
//                websocket.onerror = function () {
//                    setMessageInnerHTML("WebSocket连接发生错误");
//                };
//
//                //连接成功建立的回调方法
//                websocket.onopen = function () {
//                    setMessageInnerHTML("WebSocket连接成功");
//                }
//
//                //接收到消息的回调方法
//                websocket.onmessage = function (event) {
//                    setMessageInnerHTML(event.data);
//                }
//
//                //连接关闭的回调方法
//                websocket.onclose = function () {
//                    setMessageInnerHTML("WebSocket连接关闭");
//                }
//
//                //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
//                window.onbeforeunload = function () {
//                    closeWebSocket();
//                }
//
//                //将消息显示在网页上
//                function setMessageInnerHTML(innerHTML) {
//                    document.getElementById('message').innerHTML += innerHTML + '<br/>';
//                }
//
//                //关闭WebSocket连接
//                function closeWebSocket() {
//                    websocket.close();
//                }
//
//                //发送消息
//                function send() {
//                    var message = document.getElementById('text').value;
//                    console.log("msg",message);
//                    websocket.send(message);
//                }
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
                },
                onSubmit(type) {
                },
                onSearch() {
                    const that = this;
                }
            }
        })
    }
</script>
</body>
</html>