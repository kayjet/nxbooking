<script>
    window.vm;
    window.service = {
        listTagForShop: function (shopId) {
            return axios.get(window.ctxPath + '/productionAdditional/listShopTags?shopId=' + shopId);
        },
        listTagForAdding: function (shopId) {
            return axios.get(window.ctxPath + '/productionAdditional/listTagForAdding?shopId=' + shopId);
        },
        listProductForShop: function (pageNo, pageSize, like) {
            var paramString = "";
            if (like != undefined) {
                for (var x in like) {
                    if (like.hasOwnProperty(x)) {
                        paramString += "&" + x + "=" + encodeURI(like[x])
                    }
                }
            }
            return axios.get(window.ctxPath + '/productionAdditional/listProductForShop?pageNo=' + pageNo + '&pageSize=' + pageSize + paramString);
        },
        addTagForShop: function (tagIds, shopId) {
            var data = {
                tagIds: tagIds,
                shopId: shopId
            };
            return axios.post(window.ctxPath + '/productionAdditional/addTagForShop', data);
        },
        addProductForShop: function (productIds, tagId, shopId) {
            var data = {
                productIds: productIds,
                tagId: tagId,
                shopId: shopId
            };
            return axios.post(window.ctxPath + '/productionAdditional/addProductForShop', data);
        },
        removeProductForShop: function (productIds, tagId, shopId) {
            var data = {
                productIds: productIds,
                tagId: tagId,
                shopId: shopId
            };
            return axios.post(window.ctxPath + '/productionAdditional/removeProductForShop', data);
        },
        removeTagForShop: function (tagIds, shopId) {
            var data = {
                tagIds: tagIds,
                shopId: shopId
            };
            return axios.post(window.ctxPath + '/productionAdditional/removeTagForShop', data);
        },
    };
    window.onload = function () {
        window.vm = new Vue({
            el: '#app',
            template: '#myComponent',
            data() {
                return {
                    tableData: [],
                    form: {},
                    addTagForm: {},
                    productForm: {},
                    productSearch: {},
                    totalProductPage: 0,
                    currentProductPage: 1,
                    shopId: '',
                    shopName: "",
                    dialogAddTagVisible: false,
                    dialogProductVisible: false,
                    tagList: [],
                    selectedTagIdsList: [],
                    addProductIdsList: [],
                    productList: [],
                }
            },
            created() {
                const that = this;
            },
            mounted() {
                const that = this;
            },
            methods: {
//                tag start
                dialogAddTag() {
                    const that = this;
                    window.service.addTagForShop(that.selectedTagIdsList, that.shopId).then(function (response) {
                        if (response.data.data) {
                            that.dialogAddTagVisible = false;
                            that.tagList = [];
                            that.selectedTagIdsList = [];
                            that.$message.success('添加成功');
                            window.service.listTagForShop(that.shopId).then(function (response) {
                                that.tableData = response.data.data;
                            });
                        }
                    });
                },
                onSelectTagForAdding(eventData) {
                    console.log("onSelectTagForAdding", eventData);
                    this.selectedTagIdsList = [];
                    var tagIds = [];
                    for (var i = 0; i < eventData.length; i++) {
                        tagIds.push(eventData[i].id);
                    }
                    this.selectedTagIdsList = tagIds;
                },
//                tag end
//                product start
                onInsertProduct() {
                    if (!this.isTagIdsSelected()) {
                        return;
                    }
                    const that = this;
                    that.dialogProductVisible = true;
                    that.currentProductPage = 1;
                    that.listProductForShop();
                },
                onSelectAddProduct(products) {
                    console.log("onSelectAddProduct", products);
                    const that = this;
                    that.addProductIdsList = [];
                    var productIds = [];
                    for (var i = 0; i < products.length; i++) {
                        productIds.push(products[i].id);
                    }
                    that.addProductIdsList = productIds;
                },
                isTagIdsSelected() {
                    if (this.selectedTagIdsList.length == 0) {
                        this.$message.error('请选择分类');
                        return false;
                    }
                    if (this.selectedTagIdsList.length > 1) {
                        this.$message.error('请勿选中多个分类');
                        return false;
                    }
                    return true;
                },
                addProduct() {
                    console.log(this.selectedTagIdsList);
                    if (!this.isTagIdsSelected()) {
                        return;
                    }
                    const that = this;
                    var tagId = this.selectedTagIdsList[0];
                    window.service.addProductForShop(that.addProductIdsList, tagId, that.shopId).then(function (response) {
                        if (response.data.data) {
                            that.$message.success('添加成功');
                            that.dialogProductVisible = false;
                            that.addProductIdsList = [];
                            window.service.listTagForShop(that.shopId).then(function (response) {
                                that.tableData = response.data.data;
                            });
                        }
                    })
                },
                searchProduct() {
                    const that = this;
                    that.dialogProductVisible = true;
                    that.listProductForShop();
                },
                onDeleteProduct() {
                    const that = this;
                    if (!this.isTagIdsSelected()) {
                        return;
                    }
                    window.service.removeProductForShop(that.addProductIdsList, that.selectedTagIdsList[0], that.shopId).then(function (response) {
                        if (response.data.data) {
                            that.$message.success('删除成功');
                            that.dialogProductVisible = false;
                            that.addProductIdsList = [];
                            window.service.listTagForShop(that.shopId).then(function (response) {
                                that.tableData = response.data.data;
                            });
                        }
                    });
                },
                onUpdateProduct() {

                },
                handleProductSizeChange() {

                },
                handleProductCurrentChange(val) {
                    console.log('当前页: ' + val);
                    const that = this;
                    that.currentProductPage = val;
                    that.listProductForShop();
                },
                listProductForShop() {
                    const that = this;
                    console.log(" that.selectedTagIdsList", that.selectedTagIdsList);
                    that.productSearch.shopId = that.shopId;
                    that.productSearch.tagId = that.selectedTagIdsList[0];
                    window.service.listProductForShop(that.currentProductPage, 10, that.productSearch).then(function (response) {
                        that.productList = response.data.data;
                        that.totalProductPage = response.data.countSize;
                        that.currentProductPage = response.data.pageNo;
                    });
                },
//                product end
                onSelectTableData(eventData) {
                    console.log("onSelectTableData", eventData);
                    this.selectedTagIdsList = [];
                    var tagIds = [];
                    for (var i = 0; i < eventData.length; i++) {
                        tagIds.push(eventData[i].id);
                    }
                    this.selectedTagIdsList = tagIds;
                },
                onInsertTag() {
                    const that = this;
                    if (that.shopId == '') {
                        that.$message.error('请先选择门店');
                        return;
                    }
                    that.dialogAddTagVisible = true;
                    window.service.listTagForAdding(that.shopId).then(function (response) {
                        that.tagList = response.data.data;
                    });
                },
                onDeleteTag() {
                    const that = this;
                    this.$confirm('此操作将删除分类下所有产品, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        window.service.removeTagForShop(that.selectedTagIdsList, that.shopId).then(function (response) {
                            if (response.data.data) {
                                that.tagList = [];
                                that.selectedTagIdsList = [];
                                that.$message.success('删除成功');
                                window.service.listTagForShop(that.shopId).then(function (response) {
                                    that.tableData = response.data.data;
                                });
                            }
                        });
                    }).catch(() => {
                    });

                },
                onUpdateTag() {

                },
                tableRowClassName({row, rowIndex}) {
                    if (rowIndex === 1) {
                        return 'warning-row';
                    } else if (rowIndex === 3) {
                        return 'success-row';
                    }
                    return '';
                },
                handleCommand(command) {
                    const that = this;
                    var arr = command.split(",");
                    that.tableData = [];
                    that.shopId = arr[0];
                    that.shopName = arr[1];
                    window.service.listTagForShop(that.shopId).then(function (response) {
                        that.tableData = response.data.data;
                    });
                },

            }
        })
    }
</script>