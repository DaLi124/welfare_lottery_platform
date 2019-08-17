ALTER TABLE "BLADEX"."BLADE_USER"
    ADD ("AVATAR" VARCHAR2(500) );

COMMENT ON COLUMN "BLADEX"."BLADE_USER"."AVATAR" IS '头像'


CREATE TABLE "BLADE_DATASOURCE" (
"ID" NUMBER(20) NOT NULL ,
"NAME" NVARCHAR2(100) ,
"DRIVER_CLASS" NVARCHAR2(100) ,
"URL" NVARCHAR2(500) ,
"USERNAME" NVARCHAR2(45) ,
"PASSWORD" NVARCHAR2(45) ,
"REMARK" NVARCHAR2(500) ,
"CREATE_USER" NUMBER(20) ,
"CREATE_DEPT" NUMBER(20) ,
"CREATE_TIME" DATE ,
"UPDATE_USER" NUMBER(20) ,
"UPDATE_TIME" DATE ,
"STATUS" NUMBER(11) ,
"IS_DELETED" NUMBER(11),
PRIMARY KEY ("ID")
);
COMMENT ON COLUMN "BLADE_DATASOURCE"."ID" IS '主键';
COMMENT ON COLUMN "BLADE_DATASOURCE"."NAME" IS '名称';
COMMENT ON COLUMN "BLADE_DATASOURCE"."DRIVER_CLASS" IS '驱动类';
COMMENT ON COLUMN "BLADE_DATASOURCE"."URL" IS '链接地址';
COMMENT ON COLUMN "BLADE_DATASOURCE"."USERNAME" IS '用户名';
COMMENT ON COLUMN "BLADE_DATASOURCE"."PASSWORD" IS '密码';
COMMENT ON COLUMN "BLADE_DATASOURCE"."REMARK" IS '备注';
COMMENT ON COLUMN "BLADE_DATASOURCE"."CREATE_USER" IS '创建人';
COMMENT ON COLUMN "BLADE_DATASOURCE"."CREATE_DEPT" IS '创建部门';
COMMENT ON COLUMN "BLADE_DATASOURCE"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "BLADE_DATASOURCE"."UPDATE_USER" IS '修改人';
COMMENT ON COLUMN "BLADE_DATASOURCE"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "BLADE_DATASOURCE"."STATUS" IS '状态';
COMMENT ON COLUMN "BLADE_DATASOURCE"."IS_DELETED" IS '是否已删除';
COMMENT ON TABLE "BLADE_DATASOURCE" IS '数据源配置表';

-- ----------------------------
-- 插入数据源默认数据
-- ----------------------------
INSERT INTO "BLADEX"."BLADE_DATASOURCE"("ID", "NAME", "DRIVER_CLASS", "URL", "USERNAME", "PASSWORD", "REMARK", "CREATE_USER", "CREATE_DEPT", "CREATE_TIME", "UPDATE_USER", "UPDATE_TIME", "STATUS", "IS_DELETED")
VALUES (1161483357481541634, 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3306/bladex?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true', 'root', 'root', 'mysql', 1123598821738675201, 1123598813738675201, TO_DATE('2019-08-14 11:43:06', 'SYYYY-MM-DD HH24:MI:SS'), 1123598821738675201, TO_DATE('2019-08-14 11:43:06', 'SYYYY-MM-DD HH24:MI:SS'), 1, 0);
INSERT INTO "BLADEX"."BLADE_DATASOURCE"("ID", "NAME", "DRIVER_CLASS", "URL", "USERNAME", "PASSWORD", "REMARK", "CREATE_USER", "CREATE_DEPT", "CREATE_TIME", "UPDATE_USER", "UPDATE_TIME", "STATUS", "IS_DELETED")
VALUES (1161483504353484802, 'postgresql', 'org.postgresql.Driver', 'jdbc:postgresql://127.0.0.1:5432/bladex', 'postgres', '123456', 'postgresql', 1123598821738675201, 1123598813738675201, TO_DATE('2019-08-14 11:43:41', 'SYYYY-MM-DD HH24:MI:SS'), 1123598821738675201, TO_DATE('2019-08-14 11:43:41', 'SYYYY-MM-DD HH24:MI:SS'), 1, 0);
INSERT INTO "BLADEX"."BLADE_DATASOURCE"("ID", "NAME", "DRIVER_CLASS", "URL", "USERNAME", "PASSWORD", "REMARK", "CREATE_USER", "CREATE_DEPT", "CREATE_TIME", "UPDATE_USER", "UPDATE_TIME", "STATUS", "IS_DELETED")
VALUES (1161483594023510018, 'oracle', 'oracle.jdbc.OracleDriver', 'jdbc:oracle:thin:@127.0.0.1:49161:orcl', 'BLADEX', 'bladex', 'oracle', 1123598821738675201, 1123598813738675201, TO_DATE('2019-08-14 11:44:03', 'SYYYY-MM-DD HH24:MI:SS'), 1123598821738675201, TO_DATE('2019-08-14 11:44:03', 'SYYYY-MM-DD HH24:MI:SS'), 1, 0);

-- ----------------------------
-- 插入数据源菜单数据
-- ----------------------------
INSERT INTO "BLADEX"."BLADE_MENU"("ID", "PARENT_ID", "CODE", "NAME", "ALIAS", "PATH", "SOURCE", "SORT", "CATEGORY", "ACTION", "IS_OPEN", "REMARK", "IS_DELETED")
VALUES ('1161272593873321991', '1123598815738675217', 'datasource', '数据源管理', 'menu', '/tool/datasource', 'iconfont icon-caidanguanli', 2, 1, 0, 1, NULL, 0);
INSERT INTO "BLADEX"."BLADE_MENU"("ID", "PARENT_ID", "CODE", "NAME", "ALIAS", "PATH", "SOURCE", "SORT", "CATEGORY", "ACTION", "IS_OPEN", "REMARK", "IS_DELETED")
VALUES ('1161272593873321992', '1161272593873321991', 'datasource_add', '新增', 'add', '/tool/datasource/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO "BLADEX"."BLADE_MENU"("ID", "PARENT_ID", "CODE", "NAME", "ALIAS", "PATH", "SOURCE", "SORT", "CATEGORY", "ACTION", "IS_OPEN", "REMARK", "IS_DELETED")
VALUES ('1161272593873321993', '1161272593873321991', 'datasource_edit', '修改', 'edit', '/tool/datasource/edit', 'form', 2, 2, 2, 2, NULL, 0);
INSERT INTO "BLADEX"."BLADE_MENU"("ID", "PARENT_ID", "CODE", "NAME", "ALIAS", "PATH", "SOURCE", "SORT", "CATEGORY", "ACTION", "IS_OPEN", "REMARK", "IS_DELETED")
VALUES ('1161272593873321994', '1161272593873321991', 'datasource_delete', '删除', 'delete', '/api/blade-develop/datasource/remove', 'delete', 3, 2, 3, 3, NULL, 0);
INSERT INTO "BLADEX"."BLADE_MENU"("ID", "PARENT_ID", "CODE", "NAME", "ALIAS", "PATH", "SOURCE", "SORT", "CATEGORY", "ACTION", "IS_OPEN", "REMARK", "IS_DELETED")
VALUES ('1161272593873321995', '1161272593873321991', 'datasource_view', '查看', 'view', '/tool/datasource/view', 'file-text', 4, 2, 2, 2, NULL, 0);


-- ----------------------------
-- 插入数据源角色权限数据
-- ----------------------------
INSERT INTO "BLADEX"."BLADE_ROLE_MENU"(ID,MENU_ID,ROLE_ID)
VALUES ('1161272593873322991', '1161272593873321991', '1123598816738675201');
INSERT INTO "BLADEX"."BLADE_ROLE_MENU"(ID,MENU_ID,ROLE_ID)
VALUES ('1161272593873322992', '1161272593873321992', '1123598816738675201');
INSERT INTO "BLADEX"."BLADE_ROLE_MENU"(ID,MENU_ID,ROLE_ID)
VALUES ('1161272593873322993', '1161272593873321993', '1123598816738675201');
INSERT INTO "BLADEX"."BLADE_ROLE_MENU"(ID,MENU_ID,ROLE_ID)
VALUES ('1161272593873322994', '1161272593873321994', '1123598816738675201');
INSERT INTO "BLADEX"."BLADE_ROLE_MENU"(ID,MENU_ID,ROLE_ID)
VALUES ('1161272593873322995', '1161272593873321995', '1123598816738675201');


-- ----------------------------
-- 代码生成表增加数据源字段
-- ----------------------------
ALTER TABLE "BLADEX"."BLADE_CODE"
    ADD ("DATASOURCE_ID" NUMBER(20) )
    ADD ("BASE_MODE" NUMBER(11) )
    ADD ("WRAP_MODE" NUMBER(11) );

COMMENT ON COLUMN "BLADE_CODE"."DATASOURCE_ID" IS '数据源主键';
COMMENT ON COLUMN "BLADE_CODE"."BASE_MODE" IS '基础业务模式';
COMMENT ON COLUMN "BLADE_CODE"."WRAP_MODE" IS '包装器模式';


-- ----------------------------
-- 代码生成记录增加数据源字段
-- ----------------------------
UPDATE "BLADEX"."BLADE_CODE" SET "DATASOURCE_ID" = 1161483357481541634, "BASE_MODE" = 1, "WRAP_MODE" = 1 WHERE "ID" = 1123598812738675201;
