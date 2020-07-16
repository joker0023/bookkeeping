package com.jokerstation.bookkeeping.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import lombok.Data;

@Data
public class User implements Serializable {

	private static final long serialVersionUID = -9167670728858097139L;
	
	public static final byte DEFAULT_ROLE_BUYER = 1;	//会员
	public static final byte DEFAULT_ROLE_SELLER = 2;	//店家
	
	public static final byte STATUS_OK = 1; //正常
	
	@Id
	private Long id;
	
	private String nick;
	
	private String avatar;
	
	private String openId;
	
	private String remark;
	
	private String phone;
	
	private Byte status;
	
	private Double balance;
	
	private Byte defaultRole;
	
	private Date created;
	
}
