package com.jokerstation.bookkeeping.vo;

import lombok.Data;

@Data
public class TokenVo {

//	private User user;
	
	private String sessionKey;
	
	private String token;
	
	private Long timeMillis;
	
	private String openId;
}
