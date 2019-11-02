/**
 * 公共方法
 */

//  限制图片大小
const limitFileSize = (sourceId) => {
    //图片大小限制
    var fileSize = 0;
    var fileMaxSize = 1024;//1024k
    // 通过 input ID 获取到图片的大小
    fileSize = document.getElementById(sourceId).files.item(0).size;
    var size = fileSize / 1024; //转换成 KB
    if(size >= fileMaxSize){
        alert("文件大小不能大于 1 M！");
        return false;
    }else if(size <= 0){
        alert("文件大小不能小于 0 M！");
        return false;
    }
    return true;
}

/**
 * 显示图片
 * @param {*} sourceId  input ID
 * @param {*} targetId  imgage ID
 * @param {*} file      input Object
 */
export default function showImg(sourceId, targetId, file){
    var url = getFileUrl(sourceId);
    // var imgPre = document.getElementById(targetId);
    // imgPre.src = url;
    return url;
}

/**
 * 选取本地文件
 * @param {*} sourceId 
 */
function getFileUrl(sourceId) {
    var url;
    if (navigator.userAgent.indexOf("MSIE")>=1) { // IE
        //限制文件大小
        if(limitFileSize(sourceId)){
            // 照片的路径就是 input 的 value 的值
            url = document.getElementById(sourceId).value;
        }else{
            return;
        } 
    } else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox
        //限制文件大小
        if(limitFileSize(sourceId)){
            url = document.getElementById(sourceId).files.item(0);
        }else{
            return;
        } 
    } else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome
        //限制文件大小
        if(limitFileSize(sourceId)){
            url = document.getElementById(sourceId).files.item(0); 
        }else{
            return;
        }  
    }
    return url;
}



