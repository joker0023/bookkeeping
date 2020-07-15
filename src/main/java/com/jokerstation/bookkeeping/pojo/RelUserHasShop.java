package com.jokerstation.bookkeeping.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import lombok.Data;

@Data
public class RelUserHasShop implements Serializable {

	private static final long serialVersionUID = -5650670531872117351L;

	@Id
	private Long userId;
	
	@Id
	private Long shopId;
	
	private Date created;
}
