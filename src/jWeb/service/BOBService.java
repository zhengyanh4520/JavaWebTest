package jWeb.service;

import java.util.List;

import jWeb.dao.BOBDao;
import jWeb.pojo.BuyOrBrowse;
import jWeb.pojo.Goods;

public class BOBService {
	private BOBDao bd=new BOBDao();
	
	//插入浏览/购买记录，当bType为0时浏览，当bType为1时购买
	public int AddRecoding(BuyOrBrowse b) {
		return bd.AddRecoding(b);
	}

	//查询购买记录
	public List<BuyOrBrowse> searchBuy(String uId) {
		return bd.searchBuy(uId);
	}

	//查询浏览记录
	public List<BuyOrBrowse> searchBrowse(String uId) {
		return bd.searchBrowse(uId);
	}

	//查找购物车记录
	public List<BuyOrBrowse> searchCart(String uId) {
		return bd.searchCart(uId);
	}

	//主键查找某一记录
	public BuyOrBrowse findRecoding(String bId) {
		return bd.findRecoding(bId);
	}

	//删除记录
	public int deleteRecoding(String bId) {
		return bd.deleteRecoding(bId);	
	}

	
	//加载出销售报表，统计出已售卖的商品的信息
	public List<Goods> searchSaleTable() {
		return bd.searchSaleTable();
	}

}
