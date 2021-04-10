import request from '@/utils/request'

export default {
  saveDiseaseInfo(diseaseInfo) {
    return request({
      url: "/disease/save-disease-info",
      method: "post",
      data: diseaseInfo
    });
  },
  getDiseaseInfoById(id) {
    return request({
      url: `/disease/disease-info/${id}`,
      method: "get"
    });
  },
  updateDiseaseInfoById(diseaseInfo) {
    return request({
      url: "/disease/update-disease-info",
      method: "put",
      data: diseaseInfo
    });
  },
  getPageList(page, limit, searchObj) {
    return request({
      url: `/disease/list/${page}/${limit}`,
      method: "get",
      params: searchObj
    });
  },
  removeById(id) {
    return request({
      url: `/disease/remove/${id}`,
      method: "delete"
    });
  }
}
