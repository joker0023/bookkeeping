package com.jokerstation.bookkeeping.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import lombok.Data;

@Data
public class RelSeller implements Serializable {

	private static final long serialVersionUID = -5650670531872117351L;
	
	public static final byte role_owner = 1;	//店长
	public static final byte role_clerk = 2;	//店员

	@Id
	private Long userId;
	
	@Id
	private Long shopId;
	
	private Date created;
	
	private Byte role;
}
