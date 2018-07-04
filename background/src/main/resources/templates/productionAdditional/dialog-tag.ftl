<el-dialog :title="shopName" :visible.sync="dialogAddTagVisible">
    <el-row>
        <el-col :span="24">
            <div class="grid-content bg-purple-dark">
                <div style="padding-bottom: 14px;">
                    以下是可添加分类：
                </div>
            </div>
        </el-col>
    </el-row>
    <el-form :model="addTagForm">
        <el-table   :data="tagList" border="false" stripe="false" @selection-change="onSelectTagForAdding">
            <el-table-column
                    type="selection"
                    width="55">
            </el-table-column>

            <el-table-column label="分类">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{  scope.row.title }}</span>
                </template>
            </el-table-column>

        <#--  <el-table-column label="图片">
              <template slot-scope="scope">
                  <span style="margin-left: 10px">{{  scope.row.pic }}</span>
              </template>
          </el-table-column>-->
        </el-table>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogAddTag">确认添加</el-button>
    </div>
</el-dialog>