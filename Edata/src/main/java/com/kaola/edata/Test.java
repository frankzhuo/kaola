package com.kaola.edata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.hankcs.hanlp.collection.AhoCorasick.AhoCorasickDoubleArrayTrie;
import com.hankcs.hanlp.dictionary.BaseSearcher;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.dictionary.CustomDictionary;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    
		Map<String,String> m=new  HashMap();
		m.get("");
        String str="上诉人（原审起诉人）：邢黎滨";
        String r=str.replaceAll("（.*）", "");
        System.out.println("1"+r);
        String tmp1="原告赖创茂，男，1981年10月22日出生，汉族，行政协助 身份证住址：广东省普宁市。系东莞市常平创潮贸易商行业主。";
        if(tmp1.startsWith("原告")){
        	System.out.println("ok");
        }
        try{
        	String line="";
        	String casestr="";
        	BufferedReader reader2 = new BufferedReader(new FileReader("d:\\case.txt"));
        	while ((line = reader2.readLine()) != null) {
        		casestr="";
        		int num=line.indexOf("\"name\": \"");
        		if(num>-1){
        			casestr=line.substring(num+9, line.length()-1);
        		}
        		System.out.println(casestr);
        		CustomDictionary.add(casestr);
        	}
        }catch(Exception s){
        	
        }
/*     // 动态增加
        CustomDictionary.add("攻城狮");
        // 强行插入
        CustomDictionary.insert("白富美", "nz 1024");
        // 删除词语（注释掉试试）
//        CustomDictionary.remove("攻城狮");
        System.out.println(CustomDictionary.add("单身狗", "nz 1024 n 1"));
        System.out.println(CustomDictionary.get("单身狗"));

        String text = "攻城狮逆袭单身狗，迎娶白富美，走上人生巅峰";  // 怎么可能噗哈哈！

        // AhoCorasickDoubleArrayTrie自动机分词
        final char[] charArray = text.toCharArray();
        CustomDictionary.parseText(charArray, new AhoCorasickDoubleArrayTrie.IHit<CoreDictionary.Attribute>()
        {
            @Override
            public void hit(int begin, int end, CoreDictionary.Attribute value)
            {
                System.out.printf("[%d:%d]=%s %s\n", begin, end, new String(charArray, begin, end - begin), value);
            }
        });*/
        // trie树分词
        BaseSearcher searcher = CustomDictionary.getSearcher(tmp1);
        Map.Entry entry;
        while ((entry = searcher.next()) != null)
        {
            System.out.println(entry);
        }
	}

}
