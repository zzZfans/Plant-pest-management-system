<template>
  <div class="app-container">

    <el-form label-width="120px">

      <el-form-item label="病害名称">
        <el-input style="width: 300px" v-model="diseaseInfo.name" placeholder="示例：松树腐烂病"/>
      </el-form-item>

      <el-form-item label="拉丁文">
        <el-input style="width: 300px" v-model="diseaseInfo.latin" placeholder="示例：Cytospora. Sp"/>
      </el-form-item>

      <el-form-item label="病害图片">
        <el-upload
          :show-file-list="false"
          :on-success="handleCoverSuccess"
          :before-upload="beforeCoverUpload"
          :on-error="handleCoverError"
          class="cover-uploader"
          :action='defaultSettings.ossApi+"disease"'>
          <img v-if="diseaseInfo.picture" :src="diseaseInfo.picture">
          <i v-else class="el-icon-plus avatar-uploader-icon"/>
        </el-upload>
      </el-form-item>

      <el-form-item label="防治措施">
        <el-input
          style="width: 700px"
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 10}"
          placeholder="请输入该病害的防治措施。"
          v-model="diseaseInfo.preventiveMethod">
        </el-input>
      </el-form-item>

      <el-form-item label="症状">
        <el-input
          style="width: 700px"
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 10}"
          placeholder="请输入该病害的症状。"
          v-model="diseaseInfo.symptom">
        </el-input>
      </el-form-item>

      <el-form-item label="病症原因">
        <el-input
          show-word-limit
          maxlength="200"
          style="width: 700px"
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 10}"
          placeholder="请输入该病害的病症原因。"
          v-model="diseaseInfo.causeOfDisease">
        </el-input>
      </el-form-item>

      <el-form-item label="受灾状况">
        <el-input
          show-word-limit
          maxlength="200"
          style="width: 700px"
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 10}"
          placeholder="请输入该病害的受灾状况。"
          v-model="diseaseInfo.disasterSituation">
        </el-input>
      </el-form-item>

      <el-form-item label="苗木">
        <!-- 苗木 -->
        <el-select
          filterable
          v-model="diseaseInfo.plantId"
          placeholder="苗木"
          @change="plantChanged">
          <el-option
            v-for="plant in plantList"
            :key="plant.id"
            :label="plant.name"
            :value="plant.id"/>
        </el-select>

        <!-- 地块 -->
        <el-select
          style="margin: 10px"
          filterable
          v-model="diseaseInfo.acre"
          placeholder="地块">
          <el-option
            v-for="(acre,index) in acreList"
            :key="index"
            :label="acre"
            :value="acre"/>
        </el-select>
      </el-form-item>

    </el-form>

    <div style="text-align:center">
      <el-button :disabled="saveBtnDisabled" type="primary" @click="save()">保存</el-button>
    </div>

  </div>
</template>

<script>
import diseaseApi from "@/api/disease";
import plantApi from "@/api/plant";
import router from "@/router";
import defaultSettings from "@/settings"

export default {
  data() {
    return {
      defaultSettings,
      saveBtnDisabled: false, // 按钮是否禁用
      diseaseInfo: {// 表单数据
        name: "",
        latin: "",
        picture: "",
        preventiveMethod: "",
        symptom: "",
        causeOfDisease: "",
        disasterSituation: "",
        plantId: '',
        acre: ""
      },
      plantList: [],
      acreList: []
    };
  },
  created() {
    if (this.$route.params.id) { // 回显
      this.fetchdiseaseInfoById(this.$route.params.id);
    } else {
      this.initPlantList();
    }
  },

  methods: {
    // 保存
    save() {
      if (this.judge()) {
        this.saveBtnDisabled = true;
        if (!this.$route.params.id) {
          this.saveData();
        } else {
          this.updateData();
        }
        router.push('/disease')
      }
    },
    // 保存
    saveData() {
      diseaseApi
        .saveDiseaseInfo(this.diseaseInfo)
        .then(response => {
          this.$message.success(response.message);
        });
    },
    // 修改
    updateData() {
      diseaseApi
        .updateDiseaseInfoById(this.diseaseInfo)
        .then(response => {
          this.$message.success(response.message);
        });
    },
    // 上传成功回调
    handleCoverSuccess(res, file) {
      if (res.success) {
        // console.log(res)
        this.diseaseInfo.picture = res.data.url;
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
    fetchdiseaseInfoById(id) {
      diseaseApi
        .getDiseaseInfoById(id)
        .then(response => {
          this.diseaseInfo = response.data.item;
        });
    },
    judge() {
      if (this.diseaseInfo.name === "") {
        this.$message.warning("请输入病毒名称！");
        return false;
      }
      if (this.diseaseInfo.latin === "") {
        this.$message.warning("请输入拉丁文！");
        return false;
      }
      if (this.diseaseInfo.picture === "") {
        this.$message.warning("请上传病毒图片！");
        return false;
      }
      if (this.diseaseInfo.preventiveMethod === "") {
        this.$message.warning("请输入防治措施！");
        return false;
      }
      if (this.diseaseInfo.symptom === "") {
        this.$message.warning("请输入病害症状！");
        return false;
      }
      if (this.diseaseInfo.causeOfDisease === "") {
        this.$message.warning("请输入病症原因！");
        return false;
      }
      if (this.diseaseInfo.disasterSituation === "") {
        this.$message.warning("请输入受灾状况！");
        return false;
      }
      if (this.diseaseInfo.plantId === "") {
        this.$message.warning("请选择苗木！");
        return false;
      }
      if (this.diseaseInfo.acre === "") {
        this.$message.warning("请选择地块！");
        return false;
      }
      return true;
    },
    plantChanged(value) {
      this.plantList.forEach(plant => {
        if (plant.id === value) {
          this.diseaseInfo.acre = "";
          this.acreList = plant.acreList;
        }
      });
    },
    initPlantList() {
      plantApi.getPlantList().then(response => {
        this.plantList = response.data.items;
      });
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

