package com.jokerstation.bookkeeping.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import lombok.Data;

@Data
public class Shop implements Serializable {

	private static final long serialVersionUID = -7879312032660722237L;

	@Id
	private Long id;
	
	private String name;
	
	private String address;

	private String img;
	
	private String remark;
	
	private Date created;
}
