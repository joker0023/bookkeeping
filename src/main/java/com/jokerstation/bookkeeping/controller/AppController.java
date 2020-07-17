package com.jokerstation.bookkeeping.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.pagehelper.PageInfo;
import com.jokerstation.bookkeeping.pojo.Bill;
import com.jokerstation.bookkeeping.pojo.User;
import com.jokerstation.bookkeeping.service.AppService;
import com.jokerstation.bookkeeping.service.WsServerEndpoint;
import com.jokerstation.bookkeeping.vo.MineVo;
import com.jokerstation.common.data.ResultModel;

@RestController
@RequestMapping("/app")
public class AppController {
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private WsServerEndpoint wsServerEndpoint;
	
	@RequestMapping(path="/loginScanOk", method=RequestMethod.POST)
	public ResultModel loginScanOk(String sid) throws Exception {
		String token = getToken();
		wsServerEndpoint.sendMessage(sid, token);
		return new ResultModel();
	}

	@RequestMapping("/getUser")
	public ResultModel getMineVo() {
		String token = getToken();
		MineVo mineVo = appService.getMineVo(token);
		return new ResultModel(mineVo);
	}
	
	@RequestMapping(path="/updateUser", method=RequestMethod.POST)
	public ResultModel updateUser(String nick, String avatar) {
		String token = getToken();
		User user = appService.updateUser(token, nick, avatar);
		return new ResultModel(user);
	}
	
	@RequestMapping(path="/toggleUserRole", method=RequestMethod.POST)
	public ResultModel toggleUserRole(byte role) {
		String token = getToken();
		User user = appService.toggleUserRole(token, role);
		return new ResultModel(user);
	}
	
	@RequestMapping("listBuyerBills")
	public ResultModel listBuyerBills(long shopId, int page, int size) {
		String token = getToken();
		PageInfo<Bill> pageInfo = appService.listBuyerBills(token, shopId, page, size);
		return new ResultModel(pageInfo);
	}
	
	@RequestMapping("listSellerBills")
	public ResultModel listSellerBills(long shopId, int page, int size) {
		String token = getToken();
		PageInfo<Bill> pageInfo = appService.listSellerBills(token, shopId, page, size);
		return new ResultModel(pageInfo);
	}
	
	private String getToken() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getHeader("token");
	}
	
}
