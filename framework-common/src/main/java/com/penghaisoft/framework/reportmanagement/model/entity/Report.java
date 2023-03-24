package com.penghaisoft.framework.reportmanagement.model.entity;

/**
 * 报表数据实体
 */
public class Report {
	//报表id
	Integer id;
	//报表名
	String title;
	//报表描述
	String description;
	//报表对应路径
	String path;
	//报表对应路径Url
	String url;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Report(){}
	
	public Report(String title, String description, String url,String path){
		
		this.title = title;

		if(description != null){
			this.description = description;
		}else{
			this.description = "";
		}

		this.url = url;

		this.path = path;

	}
}
