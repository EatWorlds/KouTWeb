<template>
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
                <p><button class="btn btn-lg" role="button">上传图片</button></p>
            </div> 
        </div>
    </section>
</template>

<script>
export default {
  name: "HomeBanner",

};
</script>

<style scoped>
    .Banner{
        position: relative;
        /* top: 30px; */
        width: 100%;
        height: 558px;
        background: #e3e7e9;
        background-size: 12px 12px;
        /* background: url('../../../assets/images/banner.jpg') no-repeat; */
        background-image:linear-gradient(45deg,#f6fafc 25%,transparent 0),
                        linear-gradient(45deg,transparent 75%,#f6fafc 0),
                        linear-gradient(45deg,#f6fafc 25%,transparent 0),
                        linear-gradient(45deg,transparent 75%,#f6fafc 0);
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
</style>
