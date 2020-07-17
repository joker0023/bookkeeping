package com.jokerstation.bookkeeping.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.jokerstation.bookkeeping.mapper.RelBuyerMapper;
import com.jokerstation.bookkeeping.mapper.RelSellerMapper;
import com.jokerstation.bookkeeping.mapper.ShopMapper;
import com.jokerstation.bookkeeping.mapper.UserMapper;
import com.jokerstation.bookkeeping.pojo.Bill;
import com.jokerstation.bookkeeping.pojo.RelBuyer;
import com.jokerstation.bookkeeping.pojo.RelSeller;
import com.jokerstation.bookkeeping.pojo.Shop;
import com.jokerstation.bookkeeping.pojo.User;
import com.jokerstation.bookkeeping.vo.MineVo;
import com.jokerstation.bookkeeping.vo.TokenVo;
import com.jokerstation.common.util.JsonUtils;
import com.jokerstation.common.util.WebUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class AppService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ShopMapper shopMapper;
	
	@Autowired
	private RelBuyerMapper relBuyerMapper;
	
	@Autowired
	private RelSellerMapper relSellerMapper;
	
	@Autowired
	private BillService billService;
	
	@Value("${app.id}")
	private String AppID;
	@Value("${app.secret}")
	private String AppSecret;
	
	public static Map<String, TokenVo> tokenMap = new HashMap<String, TokenVo>();
	
	public static String getOpenId(String token) {
		if (null == token) {
			return null;
		}
		
		TokenVo tokenVo = tokenMap.get(token);
		if (null == tokenVo) {
			return null;
		}
		
		return tokenVo.getOpenId();
	}
	
	private static void setSessionTokenVo(TokenVo tokenVo) {
		tokenMap.put(tokenVo.getToken(), tokenVo);
	}
	
	public static void cleanToken() {
		List<String> expiredKey = new ArrayList<String>();
		for (String key : tokenMap.keySet()) {
			TokenVo tokenVo = tokenMap.get(key);
			Long now = System.currentTimeMillis();
			Long timeMillis = tokenVo.getTimeMillis();
			if (now - timeMillis > 12 * 3600 * 1000) {
				expiredKey.add(key);
			}
		}
		
		for (String key : expiredKey) {
			tokenMap.remove(key);
		}
	}
	
	@SuppressWarnings("unchecked")
	public TokenVo getAppUserByCode(String code) throws Exception {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid={{appid}}&secret={{secret}}&js_code={{js_code}}&grant_type=authorization_code";
		url = url.replace("{{appid}}", AppID)
				.replace("{{secret}}", AppSecret)
				.replace("{{js_code}}", code);
		String result = WebUtil.doGet(url);
		Map<String, String> resultMap = JsonUtils.toBean(result, Map.class);
		String openId = (String)resultMap.get("openid");
		String sessionKey = (String)resultMap.get("session_key");
		long timeMillis = System.currentTimeMillis();
		String token = DigestUtils.md5Hex(openId + "_" + sessionKey + "_" + timeMillis);
		initUser(openId);
		
		TokenVo vo = new TokenVo();
		vo.setSessionKey(sessionKey);
		vo.setToken(token);
//		vo.setUser(user);
		vo.setTimeMillis(timeMillis);
		vo.setOpenId(openId);
		setSessionTokenVo(vo);
		return vo;
	}
	
	private User initUser(String openId) {
		User user = getUserByOpenId(openId);
		if (null != user) {
			return user;
		}
		
		user = new User();
		user.setCreated(new Date());
		user.setDefaultRole(User.DEFAULT_ROLE_BUYER);
		user.setOpenId(openId);
		user.setUserStatus(User.STATUS_OK);
		userMapper.insert(user);
		
		return getUserByOpenId(openId);
	}
	
	public MineVo getMineVo(String token) {
		User user = getUserByToken(token);
		if (null == user) {
			return null;
		}
		
		List<Shop> buyerShops = listBuyerShops(user.getId());
		List<Shop> sellerShops = listSellerShops(user.getId());
		
		MineVo mineVo = new MineVo();
		mineVo.setBuyerShops(buyerShops);
		mineVo.setSellerShops(sellerShops);
		mineVo.setUser(user);
		return mineVo;
	}
	
	private User getUserByToken(String token) {
		String openId = getOpenId(token);
		if (null == openId) {
			return null;
		}
		return getUserByOpenId(openId);
	}
	
	private List<Shop> listBuyerShops(Long userId) {
		RelBuyer record = new RelBuyer();
		record.setUserId(userId);
		List<RelBuyer> relList = relBuyerMapper.select(record);
		List<Long> shopIds = relList.stream().map(RelBuyer::getShopId).collect(Collectors.toList());
		if (shopIds.size() == 0) {
			return new ArrayList<>();
		}
		
		Example example = new Example(Shop.class);
		example.createCriteria().andIn("id", shopIds);
		return shopMapper.selectByExample(example);
	}
	
	private List<Shop> listSellerShops(Long userId) {
		RelSeller record = new RelSeller();
		record.setUserId(userId);
		List<RelSeller> relList = relSellerMapper.select(record);
		List<Long> shopIds = relList.stream().map(RelSeller::getShopId).collect(Collectors.toList());
		if (shopIds.size() == 0) {
			return new ArrayList<>();
		}
		
		Example example = new Example(Shop.class);
		example.createCriteria().andIn("id", shopIds);
		return shopMapper.selectByExample(example);
	}
	
	public User updateUser(String token, String nick, String avatar) {
		String openId = getOpenId(token);
		if (null == openId) {
			return null;
		}
		User record = new User();
		record.setNick(nick);
		record.setAvatar(avatar);
		Example example = new Example(User.class);
		example.createCriteria().andEqualTo("openId", openId);
		userMapper.updateByExampleSelective(record, example);
		
		return getUserByOpenId(openId);
	}
	
	public User toggleUserRole(String token, byte role) {
		String openId = getOpenId(token);
		if (null == openId) {
			return null;
		}
		User record = new User();
		if (role == 1) {
			record.setDefaultRole(User.DEFAULT_ROLE_BUYER);
		} else if (role == 2) {
			record.setDefaultRole(User.DEFAULT_ROLE_SELLER);
		} else {
			return null;
		}
		Example example = new Example(User.class);
		example.createCriteria().andEqualTo("openId", openId);
		userMapper.updateByExampleSelective(record, example);
		
		return getUserByOpenId(openId);
	}
	
	public User getUserByOpenId(String openId) {
		User record = new User();
		record = new User();
		record.setOpenId(openId);
		return userMapper.selectOne(record);
	}
	
	public PageInfo<Bill> listBuyerBills(String token, Long shopId, int page, int size) {
		User user = getUserByToken(token);
		if (null == user) {
			return null;
		}
		
		return billService.listBills(user.getId(), shopId, page, size);
	}
	
	public PageInfo<Bill> listSellerBills(String token, Long shopId, int page, int size) {
		User user = getUserByToken(token);
		if (null == user) {
			return null;
		}
		
		List<Shop> sellerShops = listSellerShops(user.getId());
		Set<Long> shopIds = sellerShops.stream().map(Shop::getId).collect(Collectors.toSet());
		if (!shopIds.contains(shopId)) {
			return new PageInfo<Bill>(new ArrayList<>());
		}
		
		return billService.listBills(null, shopId, page, size);
	}
}
