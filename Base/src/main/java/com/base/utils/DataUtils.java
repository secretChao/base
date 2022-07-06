package com.base.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtils {
    public List<Map> getData() {
        Context ctx = null;
        DataSource ds;
        Connection con = null;
        List<Map> list = new ArrayList<>();
        try {
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/baseDB");
            con = ds.getConnection();

            String sql = "select * from Employee where EmployeeId > ? and LastName like ? order by LastName";
            try (
                    PreparedStatement statement = con.prepareStatement(sql);// 產生PreparedStatement並輸入sql
            ) {
                statement.setInt(1, 2); // 跟上面取得資料用法相同，但只能以?的index做輸入，且從1開始計算
                statement.setString(2, "%a%");
                ResultSet resultSet = statement.executeQuery();// statement.executeQuery()執行sql，並取得執行結果

                while (resultSet.next()) {
                    Map map = new HashMap();
                    map.put("EmployeeId", resultSet.getString("EmployeeId"));
                    map.put("LastName", resultSet.getString("LastName"));
                    map.put("FirstName", resultSet.getString("FirstName"));
                    map.put("Title", resultSet.getString("Title"));
                    map.put("HireDate", resultSet.getString("HireDate"));
                    list.add(map);
                }
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                ctx.close();
            } catch (SQLException e) {
                System.out.println("Exception in closing DB resources");
            } catch (NamingException e) {
                System.out.println("Exception in closing Context");
            }
        }

        return list;
    }
}
