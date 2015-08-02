var rootURI="/";
function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(projectName);
}
var addrules = function(){
	var price=$('#price').val();
	var re=/^[0-9]*$/;
	if(price!=''&&re.test(price)){
		var prices=price*1.0;
		 $("#editGoodsForm input[name='oldPrice']").rules("add",{min:prices});
	}
}
var Initrules =function(){
	var price=$('#price').val();
	var prices=price/1;
		prices=prices*1.0;
	 $("#editGoodsForm input[name='oldPrice']").rules("add",{min:prices});
	
}
var Editgoods = function () {

    var handleImages = function() {
    	var id=$('#productid').val();
    	$.ajax( {
            "dataType": 'json', 
            "type":'POST', 
            "url": rootURI+"editgoods/getImages/"+id, 
            "success": function(data,status){
           	 if(status == "success"){  
           		 if(data.status){	
           		 // $('#uploaded_file_' + file.id + ' > .status').removeClass("label-info").addClass("label-success").html('<i class="fa fa-check"></i> Done'); // set successfull upload
           		  var rows = $();
           		  var file=data.files; 
           	      $.each(data.files, function (index,file) {
                      var row = $('<tr>' +
                          '<td><a href="'+getRootPath()+''+file.url+'" class="fancybox-button" data-rel="fancybox-button">'+
                          '<img class="img-responsive" src="'+getRootPath()+''+file.url+'" alt=""></a></td>'+                            	
                          '<td><p class="name"></p></td>' + 
                          '<td><p class="size"></p></td>' +                                
                          '<td>存在</td>' +                                 
                          '<td><a href="javascript:;" class="btn default btn-sm"><i class="fa fa-times"></i> 移除 </a></td>' +                                
                          '</tr>');                            
                      row.find('.name').text(file.fileName);
                      row.find('.size').text(file.fileSize);
                      row.find('a.btn').on("click",function(){                            	                            	
                      	$.get(rootURI+"editgoods/deleteImage/"+file.id, function(data){
                      		var res=$.parseJSON(data);
                  		  if(res.status){
                  			  row.remove();
                  		  }
                  		});
                      });
                      rows = rows.add(row);                            
                  });
                  $("#uploadedImagesList").append(rows);
                  rows.find("a.fancybox-button").fancybox();
   				 }
   				 else{
   					 handleAlerts("Failed to add the data.","danger","#addFormMsg");						 
   				 }
   			}             	 
            },
            "error":function(XMLHttpRequest, textStatus, errorThrown){
           	 alert(errorThrown);
            }
          });		
         // see http://www.plupload.com/
        var uploader = new plupload.Uploader({
            runtimes : 'html5,flash,silverlight,html4',
             
            browse_button : document.getElementById('tab_images_uploader_pickfiles'), // you can pass in id...
            container: document.getElementById('tab_images_uploader_container'), // ... or DOM Element itself
             
            url : rootURI+"editgoods/uploadImages",

            filters : {
                max_file_size : '2mb',
                mime_types: [
                    {title : "Image files", extensions : "jpg,jpeg,gif,png"}
                    //{title : "Zip files", extensions : "zip"}
                ]
            },
//            max_file_count: 5,
            unique_names: true,             
//            resize: {width: 640, height: 480, quality: 90},               
//            multipart_params: {'user': 'Rocky', 'time': '2012-06-12'}, 
//            chunk_size: '1mb',
            
            multiple_queues:true,

            // Flash settings
            flash_swf_url : rootURI+'assets/plugins/plupload/js/Moxie.swf',
     
            // Silverlight settings
            silverlight_xap_url : rootURI+'assets/plugins/plupload/js/Moxie.xap',             
         
            init: {
                PostInit: function() {
                	
                    $('#tab_images_uploader_filelist').html("");
         
                    $('#tab_images_uploader_uploadfiles').click(function() {
                        uploader.start();
                        return false;
                    });

                    $('#tab_images_uploader_filelist').on('click', '.added-files .remove', function(){
                        uploader.removeFile($(this).parent('.added-files').attr("id"));    
                        $(this).parent('.added-files').remove();                     
                    });
                },
         
                FilesAdded: function(up, files) {
                    plupload.each(files, function(file) {
                        $('#tab_images_uploader_filelist').append('<div class="alert alert-warning added-files" id="uploaded_file_' + file.id + '">' + file.name + '(' + plupload.formatSize(file.size) + ') <span class="status label label-info"></span>&nbsp;<a href="javascript:;" style="margin-top:-5px" class="remove pull-right btn btn-sm red"><i class="fa fa-times"></i> 移除</a></div>');
//                        previewImage(file,function(imgsrc){
//                        	$('#uploaded_file_' + file.id).append('<img width="80px" height="60px" src="'+imgsrc+'" />' );
//                        });
                    });
                },
         
                UploadProgress: function(up, file) {
                    $('#uploaded_file_' + file.id + ' > .status').html(file.percent + '%');
                },

                FileUploaded: function(up, file, response) {
                    var data = $.parseJSON(response.response);

                    if (data.status) {                        
                        $('#uploaded_file_' + file.id + ' > .status').removeClass("label-info").addClass("label-success").html('<i class="fa fa-check"></i> Done'); // set successfull upload
                        var rows = $();
                        $.each(data.files, function (index, file) {
                            var row = $('<tr>' +
                                '<td><a href="'+file.url+'" class="fancybox-button" data-rel="fancybox-button">'+
                                '<img class="img-responsive" src="'+file.url+'" alt=""></a></td>'+                            	
                                '<td><p class="name"></p></td>' + 
                                '<td><p class="size"></p></td>' +                                
                                '<td>上传成功</td>' +                                 
                                '<td><a href="javascript:;" class="btn default btn-sm"><i class="fa fa-times"></i> 移除 </a></td>' +                                
                                '</tr>');                            
                            row.find('.name').text(file.fileName);
                            row.find('.size').text(file.fileSize);
                            row.find('a.btn').on("click",function(){                            	                            	
                            	$.get(rootURI+"editgoods/deleteImage/"+file.id, function(data){
                            		var res=$.parseJSON(data);
                        		  if(res.status){
                        			  row.remove();
                        		  }
                        		});
                            });
                            rows = rows.add(row);                            
                        });
                        $("#uploadedImagesList").append(rows);
                        rows.find("a.fancybox-button").fancybox();
                        
                    } else {
                        $('#uploaded_file_' + file.id + ' > .status').removeClass("label-info").addClass("label-danger").html('<i class="fa fa-warning"></i> Failed'); // set failed upload
                        Metronic.alert({type: 'danger', message: 'One of uploads failed. Please retry.', closeInSeconds: 10, icon: 'warning'});
                    }
                },
         
                Error: function(up, err) {
                    Metronic.alert({type: 'danger', message: err.message, closeInSeconds: 10, icon: 'warning'});
                }
            }
        });
        
        //plupload中提供了mOxie对象，可以实现文件预览
        function previewImage(file,callback){
        	if(!file||!/image\//.test(file.type)) retrun;
        	if(file.type=='image/gif'){//gif图片只能使用FileReader进行预览
        		var fr=new mOxie.FileReader();
        		fr.onload=function(){
        			callback(fr.result);
        			fr.destroy();
        			fr=null;
        		}
        		fr.readAsDataURL(file.getSource);
        	}else{
        		var preloader=new mOxie.Image();
        		preloader.onload=function(){
        			preloader.downsize(300,300);//压缩要预览的图片
        			var imgsrc=preloader.type=='image/jpeg'?preloader.getAsDataURL('image/jpeg',80):preloader.getAsDataURL();
        			callback&&callback(imgsrc);
        			preloader.destroy();
        			preloader=null;
        		};
        		preloader.load(file.getSource());
        	}
        }

        uploader.init();

    }
    //chooseSpecCategory set attribute
    $('#chooseSpecCategory').on('change',function(e){
		$("#specattributeGroup").empty();
		var id=$(this).val();							
		$.ajax({
			"dataType": 'json', 
             "type"   : "GET", 
             "url"    : rootURI+"goods/getAttributesGroupByid/"+id,
             "success": function(data,status){
            	 if(status == "success"){ 
            		 if(data.status){
            			 var rows = $();	
            			 var list=data.list;
            			 var k=0;
            			// var arrrequired=new Array();
            			 var attributeIds=new Array();
            			
            			 for(var i=0;i<list.length;i++){
            				 var attributeId=list[i].attributeId;
            				 var title=list[i].title;
            				 var type=list[i].type;
            				 var attributevalue=list[i].attributeValue;
            				 var required=list[i].required;
            				 
            				 var row=$('<div class="form-group" style="padding:5px 0;"><div class="col-md-12">'+
            							'<label class="control-label col-md-2" style="text-align:left;"></label>'+
            							'<input type="checkbox" name="required_'+attributeId+'" hidden="true" value="false" checked="checked"/>'+
            							'<div class="col-md-10"></div>'+						
            						    '</div></div>');
            				 row.find('.control-label').text(title+": ");
            				 switch (type) {								
								case 1:
									var radioGroup=$('<div class="radio-list"></div>');
									
									for(var n=0;n<attributevalue.length;n++){
									    var attr=attributevalue[n].value;
										var radioObj='<label class="radio-inline">&nbsp&nbsp&nbsp<input type="radio" name="attr_'+attributeId+'" value="'+attributevalue[n].valueId+'"/>'+attr+'</label>';
										
										radioGroup.append(radioObj);
									}
									if(required){
										/*attributeIds[k]=attributeId;
											k++;*/
										attributeIds[attributeId]=attributeId;
										row.find('.control-label').append('<span class="required"> * </span>');
										}
									row.find('.col-md-10').append(radioGroup);
									row.append('<input type="hidden" name="attributeId" value="'+attributeId+'"/>');
									break;
								case 2:
									var checkboxGroup=$('<div class="checkbox-list"></div>');
									var checkboxes=$();												
									for(var n=0;n<attributevalue.length;n++){
										var attr=attributevalue[n].value;
										var checkboxObj=$('<label class="checkbox-inline">&nbsp&nbsp&nbsp<input type="checkbox" name="attr_'+attributeId+'" value="'+attributevalue[n].valueId+'" data="'+attr+'"/>'+attr+'</label>');
										checkboxes=checkboxes.add(checkboxObj);
									}
									if(required){
										attributeIds[attributeId]=attributeId;
										row.find('.control-label').append('<span class="required"> * </span>');
										}
									checkboxes.appendTo(checkboxGroup);
									row.append('<input type="hidden" name="attributeId" value="'+attributeId+'"/>');
									row.find('.col-md-10').append(checkboxGroup);
									break;
								case 3:											
									var selectGroup=$('<select class="form-control input-xlarge" name="attr_'+attributeId+'"></select>');
									for(var n=0;n<attributevalue.length;n++){
										var attr=attributevalue[n].value;
										var selectObj='<option value="'+attributevalue[n].valueId+'">'+attr+'</option>';
										selectGroup.append(selectObj);
									}
									row.find('.col-md-10').append(selectGroup);
									row.append('<input type="hidden" name="attributeId" value="'+attributeId+'"/>');
									break;
								default:
									break;
							}
            				 
            				rows = rows.add(row);
            			 }
            			 $("#specattributeGroup").append(rows);
            			for(var i=0;i<attributeIds.length;i++){
            				if(attributeIds[i]!=undefined){
            				 $("#editGoodsForm input[name='attr_"+attributeIds[i]+"']").rules("add",{required: true});
            					}
            				}
					 }
					 else{
						 alert("Failed to query the attirbute group.");
					 } 
				}             	 
             },
             "error":function(XMLHttpRequest, textStatus, errorThrown){
            	alert(errorThrown);
              }
           
		});
	});
    //chooseorderCategory set attribute
    $('#chooseorderCategory').on('change',function(e){
		$("#orderattributeGroup").empty();
		var id=$(this).val();							
		$.ajax({
			"dataType": 'json', 
             "type"   : "GET", 
             "url"    : rootURI+"goods/getAttributesGroupByid/"+id,
             "success": function(data,status){
            	 if(status == "success"){ 
            		 if(data.status){
            			 var rows = $();	
            			 var list=data.list;
            			 var attributeIds=new Array();
            			 for(var i=0;i<list.length;i++){
            				 var attributeId=list[i].attributeId;
            				 var attributevalue=list[i].attributeValue;
            				 var title=list[i].title;
            				 var required=list[i].required;
            				 attributeIds[attributeId]=attributeId;
            				 var row=$('<div class="form-group"><div class="col-md-12">'+
            							'<label class="control-label col-md-2" style="text-align:left;"></label>'+
            							'<div class="col-md-1"><div class="checkbox-list"><label class="checkbox-inline"><input type="checkbox" class="checkedAll"/>全选</label></div></div>'+
            							'<div class="col-md-9"></div><br/>'+            							
            						    '</div></div>');            				 
            				 row.find('label.col-md-2').append(title+':<span style="color:red"> * </span>');
							var page=$();
							var checkboxGroup=$('<div class="checkbox-list"></div>');
							var tableGroup=$('<br/><div class="col-md-6"><br/><lable class="control-label col-md-10" style="text-align:left;">请设置商品附加属性对应的价格</lable><table class="table table-striped table-bordered">'+
								        '<thead><tr><th width="40%">属性名</th><th width="60%">附加价格</th></tr></thead>'+
								        '<tbody></tbody></table></div>');
																									
							var checkboxes=$();
							for(var n=0;n<attributevalue.length;n++){
							    var attr=attributevalue[n].value;
								var checkboxObj=$('<label class="checkbox-inline">&nbsp&nbsp&nbsp<input type="checkbox" name="attr_'+attributeId+'"  value="'+attributevalue[n].valueId+'" data="'+attr+'"/>'+attr+'</label>');
								checkboxes=checkboxes.add(checkboxObj);
							}
							checkboxes.appendTo(checkboxGroup);
							page=page.add(checkboxGroup);
							row.append('<input type="hidden" name="attributeId" value="'+attributeId+'"/>');
							row.find('.col-md-9').append(page);
							row.append(tableGroup);
							tableGroup.hide();
							row.find('.checkedAll').on('change',function(){
								attributeId=$(this).parents(".form-group").find("input[name=attributeId]").val();										
								var prices=new Array();
								var pricess=$(this).parents(".form-group").find("tbody").find("input[name='attrPrice_"+attributeId+"']");
								var checkedObjs=$(".checkbox-list").find(".checkbox-inline").find("input[name='attr_"+attributeId+"']");
								$(checkedObjs).parents(".form-group").find("tbody").empty();
								if(this.checked){
									$(this).parents(".form-group").find(".col-md-6").show();
									$.each(checkedObjs, function (index, obj) {
										$(obj).attr("checked", true);
					                    $(obj).parents('span').addClass("checked");
										attributeId=$(this).parents(".form-group").find("input[name=attributeId]").val();
										
										for(var i=0;i<pricess.length;i++){
											if($(obj).attr("value")==$(pricess[i]).attr("data")){
												prices[$(obj).attr("value")]=$(pricess[i]).attr("value");
											}
											}
										if(prices[$(obj).attr("value")]==undefined){
											$(obj).parents(".form-group").find("tbody").append('<tr><td><span>'+$(obj).attr("data")+'</span></td><td><span>HK$:</span> <input type="text" data="'+$(obj).attr("value")+'" name="attrPrice_'+attributeId+'" placeholder="0.00"   value="0"/></td></tr>');	
										}else{
										$(obj).parents(".form-group").find("tbody").append('<tr><td><span>'+$(obj).attr("data")+'</span></td><td><span>HK$:</span> <input type="text" data="'+$(obj).attr("value")+'" name="attrPrice_'+attributeId+'" placeholder="0.00"  value="'+prices[$(obj).attr("value")]+'"/></td></tr>');
										}
									});
								}else{
									$(this).parents(".form-group").find(".col-md-6").hide();
									$.each(checkedObjs, function (index, obj) {
										$(obj).removeAttr("checked");
					                    $(obj).parents('span').removeClass("checked");
					                   
								});
								}
								
								//$(obj).parents(".form-group").find("tbody").append('<tr><td><span>'+$(obj).attr("data")+'</span></td><td><span>HK$:</span> <input type="text" data="'+$(obj).attr("value")+'" name="attrPrice_'+attributeId+'" placeholder="0.00"  /></td></tr>');	
							});
							checkboxGroup.on('change', ':checkbox', function () {
								attributeId=$(this).parents(".form-group").find("input[name=attributeId]").val();
								var prices=new Array();
								var pricess=$(this).parents(".form-group").find("tbody").find("input[name='attrPrice_"+attributeId+"']");
								$(this).parents(".form-group").find("tbody").empty();
								var checkedObj=$(this).parents(".checkbox-list").find(":checked");
								if(checkedObj.length>0){
									$(this).parents(".form-group").find(".col-md-6").show();
								}
								else{
									$(this).parents(".form-group").find(".col-md-6").hide();
								}
								$.each(checkedObj, function (index, obj) {
									attributeId=$(this).parents(".form-group").find("input[name=attributeId]").val();
									for(var i=0;i<pricess.length;i++){
										if($(obj).attr("value")==$(pricess[i]).attr("data")){
											prices[$(obj).attr("value")]=$(pricess[i]).attr("value");
										}
										}
									if(prices[$(obj).attr("value")]==undefined){
										$(obj).parents(".form-group").find("tbody").append('<tr><td><span>'+$(obj).attr("data")+'</span></td><td><span>HK$:</span> <input type="text" data="'+$(obj).attr("value")+'" name="attrPrice_'+attributeId+'" placeholder="0.00" value="0" /></td></tr>');	
									}else{
									$(obj).parents(".form-group").find("tbody").append('<tr><td><span>'+$(obj).attr("data")+'</span></td><td><span>HK$:</span> <input type="text" data="'+$(obj).attr("value")+'" name="attrPrice_'+attributeId+'" placeholder="0.00"  value="'+prices[$(obj).attr("value")]+'"/></td></tr>');
									}
								});
					        });
            				rows = rows.add(row);
            			 }
            			 $("#orderattributeGroup").append(rows);
            			 for(var i=0;i<attributeIds.length;i++){
             				if(attributeIds[i]!=undefined){
             				 $("#editGoodsForm input[name='attr_"+attributeIds[i]+"']").rules("add",{required: true});
             					}
             				}
            		 	}
					 else{
						 alert("Failed to query the attirbute group.");
					 } 
				}             	 
             },
             "error":function(XMLHttpRequest, textStatus, errorThrown){
            	alert(errorThrown);
              }
		});
	});
    //init attributes
    var Initspecattributes = function(){
    	var categoryId=$('#chooseSpecCategory').val();
    	var productid=$('#productid').val();
    	var ids=categoryId+","+productid;
    	$.ajax({
			"dataType": 'json', 
             "type"   : "GET", 
             "url"    : rootURI+"goods/getAttributesGroupById/"+ids,
             "success": function(data,status){
            	 if(status == "success"){ 
            		 if(data.status){
            			 var rows = $();	
            			 var list=data.list;
            			 var attributeIds=new Array();
            			 for(var k=0;k<list.length;k++){
            				 var attributeId=list[k].attributeId;
            				var  type=list[k].type;
            				var title=list[k].title;
            				var required=list[k].required;
            				var attributevalue=list[k].attributeValue;
            				var productattribute=list[k].productAttribute;
            				var pattrArr="";
            				if(productattribute){
            					var productattributecontents=productattribute.content;
            					pattrArr=productattributecontents.split(",");
            				}
            				
            				var row=$('<div class="form-group" style="padding:5px 0;"><div class="col-md-12">'+
	         							'<label class="control-label col-md-2" style="text-align:left;"></label>'+
	         							'<div class="col-md-10"></div>'+						
	         						    '</div></div>');
		            			row.find('.control-label').text(title+": ");
		            			if(required){
		            				attributeIds[attributeId]=attributeId;
									row.find('.control-label').append('<span class="required"> * </span>');
	            				}
		            			 switch (type) {
									case 1:
										var radioGroup=$('<div class="radio-list"></div>');
										
										for(var n=0;n<attributevalue.length;n++){
										    var attr=attributevalue[n].value;
										    for(var i=0;i<pattrArr.length;i++){
											    if(pattrArr[i]==attributevalue[n].valueId){
												    var radioObj='<label class="radio-inline">&nbsp&nbsp&nbsp<input type="radio" checked="checked" name="attr_'+attributeId+'" value="'+attributevalue[n].valueId+'"/>'+attr+'</label>';
												    radioGroup.append(radioObj);
												    break;
											    } 
										    }
										    if(i==pattrArr.length&&pattrArr[pattrArr.length-1]!=attributevalue[n].valueId){
										    	var radioObj='<label class="radio-inline">&nbsp&nbsp&nbsp<input type="radio" name="attr_'+attributeId+'" value="'+attributevalue[n].valueId+'"/>'+attr+'</label>';
 												radioGroup.append(radioObj);
										    }
										}
										row.find('.col-md-10').append(radioGroup);
										row.append('<input type="hidden" name="attributeId" value="'+attributeId+'"/>');
										break;
									case 2:
										var checkboxGroup=$('<div class="checkbox-list"></div>');
										var checkboxes=$();												
										
										for(var n=0;n<attributevalue.length;n++){
										    var attr=attributevalue[n].value;
										    for(var i=0;i<pattrArr.length;i++){
										    if(pattrArr[i]==attributevalue[n].valueId){
											var checkboxObj=$('<label class="checkbox-inline">&nbsp&nbsp&nbsp<input type="checkbox" checked="checked" name="attr_'+attributeId+'" value="'+attributevalue[n].valueId+'" data="'+attr+'"/>'+attr+'</label>');
											checkboxes=checkboxes.add(checkboxObj);
											break;
										    }
										    }
										    if(i==pattrArr.length&&pattrArr[pattrArr.length-1]!=attributevalue[n].valueId){
										    	 var checkboxObj=$('<label class="checkbox-inline">&nbsp&nbsp&nbsp<input type="checkbox"  name="attr_'+attributeId+'" value="'+attributevalue[n].valueId+'" data="'+attr+'"/>'+attr+'</label>');
  										    checkboxes=checkboxes.add(checkboxObj);	
										    }
										}
										checkboxes.appendTo(checkboxGroup);
										row.append('<input type="hidden" name="attributeId" value="'+attributeId+'"/>');
										row.find('.col-md-10').append(checkboxGroup);
										break;
									case 3:											
										var selectGroup=$('<select class="form-control input-xlarge" name="attr_'+attributeId+'"></select>');
										
										for(var n=0;n<attributevalue.length;n++){
										    var attr=attributevalue[n].value;
										    for(var i=0;i<pattrArr.length;i++){
										    if(pattrArr[i]==attributevalue[n].valueId){
											var selectObj='<option selected="selected" value="'+attributevalue[n].valueId+'">'+attr+'</option>';
											selectGroup.append(selectObj);
											break;
										    }
										    } 
										    if(i==pattrArr.length&&pattrArr[pattrArr.length-1]!=attributevalue[n].valueId){
										    	 var selectObj='<option value="'+attributevalue[n].valueId+'">'+attr+'</option>';
  										    selectGroup.append(selectObj);
										    }
										}
										row.find('.col-md-10').append(selectGroup);
										row.append('<input type="hidden" name="attributeId" value="'+attributeId+'"/>');
										break;
								}
		            		 rows = rows.add(row);
		            		 }
		            		 $("#specattributeGroup").append(rows);
		            		 for(var i=0;i<attributeIds.length;i++){
		            				if(attributeIds[i]!=undefined){
		            				 $("#editGoodsForm input[name='attr_"+attributeIds[i]+"']").rules("add",{required: true});
		            					}
		            				}
            			 }			            			 
					 else{
						 alert("Failed to query the attirbute group.");
					 } 
				}          	 
             },
             "error":function(XMLHttpRequest, textStatus, errorThrown){
            	alert(errorThrown);
              }
		});
    };
  //init attributes
    var Initorderattributes = function(){
    	var categoryId=$('#chooseorderCategory').val();
    	var productid=$('#productid').val();
    	var ids=categoryId+","+productid;
    	var types=new Array();
		var attributevaluelist=new Array();
		var titles=new Array();
    	$.ajax({
			"dataType": 'json', 
             "type"   : "GET", 
             //"url"    : rootURI+"goods/getAttributesGroupById/"+categoryId,
             "url"    : rootURI+"goods/getAttributesGroupById/"+ids,
             "success": function(data,status){
            	 if(status == "success"){ 
            		 if(data.status){
            			 var rows = $();	
            			 var list=data.list;
            			 var attributeIds=new Array();
            			 for(var k=0;k<list.length;k++){
            				 var attributeId=list[k].attributeId;
            				var  type=list[k].type;
            				var title=list[k].title;
            				var attributevalue=list[k].attributeValue;
            				var productattribute=list[k].productAttribute;
            				var productattributecontents=productattribute.content;
            				var productattributeprices=productattribute.price;
            				var price = new Array();
            				price=productattributeprices.split(',');
            				attributeIds[attributeId]=attributeId;
            				var row=$('<div class="form-group"><div class="col-md-12">'+
        							'<label class="control-label col-md-2" style="text-align:left;"></label>'+
        							'<div class="col-md-1"><div class="checkbox-list"><label class="checkbox-inline"><input type="checkbox" class="checkedAll"/>全选</label></div></div>'+
        							'<div class="col-md-9"></div>'+        							
        						    '</div></div>');            				
            				row.find('label.col-md-2').append(title+'<span style="color:red"> * </span>:');
            				var page=$();
            				var checkboxGroup=$('<div class="checkbox-list"></div>');
            				var tableGroup=$('<div class="col-md-6"><br/><lable class="control-label col-md-10" style="text-align:left;">请设置商品附加属性对应的价格</lable><table class="table table-striped table-bordered">'+
							        '<thead><tr><th width="40%">Attribute Name</th><th width="60%">Attribute Price</th></tr></thead>'+
							        '<tbody></tbody></table></div>');													
							var checkboxes=$();												
							var pattrArr=productattributecontents.split(",");
							if(pattrArr.length==0){
								tableGroup.hide();
							}
							for(var n=0;n<attributevalue.length;n++){
							    var attr=attributevalue[n].value;
							    for(var i=0;i<pattrArr.length;i++){
								    if(pattrArr[i]==attributevalue[n].valueId){
										var checkboxObj=$('<label class="checkbox-inline">&nbsp&nbsp&nbsp<input type="checkbox" checked="checked" name="attr_'+attributeId+'" value="'+attributevalue[n].valueId+'" data-order="'+n+'" data-title="'+attr+'"/>'+attributevalue[n].value+'</label>');
										checkboxes=checkboxes.add(checkboxObj);
										break;
								    }
							    }
							    if(i==pattrArr.length&&pattrArr[pattrArr.length-1]!=attributevalue[n].valueId){
							    	 var checkboxObj=$('<label class="checkbox-inline">&nbsp&nbsp&nbsp<input type="checkbox"  name="attr_'+attributeId+'" value="'+attributevalue[n].valueId+'" data-order="'+n+'" data-title="'+attr+'"/>'+attr+'</label>');
								    checkboxes=checkboxes.add(checkboxObj);	
							    }
							}
							checkboxes.appendTo(checkboxGroup);
							page=page.add(checkboxGroup);
							row.append('<input type="hidden" name="attributeId" value="'+attributeId+'"/>');
							row.find('.col-md-9').append(page);
							row.append(tableGroup);
							var len=row.find(".checkbox-list").find(":checked").length;
							var checkedObj=row.find(".checkbox-list").find(":checked");
							if(len==attributevalue.length){
								row.find('.checkedAll').attr("checked", true);
								row.find('.checkedAll').parents('span').addClass("checked");
							}
							$.each(checkedObj, function (index, obj) {
								$(obj).parents(".form-group").find("tbody").append('<tr><td><span>'+$(obj).attr("data-title")+'</span></td><td><span>HK$:</span> <input type="text"  data="'+index+'" name="attrPrice_'+attributeId+'" placeholder="0.00" value="'+price[index]+'" /></td></tr>');
								
							});
							

            				row.find('.checkedAll').on('change',function(){
								attributeId=$(this).parents(".form-group").find("input[name=attributeId]").val();								
								var price=new Array();
								var pricess=$(this).parents(".form-group").find("tbody").find("input[name='attrPrice_"+attributeId+"']");
								var checkedObjs=$(".checkbox-list").find(".checkbox-inline").find("input[name='attr_"+attributeId+"']");
								$(checkedObjs).parents(".form-group").find("tbody").empty();
								if(this.checked){
									$(this).parents(".form-group").find(".col-md-6").show();
									$.each(checkedObjs, function (index, obj) {
										$(obj).attr("checked", true);
					                    $(obj).parents('span').addClass("checked");
										attributeId=$(this).parents(".form-group").find("input[name=attributeId]").val();
										for(var i=0;i<pricess.length;i++){
											if(i==$(pricess[i]).attr("data")){
												price[$(pricess[i]).attr("data")]=$(pricess[i]).attr("value");
											}
											}
										if(price[$(obj).attr("data-order")]==undefined){
											$(obj).parents(".form-group").find("tbody").append('<tr><td><span>'+$(obj).attr("data-title")+'</span></td><td><span>HK$:</span> <input type="text" data="'+$(obj).attr("data-order")+'" name="attrPrice_'+attributeId+'" placeholder="0.00"   value="0"/></td></tr>');	
										}else{
											$(obj).parents(".form-group").find("tbody").append('<tr><td><span>'+$(obj).attr("data-title")+'</span></td><td><span>HK$:</span> <input type="text" data="'+$(obj).attr("data-order")+'" name="attrPrice_'+attributeId+'" placeholder="0.00"  value="'+price[$(obj).attr("data-order")]+'"/></td></tr>');										}
									});
								}else{
									$(this).parents(".form-group").find(".col-md-6").hide();
									$.each(checkedObjs, function (index, obj) {
										$(obj).removeAttr("checked");
					                    $(obj).parents('span').removeClass("checked");
								});
								}
							});		            		
							
								
							checkboxGroup.on('change', ':checkbox', function () {
								var prices=new Array();
								var attributeId=$(this).parents(".form-group").find("input[name='attributeId']").val();
								var pricess=$(this).parents(".form-group").find("tbody").find("input[name='attrPrice_"+attributeId+"']");
								$(this).parents(".form-group").find("tbody").empty();
								var checkedObj=$(this).parents(".checkbox-list").find(":checked");
								if(checkedObj.length>0){
									$(this).parents(".form-group").find(".col-md-6").show();
								}
								else{
									$(this).parents(".form-group").find(".col-md-6").hide();
								}
								for(var i=0;i<pricess.length;i++){
									if(i==$(pricess[i]).attr("data")){
										prices[$(pricess[i]).attr("data")]=$(pricess[i]).attr("value");
									}
								}
								$.each(checkedObj, function (index, obj) {
									if(prices[$(obj).attr("data-order")]==undefined){
										$(obj).parents(".form-group").find("tbody").append('<tr><td><span>'+$(obj).attr("data-title")+'</span></td><td><span>HK$:</span> <input type="text" data="'+$(obj).attr("data-order")+'" name="attrPrice_'+attributeId+'" placeholder="0.00"  value="0"/></td></tr>');	
									}else{
										$(obj).parents(".form-group").find("tbody").append('<tr><td><span>'+$(obj).attr("data-title")+'</span></td><td><span>HK$:</span> <input type="text" data="'+$(obj).attr("data-order")+'" name="attrPrice_'+attributeId+'" placeholder="0.00"  value="'+prices[$(obj).attr("data-order")]+'"/></td></tr>');
									}
								});
					        });
		            		rows = rows.add(row);
		            	}
            			 
	            		 $("#orderattributeGroup").append(rows);
	            		 for(var i=0;i<attributeIds.length;i++){
	             				if(attributeIds[i]!=undefined){
	             				 $("#editGoodsForm input[name='attr_"+attributeIds[i]+"']").rules("add",{required: true});
	             					}
	             		 }
        			} 
    			 }			            			              		
				 else{
					 alert("Failed to query the attirbute group.");
				 } 
			},        	 
             "error":function(XMLHttpRequest, textStatus, errorThrown){
            	alert(errorThrown);
              }
               
		});
    	
    	
    };
    //处理表单验证方法
    var editFormValidation = function() {
            var addform = $('#editGoodsForm');
            var errorDiv = $('.alert-danger', addform);            
            addform.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",  // validate all fields including form hidden input                
                rules: {                	
	                price: {
	                	required: true,
	                	number:true				            	
	    			},
	    			oldPrice: {
	                	number:true
	    			},
			    	productName: {
					   required: true,
					   maxlength:20
					},
					shortDescr: {
					  required: true,							
					}											  
	    			             
                },
                errorPlacement: function(error, element) {
                    if ( element.is(":radio") )
                        error.appendTo( element.parent().next().next() );
                    else if ( element.is(":checkbox") )
                        error.appendTo ( element.next() );
                    else
                        error.appendTo( element.parent());
                },
                invalidHandler: function (event, validator) { //display error alert on form submit                	
                    errorDiv.show();                    
                },
                highlight: function (element) { // hightlight error inputs
                    $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
                },
                unhighlight: function (element) { // revert the change done by hightlight
                    $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
                },
                onfocusout: function (element) { // hightlight error inputs
                    $(element).valid();
                },
                success: function (label) {
                    label.closest('.form-group').removeClass('has-error'); // set success class to the control group
                },
                submitHandler: function (form) {                	
                    errorDiv.hide();
                    $.each($(form).find("input[name='attributeId']"), function (index, attrIdObj) {
	            		var attributeId=attrIdObj.value;
	            		var attrArr=new Array();
	            		var attrPriceArr=new Array();
	            		$.each($(form).find("input[name='attr_"+attributeId+"']:checked"), function (index, attrObj) {
	            			attrArr[index]=attrObj.value;
	            		});
	            		$.each($(form).find("select[name='attr_"+attributeId+"'] option:selected"), function (index, attrObj) {
	            			attrArr[index]=attrObj.value;
	            		});
	            		$.each($(form).find("input[name='attrPrice_"+attributeId+"']"), function (index, attrPriceObj) {
	            			if(attrPriceObj.value==""){
	            				attrPriceArr[index]=0;
	            			}else{
	            			attrPriceArr[index]=attrPriceObj.value;
	            			}
	            		});
	            		$(form).append('<input type="hidden" name="attributes['+index+'].id" value="'+attributeId+'"/>');
	            		$(form).append('<input type="hidden" name="attributes['+index+'].attributeValue" value="'+attrArr.join(",")+'"/>');
	            		$(form).append('<input type="hidden" name="attributes['+index+'].attributePrice" value="'+attrPriceArr.join(",")+'"/>');
	            		
	            	});
	            	form.submit();                    
                }
            });
    };
    
   
 return {
    //main function to initiate the module
    init: function (rootPath) {
    	rootURI=rootPath;
    	handleImages();
    	
    	editFormValidation();
    	Initrules();
    	Initspecattributes();
    	Initorderattributes();
    	
    }

};

}();
