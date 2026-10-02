<%@page import="CAMPS.Connect.DBConnect"%>
<%@page import="CAMPS.Connect.RODBConnect"%>
<%@page import="CAMPS.Common.PageHeader"%>
<%@page import="CAMPS.Common.SelectOptions"%> 
<%@page import="java.util.*"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="CAMPS.Admin.menu"%>

<link rel="icon" href="https://cdn.campusstack.in/favicon.ico" type="image/x-icon" />
<link href="https://fonts.googleapis.com/css?family=Roboto:400,700&subset=latin,cyrillic-ext&display=swap" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons&display=swap" rel="stylesheet" type="text/css">
<link href="https://cdn.campusstack.in/assets/plugins/bootstrap/css/bootstrap.css" rel="stylesheet" />
<link href="https://cdn.campusstack.in/assets/plugins/node-waves/waves.css" rel="stylesheet" />
<link href="https://cdn.campusstack.in/assets/css/additional_style.min.css" rel="stylesheet" />
<link href="https://cdn.campusstack.in/assets/css/noprint.css" rel="stylesheet" />
<link href="https://cdn.campusstack.in/assets/plugins/animate-css/animate.css" rel="stylesheet" />
<link href="https://cdn.campusstack.in/assets/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" />
<link href="https://cdn.campusstack.in/assets/plugins/multi-select/css/multi-select.css" rel="stylesheet" />
<link href="https://cdn.campusstack.in/assets/plugins/bootstrap-datepicker-master/css/bootstrap-datepicker3.css" rel="stylesheet" />
<link href="https://cdn.campusstack.in/assets/plugins/bootstrap-material-datetimepicker/css/bootstrap-material-datetimepicker.css" rel="stylesheet" />
<link href="https://cdn.campusstack.in/assets/css/style.min.css" rel="stylesheet">
<link href="https://cdn.campusstack.in/assets/css/theme.css" rel="stylesheet" />
<!-- AdminBSB Themes. -->
<script src="https://cdn.campusstack.in/assets/plugins/jquery/jquery.min.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/bootstrap/js/bootstrap.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/bootstrap-select/js/bootstrap-select.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-slimscroll/jquery.slimscroll.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/node-waves/waves.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-countto/jquery.countTo.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/bootstrap-notify/bootstrap-notify.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/momentjs/moment.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/bootstrap-datepicker-master/js/bootstrap-datepicker.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-validation/jquery-validate.bootstrap-tooltip.js"></script>
<script src="https://cdn.campusstack.in/assets/js/pages/CommonJSP/disable_back.js"></script>
<script type="text/javascript" src="https://cdn.campusstack.in/assets/plugins/tableExport.jquery.plugin-master/tableExport.js"></script>
<script type="text/javascript" src="https://cdn.campusstack.in/assets/plugins/tableExport.jquery.plugin-master/libs/js-xlsx/xlsx.core.min.js"></script>
<script type="text/javascript" src="https://cdn.campusstack.in/assets/plugins/tableExport.jquery.plugin-master/libs/FileSaver/FileSaver.min.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/bootpag/jquery.bootpag.js"></script>
<!-- CAMPUS STACK JS FILES -->
<script src="https://cdn.campusstack.in/assets/js/admin.js"></script>
<script src="https://cdn.campusstack.in/assets/js/pages/CommonJSP/notifications.js"></script>

<!-- Font Awesome Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<!-- Google Fonts (Inter) -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined&display=block">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap">

<%@include file="pageHeaderScripts.jsp" %>
<%@include file="pageHeaderPDFScripts.jsp" %>
<%@include file="mastercss.jsp" %>
<%@include file="pageHeaderStyles.jsp" %>
