<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>Promotion Addition</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link
	href="../assets/global/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="../assets/global/plugins/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link href="../assets/global/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="../assets/global/plugins/uniform/css/uniform.default.css"
	rel="stylesheet" type="text/css" />
<link
	href="../assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<link rel="stylesheet" type="text/css"
	href="../assets/global/plugins/clockface/css/clockface.css" />
<link rel="stylesheet" type="text/css"
	href="../assets/global/plugins/bootstrap-datepicker/css/datepicker3.css" />
<link rel="stylesheet" type="text/css"
	href="../assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css" />
<link rel="stylesheet" type="text/css"
	href="../assets/global/plugins/bootstrap-colorpicker/css/colorpicker.css" />
<link rel="stylesheet" type="text/css"
	href="../assets/global/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css"
	href="../assets/global/plugins/bootstrap-datetimepicker/css/datetimepicker.css" />
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="../assets/global/plugins/select2/select2.css"
	rel="stylesheet" type="text/css" />
<link
	href="../assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<link
	href="../assets/global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css"
	rel="stylesheet" type="text/css" />
<link
	href="../assets/global/plugins/bootstrap-modal/css/bootstrap-modal.css"
	rel="stylesheet" type="text/css" />
<link
	href="../assets/global/plugins/datatables/extensions/TableTools/css/dataTables.tableTools.min.css"
	rel="stylesheet" type="text/css" />

<!-- END PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css"
	href="../assets/global/plugins/bootstrap-select/bootstrap-select.min.css" />
<link rel="stylesheet" type="text/css"
	href="../assets/global/plugins/select2/select2.css" />
<link rel="stylesheet" type="text/css"
	href="../assets/global/plugins/jquery-multi-select/css/multi-select.css" />

<!-- BEGIN THEME STYLES -->
<link href="../assets/global/css/components.css" rel="stylesheet"
	type="text/css" />
<link href="../assets/global/css/plugins.css" rel="stylesheet"
	type="text/css" />
<link href="../assets/admin/layout/css/layout.css" rel="stylesheet"
	type="text/css" />
<link id="style_color"
	href="../assets/admin/layout/css/themes/default.css" rel="stylesheet"
	type="text/css" />
<link href="../assets/admin/layout/css/custom.css" rel="stylesheet"
	type="text/css" />
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="../media/image/favicon.ico" />
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
	<c:import url="/common/header" />
	<!-- END HEADER -->
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<c:import url="/common/left" />
		<!-- END SIDEBAR -->
		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">
			<div class="page-content">
				<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				 <div class="page-bar">
					<%--<ul class="page-breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="<c:url value="/"/>home"><s:message code="home"/></a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="<c:url value="/"/>add_promotion">Promotion Addition</a></li>

					</ul>--%>
				</div> 
				<!-- END PAGE TITLE & BREADCRUMB-->
				<div id="spin" class="display-hide"></div>
				<!-- BEGIN PAGE CONTENT-->

				<!-- BEGIN SEARCH FORM -->
				<div class="portlet-body">
					<form action="" class="horizontal-form" id="promotion_form"
						method="post">
						<div class="form-body">
							<div class="row">
								<div class="col-md-12 ">
									<div class="form-group">
										<label>Promotion Type</label> <label class="radio-inline">
											<input type="radio" name="way" value="0" checked />Straight
											Down
										</label> <label class="radio-inline"> <input type="radio"
											name="way" value="1" />The Full Reduction
										</label> <label class="radio-inline"> <input type="radio"
											name="way" value="2" />Discount
										</label><label class="radio-inline"> <input type="radio"
											name="way" value="3" />Combination
										</label><label class="radio-inline"> <input type="radio"
											name="way" value="4" />X Give Y
										</label> <label class="radio-inline"> <input type="radio"
											name="way" value="5" />M For Y Discount
										</label> <label class="radio-inline"> <input type="radio"
											name="way" value="6" />Custom
										</label>

									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label>Promotion Name</label> <input name="promotionName"
											type="text" class="form-control">
									</div>
								</div>
								<!--/span-->
							</div>
							<!--/row-->
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label>Promotion Time</label>
										<div class="row">
											<div class="col-md-5">

												<input type="text" size="16" readonly
													class="form-control date form_datetime" name="startTime">


											</div>
											<div class="col-md-2">To</div>
											<div class="col-md-5">

												<input type="text" size="16" readonly
													class="form-control date form_datetime" name="endTime">


											</div>
										</div>

									</div>
								</div>
								<!--/span-->

							</div>
							<!--/row-->
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label>Promotion Rule</label>
										<div id="type_SalesRule_show">


											<div class="col-md-5">
												<label>Straight down: the rules of commodity, single
													commodity in the actual price basis, reduction</label>
											</div>
											<div class="col-md-1">
												<input type="text" class="form-control" name="paramOne" />
											</div>
											<div class="col-md-1">
												<label>$.</label>
											</div>
										</div>


									</div>

								</div>
							</div>
							<div class="row">
								<div class="form-group"></div>
							</div>
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label>Product</label> <label class="radio-inline"> <input
											type="radio" name="bindType" value="0" checked />ALL
										</label> <label class="radio-inline"> <input type="radio"
											name="bindType" value="1" />Classification
										</label> <label class="radio-inline"> <input type="radio"
											name="bindType" value="2" />Menu
										</label> <label class="radio-inline"> <input type="radio"
											name="bindType" value="3" />Goods
										</label>

									</div>
								</div>
								<!--/span-->
								<div class="col-md-4">
									<div class="form-group">
										<div class="col-md-3 hidden" id="classification">
											<input type="hidden" id="cla" style="width: 420px;"
												name="claId" />
										</div>
										<div class="col-md-3 hidden" id="menu">
											<input type="hidden" id="men" style="width: 420px;"
												name="menId" />
										</div>
										<div class="col-md-3 hidden" id="goods">
											<input type="hidden" id="goo" style="width: 420px;"
												name="gooId" />
										</div>

									</div>
								</div>
							</div>


							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label>Shared</label> <label class="radio-inline"> <input
											type="radio" name="shared" value="true" checked />Yes
										</label> <label class="radio-inline"> <input type="radio"
											name="shared" value="false" />No
										</label>
									</div>
								</div>
								<!--/span-->


								<div class="col-md-4">
									<div class="form-group">
										<label>Status</label> <label class="radio-inline"> <input
											type="radio" name="status" value="0" checked />False
										</label> <label class="radio-inline"> <input type="radio"
											name="status" value="1" />True
										</label>

									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label>Priority</label>
										<div id="spinner3">
											<div class="input-group" style="width: 150px;">
												<input type="text" class="spinner-input form-control"
													maxlength="3" name="priority" readonly>
												<div class="spinner-buttons input-group-btn">
													<button type="button" class="btn spinner-up default">
														<i class="fa fa-angle-up"></i>
													</button>
													<button type="button" class="btn spinner-down default">
														<i class="fa fa-angle-down"></i>
													</button>
												</div>
											</div>
										</div>
									</div>
								</div>
								<!--/span-->

							</div>
						</div>

						<div class="form-actions right">
							<div class="row">
								<div class="col-md-2"></div>
								<div class="col-md-4">
									<a class="btn grey-cascade" href="<c:url value="/"/>promotion"><i class="fa fa-reply"></i> Back</a>
									<button type="submit" class="btn blue">
										<i class="fa fa-check"></i> Save
									</button>
								</div>
							</div>

						</div>
					</form>
				</div>
				<!-- END SEARCH FORM -->
				<!-- END PAGE CONTENT -->




			</div>
		</div>
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<c:import url="/common/footer" />
	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
	<script src="../assets/global/plugins/respond.min.js"></script>
	<script src="../assets/global/plugins/excanvas.min.js"></script> 
	<![endif]-->
	<script src="../assets/global/plugins/jquery-1.11.0.min.js"
		type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery-migrate-1.2.1.min.js"
		type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script
		src="../assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js"
		type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js"
		type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery.blockui.min.js"
		type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery.cokie.min.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js"
		type="text/javascript"></script>
	<script src="../assets/global/plugins/uniform/jquery.uniform.min.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js"
		type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="../assets/global/plugins/select2/select2.min.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/datatables/media/js/jquery.dataTables.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/bootstrap-modal/js/bootstrap-modal.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/datatables/extensions/TableTools/js/dataTables.tableTools.min.js"
		type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<script type="text/javascript"
		src="../assets/global/plugins/bootstrap-select/bootstrap-select.min.js"></script>
	<script type="text/javascript"
		src="../assets/global/plugins/select2/select2.min.js"></script>
	<script type="text/javascript"
		src="../assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
	<script type="text/javascript"
		src="../assets/global/plugins/fuelux/js/spinner.min.js"></script>
	<script type="text/javascript"
		src="../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="../assets/global/plugins/json/json2.js"
		type="text/javascript"></script>
	<script src="../assets/global/scripts/metronic.js"
		type="text/javascript"></script>
	<script src="../assets/admin/layout/scripts/layout.js"
		type="text/javascript"></script>
	<script src="../assets/global/plugins/plupload/js/plupload.full.min.js"
		type="text/javascript"></script>

	<script src="../assets/global/plugins/plupload/js/plupload.full.min.js"
		type="text/javascript"></script>

	<script src="../static/js/addpromotion.js" type="text/javascript"></script>


	<script>
		jQuery(document).ready(function() {
			Metronic.init(); // init metronic core components
			Layout.init(); // init current layout	
			AddPromotion.init("<c:url value="/"/>");

		});
	</script>
<c:import url="/common/notice"/>

</body>
<!-- END BODY -->

</html>