# 创建自己的视图  通过构建原先表的结构 但是数据来源于主系统 该方法同样适用多系统不同的用户组


-- 用户表
CREATE VIEW ACT_ID_USER AS SELECT SU.ID AS ID_,1 AS REV_,SU.REALNAME AS
                                                FIRST_,
                                  concat('',SU.ID) AS LAST_ ,'000000' AS PWD_ , SU.EMAIL AS EMAIL_,
                                  SU.AVATAR AS PICTURE_ID_
                           FROM SYS_USER AS SU ;
-- 分组表
CREATE VIEW ACT_ID_GROUP AS SELECT SR.ID AS ID_ , 1 AS REV_, SR.ROLE_NAME
                                              AS NAME_,
                                   CASE WHEN SR.ROLE_CODE='root' then 'admin' else 'ordinary' end as TYPE_ FROM
                                SYS_ROLE AS SR;
-- 关系表
CREATE VIEW ACT_ID_MEMBERSHIP AS SELECT SUR.USER_ID AS USER_ID_,
                                        SUR.ROLE_ID AS GROUP_ID_ FROM SYS_USER_ROLE AS SUR;
