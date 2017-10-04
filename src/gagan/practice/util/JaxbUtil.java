package gagan.practice.util;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class JaxbUtil {

	public static String generateXMLStringFromObject(
			Class clazz, Object object) throws JAXBException {
		StringWriter stringWriter = new StringWriter();
		try {
			JAXBContext
					.newInstance(new Class[] { clazz })
					.createMarshaller().marshal(object, stringWriter);
		} catch (JAXBException e) {
			System.out.println("Exception encountered during Marshalling.");
			throw e;
		}
		return stringWriter.toString();

	}

	public static Object generateObjectFromXMLString(
			Class clazz, String xmlString) throws JAXBException {
		Object object = null;
		try {
			JAXBContext context = (JAXBContext) JAXBContext
					.newInstance(new Class[] { clazz });
			Unmarshaller unmarshaller = context.createUnmarshaller();
			ByteArrayInputStream byteArrIS = new ByteArrayInputStream(
					xmlString.getBytes());

			object = unmarshaller
					.unmarshal(byteArrIS);
		} catch (JAXBException e) {
			System.out.println("Exception encountered during Unmarshalling.");
			throw e;
		}
		return object;

	}

}
