<template>
    <section class="showImage">
        <div class="img-card transparent-bg" v-for="(item,index) in imagesList" :key="index">
            <img width="100%;" :src="item" alt="">
            <div class="img-btn-ground">
                <button type="button" class="btn btn-success">抠图</button>
                <button type="button" class="btn btn-primary" style="margin-left:10px;">下载</button>
            </div>
        </div>
    </section>
</template>

<script>

export default {
    name:'Show',
    data(){
        return {
            imagesList : this.$store.getters.getImageLists
        }
    }
}
</script>

<style scoped>
    .showImage{
        display: grid;
        flex: 1 1 auto;
        grid-template-columns: repeat(4,25%);
        height: 790px;
        padding: 22px 26px;
        background: #ecf0f5;
    }

    .transparent-bg{
        background-image: linear-gradient(45deg,#e4e7e9 25%,transparent 0),
                          linear-gradient(45deg,transparent 75%,#e4e7e9 0),
                          linear-gradient(45deg,#e4e7e9 25%,transparent 0),
                          linear-gradient(45deg,transparent 75%,#e4e7e9 0);
        background-position: 0 0,8px 8px,8px 8px,16px 16px;
        background-size: 16px 16px;
    }

    .img-card{
        display: flex;
        flex-direction:column;
        align-items: center;
        width: 240px;
        height: 240px;
        padding-top: 20px;
    }
    /* 抠图下载按钮组 */
    .img-btn-ground{
        margin-top: 20px;
    }

    .img-btn-ground button{
        width: 70px;
    }
</style>