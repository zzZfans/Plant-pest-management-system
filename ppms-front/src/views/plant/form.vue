<template>
  <div class="app-container">

    <!-- 课程信息表单 -->
    <el-form label-width="120px">

      <el-form-item label="苗木名称">
        <el-input style="width: 300px" v-model="plantInfo.name" placeholder="示例：国槐"/>
      </el-form-item>

      <el-form-item label="拉丁文">
        <el-input style="width: 300px" v-model="plantInfo.latin" placeholder="示例：Sophora japonica Linn."/>
      </el-form-item>

      <!-- 苗木图片 -->
      <el-form-item label="苗木图片">
        <el-upload
          :show-file-list="false"
          :on-success="handleCoverSuccess"
          :before-upload="beforeCoverUpload"
          :on-error="handleCoverError"
          class="cover-uploader"
          :action='defaultSettings.ossApi+"plant"'>
          <img v-if="plantInfo.picture" :src="plantInfo.picture">
          <i v-else class="el-icon-plus avatar-uploader-icon"/>
        </el-upload>
      </el-form-item>


      <!-- 苗木习性 -->
      <el-form-item label="苗木习性">
        <el-input
          style="width: 700px"
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 10}"
          placeholder="请输入该苗木习性。"
          v-model="plantInfo.habit">
        </el-input>
      </el-form-item>

      <el-form-item label="苗木数量">
        <el-input-number :min="1" v-model="plantInfo.number" controls-position="right" placeholder="请填写苗木数"/>
      </el-form-item>

      <el-form-item label="苗木规格">
        <el-input style="width: 300px" v-model="plantInfo.specifications" placeholder="请输入苗木规格"/>
      </el-form-item>

      <el-form-item label="行株距">
        <el-input style="width: 300px" v-model="plantInfo.rowSpacing" placeholder="示例：6*6"/>
      </el-form-item>

      <el-form-item label="科">
        <el-input style="width: 300px" v-model="plantInfo.subject" placeholder="示例：木犀科"/>
      </el-form-item>

      <el-form-item label="属">
        <el-input style="width: 300px" v-model="plantInfo.genus" placeholder="示例：槐属"/>
      </el-form-item>

      <el-form-item label="地块">
        <el-tag
          :key="index"
          v-for="(acre,index) in plantInfo.acreList"
          closable
          :disable-transitions="false"
          @close="handleClose(acre)">
          {{ acre }}
        </el-tag>
        <el-input
          class="input-new-tag"
          v-if="inputVisible"
          v-model="inputValue"
          ref="saveTagInput"
          size="small"
          @keyup.enter.native="handleInputConfirm"
          @blur="handleInputConfirm"
        >
        </el-input>
        <el-button v-else class="button-new-tag" size="small" @click="showInput">+</el-button>
      </el-form-item>

    </el-form>

    <div style="text-align:center">
      <el-button :disabled="saveBtnDisabled" type="primary" @click="save()">保存</el-button>
    </div>

  </div>
</template>

<script>
import plantApi from "@/api/plant";
import router from "@/router";
import defaultSettings from "@/settings"

export default {
  data() {
    return {
      defaultSettings,
      saveBtnDisabled: false, // 按钮是否禁用
      plantInfo: {// 表单数据
        name: "",
        latin: "",
        picture: "",
        habit: "",
        number: 1,
        specifications: "",
        rowSpacing: "",
        subject: "",
        genus: "",
        acreList: []
      },
      inputVisible: false,
      inputValue: ''
    };
  },
  created() {
    if (this.$route.params.id) { // 回显
      this.fetchplantInfoById(this.$route.params.id);
    }
  },

  methods: {
    // 保存并下一步
    save() {
      if (this.judge()) {
        this.saveBtnDisabled = true;
        if (!this.$route.params.id) {
          this.saveData()
        } else {
          this.updateData()
        }
        router.push('/plant')
      }
    },
    // 保存
    saveData() {
      plantApi
        .savePlantInfo(this.plantInfo)
        .then(response => {
          this.$message.success(response.message);
        });
    },
    // 修改
    updateData() {
      plantApi
        .updatePlantInfoById(this.plantInfo)
        .then(response => {
          this.$message.success(response.message);
        });
    },
    // 上传成功回调
    handleCoverSuccess(res, file) {
      if (res.success) {
        this.plantInfo.picture = res.data.url;
        // 强制重新渲染
        this.$forceUpdate();
      } else {
        this.$message.error("上传失败！");
      }
    },
    // 上传校验
    beforeCoverUpload(file) {
      const isJPG = (file.type === "image/jpeg" || file.type === "image/png");
      const isLt2M = file.size / 1024 / 1024 < 10;

      if (!isJPG) {
        this.$message.error("上传封面图片只能是 JPG 或 PNG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传封面图片大小不能超过 2MB！");
      }
      return isJPG && isLt2M;
    },
    // 错误处理
    handleCoverError() {
      this.$message.error("上传失败(http error)");
    },
    fetchplantInfoById(id) {
      plantApi
        .getPlantInfoById(id)
        .then(response => {
          this.plantInfo = response.data.item;
        });
    },
    handleClose(tag) {
      this.plantInfo.acreList.splice(this.plantInfo.acreList.indexOf(tag), 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.plantInfo.acreList.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = '';
    },
    judge() {
      if (this.plantInfo.name === "") {
        this.$message.warning("请输入苗木名称！");
        return false;
      }
      if (this.plantInfo.latin === "") {
        this.$message.warning("请输入拉丁文！");
        return false;
      }
      if (this.plantInfo.picture === "") {
        this.$message.warning("请上传图片！");
        return false;
      }
      if (this.plantInfo.habit === "") {
        this.$message.warning("请输入苗木习性！");
        return false;
      }
      if (this.plantInfo.specifications === "") {
        this.$message.warning("请输入苗木规格！");
        return false;
      }
      if (this.plantInfo.rowSpacing === "") {
        this.$message.warning("请输入行株距！");
        return false;
      }
      if (this.plantInfo.subject === "") {
        this.$message.warning("请输入科！");
        return false;
      }
      if (this.plantInfo.genus === "") {
        this.$message.warning("请输入属！");
        return false;
      }
      if (this.plantInfo.acreList.length === 0) {
        this.$message.warning("请输入地块！");
        return false;
      }
      if (this.isAcreRepeat(this.plantInfo.acreList)) {
        this.$message.warning("地块重复！");
        return false;
      }
      return true;
    },
    isAcreRepeat(arr) {
      var hash = {};
      for (var i in arr) {
        if (hash[arr[i]]) {
          return true;
        }
        hash[arr[i]] = true;
      }
      return false;
    }
  }
};
</script>

<style>
.cover-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.cover-uploader .el-upload:hover {
  border-color: #409EFF;
}

.cover-uploader .avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 640px;
  height: 357px;
  line-height: 357px;
  text-align: center;
}

.cover-uploader img {
  width: 640px;
  height: 357px;
  display: block;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>

