<template>
  <header class="navbar navbar-fixed-top header_bg">
    <div class="container">
      <!-- logo -->
      <div class="navbar-header">
        <img class="img-fluid" style="height:80px" src="../../../assets/images/logo.png" alt="">
      </div>
      <!-- end logo -->
      <button
        type="button"
        class="navbar-toggle collapsed"
        data-toggle="collapse"
        data-target="#navbar"
        aria-expanded="false"
        aria-controls="navbar"
      >
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <!-- nav -->
      <div class="navbar-collapse collapse" aria-expanded="false">
        <ul class="nav navbar-nav">
          <li class="active">
            <a href="#">说明文档</a>
          </li>
          <li class="active">
            <a href="#">立即购买</a>
          </li>
          <li class="active">
            <a href="#">模板</a>
          </li>
          <li class="active">
            <a href="#">联系我们</a>
          </li>
        </ul>
        <!-- Login Regist -->
        <div class="header_login">
          <button class="btn btn-primary" type="button">登录</button>
          <button class="btn btn-primary" type="button">注册</button>
      </div>
      </div>
      <!-- end nav -->
    </div>
  </header>
</template>

<script>
export default {
  name: "HomeHeader"
};
</script>

<style scoped>
  .header_bg{
    background-color:#fff;
    height:80px;
  }
  .navbar-nav>li>a{
    line-height: 80px;
    padding: 0 15px 0 15px;
  }
  .header_login{
    width: 100%;
    line-height: 80px;
  }

  .header_login>button:first-child{
    margin-left: 300px;
  }
   .header_login>button:last-child{
    margin-left: 10px;
  }
  /* ul{
    margin-left: 200px;
  } */
</style>
