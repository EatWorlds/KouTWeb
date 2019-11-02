
// 状态存储
const state = {
    imageLists: []
}

// 获取属性变化
const getters = {
    getImageLists : function(state){
        return state.imageLists;
    }
}

// 改变属性状态
const mutations = {
    setImageLists : function(state,imageBase64) {
        state.imageLists.push(imageBase64)
    },
}

// 应用 mutations
const actions = {
    storeImage : function(context, imageBase64) {
        context.commit('setImageLists',imageBase64)
    }
}

export default {
    state,
    getters,
    mutations,
    actions
}