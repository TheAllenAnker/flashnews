<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>FlashNews - shadder</title>
    <meta name="viewport"
          content="width=device-width, minimum-scale=1.0, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="keywords" content="读《Web 全栈工程师的自我修养》">
    <meta name="description" content="阅读影浅分享的读《Web 全栈工程师的自我修养》">

    <link rel="stylesheet" type="text/css" href="/styles/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/styles/font-awesome.min.css">

    <link rel="stylesheet" media="all" href="/styles/style.css">

    <script src="/scripts/hm.js"></script>
    <script src="/scripts/detail.js"></script>

    <script type="text/javascript" src="/scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery.qrcode.min.js"></script>
</head>
<body class="welcome_index">

<#include "header.ftl">

<div id="main">
    <!--
        <div class="hero">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="app-iphone">
                            <div class="carousel slide" data-ride="carousel" data-interval="3000">
                                <div class="carousel-inner" role="listbox">
                                    <div class="item">
                                      <img alt="牛客网应用截图阅读精选" src="http://images.nowcoder.com/images/20141231/622873_1420036789276_622873_1420036771761_%E8%98%91%E8%8F%87.jpg@0e_200w_200h_0c_1i_1o_90Q_1x">
                                      <div class="carousel-caption">阅读精选</div>
                                    </div>
                                    <div class="item active">
                                      <img alt="牛客网应用截图订阅主题" src="http://images.nowcoder.com/images/20141231/622873_1420036789276_622873_1420036771761_%E8%98%91%E8%8F%87.jpg@0e_200w_200h_0c_1i_1o_90Q_1x">
                                      <div class="carousel-caption">订阅主题</div>
                                    </div>
                                    <div class="item">
                                      <img alt="牛客网应用截图分享干货" src="http://images.nowcoder.com/images/20141231/622873_1420036789276_622873_1420036771761_%E8%98%91%E8%8F%87.jpg@0e_200w_200h_0c_1i_1o_90Q_1x">
                                      <div class="carousel-caption">分享干货</div>
                                    </div>
                                    <div class="item">
                                      <img alt="牛客网应用截图兑换礼品" src="http://images.nowcoder.com/images/20141231/622873_1420036789276_622873_1420036771761_%E8%98%91%E8%8F%87.jpg@0e_200w_200h_0c_1i_1o_90Q_1x">
                                      <div class="carousel-caption">兑换礼品</div>
                                    </div>
                                    <div class="item">
                                      <img alt="牛客网应用截图建立品牌" src="http://images.nowcoder.com/images/20141231/622873_1420036789276_622873_1420036771761_%E8%98%91%E8%8F%87.jpg@0e_200w_200h_0c_1i_1o_90Q_1x">
                                      <div class="carousel-caption">建立品牌</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-6">
                        <div class="intro">
                            <div class="title">
                              <h1>牛客网</h1>
                              <h3>程序员必装的 App</h3>
                            </div>
                            <div class="media">
                                <div class="media-left">
                                    <img class="media-object app-qrcode" src="../images/res/qrcode.png" alt="App qrcode web index">
                                </div>
                                <div class="media-body">
                                    <div class="buttons">
                                      <p><a onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;app&#39;, &#39;download&#39;, &#39;ios&#39;])" class="btn btn-success btn-lg" href="http://nowcoder.com/s/ios"><i class="fa icon-apple"></i> iPhone 版</a></p>
                                      <p><a onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;app&#39;, &#39;download&#39;, &#39;apk&#39;])" class="btn btn-success btn-lg" href="http://nowcoder.com/s/apk"><i class="fa icon-android"></i> Android 版</a></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        -->

    <div class="container" id="daily">
        <div class="jscroll-inner">
        <div class="daily">

                    <#assign cur_date = ''>
                    <#list vos as vo>
                        <#if cur_date != (vo.news.createdDate?string('yyyy-MM-dd'))>
                            <#if vo_index gt 0>
                            </div> ## 上一个要收尾
                            </#if>
                            <#assign current_node = (vo.news.createdDate?string('yyyy-MM-dd'))>
                    <h3 class="date">
                        <i class="fa icon-calendar"></i>
                        <span>FlashNews &nbsp; ${vo.news.createdDate?string('yyyy-MM-dd')}</span>
                    </h3>

                    <div class="posts">
                        </#if>
                        <div class="post">
                            <div class="votebar">
                                <#if (vo.like = 1)>
                                <button class="click-like up pressed" aria-pressed="false" title="赞同"><i
                                        class="vote-arrow"></i><span
                                        class="count">${vo.news.likeCount}</span></button>
                                <#else>
                                <button class="click-like up" aria-pressed="false" title="赞同"><i class="vote-arrow"></i><span
                                        class="count">${vo.news.likeCount}</span></button>
                                </#if>
                                <#if (vo.like = -1)>
                                <button class="click-dislike down pressed" aria-pressed="true" title="反对"><i
                                        class="vote-arrow"></i>
                                </button>
                                <#else>
                                <button class="click-dislike down" aria-pressed="true" title="反对"><i
                                        class="vote-arrow"></i>
                                </button>
                                </#if>
                            </div>
                            <div class="content" data-url="${vo.news.link}">
                                <div>
                                    <img class="content-img" src="${vo.news.image}" alt="">
                                </div>
                                <div class="content-main">
                                    <h3 class="title">
                                        <a target="_blank" rel="external nofollow"
                                           href="${vo.news.link}">${vo.news.title}</a>
                                    </h3>
                                    <div class="meta">
                                        ${vo.news.link}
                                        <span>
                                            <i class="fa icon-comment"></i> ${vo.news.commentCount}
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="user-info">
                                <div class="user-avatar">
                                    <a href="#"><img width="32" class="img-circle" src="${vo.user.headUrl}"></a>
                                </div>

                                <!--
                                <div class="info">
                                    <h5>分享者</h5>

                                    <a href="http://nowcoder.com/u/251205"><img width="48" class="img-circle" src="http://images.nowcoder.com/images/20141231/622873_1420036789276_622873_1420036771761_%E8%98%91%E8%8F%87.jpg@0e_200w_200h_0c_1i_1o_90Q_1x" alt="Thumb"></a>

                                    <h4 class="m-b-xs">冰燕</h4>
                                    <a class="btn btn-default btn-xs" href="http://nowcoder.com/signin"><i class="fa icon-eye"></i> 关注TA</a>
                                </div>
                                -->
                            </div>

                            <div class="subject-name">来自 <a href="#">${vo.user.name}</a></div>
                        </div>

                        <!--
                        <div class="alert alert-warning subscribe-banner" role="alert">
                          《头条八卦》，每日 Top 3 通过邮件发送给你。      <a class="btn btn-info btn-sm pull-right" href="http://nowcoder.com/account/settings">立即订阅</a>
                        </div>vo
                        -->
                    <#--最后有个元素要收尾-->
                        <#if !vo_has_next>
                    </div>
                        </#if>

                    </#list>


        </div>
    </div>
</div>

</div>

<#include "footer.ftl">

<div id="quick-download">
    <button type="button" class="close-link btn-link" data-toggle="modal" data-target="#quick-download-app-modal"><i
            class="fa icon-times-circle"></i></button>

    <a class="download-link" href="http://nowcoder.com/download">
        <h3>牛客网</h3>
        <h4>程序员的首选学习分享平台</h4>
        <button type="button" class="btn btn-info btn-sm">下载 APP</button>
    </a>

    <div class="modal fade" id="quick-download-app-modal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">提示</div>
                <div class="modal-body">
                    <div class="checkbox">
                        <label class="i-checks">
                            <input id="already-installed" type="checkbox"><i></i> 我已安装了牛客网App，不再显示
                        </label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-block btn-default" id="close-quick-download-app-modal">关 闭
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<#if pop??>
    <script>window.loginpop = ${pop};</script>
</#if>
<script type="text/javascript" src="/scripts/jquery.js"></script>
<script type="text/javascript" src="/scripts/main/base/base.js"></script>
<script type="text/javascript" src="/scripts/main/base/util.js"></script>
<script type="text/javascript" src="/scripts/main/base/event.js"></script>
<script type="text/javascript" src="/scripts/main/component/component.js"></script>
<script type="text/javascript" src="/scripts/main/component/popup.js"></script>
<script type="text/javascript" src="/scripts/main/component/popupLogin.js"></script>
<script type="text/javascript" src="/scripts/main/site/home.js"></script>


</body>
</html>