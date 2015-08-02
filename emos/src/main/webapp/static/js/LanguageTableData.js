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
var locale = "zh_CN";
var LanguagesTable = function () {
	var oTable;
	var oLogTable;
	var selected = [];
	var handleTable = function () {		
		var table=$('#languages_table');
		 oTable = table.dataTable({
			"lengthChange":false,
        	"filter":true,
        	"sort":false,
        	"info":true,
        	"processing":true,
        	"scrollX":"100%",
           	"scrollXInner":"100%",
            "displayLength": 10,
            "dom": "tr<'row'<'col-md-6'i><'col-md-6'p>>",
            "oLanguage": {
                "sProcessing": loadProperties("dataTable.page.process","zh_CN",rootURI),                
                "sZeroRecords":loadProperties("dataTable.page.data.zero",locale,rootURI),
                "sEmptyTable": loadProperties("dataTable.page.data.empty",locale,rootURI),
                "sInfo": loadProperties("dataTable.page.info","zh_CN",rootURI),
                "sInfoEmpty":loadProperties("dataTable.page.info.empty",locale,rootURI),
            },
            "columnDefs": [{                    
                    'targets': 0,   
                    'render':function(data,type,row){
                    	return '<div class="checker"><span><input type="checkbox" class="checkboxes"/></span></div>';
                    },
                    //'defaultContent':'<div class="checker"><span><input type="checkbox" class="checkboxes" value="1"/></span></div>'                    
                }
            ],
            "columns": [
               {"orderable": false },
	           //{    data: "id"  },
	           {  	data: "name" },
	           {    data: "local" },
	           {  	data: "flagImage"},
	           { 	data: "status"},
	           {    data: "sort"},
	          
	        ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"language/languageList?rand="+Math.random()
		});		
		 
		//打开删除对话框前判断是否已选择要删除的行
			$("#openActivelanguageModal").on("click",function(event){
				if(selected.length==0){
					handleAlerts(loadProperties("error.active.select",locale,rootURI),"warning","");				
					return false;
				}
			});
			$("#openDeletelanguageModal").on("click",function(event){
				if(selected.length==0){
					handleAlerts(loadProperties("error.deactive.select",locale,rootURI),"warning","");				
					return false;
				}
			});
		//删除操作
		$('#deleteBtn').on('click', function (e) {
			//var tt=selected.join();
			
			$.ajax( {
             "dataType": 'json', 
             "type": "DELETE", 
             "url": rootURI+"language/deletelanguage/"+selected.join(), 
             "success": function(data,status){
            	 if(status == "success"){					
					 if(data.status){
						 selected=[];						 
		            	 oTable.api().draw();
		            	 oTable.$('th span').removeClass();
		            	 handleAlerts(data.info,"success","");
					 }
					 else{
						 handleAlerts(data.info,"danger","");
					 }
				}             	 
             },
             "error":function(XMLHttpRequest, textStatus, errorThrown){
            	 alert(errorThrown);
             }
           });
        });  
		
		//激活语言
		$('#activeBtn').on('click', function (e) {
			$.ajax( {
             "dataType": 'json', 
             "type": "POST", 
             "url": rootURI+"language/activelanguage/"+selected.join(), 
             "success": function(data,status){
            	 if(status == "success"){					
					 if(data.status){
						 selected=[];						 
		            	oTable.api().draw();
		            	oTable.$('th span').removeClass();
		            	 handleAlerts(data.info,"success","");
					 }
					 else{
						 alert(data.info,"danger","");
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
		$("#openAddlanguageModal").on("click",function(event){
			AddlanguagesValidation();
		});
		
		$("#openEditlanguageModal").on("click",function(event){
			if(selected.length!=1){
				handleAlerts(loadProperties("error.edit.select",locale,rootURI),"warning","");
				return false;
			}
			else{
				EditLanguagesValidation();
				var data = oTable.api().row($("tr input:checked").parents('tr')).data();
	            var Id = data.id;
	            var name =data.name;
	            var local=data.local;
	            var sort=data.sort;
	           
	            $("#editLanguagesForm select[name='flagImage']").val(local);
	            $("#editLanguagesForm input[name='id']").val(Id);
	            $("#editLanguagesForm input[name='name']").val(name);
	            $("#editLanguagesForm input[name='local']").val(local);
	            $("#editLanguagesForm input[name='sort']").val(sort);
			}
			
		});
				
		$("#country_list2").on('change',function(){
			var local=$(this).val();
			 $("#editLanguagesForm input[name='local']").val(local);
		});
		$("#country_list").on('change',function(){
			var local=$(this).val();
			 $("#addLanguagesForm input[name='local']").val(local);
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
	            	var ids=data.id;
	                var index = $.inArray(ids, selected);
	                selected.push( ids );
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
        
        
        table.on('click', 'tbody tr a',function(){
            var data = oTable.api().row($(this).parents('tr')).data();
           var ids=data.adminId;
           if(oLogTable!=null){
        	   oLogTable.fnDestroy();
        	   viewTable(ids); 
           }else{
        	   viewTable(ids);
           }
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
		if(position==""){
			position = $("#msg");
		}
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
  //添加操作
	var AddLanguage = function(){
		event.stopPropagation();
		$.ajax( {
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"language/addLanguage", 
         "data": $('#addLanguagesForm').serialize(),
         "success": function(resp,status){
        	 if(status == "success"){  
        		 if(resp.status){						 
	            	 oTable.api().draw();
	            	 handleAlerts(resp.info,"success","");		            	 
				 }
				 else{
					 handleAlerts(resp.info,"danger","");						 
				 }
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });
		$('#add_language').modal('hide');
    };
    
    var AddlanguagesValidation = function() {
        var form = $('#addLanguagesForm');
        var errorDiv = $('.alert-danger', form);            
        var validator=form.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input                
            rules: {
            	name: {
            	required: true,
            	maxlength:9,
                		},
                flagImage: {
        		
        		required: true,
    				},
    			sort: {
        		
        		required: true,
        		integer:true,
        		
    				}

            },
           invalidHandler: function (event, validator) { //display error alert on form submit              
                errorDiv.show();                    
            },

                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
                },

                unhighlight: function (element) { // revert the change done by hightlight
                    $(element)
                        .closest('.form-group').removeClass('has-error'); // set error class to the control group
                },

            success: function (label) {
                label
                    .closest('.form-group').removeClass('has-error'); // set success class to the control group
            },
            onfocusout:function(element){
            	$(element).valid();
            },
            submitHandler: function (form) { 
            	errorDiv.hide();
            	AddLanguage();
            }
        });
        //重置表单页面
    	form[0].reset();
        errorDiv.hide(); 
    	$('input',form).closest('.form-group').removeClass('has-error');
    	validator.resetForm();	        
    };
    
	//编辑表单提交操作
	var EditLanguages= function() {
	  $.ajax( {
         "dataType": 'json', 
         "type": "POST", 
         "url": rootURI+"language/editlanguage", 
         "data" :$('#editLanguagesForm').serializeJson(),
         "success": function(resp,status){
        	 if(status == "success"){  
        		 if(resp.status){
					 selected=[];
	            	 oTable.api().draw();
	            	 handleAlerts(resp.info,"success","");
				 }
				 else{
					 handleAlerts(resp.info,"danger","");
				 }
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });
	 $('#edit_language').modal('hide');
	};
		
            
	var EditLanguagesValidation = function() {
		var form = $('#editLanguagesForm');
		var errorDiv = $('.alert-danger', form);            
		var validator=form.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block help-block-error', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",  // validate all fields including form hidden input                
			rules: {
				name: {
	            	required: true,
	            	maxlength:9,
	                		},
	    			sort: {
	        		required: true,
	        		integer:true,
	        		
	    				}

        },
       invalidHandler: function (event, validator) { //display error alert on form submit              
            errorDiv.show();                    
        },

        highlight: function (element) { // hightlight error inputs
            $(element)
                .closest('.form-group').addClass('has-error'); // set error class to the control group
        },

        unhighlight: function (element) { // revert the change done by hightlight
            $(element)
                .closest('.form-group').removeClass('has-error'); // set error class to the control group
        },

        success: function (label) {
            label
                .closest('.form-group').removeClass('has-error'); // set success class to the control group
        },
        onfocusout:function(element){
        	$(element).valid();
        },
        submitHandler: function (form) { 
        	errorDiv.hide();
        	EditLanguages();
        }
    });
    //重置表单页面
	form[0].reset();
    errorDiv.hide(); 
	$('input',form).closest('.form-group').removeClass('has-error');
	validator.resetForm();		
};
	

    return {
        //main function to initiate the module
        init: function (rootPath,locale_value) {
        	rootURI=rootPath;
        	handleTable();
        	locale=locale_value;
        	AddlanguagesValidation();
        	EditLanguagesValidation();        	
        }

    };
}();
