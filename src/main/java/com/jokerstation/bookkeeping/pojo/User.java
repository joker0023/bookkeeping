package com.jokerstation.bookkeeping.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import lombok.Data;

@Data
public class User implements Serializable {

	private static final long serialVersionUID = -9167670728858097139L;
	
	@Id
	private Long id;
	
	private String nick;
	
	private String name;
	
	private String avatar;
	
	private String loginId;
	
	private String remark;
	
	private String phone;
	
	private Byte type;
	
	private Byte status;
	
	private Double balance;
	
	private Date created;
	
}
