<script>
    $(document).ajaxStart(function () {
        $('.page-loader-wrapper').show();
    });
    $(document).ajaxComplete(function () {
        $('.page-loader-wrapper').fadeOut();
    });

    function loadPage(page) {
        $(".content").html(""); $(".content1").html("");
        $("#pageContent").html("<div class='loader'>Loading...</div>");    
        $("#pageContent").load(page, function(response, status, xhr) {
            if (status === "error") {
                $("#pageContent").html("<h3>Error loading page</h3>");
            }
        });
    }
    
    $(document).ajaxError(function(event, xhr) {
        if (xhr.status === 429) {
            window.location.href = "/CAMPS/CommonJSP/rate_limit_exceeded.jsp";
        }
    });
    
    function showResourceDetails(resource_id) {
        $('#resource_det').html("Loading... pls wait...");
        var DataString = 'resource_id=' + resource_id  +  '&option=showResourceInfo';
        //alert(DataString);
        $.ajax({
            url: "../Common/ControllerCommon.do", data: DataString, type: "post",
            success: function (data) {
                //alert(data);
                $('#resource_det').html(data);
            }
        });
    }
    
    function exportToWordDocument(data){
        var header = "<html xmlns:o='urn:schemas-microsoft-com:office:office' "+"xmlns:w='urn:schemas-microsoft-com:office:word' "+"xmlns='http://www.w3.org/TR/REC-html40'>"+"<head><meta charset='utf-8'><title>CS</title></head><body>";
        var footer = "</body></html>";
        var sourceHTML = header+data+footer;
        
        var source = 'data:application/vnd.ms-word;charset=utf-8,' + encodeURIComponent(sourceHTML);
        var fileDownload = document.createElement("a");
        document.body.appendChild(fileDownload);
        fileDownload.href = source;
        fileDownload.download = 'document.doc';
        fileDownload.click();
        document.body.removeChild(fileDownload);
    }
    
    function exportDivToWordDocument(div_id){
        var header = "<html xmlns:o='urn:schemas-microsoft-com:office:office' "+"xmlns:w='urn:schemas-microsoft-com:office:word' "+"xmlns='http://www.w3.org/TR/REC-html40'>"+"<head><meta charset='utf-8'><title>CS</title></head><body>";
        var footer = "</body></html>";
        var sourceHTML = header+document.getElementById(div_id).innerHTML+footer;
        
        var source = 'data:application/vnd.ms-word;charset=utf-8,' + encodeURIComponent(sourceHTML);
        var fileDownload = document.createElement("a");
        document.body.appendChild(fileDownload);
        fileDownload.href = source;
        fileDownload.download = 'document.doc';
        fileDownload.click();
        document.body.removeChild(fileDownload); 
    }
            

    // ===================================================================
    
    // ================ Pin and Unpin the resource ===========================

    function pin_this_resource(rid) {
        var DataString = 'resource_id=' + rid + '&option=pin_this_resource';
        $.ajax({
            url : "../Common/ControllerCommon.do",
            data : DataString,
            type : "post",
            success : function(data) {
                alert("Success: Resource added to your favorites.")  
            }
        });
    }
    
    function unpin_this_resource(rid) {
        var DataString = 'resource_id=' + rid + '&option=unpin_this_resource';
        $.ajax({
            url : "../Common/ControllerCommon.do",
            data : DataString,
            type : "post",
            success : function(data) {            
                alert("Success:  Resource removed from your favorites.")  
            }
        });
    }
    
    // ===================================================================

    // ================ New CSS Formate hideui ===========================

    $(document).ready(function () {
        $(".selectpicker").selectpicker();
        $(".cs-temp-b").each(function () {
            const count = $(this).children().length;
            $(this).addClass("cs-count-" + count);
        });
    });

	function hideui(header) {
        const panel = header.closest(".cs-panel");
        panel.classList.toggle("collapsed");
        const icon = header.querySelector(".toggle-arrow");    
        if (panel.classList.contains("collapsed")) {
            icon.innerHTML = "chevron_right";
        } else {
            icon.innerHTML = "expand_more";
        }
	}

    // ===================================================================

    // ================ New CSS and Json Dynamic Report Builder ===========================

    function buildDynamicReportTable(config) {
        const { columns = [], keys = [], data = [], header = "", query = "", target = "", validateFields = []   } = config;
        const shouldValidate = Array.isArray(validateFields) && validateFields.length > 0;

        let html = "";
        if(header){
            html += header;
            html += `<style>@media print {.cs-table-grid td, .cs-table-grid th {text-align: center !important;}}</style>`;
        }
        html += "<div class='cs-table-a'>";
        html += "<table class='cs-table-grid' border='1' style='width:100%;border-collapse:collapse;'>";
        html += "<thead><tr>";
        columns.forEach(col => {
            html += "<th>" + col + "</th>";
        });
        html += "</tr></thead>";
        html += "<tbody>";

        data.forEach(obj => {
            html += "<tr>";
            keys.forEach((key, index) => {
                let value = obj[key];
                if (shouldValidate && validateFields.includes(key)) {
                    if (value === null || value === undefined || value === "") {
                        value = "<span style='color:red'>No Data</span>";
                    }
                }
                if (value === null || value === undefined) {
                    value = "";
                }
                html += "<td data-cs-label='" + columns[index] + "'><span>" + value + "</span></td>";
            });
            html += "</tr>";
        });
        html += "</tbody></table></div>";
        html += "<div style='text-align:center;margin-top:15px'><input class='no-print cs-btn cs-btn-orange' type='button' value='Print / Save PDF' onclick=\"printDiv('"+target+"');\"> ";
        html += "<button class='no-print cs-btn cs-btn-orange' onclick=\"excelExport('"+target+"')\">Excel</button></div>";
        html += "<!-- " + query + " -->";
        $("#" + target).html(html);
    }

    function buildDynamicReportTable2(config) {
        const { columns = [], keys = [], data = [], header = "", query = "", target = "", validateFields = [], header_status = ""  } = config;
        const shouldValidate = Array.isArray(validateFields) && validateFields.length > 0;

        let html = "";
        if(header){
            html += header;
            html += `<style>@media (max-width: 768px) { .cs-table-grid td, .cs-table-grid td span {text-align: left !important;}}  @media print {.cs-table-grid th,.cs-table-grid td { vertical-align:middle !important; }.cs-table-grid td span {display:block !important; width:100% !important;text-align:inherit !important;padding:4px 7px  !important;}.cs-pagination { display:none !important; }}</style>`;
        }

        html += "<div class='cs-table-a'>";
        html += "<table class='cs-table-grid' border='1' style='width:100%;border-collapse:collapse;'>"; 
        html += "<thead><tr>";
        columns.forEach(col => {
            html += "<th style='text-align:center'>" + col + "</th>";
        });
        html += "</tr></thead>";
        html += "<tbody>";

        data.forEach(obj => {
            html += "<tr>";
            keys.forEach((key, index) => {
                let value = obj[key];
                if (shouldValidate && validateFields.includes(key)) {
                    if (value === null || value === undefined || value === "") {
                        html += "<td data-cs-label='" + columns[index] + "' style='text-align:left'><span style='padding:4px 7px;color:red'>No Data Found</span></td>";
                        return;
                    }
                }
                if (value === null || value === undefined) {
                    value = "";
                }
                let isOnlyText = /^[A-Za-z\s().-]+$/.test(value);
                let align = isOnlyText ? "left" : "center";
                html += "<td data-cs-label='" + columns[index] + "' style='text-align:" + align + "'><span style='padding:4px 7px;display:block;width:100%'>" + value + "</span></td>";
            });
            html += "</tr>";
        });
        html += "</tbody></table></div>";
        if(header_status === "1"){
            html += "<div style='text-align:center;margin-top:15px'>";
            html += "<input class='no-print cs-btn cs-btn-orange' type='button' value='Print / Save PDF' onclick=\"printDiv('"+target+"');\"> ";
            html += "<button class='no-print cs-btn cs-btn-orange' onclick=\"excelExport('"+target+"')\">Excel</button></div>";
        }
        html += "<!-- " + query + " -->";
        $("#" + target).html(html);
    }

    function buildDynamicReportTable3(config, pagination) {
        const { columns = [], keys = [], data = [], header = "", query = "", target = "", validateFields = [], header_status = "", mtr_id = ""} = config;
        const shouldValidate = Array.isArray(validateFields) && validateFields.length > 0;

        let html = "";
        if(header){
            html += header;
            html += `<style>@media (max-width: 768px) { .cs-table-grid td, .cs-table-grid td span {text-align: left !important;}} @media print {.cs-table-grid th,.cs-table-grid td { vertical-align:middle !important; }.cs-table-grid td span {display:block !important; width:100% !important;text-align:inherit !important;padding:4px 7px  !important;}.cs-pagination { display:none !important; }}</style>`;
        }
        html += "<div class='cs-table-a'>";
        html += "<table class='cs-table-grid' border='1' style='width:100%;border-collapse:collapse;'>";

        html += "<thead><tr>";
        columns.forEach(col => {
            html += "<th style='text-align:center'>" + col + "</th>";
        });
        html += "</tr></thead>";
        html += "<tbody>";

        data.forEach(obj => {
            html += "<tr>";
            keys.forEach((key, index) => {
                let value = obj[key];
                if (shouldValidate && validateFields.includes(key)) {
                    if (value === null || value === undefined || value === "") {
                        html += "<td data-cs-label='" + columns[index] + "' style='text-align:left'><span style='padding:4px 7px;color:red'>No Data Found</span></td>";
                        return;
                    }
                }
                if (value === null || value === undefined) {
                    value = "";
                }
                let isOnlyText = /^[A-Za-z\s().-]+$/.test(value);
                let align = isOnlyText ? "left" : "center";
                html += "<td data-cs-label='" + columns[index] + "' style='text-align:" + align + "'><span style='padding:4px 7px;display:block;width:100%'>" + value + "</span></td>";
            });
            html += "</tr>";
        });

        html += "</tbody></table></div>";
        if(header_status === "1"){
            html += "<div style='text-align:center;margin-top:15px'>";
            html += "<input class='no-print cs-btn cs-btn-orange' type='button' value='Print / Save PDF' onclick=\"printDiv('"+target+"');\"> ";
            html += "<button class='no-print cs-btn cs-btn-orange' onclick=\"excelExport('"+target+"')\">Excel</button></div>";
        }
        if(pagination.total_pages > 1){
            html += "<div class='cs-pagination'>";
            html += "<button class='pg-btn' " + (pagination.page == 1 ? "disabled" : "onclick='loadMasterReportData("+mtr_id+", 1)'") + ">&laquo;</button>";
            html += "<button class='pg-btn' " + (!pagination.has_prev ? "disabled" : "onclick='loadMasterReportData("+mtr_id+", "+(pagination.page-1)+")'") + ">&larr;</button>";
            html += "<button class='pg-btn' " + (!pagination.has_next ? "disabled" : "onclick='loadMasterReportData("+mtr_id+", "+(pagination.page+1)+")'") + ">&rarr;</button>";
            html += "<button class='pg-btn' " + (pagination.page == pagination.total_pages ? "disabled" : "onclick='loadMasterReportData("+mtr_id+", "+pagination.total_pages+")'") + ">&raquo;</button>";
            html += "</div>";
        }
        html += "<!-- " + query + " -->";
        $("#" + target).html(html);
    }
    
    // ===================================================================

</script>