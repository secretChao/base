package com.base.controller;

import com.base.utils.DataUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
		List<Map> datas = new DataUtils().getData();
		if (datas.size() > 0) {
			return "index";
		} else {
			throw new Exception("帳號或密碼錯誤");
		}

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



}
