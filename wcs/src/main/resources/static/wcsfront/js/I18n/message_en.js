(function(window) {

	window.MESResource = {
		"common.notice" : "Notice",
		"save.success" : "Save Success!",
		"save.faild" : "Save Failed!",
		"delete.success" : "Delete Success!",
		"delete.faild" : "Delete Failed!",
		"add" : "Add",
		"update" : "Edit",
		"mapping" : "Column Mapping",
		"mappingADD" : "New Column Mapping",
		"selectOne" : "Please Choose One!",
		"confirm.delete" : "Confirm?",
		"select.error" : "Select Failed",
		"markAble" : "MarkAble Status",
		"markAble.yes" : "Yes",
		"markAble.no" : "No",
		"createUser" : "Create User",
		"createDate" : "Create Date",
		"updateUser" : "Update User",
		"updateDate" : "Update Date",
		"wating" : "Wating...",
		"yes":"Yes",
		"no":"No",
		"checkTime" : "Click too frequent, please try later!",
		"format.error":"Incorrectly formatting",
		"isDel" : "Delete Status",
		"isDel.yes" : "Yes",
		"isDel.no" : "No",
		"select.noResult":"No Result！",
		"not.null":"Not Null！",
		"operate.success" : "Operate Success!",
		"operate.faild" : "Operate Failed!",
		"inputContent" : "Please enter the content!",
		"inputEnglishCharacters" : "Please enter English characters!",
		"selectContent" : "Please select content!",
		"inputNumber" : "Please input number！",
		"inputMail" : "Mailbox address format is incorrect!",
		"inputMobile" : "The format of the phone number is wrong!",
		"inputPhone" : "The telephone format is wrong!",
		"common.pack":"pack",
		"common.spread":"spread"
	};
	
	window.MESResource.jobInfo = {
		"jobInfo.confirm.pause":"Pause?",
		"jobInfo.pause.success":"Pause Success！",
		"jobInfo.pause.faild":"Pause Failed！",
		"jobInfo.confirm.resume":"Resume?",
		"jobInfo.resume.success":"Resume Success！",
		"jobInfo.resume.faild":"Resume Failed！"
	};

	window.MESResource.userInfo = {
		"userInfo.colum.selectOne" : "Please Choose One",
		"userInfo.colum.selectMore" : "Please choose at least one"
	};
	window.MESResource.roleInfo = {
		"roleInfo.colum.selectOne" : "Please choose One",
		"roleInfo.colum.selectMore" : "Please choose at least one",
		"roleInfo.colum.repeat" : "Repeat"
	};
	window.MESResource.tableModelInfo = {
		"tableModelInfo.table.tablename" : "Registry Name",
		"tableModelInfo.colum.columName" : "Column Name",
		"tableModelInfo.colum.columDesc" : "Column Desc",
		"tableModelInfo.colum.columDescEn" : "Column DescEn",
		"tableModelInfo.colum.columProperty" : "Column Property",
		"tableModelInfo.colum.columProperty.yb" : "Common Column",
		"tableModelInfo.colum.columProperty.wj" : "Foreign Column",
		"tableModelInfo.colum.columProperty.bm" : "CodeRule Column",
		"tableModelInfo.colum.foreignTable" : "Foreign Table",
		"tableModelInfo.colum.foreignColum" : "Foreign Table Column",
		"tableModelInfo.colum.codeRuleId" : "Colde Rule",
		"tableModelInfo.colum.selectOne" : "Please Select One Of Registry!",
		"tableModelInfo.colum.selectForeignTable" : "Please Choose Foreign Table!"

	};
	window.MESResource.busiModelInfo = {
		"busiModelInfo.busi.selectOne" : "Please Select One Of Business Model!",
		"busiModelInfo.busi.checkCodeUnique" : "Business model code must be unique!",
		"busiModelInfo.view.isparent" : "The business model node must be a child node!",
		"busiModelInfo.view.checkCodeUnique" : "View model code must be unique under same business model!",
		"busiModelInfo.view.selectOne" : "Please select view model node!",
		"busiModelInfo.view.selectFirstNode" : "Please select the main view!"

	};
	window.MESResource.viewModelMappingInfo = {
		"viewModelMappingInfo.colum.viewCode" : "View Code",
		"viewModelMappingInfo.colum.tableName" : "View Table Name",
		"viewModelMappingInfo.colum.columName" : "View Table Column",
		"viewModelMappingInfo.colum.mappingViewCode" : "Mapping View Code",
		"viewModelMappingInfo.colum.mappingTableName" : "Mapping Table Name",
		"viewModelMappingInfo.colum.mappingColumName" : "Mapping Column Name",
		"viewModelMappingInfo.colum.remark" : "Business Desc",
		"viewModelMappingInfo.colum.createUser" : "Create User",
		"viewModelMappingInfo.colum.createDate" : "Create Date"


	};
	window.MESResource.pageConfig = {
		"pageConfig.colum.pageCode" : "Page Code",
		"pageConfig.colum.pageDesc" : "Page Desc",
		"pageConfig.colum.busiCode" : "Business Model Node",
		"pageConfig.colum.menuCode" : "Menu Code",
		"pageConfig.colum.checkPageCodeUnique" : "Config page code must be unique!",
		"pageConfig.colum.addRuleError" : "Code rule generate failed!",
		"tableModelInfo.colum.columType" : "Column Type",
		"tableModelInfo.colum.widgetType" : "Widget Type",
		 "pageConfig.colum.nocontent" : "Please select flush content!",
		"pageConfig.colum.flushSuccess" : "Flush success",
		"pageConfig.colum.flushFail" : "Flush failed"
	};
	window.MESResource.searchPageConfig = {
		"searchPageConfig.add.selectTableName" : "Please select registry",
		"searchPageConfig.table.columType.rq" : "Date",
		"searchPageConfig.table.columType.zf" : "Varchar",
		"searchPageConfig.table.widgetType.wbk" : "Text",
		"searchPageConfig.table.widgetType.xlk" : "Select",
		"searchPageConfig.table.widgetType.jlxlk" : "Cascade Select",
		"searchPageConfig.table.widgetType.mrtcct" : "Default Modal",
		"searchPageConfig.table.widgetType.zdytcct" : "Defined Modal",
		"searchPageConfig.table.baseCode" : "Default Select Params",
		"searchPageConfig.table.parentColumName" : "Parent Column Name",
		"searchPageConfig.table.orderNo" : "Order",
		"searchPageConfig.table.columAlias" : "Column Alias",
		"searchPageConfig.table.viewCode" : "View Model",
		"searchPageConfig.table.viewDesc" : "View Model Desc"

	};
	window.MESResource.resultPageConfig = {
		"resultPageConfig.add.selectTableName" : "Please Select Registy",
		"resultPageConfig.table.columType.rq" : "Date",
		"resultPageConfig.table.columType.zf" : "Varchar",
		"resultPageConfig.table.columType.sz" : "Number",
		"resultPageConfig.table.columType.fxk" : "Check Box",
		"resultPageConfig.table.columSource" : "Data Source",
		"resultPageConfig.table.columSource.sjkb" : "DataBase Table",
		"resultPageConfig.table.columSource.zdyff" : "Defined Method",
		"resultPageConfig.table.appendSql" : "select Sql",
		"resultPageConfig.table.appendMethod" : "Defined Method",
		"resultPageConfig.table.displayType":"TableGrid Display",
		"resultPageConfig.table.displaySort":"Order"
		

	};
	window.MESResource.columnPageConfig = {
		"columnPageConfig.add.selectColumn" : "Please add column first",
		"columnPageConfig.type.selectOneColumn" : "Please select one column of configured",
		"columnPageConfig.type.saveColumns" : "Please save configured columns",
		"columnPageConfig.add.columnType" : "Column Type"

	};
	window.MESResource.menuConfMulti = {
			"menuConfMulti.add.childMappings" : "Please maintain column mappings between view models"

	};
	window.MESResource.systemSign = {
			"systemSign.systemNo" : "System No.",
			"systemSign.systemName" : "System Name",
			"systemSign.systemLinke" : "Contact Person",
			"systemSign.systemPhone" : "Contact Phone",
			"systemSign.systemEmail" : "Contact Email",
			"systemSign.find" : "item"
		};
	window.MESResource.dataDistribute = {
			"dataDistribute.systemNo" : "System No.",
			"dataDistribute.tableName" : "Interface Table Name",
			"dataDistribute.type" : "Table Type",
			"dataDistribute.type.table" : "Table",
			"dataDistribute.type.view" : "View",
			"dataDistribute.field" : "Column",
			"dataDistribute.systemName" : "System Name",
			"dataDistribute.remarks" : "Remarks",
			"dataDistribute.lineNo" : "Line No.",
			"dataDistribute.fieldName" : "Field Name",
			"dataDistribute.snr" : "select not results!"
		};
	window.MESResource.codeDef = {
		"codeDef.conflict" : "CodeNo and TypeCode must be unique",
		"codeDef.error" : "Service error"
	};
	window.MESResource.codeType = {
			"codeType.typeCode" : "Type Code",
			"codeType.typeName" : "Type Name"
		};
	window.MESResource.codeRule = {
		"codeRule.errorOne" : "The end of the rules is wrong！",
		"codeRule.errorTwo" : "Please select the type of the number！",
		"codeRule.errorThree" : "Fixed number of numeric digits！",
		"codeRule.errorFour" : "The number of random numbers is mistaken！",
		"codeRule.errorFive" : "The number of digits in the flow is mistaken！",
		"codeRule.errorsix" : "Date digits error！",
		"codeRule.inputContent" : "Please enter the value！",
		"codeRule.conflict" : "Rule code must be unique！",
		"codeRule.error" : "Service error！"

	};
	window.MESResource.initializeData = {
			"initializeData.import.success" : "Import Success!",
			"initializeData.selectNeedExcel" : "Please select import excel file",
			"initializeData.selectExcel" : "Select the file import in Excel format",
			"initializeData.pleaseSelect" : "Please select content！"
		};
	window.MESResource.menuAndButton={
		"menuAndButton.pageCode" : "Config Page Code",
		"menuAndButton.pageDesc" : "Config Page Desc",
		"menuAndButton.buttonLabel" : "Button Label",
		"menuAndButton.iconStyle" : "Button IconStyle",
		"menuAndButton.event" : "Event",
		"menuAndButton.buttonName" : "Button Name",
		"menuAndButton.buttonName_EN" : "Button Name En",
		"menuAndButton.remark" : "Remark"
	};
	window.MESResource.inputTest = {
		"input.zcbm":"Registry code can't be null",
		"input.zcbms":"Registry name can't be null",
		"input.zdbm":"Column code can't be null",
		"input.zdmc":"Column name can't be null",
		"input.zdbim":"Column Alias can't be null",
		"input.ywmc":"Business Name can't be null",
		"input.stzw":"View Name can't be null",
		"input.styw":"View Name En can't be null",
		"input.zcsjb":"Registry can't be null",
		"input.stmx":"View Model can't be null",
		"input.zdzms":"Column Name can't be null",
		"input.zdyms":"Column Name En can't be null",
		"input.kjlx":"Please select widget type"
	}
	window.MESResource.report = {
			"option.default":"Please choose",
			"th.reportName":"Report Name",
			"th.fileName":"File Name",
			"th.createUser":"Create User",
			"th.createTime":"Create Date",
			"alert.rnnn":"Report Name can't be null",
			"alert.ars":"Add report success",
			"alert.rne":"Report name repeat",
			"alert.se":"Service error",
			"alert.sir":"Please select report file",
			"alert.scfi":"Please select file in the .cpt or .frm formate"

		}
	window.MESResource.druid = {
			"ExecuteCount":"ExecuteCount",
			"ExecuteTimeMillis":"ExecuteTimeMillis",
			"MaxTimespan":"MaxTimespan",
			"InTransactionCount":"Txn",
			"ErrorCount":"ErrorCount",
			"EffectedRowCount":"UpdateCount",
			"FetchRowCount":"FetchRowCount",
			"RunningCount":"RunningCount",
			"ConcurrentMax":"ConcurrentMax",
			"Histogram":"ExecHisto",
			"ExecuteAndResultHoldTimeHistogram":"ExecRsHisto",
			"FetchRowCountHistogram":"FetchRowHisto",
			"EffectedRowCountHistogram":"UpdateHisto"
		}
})(window)
