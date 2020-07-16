package com.jokerstation.bookkeeping.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jokerstation.bookkeeping.interceptor.ConsoleInterceptor;
import com.jokerstation.bookkeeping.pojo.Bill;
import com.jokerstation.bookkeeping.pojo.RelSeller;
import com.jokerstation.bookkeeping.pojo.Shop;
import com.jokerstation.bookkeeping.pojo.User;
import com.jokerstation.bookkeeping.service.ConsoleService;
import com.jokerstation.common.data.ResultModel;
import com.jokerstation.common.util.JsonUtils;

@RestController
@RequestMapping("/console")
public class ConsoleController {
	
	private static Logger logger = LoggerFactory.getLogger(ConsoleController.class);
	
	@Autowired
	private ConsoleService consoleService;
	
	@RequestMapping("/getCurrentUser")
	public ResultModel getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
		Long userId = ConsoleInterceptor.getConsoleUserId(request);
		User consoleUser = consoleService.getUser(userId);
		List<RelSeller> relSellers = consoleService.listRelSellers(userId);
		List<Shop> shopList = consoleService.listShops(relSellers);
		Set<Long> shopIds = shopList.stream().map(Shop::getId).collect(Collectors.toSet());
		consoleService.resetShopIds(shopIds);
		
		Map<Long, Byte> shopRoleMap = relSellers.stream().collect(Collectors.toMap(RelSeller::getShopId, RelSeller::getRole));
		
		Map<String, Object> data = new HashMap<>();
		data.put("consoleUser", consoleUser);
		data.put("shopList", shopList);
		data.put("shopRoleMap", shopRoleMap);
		return new ResultModel(data);
	}
	
	@RequestMapping("/listBills")
	public ResultModel listBills(Long userId, Long shopId, int page, int size) {
		PageInfo<Bill> bills = consoleService.listBills(userId, shopId, page, size);
		if (null == bills) {
			return new ResultModel("-1", "非法请求");
		}
		return new ResultModel(bills);
	}
	
	@RequestMapping("/recharge")
	public ResultModel recharge(@RequestBody Bill billvo) {
		boolean result = consoleService.recharge(billvo);
		if (!result) {
			logger.warn("非法请求: " + JsonUtils.toJson(billvo));
			return new ResultModel("-1", "非法请求");
		}
		
		return new ResultModel();
	}
	
	@RequestMapping("/consume")
	public ResultModel consume(@RequestBody Bill billvo) {
		boolean result = consoleService.consume(billvo);
		if (!result) {
			logger.warn("非法请求: " + JsonUtils.toJson(billvo));
			return new ResultModel("-1", "非法请求");
		}
		
		return new ResultModel();
	}
}
