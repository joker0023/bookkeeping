package com.jokerstation.bookkeeping.vo;

import lombok.Data;

@Data
public class ShopVo {

	private Long shopId;
	
	private String shopName;
	
	private Long ownerId;
	
	private String ownerNick;
	
	private String ownerPhone;
}
