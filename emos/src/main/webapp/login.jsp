<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>用户登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="${pageContext.request.contextPath}/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="${pageContext.request.contextPath}/assets/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/admin/pages/css/login.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME STYLES -->
<link href="${pageContext.request.contextPath}/assets/global/css/components.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/global/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
<link id="style_color" href="${pageContext.request.contextPath}/assets/admin/layout/css/themes/default.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="media/image/favicon.ico"/>
</head>
<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices -->
<!-- DOC: Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default -->
<!-- DOC: Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle -->
<!-- DOC: Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-fixed" class to have fixed sidebar -->
<!-- DOC: Apply "page-footer-fixed" class to the body element to have fixed footer -->
<!-- DOC: Apply "page-sidebar-reversed" class to put the sidebar on the right side -->
<!-- DOC: Apply "page-full-width" class to the body element to have full width page without the sidebar menu -->
<body class="login">
<!-- BEGIN LOGO -->
<div class="logo">
	<span class="label" style="font-size:32px;font-weight: 800;font-family: 黑体;">菜单管理系统</span>
</div>
<!-- END LOGO -->
<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
<div class="menu-toggler sidebar-toggler">
</div>
<!-- END SIDEBAR TOGGLER BUTTON -->
<!-- BEGIN LOGIN -->
<div class="content">
	<!-- BEGIN LOGIN FORM -->
	<form:form action="${pageContext.request.contextPath}/login" method="post" commandName="user" cssClass="login-form">	
		<h3 class="form-title"><s:message code="login.form.title"/></h3>
		<c:if test="${not empty errorMsg}">
		<div class="alert alert-danger">
			<button class="close" data-close="alert"></button>
			<span>${errorMsg}</span>
		</div>
		</c:if>
		<c:if test="${not empty msg}">
		<div class="alert alert-success">
			<button class="close" data-close="alert"></button>
			<span style="font-size:10px">${msg}</span>
		</div>
		</c:if>
		<div class="form-group">
			<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
			<label class="control-label visible-ie8 visible-ie9"><s:message code="login.form.username"/></label>
			<div class="input-icon">
				<i class="fa fa-user"></i>				
				<form:input path="adminId" cssClass="form-control placeholder-no-fix" placeholder="" autocomplete="off" maxlength="50"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9"><s:message code="login.form.password"/></label>
			<div class="input-icon">
				<i class="fa fa-lock"></i>
				<form:password path="password" cssClass="form-control placeholder-no-fix" placeholder="" autocomplete="off"/>				
			</div>
		</div>
		<div class="form-actions">
			<label class="checkbox">
			<input type="checkbox" name="remember" value="1"/> <s:message code="login.form.rememberme"/> </label>
			<button type="submit" class="btn green pull-right">
			<s:message code="login.form.login"/> <i class="m-icon-swapright m-icon-white"></i>
			</button>
		</div>		
		<div class="forget-password">
			<h4>忘记密码？</h4>
			<p>
				 点击 <a href="javascript:;" id="forget-password">
				这里 </a>
				重置密码
			</p>
		</div>
		<div class="create-account">
		
		</div>
	</form:form>
	<!-- END LOGIN FORM -->
	<!-- BEGIN FORGOT PASSWORD FORM -->
	<form class="forget-form" action="${pageContext.request.contextPath}/common/ResetPassword" method="post">
		<h3>忘记密码 ?</h3>
		<p>
			输入邮箱，重置密码
		</p>
		<div class="form-group">
			<div class="input-icon">
				<i class="fa fa-envelope"></i>
				<input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Email" name="email"/>
			</div>
		</div>
		<div class="form-actions">
			<button type="button" id="back-btn" class="btn">
			<i class="m-icon-swapleft"></i> 返回 </button>
			<button type="submit" class="btn green pull-right">
			提交 <i class="m-icon-swapright m-icon-white"></i>
			</button>
		</div>
	</form>
	<!-- END FORGOT PASSWORD FORM -->
	
</div>
<!-- END LOGIN -->
<!-- BEGIN COPYRIGHT -->
<div class="copyright">
	 
</div>
<!-- END COPYRIGHT -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/assets/global/plugins/respond.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-1.11.0.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/global/plugins/select2/select2.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${pageContext.request.contextPath}/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/admin/pages/scripts/login.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script>
jQuery(document).ready(function() { 
  $("#adminId").attr("placeholder","<s:message code="login.form.username"/>");
  $("#password").attr("placeholder","<s:message code="login.form.password"/>");
  $("#adminId").focus();    
  Metronic.init(); // init metronic core components
  Layout.init(); // init current layout
  Login.init();
});
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>