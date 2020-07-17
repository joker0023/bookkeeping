package com.jokerstation.bookkeeping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.jokerstation.bookkeeping.pojo.Shop;
import com.jokerstation.bookkeeping.vo.ShopVo;

import tk.mybatis.mapper.common.Mapper;

public interface ShopMapper extends Mapper<Shop> {

	@Insert("insert into shop " + 
			"	(id, name, address, img, remark, created)" + 
			"    values (#{id}, #{name}, #{address}, #{img}, #{remark}, #{created})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public int initShop(Shop shop);
	
	@Select("SELECT s.id shopId, s.`name` shopName, u.id ownerId, u.nick ownerNick, u.phone ownerPhone from shop s " + 
			"INNER JOIN rel_seller sel on sel.shop_id=s.id and sel.role=1 " + 
			"INNER JOIN `user` u on u.id=sel.user_id")
	public List<ShopVo> listShopVos();
}
