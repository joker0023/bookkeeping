package com.jokerstation.bookkeeping.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jokerstation.bookkeeping.mapper.RelSellerMapper;
import com.jokerstation.bookkeeping.mapper.ShopMapper;
import com.jokerstation.bookkeeping.mapper.UserMapper;
import com.jokerstation.bookkeeping.pojo.RelSeller;
import com.jokerstation.bookkeeping.pojo.Shop;
import com.jokerstation.bookkeeping.pojo.User;
import com.jokerstation.bookkeeping.vo.ShopVo;

@Service
public class AdminService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ShopMapper shopMapper;
	
	@Autowired
	private RelSellerMapper relSellerMapper;

	public List<User> listAllUser() {
		return userMapper.selectAll();
	}
	
	public List<ShopVo> listAllShop() {
		return shopMapper.listShopVos();
	}
	
	public void initShop(Long userId, String shopName) {
		Shop shop = new Shop();
		shop.setName(shopName);
		shop.setCreated(new Date());
		shopMapper.initShop(shop);
		System.out.println("shop id : " + shop.getId());
		
		RelSeller relSeller = new RelSeller();
		relSeller.setCreated(new Date());
		relSeller.setRole(RelSeller.role_owner);
		relSeller.setShopId(shop.getId());
		relSeller.setUserId(userId);
		relSellerMapper.insert(relSeller);
	}
}
