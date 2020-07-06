package com.gckj.common.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description：JAXBXml工具类
 * @author：ldc
 * date：2020-06-23
 */
public class JAXBXmlUtils {
	
	protected static final Logger log = LoggerFactory.getLogger(JAXBXmlUtils.class);

	/**
	 * JAXBC将对象转xml
	 */
	public static String beanToXML(Object object) {
		String resultXml = "";
		if (object == null) {
			log.error("输入的参数为空！");
			return resultXml;
		}
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");  
			StringWriter writer = new StringWriter();
			jaxbMarshaller.marshal(object, writer);
			resultXml = writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		return resultXml;
	}
	
	/**
	 * JAXBC将xml转对象
	 */
    @SuppressWarnings("unchecked")
	public static <T> T xmlToBean(String xml, Class<?> clazz) {  
        try {  
        	JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            StringReader reader = new StringReader(xml);
            Unmarshaller unmarshaller= jaxbContext.createUnmarshaller(); 
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {  
            throw new RuntimeException(e);  
        }  
    }
	
}
