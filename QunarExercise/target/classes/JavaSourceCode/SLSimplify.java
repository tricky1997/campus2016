package zhangxl ;

import java.util.Stack ;

public class SLSimplify{
	// 
	public String simplifyPath(String path){
		Stack<Character> mStack = new Stack<Character>(); 
		StringBuilder results = new StringBuilder() ;
		for(int i = 0 ; i < path.length() ; i++){
			Character c = path.charAt(i) ; 
			if(c == '/' && i == 0){
				mStack.push(c);
			}
			else if(c == '/' && i > 0){
				//'/'不在最后一个字符上
				if(path.charAt(i-1) !='/' && i != path.length()-1){
					mStack.push(c);
				}
				else if(path.charAt(i-1) == '/'){
					continue ;
				}
			}
			else if(c == '.' && i < path.length() - 2){
				if( path.charAt(i+1) == '/'){
					i++ ;
					continue ;
				}
				//返回上一级目录
				else if(path.charAt(i+1) == '.' && path.charAt(i+2) == '/'){
					if(mStack.size() > 1)
						mStack.pop();
					while(!mStack.empty() && mStack.peek()!='/'){
						mStack.pop();
					}
					//i往前移两格
					i = i + 2 ;
				}
			//	else mStack.push(c) ;
			}
			else if(c != '/' ){//&& c != '.'
				mStack.push(c) ;
			}
		}
		//把一个栈移到另一个栈上
		Stack<Character> STemp = new Stack<Character>();
		while(!mStack.empty()){
			STemp.push(mStack.peek());
			mStack.pop();
		}
		// 输出目录
		while(!STemp.empty()){
			results.append(STemp.peek());
			STemp.pop();
		}
		return results.toString();
	}

	public static void main(String[] args){
		String path = "/...";
		System.out.println(new SLSimplify().simplifyPath(path));
	}
}