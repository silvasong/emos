<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title><s:message code="system.set"></s:message></title>
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
<link href="${pageContext.request.contextPath}/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/global/plugins/select2/select2.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-editable/bootstrap-editable/css/bootstrap-editable.css" />
<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="${pageContext.request.contextPath}/assets/global/css/components.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/global/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
<link id="style_color" href="${pageContext.request.contextPath}/assets/admin/layout/css/themes/default.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/media/image/favicon.ico"/>
</head>
<!-- END HEAD -->
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
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<c:import url="/common/header"/>
	<!-- END HEADER -->
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<c:import url="/common/left"/>
		<!-- END SIDEBAR -->
		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">	
			<div class="page-content">								
				<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				 <div class="page-bar">
					<%--<ul class="page-breadcrumb">
						<li>
							<i class="fa fa-home"></i>
							<a href="<c:url value="/"/>home"><s:message code="home"/></a>
							<i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="<c:url value="/"/>settings"><s:message code="setting"/></a>
							
						</li>
					</ul>--%>					
				</div> 
				<!-- END PAGE TITLE & BREADCRUMB-->
				<div class="row">
					<div class="col-md-12">
						<button id="enable" class="btn blue"><s:message code="enable.disable"/></button>
						<hr>
					</div>
				</div>
				<!-- BEGIN PAGE CONTENT-->
				<div class="row">
					<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box green">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-edit"></i><s:message code="settingtable"/>
								</div>
								
							</div>							
							<div class="portlet-body">																
								<table id="system_setting" class="table table-bordered table-striped">
									<tbody>
										<tr>
											<td style="width: 15%">Email Username</td>
											<td style="width: 50%"><a href="#" id="email_username"
												data-type="text" data-pk="1"
												data-original-title="Enter Email Username"> ${system_setting['Email_Username'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted">
													${system_setting['Email_Username'][1]} </span></td>
										</tr>
										<tr>
											<td style="width: 15%">Email Password</td>
											<td style="width: 50%"><a href="#" id="email_password"
												data-type="password" data-pk="1"
												data-original-title="Enter Email Password"> [hidden] </a></td>
											<td style="width: 35%"><span class="text-muted">
													${system_setting['Email_Password'][1]} </span></td>
										</tr>
										<tr>
											<td style="width: 15%">Email Host</td>
											<td style="width: 50%"><a href="#" id="email_host"
												data-type="text" data-pk="1"
												data-original-title="Enter Email Host"> ${system_setting['Email_Host'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted">
													${system_setting['Email_Host'][1]} </span></td>
										</tr>
										<tr>
											<td style="width: 15%">Max Login Error Times</td>
											<td style="width: 50%"><a href="#" id="max_login_error_times"
												data-type="text" data-pk="1"
												data-original-title="Enter Max_Login_Error_Times"> ${system_setting['Max_Login_Error_Times'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted">
													${system_setting['Max_Login_Error_Times'][1]} </span></td>
										</tr>
										<tr>
											<td style="width: 15%">Login ErrorLocked</td>
											<td style="width: 50%"><a href="#" id="login_error_locked"
												data-type="text" data-pk="1"
												data-original-title="Enter Login_Error_Locked"> ${system_setting['Login_Error_Locked'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted">
													${system_setting['Login_Error_Locked'][1]} </span></td>
										</tr>
										<tr>
											<td style="width: 15%">Push Android API Key </td>
											<td style="width: 50%"><a href="#" id="push_android_api_key"
												data-type="text" data-pk="1"
												data-original-title="Enter Push_Android_API_Key"> ${system_setting['Push_Android_API_Key'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted">
													${system_setting['Push_Android_API_Key'][1]} </span></td>
										</tr>
										<tr>
											<td style="width: 15%">Push Android Secret Key</td>
											<td style="width: 50%"><a href="#" id="push_android_secret_key"
												data-type="text" data-pk="1"
												data-original-title="Enter Push_Android_Secret_Key"> ${system_setting['Push_Android_Secret_Key'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted">
													${system_setting['Push_Android_Secret_Key'][1]} </span></td>
										</tr>
										<tr>
											<td style="width: 15%">Push IOS API Key</td>
											<td style="width: 50%"><a href="#" id="push_ios_api_key"
												data-type="text" data-pk="1"
												data-original-title="Enter Push_IOS_API_Key"> ${system_setting['Push_IOS_API_Key'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted">
													${system_setting['Push_IOS_API_Key'][1]} </span></td>
										</tr>
										
										<tr>
											<td style="width: 15%">Push IOS Secret Key</td>
											<td style="width: 50%"><a href="#" id="push_ios_secret_key"
												data-type="text" data-pk="1"
												data-original-title="Enter Push_IOS_Secret_Key"> ${system_setting['Push_IOS_Secret_Key'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted">
													${system_setting['Push_IOS_Secret_Key'][1]} </span></td>
										</tr>
										
										<tr>
											<td style="width: 15%">Alipay Pid</td>
											<td style="width: 50%"><a href="#" id="push_ios_secret_key"
												data-type="text" data-pk="1"
												data-original-title="Enter Alipay Pid"> ${system_setting['ZFB_PID'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted">
													${system_setting['ZFB_PID'][1]} </span></td>
										</tr>
										
										<tr>
											<td style="width: 15%">Alipay Key</td>
											<td style="width: 50%"><a href="#" id="push_ios_secret_key"
												data-type="text" data-pk="1"
												data-original-title="Enter Alipay Key"> ${system_setting['ZFB_KEY'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted">
													${system_setting['ZFB_KEY'][1]} </span></td>
										</tr>
										
										<tr>
											<td style="width: 15%">Rest Pwd Valid Time</td>
											<td style="width: 50%"><a href="#" id="push_ios_secret_key"
												data-type="text" data-pk="1"
												data-original-title="Enter Rest_Pwd_Valid_Time"> ${system_setting['Rest_Pwd_Valid_Time'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted">
													${system_setting['Rest_Pwd_Valid_Time'][1]} </span></td>
										</tr>
										
									</tbody>
								</table>
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</div>
				<!-- END PAGE CONTENT -->
			</div>		
		</div>
	</div>	
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<c:import url="/common/footer"/>
	<!-- END FOOTER -->
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
	<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="${pageContext.request.contextPath}/assets/global/plugins/select2/select2.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/plugins/datatables/media/js/jquery.dataTables.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
    <script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/global/plugins/select2/select2.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/global/plugins/moment.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/global/plugins/jquery.mockjax.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-editable/bootstrap-editable/js/bootstrap-editable.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-editable/inputs-ext/wysihtml5/wysihtml5.js"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${pageContext.request.contextPath}/assets/global/plugins/json/json2.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/scripts/metronic.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>	
	<script src="${pageContext.request.contextPath}/static/js/settingTableData.js"></script>
	<script>
	jQuery(document).ready(function() {       
	   Metronic.init(); // init metronic core components
	   Layout.init(); // init current layout	
	   //Demo.init(); // init demo features
	   SettingTable.init("<c:url value="/"/>");   
	});
	</script>
	<c:import url="/common/notice"/>
</body>
<!-- END BODY -->

</html>