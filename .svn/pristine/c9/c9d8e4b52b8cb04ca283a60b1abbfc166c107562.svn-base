package com.penghaisoft.framework.util.ConvertFactory;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/** 
* 将对象转化为XML格式
* @author 刘立华
* @date 2017-08-25 
*/  
public class XMLConvert implements Convert{
	
	@Override
	public String convert(Object object) {
        // 创建输出流  
        StringWriter stringWriter = new StringWriter();  
        try {    
            JAXBContext context = JAXBContext.newInstance(object.getClass());  
            Marshaller marshaller = context.createMarshaller();  
            // 格式化xml输出的格式  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // 将对象转换成输出流形式的xml  
            marshaller.marshal(object, stringWriter); 
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }
        return stringWriter.toString(); 
	}  
	
} 
