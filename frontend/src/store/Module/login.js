const state = {
    username: "", // 用户名
    token:"",     // token
}

const getters = {
    getUsername : state => state.username,
    getToken : state => state.token
}

const mutations = {
    setUsername : (state,data) => {
        state.username = data
    },
    setToken : (state,data) => {
        state.token = data
    }
}

const actions = {
    
}

export default {
    state,
    getters,
    mutations,
    actions
}