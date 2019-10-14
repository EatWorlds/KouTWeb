<template>
  <header class="navbar navbar-fixed-top header_bg">
    <div class="container-fluid">
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
            <a href="#">专题套装</a>
          </li>
          <li class="active">
            <a href="#">|</a>
          </li>
          <li class="active" >
            <a href="#" style="color:#2254f4;font-weight:bold;">智能抠图</a>
          </li>
          <li class="active">
            <a href="#">联系我们</a>
          </li>
        </ul>
        <!-- Login Regist -->
        <div class="header_intro">
           <!-- <img src="../../../assets/images/introduce.png" srcset="https://cdn.dancf.com/gaodingx/www/image/video3x_8dd6639b.png 3x" alt="新手教程" class="header_novice"> -->
        </div>
        <div class="header_login">
          <div class="btn_grounp">
            <button 
              data-toggle="modal" 
              data-target="#loginModel" 
              class="btn btn-primary btn-login" 
              type="button" 
              @click="login">登录</button>
            <button 
              data-toggle="modal" 
              data-target="registeredModel" 
              class="btn btn-primary btn-registered" 
              type="button"
              @click="registereted">注册</button>
          </div>
      </div>
      </div>
      <!-- end nav -->
    </div>

    <!-- 登录模态框（Modal） -->
    <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="model model_login_title">
           <h3>账号密码登录</h3>
          <div class="modal-body">
             <form>
                <div class="form-group">
                  <label for="exampleInputEmail1">邮箱</label>
                  <input type="email" class="form-control inputTextStyle" id="InputEmail1" placeholder="请输入邮箱">
                </div>
                <div class="form-group">
                  <label for="exampleInputPassword1">密码</label>
                  <input type="password" class="form-control inputTextStyle" id="InputPassword1" placeholder="请输入6-30位密码">
                </div>
                <div class="form-group">
                  <button type="submit" class="btn btn-default btn_login">登录</button>
                </div>
                <div class="forgetPassword">
                  <a href="#" data-toggle="modal" data-dismiss="modal" data-target="#forgetPassword">忘记密码</a>
                  <a href="#" @click="setZinex" data-toggle="modal" data-dismiss="modal" data-target="#registeredModel">没有账号？去注册</a>
                </div>
            </form>
          </div>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal -->
    </div>

    <!-- 注册模态框（Modal） -->
    <div class="modal fade" id="registeredModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="model model_login_title">
           <h3>注册账号</h3>
          <div class="modal-body">
             <form id="addForm">
                <div class="form-group" enctype="multipart/form-data">
                  <label for="exampleInputEmail1">邮箱</label>
                  <input type="email" class="form-control inputTextStyle" id="rInputEmail1" placeholder="请输入邮箱">
                </div>
                <div class="form-group">
                  <input type="String " class="form-control inputNoCodeStyle" id="rInputCode" placeholder="请输入验证码">
                  <button 
                    id="btnCode"
                    style="margin-left:20px;" 
                    class="btn" 
                    type="button"
                    @click="getCode()"
                    >获取验证码</button>
                </div>
                <div class="form-group">
                  <input type="password" class="form-control inputNoTextStyle" id="rInputPassword" placeholder="请输入6-30位字母、数字的密码">
                </div>
                <div class="hintText" id="hintText" style="display: none">
                  <p>请输入6-30位字母、数字的密码</p>
                </div>
                <div class="form-group">
                  <button 
                  type="submit" 
                  class="btn btn-default btn_login"
                  @click="postRegistereted()"
                  >注册</button>
                </div>
                <div class="form-group">
                  <a href="#" data-toggle="modal" data-dismiss="modal" data-target="#loginModal">已有账号？去登陆</a>
                </div>
            </form>
          </div>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal -->
    </div>

    <!-- 找回密码 -->
    <div class="modal fade" id="forgetPassword" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="model model_login_title">
           <h3>忘记密码</h3>
          <div class="modal-body">
             <form id="addForm">
                <div class="form-group" enctype="multipart/form-data">
                  <label for="exampleInputEmail1">邮箱</label>
                  <input type="email" class="form-control inputTextStyle" id="rInputEmail1" placeholder="请输入邮箱">
                </div>
                <div class="form-group">
                  <input type="String " class="form-control inputNoCodeStyle" id="rInputCode" placeholder="请输入验证码">
                  <button 
                    id="btnCode"
                    style="margin-left:20px;" 
                    class="btn" 
                    type="button"
                    @click="getCode()"
                    >获取验证码</button>
                </div>
                <div class="form-group">
                  <a href="#"
                  class="btn btn-default firstNext" 
                  data-toggle="modal" 
                  data-dismiss="modal" 
                  data-target="#updatePassword"
                  >下一步</a>
                </div>
                <div class="form-group">
                  <a href="#" 
                  data-toggle="modal" 
                  data-dismiss="modal" 
                  data-target="#loginModal"
                  >已有账号？去登陆</a>
                </div>
            </form>
          </div>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal -->
    </div>

    <!-- 修改密码 -->
    <div class="modal fade" id="updatePassword" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="model model_login_title">
           <h3>修改密码</h3>
          <div class="modal-body">
             <form id="addForm">
                <div class="form-group">
                  <input type="password" class="form-control inputNoTextStyle" id="updatePassword" placeholder="请输入6-30位字母、数字的密码">
                </div>
                <div class="hintText" id="hintText" style="display: none">
                  <p>请输入6-30位字母、数字的密码</p>
                </div>
                <!-- 确认密码 -->
                <div class="form-group">
                  <input type="password" class="form-control inputNoTextStyle" id="confirmPassword" placeholder="确认密码">
                </div>
                <div class="hintText" id="hintText" style="display: none">
                  <p>两次密码输入不一致</p>
                </div>
                <div class="form-group">
                  <button 
                  type="submit" 
                  class="btn btn-default btn_login"
                  >确认</button>
                </div>
                <div class="form-group">
                  <a href="#" data-toggle="modal" data-dismiss="modal" data-target="#loginModal">已有账号？去登陆</a>
                </div>
            </form>
          </div>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal -->
    </div>
  </header>
</template>

<script>

export default {
  name: "HomeHeader",
  methods: {
    // 登录弹框
    login() {
      $('#loginModal').modal({
        keyboard: true
      })
    },

    // 注册弹框
    registereted() {
       $('#registeredModel').modal({
        keyboard: true
      })
    },

    // 获取邮箱验证码
    getCode() {
      let number = 60;
      var countdown = function(){
        if (number == 0) {
          $('#btnCode').attr("disabled",false);
          $('#btnCode').html("发送验证码");
          number = 60;
          return;
        } else {
          $('#btnCode').attr("disabled",true);
          $('#btnCode').html(number + "秒 重新发送");
          number--;
        }
        setTimeout(countdown,1000);
      }
		  setTimeout(countdown,1000);
    },

    // 注册/登录验证
    verification(value) {
      if(!(/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/.test())){
        $('#hintText').show();
      }else{
        $('#hintText').hide();
      }
    },

    // 进行注册
    postRegistereted() {
      // 注册验证
      this.verification($('#rInputPassword').val());
      // axios.post('/user', {
      //   email: $('#rInputEmail').val(), // 邮箱 
      //   password:  $('#rInputPassword').val(), // 密码
      //   code: $('#rInputCode').val()    // 验证码
      // })
      // .then(function (res) {
      //   console.log(res)
      // })
      // .catch(function (error) {
      //   console.log(error)
      // })
    },

    // 进行登录
    postLogin() {
      // 登录验证
      this.verification($('#InputPassword').val());

      // axios.post('/user', {
      //   email: $('#InputEmail').val(), // 邮箱 
      //   password:  $('#InputPassword').val(), // 密码
      // })
      // .then(function (res) {
      //   console.log(res)
      // })
      // .catch(function (error) {
      //   console.log(error)
      // })
    }
  }
};

</script>

<style scoped>
  /* 公共样式 */
  a{
    color:#000;
  }

  ul{
    margin-left: 100px;
  }

  /* 模态框位置 */
  .modal-content{
    left: 100px;
  }

  /* 单独样式 */
  .header_bg{
    background-color:#fff;
    height:80px;
    margin-bottom: 0;
    z-index: 1050;
  }

  .navbar-nav>li>a{
    line-height: 80px;
    padding: 0 15px 0 15px;
  }

  .header_login{
    position: relative;
    width: 100%;
    line-height: 80px;
  }

  /* 新手教程 */
  .header_novice{
    width: 117px;
    cursor: pointer;
  }

  /* 登录注册 */
  .btn_grounp{
    position: absolute;
    right: 100px;
  }

  /* 提示文字 */
  .hintText{
    margin-left: 35px;
    font-size: 12px;
    color:red;
  }

  /* 忘记密码文字 */
  .forgetPassword{
    color: red;
    margin-left: 40px;
  }

  .forgetPassword a:last-child{
    margin-left: 170px;
  }

  .btn-registered{
    margin-left:10px;
  }

  .header_login .btn-primary{
    background: #2263fe;
    color: #fff;
  }

  .modal-content{
    position: relative;
    top: 100px;
    width: 450px;
    height: 550px;
  }

  .model_login_title h3{
    margin: 60px 0;
    font-size: 25px;
    font-weight: bold;
    text-align: center;
  }

  /* 修改表单组的样式 */
  .form-group{
    position: relative;
    display: flex;
    flex-direction: row;
    justify-content: center;
  }

  /* 改变变淡组下的 label 文字 */
  .form-group label{
    position: absolute;
    left: 45px;
    display: flex;
    align-items: center;
    font-size: 16px;
    width: 50px;
    height: 50px;
  }

  /* 有 label 的输入框 */
  .inputTextStyle{
    width: 350px;
    height: 50px;
    padding-left: 60px;
  }

  /* 没有 label 的验证码框 */
  .inputNoCodeStyle{
    width: 230px;
    height: 50px;
  }

  /* 没有 label 的输入框 */
  .inputNoTextStyle{
    width: 350px;
    height: 50px;
  }

  /* 登录按钮 */
  .btn_login{
    width: 350px;
    height: 50px;
    font-size: 18px;
    font-weight: bold;
    color: #fff;
    background: #2263fe;
  }

  .firstNext{
    display: flex;
    align-items: center;
    justify-content: center;
    width: 350px;
    height: 50px;
    font-size: 18px;
    font-weight: bold;
    color: #fff;
    background: #2263fe;
  }

</style>
