package jWeb.service;

import java.util.List;

import jWeb.dao.BOBDao;
import jWeb.pojo.BuyOrBrowse;
import jWeb.pojo.Goods;

public class BOBService {
	private BOBDao bd=new BOBDao();
	
	//�������/�����¼����bTypeΪ0ʱ�������bTypeΪ1ʱ����
	public int AddRecoding(BuyOrBrowse b) {
		return bd.AddRecoding(b);
	}

	//��ѯ�����¼
	public List<BuyOrBrowse> searchBuy(String uId) {
		return bd.searchBuy(uId);
	}

	//��ѯ�����¼
	public List<BuyOrBrowse> searchBrowse(String uId) {
		return bd.searchBrowse(uId);
	}

	//���ҹ��ﳵ��¼
	public List<BuyOrBrowse> searchCart(String uId) {
		return bd.searchCart(uId);
	}

	//��������ĳһ��¼
	public BuyOrBrowse findRecoding(String bId) {
		return bd.findRecoding(bId);
	}

	//ɾ����¼
	public int deleteRecoding(String bId) {
		return bd.deleteRecoding(bId);	
	}

	
	//���س����۱���ͳ�Ƴ�����������Ʒ����Ϣ
	public List<Goods> searchSaleTable() {
		return bd.searchSaleTable();
	}

}
