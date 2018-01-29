function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}
var userInfo = {};
var getUserInfoFromCookies = function () {
    return $.cookie("userInfo");
}

var renderUserInfo = function(){
    var userInfo = getUserInfoFromCookies();
    $("#nickname").html(userInfo.nickname);
    $("#headimg").attr("src", userInfo.headimgurl);
}


wx.config({
    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: '', // 必填，公众号的唯一标识
    timestamp: , // 必填，生成签名的时间戳
    nonceStr: '', // 必填，生成签名的随机串
    signature: '',// 必填，签名，见附录1
    jsApiList: [] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});
(function () {
    var authCode = getQueryVariable("code");
    alert("authCode ====" + authCode);
    if(!authCode){
        // window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx731f37de4b367a23&redirect_uri=https%3a%2f%2fwww.wangdasong.top%2findex%2findex.html&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
    }
    //WX登录
    $.ajax({
        type:"GET",
        url:"/api/wxBns/login",
        data:{
            authCode: authCode
        },
        success: function(data){
            alert(data.result);
            var access_token, expires_in, openid, scope;
            access_token = data.access_token;
            expires_in = data.expires_in;
            openid = data.openid;
            scope = data.scope;
            $.ajax({
                type: "GET",
                url: "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN",
                success: function (userData) {
                    userInfo.openid = userData.openid;
                    userInfo.nickname = userData.nickname;
                    userInfo.sex = userData.sex;
                    userInfo.province = userData.province;
                    userInfo.city = userData.city;
                    userInfo.country = userData.country;
                    userInfo.headimgurl = userData.headimgurl;
                    userInfo.privilege = userData.privilege;
                    userInfo.unionid = userData.unionid;
                    var expire= new Date();
                    //var expiresDate.setTime(expire.getTime() + (expires_in * 1000));
                    $.cookie("userInfo", userInfo, { expires: 1, path: '/' });
                    renderUserInfo();
                }
            });
        }
    });

}());
function gotoSecKillPage() {

}

function gotoSecKill() {
    wx.scanQRCode({
        needResult: 1,
        scanType : [ "qrCode"],
        success: function(scanResult) {
            var strResult = scanResult.resultStr;
            alert(strResult);
        }
    });
    /*
    var userInfo = getUserInfoFromCookies();
    var nickName = userInfo.nickname + "_" + userInfo.openid;
    $.ajax({
        url: "/api/token/check/",
        data:{
            wxCode: nickName
        },
        success: function(res){
            if(res.data == ""){
                wx.scanQRCode({
                    needResult: 1,
                    success: function(scanResult) {
                        var strResult = scanResult.resultStr;
                        $.ajax({
                            url: "/api/token/update/",
                            data:{
                                wxCode:nickName,
                                id: strResult
                            },
                            success: function(res2){
                                if(res2.data == ""){
                                    alert("您的二维码无效或已经被使用！");
                                }else{
                                    gotoSecKillPage();
                                }
                            }
                        });
                    }
                });
            }else{
                gotoSecKillPage();
            }
        }
    });
    */
}