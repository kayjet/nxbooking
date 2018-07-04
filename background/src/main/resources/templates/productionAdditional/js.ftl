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
        saveOrUpdateProductSpPrice: function (products, tagId, shopId) {
            var prices = [];
            for (var i = 0; i < products.length; i++) {
                var d = {};
                d.spPrice = products[i].spPrice;
                d.productId = products[i].id;
                prices.push(d);
            }
            var data = {
                prices: prices,
                tagId: tagId,
                shopId: shopId
            };
            return axios.post(window.ctxPath + '/productionAdditional/saveOrUpdateProductSpPrice', data);
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
                    addProductList: [],
                    productList: [],
                    isUpdating: false
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
                            that.listTagForShop();
                        }
                    });
                },
                onSelectTagForAdding(eventData) {
//                    console.log("onSelectTagForAdding", eventData);
                    this.selectedTagIdsList = [];
                    var tagIds = [];
                    for (var i = 0; i < eventData.length; i++) {
                        tagIds.push(eventData[i].id);
                    }
                    this.selectedTagIdsList = tagIds;
                },
//                tag end
//                product start
                onSelectProductRow(selection, row) {
//                    console.log("onSelectProductRow", selection, row);

                },
                onInsertProduct() {
                    if (!this.isTagIdsSelected()) {
                        return;
                    }
                    const that = this;
                    that.dialogProductVisible = true;
                    that.currentProductPage = 1;
                    that.listProductForShop();
                },
                onCancelUpdateProductPrice(){
                    const that = this;
                    that.isUpdating = false;
                    that.tagList = [];
                    that.selectedTagIdsList = [];
                    that.addProductList = [];
                    that.listTagForShop();
                },
                onUpdateProductPrice() {
                    const that = this;
                    if (this.selectedTagIdsList.length == 0) {
                        this.$message.error('请选择分类');
                        return false;
                    }
                    var tagId = this.selectedTagIdsList[0];
                    window.service.saveOrUpdateProductSpPrice(that.addProductList, tagId, that.shopId).then(function (response) {
                        if (response.data.data) {
                            that.isUpdating = false;
                            that.tagList = [];
                            that.selectedTagIdsList = [];
                            that.addProductList = [];
                            that.$message.success('修改成功');
                            that.$refs.productTable.clearSelection();
                            that.$refs.parentTable.clearSelection();
                        }
                    });
                },
                onSelectAddProduct(products) {
//                    console.log("onSelectAddProduct", products);
                    const that = this;
                    if (that.addProductList.length != 0) {
                        for (var i = 0; i < that.addProductList.length; i++) {
                            that.addProductList[i].selected = false;
                        }
                        that.isUpdating = false;
                    }
                    that.addProductList = [];
                    var productIds = [];
                    for (var i = 0; i < products.length; i++) {
                        productIds.push(products[i]);
                    }
                    that.addProductList = productIds;
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
//                    console.log(this.selectedTagIdsList);
                    if (!this.isTagIdsSelected()) {
                        return;
                    }
                    const that = this;
                    var tagId = this.selectedTagIdsList[0];
                    var productIds = [];
                    for (var i = 0; i < that.addProductList.length; i++) {
                        productIds.push(that.addProductList[i].id)
                    }
                    window.service.addProductForShop(productIds, tagId, that.shopId).then(function (response) {
                        if (response.data.data) {
                            that.$message.success('添加成功');
                            that.dialogProductVisible = false;
                            that.addProductList = [];
                            that.listTagForShop();
                        }
                    })
                },
                listTagForShop() {
                    const that = this;
                    window.service.listTagForShop(that.shopId).then(function (response) {
                        that.tableData = response.data.data;
                    });
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
                    var productIds = [];
                    for (var i = 0; i < that.addProductList.length; i++) {
                        productIds.push(that.addProductList[i].id)
                    }
                    window.service.removeProductForShop(productIds, that.selectedTagIdsList[0], that.shopId).then(function (response) {
                        if (response.data.data) {
                            that.$message.success('删除成功');
                            that.dialogProductVisible = false;
                            that.addProductList = [];
                            that.listTagForShop();
                        }
                    });
                },
                onUpdateProduct() {
                    const that = this;
                    if (that.addProductList.length == 0) {
                        that.$message.error('请先选择产品');
                        that.isUpdating = false;
                        return;
                    }
                    if (this.selectedTagIdsList.length == 0) {
                        this.$message.error('请选择分类');
                        return;
                    }
                    that.isUpdating = true;
                    for (var i = 0; i < that.addProductList.length; i++) {
                        that.addProductList[i].selected = !that.addProductList[i].selected;
                    }
                },
                handleProductSizeChange() {

                },
                handleProductCurrentChange(val) {
//                    console.log('当前页: ' + val);
                    const that = this;
                    that.currentProductPage = val;
                    that.listProductForShop();
                },
                listProductForShop() {
                    const that = this;
//                    console.log(" that.selectedTagIdsList", that.selectedTagIdsList);
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
//                    console.log("onSelectTableData", eventData);
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
                                that.listTagForShop();
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
                    that.listTagForShop();
                },

            }
        })
    }
</script>