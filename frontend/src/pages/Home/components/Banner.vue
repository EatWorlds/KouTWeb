<template>
    <div>
        <section class="Banner">
            <div class="background-half"></div>
            <div class="content">
                <div class="portrait-figure">
                    <div class="background-gradient"></div>
                    <div class="portrait">
                        <!-- <img class="portrait_person" src="../../../assets/images/person.png" alt=""> -->
                    </div>
                </div>
                <div class="intro">
                    <h1>在线智能自动抠图</h1>
                    <p>小胖抠图。更简单，更准确。</p>
                    <p>
                        <button 
                        class="btn btn-lg" 
                        role="button" 
                        @click="selectImage">上传图片</button>
                        <input id="file" type="file" accept="image/jpeg,image/jpg,image/png" style="display:none">
                    </p>
                </div> 
            </div>
        </section>

        <!-- 上传图片模态框 -->
        <!-- <transition name="hidden-fade">
            <div draggable="false" v-show="modelShow" class="img_modal img img_modal_show">
                <div class="img-dialog">
                    <div class="img-pool">
                        <label class="img_upload-btn">
                            上传图片
                        <input type="file" multiple="multiple" accept="image/jpeg,image/jpg,image/png" style="display:none;"/>
                        </label>
                        <div 
                            id="hoverImage" 
                            class="img_images"
                            @mouseenter="changShow" 
                            @mouseleave="changShow"
                            >
                            <img src="https://imgs.qunarzz.com/vs_ceph_vs_tts/853f321a-fd55-4999-8337-e77aafe85f15.jpg_r_390x260x90_0c644257.jpg" alt="">
                            <transition name="hidden-fade">
                                <div  v-show="imgShow" class="img-mask">
                                    <p>名字</p>
                                    <div>删除</div>
                                </div>
                            </transition>
                        </div>
                    </div>
                </div>
            </div>
        </transition> -->
    </div>
</template>

<script>
export default {
    name: "HomeBanner",
    data(){
        return {
            imgShow: false,
            modelShow: false
        }
    },
    methods:{
        // 批量上传图片模态框
        // postImages(){
        //     this.modelShow = true;
        // },

        // 显示删除
        changShow(){
            this.imgShow = !this.imgShow
        },

        // 上传单个图片
        selectImage(){
            $('#file').click()
        }
    }
};
</script>

<style scoped>
    /* ------------------------------ 动画 ---------------------------------- */

    /* 鼠标悬停动画： 设置过渡属性即时间 */
    .hidden-fade-enter-active, .hidden-fade-leave-active {
        transition: opacity .5s;
    }
    /* 定义进入/进出状态 */
    .hidden-fade-enter, .hidden-fade-leave-to {
        opacity: 0;
    }

    .img .img_modal_show{
        visibility: visible;
        opacity: 1;
    }
    /* 模糊图层 */
    .img_modal .img{
        position: fixed;
        left: 0;
        top: 0;
        width: 100vm;
        height: 100vm;
        background: rgba(0,0,0,.2);
        overflow: auto;
        z-index: 100;
        transition: opacity .5, visibility .5s;
    }
    /* 批量上传图片模态框 */
    .img-dialog{
        position: fixed;
        left: 50%;
        top: 100px;
        width: 800px;
        border-radius: 4px;
        overflow: hidden;
        transform: translate3d(-50%,0,0)
    }
    /* 网格布局 */
    .img-pool{
        display: grid;
        grid-template-columns: repeat(5,20%);
        grid-auto-flow: row;
        /* 垂直方向居上  */
        align-content: flex-start;
        /* 水平间隔相等,项目与容器边框之间没有间隔 */
        justify-content: space-between; 
        height: 520px;
        padding: 40px 25px;   
        overflow: auto;
        background-color: #ecf0f5;
    }
    label{
        width: 120px;
        height: 120px;
        margin: 0 auto 34px;
        background-color: #fff;
    }
    /* 上传图片按钮 */
    .img_upload-btn{
        border: 1px solid #2254f4;
        padding: 64px 0 0;
        color: #2254f4;
        font-size: 14px;
        text-align: center;
        line-height: 20px;
        background-image: url('../../../assets/images/shangchuan.svg');
        background-repeat: no-repeat;
        background-size: 33px;
        background-position: center 34px;
        border-radius: 0;
    }

    /* 上传图片大小 */
    .img_images{
        position: relative;
        width: 120px;
        height: 120px;
        margin: 0 auto 34px;
        background-color: #fff;
    }
    /* 图片样式 */
    .img_images img{
        width: 100%;
        height: 100%;
        object-fit: contain;
        object-position: center;
    }
    /* 删除图层 */
    .img-mask{
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        font-size: 14px;
        padding: 12px 12px 16px;
        background-color: #fff;
    }
    /* 图层文字 */
    .img-mask > p{
        max-width: 100%;
        width: max-content;
        height: 70px;
        overflow: hidden;
        margin: 0 auto 8px;
        line-height: 17px;
        word-break: break-word;
        -webkit-line-clamp: 4;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
    }
    /* 图层删除 */
    .img-mask > div{
        width: 52px;
        padding-left: 24px;
        margin: 0 auto;
        color: #434951;
        line-height: 20px;
        background-image: url('../../../assets/images/shanchu.svg');
        background-size: 20px;
        background-repeat: no-repeat;
    }
    
    .Banner{
        position: relative;
        /* top: 30px; */
        width: 100%;
        height: 558px;
        background: #2254f4;
        background-size: 12px 12px;
        /* background: url('../../../assets/images/banner.jpg') no-repeat; */
        /* background-image:linear-gradient(45deg,#f6fafc 25%,transparent 0),
                        linear-gradient(45deg,transparent 75%,#f6fafc 0),
                        linear-gradient(45deg,#f6fafc 25%,transparent 0),
                        linear-gradient(45deg,transparent 75%,#f6fafc 0); */
        background-position: 0 0,6px 6px,6px 6px,12px 12px;
    }

    .background-half{
        position: absolute;
        top: 0;
        left: 50%;
        width: 50%;
        height: 100%;
        background: #2263fe;
    }

    /* 解决内边距问题 */
    .modal-open { 
        overflow: inherit;
        padding-right: 0px !important;
    }

    /* 主要内容 */
    .content{
        position: relative;
        top: 0;
        width: 1200px;
        height: 100%;
        margin: 0 auto;
        /* border: 1px solid #000; */
    }

    .portrait-figure{
        position: relative;
        width: 650px;
        height: 100%;
        /* border: 1px solid #000; */
    }

    .portrait{
        position: absolute;
        top: 0;
        left: 0;
        /* background-image: url('https://cdn.dancf.com/gaodingx/www/image/banner-sample_036501fb.png') no-repeat; */
        background-size: cover;
    }

    .background-gradient{
        position: absolute;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        background-image:linear-gradient(95deg,rgba(34,99,254,0) 40%,#2263fe 53%);
    }

    .portrait_person{
        width: 100
    }

    .intro{
        position: absolute;
        top: 200px;
        right: 0px;
    }

    .intro h1{
        font-size: 48px;
        font-weight: bold;
        color: #fff;
        margin: 0 0 10px 0;
    }

    .intro > p{
        position: relative;
        line-height: 30px;
        color: #fff;
        font-size: 22px;
        font-weight: 400;
        margin-bottom: 40px;
    }
    
    .btn-lg{
        border-radius: 35px;
        color: #2263fe;
        font-size: 22px;
        font-weight: bolder;
        background: #fff;
        padding: 20px 86px;
        transition: background-color 0.3s ease-out;
    }

    /* ------------------------------------模态框样式 ----------------------------------------*/

    /* 模态框的位置 */
    .modal-dialog {
        width: 800px;
        margin:100px auto;
    }
    /* 模态框的大小 */
    .modal-content{
        width: 800px;
        height: 520px;
    }
</style>
