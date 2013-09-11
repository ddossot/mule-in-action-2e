package com.prancingdonkey.product.transformer;

import javax.xml.xpath.XPathExpressionException;

import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;
import org.mule.module.xml.util.XMLUtils;
import org.w3c.dom.Node;

public class ProductXmlToProductTransformer extends AbstractTransformer {

	@Override
	protected Object doTransform(Object src, String enc)
			throws TransformerException {
		
		try {
			org.w3c.dom.Document productDocument = XMLUtils.toW3cDocument(src);
			XMLUtils.selectValue("//name", productDocument);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	

}
