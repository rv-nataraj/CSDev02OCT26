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
		$('#addDesignationData').html(""); 
	}
	function loadDivisions() {
		loadDesignationData();
		loadAddDesignationData();
	}
    
    function loadDesignationData() {
        var DataString =  'option=loadDesignationData';
        //alert(DataString);
        $.ajax({
            url: "DesignationMasterController.do", data: DataString, type: "post",
            success: function (data)
            {
                //alert(data);
                $('#loadDetailsUpdateUI').html(data); 
            }
        });
    }
    
    function loadAddDesignationData() {
        var DataString =  'option=loadAddDesignationData';
        //alert(DataString);
        $.ajax({
            url: "DesignationMasterController.do", data: DataString, type: "post",
            success: function (data)
            {
                //alert(data);
                $('#addDesignationData').html(data); 
            }
        });
    }
    
    function addDesignationData() {
		if (  $('#desigination').val() !="" && $('#order_no').val() != "") {
	        var DataString =  'desigination=' + $('#desigination').val() + '&order_no='+ $('#order_no').val() + '&option=addDesignationData';
	        //alert(DataString);
	        $.ajax({
	            url: "DesignationMasterController.do", data: DataString, type: "post",
	            success: function (data)
	            {
	                //alert(data);
	                loadDivisions();
	            }
	        });
   		}
		else 
			alert("Please insert all values!!!");
    }
    
    function updateDesignationData(field, md_id) {
		var DataString = 'fname=' + field + '&fvalue='+ $('#' + field + md_id).val() + '&md_id='+ md_id + '&option=updateDesignationData';
		//alert(DataString);
		$.ajax({
			url : "DesignationMasterController.do",	data : DataString,		type : "post",
			success : function(data) {
				//alert(data);
				if (data.startsWith("Error")) 
					alert(data);
				
			}
		});
	}
    
    function freezeDesignationData(md_id) {
    	if (confirm("Are you sure want to Freeze this Designation?")) {
			var DataString = 'md_id='+ md_id + '&option=freezeDesignationData';
			//alert(DataString);
			$.ajax({
				url : "DesignationMasterController.do",	data : DataString,		type : "post",
				success : function(data) {
					loadDivisions();
					//alert(data);
					if (data.startsWith("Error")) 
						alert(data);
				}
			});
    	}
	}
    
    function deleteDesignationData(md_id) {
    	if (confirm("Are you sure want to Delete this Designation?")) {
			var DataString = 'md_id='+ md_id + '&option=deleteDesignationData';
			//alert(DataString);
			$.ajax({
				url : "DesignationMasterController.do",	data : DataString,		type : "post",
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
					<h2>Designation Master</h2>
				</div>
				<div class="body">
					<div class="row clearfix">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group form-float" style="text-align: center">
										<button class="btn bg-orange waves-effect"  onclick="loadDivisions()">Load Designation Data</button>
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
							<a role="button" data-toggle="collapse" href="#pt1"	aria-expanded="true" aria-controls="pt1"> <i class="material-icons">view_comfy</i> Add Designation	</a>
						</h4>
					</div>
					<div id="pt1" class="panel-collapse collapse in">
						<div class="panel-body" id="addDesignationData"></div>
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
							<a role="button" data-toggle="collapse" href="#pt2"	aria-expanded="true" aria-controls="pt2"> <i class="material-icons">view_comfy</i> Designation Details	</a>
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