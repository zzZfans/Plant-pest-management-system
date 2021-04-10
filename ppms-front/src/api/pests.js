import request from '@/utils/request'

export default {
  savePestsInfo(pestsInfo) {
    return request({
      url: "/pests/save-pests-info",
      method: "post",
      data: pestsInfo
    });
  },
  getPestsInfoById(id) {
    return request({
      url: `/pests/pests-info/${id}`,
      method: "get"
    });
  },
  updatePestsInfoById(pestsInfo) {
    return request({
      url: "/pests/update-pests-info",
      method: "put",
      data: pestsInfo
    });
  },
  getPageList(page, limit, searchObj) {
    return request({
      url: `/pests/list/${page}/${limit}`,
      method: "get",
      params: searchObj
    });
  },
  removeById(id) {
    return request({
      url: `/pests/remove/${id}`,
      method: "delete"
    });
  }
}
