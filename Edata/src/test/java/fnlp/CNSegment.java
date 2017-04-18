package fnlp;

import org.fnlp.nlp.cn.CNFactory;

public class CNSegment {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		CNFactory factory = CNFactory.getInstance("models");
		
		//
		String[] words = factory.seg("关注自然处理语言处理,语音识别,深度学习等方向的前沿技术和业界动态。");
		
		for (String word : words) {	
			System.out.print(word + " " );
		}
		
		
		
		
		System.out.println("");

	}

}
