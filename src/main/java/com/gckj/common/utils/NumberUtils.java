package com.omysoft.common.utils;

/**
 * ClassName: NumberUtil
 * @Description: TODO
 * @author yy
 * @date 2014-10-16
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils{
	
	public static String automaticZero(int digit, int defaultMaxLen){
		StringBuffer buffer = new StringBuffer();
		String digitalCharacter = String.valueOf(digit);
		for(int i=0, len=digitalCharacter.length(); i < defaultMaxLen-len; i++){
			buffer.append("0");
		}
		return buffer.append(digitalCharacter).toString();
    }
	
	/**
     * 将输入金额num转换为汉字大写格式
     * @param num 输入金额（小于10000000）
     * @return 金额的大写格式
     */
    
    public static String translate(double num){
        String[] upChinese={"零","壹","贰","叁","肆","伍","陆","柒","捌","玖",};
        String[] upChinese2={"分","角","圆","拾","佰","仟","萬","拾","佰","仟","亿","拾","佰","仟","兆"};
        StringBuffer result=new StringBuffer();
        int count=0;
        int zeroflag=0;
        boolean mantissa=false;
        if(num<0){                      //输入值小于零
            return "输入金额不能为负数！";
        }
        if(num==0){                     //输入值等于零
            return "零";
        }
        if(String.valueOf(num).indexOf('E')!=-1){ //输入值过大转为科学计数法本方法无法转换
            return "您输入的金额过大";
        }
        int tem=(int)(num*100);
        if(tem%100==0){                           //金额为整时
            if(tem==0)return "穷鬼别来！";         //输入额为e:0.0012小于分计量单位时  
            result.insert(0, "整");
            tem=tem/100;
            count=2;
            mantissa=true;
        }
        while(tem>0){
              
            int t=(int)tem%10;                    //取得最后一位
            if(t!=0){                             //最后一位不为零时  
                if(zeroflag>=1){                  //对该位前的单个或多个零位进行处理  
                    if(((!mantissa)&&count==1)){                    //不是整数金额且分为为零
                        
                    }else if(count>2&&count-zeroflag<2){            //输入金额为400.04小数点前后都有零
                        
                            result.insert(1,"零");
                       
                    }else if(count>6&&count-zeroflag<6&&count<10){  //万位后为零且万位为零
                        if(count-zeroflag==2){                      //输入值如400000
                            result.insert(0,"萬");
                        }else{
                            result.insert(0,"萬零");                 //输入值如400101 
                        }
                    }else if(count>10&&count-zeroflag<10){
                        if(count-zeroflag==2){
                            result.insert(0,"亿");
                        }else{
                            result.insert(0,"亿零");
                        }
                        
           
                    }else if(((count-zeroflag)==2)){                //个位为零
                        
                    }else if(count>6&&count-zeroflag==6&&count<10){ //以万位开始出现零如4001000
                        result.insert(0,"萬");
                    }else if(count==11&&zeroflag==1){               
                        result.insert(0,"亿");
                    }else{
                        result.insert(0,"零");
                    }
                    
                }           
                result.insert(0,upChinese[t]+ upChinese2[count]); 
                zeroflag=0;
                
            }else{
                if(count==2){
                    result.insert(0,upChinese2[count]);             //个位为零补上"圆"字
                }
                zeroflag++;
            }
            tem/=10;
            System.out.println("count="+count+"---zero="+zeroflag+"----"+result.toString());
            count++;
            
            if(count>20)break;      
        }
        return result.toString();
    }
    
    // 将数字转化为大写  
    public static String numToUpper(Double num) {  
        String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};  
        //String u[] = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };  
        char[] str = String.valueOf(num).toCharArray();  
        String rstr = "";  
        for (int i = 0; i < str.length; i++) {  
        	if(".".equals(str[i]+"")){
        		rstr = rstr + "点";  
        	}else{
        		rstr = rstr + u[Integer.parseInt(str[i] + "")];  
        	}
        }  
        return rstr;  
    }  
    
    /*public static void main(String[] args) {
    	String aa = translate(2152272);
    	System.out.println(aa);
    	String baiwan = "";
    	String wan = "";
    	String qian = "";
    	String bai = "";
    	String shi = "";
    	String yuan = "";
    	String jiao = "";
    	if(aa.indexOf("佰")<aa.indexOf("萬")){
    		baiwan = aa.substring(aa.indexOf("佰")-1,aa.indexOf("佰"));
    		System.out.println(baiwan);
    		wan = aa.substring(aa.indexOf("佰")+1,aa.indexOf("萬"));
    		System.out.println(wan);
    	}else if(aa.indexOf("萬")>=0){
    		wan = aa.substring(0,aa.indexOf("萬"));
    		System.out.println(wan);
    	}
    	if(aa.indexOf("萬")<aa.indexOf("仟")){
    		qian = aa.substring(aa.indexOf("萬")+1,aa.indexOf("仟"));
    		System.out.println(qian);
    	}else if(aa.indexOf("仟")>=0){
    		qian = aa.substring(0,aa.indexOf("仟"));
    		System.out.println(qian);
    	}
    	if(aa.indexOf("仟")<aa.lastIndexOf("佰")){
    		bai = aa.substring(aa.indexOf("仟")+1,aa.lastIndexOf("佰"));
    		System.out.println(bai);
    	}else if(aa.lastIndexOf("佰")>=0){
    		bai = aa.substring(0,aa.lastIndexOf("佰"));
    		System.out.println(bai);
    	}
    	if(aa.lastIndexOf("佰")<aa.lastIndexOf("拾")){
    		shi = aa.substring(aa.lastIndexOf("佰")+1,aa.lastIndexOf("拾"));
    		System.out.println(shi);
    	}else if(aa.lastIndexOf("拾")>=0){
    		shi = aa.substring(0,aa.lastIndexOf("拾"));
    		System.out.println(shi);
    	}
    	if(aa.lastIndexOf("拾")<aa.indexOf("圆")){
    		yuan = aa.substring(aa.lastIndexOf("拾")+1,aa.indexOf("圆"));
    		System.out.println(yuan);
    	}else if(aa.indexOf("圆")>=0){
    		yuan = aa.substring(0,aa.indexOf("圆"));
    		System.out.println(yuan);
    	}
    	if(aa.indexOf("圆")<aa.indexOf("角")){
    		jiao = aa.substring(aa.indexOf("圆")+1,aa.indexOf("角"));
    		System.out.println(jiao);
    	}else if(aa.indexOf("角")>=0){
    		jiao = aa.substring(0,aa.indexOf("角"));
    		System.out.println(jiao);
    	}
    	System.out.println(baiwan+"佰"+wan+"萬"+qian+"仟"+bai+"佰"+shi+"拾"+yuan+"圆"+jiao+"角");
	}*/
}
