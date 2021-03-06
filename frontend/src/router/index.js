import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: () => import('@/pages/Home/Home')
    },{
      path:'/koutu',
      name: 'koutu',
      component:() => import('@/pages/koutu/Koutu')
    }
  ]
})
