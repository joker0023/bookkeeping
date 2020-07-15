package com.jokerstation.bookkeeping.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import lombok.Data;

@Data
public class RelShopHasUser implements Serializable {

	private static final long serialVersionUID = -6842159223841684760L;

	@Id
	private Long shopId;
	
	@Id
	private Long userId;
	
	private Date created;
}
