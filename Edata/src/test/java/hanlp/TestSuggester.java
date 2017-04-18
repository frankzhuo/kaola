package hanlp;

import com.hankcs.hanlp.dependency.CRFDependencyParser;
import com.hankcs.hanlp.suggest.Suggester;

public class TestSuggester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Suggester suggester = new Suggester();
		String[] titleArray =  ("威廉王子发表演说及发言，呼吁保护野生动物\n"+
				"年度任务最终入围名单出炉， 普京马云入选\n"+
				"建雕塑\n" +
				"日本保密法将正式生效 日媒指其损害国家民主执行权\n" +
				"英媒体说空气污染带来 公共健康危害"
				).split("\n");

		for (String title : titleArray) {
			suggester.addSentence(title);
		}
		
		System.out.println(suggester.suggest("发言",2));
		System.out.println(suggester.suggest("危机公共",1));
		System.out.println(suggester.suggest("jianyikai",1));
		
		
		System.out.println(CRFDependencyParser.compute("把市场经济奉行的等价交换原则引入到党的生活和国家机关政务活动中"));
	}

}
