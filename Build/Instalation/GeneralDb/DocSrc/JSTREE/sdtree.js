// URL: http://www.softdrawer.com/products/tree/
// Email: support@softdrawer.com
// Version: 3.0.2
// Last Modify: 18 Jun 2003
// This script is FREEWARE and also we can provide script without "watermark"
// if you put a back link to www.softdrawer.com at your links page.
// Visit http://www.softdrawer.com/products/treepro/ to get information about 
// professional version of jsTree.
// Copyright (c) 1999-2003 by SoftDrawer.Com
bw=new Browser();
function SoftDrawerTree(name,def){
	this.def=def;this.format=def.format;this.name=name;this.num=0;this.anim_ref=0;this.id='SDTree'+name;
	this.nodes=[];this.root={id:name+"_i"};this.root.level_format=this.format;
	this.root.format=this.format;
	this.root.sub=def.sub;eval("window."+this.id+"=this");
	if (!bw) bw=new Browser();
	this.init=function(){
		this.code='';
		this.back=new DHTMLObject(this.name+'_back');
		this._init(null,this.root,-1,0);
		if (!this.format.no_images) {var f=this.format;pldImg([f.e_image,f.c_image])};
		document.write(this.back.draw(this.format.left,this.format.top,this.format.width,this.format.height,this.getp(this.root,"back_bgcolor",0),0,1,this.getp(this.root,"back_class",0),this.code,0)+this.wm_code());
		this.back.init();if (this.format.dont_resize_back)this.back.show();
	};
	this._init=function(parent,node,lvl,num){
		if(!node) return; node.parent=parent; node.id=node.id||node.parent.id+"_"+num;node.lvl=lvl;node.ind=(this.num++)-1;
		if(node!=this.root){
			this.nodes[node.id]=node;var s=this.init_item(node);
			node.obj=new DHTMLObject(node.id);
			this.code+=(node.obj.draw(0,0,this.getp(node,"width","100%"),0,this.getp(node,"bgcolor",0),0,(1000-this.num),this.getp(node,"div_class",0),s));
		}
		if(node.sub) for(var i=0;i<node.sub.length;i++) this._init(node,node.sub[i], lvl+1,i);
	};
	this.wm_code=function(){
	this.wm=new DHTMLObject(this.name+'_wm');
		return this.wm.draw(this.format.left,this.format.top+this.format.height+2,this.format.width,12,0,1,2,0,(bw.ns4?'<font face="Verdana, sans-serif" color=silver point-size=6>':'<div style="font-size:8px;color:silver;">')+unescape('%53%6F%66%74%44%72%61%77%65%72%20%6A%73%54%72%65%65')+(bw.ns4?'</font>':'</div>'));
	};
	this.getp=function(n,name,def,nf){return this.format[name]||def};
	this.indent_code=function(n){
		var indent=this.getp(n,"indent",parseInt(this.format.level_indent||16)*n.lvl-this.getp(n,"padding",0))||0;
		return n.lvl&&indent?'<td><img src="'+this.format.b_image+'" width="'+indent+'" height="1"></td>':'';
	};
	this.init_item=function(n){
		var tc=this.getp(n,"table_class",0);
		var s='<table width="'+this.getp(n,"width","100%")+'" cellpadding="'+this.getp(n,"padding",0)+'" cellspacing="'+this.getp(n,"spacing",0)+'" border="0"'+(tc?' class="'+tc+'"':'')+'><tr>';
		s+=this.indent_code(n);
		var img=this.getp(n,(n.sub?n.exp?"e":"c":"i")+"_image",this.format.b_image);
		var sz=this.getp(n,"img_size",[32,16]);
		s+=!this.getp(n,"no_images",0)?'<td>'+(n.sub?'<a onclick="if(this.blur)this.blur()" href="javascript:Toggle(\''+this.name+'\',\''+n.id.substr(this.name.length+3)+'\',0)">':'')+'<img '+(bw.ns4?'name="'+n.id+'_img" ':'')+'id="'+n.id+'_img" src="'+img+'" width="'+sz[0]+'" height="'+sz[1]+'" border="0"/>'+(n.sub?'</a>':'')+'</td>':'';
		var bgc=this.getp(n,"item_bgcolor",0);bgc=bgc?' bgcolor="'+bgc+'"':' ';
		var targ=n.target||this.format.target||0;
		tc=this.getp(n,"item_class",0);var lc=this.getp(n,"link_class",0);
		var hr='Toggle(\''+this.name+'\',\''+n.id.substr(this.name.length+3)+'\',1)';
		s+='<td width="100%"'+bgc+(tc?'class="'+tc+'"':'')+'><a '+(lc?'class="'+lc+'"':'')+'href="'+(n.url||'javascript:'+(n.sub?hr:'void(0)'))+'" onclick="if(this.blur)this.blur();'+(n.sub&&n.url?'Toggle(\''+this.name+'\',\''+n.id.substr(this.name.length+3)+'\',1)':'')+'"'+(targ?' target="'+targ+'"':'')+'>'+n.html+'</a>'+'</td>';
		s+="</tr></table>";
		return s;
	};
	this.get_image=function(par,name){return bw.dom?document.getElementById(name):bw.ie4?document.all[name]:bw.ns4?par.el.document.images[name]:[]};
	this.update=function(n){
		if(this.format.no_images) return;
		var src=this.getp(n,(n.sub?n.exp?"e":"c":"i")+"_image",this.format.b_image);
		var img=this.get_image(n.obj,n.id+'_img')||[];
		img.src=src;
	};
	this.close_level=function(p,n){
		for(var i=0;i<p.sub.length;i++)
			if(p.sub[i]!=n&&p.sub[i].sub&&p.sub[i].exp){
				p.sub[i].exp=0; this.update(p.sub[i]);
				this.collapse_children(p.sub[i],p.sub[i].obj.x,p.sub[i].obj.y,this.format.animation,1,1);
			}
	};
	this.click=function(id){
		this.togg(id);
	};
	this.togg=function(id){
		if(this.anim_ref>0){return};
		var n=this.nodes[id];
		n.exp=!n.exp;
		if(n.exp&&this.format.one_branch) this.close_level(n.parent,n);
		this.update(n);
		if(this.format.animation&&n.sub&&n.exp) this.collapse_children(n,n.obj.x,n.obj.y,0,0,0);
		if(n.sub&&!n.exp) this.collapse_children(n,n.obj.x,n.obj.y,this.format.animation,1,1,1);
		this.draw(0);
	};
	this.collapse_children=function(n,x,y,anim,f,hide,no_move){
		if(!n.obj.el) n.obj.init(this.back);
		if(f)if(!anim){if(!no_move)n.obj.move(x,y);if(hide)n.obj.hide()} else this.slide(n,x,y,this.format.anim_step||20,this.format.anim_timer||20,hide?n.obj.name+".hide()":'');
		if(n.sub) for(var i=0;i<n.sub.length;i++) this.collapse_children(n.sub[i],x,y,anim,1,hide)
	};
	this.draw=function(no_anim){
		if(this.onbeforedraw) this.onbeforedraw(this);
		this.top=0;this.dnum=0;
		this._draw(this.root,no_anim);
		if(!(this.format.animation&&!no_anim)) this.resize_back(0,0);
	};
	this.resize_back=function(x,y){
		if(this.format.dont_resize_back) return;
		var n=this.last_drawed;
		n.bott=n.obj.y+n.obj.h;
		this.back.resize(x||this.format.width, y||n.bott,1);
		this.back.show();
		if(!this.wm.el) this.wm.init();
		this.wm.move(this.back.x,this.back.y+this.back.h+2);
	};
	this.expand=function(n,e){if(!n.obj.el) n.obj.init(this.back);if(n.sub){n.exp=e;this.update(n);}};
	this.expandAll=function(){for(var i in this.nodes)this.expand(this.nodes[i],1)};
	this.collapseAll=function(){for(var i in this.nodes){this.expand(this.nodes[i],0);this.nodes[i].obj.hide()}};
	this.slide=function(n,x,y,speed,step,done,estep){this.anim_ref++;n.obj.slide(x,y,speed,step,this.id+".anim_ref--;"+done, estep||this.id+".resize_back(0,0)")};
	this._draw=function(n,no_anim){
		this.last_drawed=n;
		this.dnum++;
		if(this.root!=n){
			if(!n.obj.el) n.obj.init(this.back);
			var indent=this.getp(n,"real_indent",0);
			if(indent!=n.obj.x||this.top.y!=n.obj.y) {
				if(a=this.format.animation&&!no_anim) {
					n.obj.show();
					this.slide(n,indent,this.top,this.format.anim_step||20,this.format.anim_timer||20,'');
				}else{
					n.obj.move(indent,this.top);
					n.obj.show();
				}
			}
			this.top+=n.obj.h+this.getp(n,"offset_y",0);;
		}
		if(n.sub&&(n.exp||this.root==n))
			for(var i=0;i<n.sub.length;i++)
				this._draw(n.sub[i], no_anim);
	};
	this.init();this.draw(1);
	return this;
}
function Browser(){
    this.ver=navigator.appVersion;
    this.agent=navigator.userAgent;
    this.dom=document.getElementById?1:0;
    this.o5=this.agent.indexOf("Opera 5")>-1;
    this.ie4=(document.all && !this.dom && !this.o5)?1:0;
    this.ns4=(document.layers && !this.dom)?1:0;
    return this};
function DHTMLObject(id){
	this.id=id;
	this.name='DHTMLObject_'+this.id;
	this.init=function(ns4p){this.el=bw.dom?document.getElementById(this.id):bw.ie4?document.all[this.id]:bw.ns4?ns4p?ns4p.doc.layers[this.id]:document.layers[this.id]:0;if(!this.el) return;this.css=bw.dom||bw.ie4?this.el.style:this.el;this.doc=bw.dom||bw.ie4?document:this.css.document;this.x=parseInt(this.css.left)||this.css.pixelLeft||this.el.offsetLeft||0;this.y=parseInt(this.css.top)||this.css.pixelTop||this.el.offsetTop||0;this.w=this.el.offsetWidth||this.css.clip.width||this.doc.width||this.css.pixelWidth||0;this.h=this.el.offsetHeight||this.css.clip.height||this.doc.height||this.css.pixelHeight||0;this.h=bw.opera7?this.el.firstChild.offsetHeight:this.h;this.w=bw.opera7?this.el.firstChild.offsetWidth:this.w;if(this.initClip)this.initClip()};
	this.draw=function(x,y,w,h,bg,v,z,css,code,rel,mover,mout){return (bw.ns4?'<'+(rel?'i':'')+'layer id="'+this.id+'" z-index='+z+' left='+x+' top='+y+' '+(w&&w!='auto'?'width='+w:'')+' height='+h+(!v?' visibility=hidden':'')+(bg!=""?' bgcolor="'+bg+'"':'')+(css!=''?' class="'+css+'"':'')+(mover?' onmouseover="'+mover+'"':'')+(mout?' onmouseout="'+mout+'"':'')+'>'+code+'</'+(rel?'i':'')+'layer>':'<div id="'+this.id+'" style="position:'+(rel?'relative':'absolute')+';z-index:'+z+';left:'+x+'px;top:'+y+'px;'+(w?'width:'+w+(w=='auto'?'':'px;'):'')+(h?'height:'+h+'px;':'')+(!v?'visibility:hidden;':'')+(bg!=""?''+(bw.ns4?'layer-':'')+'background-color:'+bg+';':'')+'"'+(css!=''?' class="'+css+'"':'')+'>'+code+'</div>')};
	this.move=function(x,y){this.css.left=this.x=x;this.css.top=this.y=y};
	this.moveBy=function(x,y){this.css.left=this.x+=x; this.css.top=this.y+=y};
	this.resize=function(w,h,clip){this.css.pixelWidth=this.css.width=this.w=w;this.css.pixelHeight=this.css.height=this.h=h;if(clip&&this.clip)this.clip(0,w,h,0)};
	this.show=function(){this.css.visibility="visible"};
	this.hide=function(){this.css.visibility="hidden"};
	this.bgColor=function(color){if(bw.opera) this.css.background=color; else if(bw.dom || bw.ie4) this.css.backgroundColor=color; else if(bw.ns4) this.css.bgColor=color};
	this.slide=function(toX,toY,step,speed,execDone,execStep){if(!this.inWay){var newX=toX-this.x;var newY=toY-this.y;var num=Math.sqrt(Math.pow(newX,2)+Math.pow(newY,2))/step;var stepX=newX/num; var stepY=newY/num;this.inWay=1;this.slideInt(stepX,stepY,toX,toY,speed,execDone,execStep)}};
	this.slideInt=function(stepX,stepY,toX,toY,speed,execDone,execStep){function fabs(v){return Math.floor(Math.abs(v))};if(this.inWay&&(fabs(stepX)<fabs(toX-this.x)||fabs(stepY)<fabs(toY-this.y))){this.moveBy(stepX,stepY);if(execStep)eval(execStep);setTimeout(this.name+".slideInt("+stepX+","+stepY+","+toX+","+toY+","+speed+",'"+execDone+"', '"+execStep+"')",speed)}else{this.inWay=0;this.move(toX,toY);if(execStep)eval(execStep);if(execDone)eval(execDone)}};
	this.initClip=function(){this.clp=[];if((bw.dom||bw.ie4)&&this.css.clip){this.clp=this.css.clip.slice(5,this.clp.length-1).split(' ');for(var i=0;i<4;i++)this.clp[i]=parseInt(this.clp[i])};this.ct=this.css.clip.top||this.clp[0]||0; this.cr=this.css.clip.right||this.clp[1]||this.w||0;this.cb=this.css.clip.bottom||this.clp[2]||this.h||0;this.cl=this.css.clip.left||this.clp[3]||0};
	this.clip=function(t,r,b,l,resize){if(bw.ns4){this.css.clip.top=this.ct=t;this.css.clip.right=this.cr=r;this.css.clip.bottom=this.cb=b;this.css.clip.left=this.cl=l}else{this.ct=t>0?t:0;this.cr=r>0?r:0;this.cb=b>0?b:0;this.cl=l>0?l:0;this.css.clip="rect("+this.ct+","+this.cr+","+this.cb+","+this.cl+")"}if(resize)this.resize(this.cr,this.cb,0)};
	eval(this.name+'=this');
	return this;
}
function pldImg(arg){for(var i in arg)if(arg[i]&&arg[i]!=''){var im=new Image();im.src=arg[i];}}
function und(val){return typeof(val)=='undefined'}
function Toggle(name,id,br){void(eval('SDTree'+name+'.click(\''+name+'_i_'+id+'\','+br+')'));}
