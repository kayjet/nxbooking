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
                        <div class="grid-content bg-purple-dark">
                            <span style="margin-right: 24px;">等待制作的订单</span>
                            <el-dropdown @command="handleCommand">
                                  <span class="el-dropdown-link">
                                  选择门店: {{shopName}}<i class="el-icon-arrow-down el-icon--right"></i>
                                  </span>
                                <el-dropdown-menu slot="dropdown">
                                <#list shopList as item>
                                    <el-dropdown-item command="${item.id},${item.name}">${item.name}</el-dropdown-item>
                                </#list>

                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                    </el-col>
                </el-row>
                <el-row style="margin-top: 14px;">
                    <el-col :span="24">
                        <el-table :data="tableData" border="true" stripe="true" @selection-change="onSelectTableData">

                            <el-table-column label="订单编号" index="0">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.orderNo }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="联系方式" index="8">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.concatPhone }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="订单状态" index="1">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.orderStatus | orderStauts }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="处理状态" index="1">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.isHandler | isHandler}}</span>
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


                            <el-table-column label="生成时间" index="4">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.createTime | formatDate }}</span>
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
                    websocket: null,
                    heartbeatTimeout: 3000,
                    timer: null,
                    reconnectTimer: null,
                    shopId:"",
                    shopName:"",
                }
            },

            created() {

            },
            mounted() {
                const that = this;
            },
            filters: {
                orderStauts(val) {
                    if (val == 1) {
                        return "等待付款";
                    }
                    if (val == 2) {
                        return "付款成功";
                    }
                    if (val == 3) {
                        return "取消";
                    }
                    return "";
                },
                isHandler(val) {
                    if (val == 1) {
                        return "未处理";
                    }
                    if (val == 2) {
                        return "处理成功";
                    }
                    return "";
                }
            },
            methods: {
                heartbeat() {
                    const that = this;
                    console.log("heatbeating...");
                    that.timer = setTimeout(function () {
                        try {
                            that.websocket.send(JSON.stringify({
                                code: 1
                            }));
                            that.timer = setTimeout(that.heartbeat(), that.heartbeatTimeout);
                        } catch (e) {

                        }
                    }, that.heartbeatTimeout);

                },
                closeWebSocket() {
                    const that = this;
                    that.websocket.close();
                },
                initWs() {
                    const that = this;
                    var websocket = that.websocket;
                    //连接发生错误的回调方法
                    websocket.onerror = function (event) {
//                        console.log("ws onerror",event);
                        if (event.readyState == 0) {
                            return;
                        }
                        if (that.timer != null) {
                            clearTimeout(that.timer);
                            that.timer = null;
                        }
                    };
                    //连接关闭的回调方法
                    websocket.onclose = function (event) {
//                        console.log("ws onclose",event);
                        that.websocket = null;
                        setTimeout(function () {
                            that.createWs();
                        }, 3000);
                    };

                    //连接成功建立的回调方法
                    websocket.onopen = function (event) {
                        that.heartbeat();
                    };

                    //接收到消息的回调方法
                    websocket.onmessage = function (event) {
//                        console.log("ws onmessage", event);

                        if (that.timer == null) {
                            that.heartbeat();
                        }

                        var hearbeatDto = JSON.parse(event.data);
//                        console.log(hearbeatDto);
                        if (hearbeatDto.code == 1 && hearbeatDto.data != null) {
                            if (hearbeatDto.data instanceof Array) {
                                Vue.set(that, "tableData", hearbeatDto.data)
                            } else {
                                that.tableData.push(hearbeatDto.data);
                            }
                        }
                    };


                    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
                    window.onbeforeunload = function () {
                        that.closeWebSocket();
                    };
                },
                setMessageInnerHTML() {
                    document.getElementById('message').innerHTML += innerHTML + '<br/>';

                },
                createWs() {
                    const that = this;
                    try {
                        that.websocket = new WebSocket("ws://localhost:8080/background/springws/websocket.ws?shopId=" + that.shopId);
                    } catch (e) {
                    }
//                that.websocket = new WebSocket("ws://www.opdar.com/booking/background/springws/websocket.ws");
                    that.initWs();
                },
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
                },
                handleCommand(command) {
                    const that = this;
                    var arr = command.split(",");
                    if(that.websocket!=null){
                        that.websocket.close();
                    }
                    that.tableData = [];
                    that.shopId = arr[0];
                    that.shopName = arr[1];
                    that.createWs();
                }
            }
        })
    }
</script>
</body>
</html>