package zhangxl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The n-queens puzzle is the problem of placing n queens on an n×n chessboard 
 * such that no two queens attack each other.
 * @author maoge
 *
 */
public class SL_NQueens {

	/**
	 * N皇后问题，使用递归回溯的方法，用一个1维数组代表皇后的坐标，数组的下标表示横坐标的位置，数组的值表示纵坐标的值。
	 * @param n
	 * @return
	 */
	public List<List<String>> solveNQueens(int n){
		List<List<String>> res = new ArrayList<List<String>>();
		if(n <= 0)
			return res ;
		//一维数组，表示皇后的位置
		int[] coordinate = new int[n] ;
		DFS_Helper(n, 0, res, coordinate);
		return res ;
	}
	public void DFS_Helper(int n , int row , List<List<String>> res 
										, int[] coordinate ){
		//若row == n,则找到解法
		if( row == n){
			List<String> unit = new ArrayList<String>();
			//添加皇后的位置
			for(int i = 0 ; i < n ; i++){
				StringBuffer sb = new StringBuffer();
				for(int j = 0 ; j < n ; j++){
					if( coordinate[i] == j)
						sb.append("Q");
					else sb.append(".");
				}
				unit.add(sb.toString());
			}
			res.add(unit);
 		}
		//当 row < n时
		else {
			for(int i = 0 ; i < n ; i++){
				//将每一行的值赋给皇后的纵坐标
				coordinate[row] = i ;
				//判断是否合法，合法则递归调用
				if(isVaild(row, coordinate)){
					DFS_Helper(n, row+1, res, coordinate);
				}
			}
			
		}
	}
	/**
	 * 判断第n行是否合法,不能在同一行、同一列或者同一斜线上
	 * 
	 * @param row
	 * @param coordinate
	 */
	public boolean isVaild(int row , int[] coordinate){
		for(int i=0 ; i < row ; i++){
			if(coordinate[row] == coordinate[i] || 
					Math.abs(coordinate[row] - coordinate[i]) == row - i)
				return false ;
		}
		return true ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SL_NQueens sl = new SL_NQueens();
		int n = 8 ;
		List<List<String>> res = sl.solveNQueens(n);
		Iterator it = res.iterator();
		System.out.println(res.size());
		while(it.hasNext()){
			Iterator it_s = ((List<String>)it.next()).iterator();
			System.out.println("[");
			while(it_s.hasNext()){
				System.out.println(it_s.next()+",");
			}
			System.out.println("]");
		}
	}

}
