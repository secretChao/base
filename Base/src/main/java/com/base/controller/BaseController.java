package com.base.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin(origins = { "http://localhost:3000" })
@Controller
public class BaseController {

	private static final Logger logger = LogManager.getLogger(BaseController.class);

	/**
	 * 首頁
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index", method= RequestMethod.GET)
	public String doIndex(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<Map> datas = getData();
		System.out.println(datas);
		return "index";
	}

	/**
	 * 錯誤頁
	 *
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/err")
	public String error(Model model) throws Exception {
		System.out.println(LocalDateTime.now());
		return "err";
	}

	private List<Map> getData(){
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
