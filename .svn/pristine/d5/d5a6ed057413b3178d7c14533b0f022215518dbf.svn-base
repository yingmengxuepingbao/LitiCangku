/*
 Navicat Premium Data Transfer

 Source Server         : wcs
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:1433
 Source Schema         : nh_wms

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 15/08/2022 15:42:42
*/
DROP TABLE [dbo].[base_dict_item];
DROP TABLE [dbo].[base_dict_type];
DROP TABLE [dbo].[base_pda_version];
DROP TABLE [dbo].[base_unit];
DROP TABLE [dbo].[department];
DROP TABLE [dbo].[department_role];
DROP TABLE [dbo].[department_type];
DROP TABLE [dbo].[login_log];
DROP TABLE [dbo].[operation_log];
DROP TABLE [dbo].[report];
DROP TABLE [dbo].[resources];
DROP TABLE [dbo].[role];
DROP TABLE [dbo].[role_res];
DROP TABLE [dbo].[user];
DROP TABLE [dbo].[user_department];
DROP TABLE [dbo].[user_role];
DROP TABLE [dbo].[wms_address_real_rela];
DROP TABLE [dbo].[wms_address_real_rela_qxf];
DROP TABLE [dbo].[wms_address_transform];
DROP TABLE [dbo].[wms_box_barcode];
DROP TABLE [dbo].[wms_combine_log];
DROP TABLE [dbo].[wms_customer];
DROP TABLE [dbo].[wms_goods];
DROP TABLE [dbo].[wms_in_temporary];
DROP TABLE [dbo].[wms_in_out_tactics];
DROP TABLE [dbo].[wms_interface_call_log];
DROP TABLE [dbo].[wms_location_stereoscopic];
DROP TABLE [dbo].[wms_move_log];
DROP TABLE [dbo].[wms_move_stereoscopic];
DROP TABLE [dbo].[wms_order_check];
DROP TABLE [dbo].[wms_order_check_pallet];
DROP TABLE [dbo].[wms_order_check_pallet_box_barcode];
DROP TABLE [dbo].[wms_order_cross_in];
DROP TABLE [dbo].[wms_order_cross_in_deail];
DROP TABLE [dbo].[wms_order_cross_product];
DROP TABLE [dbo].[wms_order_cross_product_deail];
DROP TABLE [dbo].[wms_order_in];
DROP TABLE [dbo].[wms_order_out_plane];
DROP TABLE [dbo].[wms_order_out_plane_deail];
DROP TABLE [dbo].[wms_order_out_stereoscopic];
DROP TABLE [dbo].[wms_order_out_stereoscopic_deail];
DROP TABLE [dbo].[wms_out_temporary];
DROP TABLE [dbo].[wms_out_temporary_box_barcode];
DROP TABLE [dbo].[wms_pallet];
DROP TABLE [dbo].[wms_plane_location];
DROP TABLE [dbo].[wms_platform];
DROP TABLE [dbo].[wms_send_log];
DROP TABLE [dbo].[wms_storage_plane];
DROP TABLE [dbo].[wms_supplier];
DROP TABLE [dbo].[wms_task_execution_log];
DROP TABLE [dbo].[wms_warehouse];
DROP TABLE [dbo].[wms_warehouse_area];

-- ----------------------------
-- Table structure for base_dict_item
-- ----------------------------
CREATE TABLE base_dict_item (
 dict_item_id varchar(64)  not null  PRIMARY KEY,
 dic_type_code varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
 dic_item_name varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
 dic_item_code varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
 sequence_number int DEFAULT NULL  ,
 remark varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
 user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
 user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
 user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
 user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
 user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
 create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
 gmt_create datetime DEFAULT (GETDATE())  ,
 last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
 gmt_modified datetime DEFAULT (GETDATE())  ,
 active_flag char  COLLATE Chinese_PRC_CI_AS default '1'
) 

-- ----------------------------
-- Records of base_dict_item
-- ----------------------------
INSERT INTO base_dict_item VALUES ('0ac461d214274d6a86826d03f6776393', 'wh_type', '化工仓库', 'hg', 3, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-03-20 16:55:23', '管理员', '2020-04-07 13:24:57', '1');
INSERT INTO base_dict_item VALUES ('0b1236a06c584492a6893e4aea190628', 'man_page', '展示天数', '27', 1, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-10-14 10:52:54', '宝佳管理员', '2021-01-28 15:39:14', '1');
INSERT INTO base_dict_item VALUES ('0ce66c499a26433bb26199421e1d6c3f', 'order_out_type', '销售出库', '20', 3, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-05-26 17:36:52', '管理员', '2020-09-23 10:19:28', '1');
INSERT INTO base_dict_item VALUES ('0e970042a5bd4203813f426396130cdc', 'supplier_type', '委外加工供应商', 'ww_supplier', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('1', 'wh_type', '食品仓库', 'sp', 1, 'NA', NULL, NULL, NULL, NULL, NULL, NULL, '2020-02-05 14:38:35', NULL, '2020-02-05 14:38:35', '1');
INSERT INTO base_dict_item VALUES ('122099260de942b2ba2c7ce1da1737f9', 'manual_location', '分拣/托盘入库口', '1002', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-03-20 16:55:23', '罗腾', '2020-08-04 15:46:21', '1');
INSERT INTO base_dict_item VALUES ('2', 'wh_type', '医疗仓库', 'yl', 2, 'NA', NULL, NULL, NULL, NULL, NULL, NULL, '2020-02-05 14:38:46', '管理员', '2020-04-07 13:24:52', '1');
INSERT INTO base_dict_item VALUES ('2005f0c0d6ff480fb00cffef4133a8b8', 'num_progress', '展示状态（1：展示  0：不展示）', '1', 1, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-09-24 15:13:49', '管理员', '2020-09-24 16:14:02', '1');
INSERT INTO base_dict_item VALUES ('4d68c841cc32441880fd8bb609009308', 'virtual_pallet_layer_num', '虚拟托盘层数', '0', 1, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-10-19 17:15:47', '宝佳管理员', '2020-11-14 18:33:09', '1');
INSERT INTO base_dict_item VALUES ('59251fabaa7d4acb9b394afae30ad276', 'order_in_type', '手工', 'order_in_sg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('60bdb53e19ac433cafcc75f304729bb2', 'order_in_type', '委外加工', 'order_in_wwjg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('67c27c007e504b459c8f4a331c776b42', 'area_attr', '低', 'low', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('7c6445cefb0d4d51a9b22a3f774d5429', 'goods_type', '器械', 'qx', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('8793c3f4954b4901b328e2f54caecc1d', 'order_type', '入库单', 'ORDER_IN', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('8c8fbc3a4a6d438ebf11bc4386d245aa', 'area_attr', '冷藏区', 'cold', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('8dec55122ac745b89729db518578216b', 'goods_type', '食品', 'sp', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('9b07579f4299460fbb74b8ca1f5520ed', 'supplier_type', '包材供应商', 'bc_supplier', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('a937df525ec34f12884752d899d47f1b', 'customer_type', '二级客户', 'two_customer', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('aad8e589f84e4ca3b72e3d6aa5e519cb', 'supplier_type', '原材料供应商', 'material_supplier', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('af715f79f0aa4d39b36d46f72bca1a28', 'order_type', '出库单', 'ORDER_OUT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('b610e7b3d12d4a949639443c4b6d3563', 'customer_type', '一级客户', 'one_customer', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('bf4ab0a739ba409ba26d6e45c11dbc58', 'area_attr', '普通区', 'normal', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('cea16c785b214f46a5c7867807987710', 'goods_type', '药物', 'yw', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('d782e1e5a4544bd583477f4c04a4ca18', 'area_attr', '高', 'high', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('d7f9c029724647caad0b54490c4fe3fd', 'jl_type', '米', 'mi_jl', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('dcb2809148a34ef38b0345a590818a97', 'order_in_type', '采购', 'order_in_cg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('df56c7c107eb4a96a3e0f7f17b5b6ccf', 'jl_type', 'ge', 'ge_jl', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_item VALUES ('f6cca42aab01417a84cc85c7ff2a2d69', 'goods_type', '保健品', 'bjp', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-03-20 16:55:23', NULL, NULL, '1');

-- ----------------------------
-- Table structure for base_dict_type
-- ----------------------------
CREATE TABLE base_dict_type  (
  dict_type_id varchar(64)  not NULL PRIMARY KEY,
  dic_type_name varchar(64)  COLLATE Chinese_PRC_CI_AS NULL,
  dic_type_code varchar(100)  COLLATE Chinese_PRC_CI_AS NULL,
  remark varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL ,
  sys_param char  DEFAULT '0',
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char NOT NULL DEFAULT '1' 
) 

-- ----------------------------
-- Records of base_dict_type
-- ----------------------------
INSERT INTO base_dict_type VALUES ('02b51c46772542aebec6ba2a617b7e9c', '虚拟托盘层数', 'virtual_pallet_layer_num', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-10-19 17:14:35', NULL, NULL, '1');
INSERT INTO base_dict_type VALUES ('1b8d2ad82f7a468fbbcdcb7f480831f1', '展示天数', 'man_page', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-10-14 10:52:38', NULL, NULL, '1');
INSERT INTO base_dict_type VALUES ('26f092d034984d5898d2fe6c78ddd975', '产品属性', 'goods_type', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_type VALUES ('3b298fd5454740a9831ee93c1f1de30a', '手工位置1', 'manual_location', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_type VALUES ('3e83ec0a0ce8461aa1dcbb1db3119269', '计量单位', 'jl_type', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_type VALUES ('571fc21a7f3b48a08b78745331e9e236', '供应商类型', 'supplier_type', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_type VALUES ('8277a36a118a4daf9ceeceaa59d282ed', '库区属性', 'area_attr', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_type VALUES ('831f14468a9b447fbffc2007fcad5a24', '客户类型', 'customer_type', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_type VALUES ('93cc8a6306074262bbad707fdb52980b', '发货LED数量进度', 'num_progress', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-09-24 15:13:02', NULL, NULL, '1');
INSERT INTO base_dict_type VALUES ('ad51cc82bd924b208b5827b66b52e9a9', '入库单据类型', 'order_in_type', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_type VALUES ('be6b34acc03240b7b6f06df8a3d246cc', '单据类型', 'order_type', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_type VALUES ('eb7e119adc954f51991509973023dad7', '出库单据类型', 'order_out_type', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '管理员', '2020-03-20 16:55:23', NULL, NULL, '1');
INSERT INTO base_dict_type VALUES ('ed607148379842baa56d33b78711144c', '仓库类型', 'wh_type', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-03-20 16:55:23', NULL, NULL, '1');

-- ----------------------------
-- Table structure for base_pda_version
-- ----------------------------
CREATE TABLE base_pda_version  (
  base_pda_version_id varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  code varchar(64)  NOT NULL  PRIMARY KEY,
  ip varchar(100)  COLLATE Chinese_PRC_CI_AS NULL ,
  port varchar(10)  COLLATE Chinese_PRC_CI_AS NULL  ,
  app_name varchar(60)  COLLATE Chinese_PRC_CI_AS NULL ,
  url varchar(200)  COLLATE Chinese_PRC_CI_AS NULL  ,
  version varchar(50)  COLLATE Chinese_PRC_CI_AS NULL  ,
  wgt_version varchar(50)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(100)  COLLATE Chinese_PRC_CI_AS NULL,
  user_defined2 varchar(100)  COLLATE Chinese_PRC_CI_AS NULL,
  user_defined3 varchar(100)  COLLATE Chinese_PRC_CI_AS NULL,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT NULL  ,
  active_flag char DEFAULT '1' ,
  remark varchar(255) DEFAULT NULL  
) 

-- ----------------------------
-- Records of base_pda_version
-- ----------------------------
INSERT INTO base_pda_version VALUES ('1001', '1001', '10.1.1.249', '80', 'pda', 'http://10.1.1.249/pda_1.0.0.apk', '1.0.0', '1.0.3', NULL, NULL, NULL, NULL, '2020-08-06 16:57:19', '35', '2020-09-23 16:34:41', '1', NULL);

-- ----------------------------
-- Table structure for base_unit
-- ----------------------------
CREATE TABLE base_unit  (
  unit_id varchar(64)  NOT NULL PRIMARY KEY,
  unit_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL,
  unit_zh_name varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  unit_en_name varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  unit_change varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  unit_type char(2)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1)  DEFAULT '1'  
) 
-- ----------------------------
-- Records of base_unit
-- ----------------------------
INSERT INTO base_unit VALUES ('1ee6a45129a14630ad34ac308a1bb05a', 'qianke', '千克', 'KG', '1000', '5', NULL, NULL, NULL, NULL, NULL, '井振瀚', '2020-03-05 21:23:01', '管理员', '2022-07-20 10:50:15', '1');
-- ----------------------------
-- Table structure for department
-- ----------------------------
CREATE TABLE department  (
  id int  NOT NULL  PRIMARY KEY,
  name varchar(50)  COLLATE Chinese_PRC_CI_AS NULL  ,
  code varchar(50)  COLLATE Chinese_PRC_CI_AS NULL  ,
  parent_id int DEFAULT NULL  ,
  sequence int DEFAULT NULL  ,
  type_id int DEFAULT NULL  ,
  description varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  
) 

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO department VALUES (85, '北京诺华制药有限公司', 'GSMC', 0, 1, 20, '客户的工厂');
INSERT INTO department VALUES (86, '综合管理部', 'BM_ZHGL', 85, 1, 11, '综合管理部');
INSERT INTO department VALUES (87, '人力资源部', 'BM_HR', 85, 2, 23, '人力资源部');
INSERT INTO department VALUES (88, '财务部', 'BM_CW', 85, 4, 19, '财务部');
INSERT INTO department VALUES (89, '市场部', 'BM_SC', 85, 3, 14, '市场部');
INSERT INTO department VALUES (90, '物流管理', 'BM_QA', 85, 5, 28, '物流管理');
INSERT INTO department VALUES (91, '后勤部', 'BM_HQ', 85, 7, 11, '后勤部');
INSERT INTO department VALUES (92, '后勤一部', 'hq_1', 91, 1, 20, '');

-- ----------------------------
-- Table structure for department_role
-- ----------------------------
CREATE TABLE department_role  (
  department_id int NOT NULL PRIMARY KEY,
  role_id int NOT NULL
) 
-- ----------------------------
-- Table structure for department_type
-- ----------------------------
CREATE TABLE department_type  (
  id int  NOT NULL PRIMARY KEY ,
  name varchar(50)  COLLATE Chinese_PRC_CI_AS NULL  
) 
-- ----------------------------
-- Records of department_type
-- ----------------------------
INSERT INTO department_type VALUES (11, '行政');
INSERT INTO department_type VALUES (14, '市场');
INSERT INTO department_type VALUES (16, '研发');
INSERT INTO department_type VALUES (19, '财务');
INSERT INTO department_type VALUES (20, '公司');
INSERT INTO department_type VALUES (22, '销售');
INSERT INTO department_type VALUES (23, '人力');
INSERT INTO department_type VALUES (28, '质量');
INSERT INTO department_type VALUES (30, 'CC');

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
CREATE TABLE login_log  (
  id int IDENTITY(1,1) NOT NULL ,
  user_id int NOT NULL ,
  login_time datetime NOT NULL  ,
  login_ip varchar(20)  COLLATE Chinese_PRC_CI_AS NULL
) 

-- ----------------------------
-- Table structure for operation_log
-- ----------------------------
CREATE TABLE operation_log  (
  id int NOT NULL PRIMARY KEY,
  user_id int NOT NULL,
  description varchar(200)  NOT NULL,
  operation_time datetime NOT NULL
) 
-- ----------------------------
-- Table structure for report
-- ----------------------------
CREATE TABLE report  (
  id int NOT NULL PRIMARY KEY,
  title varchar(50) NOT NULL,
  description varchar(100)  COLLATE Chinese_PRC_CI_AS NULL,
  url varchar(128)  COLLATE Chinese_PRC_CI_AS NULL,
  path varchar(128)  COLLATE Chinese_PRC_CI_AS NULL
)
-- ----------------------------
-- Records of report
-- ----------------------------
INSERT INTO report VALUES (10, '设备报表1', '描述，，，，', '/ReportServer?formlet=file', 'G:/tmp/PhReport8/WEB-INF/reportlets/设备报表1.frm');
INSERT INTO report VALUES (11, 'test-zhangx-1', '描述，，，，', '/ReportServer?formlet=test-zhangx-1.frm', 'G:/tmp/PhReport8/WEB-INF/reportlets/test-zhangx-1.frm');
INSERT INTO report VALUES (12, 'test-zhangx-2', '描述，，，，', 'ReportServer?formlet=test-zhangx-2.frm', 'G:/tmp/PhReport8/WEB-INF/reportlets/test-zhangx-2.frm');
INSERT INTO report VALUES (13, 'device', '123', 'ReportServer?formlet=device.frm', 'E:\\ideaWorkSpace\\phReportWEB-INF/reportlets/device.frm');
INSERT INTO report VALUES (14, 'breakRule', '123', 'ReportServer?formlet=breakRule.frm', 'E:\\ideaWorkSpace\\phReportWEB-INF/reportlets/breakRule.frm');
INSERT INTO report VALUES (16, '财务', '', 'ReportServer?formlet=财务.frm', 'H:/PhReport8/WEB-INF/reportlets/财务.frm');
-- ----------------------------
-- Table structure for resources
-- ----------------------------
CREATE TABLE resources  (
    id int identity(1,1) NOT NULL,
    parent_id int    NOT NULL,
    level int NOT NULL  ,
    sequence int NOT NULL  ,
    title varchar(50)  COLLATE Chinese_PRC_CI_AS NULL,
    res_code varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
    description varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
    icon varchar(100) DEFAULT NULL  ,
    is_modifiable tinyint NOT NULL DEFAULT 0,
    res_type char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
    url varchar(128)  COLLATE Chinese_PRC_CI_AS NULL
)
-- ----------------------------
-- Records of resources
-- ----------------------------
SET IDENTITY_INSERT resources ON;
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (1, 0, 1, 1, '组织管理', '0', '', '/framework/images/menuIcon/icon-26.png', 0, '0', NULL);
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (2, 1, 2, 1, '部门管理', 'department', '', '', 0, '0', 'module/basicInformation/manageDepartment.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (3, 1, 2, 2, '岗位管理', 'post', '', '', 0, '0', 'module/basicInformation/postManagement.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (4, 1, 2, 3, '员工管理', 'user', '', '', 0, '0', 'module/basicInformation/managePerson.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (12, 0, 1, 2, '权限管理', '0', '', '/framework/images/menuIcon/icon-38.png', 0, '0', NULL);
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (13, 12, 2, 1, '资源管理', 'resource', '', NULL, 0, '0', 'module/privilegeManagement/resourceManagement.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (14, 12, 2, 2, '角色管理', 'role', '', NULL, 0, '0', 'module/privilegeManagement/roleManagement.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (15, 0, 1, 5, '报表管理', '0', '', '/framework/images/menuIcon/icon-15.png', 0, '0', NULL);
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (16, 15, 2, 1, '报表管理', 'report', '', NULL, 0, '0', 'module/reportsManagement/reportsManagement.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (17, 0, 1, 6, '系统日志', '0', '', '/framework/images/menuIcon/icon-05.png', 0, '0', NULL);
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (18, 17, 2, 1, '登录日志', 'loginLog', '', NULL, 0, '0', 'module/logManagement/loginLog.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (19, 17, 2, 2, '操作日志', 'operateLog', '', NULL, 0, '0', 'module/logManagement/operationLog.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (28, 4, 3, 1, '用户查询', 'user:search', NULL, NULL, 0, '1', 'userManagement/getCurrentUser');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (71, 4, 3, 2, '用户停用', 'user:disenable', NULL, NULL, 0, '1', 'userManagement/disableUser');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (74, 4, 3, 3, '用户编辑', 'user:edit', NULL, NULL, 0, '1', 'userManagement/updateUserInformation');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (75, 4, 3, 4, '用户删除', 'user:delete', NULL, NULL, 0, '1', 'userManagement/deleteUser');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (76, 4, 3, 5, '用户新增', 'user:add', NULL, NULL, 0, '1', 'userManagement/addUser');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (77, 4, 3, 6, '重置密码', 'user:resetPass', NULL, NULL, 0, '1', 'userManagement/resetPassword');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (78, 4, 3, 7, '用户启用', 'user:enable', NULL, NULL, 0, '1', 'userManagement/enable');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (80, 0, 0, 7, '基础数据', '', '', '/framework/images/menuIcon/icon-11.png', 1, '0', '');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (82, 0, 0, 15, '手持功能', '', '', '/wms/framework/images/menuIcon/device.png', 1, '4', '');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (91, 80, 1, 4, '客户管理', '', '', ' ', 1, '0', '/wms/wmsfront/module/wmsbasicmanagement/wmsCustomerList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (92, 80, 1, 3, '商品管理', '', '', '', 1, '0', '/wms/wmsfront/module/nuohua/wmsNHGoodsList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (93, 80, 1, 1, '仓库管理', '', '', ' ', 1, '0', '/wms/wmsfront/module/wmsbasicmanagement/wmsWarehouseList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (94, 80, 1, 5, '供应商管理', '', '', ' ', 1, '0', '/wms/wmsfront/module/wmsbasicmanagement/wmsSupplierList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (95, 80, 1, 2, '库区管理', '', '', ' ', 1, '0', '/wms/wmsfront/module/wmsbasicmanagement/wmsWarehouseAreaList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (100, 0, 0, 9, '运行数据', '', '', '/framework/images/menuIcon/icon-12.png', 1, '0', '');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (101, 100, 1, 0, '立体库库位管理', '', '', '', 1, '0', '/wms/wmsfront/module/nuohua/wmsNHLocationStereoscopicList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (102, 0, 0, 10, '库存管理', '', '', '/framework/images/menuIcon/icon-09.png', 1, '0', '');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (103, 102, 1, 1, '收货未上架信息', '', '', ' ', 1, '0', '/wms/wmsfront/module/storagemanagement/wmsPalletUnShelevsList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (104, 102, 1, 2, '平库库存信息', '', '', ' ', 1, '0', '/wms/wmsfront/module/storagemanagement/wmsStoragePlaneList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (105, 102, 1, 0, '收货暂存信息', '', '', ' ', 1, '0', '/wms/wmsfront/module/storagemanagement/wmsInTemporaryList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (106, 102, 1, 4, '发货暂存信息', '', '', ' ', 1, '0', '/wms/wmsfront/module/storagemanagement/wmsOutTemporaryList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (107, 102, 1, 3, '立库库存信息', '', '', ' ', 1, '0', '/wms/wmsfront/module/storagemanagement/wmsPalletStereoscopicList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (108, 100, 1, 2, '托盘管理', '', '', ' ', 1, '0', '/wms/wmsfront/module/storagemanagement/wmsPalletList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (109, 100, 1, 3, '月台管理', '', '', ' ', 1, '0', '/wms/wmsfront/module/wmsbasicmanagement/wmsPlatformList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (110, 0, 0, 11, '入库管理', '', '', '/framework/images/menuIcon/icon-29.png', 1, '0', '');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (111, 110, 1, 0, '收货任务', '', '', ' ', 1, '0', '/wms/wmsfront/module/inboundmanagement/orderInTask.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (112, 0, 0, 14, '业务日志', '', '', '/framework/images/menuIcon/icon-33.png', 1, '0', '');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (113, 112, 1, 0, '任务执行日志', '', '', ' ', 1, '0', '/wms/wmsfront/module/logmanagement/wmsTaskExecutionLogList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (115, 112, 1, 2, '移库日志', '', '', ' ', 1, '0', '/wms/wmsfront/module/logmanagement/wmsMoveLogList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (117, 112, 1, 3, '发货日志', '', '', ' ', 1, '0', '/wms/wmsfront/module/logmanagement/wmsSendLogList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (118, 82, 1, 1, '订单收货', 'order-in', '收货到入库暂存区', '/static/menu/order-in.png', 1, '4', '../mould/temporary-area/order-in/order-in');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (119, 82, 1, 2, '商品绑定', 'goods-bind', '商品绑定', '/static/menu/goods-bind.png ', 1, '4', '../mould/temporary-area/goods-bind/goods-bind');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (120, 82, 1, 11, '平库入库', 'plane-in', '平库入库', '/static/menu/plane-in.png', 1, '4', '../mould/plane/plane-in/plane-in');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (121, 82, 1, 21, '手动入立库', 'stereoscopic-hand-in', '异常口/特殊分拣口', '/static/menu/stereoscopic-hand-in.png', 1, '4', '../mould/stereoscopic/stereoscopic-hand-in/stereoscopic-hand-in');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (122, 82, 1, 22, '托盘手动入库', 'stereoscopic-pallet-hand-in', '托盘口', '/static/menu/stereoscopic-pallet-hand-in.png', 1, '4', '../mould/stereoscopic/pallet-hand-in-qxf/pallet-hand-in-qxf');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (123, 82, 1, 24, '立库分拣', 'stereoscopic-select-out', '立库分拣', '/static/menu/stereoscopic-select-out.png', 1, '4', '../mould/stereoscopic/stereoscopic-out/stereoscopic-out');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (124, 82, 1, 14, '平库分拣', 'plane-select-out', '平库分拣', '/static/menu/plane-select-out.png', 1, '4', '../mould/plane/plane-out/plane-out');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (125, 82, 1, 4, '装车复核', 'car-confirm', '出库暂存区', '/static/menu/car-confirm.png', 1, '4', '../mould/temporary-area/car-confirm/car-confirm');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (126, 82, 1, 23, '立库盘点', 'stereoscopic-check', '立库盘点', '/static/menu/stereoscopic-check.png', 1, '4', '../mould/stereoscopic/stereoscopic-check/stereoscopic-check');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (127, 82, 1, 12, '平库盘点', 'plane-check', '平库盘点', '/static/menu/plane-check.png', 1, '4', '../mould/plane/plane-check/plane-check');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (128, 82, 1, 13, '平库移库', 'plane-move', '平库移库', '/static/menu/plane-move.png', 1, '4', '../mould/plane/plane-move/plane-move');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (129, 82, 1, 10, '平库合托', 'plane-pallet-combine', '平库合托', '/static/menu/plane-pallet-combine.png', 1, '4', '../mould/plane/plane-pallet-combine/plane-pallet-combine');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (130, 82, 1, 3, '收货越库', 'order-cross', '收货越库', '/static/menu/order-cross.png', 1, '4', '../mould/temporary-area/order-cross/order-cross');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (131, 112, 1, 6, '托盘合并日志', '', '', ' ', 1, '0', '/wms/wmsfront/module/logmanagement/wmsCombineLogList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (132, 0, 0, 12, '出库管理', '', '', '/framework/images/menuIcon/icon-30.png', 1, '0', '');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (133, 132, 1, 0, '立库出库任务', '', '', '', 1, '0', '/wms/wmsfront/module/nuohua/wmsNHOrderOutStereoscopicList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (134, 132, 1, 2, '平库出库任务', '', '', ' ', 1, '0', '/wms/wmsfront/module/outboundmanagement/wmsOrderOutPlaneList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (135, 132, 1, 3, '立库托盘出库', '', '', ' ', 1, '0', '/wms/wmsfront/module/outboundmanagement/wmsPalletOutStereoscopicList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (136, 0, 0, 13, '库内管理', '', '', '/framework/images/menuIcon/icon-41.png', 1, '0', '');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (137, 136, 1, 0, '盘点任务', '', '', '', 1, '0', '/wms/wmsfront/module/nuohua/wmsNHOrderCheckList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (139, 136, 1, 1, '立库移库', '', '', '', 1, '0', '/wms/wmsfront/module/nuohua/wmsNHMoveStereoscopicList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (140, 132, 1, 6, '收货越库单', '', '', ' ', 1, '0', '/wms/wmsfront/module/outboundmanagement/wmsOrderCrossInList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (141, 132, 1, 7, '生产越库单', '', '', ' ', 1, '0', '/wms/wmsfront/module/outboundmanagement/wmsOrderCrossProductList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (142, 80, 1, 7, '计量单位', '', '', ' ', 1, '0', 'module/basicInformation/baseUnit.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (143, 80, 1, 6, '数据字典', '', '', ' ', 1, '0', 'module/basicInformation/manageDictionary.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (144, 100, 1, 6, '地址关系维护', '', '', ' ', 1, '0', '/wms/wmsfront/module/wmsbasicmanagement/wmsAddressTransformList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (145, 100, 1, 7, '平库库位管理', '', '', ' ', 1, '0', '/wms/wmsfront/module/wmsbasicmanagement/wmsPlaneLocationList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (147, 100, 1, 8, '立库库位视图', '', '', '', 1, '0', '/wms/wmsfront/module/nuohua/NHStereoscopicLocationView.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (148, 102, 1, 8, '进出周转汇总', '', '', '', 1, '0', '/wms/wmsfront/module/storagemanagement/wmsTurnoverList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (150, 80, 1, 11, 'PDA版本管理', '', '', '', 1, '0', '/wms/wmsfront/module/wmsbasicmanagement/basePdaVersionList.html');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (151, 82, 1, 20, '异常入立库', 'stereoscopic-error-hand-in', '异常口入立库', '/static/menu/stereoscopic-hand-in.png', 1, '4', '../mould/stereoscopic/stereoscopic-error-hand-in/stereoscopic-error-hand-in');
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (N'152', N'82', N'1', N'25', N'托盘解绑', N'pallet-remove', N'立库托盘移除', N'/static/menu/stereoscopic-select-out.png', N'1', N'4', N'../mould/temporary-area/goods-unbind/goods-unbind')
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (N'158', N'132', N'1', N'4', N'成品出库任务', N'', N'成品出库', N'', N'1', N'0', N'/wms/wmsfront/module/nuohua/wmsNHCPOrderOutStereoscopicList.html')
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (N'159', N'132', N'1', N'5', N'原材料出库任务', N'', N'原材料', N'', N'1', N'0', N'/wms/wmsfront/module/nuohua/wmsNHYCLOrderOutStereoscopicList.html')
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (N'156', N'80', N'1', N'15', N'出入库策略管理', N'', N'出入库策略管理', N'', N'1', N'0', N'/wms/wmsfront/module/nuohua/wmsNHTacticsList.html')
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (N'160', N'82', N'1', N'16', N'产成品入库', N'stereoscopic-hand-in-product', N'产品入库', N'/static/menu/order-cross.png', N'1', N'4', N'../mould/stereoscopic/stereoscopic-hand-in/stereoscopic-hand-in-product')
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (N'161', N'82', N'1', N'17', N'原料入库', N'stereoscopic-hand-in-material', N'原料入库', N'/static/menu/mould-ruku.png', N'1', N'4', N'../mould/stereoscopic/stereoscopic-hand-in/stereoscopic-hand-in-material')
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (N'162', N'82', N'1', N'18', N'原料出库', N'stereoscopic-hand-out-material', N'原料出库', N'/static/menu/mould-chuku.png', N'1', N'4', N'../mould/stereoscopic/stereoscopic-out/stereoscopic-hand-out-material')
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (N'163', N'82', N'1', N'19', N'产成品出库', N'stereoscopic-hand-out-product', N'产成品出库', N'/static/menu/car-confirm.png', N'1', N'4', N'../mould/stereoscopic/stereoscopic-out/stereoscopic-hand-out-product')
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (N'164', N'110', N'1', N'2', N'成品收货任务', N'', N'成品收货任务', N'', N'1', N'0', N'/wms/wmsfront/module/inboundmanagement/orderCPInTask.html')
INSERT INTO resources  (id, parent_id, level, sequence, title, res_code, description, icon, is_modifiable,res_type, url)  VALUES (N'165', N'110', N'1', N'3', N'原材料收货任务', N'', N'原材料收货任务', N'', N'1', N'0', N'/wms/wmsfront/module/inboundmanagement/orderYCLInTask.html')

SET IDENTITY_INSERT resources OFF;
-- ----------------------------
-- Table structure for role
-- ----------------------------
CREATE TABLE role  (
   id int identity(1,1) NOT NULL PRIMARY KEY,
   name varchar(50) NOT NULL,
   description varchar(100)  COLLATE Chinese_PRC_CI_AS NULL,
   created_time smalldatetime NOT NULL  DEFAULT (GETDATE())
)
    -- ----------------------------
-- Records of role
-- ----------------------------
SET IDENTITY_INSERT role ON
INSERT INTO role (id,name,description,created_time) VALUES (1, 'administrator', '超级管理员', '2017-09-19 15:13:29');
INSERT INTO role (id,name,description,created_time) VALUES (49, 'pda_user', '产线手持用户', '2020-09-14 15:26:13');
INSERT INTO role (id,name,description,created_time) VALUES (50, 'pda_user_warehouse', '仓库手持管理员', '2020-09-23 10:44:07');
INSERT INTO role (id,name,description,created_time) VALUES (51, 'wh_admin', 'PC仓库管理员', '2020-09-23 10:58:25');
INSERT INTO role (id,name,description,created_time) VALUES (52, 'bj_admin', '管理员', '2020-10-28 10:09:08');
SET IDENTITY_INSERT role OFF
-- ----------------------------
-- Table structure for role_res
-- ----------------------------
CREATE TABLE role_res  (
  role_id int NOT NULL ,
  res_id int NOT NULL 
)
-- ----------------------------
-- Records of role_res
-- ----------------------------
INSERT INTO role_res VALUES (1, 1);
INSERT INTO role_res VALUES (1, 2);
INSERT INTO role_res VALUES (1, 3);
INSERT INTO role_res VALUES (1, 4);
INSERT INTO role_res VALUES (1, 12);
INSERT INTO role_res VALUES (1, 13);
INSERT INTO role_res VALUES (1, 14);
INSERT INTO role_res VALUES (1, 15);
INSERT INTO role_res VALUES (1, 16);
INSERT INTO role_res VALUES (1, 17);
INSERT INTO role_res VALUES (1, 18);
INSERT INTO role_res VALUES (1, 19);
INSERT INTO role_res VALUES (1, 28);
INSERT INTO role_res VALUES (1, 71);
INSERT INTO role_res VALUES (1, 74);
INSERT INTO role_res VALUES (1, 75);
INSERT INTO role_res VALUES (1, 76);
INSERT INTO role_res VALUES (1, 77);
INSERT INTO role_res VALUES (1, 78);
INSERT INTO role_res VALUES (1, 80);
INSERT INTO role_res VALUES (1, 82);
INSERT INTO role_res VALUES (1, 91);
INSERT INTO role_res VALUES (1, 92);
INSERT INTO role_res VALUES (1, 93);
INSERT INTO role_res VALUES (1, 94);
INSERT INTO role_res VALUES (1, 95);
INSERT INTO role_res VALUES (1, 100);
INSERT INTO role_res VALUES (1, 101);
INSERT INTO role_res VALUES (1, 102);
INSERT INTO role_res VALUES (1, 103);
INSERT INTO role_res VALUES (1, 105);
INSERT INTO role_res VALUES (1, 106);
INSERT INTO role_res VALUES (1, 107);
INSERT INTO role_res VALUES (1, 108);
INSERT INTO role_res VALUES (1, 109);
INSERT INTO role_res VALUES (1, 110);
INSERT INTO role_res VALUES (1, 111);
INSERT INTO role_res VALUES (1, 112);
INSERT INTO role_res VALUES (1, 113);
INSERT INTO role_res VALUES (1, 115);
INSERT INTO role_res VALUES (1, 117);
INSERT INTO role_res VALUES (1, 118);
INSERT INTO role_res VALUES (1, 119);
INSERT INTO role_res VALUES (1, 121);
INSERT INTO role_res VALUES (1, 125);
INSERT INTO role_res VALUES (1, 126);
INSERT INTO role_res VALUES (1, 129);
INSERT INTO role_res VALUES (1, 130);
INSERT INTO role_res VALUES (1, 132);
INSERT INTO role_res VALUES (1, 133);
INSERT INTO role_res VALUES (1, 136);
INSERT INTO role_res VALUES (1, 137);
INSERT INTO role_res VALUES (1, 139);
INSERT INTO role_res VALUES (1, 142);
INSERT INTO role_res VALUES (1, 143);
INSERT INTO role_res VALUES (1, 144);
INSERT INTO role_res VALUES (1, 147);
INSERT INTO role_res VALUES (1, 148);
INSERT INTO role_res VALUES (1, 150);
INSERT INTO role_res VALUES (1, 152);
INSERT INTO role_res VALUES (1, 156);
INSERT INTO role_res VALUES (1, 158);
INSERT INTO role_res VALUES (1, 159);
INSERT INTO role_res VALUES (1, 160);
INSERT INTO role_res VALUES (1, 161);
INSERT INTO role_res VALUES (1, 162);
INSERT INTO role_res VALUES (1, 163);
INSERT INTO role_res VALUES (1, 164);
INSERT INTO role_res VALUES (1, 165);
INSERT INTO role_res VALUES (49, 15);
INSERT INTO role_res VALUES (49, 16);
INSERT INTO role_res VALUES (49, 82);
INSERT INTO role_res VALUES (49, 151);
INSERT INTO role_res VALUES (49, 152);
INSERT INTO role_res VALUES (50, 15);
INSERT INTO role_res VALUES (50, 16);
INSERT INTO role_res VALUES (50, 82);
INSERT INTO role_res VALUES (50, 119);
INSERT INTO role_res VALUES (50, 121);
INSERT INTO role_res VALUES (50, 122);
INSERT INTO role_res VALUES (50, 125);
INSERT INTO role_res VALUES (51, 15);
INSERT INTO role_res VALUES (51, 16);
INSERT INTO role_res VALUES (51, 80);
INSERT INTO role_res VALUES (51, 92);
INSERT INTO role_res VALUES (51, 100);
INSERT INTO role_res VALUES (51, 101);
INSERT INTO role_res VALUES (51, 102);
INSERT INTO role_res VALUES (51, 107);
INSERT INTO role_res VALUES (51, 108);
INSERT INTO role_res VALUES (51, 112);
INSERT INTO role_res VALUES (51, 115);
INSERT INTO role_res VALUES (51, 117);
INSERT INTO role_res VALUES (51, 132);
INSERT INTO role_res VALUES (51, 133);
INSERT INTO role_res VALUES (51, 135);
INSERT INTO role_res VALUES (51, 136);
INSERT INTO role_res VALUES (51, 139);
INSERT INTO role_res VALUES (51, 147);
INSERT INTO role_res VALUES (51, 148);
INSERT INTO role_res VALUES (52, 15);
INSERT INTO role_res VALUES (52, 16);
INSERT INTO role_res VALUES (52, 17);
INSERT INTO role_res VALUES (52, 18);
INSERT INTO role_res VALUES (52, 80);
INSERT INTO role_res VALUES (52, 92);
INSERT INTO role_res VALUES (52, 93);
INSERT INTO role_res VALUES (52, 95);
INSERT INTO role_res VALUES (52, 100);
INSERT INTO role_res VALUES (52, 101);
INSERT INTO role_res VALUES (52, 102);
INSERT INTO role_res VALUES (52, 107);
INSERT INTO role_res VALUES (52, 108);
INSERT INTO role_res VALUES (52, 112);
INSERT INTO role_res VALUES (52, 113);
INSERT INTO role_res VALUES (52, 115);
INSERT INTO role_res VALUES (52, 132);
INSERT INTO role_res VALUES (52, 133);
INSERT INTO role_res VALUES (52, 135);
INSERT INTO role_res VALUES (52, 136);
INSERT INTO role_res VALUES (52, 137);
INSERT INTO role_res VALUES (52, 139);
INSERT INTO role_res VALUES (52, 143);
INSERT INTO role_res VALUES (52, 147);
INSERT INTO role_res VALUES (52, 148);
INSERT INTO role_res VALUES (52, 150);
-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE [user]  (
  id int identity(1,1) NOT NULL PRIMARY KEY,
  account varchar(50)  COLLATE Chinese_PRC_CI_AS NULL,
  password varchar(50)  COLLATE Chinese_PRC_CI_AS NULL,
  nickname varchar(50)  COLLATE Chinese_PRC_CI_AS NULL,
  office_phone varchar(50)  COLLATE Chinese_PRC_CI_AS NULL  ,
  phone varchar(11)  COLLATE Chinese_PRC_CI_AS NULL  ,
  email varchar(50)  COLLATE Chinese_PRC_CI_AS NULL,
  status tinyint NOT NULL DEFAULT 0  ,
  user_type char(1)  DEFAULT '0'  ,
  entry_date datetime DEFAULT (GETDATE()) ,
  description varchar(255)  COLLATE Chinese_PRC_CI_AS NULL 
) 
-- ----------------------------
-- Records of user
-- ----------------------------
SET IDENTITY_INSERT [user] ON
INSERT INTO [user] (id,account,password,nickname,office_phone,phone,email,status,user_type,entry_date,description) VALUES (35, '35', '12345678', '管理员', '1111111111111', '15865878454', '444@ww.com', 0, '0', '2021-03-31 18:36:26', '5556');
INSERT INTO [user] (id,account,password,nickname,office_phone,phone,email,status,user_type,entry_date,description) VALUES (36, 'admin', '12345678', '管理员', '1111111111111', '15865878454', '444@ww.com', 0, '0', '2021-03-31 18:36:26', '5556');
INSERT INTO [user] (id,account,password,nickname,office_phone,phone,email,status,user_type,entry_date,description) VALUES (122, '2001', '12345678', '产线管理员', '', '', '', 0, '0', '2020-09-14 00:00:00', '');
INSERT INTO [user] (id,account,password,nickname,office_phone,phone,email,status,user_type,entry_date,description) VALUES (123, '3001', '12345678', '仓库管理员', '', '', '', 0, '0', '2020-09-23 00:00:00', '');
INSERT INTO [user] (id,account,password,nickname,office_phone,phone,email,status,user_type,entry_date,description) VALUES (124, 'wh001', '12345678', '仓库', '', '', '', 0, '0', '2020-09-23 00:00:00', '');
INSERT INTO [user] (id,account,password,nickname,office_phone,phone,email,status,user_type,entry_date,description) VALUES (125, 'bj_admin', '12345678', '宝佳管理员', '', '', '', 0, '0', '2020-10-28 00:00:00', '');
SET IDENTITY_INSERT [user] OFF
-- ----------------------------
-- Table structure for user_department
-- ----------------------------
CREATE TABLE user_department  (
  user_id int NOT NULL,
  department_id int NOT NULL
) 
-- ----------------------------
-- Records of user_department
-- ----------------------------
INSERT INTO user_department VALUES (35, 90);
INSERT INTO user_department VALUES (36, 90);
INSERT INTO user_department VALUES (122, 90);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
CREATE TABLE user_role  (
  user_id varchar(20) NOT NULL,
  role_id int NOT NULL
)
-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO user_role VALUES ('35', 1);
INSERT INTO user_role VALUES ('36', 1);
INSERT INTO user_role VALUES ('122', 49);
INSERT INTO user_role VALUES ('123', 50);
INSERT INTO user_role VALUES ('124', 51);
INSERT INTO user_role VALUES ('125', 52);

-- ----------------------------
-- Table structure for wms_address_real_rela
-- ----------------------------
CREATE TABLE wms_address_real_rela  (
    id int  identity(1,1) NOT NULL,
    address_code varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
    address_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
    real_address int DEFAULT NULL  ,
    remark varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
    user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
    user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
    user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
    user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
    user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
    create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
    gmt_create datetime DEFAULT (GETDATE())  ,
    last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
    gmt_modified datetime DEFAULT (GETDATE())  ,
    active_flag char(1) DEFAULT '1'
)

-- ----------------------------
-- Records of wms_address_real_rela
-- ----------------------------
SET IDENTITY_INSERT wms_address_real_rela ON
INSERT INTO wms_address_real_rela (id,address_code,address_name,real_address,remark,user_defined1,user_defined2,user_defined3,user_defined4,user_defined5,create_by,gmt_create,last_modified_by,gmt_modified,active_flag) VALUES (1, '2001', '立库出库口1', 2001, '', '10.1.1.241', '5005', NULL, NULL, NULL, '诺华', '2022-08-01 13:47:31', '管理员', '2022-08-01 13:26:22', '1');
SET IDENTITY_INSERT wms_address_real_rela OFF
-- ----------------------------
-- Table structure for wms_address_transform
-- ----------------------------
CREATE TABLE wms_address_transform  (
    transform_id int   identity(71,1) NOT NULL,
    address_type char(2)  COLLATE Chinese_PRC_CI_AS NULL  ,
    rela_id int DEFAULT NULL
)

-- ----------------------------
-- Records of wms_address_transform
-- ----------------------------
SET IDENTITY_INSERT wms_address_transform ON
INSERT INTO wms_address_transform (transform_id,address_type,rela_id) VALUES (62, '16', 28);
INSERT INTO wms_address_transform (transform_id,address_type,rela_id) VALUES (68, '20', 27);
INSERT INTO wms_address_transform (transform_id,address_type,rela_id) VALUES (69, '20', 26);
INSERT INTO wms_address_transform (transform_id,address_type,rela_id) VALUES (70, '20', 25);
SET IDENTITY_INSERT wms_address_real_rela OFF

-- ----------------------------
-- Table structure for wms_box_barcode
-- ----------------------------
CREATE TABLE wms_box_barcode  (
  pallet_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL,
  box_barcode varchar(32)  COLLATE Chinese_PRC_CI_AS NULL,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1' 
) 

-- ----------------------------
-- Table structure for wms_combine_log
-- ----------------------------
CREATE TABLE wms_combine_log  (
  combine_log_id varchar(64) NOT NULL PRIMARY KEY,
  combine_task_id varchar(64) NOT NULL,
  from_pallet_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  to_pallet_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  from_amount int DEFAULT NULL  ,
  batch_no varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  warehouse_code varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  area_code varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  from_location_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  to_location_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  move_result char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  error_msg varchar(512)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
) 

-- ----------------------------
-- Table structure for wms_customer
-- ----------------------------
CREATE TABLE wms_customer  (
  customer_id varchar(64)  COLLATE Chinese_PRC_CI_AS NULL,
  customer_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  customer_full_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  customer_short_name varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  customer_eng_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  customer_type varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  customer_level varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  first_contract_date datetime DEFAULT NULL  ,
  end_contract_date datetime DEFAULT NULL  ,
  address varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  contacts varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  website varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  registration_date datetime DEFAULT NULL  ,
  remark varchar(128)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
) 

-- ----------------------------
-- Table structure for wms_goods
-- ----------------------------
CREATE TABLE wms_goods  (
  goods_id varchar(64) NOT NULL,
  owner varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  owner_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_desc varchar(300)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_attr varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_type char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  unit varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  has_box_code char(1) DEFAULT '0'  ,
  layer_quantity int DEFAULT 0  ,
  box_length int DEFAULT NULL  ,
  box_width int DEFAULT NULL  ,
  box_height int DEFAULT NULL  ,
  quality_guarantee_days int DEFAULT 0  ,
  split_unit varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  split_count int DEFAULT NULL  ,
  gross_weight int DEFAULT NULL  ,
  user_defined1 varchar(256)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1' 
) 

-- ----------------------------
-- Records of wms_goods
-- ----------------------------
INSERT INTO wms_goods VALUES ('0415178e0ff44cc3aeca8b2c876962d1', NULL, NULL, '成品', '成品', '899030', NULL, NULL, 'KG', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2022-09-21 15:48:18', NULL, NULL, '1');
INSERT INTO wms_goods VALUES ('0415178e0ff44cc3aeca8b2c87696202', NULL, NULL, '原材料', '原材料', '1001', NULL, NULL, 'KG', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2022-09-21 15:48:18', NULL, NULL, '1');

-- ----------------------------
-- Table structure for wms_in_temporary
-- ----------------------------
CREATE TABLE wms_in_temporary  (
  id varchar(64) NOT NULL,
  goods_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  amount int DEFAULT NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
) 

-- ----------------------------
-- Table structure for wms_in_out_tactics
-- ----------------------------
CREATE TABLE wms_in_out_tactics  (
  id int NOT NULL  identity(1,1) ,
  tactics_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  tactics_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
) 
-- ----------------------------
-- Table structure for wms_interface_call_log
-- ----------------------------
CREATE TABLE wms_interface_call_log  (
  id int NOT NULL  identity(1,1) ,
  interface_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  status char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  error_msg varchar(512)  COLLATE Chinese_PRC_CI_AS NULL  ,
  elapsed_time int NOT NULL ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  gmt_end datetime DEFAULT (GETDATE()) 
) 

-- ----------------------------
-- Table structure for wms_location_stereoscopic
-- ----------------------------
CREATE TABLE wms_location_stereoscopic  (
  location_id varchar(64) NOT NULL DEFAULT '0',
  warehouse_code varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  area_code varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  location_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  location_attr varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  location_desc varchar(300)  COLLATE Chinese_PRC_CI_AS NULL  ,
  use_status char(1) DEFAULT '0'  ,
  pallet_code varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  allow_mix char(1) DEFAULT '0'  ,
  in_seq int DEFAULT NULL  ,
  out_seq int DEFAULT NULL  ,
  floor_number int DEFAULT NULL  ,
  shelves_number int DEFAULT NULL  ,
  layer_number int DEFAULT NULL  ,
  column_number int DEFAULT NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
) 
-- ----------------------------
-- Records of wms_location_stereoscopic
-- ----------------------------
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ba3db742f4aa4d73bed1f3871562168e', N'NH_WAREHOUSE', N'L-NH01', N'020401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'2', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2f2527c7ba924446b7ea3091948d9bd9', N'NH_WAREHOUSE', N'L-NH01', N'020501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'2', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ad2ac5ec80714df0a948faaecf063496', N'NH_WAREHOUSE', N'L-NH01', N'020601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'2', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'fb4687a4434d4d4daf5a950933215c89', N'NH_WAREHOUSE', N'L-NH01', N'030401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'3', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'3a386c5fa92247c5b9950d0ca353b9c3', N'NH_WAREHOUSE', N'L-NH01', N'030501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'3', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'44f43883a74a4d4cbc895b7b4d21edb7', N'NH_WAREHOUSE', N'L-NH01', N'030601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'3', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'bd6f384b847843fe834ae164cdab83a7', N'NH_WAREHOUSE', N'L-NH01', N'040401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'4', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8713ad4be7b34bc38ff779e34eafde07', N'NH_WAREHOUSE', N'L-NH01', N'040501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'4', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'743f99cec78648e4854b71daf73c23e1', N'NH_WAREHOUSE', N'L-NH01', N'040601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'4', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'30daa15f0a8045f0b5889ac0d0610eeb', N'NH_WAREHOUSE', N'L-NH01', N'050401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'5', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'49407fa0e77d41fe8fb75374fbc17b2b', N'NH_WAREHOUSE', N'L-NH01', N'050501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'5', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'c75c7c33eb3a48cbbd25c478eed0af87', N'NH_WAREHOUSE', N'L-NH01', N'050601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'5', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e6306418ad084d56ba0482da00e882ab', N'NH_WAREHOUSE', N'L-NH01', N'060401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'6', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e2e2d6cceaa54661a3b5cab8acad45a3', N'NH_WAREHOUSE', N'L-NH01', N'060501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'6', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'6ebf8e6630f0484abbba2a1dbdd4e248', N'NH_WAREHOUSE', N'L-NH01', N'060601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'6', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'31517c836666427ca96835165860b326', N'NH_WAREHOUSE', N'L-NH01', N'070101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'7', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b660364502d241a49e94e432c53eafbb', N'NH_WAREHOUSE', N'L-NH01', N'070301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'7', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8c6c7713280e4a1ab5ec3bc1a019e10c', N'NH_WAREHOUSE', N'L-NH01', N'070401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'7', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'90e2d0c5c7514ab4803be875cf2e0dd4', N'NH_WAREHOUSE', N'L-NH01', N'070501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'7', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f699d7f340e24132b0d7db6558f0ee03', N'NH_WAREHOUSE', N'L-NH01', N'070601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'7', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'7d435130c605445f9529ba171d05efd2', N'NH_WAREHOUSE', N'L-NH01', N'080101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'8', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a72db2cb446e4758a99a69158b00adfa', N'NH_WAREHOUSE', N'L-NH01', N'080301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'8', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'00d253c9a46c413ab3fec3128760b64c', N'NH_WAREHOUSE', N'L-NH01', N'080401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'8', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a702b0b523874baa9af01e19c1188e72', N'NH_WAREHOUSE', N'L-NH01', N'080501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'8', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'6da2bf0a6f3e4eacbb14a4989d108e6e', N'NH_WAREHOUSE', N'L-NH01', N'080601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'8', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f7713ff0b4d741d58e04dfaab39b2429', N'NH_WAREHOUSE', N'L-NH01', N'090101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'9', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'79f605c1e8bf421db1e1731aeff8bee7', N'NH_WAREHOUSE', N'L-NH01', N'090301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'9', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'5b6783ff38884f6b867dad2978d8265a', N'NH_WAREHOUSE', N'L-NH01', N'090401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'9', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'7040d77ff14849b59c28f3ea29d2e4d5', N'NH_WAREHOUSE', N'L-NH01', N'090501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'9', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'11dc0750277242b39ff568925ebb6984', N'NH_WAREHOUSE', N'L-NH01', N'090601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'9', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'824075b779c94315bdd2b8a07db272e3', N'NH_WAREHOUSE', N'L-NH01', N'100401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'10', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'5a5ad05bd9c24cff97f5ff603d934bd9', N'NH_WAREHOUSE', N'L-NH01', N'100501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'10', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2a10ab873e77440895551d16c97ab18a', N'NH_WAREHOUSE', N'L-NH01', N'100601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'10', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'246ccb3f669f41968753b78808005946', N'NH_WAREHOUSE', N'L-NH01', N'110101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'11', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f76af667df15489aa0addf1a00136e4e', N'NH_WAREHOUSE', N'L-NH01', N'110301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'11', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'efd3ca0169b0495bbf5b2372836e580c', N'NH_WAREHOUSE', N'L-NH01', N'110401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'11', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'6922e54c68ab43fda96d24e2863dbcd5', N'NH_WAREHOUSE', N'L-NH01', N'110501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'11', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ccad1887941d4e8498dde22a9ccd5c7d', N'NH_WAREHOUSE', N'L-NH01', N'110601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'11', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'28d22cf7be7d4b81812aca6454b930a2', N'NH_WAREHOUSE', N'L-NH01', N'120101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'12', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'5fce87a2d8f54a2ebbb011d85ab0518d', N'NH_WAREHOUSE', N'L-NH01', N'120301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'12', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'67506ce7065e4a2da860a52f8bdc02bf', N'NH_WAREHOUSE', N'L-NH01', N'120401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'12', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'4582aae765cb4a459175e6afb77db040', N'NH_WAREHOUSE', N'L-NH01', N'120501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'12', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f5e43ba0983044bb92693e36afa657a7', N'NH_WAREHOUSE', N'L-NH01', N'120601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'12', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'db7e5694fa774defb1f7fd1416bacb7b', N'NH_WAREHOUSE', N'L-NH01', N'130101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'13', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'74aaa177986a437dab809388956cfc0e', N'NH_WAREHOUSE', N'L-NH01', N'130301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'13', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ad10ba27e2cf46f9bf3b1ac1c1f13c31', N'NH_WAREHOUSE', N'L-NH01', N'130401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'13', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'540af0e160ae4675ad8c47c370b010f8', N'NH_WAREHOUSE', N'L-NH01', N'130501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'13', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'232796e1e5d54cff855dffd5e98ae441', N'NH_WAREHOUSE', N'L-NH01', N'130601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'13', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f3ac99fb851e440599ebcc7d9e42e3de', N'NH_WAREHOUSE', N'L-NH01', N'140101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'14', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'7a73deb4f4c24c338ced569d51e6fd80', N'NH_WAREHOUSE', N'L-NH01', N'140301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'14', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'aa105992fcae46af8f9dcfa80e3a7f7a', N'NH_WAREHOUSE', N'L-NH01', N'140401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'14', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'7788e3dbfde640579d270984c1744e19', N'NH_WAREHOUSE', N'L-NH01', N'140501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'14', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a7968b7a1e834f90ad5e7d299d837658', N'NH_WAREHOUSE', N'L-NH01', N'140601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'14', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ca58fb339a0243a39ee58ccb4cc1d460', N'NH_WAREHOUSE', N'L-NH01', N'160101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'16', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a79831b913ab48c1bda643cc1b4aab89', N'NH_WAREHOUSE', N'L-NH01', N'160201', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'16', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'390e7e50dd924467a75047e7ad0027bd', N'NH_WAREHOUSE', N'L-NH01', N'160301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'16', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a0dfaec71b4846bcb4eae230c0b5d54e', N'NH_WAREHOUSE', N'L-NH01', N'160401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'16', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'fbdc41b768894dfaa3269999e600193f', N'NH_WAREHOUSE', N'L-NH01', N'160501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'16', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'c77b0e32cbcc4aea8f8b457f62ac4d09', N'NH_WAREHOUSE', N'L-NH01', N'160601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'16', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1a0c30caede5478d95bf46e8355784de', N'NH_WAREHOUSE', N'L-NH01', N'170101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'17', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'fd524b3b54eb4f95842f9303371e3370', N'NH_WAREHOUSE', N'L-NH01', N'170201', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'17', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b4f9fc2a2611425cbcf5f322762ca2d9', N'NH_WAREHOUSE', N'L-NH01', N'170301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'17', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'bca91ef861764291a2997a8e1a0a61fb', N'NH_WAREHOUSE', N'L-NH01', N'170401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'17', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'5c786d42e50f45a2a5f4d34a35434159', N'NH_WAREHOUSE', N'L-NH01', N'170501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'17', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'4fc760801d5f40e1925bdb1b2fc7002f', N'NH_WAREHOUSE', N'L-NH01', N'170601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'17', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'12335af5eb774321a6cd8c4675de187c', N'NH_WAREHOUSE', N'L-NH01', N'180101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'18', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'035c7f0df6c44062a98237d5445a9972', N'NH_WAREHOUSE', N'L-NH01', N'180201', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'18', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0f0953961b9e45e0873399a3d54041e4', N'NH_WAREHOUSE', N'L-NH01', N'180301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'18', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'7c0c8100230d441081ed14e31eeffe17', N'NH_WAREHOUSE', N'L-NH01', N'180401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'18', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'eaca1685ac1a44029eab600508fd18a2', N'NH_WAREHOUSE', N'L-NH01', N'180501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'18', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'fd670970447f445c8930037246308832', N'NH_WAREHOUSE', N'L-NH01', N'180601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'18', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e0ced22e76724d60a6b6da8c600bc57b', N'NH_WAREHOUSE', N'L-NH01', N'190101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'19', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a69c384a90e84e26b571ecc67ba376ea', N'NH_WAREHOUSE', N'L-NH01', N'190201', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'19', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'190c911262c0438aa442365e96174055', N'NH_WAREHOUSE', N'L-NH01', N'190301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'19', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd5714fcfa64f4f71817dea3a0b17ee7e', N'NH_WAREHOUSE', N'L-NH01', N'190401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'19', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0909dd353af94cf6875c60f42aa6b010', N'NH_WAREHOUSE', N'L-NH01', N'190501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'19', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'99e4d06e9df7466ea7eefd2624af9b7e', N'NH_WAREHOUSE', N'L-NH01', N'190601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'19', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f9dac007064c44bab8c50b493daa06db', N'NH_WAREHOUSE', N'L-NH01', N'200101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'20', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1dd732a296324e09addec1273c40d565', N'NH_WAREHOUSE', N'L-NH01', N'200201', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'20', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b01ae484ec6b4c2e873c4ca97b7e1ddb', N'NH_WAREHOUSE', N'L-NH01', N'200301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'20', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'6da77c950ab44de59955909ecd630a8d', N'NH_WAREHOUSE', N'L-NH01', N'200401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'20', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'412ac261848e45fa9be9ec21928d0c9b', N'NH_WAREHOUSE', N'L-NH01', N'200501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'20', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'16586443f79e4d7ba1394b73d1e28711', N'NH_WAREHOUSE', N'L-NH01', N'200601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'20', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'86702ba68c88484bb7341215c85b8284', N'NH_WAREHOUSE', N'L-NH01', N'210101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'21', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2b602bc78bc74f9c9bc439f104ad511c', N'NH_WAREHOUSE', N'L-NH01', N'210201', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'21', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'c88501faafd84ef08635aba388a8e3ef', N'NH_WAREHOUSE', N'L-NH01', N'210301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'21', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd0a48bf6b88347ddaa031de61b17df5e', N'NH_WAREHOUSE', N'L-NH01', N'210401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'21', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2ab7b05c5fca469496d6fa6924b813a9', N'NH_WAREHOUSE', N'L-NH01', N'210501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'21', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'4e544faa2bb14d1db7187d5b062157f7', N'NH_WAREHOUSE', N'L-NH01', N'210601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'21', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'266c2d85426f49bb9c164543dcfe53f5', N'NH_WAREHOUSE', N'L-NH01', N'220101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'22', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2e68a0001a2b43f881cd7104006f69b6', N'NH_WAREHOUSE', N'L-NH01', N'220201', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'22', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'3bd3f9d01f1148de82885c6e079b038e', N'NH_WAREHOUSE', N'L-NH01', N'220301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'22', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0f94bbb37df944fc867a5328c57c9c6c', N'NH_WAREHOUSE', N'L-NH01', N'220401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'22', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'3fc0020d1b6249a8bad7dd740f602894', N'NH_WAREHOUSE', N'L-NH01', N'220501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'22', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'9058100821f54a56905b4dd65606fc9d', N'NH_WAREHOUSE', N'L-NH01', N'220601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'22', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'44f2376a70554171a38646daea826ddb', N'NH_WAREHOUSE', N'L-NH01', N'230101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'23', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'992dc15a21184b96a3028650a4195120', N'NH_WAREHOUSE', N'L-NH01', N'230201', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'23', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8db84892fe1b44669c4054f5ef75ce83', N'NH_WAREHOUSE', N'L-NH01', N'230301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'23', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'dbed0de231cc46a885cd155965d2ad0b', N'NH_WAREHOUSE', N'L-NH01', N'230401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'23', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'9f59a111cc8b4515af955fda7239583b', N'NH_WAREHOUSE', N'L-NH01', N'230501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'23', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'83eaf4619c6c4811b61463ab0f4a484a', N'NH_WAREHOUSE', N'L-NH01', N'230601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'23', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'7810bee12bf74a8690dab5cb0cdaf0a6', N'NH_WAREHOUSE', N'L-NH01', N'240101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'24', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'c9b19bc364f14c95a62e0d3a9a89e6ed', N'NH_WAREHOUSE', N'L-NH01', N'240201', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'24', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f238f49b38e04c09838208328bb86776', N'NH_WAREHOUSE', N'L-NH01', N'240301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'24', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd3b90e12244747db99ac58e25d5e89bb', N'NH_WAREHOUSE', N'L-NH01', N'240401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'24', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'90ece604d64648ffbaf7220e32b5c894', N'NH_WAREHOUSE', N'L-NH01', N'240501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'24', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2d09197621d84447a3d5825d9b0aa438', N'NH_WAREHOUSE', N'L-NH01', N'240601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'24', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2fdff115d685498e908d2217094ca3e2', N'NH_WAREHOUSE', N'L-NH01', N'250101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'25', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ad45d220b6484db38966b8632f9d2bf3', N'NH_WAREHOUSE', N'L-NH01', N'250201', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'25', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e516780b264441cbba7abd51d000003f', N'NH_WAREHOUSE', N'L-NH01', N'250301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'25', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2a1d1bea8ad440a586a6203999fe7cba', N'NH_WAREHOUSE', N'L-NH01', N'250401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'25', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'687b9bb44e144deca247ae3f9141a54e', N'NH_WAREHOUSE', N'L-NH01', N'250501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'25', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a8a7106f5a1e4f83a1c8d22b1f63ffb0', N'NH_WAREHOUSE', N'L-NH01', N'250601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'25', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'34b80f48b8764611bee387ac0f2116d9', N'NH_WAREHOUSE', N'L-NH01', N'260101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'26', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b1f779ecf4424219b0156812d05b1866', N'NH_WAREHOUSE', N'L-NH01', N'260201', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'26', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0d5a6218231648a2bcaeb0c622cd9795', N'NH_WAREHOUSE', N'L-NH01', N'260301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'26', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ef792867339f4a19b8aa2d18f340dc5d', N'NH_WAREHOUSE', N'L-NH01', N'260401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'26', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b29ee45da6b94c00956cd5e0250676b9', N'NH_WAREHOUSE', N'L-NH01', N'260501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'26', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'cfba3551d42c45c4a844f162afec91e6', N'NH_WAREHOUSE', N'L-NH01', N'260601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'26', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'5faf05983fec42429cc4148f2810a168', N'NH_WAREHOUSE', N'L-NH01', N'270101', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'27', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a9a75d8cbe42408db8ae9bfe4a6b869c', N'NH_WAREHOUSE', N'L-NH01', N'270201', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'27', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1a96e4ec11ff4ff99e5afc10be0b35f7', N'NH_WAREHOUSE', N'L-NH01', N'270301', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'27', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'41fcdb03aa7d4f47b54b83879b57649b', N'NH_WAREHOUSE', N'L-NH01', N'270401', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'27', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8a49e442083846bb849f8488b23a5e20', N'NH_WAREHOUSE', N'L-NH01', N'270501', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'27', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'79decca12fca4380861c9fbaa6dee33b', N'NH_WAREHOUSE', N'L-NH01', N'270601', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'1', NULL, N'27', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8d1714b9b7e44c0e8cbcdf36fccd5155', N'NH_WAREHOUSE', N'L-NH01', N'010402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'1', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e81d464844954c12a898a79272ccde77', N'NH_WAREHOUSE', N'L-NH01', N'010502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'1', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'60c00d610d0b4261a64202a39f214a0b', N'NH_WAREHOUSE', N'L-NH01', N'010602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'1', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'00ecf15336194e82b38a93f6371d366d', N'NH_WAREHOUSE', N'L-NH01', N'020402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'2', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b48fb07805714e9babecd948e936532c', N'NH_WAREHOUSE', N'L-NH01', N'020502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'2', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1941f3ba75534d5e86bcaef20a9c269b', N'NH_WAREHOUSE', N'L-NH01', N'020602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'2', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a013f33183024a398aede594e7c06f99', N'NH_WAREHOUSE', N'L-NH01', N'030402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'3', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'698daa580a364f00aa61a77be602ec98', N'NH_WAREHOUSE', N'L-NH01', N'030502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'3', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f72538fd7e624abe9217ce13091cbe30', N'NH_WAREHOUSE', N'L-NH01', N'030602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'3', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'aba8452a9bff4293acf32f69c0d1b27d', N'NH_WAREHOUSE', N'L-NH01', N'040402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'4', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ac2bc650307b4a6b925470d26b53020e', N'NH_WAREHOUSE', N'L-NH01', N'040502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'4', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b2f247eb57ae4d3aa7ae28365926f3a4', N'NH_WAREHOUSE', N'L-NH01', N'040602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'4', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'41ddd8b00b6b40c29476d4fcfecb73cb', N'NH_WAREHOUSE', N'L-NH01', N'050402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'5', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ff07498e56df49019365524a960f464a', N'NH_WAREHOUSE', N'L-NH01', N'050502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'5', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd1b56c8be8d44d75a00aba16886ee414', N'NH_WAREHOUSE', N'L-NH01', N'050602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'5', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'6dcd7a324c4c4669bbc56f50df8cc2af', N'NH_WAREHOUSE', N'L-NH01', N'060402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'6', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'32900045bd5540e89cbf304a745cbb34', N'NH_WAREHOUSE', N'L-NH01', N'060502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'6', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2915efcf56514662a5fe2c3b234286ba', N'NH_WAREHOUSE', N'L-NH01', N'060602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'6', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'3cc4a5060db549ff8f644ce36c474f3e', N'NH_WAREHOUSE', N'L-NH01', N'070102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'7', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0b456ac40d1143a88b185e46ea67b24c', N'NH_WAREHOUSE', N'L-NH01', N'070302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'7', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8736458273594f39b0f2bfdcd16190f2', N'NH_WAREHOUSE', N'L-NH01', N'070402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'7', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'bf6f5a7c36564cd994edc9f742041a83', N'NH_WAREHOUSE', N'L-NH01', N'070502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'7', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'7291238714584980ab168df4a6dfdd0e', N'NH_WAREHOUSE', N'L-NH01', N'070602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'7', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'3f06b84d2bb04f28afb4ce4e749ea23e', N'NH_WAREHOUSE', N'L-NH01', N'080102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'8', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'6fd8413e12964cf594d0d205f88c103e', N'NH_WAREHOUSE', N'L-NH01', N'080302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'8', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'dd3b866ec2064a19849d9cc4f00a588d', N'NH_WAREHOUSE', N'L-NH01', N'080402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'8', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b37921055568432ebfc70dbc01a2bc19', N'NH_WAREHOUSE', N'L-NH01', N'080502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'8', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'5530a657ca95424fa135b6f0e48c358c', N'NH_WAREHOUSE', N'L-NH01', N'080602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'8', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'94ab5a5ffd7a49a191a0fc45e072bd71', N'NH_WAREHOUSE', N'L-NH01', N'090102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'9', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0d507e6a0fd9442b8a7d2df92eaf19d3', N'NH_WAREHOUSE', N'L-NH01', N'090302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'9', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'24c74a7018ca4ec48ea06c3c185c7632', N'NH_WAREHOUSE', N'L-NH01', N'090402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'9', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b8b72afcacb740908b1e7a814b908aa1', N'NH_WAREHOUSE', N'L-NH01', N'090502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'9', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'6cd65fa6ca7a4953a1a9cb85f28506a1', N'NH_WAREHOUSE', N'L-NH01', N'090602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'9', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'413ff73f76b34be79238c38d6463b558', N'NH_WAREHOUSE', N'L-NH01', N'100402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'10', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'cf5c5f26c749419b81a3459a66487baa', N'NH_WAREHOUSE', N'L-NH01', N'100502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'10', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'528036a630b44017aa3350ab00237f84', N'NH_WAREHOUSE', N'L-NH01', N'100602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'10', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f7bd20c267e248539df39711a6a5e01b', N'NH_WAREHOUSE', N'L-NH01', N'110102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'11', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'4cf3f2e1bf2b4354afd5ab27a8c19cd6', N'NH_WAREHOUSE', N'L-NH01', N'110302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'11', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'afd9dd4cb02d47028dcfc8dca4b294f3', N'NH_WAREHOUSE', N'L-NH01', N'110402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'11', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f02776e133ac4c43b0c10209a29dc5e0', N'NH_WAREHOUSE', N'L-NH01', N'110502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'11', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0baa6bb0fe064b45b174ebb353b110fa', N'NH_WAREHOUSE', N'L-NH01', N'110602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'11', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b52c4c5e0f4748b38a4bb4f8af5bdb24', N'NH_WAREHOUSE', N'L-NH01', N'120102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'12', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'67b106b5b7574ef995711ee4eb7dfc06', N'NH_WAREHOUSE', N'L-NH01', N'120302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'12', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2a6b8a87681147889501b28e8a015269', N'NH_WAREHOUSE', N'L-NH01', N'120402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'12', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e837e55e61474d59bca5112c9f2d43c9', N'NH_WAREHOUSE', N'L-NH01', N'120502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'12', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'c8688f9a3ab04645b8d69cb2e3057451', N'NH_WAREHOUSE', N'L-NH01', N'120602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'12', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a1049816b12c48e1aaeb0f2c4274d1c1', N'NH_WAREHOUSE', N'L-NH01', N'130102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'13', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'fc6bef895a614310880e5d9f0ff07539', N'NH_WAREHOUSE', N'L-NH01', N'130302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'13', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e10014ebc14f4c65999096b579934b9a', N'NH_WAREHOUSE', N'L-NH01', N'130402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'13', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'9ec8cf8d54e642d09e5ecd9fe4e09868', N'NH_WAREHOUSE', N'L-NH01', N'130502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'13', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ba88598482714046a3ae7f79b26c2b20', N'NH_WAREHOUSE', N'L-NH01', N'130602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'13', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1b24f5f0dc0d4541b6919e72e711d34a', N'NH_WAREHOUSE', N'L-NH01', N'140102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'14', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f98b12b66c2d45b890ad53c78a27dede', N'NH_WAREHOUSE', N'L-NH01', N'140302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'14', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'337a8cdebccf41bab22a1dad1bbfab73', N'NH_WAREHOUSE', N'L-NH01', N'140402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'14', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e5f94a9480d64ac9916cd9caf6dedd55', N'NH_WAREHOUSE', N'L-NH01', N'140502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'14', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'9799bb41170f4e32b4fe78acdec75105', N'NH_WAREHOUSE', N'L-NH01', N'140602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'14', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2fa9c0b557c447ec88830aa69ae63465', N'NH_WAREHOUSE', N'L-NH01', N'160102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'16', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8163742678b44a8ba6614f60dfa50c20', N'NH_WAREHOUSE', N'L-NH01', N'160202', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'16', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'30668d96d0c54d95a9fb812aed487e04', N'NH_WAREHOUSE', N'L-NH01', N'160302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'16', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ec4e91c8c32444a98dd70ac3a0424a28', N'NH_WAREHOUSE', N'L-NH01', N'160402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'16', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'85ed4af97249468ab0566af90f46b2ad', N'NH_WAREHOUSE', N'L-NH01', N'160502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'16', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8d67262c0a43475388449d5a52997f56', N'NH_WAREHOUSE', N'L-NH01', N'160602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'16', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'58aeefca8f1e4e1cbb0a402152d1ecaa', N'NH_WAREHOUSE', N'L-NH01', N'170102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'17', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'9716b5c3ec174fb3afa42e92314a60fc', N'NH_WAREHOUSE', N'L-NH01', N'170202', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'17', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd76242554487401da0f89ece30a7c558', N'NH_WAREHOUSE', N'L-NH01', N'170302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'17', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'151fd7033e8749ad82c2771f6152cc6d', N'NH_WAREHOUSE', N'L-NH01', N'170402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'17', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'19dce6af87e244c2ae7b782373a286d1', N'NH_WAREHOUSE', N'L-NH01', N'170502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'17', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'767ad0e8459146ccb44d2465547c6100', N'NH_WAREHOUSE', N'L-NH01', N'170602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'17', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'406e5bacfaf84a458c16e45583cd5b7a', N'NH_WAREHOUSE', N'L-NH01', N'180102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'18', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0a1f40d19748418ca4714df919088987', N'NH_WAREHOUSE', N'L-NH01', N'180202', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'18', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ab41579671ea4afc86d6a1caefa6a826', N'NH_WAREHOUSE', N'L-NH01', N'180302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'18', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b27b67987caa4a2fad7a964958615c62', N'NH_WAREHOUSE', N'L-NH01', N'180402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'18', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'5054b6b9384c48dbb45d85b421312e03', N'NH_WAREHOUSE', N'L-NH01', N'180502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'18', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8141debf747e46c89f0f85c3162af65d', N'NH_WAREHOUSE', N'L-NH01', N'180602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'18', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'bf0b974c413e4c1c8a1663238fe0600d', N'NH_WAREHOUSE', N'L-NH01', N'190102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'19', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'147f1b42cb904e80a6cb61a7c06567ef', N'NH_WAREHOUSE', N'L-NH01', N'190202', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'19', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'392dbb6088d541a690092ea8bf6c06cd', N'NH_WAREHOUSE', N'L-NH01', N'190302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'19', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b8d4f90555ae40689e9d96fc40750324', N'NH_WAREHOUSE', N'L-NH01', N'190402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'19', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'46f2f20bf81646f2b80c9402a183bd53', N'NH_WAREHOUSE', N'L-NH01', N'190502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'19', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ab637cfc5be04d468e354e71aea5eb7c', N'NH_WAREHOUSE', N'L-NH01', N'190602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'19', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'87c32a3afedc43feb7d4e03be0d33db6', N'NH_WAREHOUSE', N'L-NH01', N'200102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'20', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f3ea819e92624a5192027f07fd2c6051', N'NH_WAREHOUSE', N'L-NH01', N'200202', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'20', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'de1fa0b3c7e84df3a607815971a4e6b5', N'NH_WAREHOUSE', N'L-NH01', N'200302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'20', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8a2b1f457d2043d18b33448272137942', N'NH_WAREHOUSE', N'L-NH01', N'200402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'20', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e9ec52e706634976bdfae51411a0bfcb', N'NH_WAREHOUSE', N'L-NH01', N'200502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'20', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'c4dbba0fdf7245f9ba64cb2198c5faad', N'NH_WAREHOUSE', N'L-NH01', N'200602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'20', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'61229654450d4e119f5ab582315aa676', N'NH_WAREHOUSE', N'L-NH01', N'210102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'21', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1f4ba1cf10f74318b801d9828d3ebf33', N'NH_WAREHOUSE', N'L-NH01', N'210202', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'21', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'92ad1a00a47841909ac7b7467c7127f9', N'NH_WAREHOUSE', N'L-NH01', N'210302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'21', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'744d1d99e61d4b16ac7cbef3da328d1a', N'NH_WAREHOUSE', N'L-NH01', N'210402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'21', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1f91406c79c6492493d8bbdd32c95cce', N'NH_WAREHOUSE', N'L-NH01', N'210502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'21', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'4a32afae8e974e6e99749511d2b73740', N'NH_WAREHOUSE', N'L-NH01', N'210602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'21', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'cee85df5bb91482ca9fca2b1dc6d63cc', N'NH_WAREHOUSE', N'L-NH01', N'220102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'22', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'468bf58fdc694b01bf00ddf9cfd62081', N'NH_WAREHOUSE', N'L-NH01', N'220202', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'22', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'c8cd0425315548dea084f17e0d40de6d', N'NH_WAREHOUSE', N'L-NH01', N'220302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'22', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'cc7a4eb3677648c59a64cc91eeed09a8', N'NH_WAREHOUSE', N'L-NH01', N'220402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'22', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'cfabb43110314646b6bd5ee5dbb7bb84', N'NH_WAREHOUSE', N'L-NH01', N'220502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'22', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f650fdd435f849bba3b3caa0dc58b7d2', N'NH_WAREHOUSE', N'L-NH01', N'220602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'22', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'9090531dccf545f7bf370306ffa3b0d1', N'NH_WAREHOUSE', N'L-NH01', N'230102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'23', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f95fb0c7d5564ed69d4c0a1faa8c9110', N'NH_WAREHOUSE', N'L-NH01', N'230202', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'23', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ca1f55049394406b9978deae2b070890', N'NH_WAREHOUSE', N'L-NH01', N'230302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'23', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'bfca6576759a41a593c48b1ad2a0c6b8', N'NH_WAREHOUSE', N'L-NH01', N'230402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'23', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'931dbc31d9244ba4b9bf10a8af78cc15', N'NH_WAREHOUSE', N'L-NH01', N'230502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'23', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'5852cedad9094f1ea82b9125d8af9c6d', N'NH_WAREHOUSE', N'L-NH01', N'230602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'23', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'9a66e8834d37445da6840e92d501a8bc', N'NH_WAREHOUSE', N'L-NH01', N'240102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'24', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'7bd08fa8c73b4cc1ba2d49962d47d966', N'NH_WAREHOUSE', N'L-NH01', N'240202', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'24', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'572ce9906b3f4be0b83f6b424d3bf08a', N'NH_WAREHOUSE', N'L-NH01', N'240302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'24', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2bc856792faf414f87add3b5aeb96e2b', N'NH_WAREHOUSE', N'L-NH01', N'240402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'24', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'91cfbcf4dd884d72848ec6f1a747b11f', N'NH_WAREHOUSE', N'L-NH01', N'240502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'24', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'217a6c228c604955bde04f6521616581', N'NH_WAREHOUSE', N'L-NH01', N'240602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'24', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'3233bd947adb4419b2b44ec84a631fb1', N'NH_WAREHOUSE', N'L-NH01', N'250102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'25', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'83e63d7a15de46cfb7acd89634312237', N'NH_WAREHOUSE', N'L-NH01', N'250202', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'25', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'212fe5c8fca44a64a099a2ad78e8faec', N'NH_WAREHOUSE', N'L-NH01', N'250302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'25', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'612c8d9149894ef5badae21d4b9eb327', N'NH_WAREHOUSE', N'L-NH01', N'250402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'25', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'02893b0cb5fe426484885d3d4b77d5f4', N'NH_WAREHOUSE', N'L-NH01', N'250502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'25', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'61c5f825850844e598870dfb267b398a', N'NH_WAREHOUSE', N'L-NH01', N'250602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'25', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'9edd7844e2c84451bb09c0eba0f1ef7e', N'NH_WAREHOUSE', N'L-NH01', N'260102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'26', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'664aed5918f7487687f4e7be3c73d070', N'NH_WAREHOUSE', N'L-NH01', N'260202', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'26', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'9fc7536827db4f05bf33b0b5b87575f5', N'NH_WAREHOUSE', N'L-NH01', N'260302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'26', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b5dcb38197364201a22a8a8e67b953e1', N'NH_WAREHOUSE', N'L-NH01', N'260402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'26', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'85b7862791ee4c0a903258b28cdef24f', N'NH_WAREHOUSE', N'L-NH01', N'260502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'26', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1bbb45711a934c2d9cbb8847728d7ba1', N'NH_WAREHOUSE', N'L-NH01', N'260602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'26', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a7f17faffb944bc3be1f6336c645daf5', N'NH_WAREHOUSE', N'L-NH01', N'270102', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'27', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a8e104fa19bc4761918025f1ec3b82a6', N'NH_WAREHOUSE', N'L-NH01', N'270202', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'27', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'82c75e4dcb804fa99945a0fcfc9edfcf', N'NH_WAREHOUSE', N'L-NH01', N'270302', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'27', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'835688e1b9f843f9862b4f7be7a27cb6', N'NH_WAREHOUSE', N'L-NH01', N'270402', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'27', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'4dfcf1bb6e3f4d0489275d29d5d020b0', N'NH_WAREHOUSE', N'L-NH01', N'270502', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'27', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8c1da238a6214ee59fd65046edb75ad2', N'NH_WAREHOUSE', N'L-NH01', N'270602', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'2', NULL, N'27', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'49fcc5f07b3c436d93b3f46afa824aac', N'NH_WAREHOUSE', N'L-NH01', N'010403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'1', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'07f3d539d9c54a4e96225ee3df51c347', N'NH_WAREHOUSE', N'L-NH01', N'010503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'1', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'4599338de55048e8bea5aaeb608a4818', N'NH_WAREHOUSE', N'L-NH01', N'010603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'1', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e4bd7854d1ee405a905d8c7e83c84003', N'NH_WAREHOUSE', N'L-NH01', N'020403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'2', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'4dc1b077ef5447f9915fb83fde8e7e5b', N'NH_WAREHOUSE', N'L-NH01', N'020503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'2', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'173d2c2df32d4147b3a813569fcf63ec', N'NH_WAREHOUSE', N'L-NH01', N'020603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'2', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'366e771306814e6ab2f4d16d4af6b502', N'NH_WAREHOUSE', N'L-NH01', N'030403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'3', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'cae74f808839477fae5276d1645cae17', N'NH_WAREHOUSE', N'L-NH01', N'030503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'3', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'edca8ddda2b04a738df33c939f280333', N'NH_WAREHOUSE', N'L-NH01', N'030603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'3', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a019f0c5074d4fb393a50ab854110a9d', N'NH_WAREHOUSE', N'L-NH01', N'040403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'4', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'99e2200dd3c44da8a98426f4ae3854ea', N'NH_WAREHOUSE', N'L-NH01', N'040503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'4', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2a5291ab2cd64f97b719cbfb5f59aad6', N'NH_WAREHOUSE', N'L-NH01', N'040603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'4', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'82c208c62ce748bd8182edefb74b9ba5', N'NH_WAREHOUSE', N'L-NH01', N'050403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'5', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'53de9bfb6d6c4a95bfe47eb0439d57b6', N'NH_WAREHOUSE', N'L-NH01', N'050503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'5', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'bf0a192624fd498cadcc67b89fecee5b', N'NH_WAREHOUSE', N'L-NH01', N'050603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'5', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'48f4c7572f7849bca5e82d3acab85144', N'NH_WAREHOUSE', N'L-NH01', N'060403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'6', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ef5f8c42a3af47aa86f379ceecc2f04d', N'NH_WAREHOUSE', N'L-NH01', N'060503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'6', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd095cef1c44f4e8380c595f80bddadab', N'NH_WAREHOUSE', N'L-NH01', N'060603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'6', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0ab6667131b84e0d943fa3086f673a7d', N'NH_WAREHOUSE', N'L-NH01', N'070103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'7', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2f1fcc66a51046beb8f0cbcc34177c8a', N'NH_WAREHOUSE', N'L-NH01', N'070303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'7', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'eb68ee2522664657b7d1b2d38a85bf84', N'NH_WAREHOUSE', N'L-NH01', N'070403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'7', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'74fd80fe8294420f8e5cf88c9a3afc9c', N'NH_WAREHOUSE', N'L-NH01', N'070503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'7', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'68c051e623d749a6b5b7e8dca09a7e59', N'NH_WAREHOUSE', N'L-NH01', N'070603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'7', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd72a9db86d734d57b8928320a616a37d', N'NH_WAREHOUSE', N'L-NH01', N'080103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'8', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e0cfd3f24a8e4cf69f111e8e2f68c11d', N'NH_WAREHOUSE', N'L-NH01', N'080303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'8', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b1b40e68b7b14d8cad1ea81adb16a52c', N'NH_WAREHOUSE', N'L-NH01', N'080403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'8', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ad766c728e9d4bdabfa1fc94ce533850', N'NH_WAREHOUSE', N'L-NH01', N'080503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'8', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'76a585f042ec4ab5871f15c797e723bd', N'NH_WAREHOUSE', N'L-NH01', N'080603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'8', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a31be3d721f5432d93ee11baeb9be649', N'NH_WAREHOUSE', N'L-NH01', N'090103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'9', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ab0581ec28f84bed814419fc7aa18d60', N'NH_WAREHOUSE', N'L-NH01', N'090303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'9', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'397c83f33ddc4d418e3565c0beddf2ed', N'NH_WAREHOUSE', N'L-NH01', N'090403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'9', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ceee762f389341f098e9503a9bea2769', N'NH_WAREHOUSE', N'L-NH01', N'090503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'9', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'96bc79bd549146559b31847cce049b39', N'NH_WAREHOUSE', N'L-NH01', N'090603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'9', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'cde523dd675846218a483f9cdb936fbb', N'NH_WAREHOUSE', N'L-NH01', N'100403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'10', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'29cb3392fe5f45e8a8b950a3b0ffd884', N'NH_WAREHOUSE', N'L-NH01', N'100503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'10', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0d7ee19fd2a04e8c925835cb8f8abe07', N'NH_WAREHOUSE', N'L-NH01', N'100603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'10', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'10d39b4f8ff14ffdad76795204ecdabc', N'NH_WAREHOUSE', N'L-NH01', N'110103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'11', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'601f431464f849688cec26a1e0694cef', N'NH_WAREHOUSE', N'L-NH01', N'110303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'11', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'91f4bc21de484ec09bafdad6ba7272f7', N'NH_WAREHOUSE', N'L-NH01', N'110403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'11', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'7fb01b21cbd649b2a2fcb47cf270c554', N'NH_WAREHOUSE', N'L-NH01', N'110503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'11', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ac208515cc4a4533b74a04bdcea74561', N'NH_WAREHOUSE', N'L-NH01', N'110603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'11', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0a7a1b4b5f5f4d0282524321152b2e45', N'NH_WAREHOUSE', N'L-NH01', N'120103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'12', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'84b3ee2eb2dd46be937041c1cf7378bb', N'NH_WAREHOUSE', N'L-NH01', N'120303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'12', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'874237d362d148bba28bb618f094559e', N'NH_WAREHOUSE', N'L-NH01', N'120403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'12', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'983c4eea3e1f4726bff505f5493da7aa', N'NH_WAREHOUSE', N'L-NH01', N'120503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'12', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'4f5a75cf625f4ea8b0a3c6f5fd297b01', N'NH_WAREHOUSE', N'L-NH01', N'120603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'12', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1edf857d6ea241e79a9ab712281ad5b7', N'NH_WAREHOUSE', N'L-NH01', N'130103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'13', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1795c64e0a9c4c0f97f55d0270e0bebe', N'NH_WAREHOUSE', N'L-NH01', N'130303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'13', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ab61f986827f414394ee5067b838f252', N'NH_WAREHOUSE', N'L-NH01', N'130403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'13', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'41386bab38204937bf3c46212121e6e8', N'NH_WAREHOUSE', N'L-NH01', N'130503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'13', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'9982b42e158947ffbd1632582305aa85', N'NH_WAREHOUSE', N'L-NH01', N'130603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'13', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'c9e415dd1c824840b7466c5ec3c6a217', N'NH_WAREHOUSE', N'L-NH01', N'140103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'14', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd18bf132aac8427786df4090ad7b3441', N'NH_WAREHOUSE', N'L-NH01', N'140303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'14', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'cf170ce41e97465a8e32300fb8d29ef8', N'NH_WAREHOUSE', N'L-NH01', N'140403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'14', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8e7c4de91e944fc5a65d1d550349db4c', N'NH_WAREHOUSE', N'L-NH01', N'140503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'14', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2c32ed95926a45978b28b5cb83d8b918', N'NH_WAREHOUSE', N'L-NH01', N'140603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'14', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1b856a46a07c4a989552dc4c052498cf', N'NH_WAREHOUSE', N'L-NH01', N'160103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'16', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'9e20313d0f3b45c2bf49e1d369c611aa', N'NH_WAREHOUSE', N'L-NH01', N'160203', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'16', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd9e8f77837c449e599f2d733f745065c', N'NH_WAREHOUSE', N'L-NH01', N'160303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'16', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'5450cb7f53d146feba88f390260a2209', N'NH_WAREHOUSE', N'L-NH01', N'160403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'16', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e2acc9bc64044d02b94ac213d0f86124', N'NH_WAREHOUSE', N'L-NH01', N'160503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'16', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a700928006194a56ade2b17db3b2bceb', N'NH_WAREHOUSE', N'L-NH01', N'160603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'16', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'92aa5251eaae4905957b359994c3a728', N'NH_WAREHOUSE', N'L-NH01', N'170103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'17', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1bb2e0c2765e46e6a48477e154bdcec0', N'NH_WAREHOUSE', N'L-NH01', N'170203', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'17', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'6e3e762f74da451a80bd9b1fb70f5927', N'NH_WAREHOUSE', N'L-NH01', N'170303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'17', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f4a0fceb0a9a4607b43c68491761858d', N'NH_WAREHOUSE', N'L-NH01', N'170403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'17', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'92e106d718684721b2b7c5b73e952f01', N'NH_WAREHOUSE', N'L-NH01', N'170503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'17', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0a6148f85e3f42058f924f6db4d537fb', N'NH_WAREHOUSE', N'L-NH01', N'170603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'17', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'13bed79cf7214474a132f789157bb7a6', N'NH_WAREHOUSE', N'L-NH01', N'180103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'18', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8977089e54494986a244656a8042a648', N'NH_WAREHOUSE', N'L-NH01', N'180203', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'18', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'908d09fe80404fedbba8f586c446f9ed', N'NH_WAREHOUSE', N'L-NH01', N'180303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'18', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e04d9fa8dd3f48f3870a26328012688c', N'NH_WAREHOUSE', N'L-NH01', N'180403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'18', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'969fdb1d5cec4bac862c2801111ed485', N'NH_WAREHOUSE', N'L-NH01', N'180503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'18', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'5d8eee1a754943e9b0628b0c264950ff', N'NH_WAREHOUSE', N'L-NH01', N'180603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'18', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'fcb208573211401daea62d3e1f675225', N'NH_WAREHOUSE', N'L-NH01', N'190103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'19', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'9e3d510c66bc4190b2b001547595c417', N'NH_WAREHOUSE', N'L-NH01', N'190203', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'19', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'554d4c2122754285a1f41896f5bb9feb', N'NH_WAREHOUSE', N'L-NH01', N'190303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'19', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'67ee9b80290448d989952b6de416c73c', N'NH_WAREHOUSE', N'L-NH01', N'190403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'19', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'64e021fbdfa4425eb7a747270866d5af', N'NH_WAREHOUSE', N'L-NH01', N'190503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'19', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f3a58646cd5048a2b5d4199e2ce80dfc', N'NH_WAREHOUSE', N'L-NH01', N'190603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'19', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ea453f84a0ad48159ba7f531f0de2f74', N'NH_WAREHOUSE', N'L-NH01', N'200103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'20', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'dec8041d5ccc4bcdaa6580b1e80f15c5', N'NH_WAREHOUSE', N'L-NH01', N'200203', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'20', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1a46df630c4c4ea4978a32c3bbfeaa91', N'NH_WAREHOUSE', N'L-NH01', N'200303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'20', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'5588d00d1bd4485c8828512bd54ba0b9', N'NH_WAREHOUSE', N'L-NH01', N'200403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'20', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'c83e817a19cc42bd9d9196564223f30d', N'NH_WAREHOUSE', N'L-NH01', N'200503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'20', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f7c3534a54524746a6899120551c13b4', N'NH_WAREHOUSE', N'L-NH01', N'200603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'20', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd0c7f5539de74112a3b81aa546b26bc8', N'NH_WAREHOUSE', N'L-NH01', N'210103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'21', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'11e16d8694104f4889120e73147fc97f', N'NH_WAREHOUSE', N'L-NH01', N'210203', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'21', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a890bd7353aa40b9ba380237fd867b97', N'NH_WAREHOUSE', N'L-NH01', N'210303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'21', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd37581ee8a59475b9ff765deeda6b282', N'NH_WAREHOUSE', N'L-NH01', N'210403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'21', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'13fd74a9a33e4ac7bfc20b945051fce3', N'NH_WAREHOUSE', N'L-NH01', N'210503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'21', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'5a43be3eea064cb0812f5dca45882f62', N'NH_WAREHOUSE', N'L-NH01', N'210603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'21', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'c2c62c9afa68453ca84d9d76c8242b61', N'NH_WAREHOUSE', N'L-NH01', N'220103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'22', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'6535eb457ddf46bfba05b477e2903329', N'NH_WAREHOUSE', N'L-NH01', N'220203', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'22', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'04ef71d0dcb64292a5d4540ecd99c764', N'NH_WAREHOUSE', N'L-NH01', N'220303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'22', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'fe8462c9bbd549d590286a15105c01bd', N'NH_WAREHOUSE', N'L-NH01', N'220403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'22', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'dde4961e7fe24fb8b63c33dd63d34ecd', N'NH_WAREHOUSE', N'L-NH01', N'220503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'22', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'02e25273cb6749a0ac5621a7cfefbe94', N'NH_WAREHOUSE', N'L-NH01', N'220603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'22', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'beb75a6ebd5d44c19141d882bb2fae81', N'NH_WAREHOUSE', N'L-NH01', N'230103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'23', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a1c6a7341c2c42efb9124dfaa6790bc2', N'NH_WAREHOUSE', N'L-NH01', N'230203', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'23', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ed70af0dfb79487abe9cee363cddce21', N'NH_WAREHOUSE', N'L-NH01', N'230303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'23', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e69714c317604c668e51c5117b5bd580', N'NH_WAREHOUSE', N'L-NH01', N'230403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'23', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b15d1408091442e2a3e092e157f3a239', N'NH_WAREHOUSE', N'L-NH01', N'230503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'23', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f91e631843a440e1a9cd84d9b5ec39c7', N'NH_WAREHOUSE', N'L-NH01', N'230603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'23', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'aced7e12b12d4d5c881e8f7fe2ab93e3', N'NH_WAREHOUSE', N'L-NH01', N'240103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'24', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'7087ab581b464cb5826f616483be3f8c', N'NH_WAREHOUSE', N'L-NH01', N'240203', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'24', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'3dad00ac7df34ee3b42456b7666c6aa7', N'NH_WAREHOUSE', N'L-NH01', N'240303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'24', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'4675ece135bc4e0aaf168472bbb5da4d', N'NH_WAREHOUSE', N'L-NH01', N'240403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'24', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'55e03bd103c149e5b2d665f01b3b9276', N'NH_WAREHOUSE', N'L-NH01', N'240503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'24', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'4d90af720ba2429a909373bce2970a11', N'NH_WAREHOUSE', N'L-NH01', N'240603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'24', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ead37f4badda4ae7aad2d43ce84e8a61', N'NH_WAREHOUSE', N'L-NH01', N'250103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'25', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b7de5ae31e5b4fe487d4a4439a735261', N'NH_WAREHOUSE', N'L-NH01', N'250203', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'25', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'4761c92bc5384cd2804118c0cb3ab622', N'NH_WAREHOUSE', N'L-NH01', N'250303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'25', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'eb6da598696d48609a4490e1b6fcf02c', N'NH_WAREHOUSE', N'L-NH01', N'250403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'25', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ea98ee0c61464666b7996906af60ddfb', N'NH_WAREHOUSE', N'L-NH01', N'250503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'25', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e30ea05bdc1b4395bf9e757ee8a4bf2a', N'NH_WAREHOUSE', N'L-NH01', N'250603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'25', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e180e7606e5d4e699e277b5b9f32de9e', N'NH_WAREHOUSE', N'L-NH01', N'260103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'26', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'3ccb831ecea942ef9c6af220d5365b03', N'NH_WAREHOUSE', N'L-NH01', N'260203', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'26', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'c305fef3ba364cc9b662770d5286f7c9', N'NH_WAREHOUSE', N'L-NH01', N'260303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'26', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'89f81e522d1a4d388b83a2d02e837416', N'NH_WAREHOUSE', N'L-NH01', N'260403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'26', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'df70f33f81b6422aac78d0b45e875223', N'NH_WAREHOUSE', N'L-NH01', N'260503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'26', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8ef0dfdb2c244e8ea53dc7669880de93', N'NH_WAREHOUSE', N'L-NH01', N'260603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'26', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'305923e8e84043d4be1532c21660c13e', N'NH_WAREHOUSE', N'L-NH01', N'270103', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'27', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'587d7429a6cc4092b17fa9c9ce37e2b8', N'NH_WAREHOUSE', N'L-NH01', N'270203', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'27', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ac941a5c9ddb4b0795ff4aa2212e49a4', N'NH_WAREHOUSE', N'L-NH01', N'270303', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'27', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'30a166c71f8d4e3795508b72f581e5a8', N'NH_WAREHOUSE', N'L-NH01', N'270403', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'27', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'cfdb8d54a32848df862ff0e33f0c967f', N'NH_WAREHOUSE', N'L-NH01', N'270503', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'27', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'719dd114e07a485fb0bd9cbcc7ac0bbe', N'NH_WAREHOUSE', N'L-NH01', N'270603', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'3', NULL, N'27', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'567d64bebab74a0e9ff6a26892d766b0', N'NH_WAREHOUSE', N'L-NH01', N'010404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'1', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'6d1060c42774497582554ddc07b60d51', N'NH_WAREHOUSE', N'L-NH01', N'010504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'1', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b88d06f975c544838fe6bca3a23f0634', N'NH_WAREHOUSE', N'L-NH01', N'010604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'1', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'92d22290bbc6470f9c2d0cc45167f7d5', N'NH_WAREHOUSE', N'L-NH01', N'020404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'2', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'71703a9eebc9427eb85b1a5309611514', N'NH_WAREHOUSE', N'L-NH01', N'020504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'2', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1f1e1b6e94a64e0bae53e16c60e86d2d', N'NH_WAREHOUSE', N'L-NH01', N'020604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'2', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8dc30bd3a5224f27a42b77b550f63e09', N'NH_WAREHOUSE', N'L-NH01', N'030404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'3', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'5d0080b919c6440a9f46a1200d09a1ad', N'NH_WAREHOUSE', N'L-NH01', N'030504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'3', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1de971447bf2495b98755a3be63fed2d', N'NH_WAREHOUSE', N'L-NH01', N'030604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'3', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'3b7a11e4001f49e39c07ab2efcb04e97', N'NH_WAREHOUSE', N'L-NH01', N'040404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'4', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'69b9f2a44da547d8824bbc11fa1cdcea', N'NH_WAREHOUSE', N'L-NH01', N'040504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'4', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'c0fb7e25d6024985b8b84dbe056f8263', N'NH_WAREHOUSE', N'L-NH01', N'040604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'4', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'000c0460d2844784a9f7c6614056f059', N'NH_WAREHOUSE', N'L-NH01', N'050404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'5', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'84020ffe4e65450a99defd9c71a05a6c', N'NH_WAREHOUSE', N'L-NH01', N'050504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'5', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f4ba09e5557442e9843432421f86634d', N'NH_WAREHOUSE', N'L-NH01', N'050604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'5', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b1485988607e47f996fbd4c94213fc51', N'NH_WAREHOUSE', N'L-NH01', N'060404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'6', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'65ddf1b04d0f4b868c0728a662db20ae', N'NH_WAREHOUSE', N'L-NH01', N'060504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'6', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd9d15d47e1eb4db3ba4abcb6b6a0f88c', N'NH_WAREHOUSE', N'L-NH01', N'060604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'6', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'20f6a2ea65df4f6fba1d93b6226d953b', N'NH_WAREHOUSE', N'L-NH01', N'070104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'7', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'896ba1b0ddb7434eb469dff2f1063d98', N'NH_WAREHOUSE', N'L-NH01', N'070304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'7', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2e862ce773a14c9f8f16139a63319477', N'NH_WAREHOUSE', N'L-NH01', N'070404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'7', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'9b9205151cef4042b66b9711731f28b8', N'NH_WAREHOUSE', N'L-NH01', N'070504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'7', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'4665be51010e4311b459aa2de0e7e166', N'NH_WAREHOUSE', N'L-NH01', N'070604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'7', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'27d0bd302287449f8fbf5f601c064c6c', N'NH_WAREHOUSE', N'L-NH01', N'080104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'8', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'6de60122924b4fca89c98d3605e7c321', N'NH_WAREHOUSE', N'L-NH01', N'080304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'8', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f35038b2909441b4a686edf66ff87203', N'NH_WAREHOUSE', N'L-NH01', N'080404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'8', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'6141b58faaec4592bf5c18881c1df365', N'NH_WAREHOUSE', N'L-NH01', N'080504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'8', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd98490e870cb401bbdcdd7e0254af885', N'NH_WAREHOUSE', N'L-NH01', N'080604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'8', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'959a6df20fe44098ba8c426d8abfdebd', N'NH_WAREHOUSE', N'L-NH01', N'090104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'9', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0aa083512c35480398d2fcfae25e35ab', N'NH_WAREHOUSE', N'L-NH01', N'090304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'9', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'da59d98153c948f2a8c67ae4eb3ef9c8', N'NH_WAREHOUSE', N'L-NH01', N'090404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'9', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'73bbacd3ff2a46879d920a93880e36a5', N'NH_WAREHOUSE', N'L-NH01', N'090504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'9', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'19e6865a001345b7961b48a626cd3618', N'NH_WAREHOUSE', N'L-NH01', N'090604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'9', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b6730cbda88349a9a6796164f35b811e', N'NH_WAREHOUSE', N'L-NH01', N'100404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'10', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'7c3c48e69e1d425fad58fe53eccc778f', N'NH_WAREHOUSE', N'L-NH01', N'100504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'10', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a3179a94e19d4f009933a614335fc5ea', N'NH_WAREHOUSE', N'L-NH01', N'100604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'10', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'eb7eea5a3dd141dfbf8c5c175e106fa1', N'NH_WAREHOUSE', N'L-NH01', N'110104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'11', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'7b09066e5ece4026a3b0e9decb72ec19', N'NH_WAREHOUSE', N'L-NH01', N'110304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'11', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'33fc8e1f9b6945eb8e861107e0bd804d', N'NH_WAREHOUSE', N'L-NH01', N'110404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'11', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'652f4ba4978647e885508a216ca35394', N'NH_WAREHOUSE', N'L-NH01', N'110504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'11', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'eeadec6c34964d4181046f0cbe883a01', N'NH_WAREHOUSE', N'L-NH01', N'110604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'11', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'bdbeb31edbdf48089f60ad0b22896423', N'NH_WAREHOUSE', N'L-NH01', N'120104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'12', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'de1f0e595b1d4da8b38816bdcb920f2c', N'NH_WAREHOUSE', N'L-NH01', N'120304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'12', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'54d0688810cb4a69b54f4f6ef70aa379', N'NH_WAREHOUSE', N'L-NH01', N'120404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'12', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'758b6d329bd442b3bbcd2431df2db313', N'NH_WAREHOUSE', N'L-NH01', N'120504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'12', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ab47be8aa7ba4caa9e796aa2d22b0b2e', N'NH_WAREHOUSE', N'L-NH01', N'120604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'12', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f85248bafc7e4e76beedac7ce62451c6', N'NH_WAREHOUSE', N'L-NH01', N'130104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'13', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f91b8becc8e84fd0a2e7cd9a301a648a', N'NH_WAREHOUSE', N'L-NH01', N'130304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'13', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1f17bab632d04bf89a9faa8bdbe00542', N'NH_WAREHOUSE', N'L-NH01', N'130404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'13', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'40f4e85668e94ebe9e8d781ccfea4a90', N'NH_WAREHOUSE', N'L-NH01', N'130504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'13', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8ecc01579edd41709321cbce12e161d5', N'NH_WAREHOUSE', N'L-NH01', N'130604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'13', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'df600ad826484726abece45035efd60b', N'NH_WAREHOUSE', N'L-NH01', N'140104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'14', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'23124b1a8ad2443fb1428ff7371eb4b4', N'NH_WAREHOUSE', N'L-NH01', N'140304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'14', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'82873fe2ea27493caedd3b14eab5102c', N'NH_WAREHOUSE', N'L-NH01', N'140404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'14', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0012c022b17d4e9d854f4cf2f59d76f9', N'NH_WAREHOUSE', N'L-NH01', N'140504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'14', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'3127df8f9c9c4e33b41bdc3e5302cd7a', N'NH_WAREHOUSE', N'L-NH01', N'140604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'14', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'3a35064bdd254b53be2573cdff4a596d', N'NH_WAREHOUSE', N'L-NH01', N'160104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'16', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'624b2dae6dbb4db495b65191a5b02b53', N'NH_WAREHOUSE', N'L-NH01', N'160204', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'16', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'040daab4d60642c08afa41ef7e081faf', N'NH_WAREHOUSE', N'L-NH01', N'160304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'16', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'7a33a0f7deb141888d8f89fdc35e09f6', N'NH_WAREHOUSE', N'L-NH01', N'160404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'16', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e215cb1d70fd4d35b77bb3b20e71c5bd', N'NH_WAREHOUSE', N'L-NH01', N'160504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'16', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b5c106eabd2a406c9f6ff711f4c141b4', N'NH_WAREHOUSE', N'L-NH01', N'160604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'16', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a9c8a0ec8a9549d2972c44641617835f', N'NH_WAREHOUSE', N'L-NH01', N'170104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'17', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a0038e8dc8f94d0a93d96ba6ea2813e5', N'NH_WAREHOUSE', N'L-NH01', N'170204', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'17', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e4803f34e9ec48f196ccd504b3672ea8', N'NH_WAREHOUSE', N'L-NH01', N'170304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'17', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'08cd9a5da17c4dc08840671187b100fd', N'NH_WAREHOUSE', N'L-NH01', N'170404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'17', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'5040aa5409514dc5a12d0c3ae3e6777c', N'NH_WAREHOUSE', N'L-NH01', N'170504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'17', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'20e7a5fae81d4c469f74b70bda059eea', N'NH_WAREHOUSE', N'L-NH01', N'170604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'17', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'6399fa6c3a864e729736f9fb94a208b5', N'NH_WAREHOUSE', N'L-NH01', N'180104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'18', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'32d34d4a119f4ebb968b75cb06b9a883', N'NH_WAREHOUSE', N'L-NH01', N'180204', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'18', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ea0bc7528b8c43f0a6debf21ca2435e2', N'NH_WAREHOUSE', N'L-NH01', N'180304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'18', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'84b2013e2a944c02a3abc3ebc949af28', N'NH_WAREHOUSE', N'L-NH01', N'180404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'18', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1cb463b37c4d4b7a80caff5ec0cbf405', N'NH_WAREHOUSE', N'L-NH01', N'180504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'18', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e41945a8344c4f5fa58395f1359a9328', N'NH_WAREHOUSE', N'L-NH01', N'180604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'18', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'1250452b114b4b48a7b4a990fce2eae3', N'NH_WAREHOUSE', N'L-NH01', N'190104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'19', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0c954c7beee142a5846caa3e70e5eecd', N'NH_WAREHOUSE', N'L-NH01', N'190204', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'19', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'5f696ace7fda4147903a29224a7ce336', N'NH_WAREHOUSE', N'L-NH01', N'190304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'19', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'22bef39a8cf842c18dcd96c26aa328f9', N'NH_WAREHOUSE', N'L-NH01', N'190404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'19', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'265adcb0ce2347adaba2a6a149c74ce8', N'NH_WAREHOUSE', N'L-NH01', N'190504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'19', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'69fcfbd30eda44d8bf607d8b60264df4', N'NH_WAREHOUSE', N'L-NH01', N'190604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'19', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'fd6744ad17d24a2080c99a1837c36e50', N'NH_WAREHOUSE', N'L-NH01', N'200104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'20', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'4daead4ef9d14ee6b49f8535aa833c61', N'NH_WAREHOUSE', N'L-NH01', N'200204', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'20', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'82d37854aa2b42a8b4be6c1c1f573cab', N'NH_WAREHOUSE', N'L-NH01', N'200304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'20', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'6ae5a0b5315d4783bfd87aacb6905a52', N'NH_WAREHOUSE', N'L-NH01', N'200404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'20', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'de730567c3f04c42a01f9754cf7203c3', N'NH_WAREHOUSE', N'L-NH01', N'200504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'20', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a4cc1d0d59bb423d8b2dc0ddb353098c', N'NH_WAREHOUSE', N'L-NH01', N'200604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'20', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2a5893cf4e8b4990be8c7d59994f38b9', N'NH_WAREHOUSE', N'L-NH01', N'210104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'21', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'eda1f09e989e4fc398f278448319c9db', N'NH_WAREHOUSE', N'L-NH01', N'210204', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'21', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'db5137265dce425ba2e1a4ad5ba572cf', N'NH_WAREHOUSE', N'L-NH01', N'210304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'21', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'89a2ffe6ef504b85bb1fd4ef8b180e54', N'NH_WAREHOUSE', N'L-NH01', N'210404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'21', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'332320ea1c8444fba8ba202cb9ff733d', N'NH_WAREHOUSE', N'L-NH01', N'210504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'21', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'a6a3828e3c1d4ade88619dc3062c52e0', N'NH_WAREHOUSE', N'L-NH01', N'210604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'21', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f523ed17e03847d7b1e89b016b5ff77c', N'NH_WAREHOUSE', N'L-NH01', N'220104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'22', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'c2d9d5dd848548bba31420ff02c17169', N'NH_WAREHOUSE', N'L-NH01', N'220204', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'22', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'2f28fef262294323b3b3b60d36e19590', N'NH_WAREHOUSE', N'L-NH01', N'220304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'22', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'77e53c8dce86406e92c32986b509b2e9', N'NH_WAREHOUSE', N'L-NH01', N'220404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'22', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd1a4d7f8870b455192fc24e10fe971ac', N'NH_WAREHOUSE', N'L-NH01', N'220504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'22', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd9f411ea5608404a84864651191b66d6', N'NH_WAREHOUSE', N'L-NH01', N'220604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'22', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd8e3c4901e644061ae86fd33bcf62871', N'NH_WAREHOUSE', N'L-NH01', N'230104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'23', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'518c0a7fb741401a8ada891c60f4a026', N'NH_WAREHOUSE', N'L-NH01', N'230204', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'23', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8d99e30dffc64c3c9c52e46684989a6e', N'NH_WAREHOUSE', N'L-NH01', N'230304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'23', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'c905269bd5ca4f9c89412f423b0ed317', N'NH_WAREHOUSE', N'L-NH01', N'230404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'23', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'45939b0069154dcc9bbcf5766f915aab', N'NH_WAREHOUSE', N'L-NH01', N'230504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'23', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'da523a3db8d449aca792baaddf42ea89', N'NH_WAREHOUSE', N'L-NH01', N'230604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'23', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'e0e836fc379d4b05a0bac4beb2094bf2', N'NH_WAREHOUSE', N'L-NH01', N'240104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'24', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'c635cd8b5ac04afa95adfaecbdd50a18', N'NH_WAREHOUSE', N'L-NH01', N'240204', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'24', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'60d41d4970e541cc90d91cc9f23665f4', N'NH_WAREHOUSE', N'L-NH01', N'240304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'24', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b7258d03f68d4b5995e5161adb219072', N'NH_WAREHOUSE', N'L-NH01', N'240404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'24', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'b852a3478c404f809dbf82179c83c21d', N'NH_WAREHOUSE', N'L-NH01', N'240504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'24', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'f45399070cfd4554b036bfb7d500414a', N'NH_WAREHOUSE', N'L-NH01', N'240604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'24', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'72ebf6b8a91b4929aaef58fc0d445bb4', N'NH_WAREHOUSE', N'L-NH01', N'250104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'25', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'8c135d9ced92497b9bf35a78ff28f70a', N'NH_WAREHOUSE', N'L-NH01', N'250204', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'25', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'01f87a77997545118b81df1fcc386246', N'NH_WAREHOUSE', N'L-NH01', N'250304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'25', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'52d829d6979744dea0531b1207b4d05a', N'NH_WAREHOUSE', N'L-NH01', N'250404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'25', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'79f2ff6b78ba45e7a1c1b92a14ce3c32', N'NH_WAREHOUSE', N'L-NH01', N'250504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'25', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'84e730df421247708f839c55cf0de9de', N'NH_WAREHOUSE', N'L-NH01', N'250604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'25', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'24c56f8d2cbc439ebd5760339a189076', N'NH_WAREHOUSE', N'L-NH01', N'260104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'26', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'55eee1afc3314928b563942ba44b252a', N'NH_WAREHOUSE', N'L-NH01', N'260204', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'26', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0293a07c43944791a565cefb4eea4f4d', N'NH_WAREHOUSE', N'L-NH01', N'260304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'26', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'298d198acf17452dba8649c2137ce926', N'NH_WAREHOUSE', N'L-NH01', N'260404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'26', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'd55ce76dcf6244b3ae04229036f7c174', N'NH_WAREHOUSE', N'L-NH01', N'260504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'26', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'95f315d2a1e2496981ce5ace250f86d1', N'NH_WAREHOUSE', N'L-NH01', N'260604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'26', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'324eadd1dc1f4218a7bf974c257ebc38', N'NH_WAREHOUSE', N'L-NH01', N'270104', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'27', N'1', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'42f94995da7f4c88ad0c430307567e64', N'NH_WAREHOUSE', N'L-NH01', N'270204', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'27', N'2', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'92ecf6ff63f74fba9c26a62fa5f26e14', N'NH_WAREHOUSE', N'L-NH01', N'270304', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'27', N'3', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'244a8a9451584a66b5dd8811f0ea6a1c', N'NH_WAREHOUSE', N'L-NH01', N'270404', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'27', N'4', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'0be1840e9bfd4a0eb622bc371bf118fe', N'NH_WAREHOUSE', N'L-NH01', N'270504', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'27', N'5', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')
INSERT INTO [dbo].[wms_location_stereoscopic]  VALUES (N'ff2df84c7c424a658020f075c5e0ed9c', N'NH_WAREHOUSE', N'L-NH01', N'270604', N'', N'立库', N'0', NULL, N'0', N'0', N'0', N'4', NULL, N'27', N'6', NULL, NULL, NULL, NULL, NULL, N'管理员', N'2022-10-06 15:19:52.940', NULL, NULL, N'1')

-- ----------------------------
-- Table structure for wms_move_log
-- ----------------------------
CREATE TABLE wms_move_log  (
  move_log_id varchar(64) NOT NULL,
  pallet_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  amount int DEFAULT NULL  ,
  batch_no varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  warehouse_code varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  area_code varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  from_location_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  to_location_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  move_result char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  error_msg varchar(512)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1' 
) 

-- ----------------------------
-- Table structure for wms_move_stereoscopic
-- ----------------------------
CREATE TABLE wms_move_stereoscopic  (
  move_id varchar(64) NOT NULL,
  move_no varchar(64)  COLLATE Chinese_PRC_CI_AS NULL,
  move_status char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  area_code varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  pallet_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_name varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  amount int DEFAULT NULL  ,
  batch_no varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  from_location_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  to_location_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  out_channel_location_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  in_channel_location_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  move_result char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  error_msg varchar(512)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'
)
-- ----------------------------
-- Table structure for wms_order_check
-- ----------------------------
CREATE TABLE wms_order_check  (
  check_id varchar(64) NOT NULL DEFAULT '0',
  order_no varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_type varchar(20)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_status char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  area_code varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  diff_flag char(1) DEFAULT '0'  ,
  goods_name varchar(1024)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(1024)  COLLATE Chinese_PRC_CI_AS NULL  ,
  location_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  remark varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1' 
) 

-- ----------------------------
-- Table structure for wms_order_check_pallet
-- ----------------------------
CREATE TABLE wms_order_check_pallet  (
  check_pallet_id int  identity(1,1) ,
  check_id varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_no varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  location_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  diff_flag char(1) DEFAULT '0'  ,
  batch_no varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  pallet_code varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  amount int DEFAULT NULL  ,
  real_amount int DEFAULT NULL  ,
  remark varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64) DEFAULT '0'  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1' 
) 

-- ----------------------------
-- Table structure for wms_order_check_pallet_box_barcode
-- ----------------------------
CREATE TABLE wms_order_check_pallet_box_barcode  (
  check_pallet_box_barcode_id int NOT NULL ,
  order_no varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  diff_flag char(1) DEFAULT '1'  ,
  pallet_code varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  box_barcode varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  real_box_barcode varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  remark varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
) 

-- ----------------------------
-- Table structure for wms_order_cross_in
-- ----------------------------
CREATE TABLE wms_order_cross_in  (
  order_cross_in_id varchar(64)  COLLATE Chinese_PRC_CI_AS NULL,
  order_no varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_type varchar(20)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_status char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  remark varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
)

-- ----------------------------
-- Table structure for wms_order_cross_in_deail
-- ----------------------------
CREATE TABLE wms_order_cross_in_deail  (
  order_cross_in_detail_id varchar(64) NOT NULL DEFAULT '0'  ,
  order_cross_in_id varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_no varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  plan_amount int DEFAULT 0  ,
  real_amount int DEFAULT 0  ,
  remark varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1' 
) 

-- ----------------------------
-- Table structure for wms_order_cross_product
-- ----------------------------
CREATE TABLE wms_order_cross_product  (
  order_cross_product_id varchar(64) NOT NULL,
  order_no varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_type varchar(20)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_status char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  out_address varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  remark varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1' 
) 

-- ----------------------------
-- Table structure for wms_order_cross_product_deail
-- ----------------------------
CREATE TABLE wms_order_cross_product_deail  (
  order_cross_product_detail_id varchar(64)  COLLATE Chinese_PRC_CI_AS NULL,
  order_cross_product_id varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_no varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  batch_no varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  plan_amount int DEFAULT NULL  ,
  real_amount int DEFAULT NULL  ,
  remark varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1' 
) 

-- ----------------------------
-- Table structure for wms_order_in
-- ----------------------------
CREATE TABLE wms_order_in  (
  order_in_id varchar(64) NOT NULL DEFAULT '0',
  order_no varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_item varchar(64)  COLLATE Chinese_PRC_CI_AS NULL,
  order_type varchar(20)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_status char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  plan_amount int DEFAULT NULL  ,
  real_amount int DEFAULT 0  ,
  remark varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1' 
) 
-- ----------------------------
-- Table structure for wms_order_out_plane
-- ----------------------------
CREATE TABLE wms_order_out_plane  (
  order_out_id varchar(64)  COLLATE Chinese_PRC_CI_AS NULL,
  order_no varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_type varchar(20)  COLLATE Chinese_PRC_CI_AS NULL ,
  order_status char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  cancel_flag char(255) DEFAULT '0'  ,
  remark varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
) 
-- ----------------------------
-- Table structure for wms_order_out_plane_deail
-- ----------------------------
CREATE TABLE wms_order_out_plane_deail  (
  order_out_detail_id varchar(64)  COLLATE Chinese_PRC_CI_AS NULL,
  order_out_id varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_no varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  batch_no varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  location_code varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  location_name varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  plan_amount int DEFAULT NULL  ,
  real_amount int DEFAULT NULL  ,
  remark varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
)
-- ----------------------------
-- Table structure for wms_order_out_stereoscopic
-- ----------------------------
CREATE TABLE wms_order_out_stereoscopic  (
  order_out_id varchar(64)  COLLATE Chinese_PRC_CI_AS NULL,
  order_no varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_type varchar(20)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_status char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  out_address varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  remark varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_run datetime DEFAULT NULL  ,
  gmt_over datetime DEFAULT NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
) 
-- ----------------------------
-- Table structure for wms_order_out_stereoscopic_deail
-- ----------------------------
CREATE TABLE wms_order_out_stereoscopic_deail  (
  order_out_detail_id varchar(64)  COLLATE Chinese_PRC_CI_AS NULL,
  order_out_id varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  order_no varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  batch_no varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  plan_amount int DEFAULT NULL  ,
  real_amount int DEFAULT NULL  ,
  plan_pallet_amount int DEFAULT NULL  ,
  real_pallet_amount int DEFAULT NULL  ,
  remark varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1' 
) 
-- ----------------------------
-- Table structure for wms_out_temporary
-- ----------------------------
CREATE TABLE wms_out_temporary  (
  id varchar(64) NOT NULL DEFAULT '0',
  goods_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  amount int DEFAULT 0  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
) 

-- ----------------------------
-- Table structure for wms_out_temporary_box_barcode
-- ----------------------------
CREATE TABLE wms_out_temporary_box_barcode  (
  id varchar(64) NOT NULL,
  order_no varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  send_no varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  batch_no varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  box_barcode varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
) 
-- ----------------------------
-- Table structure for wms_pallet
-- ----------------------------
CREATE TABLE wms_pallet  (
  pallet_id varchar(64) NOT NULL,
  pallet_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  amount int DEFAULT NULL  ,
  batch_no varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  warehouse_code varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  area_code varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  location_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  lock_by varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  channel_location varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1' 
) 

-- ----------------------------
-- Records of wms_pallet
-- ----------------------------
INSERT INTO wms_pallet VALUES ('00087d4a23a24ea992741ffeb9787076', 'AA1564', NULL, NULL, NULL, 'NH_WAREHOUSE', 'L-NH01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'wcs', '2020-10-04 15:41:08', 'wcs', '2020-11-04 10:55:51', '1');

-- ----------------------------
-- Table structure for wms_plane_location
-- ----------------------------
CREATE TABLE wms_plane_location  (
  location_id int  NOT NULL,
  warehouse_code varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  area_code varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  location_code varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  location_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  remark varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1' 
) 
-- ----------------------------
-- Table structure for wms_platform
-- ----------------------------
CREATE TABLE wms_platform  (
  platform_id varchar(64) NOT NULL,
  platform_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  platform_name varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  sys_address varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  warehouse_code varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1' 
) 

-- ----------------------------
-- Records of wms_platform
-- ----------------------------
INSERT INTO wms_platform VALUES ('89e6514222a5467099f4daaa8d5d539b', 'platform1', '1号月台', '', 'NH_WAREHOUSE', NULL, NULL, NULL, NULL, NULL, '管理员', '2020-03-05 14:28:32', NULL, NULL, '1');
INSERT INTO wms_platform VALUES ('f77371f93dd345fd9e87f7e549f09757', 'platform2', '2号月台', '', 'NH_WAREHOUSE', NULL, NULL, NULL, NULL, NULL, '管理员', '2020-03-05 14:28:44', NULL, NULL, '1');
-- ----------------------------
-- Table structure for wms_send_log
-- ----------------------------
CREATE TABLE wms_send_log  (
  send_id varchar(64) NOT NULL,
  send_no varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  has_box_code char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_name varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  send_amount int DEFAULT NULL  ,
  warehouse_code varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
) 
-- ----------------------------
-- Table structure for wms_storage_plane
-- ----------------------------
CREATE TABLE wms_storage_plane  (
  storage_plane_id varchar(64)  COLLATE Chinese_PRC_CI_AS NULL,
  warehouse_code varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  area_code varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  location_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  location_attr varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  location_desc varchar(300)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  batch_no varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  available_amount int NOT NULL  ,
  lock_amount int NOT NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1' 
) 

-- ----------------------------
-- Table structure for wms_supplier
-- ----------------------------
CREATE TABLE wms_supplier  (
  supplier_id varchar(64)  COLLATE Chinese_PRC_CI_AS NULL,
  supplier_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  supplier_full_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  supplier_short_name varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  supplier_eng_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  supplier_type varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  supplier_level varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  first_contract_date datetime DEFAULT NULL  ,
  end_contract_date datetime DEFAULT NULL  ,
  address varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  contacts varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  website varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  registration_date datetime DEFAULT NULL  ,
  remark varchar(128)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
) 
-- ----------------------------
-- Table structure for wms_task_execution_log
-- ----------------------------
CREATE TABLE wms_task_execution_log  (
  id int NOT NULL identity(1,1) ,
  task_id bigint DEFAULT NULL  ,
  order_no varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  task_type char(2)  COLLATE Chinese_PRC_CI_AS NULL  ,
  task_status char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  error_msg varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  in_address varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  out_address varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  goods_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  batch_no varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  warehouse_code varchar(255)  COLLATE Chinese_PRC_CI_AS NULL  ,
  area_code varchar(100)  COLLATE Chinese_PRC_CI_AS NULL  ,
  location_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  pallet_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
)
-- ----------------------------
-- Table structure for wms_warehouse
-- ----------------------------
CREATE TABLE wms_warehouse  (
  warehouse_id varchar(64) NOT NULL,
  warehouse_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL,
  warehouse_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL,
  warehouse_attr varchar(32)  COLLATE Chinese_PRC_CI_AS NULL,
  warehouse_addr varchar(128)  COLLATE Chinese_PRC_CI_AS NULL,
  warehouse_capacity int DEFAULT NULL,
  factory_name varchar(100) NOT NULL DEFAULT '',
  address varchar(100) NOT NULL DEFAULT '' ,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'  
) 

-- ----------------------------
-- Records of wms_warehouse
-- ----------------------------
INSERT INTO wms_warehouse VALUES ('4dd90393d4a74e1ea811ec02377c75ad', '诺华立体库', 'NH_WAREHOUSE', 'sp', '', 2280, '', '', NULL, NULL, NULL, NULL, NULL, '管理员', '2020-02-21 14:18:16', '管理员', '2020-09-14 15:31:19', '1');

-- ----------------------------
-- Table structure for wms_warehouse_area
-- ----------------------------
CREATE TABLE wms_warehouse_area  (
  area_id varchar(64) NOT NULL,
  warehouse_code varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  is_root char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  area_name varchar(100)  COLLATE Chinese_PRC_CI_AS NULL,
  area_code varchar(32)  COLLATE Chinese_PRC_CI_AS NULL,
  area_type char(1)  COLLATE Chinese_PRC_CI_AS NULL  ,
  area_attr varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  max_capacity int DEFAULT NULL  ,
  area_desc varchar(100) DEFAULT   NULL,
  user_defined1 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined2 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined3 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined4 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  user_defined5 varchar(64)  COLLATE Chinese_PRC_CI_AS NULL  ,
  create_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_create datetime DEFAULT (GETDATE())  ,
  last_modified_by varchar(32)  COLLATE Chinese_PRC_CI_AS NULL  ,
  gmt_modified datetime DEFAULT (GETDATE())  ,
  active_flag char(1) DEFAULT '1'
) 

-- ----------------------------
-- Records of wms_warehouse_area
-- ----------------------------
INSERT INTO wms_warehouse_area VALUES ('6d451c781df7423d918fdb8b836a72fb', 'NH_WAREHOUSE', '1', '立库库区', 'L-NH01', '1', 'normal', 8000, '诺华立库', NULL, NULL, NULL, NULL, NULL, 'ooo', '2020-02-10 16:33:23', '管理员', '2020-04-07 13:17:00', '1');
