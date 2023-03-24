function AccordionMenu(options) {
    this.config = {
        containerCls        : '.wrap-menu',                // 外层容器，默认外层容器为.wrap-menu,设置为空其实更好，防止页面上其他明明冲突，而且使用者忘记传入绑定节点
        menuArrs            :  '',                         //  JSON传进来的数据
        type                :  'click',                    // 默认为click 也可以mouseover
        renderCallBack      :  null,                       // 渲染html结构后回调
        clickItemCallBack   :  null,                       // a每点击某一项时候回调,由于,参数是被点击的a标签,只能在回调函数中定义立即执行事件，不能绑定事件，若绑定，第一次点击等不会执行,原因是此回调函数是绑定在a点击事件中的，所以要点击a之后才会触发绑定
        ifFold              :  0,						   //0不折叠，1折叠并且折叠内部，2仅仅折叠同级，3456...没做完
        initTextIndent      :  "2em"                       //缩进
    };
    this.init(options);
 }
 AccordionMenu.prototype = {
//  constructor: AccordionMenu,
    init: function(options){
        this.config = $.extend(this.config,options || {});
        var self = this,
            _config = self.config;
        // 渲染html结构,可能是很多个菜单
        $(_config.containerCls).each(function(index,item){
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
//      	console.log('aaaa');
        
        $(_config.menuArrs).each(function(index,item){
//      	console.log(item);
            var url = item.url||" ";
//          console.log(item.menuInfo);
            var id = item.id||" ";
            var name = item.title;
//      	使用src属性避免触发a标签的跳转页面事件
            var lihtml = $('<li><p class="spancon pclose" menuurl="'+url+'" menuid="'+id+'" menuname="'+name+'"><span class="textContainer"><span class="menuname">'+name+'</span></span></p></li>');
			$(lihtml).children('p').css("position","relative");
			$(lihtml).children('p').children('.textContainer').css("position","absolute");
            $(lihtml).children('p').children('.textContainer').prepend('<span class="fontspan '+item.menuIcon+'"></span>');
            $(lihtml).children('p').children('.textContainer').append('<span class="backspan"></span>');
            if(item.subMenu && item.subMenu.length > 0){
            	//如果存在子菜单添加其他东西
            	
 				$(lihtml).children('p').children('.textContainer').append('<span class="hassubMenu"></span>');
                self._createsubMenu(item.subMenu,lihtml);
            }
            $(ulhtml).append(lihtml);
        });
        $(container).append(ulhtml);
		//执行回调函数
        _config.renderCallBack && $.isFunction(_config.renderCallBack) && _config.renderCallBack(container);
        // 处理层级缩进
        self._levelIndent(ulhtml);
    },
    /**
     * 向lihtml中创建子菜单
     * @param {array} 子菜单
     * @param {lihtml} li项
     */
    _createsubMenu: function(subMenu,lihtml){
        var self = this,
            _config = self.config;
        var subUl = $('<ul></ul>'),
            callee = arguments.callee,
            subLi;

        $(subMenu).each(function(index,item){
//      	是一个链接的话

            var url = item.url||" ";
            var id = item.id||" ";
            var name = item.title;
            subLi = $('<li><p class="spancon pclose" menuurl="'+url+'" menuid="'+id+'" menuname="'+name+'"><span class="textContainer">'+name+'</span></p></li>');
//          添加a标签前的icon
			$(subLi).children('p').css("position","relative");
			$(subLi).children('p').children('.textContainer').css('position',"absolute")
            $(subLi).children('p').children('.textContainer').prepend('<span class="fontspan '+item.menuIcon+'"></span>');
            $(subLi).children('p').children('.textContainer').append('<span class="backspan"></span>');
            if(item.subMenu && item.subMenu.length > 0) {
//          	如果存在下一级菜单
//				回调
 				$(subLi).children('.textContainer').append('<span class="hassubMenu"></span>');
                callee(item.subMenu, subLi);
            }
            $(subUl).append(subLi);
        });
        $(lihtml).append(subUl);
    },
    /**
     * 处理层级缩进，传入整个html结构
     */
    _levelIndent: function(ulList){
        var self = this,
            _config = self.config;
		
        var initTextIndent = _config.initTextIndent,
            lev = 1,
            $oUl = $(ulList);

        while($oUl.find('ul').length > 0){
        	//根据根元素字体大小蛇者textindent
            initTextIndent = parseInt(initTextIndent,10) + parseInt(_config.initTextIndent)+ _config.initTextIndent.split(parseInt(initTextIndent,10))[1]; 
            $oUl.children().children('ul').addClass('lev-' + lev).children('li').children('p')
                        .children('.textContainer').css('left', initTextIndent);
            $oUl = $oUl.children().children('ul');
            lev++;
        }
//      $(ulList).find('ul').hide();
    },
    /**
     * 绑定事件
     */
    _bindEnv: function(container) {
//  	只做绑定的展开事件,并不做打开链接事件
        var self = this,
            _config = self.config;
            //先解除绑定，然后在绑定事件
        $('p',container).unbind(_config.type);
        $('p',container).bind(_config.type,function(e){
            if($(this).siblings('ul').length > 0) {
            	//如果存在下一级ul，执行其中元素动画效果,太慢的话会出现延迟效果
//              $(this).siblings('ul').slideToggle(100);
            }
            switch(_config.ifFold){
            	case 0:break;
            	case 1:$(this).parent('li').siblings().find('ul').hide();break;
            	case 2:$(this).parent('li').siblings().children('ul').hide();break;
            }
            _config.clickItemCallBack && $.isFunction(_config.clickItemCallBack) && _config.clickItemCallBack(container,$(this),e.target);
        });
    }
 };