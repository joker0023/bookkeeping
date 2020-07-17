package com.jokerstation.bookkeeping.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jokerstation.bookkeeping.pojo.User;
import com.jokerstation.bookkeeping.service.AdminService;
import com.jokerstation.bookkeeping.service.AppService;
import com.jokerstation.bookkeeping.vo.ShopVo;
import com.jokerstation.common.data.ResultModel;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	@RequestMapping("/cleanToken")
	public ResultModel cleanToken() {
		AppService.cleanToken();
		return new ResultModel();
	}
	
	@RequestMapping("/listAllSimpleUser")
	public ResultModel listAllSimpleUser() {
		List<User> allUser = adminService.listAllUser();
		List<User> simpleUserList = new ArrayList<User>();
		for (User user : allUser) {
			User u = new User();
			u.setId(user.getId());
			u.setNick(user.getNick());
			u.setAvatar(user.getAvatar());
			u.setPhone(user.getPhone());
			simpleUserList.add(u);
		}
		return new ResultModel(simpleUserList);
	}
	
	@RequestMapping("/listAllShop")
	public List<ShopVo> listAllShop() {
		return adminService.listAllShop();
	}
	
	@RequestMapping(path="/initShop", method=RequestMethod.POST)
	public ResultModel initShop(Long userId, String shopName) {
		if (null == shopName || null == userId) {
			return new ResultModel("-1", "params illegal");
		}
		adminService.initShop(userId, shopName);
		return new ResultModel();
	}
}
