<script>
        function printDiv3(div_id) 
	    	{
	    	  var divToPrint=document.getElementById(div_id);
	    	  var newWin=window.open('','Print-Window');
	    	  newWin.document.open();
	    	  newWin.document.write('<html><style> @media print{.no-print,.no-print *{display:none!important} footer { position: fixed; bottom: 0; }   header {position: fixed; top: 0; } } </style><body onload="window.print()">'+divToPrint.innerHTML+'</body></html>');
	    	  newWin.document.close();
	    	  setTimeout(function(){newWin.close();},10);
	    	}
        
        function printDiv(div_id) {
        	    var element = document.getElementById(div_id);
        	    if (!element || element.innerHTML.trim() === "") {
        	        alert("Content not loaded / wrong div id");
        	        return;
        	    }
        	    var printWindow = window.open('', '', 'width=1000,height=700');
        	    printWindow.document.open();
        	    printWindow.document.write(`
        	        <html>
        	        <head>
        	            <title></title>
        	            <style>
        	                  @media print {
        	                    .no-print, .no-print * {
        	                        display: none !important;
        	                    footer { position: fixed; bottom: 0; }  
        	                    header {position: fixed; top: 0; }
        	                    }
        	                }
        	                @page {
        	                    @bottom-right {
        	                        content: "Page " counter(page) " of " counter(pages);
        	                        font-size: 12px; 
    				margin-bottom: 20px;
        	                    }
        	                }
        	                @media print { .tbl_new,.table-bordered { width:100%; border-collapse:collapse; border:1px solid black !important; } }

        	            </style>
        	        </head>
        	        <body>
        	            <div id="printContent"></div>
        	        </body>
        	        </html>
       	    `);
        	    printWindow.document.close();
        	    printWindow.onload = function () {
        	        var target = printWindow.document.getElementById("printContent"); 
        	        if (target) {
        	            target.innerHTML = element.innerHTML;
        	            setTimeout(function () {
        	                printWindow.focus();
        	               printWindow.print();
        	            }, 10);
        	       }
        	    };
        	}    	 
  
	    	function excelExport(table_id)
	    	{
	    	    $('#'+table_id).tableExport({filename: "report", type: 'xlsx'});
	    	}
    	
    		let pdfInProgress = false;
    	 function downloadPDFReport(form_id, action_url, btnName, btnValue,timeoutVal) {
    		 if (!showPdfOverlay()) return;
    	        var form = document.getElementById(form_id);
    	        var formData = new FormData(form);
    	        formData.append(btnName, btnValue);        
    	        var tempForm = document.createElement('form');
    	        tempForm.method = 'POST';
    	        tempForm.action = action_url;

    	        formData.forEach((value, key) => {
    	            var input = document.createElement('input');
    	            input.type = 'hidden';
    	            input.name = key;
    	            input.value = value;
    	            tempForm.appendChild(input);
    	        });
    	        document.body.appendChild(tempForm);
    	        tempForm.submit(); 
    	        document.body.removeChild(tempForm);
    	        console.log(`Report request sent successfully to ${action_url}. Download initiated.`);
    	        
    	        setTimeout(() => {
    	        	hidePdfOverlay();
    	              
    	        }, timeoutVal);
    	        
    	    }
    	 
    	 function showPdfOverlay() {
    		    if (pdfInProgress) return false;
    		    pdfInProgress = true;
    		    $('#pdfOverlay').removeClass('hide');
    		    $('body').css('overflow', 'hidden');
    		    return true;
    		}

    		function hidePdfOverlay() {
    		    pdfInProgress = false;
    		    $('#pdfOverlay').addClass('hide');
    		    $('body').css('overflow', 'auto');
    		}
    		
    		
    		function csvExport(table_id)   	{
        	    $('#'+table_id).tableExport({filename: "report",type: 'csv'});
        	}

    	
        </script>
