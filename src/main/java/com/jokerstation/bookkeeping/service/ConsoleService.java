package com.jokerstation.bookkeeping.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jokerstation.bookkeeping.mapper.BillMapper;
import com.jokerstation.bookkeeping.mapper.RelUserHasShopMapper;
import com.jokerstation.bookkeeping.mapper.ShopMapper;
import com.jokerstation.bookkeeping.mapper.UserMapper;
import com.jokerstation.bookkeeping.pojo.Bill;
import com.jokerstation.bookkeeping.pojo.RelUserHasShop;
import com.jokerstation.bookkeeping.pojo.Shop;
import com.jokerstation.bookkeeping.pojo.User;

import tk.mybatis.mapper.entity.Example;

@Service
public class ConsoleService {

	@Autowired
	private BillService billService;
	
	@Autowired
	private RelUserHasShopMapper relUserHasShopMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ShopMapper shopMapper;
	
	private final static String SHOP_IDS = "shopIds";
	
	public List<Shop> listShops(Long userId) {
		RelUserHasShop record = new RelUserHasShop();
		record.setUserId(userId);
		List<RelUserHasShop> rels = relUserHasShopMapper.select(record);
		List<Long> shopIds = rels.stream().map(RelUserHasShop::getShopId).collect(Collectors.toList());
		if (shopIds.size() == 0) {
			return new ArrayList<Shop>();
		}
		
		Example example = new Example(Shop.class);
		example.createCriteria().andIn("id", shopIds);
		return shopMapper.selectByExample(example);
	}
	
	public PageInfo<Bill> listBills(Long userId, Long shopId, int page, int size) {
		if (!hasShop(shopId)) {
			return null;
		}
		return billService.listBills(userId, shopId, page, size);
	}
	
	@Transactional
	public boolean recharge(Bill billvo) {
		if (!hasShop(billvo.getShopId())) {
			return false;
		}
		
		User user = userMapper.selectByPrimaryKey(billvo.getUserId());
		if (null == user) {
			return false;
		}
		
		Bill bill = new Bill();
		bill.setCreated(new Date());
		bill.setDescript(billvo.getDescript());
		bill.setRate(billvo.getRate());
		bill.setRemark(billvo.getRemark());
		bill.setShopId(billvo.getShopId());
		bill.setType(Bill.TYPE_RECHANGE);
		bill.setUserId(billvo.getUserId());
		billService.insert(bill);
		
		userMapper.updateBalance(billvo.getUserId(), billvo.getRate());
		return true;
	}
	
	@Transactional
	public boolean consume(Bill billvo) {
		if (!hasShop(billvo.getShopId())) {
			return false;
		}
		
		User user = userMapper.selectByPrimaryKey(billvo.getUserId());
		if (null == user) {
			return false;
		}
		
		Bill bill = new Bill();
		bill.setCreated(new Date());
		bill.setDescript(billvo.getDescript());
		bill.setRate(billvo.getRate());
		bill.setRemark(billvo.getRemark());
		bill.setShopId(billvo.getShopId());
		bill.setType(Bill.TYPE_CONSUME);
		bill.setUserId(billvo.getUserId());
		billService.insert(bill);
		
		userMapper.updateBalance(billvo.getUserId(), -billvo.getRate());
		return true;
	}
	
	public void resetShopIds(Set<Long> shopIds) {
		getRequest().getSession().setAttribute(SHOP_IDS, shopIds);
	}
	
	@SuppressWarnings("unchecked")
	private boolean hasShop(Long shopId) {
		Set<Long> shopIds = (Set<Long>)getRequest().getSession().getAttribute(SHOP_IDS);
		if (null != shopIds && shopIds.contains(shopId)) {
			return true;
		}
		return false;
	}
	
	private HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
}
