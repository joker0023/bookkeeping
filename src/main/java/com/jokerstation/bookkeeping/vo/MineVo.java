package com.jokerstation.bookkeeping.vo;

import java.util.List;

import com.jokerstation.bookkeeping.pojo.Shop;
import com.jokerstation.bookkeeping.pojo.User;

import lombok.Data;

@Data
public class MineVo {

	private User user;
	
	private List<Shop> buyerShops;
	
	private List<Shop> sellerShops;
}
