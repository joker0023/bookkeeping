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
	
	public static final byte TYPE_ADMIN = 1; //admin
	
	@Id
	private Long id;
	
	private String openId;
	
	private String nick;
	
	private String avatar;
	
	private String remark;
	
	private String phone;
	
	private Byte userStatus;
	
	private Byte defaultRole;
	
	//1:admin
	private Byte type;
	
	private Date created;
}
