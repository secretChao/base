package com.base.controller;

import com.base.utils.DataUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
@RestController
public class BaseAPIController {

	private static final Logger logger = LogManager.getLogger(BaseController.class);

	/**
	 * 首頁
	 *
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/", method= RequestMethod.GET)
	public List<Map> getData(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<Map> datas = new DataUtils().getData();
		System.out.println(datas);
		return datas;
	}

}
