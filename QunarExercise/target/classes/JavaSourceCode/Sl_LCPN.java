package zhangxl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Given a digit string, return all possible letter combinations that the number could represent.
 * @author maoge
 * 先做一张电话数字和字符的映射表，再针对给定号码DFS回溯
 */
public class Sl_LCPN {

	public List<String> letterCombinations(String digits) {
        //存储结果
		ArrayList<String> list = new ArrayList<String>();
        //PhoneNumber映射表
		String[] sPN = {" ","","abc","def","ghi","jkl",
				"mno","pqrs","tuv","wxyz"};
    	if(digits == null){
    		return list ;
    	}
		//调用DFS递归函数
    	else rec(list , digits.length(),sPN,digits,new StringBuffer());
		return list;
    }
	/**
	 * DFS递归函数
	 * @param rect 保存最后可以表示的字符串的List
	 * @param length 输入的数字字符串的长度
	 * @param sPN Phone Number的映射表
	 * @param Digits 输入的数字字符串
	 * @param sb StringBuilder类
	 */
	public static void rec(ArrayList<String> rect,int length,String[] sPN , String Digits,StringBuffer sb){
		//遍历结束时，即Digits String长度为0
		if(length == 0){
			//将最后产生的字符串添加到list中
			rect.add(sb.toString());
			return ;
		}
		String s = sPN[Digits.charAt(0)-'0'];
		for(int i=0;i<s.length();i++){
			//将数字对应的字符添加到sb中
			sb.append(s.charAt(i));
			//递归调用rect
			rec(rect,length-1,sPN,Digits.substring(1),sb);
			//调用结束，恢复现场
			sb.deleteCharAt(sb.length()-1);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String digits = "2";
		ArrayList<String> results = (ArrayList<String>) new Sl_LCPN().letterCombinations(digits);
		Iterator it = results.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}

}
