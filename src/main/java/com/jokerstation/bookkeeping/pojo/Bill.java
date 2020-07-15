package com.jokerstation.bookkeeping.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import lombok.Data;

@Data
public class Bill implements Serializable {

	private static final long serialVersionUID = -7511421952971398073L;
	
	public static final Byte TYPE_RECHANGE = 1;	//充值
	public static final Byte TYPE_CONSUME = 2;	//消费

	@Id
	private Long id;
	
	private Long userId;
	
	private Long shopId;
	
	private Byte type;
	
	private Double rate;
	
	private String remark;
	
	private String descript;
	
	private Date created;
}
