package top.i7un.springboot.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * 
 * 使用集合Map对象来创建一个XML文件内容字符串  ,建立在Dom4J之上
 * @date 2010-11-01
 * @author Kenny
 *
 */
public class XMLUtilsByDom4J {

	/*@Test
	public void demo() throws Exception {
		// 1、构造种子 Map 对象
		Map<String, Object> seed = new HashMap<String, Object>();

		seed.put("name", "dom4j");

		Map<String, String> attributeMap = new HashMap<String, String>();
		attributeMap.put("id", "manyResult");
		attributeMap.put("name", "manyResult");
		seed.put("attribute", attributeMap);

		List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
		Map<String, Object> subSeedA = new HashMap<String, Object>();
		subSeedA.put("name", "sub-ms-A");
		Map<String, String> subAttributeMapA = new HashMap<String, String>();
		subAttributeMapA.put("id", "sub-attribute-A");
		subAttributeMapA.put("name", "sub-attribute-A");
		subSeedA.put("attribute", subAttributeMapA);
		subSeedA.put("content", "通过");

		Map<String, Object> subSeedB = new HashMap<String, Object>();
		subSeedB.put("name", "sub-ms-B");
		List<Map<String, Object>> subSeedContentB = new ArrayList<Map<String, Object>>();
		Map<String, Object> subSeedMapB = new HashMap<String, Object>();
		subSeedMapB.put("name", "sub-ms-BS");
		Map<String, String> subAttributeMapB = new HashMap<String, String>();
		subAttributeMapB.put("id", "sub-attribute-B");
		subAttributeMapB.put("name", "sub-attribute-B");
		subSeedMapB.put("attribute", subAttributeMapB);
		subSeedMapB.put("content", "test");
		subSeedContentB.add(subSeedMapB);

		subSeedB.put("content", subSeedContentB);

		contentList.add(subSeedA);
		contentList.add(subSeedB);
		seed.put("content", contentList);

		// write XML
		String xmlContent = toXML(seed);
		File file = new File("c:/xmlUtils.xml");
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(xmlContent.getBytes());
		fos.flush();
		System.out.println(xmlContent);

		System.out.println("--------------");

		// read XML
		Map<String, Object> xmlMap = fromXML(file);
		System.out.println("XML entry" + xmlMap.entrySet());
	}*/

	/**
	 * 
	 * @param seed
	 * @return The contents of XML documents
	 * @throws IOException
	 */
	public static String toXML(Map<String, Object> seed) throws IOException {

		Document doc = createXMLStruct(seed);
		String content = doc.asXML();
		return content;
	}

	private static Document createXMLStruct(Map<String, Object> seed) {
		Document doc = DocumentHelper.createDocument();
		createXMLSubStruct(doc, seed);
		return doc;
	}

	@SuppressWarnings("unchecked")
	private static void createXMLSubStruct(Node doc, Map<String, Object> seed) {
		String seedname = seed.get("name").toString();
		if (seedname == null || seedname.trim().length() == 0) {
			return;
		}
		Map<String, String> attributMap = (Map<String, String>) seed.get("attribute");
		Object contentObj = seed.get("content");

		// 元素节点
		Element root;
		if (doc instanceof Document) {
			root = ((Document) doc).addElement(seedname);
		} else {
			root = ((Element) doc).addElement(seedname);
		}

		// 节点属性
		if (attributMap != null) {
			Set<String> attributeSet = attributMap.keySet();
			for (String attributeName : attributeSet) {
				root.addAttribute(attributeName, attributMap.get(attributeName));
			}
		}

		// 节点儿子
		if (contentObj instanceof List<?>) {
			List<Map<String, Object>> subSeeds = (List<Map<String, Object>>) contentObj;
			for (Map<String, Object> subSeed : subSeeds) {
				createXMLSubStruct(root, subSeed);
			}
		} else {
			root.setText( contentObj.toString());
		}

		return;
	}

	public static Map<String, Object> fromXML(String source) throws Exception {
		Map<String, Object> xmlMap = new HashMap<String, Object>();

		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new ByteArrayInputStream(source.getBytes()));

		Element root = document.getRootElement();

		fromSubXML(root, xmlMap);

		return xmlMap;
	}
	
	public static Map<String, Object> fromXML(File xmlFile) throws Exception {
		Map<String, Object> xmlMap = new HashMap<String, Object>();

		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(xmlFile);

		Element root = document.getRootElement();

		fromSubXML(root, xmlMap);

		return xmlMap;
	}

	@SuppressWarnings("unchecked")
	private static void fromSubXML(Element root, Map<String, Object> xmlMap) throws DocumentException {
		// 节点名称
		xmlMap.put("name", root.getName());
		// 节点属性
		Map<String, String> attributeMap = new HashMap<String, String>();
		Iterator<Attribute> attributes = root.attributeIterator();
		if (attributes != null) {
			while (attributes.hasNext()) {
				Attribute attribute = attributes.next();
				String attrName = attribute.getName();
				String attrValue = attribute.getValue();
				attributeMap.put(attrName, attrValue);
			}
		}
		xmlMap.put("attribute", attributeMap);
		// 节点儿子
		List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
		if (root.isTextOnly()) {
			xmlMap.put("content", root.getText());
		} else {
			Iterator<Element> contents = root.elementIterator();
			if (contents != null) {
				while (contents.hasNext()) {
					Element element = contents.next();
					Map<String, Object> xmlSubMap = new HashMap<String, Object>();
					fromSubXML(element, xmlSubMap);
					contentList.add(xmlSubMap);
					xmlMap.put("content", contentList);
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	public static Map<String , Object> formXMLSingle(String source) throws Exception {
		Map<String, Object> xmlMap = new HashMap<String, Object>();

		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new ByteArrayInputStream(source.getBytes()));

		Element root = document.getRootElement();
		Iterator<Element> eleIter = root.elementIterator();
		while(eleIter.hasNext()){
			Element element = eleIter.next();
			String nodeName = element.getName();
			List<Element> el = element.elements();
			if(el.isEmpty()){
				String text = element.getTextTrim();
				xmlMap.put(nodeName, text);
			}else{
				StringBuffer sbf = new StringBuffer();
				for(Iterator<Element> i = el.iterator();i.hasNext();){
					Element element1 = i.next();
					sbf.append(element1.asXML());
				}
				xmlMap.put(nodeName, sbf);
			}
		}
		eleIter.remove();
		return xmlMap;

	}
}
