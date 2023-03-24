package com.penghaisoft.wcs.util;

import org.apache.poi.ss.formula.functions.T;

import java.sql.*;
import java.util.List;

public class JDBCUtil {
    private JDBCUtil() {

    }
    private static String driverClassName = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://47.105.177.120:35000/bj_wcs_his?characterEncoding=utf8&serverTimezone=GMT%2B8";
    private static String user = "admin";
    private static String password = "Admin@2020";
    //使用静态代码块实现 jdbcUtil的字节码被加载锦jvm中 并且只执行一次。避免每次使用都注册
    static{
        try {
            Class.forName(driverClassName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //提供外界访问
    public static Connection getConn(){
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //关闭连接
    public static void close(Connection conn,Statement st,ResultSet rs){
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
