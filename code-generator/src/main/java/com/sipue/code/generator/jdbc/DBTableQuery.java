package com.sipue.code.generator.jdbc;

import com.sipue.code.generator.config.model.Column;
import com.sipue.code.generator.config.model.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: db查询类
 *
 * @Author: wangjunyu
 * @Date: 2021/12/30 16:49
 */
public class DBTableQuery {

    //定义一个方法，查询user表的数据将其封装为对象，然后装载集合，返回。
    public static Table queryTable(Connection conn,String tableName){
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            //定义sql，先查询表信息
            String sql = "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables " +
                    " where table_schema = (select database()) and table_name = '"+tableName+"'";
            //获取执行sql的对象
            st = conn.prepareStatement(sql);
            //执行sql
            rs = st.executeQuery(sql);
            Table table = new Table();
            while (rs.next()){
                table.setComments(rs.getString("tableComment"));
                table.setTableName(rs.getString("tableName"));
            }
            queryTableColumns(conn,table,tableName);
            return table;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeResources(null,st,null);
        }
        return null;
    }


    //定义一个方法，查询user表的数据将其封装为对象，然后装载集合，返回。
    public static void queryTableColumns(Connection conn,Table table,String tableName){
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            //定义sql
            String sql = "select column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra from information_schema.columns " +
                    " where table_name = '"+tableName+"' and table_schema = (select database()) and column_name not in('deleted','creator','creator_biz_type','create_time','modifier','modifier_biz_type','modify_time') order by ordinal_position";
            //获取执行sql的对象
            st = conn.prepareStatement(sql);
            //执行sql
            rs = st.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            List<Column> list = new ArrayList<>();
            while (rs.next()){
                Column column = new Column();
                column.setColumnName(rs.getString("columnName"));
                column.setDataType(rs.getString("dataType"));
                column.setComments(rs.getString("columnComment"));
                column.setExtra(rs.getString("extra"));
                //是否主键
                if ("PRI".equalsIgnoreCase(rs.getString("columnKey")) && table.getPk() == null) {
                    table.setPk(column);
                }
                list.add(column);
            }
            table.setColumns(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeResources(null,st,null);
        }
    }
}
