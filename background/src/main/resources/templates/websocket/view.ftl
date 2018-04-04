<#include "../header.ftl"/>

</head>

<body>

<div id="app"></div>

<script>
    var websocket = null;

    window.onload = function(){
        //判断当前浏览器是否支持WebSocket
        if ('WebSocket' in window) {

        }
        else {
            alert('当前浏览器 Not support websocket')
        }
    };

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("WebSocket连接成功");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send() {
        var message = document.getElementById('text').value;
        console.log("msg",message);
        websocket.send(message);
    }
</script>
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
                }
            },
            created() {
                const that = this;
//                window.service.listPage(1, 10, that.search).then(function (response) {
//                    that.tableData = response.data.data;
//                    that.currentPage = response.data.pageNo;
//                    that.totalPage = response.data.countSize;
//                });
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