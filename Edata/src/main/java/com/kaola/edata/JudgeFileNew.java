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
/**
 * 
 * 判决文书正式ETL
 *
 */
public class JudgeFileNew {
		 
	/**
	 * 获取生日和性别
	 */
     static String getBirthdaySex(String str){
    	 String sex="";
    	 String result="";
    	 String birthday="";
    	 if(str.indexOf("男")>-1)
    		 sex="男";
    	 if(str.indexOf("女")>-1)
    		 sex="女";
        Pattern pattern = Pattern.compile("[0-9]{4}年[0-9]*月[0-9]*日");       
       	Matcher matcher = pattern.matcher(str);        
        if(matcher.find()){
        	birthday=matcher.group(0);   	
        }
    	 return  birthday+":"+sex;
     }
  	/**
  	 * 获取精确当事人
  	 */
  static  String getPerson(String str,Segment segment){
      if(str.endsWith("公司")||str.endsWith("行")||str.endsWith("社")||str.endsWith("中心")
    		  ||str.endsWith("处") ||str.endsWith("公司）") ||str.endsWith("站") ||str.endsWith("部") ||str.endsWith("厂")
    		  ||str.endsWith("局")||str.endsWith("委会")||str.endsWith("矿")
    		  ||str.endsWith("医院")||str.endsWith("委员会") 
    		  )    	  
    	  return str;
      
      List<Term> termList = segment.seg(str.replaceAll("（.*）", ""));
      //List<Term> termList =  NLPTokenizer..segment(tempString);
      //System.out.println(termList);
      for(int i=0;i<termList.size();i++){
    	  if(termList.get(i).nature.name().startsWith("v")&&!termList.get(i).word.equals("简称")){
    		  //System.out.println(termList);
    		 // System.out.println("动词:"+str);
    		  return "";
    	  }
  	 }
	  return  "";
  }
 	/**
 	 * 判决结果
 	 */
     static  String getResult(String str,SeriesPipes pp,BayesClassifier classifier ){
        Pattern pattern = Pattern.compile("准[予许]*.*撤回.*[起诉|申请]");           	
     	Pattern pattern2 = Pattern.compile("准[予许]{1}.*撤诉"); 
     	Pattern pattern1 = Pattern.compile("被告.*赔偿原告");
     	//Matcher matcher1 = pattern.matcher(str);   
        //System.out.println(tempString);
 		//str="驳回原告孙有宏的诉讼请求。案件受理费750元（已预交），由原告承担。如不服本院判决，可在判决书送达之日起十五日内，向本院递交上诉状，并按对方当事人的人数，提出副本上诉于盘锦市中级人民法院。审判员　　孙志平二〇一四年六月九日书记员　　王晓丹";
 		Instance inst = new Instance(str);
 		try{
 			pp.addThruPipe(inst);
 		}catch(Exception o){
 			
 		}	
 		String pred_label="";   
 		//设定规则
     	Matcher matcher = pattern.matcher(str);
      	Matcher matcher2 = pattern2.matcher(str);
      	Matcher matcher1 = pattern1.matcher(str);         	        
        if(matcher.find()||matcher2.find()){
        	//System.out.println("撤回 起诉  neutral");
         	pred_label="neutral";
        }
        else if(matcher.find()){
        	//System.out.println("赔偿原告 win");
         	pred_label="win";
        }  
     	else if(str.startsWith("驳回")){
        	//System.out.println("驳回 failure");
        	pred_label="failure";
        }
        else if(str.startsWith("终结")){
         	//System.out.println("终结 neutral");
         	pred_label="neutral";
        }
        else if(str.startsWith("一、终结")){
         	//System.out.println("一、终结  neutral");
         	pred_label="neutral";
        }
        else if(str.startsWith("中止执行")){
         	//System.out.println("中止执行  neutral");
         	pred_label="neutral";
        }               
        else if(str.startsWith("中止执行")){
         	//System.out.println("中止执行  neutral");
         	pred_label="neutral";
        }           
        else if(str.indexOf("终结本次执行程序")>-1){
         	//System.out.println("终结本次执行程序 neutral");
         	pred_label="neutral";
        }
        else if(str.indexOf("本次执行程序终结")>-1){
         	//System.out.println("本次执行程序终结执行  neutral");
         	pred_label="neutral";
        }
        
        else if(str.indexOf("和解并已履行完毕")>-1){
         	//System.out.println("和解并已履行完毕  neutral");
         	pred_label="neutral";
        }
        else if(str.indexOf("自动撤回起诉处理")>-1){
         	//System.out.println("自动撤回起诉处理  neutral");
         	pred_label="neutral";
        }
        else if(str.indexOf("驳回")>-1){
         	//System.out.println("驳回 neutral");
         	pred_label="neutral";
        }
                
         if(pred_label.equals("")){
        	try{ 
	         	Predict<String> pres=classifier.classify(inst, Type.STRING,1);
	         	pred_label=pres.getLabel();	
	         	//System.out.println("***"+pred_label);
//	         	if(pred_label.equals("failture"))
//	         		System.out.println("fail***"+str);
        	}catch(Exception o){
        		o.printStackTrace();
        	}
 		}	
        return  pred_label;
     }
     
     
     
     
	/**
	 * @param 主程序
	 */
	public static void main(String[] args) {

		//String trainDataPath="";
		/**
		 * Bayes分类
		 */
		String trainDataPath ="./class";		
		//分词		
		CWSTagger tag = null;
		try{
		   tag = new CWSTagger("./models/seg.m");
		}catch(Exception o){
			
		}
		Pipe segpp=new CNPipe(tag);
		Pipe s2spp=new Strings2StringArray();
		//建立字典管理器
		AlphabetFactory af = AlphabetFactory.buildFactory();		
		//将字符特征转换成字典索引;	
		Pipe sparsepp=new StringArray2SV(af);
		//将目标值对应的索引号作为类别
		Pipe targetpp = new Target2Label(af.DefaultLabelAlphabet());	
		//建立pipe组合
		SeriesPipes pp = new SeriesPipes(new Pipe[]{segpp,s2spp,targetpp,sparsepp});

		System.out.print("\nReading data......\n");
		InstanceSet instset = new InstanceSet(pp,af);	
		org.fnlp.data.reader.Reader reader1 = new MyDocumentReader(trainDataPath,"utf-8");
		try{
			instset.loadThruStagePipes(reader1);
		}catch(Exception e){}
		System.out.print("..Reading data complete\n");	
		InstanceSet trainset = instset;
		System.out.print("Training...\n");
		af.setStopIncrement(true);
		BayesTrainer trainer=new BayesTrainer();
		BayesClassifier classifier= (BayesClassifier) trainer.train(trainset);
		System.out.print("..Training complete!\n");	
		
		Segment segment = HanLP.newSegment().enableNameRecognize(true).enableOrganizationRecognize(true).enablePlaceRecognize(false);

   
		//案由字典
        try{
        	String line="";
        	String casestr="";
        	BufferedReader reader2 = new BufferedReader(new FileReader("./case.txt"));
        	while ((line = reader2.readLine()) != null) {
        		casestr="";
        		int num=line.indexOf("\"name\": \"");
        		if(num>-1){
        			casestr=line.substring(num+9, line.length()-1);
        		}
        		//System.out.println(casestr);
        		CustomDictionary.add(casestr);
        	}
        }catch(Exception s){}
		
		try{
		    //File file = new File("d:\\aa.txt");
		    File file = new File(args[0]);
		    //File file = new File("");
	        BufferedReader reader = null;
	        FileWriter fileWritter = new FileWriter(args[1],true);
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
	            String courtType="";
	            String yuangao="";
	            String beigao="";
	            String shenqingren="";
	            String beizhixinren="";
	            String shangshuren="";
	            String beishangshuren="";
	            String dangshiren="";
	            String dangshirenAll="";
	            String weitobianhuren="";
	            String weitoFlag="";
	            String judgeDate="";
	            String judgeProceed="";
	            String jcase ="";
	            String pred_label="";
	            String jsummary="";
	            String identityCard="";
	                        		
	            while ((tempString = reader.readLine()) != null) {
	            	
	            	//tempString="公诉机关江西省赣县人民检察院。被告人欧XX，男，1988年11月24日出生于安徽省蚌渠市五河县，汉族，初中文化，住安徽省蚌渠市五河县XX镇XX村XXX号，因涉嫌犯交通肇事罪，于2014年6月14日被刑事拘留，同年6月25日被逮捕，同年8月7日被取保候审，同年2014年8月29日被逮捕，现羁押在赣县看守所。辩护人刘晓春，江西章贡律师事务所律师。江西省赣县人民检察院以赣县检公诉刑诉（2014）116号起诉书指控被告人欧XX犯交通肇事罪，于2014年8月22日向本院提起公诉。本院于2014年8月22日立案受理，并依法适用简易程序公开开庭审理本案。赣县人民检察院代理检察员董声令出庭支持公诉，被告人欧XX及其辩护人刘晓春到庭参加诉讼。现已审理终结。公诉机关指控，2014年6月14日被告人欧XX驾驶皖CXXXXX重型半挂牵引车由赣县往兴国方向行驶，6时05分许，当行至S223线444KM＋450M即赣县南塘镇都口村路段，在超越前方由刘XX驾驶的无号牌燃油助力车时，与助力车发生刮擦，造成刘XX受到辗压当场死亡及其车辆受损的交通事故。被告人欧XX承担此事故的全部责任。另查明，案发后被告人欧XX主动投案自首，如实供述自己罪行，且肇事车皖CXXXXX车主与被害人家属达成了赔偿协议，并按协议付清了353800元赔偿款，取得了被害人的谅解。上述事实，被告人欧XX在开庭审理过程中亦无异议，且有证人钟XX、邱XX、邱X伟、刘XX、王X的证言，事故认定书，扣押物品、文件清单，情况说明，归案经过，驾驶人信息查询结果单，行驶证、驾驶证复印件，保单复印件，过磅单，常住人口基本信息，委托书，赔偿协议、谅解书、收据、工商银行个人业务凭证，身份证、户口簿复印件，现场勘查笔录及照片，机动车技术性能鉴定报告书，助力车技术性能司法鉴定结论书，死亡原因司法鉴定意见书，痕迹检验司法鉴定意见书等证据证实，足以认定。本院认为，被告人欧XX违反道路交通安全法，未确保安全行车距离，发生交通事故致一人死亡，承担事故全部责任的行为构成交通肇事罪。公诉机关指控的罪名成立。案发后被告人欧XX主动投案，如实供述自己罪行，属自首，且肇事车车主与被害人家属达成了经济损失赔偿协议，并已按协议履行完毕，取得了被害人家属的谅解，依法可以从轻处罚。公诉机关提出对被告人欧XX判处拘役三个月至四个月的量刑建议，与事实、法律相符，本院予以支持。据此，根据《中华人民共和国刑法》第一百三十三条、第六十七条第一款之规定，判决如下：被告人欧XX犯交通肇事罪，判处拘投三个月。（上述刑期从判决执行之日起计算。判决执行以前先行羁押的，羁押一日折抵刑期一日。被告人欧XX的刑期自2014年6月14日起至2014年10月4日止。）如不服本判决，可在接到判决书的第二日起十日内，通过本院或直接向江西省赣州市中级人民法院提出上诉。书面上诉的，应当提交上诉状正本一份，副本四份。代理";
	                // 显示行号
	            	flag=false;
	            	//System.out.println(tempString);
	            	courtType="";
	            	yuangao="";
		            beigao="";
		            shenqingren="";
		            beizhixinren="";
		            shangshuren="";
		            beishangshuren="";
		            dangshiren="";
		            dangshirenAll="";
		            weitobianhuren="";
		            weitoFlag="";
		            judgeDate="";
		            judgeProceed="";
		            jcase="";
		            pred_label="";
		            jsummary="";
		            identityCard="";
		            
		            
		            tempString=tempString.replaceAll("\\&amp;\\#xA;", "\002");
		            String []strn=tempString.split("\001");
		            String oldstr="";
		            if(strn.length<2)
		            	continue;
		            try{
		            	//System.out.println(strn.length+"***"+tempString);
		            	//System.out.println(strn.length);
		            	//System.out.println(strn[strn.length-1]);
		            	//System.out.println(strn[6]);
		            	//System.out.println("clean"+strn[6].replaceAll("&amp;#xA;", "\002"));
			            for(int i=0;i<7;i++){
			            	//System.out.println(i);
			            	//System.out.println(strn[i]);
			            	if(i==0)
			            		oldstr=strn[i];
			            	else if(i==1){
			            		//System.out.println(str[1]);
			            		if(strn[i].equals("1"))
			            			oldstr=oldstr+"\001"+"刑事案件";
			            		else if(strn[i].equals("2"))
			            			oldstr=oldstr+"\001"+"民事案件";
			            		else if(strn[i].equals("3"))
			            			oldstr=oldstr+"\001"+"行政案件";
			            		else if(strn[i].equals("4"))
			            			oldstr=oldstr+"\001"+"赔偿案件";
			            		else if(strn[i].equals("5"))
			            			oldstr=oldstr+"\001"+"执行案件";
			            		else if(strn[i].equals("6")) //知识产权
			            			oldstr=oldstr+"\001知识产权";
			            	}
			            	else if(i==6){
			            		oldstr=oldstr+"\001"+strn[i].replaceAll("&amp;#xA;", "\002");	
			            	    //System.out.println(strn[i].replaceAll("&amp;#xA;", "\002"));
			            	}
			            	else
			            		oldstr=oldstr+"\001"+strn[i];
		            	}
			            
//			            if(strn.length<7){
//		            		for(int i=strn.length;i<7;i++)
//		            			oldstr=oldstr+"\001 ";
//			            }
			            
		            }
		            catch(Exception o){
		            	o.printStackTrace();
		            	continue;
		            }
	            	String merge="";
	            	//法院等级
	            	String court=strn[0];
	            	if(court.indexOf("最高人民法院")>-1)
	            		courtType="最高法院";
	            	else if(court.indexOf("高级人民法院")>-1)
	            		courtType="高级法院";
	            	else if(court.indexOf("中级人民法院")>-1)
	            		courtType="中级法院";
	            	else if(court.indexOf("基层法院")>-1)
	            		courtType="基层法院";	     
	            	
	            	//System.out.println(court);
	            	//System.out.println(courtType);
	            	
/*	            	//审理程序
	            	String title=str[2];
	            	if(title.indexOf("一审")>-1)
	            		judgeProceed="一审";
	            	else if(title.indexOf("二审")>-1)
	            		judgeProceed="二审";
	            	else if(title.indexOf("再审")>-1)
	            		judgeProceed="再审";*/
	            	
	            	
	            	//System.out.println(title);
	            	//System.out.println("审理程序:"+judgeProceed);
	            
	            	if(strn.length>6)
	            		merge=strn[6];
	            	else 
	            		continue;
	            	int jhao=merge.indexOf("号");
	            	
	            	
	                //List<String> sentenceList = HanLP.extractSummary(merge, 3);
	                //System.out.println(sentenceList);
	            	//System.out.println("**"+merge);
	                //merge="原告赖创茂，男，1981年10月22日出生，汉族，身份证住址：广东省普宁市。系东莞市常平创潮贸易商行业主。";
	            	String []str1=merge.split("\002");
	            	String tmp1="";
	            	boolean mflag=false;
	            	//System.out.println(str1.length);
	            	//System.out.println("原文2:"+merge);
	            	if(str1.length>4){
		            	//System.out.println("原文:"+str1[3]);
		            	//System.out.println(getBirthdaySex(str1[3]));
	            		//System.out.println("原文1:"+merge);
	            	}else{
	            	  mflag=true;
	            	  //System.out.println(str1.length);
	            	  str1=merge.split("。");
	            	  //System.out.println(str1.length);
	            	  //System.out.println("******"+str1[0]);
	            	  //System.out.println("原文2:"+merge);
	            	}
	            	int numflag=0;
	            	boolean fyuangao=true;
	            	boolean fshangshuren=true;
	            	boolean fbeigao=true;
	            	boolean fbeishangshuren=true;
	            	
	             	//各当事人抽取
	            	for(int i=0;i<str1.length;i++){
	            		tmp1=str1[i];
	            		//System.out.println(tmp1);
	            		if(i==0&&mflag&&tmp1.indexOf("号")>-1){	            			
	            			tmp1=tmp1.substring(tmp1.indexOf("号")+1);
	            			//System.out.println("$$"+tmp1);
	            		}
	            		//tmp1="原告赖创茂，男，1981年10月22日出生，汉族，身份证住址：广东省普宁市。系东莞市常平创潮贸易商行业主。";
//	            		if(tmp1.length()>300)
//	            			continue;
	            		
	            		//if(tmp1.startsWith("原告")&&yuangao.equals("")&&shangshuren.equals("")){
	            		if(tmp1.startsWith("原告")&&(fyuangao||(!fyuangao&&numflag==0))){
	            			numflag=0;
	            			fyuangao=false;
	                        tmp1=tmp1.substring(2, tmp1.length());
	                        //System.out.println("*"+tmp1);
			                int  douhao=-1;
			                douhao=tmp1.indexOf(",");
			                if(tmp1.indexOf("，")!=-1&&(tmp1.indexOf("，")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf("，");
			                if(tmp1.indexOf(".")!=-1&&(tmp1.indexOf(".")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf(".");
			                if(tmp1.indexOf("。")!=-1&&(tmp1.indexOf("。")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf("。");
			                if(tmp1.indexOf("地址")!=-1&&(tmp1.indexOf("地址")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf("地址");
//			                if(yuangao.indexOf(tmp1.substring(0,2))>-1)
//			                	continue;
			                String tmp="";
			                if(douhao>-1)
			                	tmp=tmp1.substring(0,douhao) ;
			                else 
			                	tmp=tmp1;
	            			
			                tmp=tmp.replaceAll("人", "");
			                tmp=tmp.replaceAll("：", "");
			                tmp=tmp.replaceAll("。", "");
			                if(tmp.length()>5)
			                	tmp=getPerson(tmp,segment);
			                if(dangshirenAll.indexOf(tmp)<0){
				                yuangao=yuangao+" "+tmp;
				                dangshiren=dangshiren+tmp+" ";
				                dangshirenAll=dangshirenAll+tmp+":"+getBirthdaySex(tmp1)+" ";
				            	//System.out.println("all:"+dangshirenAll);
			                }
			                weitoFlag="原告";

			                
	            		}
	            		if((tmp1.startsWith("申诉人")||tmp1.startsWith("上诉人")||tmp1.startsWith("再审申请人")||
	            			tmp1.startsWith("申请人")||tmp1.startsWith("申请执行人") ||tmp1.startsWith("复议申请人"))&&(fshangshuren||(!fshangshuren&&numflag==1))){
	            			numflag=1;
	            			fshangshuren=false;
	            			if(tmp1.startsWith("再审申请人")||tmp1.startsWith("申请执行人") ||tmp1.startsWith("复议申请人"))
	            			   tmp1=tmp1.substring(5, tmp1.length());
	                        else 
	                           tmp1=tmp1.substring(3, tmp1.length());
	                        //System.out.println("上诉人2："+tmp1);
	                        int  douhao=-1;
			                douhao=tmp1.indexOf(",");
			                if(tmp1.indexOf("，")!=-1&&(tmp1.indexOf("，")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf("，");
			                if(tmp1.indexOf(".")!=-1&&(tmp1.indexOf(".")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf(".");
			                if(tmp1.indexOf("。")!=-1&&(tmp1.indexOf("。")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf("。");
			                if(tmp1.indexOf("地址")!=-1&&(tmp1.indexOf("地址")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf("地址");
			                String tmp="";
			                if(douhao>-1)
			                	tmp=tmp1.substring(0,douhao) ;
			                else 
			                	tmp=tmp1;
			                
			                
			                tmp=tmp.replaceAll("：", "");
			                tmp=tmp.replaceAll("。", "");
			                
			                if(tmp.length()>11)
			                	tmp=getPerson(tmp,segment);
			                //shangshuren=shangshuren.replaceAll("(*)", "");
			                //shangshuren=shangshuren.replaceAll("（.*）", "");
			                if(dangshirenAll.indexOf(tmp)<0){
				                shangshuren=shangshuren+" "+tmp;
				                dangshiren=dangshiren+tmp+" ";
				                dangshirenAll=dangshirenAll+tmp+":"+getBirthdaySex(tmp1)+" ";
			                }
				                //System.out.println("all:"+dangshirenAll);
			                //System.out.println(shangshuren);
			                weitoFlag="上诉人";
			                
	            		}
	        
	               		if((tmp1.startsWith("被告")||tmp1.startsWith("罪犯"))&&(fbeigao||(!fbeigao&&numflag==2))){
	               			numflag=2;
	               			fbeigao=false;
	                        tmp1=tmp1.substring(2, tmp1.length());
	                        //System.out.println("*"+tmp1);
			                int  douhao=-1;
			                douhao=tmp1.indexOf(",");
			                if(tmp1.indexOf("，")!=-1&&(tmp1.indexOf("，")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf("，");
			                if(tmp1.indexOf(".")!=-1&&(tmp1.indexOf(".")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf(".");
			                if(tmp1.indexOf("。")!=-1&&(tmp1.indexOf("。")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf("。");
			                if(tmp1.indexOf("地址")!=-1&&(tmp1.indexOf("地址")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf("地址");
			                String tmp="";
			                
			                if(douhao>-1)
			                	tmp=tmp1.substring(0,douhao) ;
			                else 
			                	tmp=tmp1;

			                tmp=tmp.replaceAll("人", "");
			                tmp=tmp.replaceAll("。", "");
			                tmp=tmp.replaceAll("：", "");
			                if(tmp.length()>11)
			                	tmp=getPerson(tmp,segment);
			                if(dangshirenAll.indexOf(tmp)<0){
	                            beigao=beigao+" " +tmp;
				                dangshiren=dangshiren+tmp+" ";
				                dangshirenAll=dangshirenAll+tmp+":"+getBirthdaySex(tmp1)+" ";
				             }
			                //System.out.println("all:"+dangshirenAll);
			            	weitoFlag="被告";

			                
	            		}
	               		if((tmp1.startsWith("被执行人")||tmp1.startsWith("被上诉人")||tmp1.startsWith("被申请人")||tmp1.startsWith("被申诉人"))&&(fbeishangshuren||(!fbeishangshuren&&numflag==3))){
	               			numflag=3;
	               			fbeishangshuren=false;
	                        tmp1=tmp1.substring(4, tmp1.length());
	                        //System.out.println("*"+tmp1);
			                int  douhao=-1;
			                if(tmp1.indexOf("，")!=-1&&(tmp1.indexOf("，")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf("，");
			                if(tmp1.indexOf(".")!=-1&&(tmp1.indexOf(".")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf(".");
			                if(tmp1.indexOf("。")!=-1&&(tmp1.indexOf("。")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf("。");
			                if(tmp1.indexOf("地址")!=-1&&(tmp1.indexOf("地址")<douhao||douhao==-1))
			                	douhao=tmp1.indexOf("地址");
			                String tmp="";
			                if(douhao>-1)
			                	tmp=tmp1.substring(0,douhao) ;
			                else 
			                	tmp=tmp1;
			                tmp=tmp.replaceAll("。", "");
			                tmp=tmp.replaceAll("：", "");
			                if(tmp.length()>11)
			                	tmp=getPerson(tmp,segment);
			                if(dangshirenAll.indexOf(tmp)<0){
				                beishangshuren=beishangshuren+" "+tmp;
				                dangshiren=dangshiren+tmp+" ";
				                dangshirenAll=dangshirenAll+tmp+":"+getBirthdaySex(tmp1)+" ";
				             }
			                //System.out.println("all:"+dangshirenAll);
			            	weitoFlag="被上诉人";		                
	            		}

	             		if(tmp1.startsWith("委托代理人")){			
	                        tmp1=tmp1.substring(5, tmp1.length());
	                        //System.out.println("*"+tmp1);
			                int  douhao=-1;
			                douhao=tmp1.indexOf(",");
			                if(douhao==-1)
			                	douhao=tmp1.indexOf("，");
			                String wtmp="";
			                if(douhao>-1)
			                	wtmp=tmp1.substring(0,douhao) ;
			                else 
			                	wtmp=tmp1;
			                if(wtmp.length()>8)
			                	wtmp=getPerson(wtmp,segment);
			                weitobianhuren=weitobianhuren+" "+wtmp;
			                weitobianhuren=weitobianhuren.replaceAll("。", "");
			                weitobianhuren=weitobianhuren.replaceAll("：", "");

			                weitobianhuren=weitobianhuren+"("+weitoFlag+")";			            				         			                
	            		}
	             		
	             		if(tmp1.startsWith("书记员")){	
	             			judgeDate=str1[i-1];
	                                 			                
	            		}
	             		if(tmp1.length()>50)
	             			numflag=10;
	            		//System.out.println(tmp1);
	            		
	            	}
	            	
//	            	System.out.println("原告："+yuangao);
//	            	System.out.println("上诉人："+shangshuren);
//	            	System.out.println("申请人："+shenqingren);
//	            	System.out.println("被告："+beigao);
//	            	System.out.println("被上诉人："+beishangshuren);
//	            	System.out.println("被执行人："+beizhixinren);
//	            	System.out.println("委托辩护人："+weitobianhuren);
//	            	System.out.println("当事人:"+dangshiren);
//	            	System.out.println("当事人all:"+dangshirenAll);
	            	
	            	//判决时间	           
	            	if(judgeDate.equals("")){
	            		int jnum=tempString.indexOf("书记员");
	            		if(jnum==-1)
	            			jnum=tempString.indexOf("书　记　员");
	            		if(jnum>-1){
	            			String jdate=tempString.substring(jnum-15,jnum);
	            			//System.out.println(jdate);
	            			try{
	            				if(jdate.indexOf("二")>-1)
	            					judgeDate=jdate.substring(jdate.indexOf("二"));
	            			}catch(Exception o){
	            				o.printStackTrace();
	            			}
	            		}
	            	}
	            	judgeDate=judgeDate.replaceAll("代理", "");
	            	judgeDate=judgeDate.replaceAll("\002", "");
	            	//System.out.println("审判时间:"+judgeDate);
	            	
	            	//判决结果抽取
	            	//flag=false;
	                int  num=5;
	                //System.out.println(termList);
	                int begin=merge.indexOf("判决如下：");
	                if(begin==-1)
	                	begin=merge.indexOf("裁定如下：");	 
	                if(begin==-1){
	                	begin=merge.indexOf("裁定如下");	
	                	num=4;
	                }
	                if(begin==-1){
	                	begin=merge.indexOf("判决如下");	
	                	num=4;
	                }
	                if(begin==-1)
	                	begin=merge.indexOf("判决如下：");
	                if(begin==-1)
	                	begin=merge.indexOf("本院认为：");
	                if(begin==-1)
	                	begin=merge.indexOf("本院认为，");	 
	                if(begin==-1)
	                	begin=merge.indexOf("决定如下：");	 
	                if(begin==-1){
	                	begin=merge.indexOf("本院认为");	
	                	num=4;
	                }	                	              	                               
	                int end=merge.indexOf("审　判　长");
	                if(end==-1)
	                	end=merge.indexOf("审判员　");
	                if(end==-1)
	                	end=merge.indexOf("审判长　");
	                if(end==-1)
	                	end=merge.indexOf("审判长　");
	                if(end==-1)
	                	end=merge.length();
	                
	                String strResult="";
	                if(begin!=-1&&end!=-1&&begin<end){
	                	strResult=merge.substring(begin+num, end);
	                }
	                if(jhao>-1&&end>-1&&jhao<end)	                	
	                	judgeProceed=merge.substring(jhao+1,end);
	                if(judgeProceed.startsWith("\002"))
	                	judgeProceed=judgeProceed.substring(1);
	                	  
	                strResult=strResult.replaceAll("\002", "");
               		//System.out.println((n++)+"***********"+strResult);
	                
	                //判决胜负结果
	                pred_label=getResult(strResult,pp,classifier);
	            	//System.out.println("***********"+pred_label);
	            	
	                //摘要
	            	//List<String> sentenceList = HanLP.extractSummary(merge, 3);
	            	
	            	//jsummary=sentenceList.toString();
	                try{
		            	List<String> sentenceList = HanLP.extractSummary(merge, 1);
		            	if(sentenceList.size()>0)
		            		jsummary=sentenceList.get(0);
	                }catch(Exception o){
	                	
	                }
	            	
	           /*     if(judgeProceed.length()>50){
	                	//System.out.println("2"+judgeProceed.substring(50));
	                    jsummary=judgeProceed.substring(0, judgeProceed.substring(50).indexOf("。")+51);
	                }
	                else
	                    jsummary=judgeProceed.substring(0,judgeProceed.length());
	     */           
	           	    //System.out.println("##"+merge);
	            	//System.out.println(judgeProceed);
	            	//System.out.println(jsummary);
	            	//System.out.println(tempString);
	            	
	            	//案由
	            	//System.out.println("##"+tempString);
	                BaseSearcher searcher = CustomDictionary.getSearcher(tempString);
	                Map.Entry entry;
	                if ((entry = searcher.next()) != null)
	                {
	                	jcase=entry.getKey().toString();
	                    //System.out.println("案由:"+jcase);
	                    
	                }
	            	
	    
		            
	               	fileWritter.write(oldstr+"\001"+courtType+"\001"+yuangao+"\001"+beigao+"\001"+shangshuren+"\001"+beishangshuren+"\001"+weitobianhuren+"\001"+dangshiren+"\001"+dangshirenAll+"\001"+judgeDate+"\001"+jcase+"\001"+judgeProceed+"\001"+jsummary+"\001"+identityCard+"\001"+strResult+"\001"+pred_label+"\n\r");	                		        
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
