package com.jokerstation.bookkeeping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jokerstation.bookkeeping.mapper.BillMapper;
import com.jokerstation.bookkeeping.pojo.Bill;

@Service
public class BillService {
	
	@Autowired
	private BillMapper billMapper;

	public PageInfo<Bill> listBills(Long userId, Long shopId, int page, int size) {
		PageHelper.startPage(page, size);
		PageHelper.orderBy("created desc");
		Bill record = new Bill();
		record.setUserId(userId);
		record.setShopId(shopId);
		List<Bill> billList = billMapper.select(record);
		return new PageInfo<>(billList);
	}
	
	public int insert(Bill bill) {
		return billMapper.insert(bill);
	}
}
