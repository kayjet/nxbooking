<script>
    window.vm;
    window.service = {
        listTagForShop: function (shopId) {
            return axios.get(window.ctxPath + '/productionAdditional/listShopTags?shopId=' + shopId);
        },
        listTagForAdding: function (shopId) {
            return axios.get(window.ctxPath + '/productionAdditional/listTagForAdding?shopId=' + shopId);
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
                    selectionChanges: [],
                    defaultWarningButton: {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    },
                    search: {},
                    totalPage: 0,
                    currentPage: 1,
                    shopId: '',
                    shopName: "",
                    dialogFormVisible: false,
                    tagList: []
                }
            },
            created() {
                const that = this;
            },
            mounted() {
                const that = this;
            },
            methods: {
                onSelectTableData() {

                },
                onInsertTag() {
                    const that = this;
                    if(that.shopId == ''){
                        that.$message.error('请先选择门店');
                        return;
                    }
                    that.dialogFormVisible = true;
                    window.service.listTagForAdding(that.shopId).then(function (response) {
                        that.tagList = response.data.data;
                    });
                },
                onDeleteTag() {

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
                }
            }
        })
    }
</script>