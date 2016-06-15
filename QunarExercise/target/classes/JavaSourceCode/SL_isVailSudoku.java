package zhangxl;
/**
 * 检查一个数独块是不是有效的，只需要检查每一个行，每一列，所有的九宫格
 * @author maoge
 *
 */
public class SL_isVailSudoku {

	public boolean isValidSudoku(char[][] board){
		return isValidRow(board) && isValidColumn(board) && isValidBox(board) ;
	}
	//判断每一行
	public boolean isValidRow(char[][] board){  
        for(int i=0; i<9; i++){  
            boolean[] flag = new boolean[10];  
            for(int j=0; j<9; j++){  
                if(!markFlag(flag, board[i][j])){  
                    return false;  
                }  
            }  
        }  
        return true;  
    }  	
	//判断每一列
	  public boolean isValidColumn(char[][] board){  
	        for(int i=0; i<9; i++){  
	            boolean[] flag = new boolean[10];  
	            for(int j=0; j<9; j++){  
	                if(!markFlag(flag, board[j][i])){  
	                    return false;  
	                }  
	            }  
	        }  
	        return true;  
	    }	
	//检查所有九宫格
	  public boolean isValidBox(char[][] board){  
	        for(int i=0; i<3; i++){  
	            for(int j=0; j<3; j++){  
	                boolean[] flag = new boolean[10];  
	                for(int m=0; m<3; m++){  
	                    for(int n=0; n<3; n++){  
	                        if(!markFlag(flag, board[i*3+m][j*3+n])){  
	                            return false;  
	                        }  
	                    }  
	                }  
	            }  
	        }  
	        return true;  
	    }  
	/**
	 * 判断有没有重复的
	 * @param flag
	 * @param c
	 * @return
	 */
	public boolean markFlag(boolean[] flag , char c){
		if(c == '.'){
			return true ;
		}
		int index = c-'0';
		if(flag[index]){
			return false ;  // 之前出现过
		}
		else {
			flag[index] = true ;
			return true ;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
