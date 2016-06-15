package zhangxl;
/**
 * 旋转矩阵
 * @author maoge
 *
 */
public class SL_RotateImage {
	/**
	 * 
	 * @param matrix
	 */
	public void rotate(int[][] matrix){
		int length = matrix.length ;
		int[][] results = new int[length][length] ;
		for(int i = 0 ; i < length ; i ++){
			for(int j = 0 ; j < length ; j++){
				results[j][length-1-i] = matrix[i][j];
			}
		}
		for(int i = 0 ; i < length ; i++){
			for(int j = 0 ; j < length ; j++){
				matrix[i][j] = results[i][j];
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
