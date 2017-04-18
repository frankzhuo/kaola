package com.kaola.edata;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.fnlp.ml.classifier.Predict;
import org.fnlp.ml.classifier.LabelParser.Type;
import org.fnlp.ml.classifier.bayes.BayesClassifier;
import org.fnlp.ml.classifier.bayes.BayesTrainer;
import org.fnlp.ml.types.Instance;
import org.fnlp.ml.types.InstanceSet;
import org.fnlp.ml.types.alphabet.AlphabetFactory;
import org.fnlp.nlp.cn.tag.CWSTagger;
import org.fnlp.nlp.pipe.Pipe;
import org.fnlp.nlp.pipe.SeriesPipes;
import org.fnlp.nlp.pipe.StringArray2SV;
import org.fnlp.nlp.pipe.Target2Label;
import org.fnlp.nlp.pipe.nlp.CNPipe;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.BaseSearcher;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

public class OpenCourt {
		 
	
     
     
     
	/**
	 * @param 开庭公告处理主程序
	 */
	public static void main(String[] args) {

		
		
		try{
		    //File file = new File("d:\\aa.txt");
		    File file = new File("e:\\esdata\\openCourt\\clean_openCourt_1.txt");
		    //File file = new File("");
	        BufferedReader reader = null;
	        FileWriter fileWritter = new FileWriter("d:\\e9.txt",true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            int  n=0;
           //int  i=1;
	        try {
	            System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            //int line = 1;
	            // 一次读入一行，直到读入null为文件结束
	            HanLP.Config.ShowTermNature = true;    // 关闭词性显示
	           // Segment segment = new CRFSegment().enableCustomDictionary(false);
	            int  j=0;
	            boolean  flag=false;
	            
	            String yuangao="";
	            String beigao="";	          
	            String dangshiren="";
	            String court="最高人民法院";
	            String fating="";
	            String jnum="";
	            String judge="";
	            String jcase ="";
	            String openDate="";
	            
	            Pattern pattern = Pattern.compile("第.*法庭");           	
	         	Pattern pattern2 = Pattern.compile("定于.*年.*月.*日.*点"); 
	         	Pattern pattern1 = Pattern.compile("开庭审理([^与]*)与([^与]*公司)"); 
             	Segment segment = HanLP.newSegment().enableNameRecognize(true).enableOrganizationRecognize(true).enablePlaceRecognize(false);	        	      	          
	                       		
	            while ((tempString = reader.readLine()) != null) {
	    	     	
		    
	            	//tempString="公诉机关江西省赣县人民检察院。被告人欧XX，男，1988年11月24日出生于安徽省蚌渠市五河县，汉族，初中文化，住安徽省蚌渠市五河县XX镇XX村XXX号，因涉嫌犯交通肇事罪，于2014年6月14日被刑事拘留，同年6月25日被逮捕，同年8月7日被取保候审，同年2014年8月29日被逮捕，现羁押在赣县看守所。辩护人刘晓春，江西章贡律师事务所律师。江西省赣县人民检察院以赣县检公诉刑诉（2014）116号起诉书指控被告人欧XX犯交通肇事罪，于2014年8月22日向本院提起公诉。本院于2014年8月22日立案受理，并依法适用简易程序公开开庭审理本案。赣县人民检察院代理检察员董声令出庭支持公诉，被告人欧XX及其辩护人刘晓春到庭参加诉讼。现已审理终结。公诉机关指控，2014年6月14日被告人欧XX驾驶皖CXXXXX重型半挂牵引车由赣县往兴国方向行驶，6时05分许，当行至S223线444KM＋450M即赣县南塘镇都口村路段，在超越前方由刘XX驾驶的无号牌燃油助力车时，与助力车发生刮擦，造成刘XX受到辗压当场死亡及其车辆受损的交通事故。被告人欧XX承担此事故的全部责任。另查明，案发后被告人欧XX主动投案自首，如实供述自己罪行，且肇事车皖CXXXXX车主与被害人家属达成了赔偿协议，并按协议付清了353800元赔偿款，取得了被害人的谅解。上述事实，被告人欧XX在开庭审理过程中亦无异议，且有证人钟XX、邱XX、邱X伟、刘XX、王X的证言，事故认定书，扣押物品、文件清单，情况说明，归案经过，驾驶人信息查询结果单，行驶证、驾驶证复印件，保单复印件，过磅单，常住人口基本信息，委托书，赔偿协议、谅解书、收据、工商银行个人业务凭证，身份证、户口簿复印件，现场勘查笔录及照片，机动车技术性能鉴定报告书，助力车技术性能司法鉴定结论书，死亡原因司法鉴定意见书，痕迹检验司法鉴定意见书等证据证实，足以认定。本院认为，被告人欧XX违反道路交通安全法，未确保安全行车距离，发生交通事故致一人死亡，承担事故全部责任的行为构成交通肇事罪。公诉机关指控的罪名成立。案发后被告人欧XX主动投案，如实供述自己罪行，属自首，且肇事车车主与被害人家属达成了经济损失赔偿协议，并已按协议履行完毕，取得了被害人家属的谅解，依法可以从轻处罚。公诉机关提出对被告人欧XX判处拘役三个月至四个月的量刑建议，与事实、法律相符，本院予以支持。据此，根据《中华人民共和国刑法》第一百三十三条、第六十七条第一款之规定，判决如下：被告人欧XX犯交通肇事罪，判处拘投三个月。（上述刑期从判决执行之日起计算。判决执行以前先行羁押的，羁押一日折抵刑期一日。被告人欧XX的刑期自2014年6月14日起至2014年10月4日止。）如不服本判决，可在接到判决书的第二日起十日内，通过本院或直接向江西省赣州市中级人民法院提出上诉。书面上诉的，应当提交上诉状正本一份，副本四份。代理";
	            	System.out.println(tempString);	
	                String []str=tempString.split("\001");
		  
		            yuangao=" ";
		            beigao=" ";	          
		            dangshiren=" ";
		            court="最高人民法院";
		            fating=" ";
		            jnum=" ";
		            judge=" ";
		            jcase =" ";
		            openDate=" ";
		     		//设定规则
		         	Matcher matcher = pattern.matcher(tempString);
		          	Matcher matcher2 = pattern2.matcher(tempString);
		          	
		            if(matcher.find()){
		            	fating=matcher.group(0);   	
		            }
		            
		            if(matcher2.find()){
		            	openDate=matcher2.group(0).substring(2);   	
		            }
		            if(str.length>3){
		            	Matcher matcher1 = pattern1.matcher(tempString);
		            	if(matcher1.find()){
		            		dangshiren=matcher1.group(1)+"  "+matcher1.group(2);  
		            		System.out.println("1"+matcher1.group(1) + " 2"+matcher1.group(2));
				        }
		            }
//			            List<Term> termList = segment.seg(str[3]);
		           
		            System.out.println(fating);
		            System.out.println(openDate);
//		            if(str.length>3){
//			            List<Term> termList = segment.seg(str[3]);
//		                System.out.println(termList);
//		                flag=false;
//		                for(int  i=0;i<termList.size();i++){
//		                	//System.out.println(i);
//		                	if(termList.get(i).word.equals("审理")){
//		                		 flag=true;
//		                	}
//		                	if(flag==true &&(termList.get(i).nature.name().equals("nr")||termList.get(i).nature.name().equals("nt"))){
//		                		dangshiren=dangshiren+" " +termList.get(i).word;
//		                	}
//		                }
//		            }
            		System.out.println(dangshiren);

		            fileWritter.write(tempString+"\001"+openDate+"\001"+court+"\001"+fating+"\001"+jnum+"\001"+judge+"\001"+yuangao+"\001"+beigao+"\001"+dangshiren+"\001"+jcase+"\001\n\r");	                		        

	    
		            
	               //	fileWritter.write(tempString+"\001"+courtType+"\001"+yuangao+"\001"+beigao+"\001"+shangshuren+"\001"+beishangshuren+"\001"+weitobianhuren+"\001"+dangshiren+"\001"+dangshirenAll+"\001"+judgeDate+"\001"+jcase+"\001"+judgeProceed+"\001"+jsummary+"\001"+identityCard+"\001"+strResult+"\001"+pred_label+"\n\r");	                		        
	                //System.out.println(""+(j++));
	               
	 				 //IndexResponse response =(IndexResponse)client.prepareIndex("twitter", "tweet").setSource(json).execute().actionGet();
	 	
	            }
	            reader.close();
	            bufferWritter.close();
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }

		}catch(Exception o){
			o.printStackTrace();
		}
	}

}
