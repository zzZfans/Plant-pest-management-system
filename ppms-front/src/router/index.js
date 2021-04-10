import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: {title: '首页', icon: '树木'}
    }]
  },

  {
    path: '/plant',
    component: Layout,
    redirect: '/plant/plantList',
    name: 'plant',
    meta: {title: '苗木管理', icon: '苗木'},
    children: [
      {
        path: 'plantList',
        name: 'plantList',
        component: () => import('@/views/plant/list'),
        meta: {title: '苗木列表', icon: '列表'}
      },
      {
        path: 'addPlant',
        name: 'addPlant',
        component: () => import('@/views/plant/form'),
        meta: {title: '添加苗木', icon: '添加'}
      },
      {
        path: 'editPlant/:id',
        name: 'editPlant',
        component: () => import('@/views/plant/form'),
        meta: {title: '修改苗木', icon: '修改'},
        hidden: true
      }
    ]
  },
  {
    path: '/disease',
    component: Layout,
    redirect: '/disease/diseaseList',
    name: 'disease',
    meta: {title: '病害管理', icon: '病害'},
    children: [
      {
        path: 'diseaseList',
        name: 'diseaseList',
        component: () => import('@/views/disease/list'),
        meta: {title: '病害列表', icon: '病虫害列表'}
      },
      {
        path: 'addDisease',
        name: 'addDisease',
        component: () => import('@/views/disease/form'),
        meta: {title: '添加病害', icon: '病虫害添加'}
      },
      {
        path: 'editDisease/:id',
        name: 'editDisease',
        component: () => import('@/views/disease/form'),
        meta: {title: '修改病害', icon: '修改'},
        hidden: true
      }
    ]
  },
  {
    path: '/pests',
    component: Layout,
    redirect: '/pests/pestsList',
    name: 'pests',
    meta: {title: '虫害管理', icon: '虫害'},
    children: [
      {
        path: 'pestsList',
        name: 'pestsList',
        component: () => import('@/views/pests/list'),
        meta: {title: '虫害列表', icon: '病虫害列表'}
      },
      {
        path: 'addPests',
        name: 'addPests',
        component: () => import('@/views/pests/form'),
        meta: {title: '添加虫害', icon: '病虫害添加'}
      },
      {
        path: 'editPests/:id',
        name: 'editPests',
        component: () => import('@/views/pests/form'),
        meta: {title: '修改虫害', icon: '修改'},
        hidden: true
      }
    ]
  }
  ,

  // 404 page must be placed at the end !!!
  {path: '*', redirect: '/404', hidden: true}
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
