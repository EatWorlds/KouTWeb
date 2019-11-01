<template>
    <div>
        <nav class="navbar navbar-light" style="border-bottom:1px solid #dfe5eb;">
            <div class="container-fluid">
                <div class="nav-header">
                    <a class="navbar-brand" href="#" id="home">
                        <svg t="1572488665557" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2104" width="200" height="200"><path d="M424.319032 885.693004 424.319032 620.123556 601.364307 620.123556 601.364307 885.693004 822.671669 885.693004 822.671669 531.60143 955.455881 531.60143 512.841158 133.24777 70.226434 531.60143 203.01167 531.60143 203.01167 885.693004Z" p-id="2105" fill="#707070"></path></svg>
                    </a>
                    <div class="gap"></div>
                    <div class="editText">批量编辑</div>
                    <div class="header-placeholder"></div>
                    <button type="button" 
                        class="btn btn-default"
                        @click="postImages"
                        >
                        继续上传
                        <input type="file" ref="files" id="filesID" multiple="multiple" accept="image/jpeg,image/jpg,image/png" @input="changeFile" style="display:none">
                    </button>
                   
                    <button type="button" style="margin-left:10px" class="btn btn-primary">全部下载</button>
                </div>
            </div>
        </nav>
    </div>
</template>

<script>
import ShowImg from '@/assets/js/common/common.js'
export default {
    name:'Header',
    data () {
        return {
            fileValue: '',
            bold:''
        }
    }, 
    methods: {
        // 继续上传
        postImages () {
            $('#filesID').click();  
        },
        // 监听 input 事件
        changeFile (e) {
            this.fileValue = e.target.value;
        },
        // 存储 base64data
        transitionBase64 () {
            // 转 base64 
            var reader = new window.FileReader();
            reader.readAsDataURL(this.bold); 
            new Promise((resolve,reject)=>{
                reader.onloadend = function () {
                    let base64data = reader.result; 
                    resolve(base64data);
                }
                FileReader.onerror = function () {
                    reject('err');
                }
            }).then((res)=>{
                // 存储到 vuex
                this.$store.dispatch('storeImage',res) 
            })
        }
    },
    watch: {
        // 监听上传文件
        fileValue: function(){
            // 返回图片二进制
            this.bold =  ShowImg('filesID');  
            // 转 base64 存储 store
            this.transitionBase64();
        }
    }
}
</script>

<style scoped>
    .navbar{
        margin-bottom: 0;
    }
    /* 头部布局 */
    .nav-header{
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }
    /* logo */
    .navbar-brand{
        height: 65px;
    }

    /* 返回首页 */
    .home{
        width: 100px;
        height: 100px;
    }
    .icon{
        width: 30px;
        height: 30px;
    }

    /* 竖线 */
    .gap{
        height: 18px;
        width: 1px;
        background-color: #dfe5eb;
    }
    .editText{
        margin-left:20px;
        font-size:20px;
        font-weight:bold;
        color:#606770
    }

    /* flex布局 */
    .header-placeholder{
        flex: 1 1 auto;
    }

    /* 按钮控制 */
    .nav-header button{
        width: 88px;
        height: 40px;
    }
</style>