<%@include file="../../CommonJSP/pageHeader.jsp" %>
<style>
    /* Chrome, Safari, Edge, Opera */
    input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
      -webkit-appearance: none;
      margin: 0;
    }
    
    /* Firefox */
    input[type=number] { -moz-appearance: textfield;
    }
    select {
      -webkit-appearance: none;
      -moz-appearance: none;
      text-indent: 1px;
      text-overflow: '';
    }
</style>

<script>

	function clearDivisions() {
		$('#loadDetailsUpdateUI').html("");
		$('#addBranchData').html(""); 
	}
	function loadDivisions() {
		loadBranchData();
		loadAddBranchData();
	}
    
    function loadBranchData() {
    	$('#loadDetailsUpdateUI').html("Loading.... please wait.....");
        var DataString =  'option=loadBranchData';
        //alert(DataString);
        $.ajax({
            url: "BranchMasterController.do", data: DataString, type: "post",
            success: function (data)
            {
                //alert(data);
                $('#loadDetailsUpdateUI').html(data); 
            }
        });
    }
    
    function loadAddBranchData() {
    	$('#addBranchData').html("Loading.... pls wait...");
        var DataString =  'option=loadAddBranchData';
        //alert(DataString);
        $.ajax({
            url: "BranchMasterController.do", data: DataString, type: "post",
            success: function (data)
            {
                //alert(data);
                $('#addBranchData').html(data); 
            }
        });
    }
    
    function addBranchData() {
		if (  $('#branch_code').val() !="" && $('#branch_name').val() != "") {
	        var DataString =  'branch_code=' + $('#branch_code').val() + '&branch_name='+ encodeURIComponent($('#branch_name').val()) + '&option=addBranchData';
	        //alert(DataString);
	        $.ajax({
	            url: "BranchMasterController.do", data: DataString, type: "post",
	            success: function (data)
	            {
	                alert(data);
	                if (data.startsWith("Error")) 
						alert(data);
	                else
	                	loadDivisions();
	            }
	        });
   		}
		else 
			alert("Please insert all values!!!");
    }
    
    function updateBranchData(field, branch_id) {
		var DataString = 'fname=' + field + '&fvalue='+ encodeURIComponent($('#' + field + branch_id).val()) + '&branch_id='+ branch_id + '&option=updateBranchData';
		//alert(DataString);
		$.ajax({
			url : "BranchMasterController.do",	data : DataString,		type : "post",
			success : function(data) {
				//alert(data);
				if (data.startsWith("Error")) 
					alert(data);
				
			}
		});
	}
    
    function freezeBranchData(branch_id) {
    	if (confirm("Are you sure want to Freeze this Branch?")) {
			var DataString = 'branch_id='+ branch_id + '&option=freezeBranchData';
			//alert(DataString);
			$.ajax({
				url : "BranchMasterController.do",	data : DataString,		type : "post",
				success : function(data) {
					loadDivisions();
					//alert(data);
					if (data.startsWith("Error")) 
						alert(data);
				}
			});
    	}
	}
    
    function deleteBranchData(branch_id) {
    	if (confirm("Are you sure want to Delete this Branch?")) {
			var DataString = 'branch_id='+ branch_id + '&option=deleteBranchData';
			//alert(DataString);
			$.ajax({
				url : "BranchMasterController.do",	data : DataString,		type : "post",
				success : function(data) {
					loadDivisions();
					//alert(data);
					if (data.startsWith("Error")) 
						alert(data);
					
				}
			});
    	}
	}
    
    </script>




<%    
	DBConnect db = new DBConnect();
    try {
        db.getConnection();
%>

<section class="content">
	<div class="row form-horizontal clearfix">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="card">
				<div class="header">
					<h2>Programmes</h2>
				</div>
				<div class="body">
					<div class="row clearfix">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group form-float" style="text-align: center">
										<button class="btn bg-orange waves-effect"  onclick="clearDivisions();loadDivisions()">Load Programme Data</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
        
    <div class="row clearfix">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="panel-group full-body" id="pgfb1" role="tablist">
				<div class="panel panel-col-blue">
					<div class="panel-heading" role="tab" id="ph1">
						<h4 class="panel-title">
							<a role="button" data-toggle="collapse" href="#pt1"	aria-expanded="true" aria-controls="pt1"> <i class="material-icons">view_comfy</i> Add Programme	</a>
						</h4>
					</div>
					<div id="pt1" class="panel-collapse collapse in">
						<div class="panel-body" id="addBranchData"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
        
    <div class="row clearfix">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="panel-group full-body" id="pgfb1" role="tablist">
				<div class="panel panel-col-blue">
					<div class="panel-heading" role="tab" id="ph2">
						<h4 class="panel-title">
							<a role="button" data-toggle="collapse" href="#pt2"	aria-expanded="true" aria-controls="pt2"> <i class="material-icons">view_comfy</i> Programme Details	</a>
						</h4>
					</div>
					<div id="pt2" class="panel-collapse collapse in">
						<div class="panel-body" id="loadDetailsUpdateUI"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

 </section>
<% } catch (Exception e) {
    } finally {
        db.closeConnection();
    }
%>

<%@include file="../../CommonJSP/pageFooter.jsp" %>