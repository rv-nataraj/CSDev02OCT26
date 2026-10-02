<%@include file="../../CommonJSP/pageHeader.jsp" %>

<script src="https://cdn.campusstack.in/assets/js/listselection.js"></script>
<link href="https://cdn.campusstack.in/assets/css/listselection.css" rel="stylesheet"/>
<%    
if (session.getAttribute("role_name").toString().contains("Role Management")) {
	DBConnect db = new DBConnect();
    try {
        db.getConnection();
%>
<style>.bg-orange  {margin-top: -8px;}</style>
<script>

    function load_role_det(staff_id) {
        var DataString = 'staff_id='+staff_id+'&option=role_select';
        $.ajax({
            url: "RoleAllocationController.do", data: DataString, type: "post",
            success: function (data)
            {
                $('#load_branch_det').html(data);
                $.listSelection("#listSelection", "100", "50", "3");
            }
        });
    }
     function loadUserDetails(stu_id) {
        var DataString = 'staff_id=' + stu_id + '&option=loadUserDetails';
        $.ajax({
            url: "RoleAllocationController.do", data: DataString, type: "post",
            success: function (data)
            {
                $('#loadstu_det').html(data);
            }
        });
    }
    function loadDivisions(staff_id) {
        loadUserDetails(staff_id);
        load_role_det(staff_id);
    }
    
    function loadSearchDetails() {
        var DataString = 'search_string=' + $('#search_string').val() + '&option=loadSearchDetails';
        //alert(DataString);
        $.ajax({
            url: "RoleAllocationController.do", data: DataString, type: "post",
            success: function (data) {
            	//alert(data);
                $('#loadSearchDetails').html(data); 
                if (data.startsWith("Error")) 
    				alert(data);
            }
        });
    }

    
   
</script>


<section class="content">

 	<div class="row clearfix">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <div class="card ">
                <div class="header bg-blue">
                    <h2>
                        Staff Search 
                    </h2>
                </div>
                
                <style>.bg-orange  {margin-top: -8px;}</style>
				<div class="body panel-body">
        			<div class="row clearfix">
            			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            				<div class="col-md-4">
            				</div>
                   			<div class="col-md-4">
                        		<div class="input-group input-group-sm">                            
                            		<div class="form-line">
                                		<input class="form-control" id="search_string" placeholder="ID / Name" type="text">
                            		</div>
                            		<span class="input-group-addon">
                               			<button id="search_button" type="button" class="btn bg-orange waves-effect" data-toggle="modal" data-target="#largeModalSSS"  onclick='loadSearchDetails()'>
                                    		<i class="material-icons">search</i>
                                		</button>
                                		<script> 
				                        	search_string.addEventListener("keydown", function (e) {
				                        	    if (e.code === "Enter") {  //checks whether the pressed key is "Enter"
				                        	    	document.getElementById('search_button').click();
				                        	    }
				                        	});
				                        </script>
                           			</span>
                        		</div>
                    		</div>
            			</div>
        			</div>
				</div>
          	</div>
        </div>
    </div>
 	
 
 
 
    <div class="row clearfix">
	    <div class="col-xs-12 ol-sm-12 col-md-12 col-lg-12">
	        <div class="panel-group full-body" id="accordion_19" role="tablist" aria-multiselectable="true">
	            <div class="panel panel-col-blue">
	                <div class="panel-heading" role="tab" id="headingOne_19">
	                    <h4 class="panel-title">
	                        <a role="button" data-toggle="collapse" href="#collapseOne_19" aria-expanded="true" aria-controls="collapseOne_19">
	                            <i class="material-icons">perm_contact_calendar</i> Staff Details </a>
	                    </h4>
	                </div>
	                <div id="collapseOne_19" class="panel-collapse collapse in"  role="tabpanel" aria-labelledby="headingOne_19">
	                    <div class="panel-body" id='loadstu_det'>
	
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
    </div>
    <div class="row clearfix">
	    <div class="col-xs-12 ol-sm-12 col-md-12 col-lg-12">
	        <div class="panel-group full-body" id="accordion_23" role="tablist" aria-multiselectable="true">
	            <div class="panel panel-col-blue">
	                <div class="panel-heading" role="tab" id="headingOne_23">
	                    <h4 class="panel-title">
	                        <a role="button" data-toggle="collapse" href="#collapseOne_23" aria-expanded="true" aria-controls="collapseOne_23">
	                            <i class="material-icons">person_add</i> Role Staff Mapping </a>
	                    </h4>
	                </div>
	                <div id="collapseOne_23" class="panel-collapse collapse in"  role="tabpanel" aria-labelledby="headingOne_23">
	                    <div class="panel-body" id='load_branch_det'>
	
	                    </div> 
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
}
else
	out.print("Not Authorized to access this resource.");
%> 
<%@include file="../../CommonJSP/pageFooter.jsp" %>
