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
(function () {
    var authCode = getQueryVariable(code);
    alert("authCode ====" + authCode);
    if(!authCode){
        window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx731f37de4b367a23&redirect_uri=https://www.wangdasong.top/index/index.html&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
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
                    var expiresDate.setTime(expire.getTime() + (expires_in * 1000));
                    $.cookie("userInfo", userInfo, { expires: expiresDate, path: '/' });
                    renderUserInfo();
                }
            });
        }
    });

}());
function gotoSecKillPage() {

}

function gotoSecKill() {
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
                    needResult: 0,
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
}