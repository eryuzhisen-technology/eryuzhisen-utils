package com.eryuzhisen.utils;

import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;

import com.eryuzhisen.utils.log.ErYuLogger;

public class HtmlUtil {
    public static int getHtmlTextCount(String html) {
        try {
            Parser parser = new Parser(html);
            TextExtractingVisitor visitor = new TextExtractingVisitor();  
            parser.visitAllNodesWith(visitor);  
//            System.out.println(visitor.getExtractedText()); 
            return visitor.getExtractedText().length();
        } catch (ParserException e) {
            ErYuLogger.error("getHtmlTextCount error:",e);
        } 
        return 0;
    }
    
    public static void main(String[] args) {
        String html = "<p><img src='http://eryu.oss-cn-shanghai.aliyuncs.com/opus/XN7yEKwRaC.jpg'></p><p><img src=\"http://eryu.oss-cn-shanghai.aliyuncs.com/opus/7X5n4RAY8R.jpg\"></p><p><img src=\"http://eryu.oss-cn-shanghai.aliyuncs.com/opus/r2FZcGpzFP.jpg\"></p><p><img src=\"http://eryu.oss-cn-shanghai.aliyuncs.com/opus/mipEKf4CtE.jpg\"></p><p><br></p>";
        HtmlUtil.getHtmlTextCount(html);
    }
}
