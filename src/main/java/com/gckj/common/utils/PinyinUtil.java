package com.omysoft.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;   
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;   
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;   
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;   
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;   
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination; 
  
/** 
 * 取得给定汉字串的首字母串,即声母串 
 * Title: ChineseCharToEn 
 * @date 2004-02-19 注：只支持GB2312字符集中的汉字 
 */  
public final class PinyinUtil {  
	  /** 汉语拼音格式化工具类 */  
    private static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();  
  
      
    /** 
     * 获取字符串内的所有汉字的汉语拼音 
     * @param src 
     * @return 
     */  
    public static String spell(String src) {  
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE); // 小写拼音字母  
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // 不加语调标识  
        format.setVCharType(HanyuPinyinVCharType.WITH_V); // u:的声母替换为v  
          
        StringBuffer sb = new StringBuffer();  
        int strLength = src.length();  
        try {  
            for (int i = 0; i < strLength; i++) {  
                // 对英文字母的处理：小写字母转换为大写，大写的直接返回  
                char ch = src.charAt(i);  
                if (ch >= 'a' && ch <= 'z')  
                    sb.append((char) (ch - 'a' + 'A'));  
                if (ch >= 'A' && ch <= 'Z')  
                    sb.append(ch);  
                // 对汉语的处理  
                String[] arr = PinyinHelper.toHanyuPinyinStringArray(ch, format);  
                if (arr != null && arr.length > 0)  
                    sb.append(arr[0]).append(" ");  
            }  
        } catch (BadHanyuPinyinOutputFormatCombination e) {  
            e.printStackTrace();  
        }  
        return sb.toString();  
    }  
  
    /** 
     * 获取字符串内的所有汉字的汉语拼音并大写每个字的首字母 
     * @param src 
     * @return 
     */  
    public static String spellWithTone(String src) {  
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写  
        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);// 标声调  
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);// u:的声母  
          
        if (src == null) {  
            return null;  
        }  
        try {  
            StringBuilder sb = new StringBuilder();  
            for (int i = 0; i < src.length(); i++) {  
                // 对英文字母的处理：小写字母转换为大写，大写的直接返回  
                char ch = src.charAt(i);  
                if (ch >= 'a' && ch <= 'z')  
                    sb.append((char) (ch - 'a' + 'A'));  
                if (ch >= 'A' && ch <= 'Z')  
                    sb.append(ch);  
                // 对汉语的处理  
                String[] arr = PinyinHelper.toHanyuPinyinStringArray(ch, format);  
                if (arr == null || arr.length == 0) {  
                    continue;  
                }  
                String s = arr[0];// 不管多音字,只取第一个  
                char c = s.charAt(0);// 大写第一个字母  
                String pinyin = String.valueOf(c).toUpperCase().concat(s.substring(1));  
                sb.append(pinyin).append(" ");  
            }  
            return sb.toString();  
        } catch (BadHanyuPinyinOutputFormatCombination e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    /** 
     * 获取字符串内的所有汉字的汉语拼音并大写每个字的首字母 
     * @param src 
     * @return 
     */  
    public static String spellNoneTone(String src) {
        // 小写
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 标声调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // u:的声母
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
          
        if (src == null) {  
            return null;  
        }  
        try {  
            StringBuilder sb = new StringBuilder();  
            for (int i = 0; i < src.length(); i++) {
                //对英文字母的处理：小写字母转换为大写，大写的直接返回
                char ch = src.charAt(i);
                if (ch >= 'a' && ch <= 'z')  
                    sb.append((char) (ch - 'a' + 'A'));  
                if (ch >= 'A' && ch <= 'Z')  
                    sb.append(ch);  
                // 对汉语的处理  
                String[] arr = PinyinHelper.toHanyuPinyinStringArray(ch, format);  
                if (arr == null || arr.length == 0) {  
                    continue;  
                }
                // 不管多音字,只取第一个
                String s = arr[0];
                // 大写第一个字母
                char c = s.charAt(0);
                //全拼
                //String pinyin = String.valueOf(c).toUpperCase().concat(s.substring(1));
                //首拼
                String pinyin = String.valueOf(c).toUpperCase();
                sb.append(pinyin).append("");  
            }  
            return sb.toString();  
        } catch (BadHanyuPinyinOutputFormatCombination e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * 获取汉语第一个字的首英文字母 
     * @param src 
     * @return 
     */  
    public static String getTerm(String src){  
        String res = spell(src);  
        if(res!=null&&res.length()>0){  
            return res.toUpperCase().charAt(0)+"";  
        }else{  
            return "OT";  
        }  
    }

//    public static void main(String[] args) {
//        System.out.println(spellWithTone("中华人民共和国"));
//        System.out.println(spell("中华人民共和国"));
//        System.out.println(spellNoneTone("中华人民共和国"));
//        System.out.println(getTerm("中华人民共和国"));
//
//        String code = spellNoneTone("中华人");
//
//        int num = 5 - code.length();
//        System.out.println(String.format("%s%0"+ num +"d",code,0));
//        code = code.substring(code.length() - 1,code.length());
//        System.out.println(code);
//
//        code = "CSZZJ01";
//        int n = Integer.parseInt(code.substring(code.length() - 2,code.length()));
//        n++;
//        code = String.format("%s%02d", code.substring(0,code.length() - 2),n);
//
//        System.out.println(code);
//     }
  
}  