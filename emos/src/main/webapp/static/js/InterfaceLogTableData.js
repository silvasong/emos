//jquery插件把表单序列化成json格式的数据start 
(function($){
    $.fn.serializeJson=function(){
        var serializeObj={};
        var array=this.serializeArray();
        var str=this.serialize();
        $(array).each(function(){
            if(serializeObj[this.name]){
                if($.isArray(serializeObj[this.name])){
                    serializeObj[this.name].push(this.value);
                }else{
                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                }
            }else{
                serializeObj[this.name]=this.value;
            }
        });
        return serializeObj;
    };
})(jQuery);

var rootURI="/";
var InterfaceLogTable = function () {
	var selected = [];
	var oTable;
	var handleTable = function () {	
		var table=$('#interfacelog_table');
		oTable= table.dataTable({
			"lengthChange":false,
        	"filter":true,
        	"sort":false,
        	"info":true,
        	"processing":true,
        	"scrollX":"100%",
           	"scrollXInner":"100%",
            // set the initial value
            "displayLength": 10,
            "dom": "t<'row'<'col-md-6'i><'col-md-6'p>>",
//            "sPaginationType": "bootstrap_full_number",   //bootstrap_extended
//            "oLanguage": {
//                "sLengthMenu": "_MENU_ records per page",
//                "oPaginate": {
//                    "sPrevious": "Prev",
//                    "sNext": "Next",
//                	"zeroRecords": "No records to display"
//                }
//            },
            "columnDefs": [{                    
                    'targets': 0,   
                    'render':function(data,type,row){
                    	return '<div class="checker"><span><input type="checkbox" class="checkboxes"/></span></div>';
                    },
                    //'defaultContent':'<div class="checker"><span><input type="checkbox" class="checkboxes" value="1"/></span></div>'                    
                },
                {                	
                	'targets':-1,
                	'data':null,//定义列名
                	'render':function(data,type,row){
                    	return '<div class="actions"><a class="btn btn-sm dark" data-toggle="modal"  href="#view_log" >view</a></div>';
                    },
                    'class':'center'
                }
            ],
            "columns": [
               {"orderable": false },
	           { title: "ID",   data: "id" },
	           { title: "Interface Name",   data: "name" },
	           { title: "Content",  data: "content"},
	           { title: "Access Time", data: "accessTimestr" },
	           { title: "Access By",  data: "accessBy"},
	           { title: "Action" ,"class":"center"}
	        ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"interfaceloglist?rand="+Math.random(),
//	        "fnServerData": function ( sSource, aoData, fnCallback, oSettings ) {
//	           $.ajax( {
//	             "dataType": 'json', 
//	             "type": "POST", 
//	             "url": sSource, 
//	             "data": aoData,
////	             "contentType":"application/json",
//	             "success": function(resp){ 		            	
//	            	 fnCallback(resp);
//	             },
//	             "error":function(XMLHttpRequest, textStatus, errorThrown){
//	            	 alert(errorThrown);
//	             }
//	           } );
//	         },
//	        "fnServerParams": function ( aoData ) {
//	           aoData.push( { "name": "more_data", "value": "my_value" } );
//	         },
//	        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {				
//	        	$('td:eq(0)', nRow).html( '<input type="checkbox" class="checkboxes" value="1"/>' );
//				return nRow;
//			},

		});		
		
		//打开删除对话框前判断是否已选择要删除的行
		$("#openDeleteinterfacesLogModal").on("click",function(event){
				if(selected.length==0){
					handleAlerts("Please select the rows which you want to delete.","warning","");				
					return false;
				}
			});
		
		//删除操作
		$('#deleteBtn').on('click', function (e) {
			
			$.ajax( {
             "dataType": 'json', 
             "type": "DELETE", 
             "url": rootURI+"interfacelog/"+selected.join(), 
             "success": function(data,status){
            	 if(status == "success"){					
					 if(data.status){
						 selected=[];						 
		            	 oTable.api().draw();
		            	 oTable.$('th span').removeClass();
		            	 handleAlerts("delete the interfaceslog successfully.","success","");
					 }
					 else{
						 handleAlerts("Failed to delete the data. " +data.info,"danger","");
					 }
				}             	 
             },
             "error":function(XMLHttpRequest, textStatus, errorThrown){
            	 alert(errorThrown);
             }
           });
        });   
		
		//搜索表单提交操作
		$("#searchForm").on("submit", function(event) {
			event.preventDefault();
			var jsonData=$(this).serializeJson();
			var jsonDataStr=JSON.stringify(jsonData);			
			oTable.fnFilter(jsonDataStr);
			return false;
		});
		
		//全选
		
		$(".group-checkable").on('change',function () {
            var set = jQuery(this).attr("data-set");
            var checked = jQuery(this).is(":checked");
            selected=[];
            if(checked){            	
	            var api=oTable.api();            
	            jQuery(set).each(function () {            	
	            	var data = api.row($(this).parents('tr')).data();
	            	 var id = data.id;
	                var index = $.inArray(id, selected);
	                selected.push( id );
                    $(this).attr("checked", true);
                    $(this).parents('tr').addClass("active");
                    $(this).parents('span').addClass("checked");
	            });
            }
            else{
            	jQuery(set).removeAttr("checked");
            	jQuery(set).parents('tr').removeClass("active");
            	jQuery(set).parents('span').removeClass("checked");
            }
            jQuery.uniform.update(set);
        });
        
        //单选
        table.on('change', 'tbody tr .checkboxes', function () {
            $(this).parents('tr').toggleClass("active");            
            var data = oTable.api().row($(this).parents('tr')).data();
            var id = data.id;
            var index = $.inArray(id, selected);     
            if ( index === -1 ) {
                selected.push( id );
                $(this).parents('span').addClass("checked");
                $(this).attr("checked","checked");
            } else {
                selected.splice( index, 1 );
                $(this).parents('span').removeClass("checked");
                $(this).removeAttr("checked");
            }
        });
       //查看日志详情
        table.on('click', 'tbody tr a', function () {
        		var datas = oTable.api().row($(this).parents('tr')).data(); 
        		var id=datas.id;
        		var name=datas.name;
        		var content=datas.content;
        		var accessTime=datas.accessTimestr;
        		var accessBy=datas.accessBy;
           		$("#viewInterfacelogForm input[name='id']").val(id);
	            $("#viewInterfacelogForm input[name='name']").val(name);
	            $("#viewInterfacelogForm textarea[name='content']").val(content);
	            $("#viewInterfacelogForm input[name='accessTime']").val(accessTime);
	            $("#viewInterfacelogForm input[name='accessBy']").val(accessBy);
			
        });
              
        /* handle show/hide columns*/
        var tableColumnToggler = $('#column_toggler');		
		$('input[type="checkbox"]', tableColumnToggler).change(function () {
		    /* Get the DataTables object again - this is not a recreation, just a get of the object */
		    var iCol = parseInt($(this).attr("data-column"));
		    var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
		    oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});
        
        
	}; 
	//提示信息处理方法（是在页面中指定位置显示提示信息的方式）
	var handleAlerts = function(msg,msgType,position) {         
        Metronic.alert({
            container: position, // alerts parent container(by default placed after the page breadcrumbs)
            place: "prepent", // append or prepent in container 
            type: msgType,  // alert's type (success, danger, warning, info)
            message: msg,  // alert's message
            close: true, // make alert closable
            reset: true, // close all previouse alerts first
            focus: false, // auto scroll to the alert after shown
            closeInSeconds: 10, // auto close after defined seconds, 0 never close
            icon: "warning" // put icon before the message, use the font Awesone icon (fa-[*])
        });        

    };
	
	 //initialize datepicker
    var datePicker = function(){
    	$('.date-picker').datepicker({
        rtl: Metronic.isRTL(),
        autoclose: true
        });
     };

    return {
        //main function to initiate the module
        init: function (rootPath) {
        	rootURI=rootPath;
        	handleTable();  
        	datePicker();
        }

    };

}();