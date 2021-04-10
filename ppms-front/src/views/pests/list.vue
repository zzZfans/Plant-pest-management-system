<template>
  <div class="app-container">

    <!-- 表格 -->
    <el-table :data="list" border stripe>

      <el-table-column fixed="left" label="#" width="50">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column fixed="left" label="名称" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>

      <el-table-column label="拉丁文" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.latin }}
        </template>
      </el-table-column>


      <el-table-column label="图片" width="150" align="center">
        <template slot-scope="scope">
          <img :src="scope.row.picture" alt="scope.row.title" width="100%">
        </template>
      </el-table-column>

      <el-table-column label="科属" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.subject }}
        </template>
      </el-table-column>

      <el-table-column label="形态特征" show-overflow-tooltip="true" width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.morphologicalCharacter }}
        </template>
      </el-table-column>


      <el-table-column label="生活习性" show-overflow-tooltip="true" width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.lifeHabit }}
        </template>
      </el-table-column>

      <el-table-column label="受灾状况" width="150" align="center">
        <template slot-scope="scope">
          {{ scope.row.disasterSituation }}
        </template>
      </el-table-column>

      <el-table-column label="整治方式" show-overflow-tooltip="true" width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.regulationMode }}
        </template>
      </el-table-column>

      <el-table-column label="受灾品种" width="120" align="center">
        <template slot-scope="scope">
          {{ scope.row.acre }}
        </template>
      </el-table-column>

      <el-table-column label="操作" width="250" min-width="250" fixed="right" align="center">
        <template slot-scope="scope">

          <router-link :to="'/pests/editPests/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit" style="margin: 5px">
              修改
            </el-button>
          </router-link>

          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeById(scope.row.id)"
                     style="margin: 5px">
            删除
          </el-button>

        </template>
      </el-table-column>

    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      style="padding: 30px 0; text-align: center;"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="changePageSize"
      @current-change="changeCurrentPage"
    />
  </div>
</template>

<script>
import pestsApi from "@/api/pests";

export default {

  data() {
    return {
      list: [], // 植物列表
      total: 0, // 总记录数
      page: 1, // 页码
      limit: 10, // 每页记录数
      searchObj: '', // 查询条件
    };
  },

  created() {
    this.fetchData();
  },

  methods: {
    fetchData() {
      pestsApi
        .getPageList(this.page, this.limit, this.searchObj)
        .then(response => {
          this.list = response.data.rows;
          this.total = response.data.total;
        });
    },

    // 每页记录数改变，size：回调参数，表示当前选中的“每页条数”
    changePageSize(size) {
      this.limit = size;
      this.fetchData();
    },

    // 改变页码，page：回调参数，表示当前选中的“页码”
    changeCurrentPage(page) {
      this.page = page;
      this.fetchData();
    },
    // 根据id删除数据
    removeById(id) {
      this.$confirm("此操作将永久删除该虫害，是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        return pestsApi.removeById(id);
      }).then(response => {
        this.fetchData();
        this.$message.success(response.message);
      }).catch((msg) => { // 失败
        if (msg === "cancel") {
          this.$message.info("取消删除");
        }
      });
    }
  }
};
</script>
