<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%><%@ taglib prefix="s" uri="http://www.springframework.org/tags"%><!DOCTYPE html><!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]--><!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]--><!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]--><!-- BEGIN HEAD --><head><meta charset="utf-8"/><title><s:message code="system.publish" /></title><meta http-equiv="X-UA-Compatible" content="IE=edge"><meta content="width=device-width, initial-scale=1.0" name="viewport"/><meta content="" name="description"/><meta content="" name="author"/><!-- BEGIN GLOBAL MANDATORY STYLES --><link href="${pageContext.request.contextPath}/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/><link href="${pageContext.request.contextPath}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/><link href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/><link href="${pageContext.request.contextPath}/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/><link href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/><!-- END GLOBAL MANDATORY STYLES --><!-- BEGIN PAGE LEVEL STYLES --><link href="${pageContext.request.contextPath}/assets/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/><link href="${pageContext.request.contextPath}/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/><link href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css"/><link href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/><!-- END PAGE LEVEL STYLES --><!-- BEGIN THEME STYLES --><link href="${pageContext.request.contextPath}/assets/global/css/components.css" rel="stylesheet" type="text/css"/><link href="${pageContext.request.contextPath}/assets/global/css/plugins.css" rel="stylesheet" type="text/css"/><link href="${pageContext.request.contextPath}/assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/><link id="style_color" href="${pageContext.request.contextPath}/assets/admin/layout/css/themes/default.css" rel="stylesheet" type="text/css"/><link href="${pageContext.request.contextPath}/assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/><!-- END THEME STYLES --><link rel="shortcut icon" href="${pageContext.request.contextPath}/media/image/favicon.ico"/></head><!-- END HEAD --><!-- BEGIN BODY --><!-- DOC: Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices --><!-- DOC: Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default --><!-- DOC: Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle --><!-- DOC: Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle --><!-- DOC: Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle --><!-- DOC: Apply "page-sidebar-fixed" class to have fixed sidebar --><!-- DOC: Apply "page-footer-fixed" class to the body element to have fixed footer --><!-- DOC: Apply "page-sidebar-reversed" class to put the sidebar on the right side --><!-- DOC: Apply "page-full-width" class to the body element to have full width page without the sidebar menu --><body class="page-header-fixed">	<!-- BEGIN HEADER -->	<c:import url="/common/header"/>	<!-- END HEADER -->	<!-- BEGIN CONTAINER -->	<div class="page-container">		<!-- BEGIN SIDEBAR -->		<c:import url="/common/left"/>		<!-- END SIDEBAR -->		<!-- BEGIN CONTENT -->		<div class="page-content-wrapper">	
			<div class="page-content">												<div class="portlet">					<div class="portlet-title">						<div class="caption">							<s:message code="data.version" />						</div>					</div>					<div class="portlet-body">												<div  class="col-md-12">						<p><s:message code="un.publish.last" />:<font color="green" style="margin-left: 10px;font-weight: 2px;font-size: 14px">${res[0]}</font></p>						</div>																											<div  class="col-md-12">						<p><s:message code="publish.last" />:<font color="red" style="margin-left: 10px;font-weight: 2px;font-size: 14px">${res[2]}</font>&nbsp;&nbsp;&nbsp;&nbsp;					 	<c:if test="${flag eq '1' }">						 <button id ="PubBtn" type="button"  class="btn green btn-sm " >&nbsp;&nbsp;<s:message code="publish" />&nbsp;&nbsp;</button>						</c:if>						<c:if test="${flag eq '2' }">						 <button id ="PubBtn" type="button"  class="btn green btn-sm" style="display:none">&nbsp;&nbsp;<s:message code="publish" />&nbsp;&nbsp;</button>						</c:if>						</p>						</div>													<div  class="col-md-12">							 <p>发布时间:<font color="green" style="margin-left: 10px;font-weight: 2px;font-size: 14px">${res[1]}</font></p>						</div>												</div>										<div class="portlet-title">						<div class="caption"></div>					</div>					<div class="portlet-body">						<div class="well">							<h4>说明</h4>							<h5>1. 已发布的数据会自动推送到客户端设备。</h5>							<h5>2. 如果客户端设备在3天内未能联网获取数据，则需要在客户端点击：设置/数据更新</h5>						</div>																					</div>				</div>					<div id="msg"></div>				<!-- END PAGE TITLE & BREADCRUMB-->								<!-- BEGIN SEARCH FORM -->				<%-- <div class="portlet-body">									<form id="searchForm" name="searchForm" action="tableList" class="form-horizontal" method="post">										<div class="row">											<div class="col-md-4">												<div class="form-group">								<label class="col-md-3 control-label"><s:message code="device.tableName" /></label>								<div class="col-md-9">									<input name="tableName" type="text" class="form-control" maxLength="45">															</div>							</div>						</div>												<div class="col-md-4">								<div class="form-group">								<label class="col-md-3 control-label"><s:message code="device.status" /></label>								<div class="col-md-9">									<div class="radio-list">										<label class="radio-inline">										<input type="radio" name="onlineStatus" value="" checked/>All </label>										<label class="radio-inline">										<input type="radio" name="onlineStatus" value="true"/>OnLine </label>										<label class="radio-inline">										<input type="radio" name="onlineStatus" value="false"/>OffLine</label>									</div>																	</div>							</div>						</div>												<div class="col-md-4">								<div class="form-group">																<div class="col-md-offset-3 col-md-9">									<button type="submit" class="btn blue">Search <i class="fa fa-search"></i></button>									<button type="reset" class="btn grey-cascade">Reset <i class="fa fa-reply"></i></button>								</div>							</div>											</div>											</div>										</form>				</div> --%>				<!-- END SEARCH FORM -->								<!-- BEGIN PAGE CONTENT-->				<%-- <div class="row">					<div class="col-md-12">						<!-- BEGIN EXAMPLE TABLE PORTLET-->						<div class="portlet box green">							<div class="portlet-title">								<div class="caption">									<i class="fa fa-edit"></i><s:message code="device.list" />								</div>								<div class="actions">																	    <a class="btn btn-default btn-sm" data-toggle="modal" href="#delete_device" id="openDeleteDeviceModal"><i class="fa fa-trash-o"></i> <s:message code="all.table.delete" /></a>								    <div class="btn-group">										<a class="btn default" href="#" data-toggle="dropdown">										Columns <i class="fa fa-angle-down"></i>										</a>										<div id="column_toggler" class="dropdown-menu hold-on-click dropdown-checkboxes pull-right">											<label><input type="checkbox" checked data-column="0">Checkbox</label>											<label><input type="checkbox" data-column="1">ID</label>											<label><input type="checkbox" checked data-column="2"><s:message code="device.tableName" /></label>											<label><input type="checkbox" checked data-column="3"><s:message code="device.status" /></label>											<label><input type="checkbox" checked data-column="4"><s:message code="device.version" /></label>											<label><input type="checkbox" checked data-column="5"><s:message code="device.report.time" /></label>											<label><input type="checkbox" checked data-column="6"><s:message code="device.sync.time" /></label>										</div>									</div>								    																								</div>							</div>														<div class="portlet-body">																								<table class="table table-striped table-hover table-bordered" id="table_table">									<thead>										<tr>											<th class="table-checkbox">												<input type="checkbox" class="group-checkable" data-set="#device_table .checkboxes"/>											</th>											<th>ID</th>											<th><s:message code="device.tableName" /></th>											<th><s:message code="device.status" /></th>											<th><s:message code="device.version" /></th>											<th><s:message code="device.report.time" /></th>											<th><s:message code="device.sync.time" /></th>										</tr>									</thead>																														</table>							</div>						</div>						<!-- END EXAMPLE TABLE PORTLET-->					</div>				</div> --%>								<!-- END PAGE CONTENT -->								<!-- BEGIN ADD MODAL FORM-->												<!-- END ADD MODAL FORM-->								<!-- BEGIN Edit MODAL FORM-->								<!-- END EDIT MODAL FORM-->								<!-- BEGIN DELETE MODAL FORM-->				<div class="modal" id="delete_device" tabindex="-1" data-backdrop="static" data-keyboard="false">					<div class="modal-body">						<p>							<s:message code="system.management.user.deletemessage" />						</p>					</div>					<div class="modal-footer">						<button type="button" data-dismiss="modal" class="btn btn-default">Cancel</button>						<button id="deleteBtn" type="button" data-dismiss="modal" class="btn blue">Confirm</button>					</div>									</div>								<!-- END DELETE MODAL FORM-->
			</div>		
		</div>	</div>	
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<c:import url="/common/footer"/>
	<!-- END FOOTER -->	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->	<!-- BEGIN CORE PLUGINS -->	<!--[if lt IE 9]>	<script src="${pageContext.request.contextPath}/assets/global/plugins/respond.min.js"></script>	<script src="${pageContext.request.contextPath}/assets/global/plugins/excanvas.min.js"></script> 	<![endif]-->	<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-1.11.0.min.js" type="text/javascript"></script>	<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->	<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>	<script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>	<script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>	<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>	<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>	<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>	<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>	<script src="${pageContext.request.contextPath}/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>	<script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>	<!-- END CORE PLUGINS -->	<!-- BEGIN PAGE LEVEL PLUGINS -->	<script src="${pageContext.request.contextPath}/assets/global/plugins/select2/select2.min.js" type="text/javascript"></script>	<script src="${pageContext.request.contextPath}/assets/global/plugins/datatables/media/js/jquery.dataTables.js" type="text/javascript"></script>	<script src="${pageContext.request.contextPath}/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js" type="text/javascript"></script>	<script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>    <script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>	<!-- END PAGE LEVEL PLUGINS -->	<!-- BEGIN PAGE LEVEL SCRIPTS -->	<script src="${pageContext.request.contextPath}/assets/global/plugins/json/json2.js" type="text/javascript"></script>	<script src="${pageContext.request.contextPath}/assets/global/scripts/metronic.js" type="text/javascript"></script>	<script src="${pageContext.request.contextPath}/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>		<script src="${pageContext.request.contextPath}/static/js/deviceTableData.js"></script>	<script>	jQuery(document).ready(function() {       	   Metronic.init(); // init metronic core components	   Layout.init(); // init current layout		   DeviceTable.init("<c:url value="/"/>","${sessionScope.locale}");	   /* //runLoad(); */	});	</script>	<%-- <c:import url="/common/notice"/> --%></body>
<!-- END BODY -->
</html>