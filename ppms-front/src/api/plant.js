import request from '@/utils/request'

export default {
  savePlantInfo(plantInfo) {
    return request({
      url: "/plant/save-plant-info",
      method: "post",
      data: plantInfo
    });
  },
  getPlantInfoById(id) {
    return request({
      url: `/plant/plant-info/${id}`,
      method: "get"
    });
  },
  updatePlantInfoById(plantInfo) {
    return request({
      url: "/plant/update-plant-info",
      method: "put",
      data: plantInfo
    });
  },
  getPageList(page, limit, searchObj) {
    return request({
      url: `/plant/list/${page}/${limit}`,
      method: "get",
      params: searchObj
    });
  },
  removeById(id) {
    return request({
      url: `/plant/remove/${id}`,
      method: "delete"
    });
  },
  getPlantList() {
    return request({
      url: `/plant/plant-info/list`,
      method: "get"
    });
  }
}
