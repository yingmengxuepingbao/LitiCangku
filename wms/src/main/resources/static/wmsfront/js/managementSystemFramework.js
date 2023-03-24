var leftframe=$('#leftframe');
var opentaglist={};//打开tag列表
var iframeindex=10;//iframe层级
var ismenumd=true;//记录菜单当前处于的两种状态，md为宽菜单，ms为窄菜单
var menumdLogo;//大logurl
var menumsLogo;//小logurl
function logoImgUrl(menumd,menums){
	menumdLogo=menumd;
	menumsLogo=menums;
}
//点击切换左侧菜单栏的样式，改变菜单大小
$('.mainbody .changestyle').click(function(){
	if(ismenumd){
		leftframe.attr('class',"menu-ms");
		$('.logoImg').attr('src',menumsLogo);
		ismenumd=false;
	}else{
		leftframe.attr('class',"menu-md");
		$('.logoImg').attr('src',menumdLogo);
		ismenumd=true;
	}
})
//更改用户名和头像
function userInfo(name,img){
	$('.username').html(name);
	$('.userimage').attr("src",img);
}

//随机颜色 提供颜色或图片地址
function changeTheme(color,url){
	//返回随机生成的html颜色
	function getRandomColor(){
	  return '#'+Math.floor(Math.random()*16777215).toString(16); 
	};
	var sta=url||color||getRandomColor();
	$('.container-fluid').css('background',sta);
    //如果是颜色的话就改变改变文字颜色但是不改变标题颜色,若存在url，将背景改为url
	if(url){
		$('.topnav').css('background','transparent');
		$('.topnav').css('color','white');
	}else{
		$('.topnav').css('background','#EDEDED');
		$('.topnav').css('color',"black");
	}
	$('#leftframe section').css('background',sta);
	$('.tabmenu').css('background',"transparent")
}

//初始化bootstrap中的control_bar提示按钮提示框
$('[data-toggle="tooltip"]').tooltip();
var menujson;//记录菜单
//创建菜单
function createMenu(object){
	var menuobject;
	//用于转换菜单格式
	if(typeof object=='object'){//如何检查类型
		//如果是对象直接new
		menuobject=object;
	}else{
    //如果不是对象尝试转为对象然后new，如果转化失败则报错
		try{
			menuobject=eval('('+object+')');
		}catch(e){
			console.log('json格式错误，无法创建菜单');
		}
	}
	try{
		new accordionMenu({
				container:'#menu',
				menujson:menuobject,
				clickItemCallBack:menuEvent,
				rendercallback:testFunction,
				inittextindent:'14px'
		});
	}catch(e){
		console.log(e+'创建菜单失败，请检查菜单对象命名');
	}
	menujson=menuobject;
}


var fullscreamdiv=document.getElementById('maincontainer')
//全屏函数
$('#fullscream').click(function(){
	fullScream(fullscreamdiv);
})

//退出登录

//全屏函数
function fullScream(element){
	 var requestMethod = element.requestFullScreen || element.webkitRequestFullScreen || element.mozRequestFullScreen || element.msRequestFullScreen;
	 requestMethod.call(element);
	 fullscreamdiv.style.height="100%";
	 fullscreamdiv.style.width="100%";
}

//退出全屏
function exitFullsSream(element){
	var elem= document;
	if (elem.webkitCancelFullScreen) {
	   elem.webkitCancelFullScreen();
	  } else if (elem.mozCancelFullScreen) {
	   element.mozCancelFullScreen();
	  } else if (elem.cancelFullScreen) {
	   elem.cancelFullScreen();
	  } else if (elem.exitFullscreen) {
	   elem.exitFullscreen();
	  } else {
	   //浏览器不支持全屏API或已被禁用
	  };
}

//刷新iframe
function reload(event){
    var ant=$(event.target.parentNode.firstChild).attr('menuid');
    //查询 当前页面的 权限列表
    //getOperationPermission(ant);
	$.each($('#loaderdiv div'),function(index,object){
		if($(object).attr("iframeid")==$(event.target.parentNode.firstChild).attr('menuid')){
			var a=$(event.target.parentNode.firstChild).attr('menuid');
			var c=opentaglist[a][1];
//			object.remove();
			
			$(object)[0].parentNode.removeChild(object);
			createNewIframe(a,c);
		}
	})
}

//关闭窗口 event为事件
function destorytagandiframe(event){
	delete opentaglist[$(event.target.parentNode.firstChild).attr('menuid')];
	$(event.target.parentNode).animate({width:"0px"},300,function(){
//		event.target.parentNode.remove();
		var removeTarget= event.target.parentNode
		removeTarget.parentNode.removeChild(removeTarget);
//		event.target.parentNode.removeNode(true);
	})
	$.each($('#loaderdiv div'),function(index,object){
		if($(object).attr("iframeid")==$(event.target.parentNode.firstChild).attr('menuid')){
//			object.remove();
			$(object)[0].parentNode.removeChild(object);
		}
	})
	var ulleft=parseInt($('.centercontainer ul').css('left'));
	var leftarrow=$('#maincontainer .tabmenu .leftarrow');
	var rightarrow=$('#maincontainer .tabmenu .rightarrow');
	var centercontainer=parseInt($('.tabmenu .centercontainer').css('width'));
	var ullength=parseInt($('.centercontainer ul').css('width'));
	if(ullength<centercontainer&&ulleft==0){
		leftarrow.css('color','gray');
		rightarrow.css('color','gray');
	}
}

//创建tag标签
var loaderdiv=$('#loaderdiv');
function createNewTag(menuid,tabName,url){
    //判断是否已经存在
	if(opentaglist[menuid]){
		selectTab(menuid);
	}else{
		opentaglist[menuid]=[tabName,url];
		createTab(menuid,tabName,url);
	}
}

//创建历史tag list为tag列表
function firstLoadCreateTab(list){
	for(var i in list){
		createTab(i,list[i][0],list[i][1]);
	}
}

//绑定菜单事件回调函数
function testFunction(container){
    //添加收藏标签
	$('.backspan').addClass('iconfont');
	$('.backspan').addClass('icon-aliyun');
    //添加下拉span
	$('.hassubMenu').addClass('iconfont');
	$('.hassubMenu').addClass('icon-xiajiantou');
}

//主菜p标签单点击事件  传入容器，容器标签和目标标签
function menuEvent(container,targeta,target){
	//a标签和目标标签 先改变颜色然后添加url
	if(targeta.next('ul')[0]){
		//如果存在兄弟节点
	}else{
		var menuurl = targeta.attr('menuurl');
		var menuid = targeta.attr('menuid');
		var menuname = targeta.attr('menuname');
		if(menuurl == ' '){
			return;
		}
		//如果opentaglist中存在menuid，就跳转页面，否则打开页面，创建tag
		if(opentaglist[menuid]){
			//如果已存在，打开已有页面
			selectTab(menuid);
		}else{
			//如果不存在，创建并打开
			createTab(menuid,menuname,menuurl)
		}
		opentaglist[menuid]=[menuname,menuurl];
	}
}

//根据传入的创建标签，创建div，传入菜单id，菜单显示名称和url
function createTab(menuid,tabName,url){
	//对管理驾驶舱--数字工厂的特殊处理
	if(menuid == 822){
		//再打开一个 浏览器窗口
        window.open(url);
		return;
	}
	var tabmenu= $(".tabmenu .centercontainer ul");
	var tagspan=$('<div><span menuid="'+menuid+'">'+tabName+'</span><span class="iconfont icon-f14" title="刷新" onclick="reload(event)"></span><span class="iconfont icon-guanbi" title="关闭" onclick="destorytagandiframe(event)"></span></div>')
	tagspan.appendTo(tabmenu);
	$.each($('.tabmenu .centercontainer ul div'),function(index,object){
		object.style.background="rgba(255,255,255,0.4)";
	})
	$.each($('.tabmenu div span'),function(index,object){
		if($(object).attr('menuid')==menuid){
			object.parentNode.style.background="rgba(255,255,255,.3)";
		}
	})
	selectTab(menuid);
	createNewIframe(menuid,url);
	//判断是否点亮左右移动标签
	var centercontainer=parseInt($('.tabmenu .centercontainer').css('width'));
	var ullength=parseInt($('.centercontainer ul').css('width'));
	var leftarrow=$('#maincontainer .tabmenu .leftarrow');
	var rightarrow=$('#maincontainer .tabmenu .rightarrow');
	if(ullength>centercontainer){
		leftarrow.css('color','white');
		rightarrow.css('color','white');
	}
    //查询 当前页面的 权限列表
	//getOperationPermission(menuid);

}
//查询 当前页面的 权限列表
// 标码
// add  新增
// eidt  编辑
// delete  删除
// import  导入
// export  导出
function getOperationPermission(menuid) {
    $.ajax({
        type:"get",
        url:"/IEAM/permissionManagement/group/getPermissionsByUrlId",
        data:{
            urlId:menuid
        },
        success: function(res){
            var result = JSON.parse(decodeURIComponent(res));
            if(result.code == 0 ){
                operationData = result.data;
            }
        },
        error: function (res) {

        }
    });
}
//创建窗口   传入窗口id和url
function createNewIframe(iframeid,url){
	var iframecon=$("<div style='position:absolute; top:0;left:0; z-index:"+iframeindex+";'iframeid="+iframeid+">");
	iframecon.css('height',"100%");
	var iframe = document.createElement("iframe"); 
	iframe.src = url; 
	var iframediv=$('<div><span class="iframeloading">加载中...</span></div>');
	$(iframediv).css({
		"width":"100%",
		"height":"100%",
		"background":"rgba(0,0,0,0.8)",
		"position":"absolute",
		"z-index":"100"
		});
	$(iframediv).appendTo(iframecon);
	iframe.onload=function(){
//		iframediv.remove();
        if($(iframediv)[0].parentNode){
            $(iframediv)[0].parentNode.removeChild($(iframediv)[0]);
        }
    }
    $(iframe).appendTo(iframecon);
    iframecon.appendTo(loaderdiv);
    iframeindex++;
}

//tag标签点击
$(".centercontainer ul").mousedown(function(event){
	var targetid=$(event.target).attr('menuid');
//	if(opentaglist[targetid]){
//	}
		$.each($('.tabmenu .centercontainer ul div'),function(index,object){
			object.style.background="none";
//			object.style.background="rgba(255,255,255,0.1)";
		})
		event.target.parentNode.style.background="rgba(255,255,255,0.6)";
		showIframe(targetid);
})

//选取标签并移动 menuid：菜单id
function selectTab(menuid){
	$.each($('.tabmenu div span'),function(index,object){
		if($(object).attr('menuid')==menuid){
			$.each($('.tabmenu .centercontainer ul div'),function(index,object){
				object.style.background="rgba(255,255,255,0.1)";
			})
			object.parentNode.style.background="rgba(255,255,255,0.5)";
			var conwidth=parseInt($(object.parentNode).css('width'));
			var divnum=$('.centercontainer ul div').length;
			var indexof =$('.tabmenu div').index($(object.parentNode))-1;
			var rightlim=parseInt($('.centercontainer ul').css('width'))+parseInt($('.centercontainer ul').css('right'));
			var leftlim=parseInt(($(".centercontainer ul").css('left')));
			var centercon=parseInt($('.tabmenu .centercontainer').css('width'));
			var ullength=parseInt($('.centercontainer ul').css('width'));
			var divposition=ullength-conwidth*indexof;
//			先统一移动到一个位置
			if((indexof)*conwidth>rightlim||(indexof)*conwidth<-leftlim){//应该移动否则不移动
				if(ullength-conwidth*(indexof)<centercon){
					$(".tabmenu .centercontainer ul").css("left",-(ullength-centercon)+'px');
				}else{
					$(".tabmenu .centercontainer ul").css("left",-(indexof-1)*conwidth+'px');
				}
			}
			showIframe(menuid);
		}
	})
}

//显示ifream窗口
function showIframe(iframeid){
	$.each($('#loaderdiv div'), function(index,object) {
//		找到对应的div
		if($(object).attr("iframeid")==iframeid){
			object.style.zIndex=iframeindex;
			iframeindex++;
		}
	});
}

//左右滚动
$(".tabmenu .rightarrow").mousedown(function(){
	var positionright =parseInt($(".tabmenu .centercontainer ul").css("right"));
	var positionleft =parseInt($(".tabmenu .centercontainer ul").css("left"));
//	如果right大于0就说明ul小于container,就不移动
	if(positionright>0){
		return false;
	}
	if(positionright+100<0){
		$(".tabmenu .centercontainer ul").css("left",positionleft-100+"px");
	}else{
		$(".tabmenu .centercontainer ul").css("left",-(Math.abs(positionright)+Math.abs(positionleft))+"px");
	}
});
$(".tabmenu .leftarrow").mousedown(function(){
	var positionright =parseInt($(".tabmenu .centercontainer ul").css("right"));
	var positionleft =parseInt($(".tabmenu .centercontainer ul").css("left"));
	var leftarrow=$('#maincontainer .tabmenu .leftarrow');
	var rightarrow=$('#maincontainer .tabmenu .rightarrow');
	if(positionright>0&&!positionleft<0){
		leftarrow.css('color','gray');
		rightarrow.css('color','gray');	
		return false;
	}
	if(positionleft<-100){
			$(".tabmenu .centercontainer ul").css("left",positionleft+100+"px");
		}
	if(positionleft<0&&positionleft>-100){
		$(".tabmenu .centercontainer ul").css("left","0px");
		var centercontainer=parseInt($('.tabmenu .centercontainer').css('width'));
		var ullength=parseInt($('.centercontainer ul').css('width'));
		if(ullength<centercontainer){
			leftarrow.css('color','gray');
			rightarrow.css('color','gray');	
		}
	}
})

//菜单js option为菜单配置对象
function accordionMenu(options) {
    this.config = {
        container           :  '#menu',                // 外层容器，默认外层容器为.wrap-menu,设置为空其实更好，防止页面上其他明明冲突，而且使用者忘记传入绑定节点
        menujson            :  '',                         //  JSON传进来的数据
        type                :  'click',                    // 默认为click 也可以mouseover
        rendercallback      :  null,                       // 渲染html结构后回调
        clickItemCallBack   :  null,                       // a每点击某一项时候回调,由于,参数是被点击的a标签,只能在回调函数中定义立即执行事件，不能绑定事件，若绑定，第一次点击等不会执行,原因是此回调函数是绑定在a点击事件中的，所以要点击a之后才会触发绑定
        ifFold              :  1,						   //0不折叠，1折叠并且折叠内部，2仅仅折叠同级，3456...没做完
        inittextindent      :  "2em"                       //缩进
    };
    this.listid={};
    this.init(options);
 }
 accordionMenu.prototype = {
//  constructor: AccordionMenu,
    init: function(options){
        this.config = $.extend(this.config,options || {});
        var self = this,
            _config = self.config;

        // 渲染html结构,可能是很多个菜单
        $(_config.container).each(function(index,item){
			//创建html结构
            self._renderHTML(item);
            // 给每一个菜单添加事件
            self._bindEnv(item);
        });
    },
    
    //渲染html结构
    _renderHTML: function(container){
        var self = this,
            _config = self.config;
        var ulhtml = $('<ul></ul>');
	        $(_config.menujson).each(function(index,item){
	            var url = item.url||" ";
	            var id = item.urlId||" ";
	            var name = item.title;
	            id=id.toString();
	//      	使用src属性避免触发a标签的跳转页面事件

	            var lihtml = $('<li><p class="spancon pclose" menuurl="'+url+'" menuid="'+id+'" menuname="'+name+'"><span class="textContainer"><span class="menuname">'+name+'</span></span></p></li>');
				$(lihtml).children('p').css("position","relative");
				$(lihtml).children('p').children('.textContainer').css("position","absolute");
	            $(lihtml).children('p').children('.textContainer').prepend('<span class="fontspan '+item.menuIcon+'"></span>');
//	            var img=$('<img src="/IEAM/menuManagement/getMenuIcon?menuId='+item.menuId+'"\>');
	            var img=$('<img src="'+item.icon+'"\>');
	            $(img).one("error", function(e){
				 	$(this).attr("src", "../img/default/defaultIcon.png");
				});
	            $(lihtml).children('p').children('.textContainer').prepend(img);
	            $(lihtml).children('p').children('.textContainer').append('<span class="backspan"></span>');
	            if(item.subMenu && item.subMenu.length > 0){
	            	//如果存在子菜单添加其他东西
	 				$(lihtml).children('p').children('.textContainer').append('<span class="hassubMenu"></span>');
	                self._createsubMenu(item.subMenu,lihtml);
	            }
	            $(ulhtml).append(lihtml);
	        });
	        $(container).empty();
	        $(container).append(ulhtml);
			//执行回调函数
	        _config.rendercallback && $.isFunction(_config.rendercallback) && _config.rendercallback(container);
	        // 处理层级缩进
	        self._levelIndent(ulhtml);
        	
    },


//   向lihtml中创建子菜单 array 子菜单 lihtml li项
    _createsubMenu: function(subMenu,lihtml){
        var self = this,
            _config = self.config;
        var subUl = $('<ul></ul>'),
            callee = arguments.callee,subLi;

        $(subMenu).each(function(index,item){
//      	是一个链接的话
			var id = item.urlId||" ";
			id=id.toString();
			var name = item.title||" ";
			try{
				var url = item.url||" ";
				if(url==" "){
//					throw "存在无url的最下级标签，请确认"
				}
			}catch(e){
				console.log(e);
			}
            subLi = $('<li><p class="spancon pclose" menuurl="'+url+'" menuid="'+id+'" menuname="'+name+'"><span class="textContainer">'+name+'</span></p></li>');
//          添加a标签前的icon
			$(subLi).children('p').css("position","relative");
			$(subLi).children('p').children('.textContainer').css('position',"absolute")
            $(subLi).children('p').children('.textContainer').prepend('<span class="fontspan '+item.menuIcon+'"></span>');
            $(subLi).children('p').children('.textContainer').append('<span class="backspan"></span>');
            if(item.subMenu && item.subMenu.length > 0) {
//          	如果存在下一级菜单回调自身
 				$(subLi).children('.textContainer').append('<span class="hassubMenu"></span>');
                callee(item.subMenu, subLi);
            }
            $(subUl).append(subLi);
        });
        $(lihtml).append(subUl);
    },

//  	处理层级缩进，传入整个html结构
    _levelIndent: function(ulList){
        var self = this,
            _config = self.config;
		
        var inittextindent = _config.inittextindent,
            level = 1,
            $oUl = $(ulList);
            
        while($oUl.find('ul').length > 0){
        	//根据根元素字体大小蛇者textindent
            inittextindent = parseInt(inittextindent,10) + parseInt(_config.inittextindent)+ _config.inittextindent.split(parseInt(inittextindent,10))[1]; 
            $oUl.children().children('ul').addClass('level-' + level).children('li').children('p')
                        .children('.textContainer').css('left', inittextindent);
            $oUl = $oUl.children().children('ul');
            level++;
        }
        $(ulList).find('ul').hide();
    },
    
   //绑定事件
    _bindEnv: function(container) {
  	//只做绑定的展开事件,并不做打开链接事件
        var self = this,
            _config = self.config;
            //先解除绑定，然后在绑定事件
        $('p',container).unbind(_config.type);
        $('p',container).bind(_config.type,function(e){
        	var p=$(this);
    	   switch(_config.ifFold){
            	case 0:break;
            	case 1:p.parent('li').siblings().find('ul').hide();break;
            	case 2:p.parent('li').siblings().children('ul').hide();break;
            }
            if(p.siblings('ul').length > 0) {
            	//如果存在下一级ul，执行其中元素动画效果,太慢的话会出现延迟效果
               
        		if(!p.hasClass('pclose')){
				//如果存在 close为关闭状态,，现在处于打开状态，关闭其他标签，改变上一级样式
					p.css('box-shadow','rgba(0,0,0,.25) 0 0px 0, inset rgba(255,255,255,.16) 0 0px 0');
					p.css('background','rgba(0,0,0,0)');
					p.parents('li').css('border-right','0px solid #1ABB9C');
					p.removeClass('popen');
					p.addClass('pclose');	
				}else{
					p.parent('li').parent('ul').children('li').children('p').removeClass('popen');
					p.parent('li').parent('ul').children('li').children('p').addClass('pclose');
					p.parent('li').parent('ul').children('li').children('p').css('box-shadow','rgba(0,0,0,.25) 0 0px 0, inset rgba(255,255,255,.16) 0 0px 0');
					p.parent('li').parent('ul').children('li').children('p').css('background','rgba(0,0,0,0)');
					p.parent('li').parent('ul').children('li').css('border-right','0px solid transparent');
					p.css('box-shadow','rgba(0,0,0,.25) 0 1px 0, inset rgba(255,255,255,.16) 0 1px 0');
					p.css('background','rgba(255,255,255,.05)');
					p.parents('li').css('border-right','5px solid #1ABB9C');
					p.removeClass('pclose');
					p.addClass('popen');
				}
			    p.siblings('ul').slideToggle(100);
           }   
            _config.clickItemCallBack && $.isFunction(_config.clickItemCallBack) && _config.clickItemCallBack(container,$(this),e.target);
        });
    }
 };