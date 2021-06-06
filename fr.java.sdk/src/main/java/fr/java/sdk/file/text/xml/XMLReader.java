package fr.java.sdk.file.text.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XMLReader {
	private static DocumentBuilderFactory builderFactory     = DocumentBuilderFactory.newInstance();
	private static TransformerFactory     transformerFactory = TransformerFactory.newInstance();
	private static DocumentBuilder        builder     		 = null;
	private static Transformer            transformer 		 = null;

	protected Document document;

	static {
		try {
			builder     = builderFactory.newDocumentBuilder();
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public Element setRootNode(String _name) {
		document = builder.newDocument();
		Element root = document.createElement(_name);
		document.appendChild(root);
		return root;
	}

	public Element addElement(String _name, String _parent) {
		final Element newElement = document.createElement(_name);

		if(_parent.equalsIgnoreCase("root")) {
			getRootElement().appendChild(newElement);
		} else {
			((Element) document.getDocumentElement().getElementsByTagName(_parent).item(0)).appendChild(newElement);
		}

		return newElement;
	}
	public boolean addElement(Element _elt, String _parent) {
		if(_parent.equalsIgnoreCase("root")) {
			getRootElement().appendChild(_elt);
		} else {
			((Element) document.getDocumentElement().getElementsByTagName(_parent).item(0)).appendChild(_elt);
		}

		return true;
	}
	public boolean addElement(Element _elt, Element _parent) {
		_parent.appendChild(_elt);
		return true;
	}


	public Document getDocument() {
		return document;
	}
	public Element getRootElement() {
		return document.getDocumentElement();
	}
	
	public boolean checkRootNode(String _name) {
		if(document == null)
			return false;

		return document.getDocumentElement().getNodeName().compareToIgnoreCase(_name) == 0;
	}
	
	public Element getElement(String _name, int _index) {
		return (Element) document.getDocumentElement().getElementsByTagName( _name ).item(_index);
	}
	

	public void load(Path _path) throws FileNotFoundException {
		try {
			document = builder.parse(_path.toFile());
/*
	        System.out.println("HEADER");
	        System.out.println("path : "       + document.getDocumentURI());
	        System.out.println("version : "    + document.getXmlVersion());
	        System.out.println("encodage : "   + document.getXmlEncoding());
	        System.out.println("entrée : "     + document.getInputEncoding());
	        System.out.println("standalone : " + document.getXmlStandalone());
	        System.out.println("erreur : "     + document.getStrictErrorChecking());
*/
	        final Element root = document.getDocumentElement();
//	        System.out.println(root.getNodeName());

		} catch (SAXException | IOException e) {
			throw new java.io.FileNotFoundException();
		}
	}
	public void load(String _path) throws FileNotFoundException {
		try {
			document = builder.parse(new File(_path));
/*
	        System.out.println("HEADER");
	        System.out.println("path : "       + document.getDocumentURI());
	        System.out.println("version : "    + document.getXmlVersion());
	        System.out.println("encodage : "   + document.getXmlEncoding());
	        System.out.println("entrée : "     + document.getInputEncoding());
	        System.out.println("standalone : " + document.getXmlStandalone());
	        System.out.println("erreur : "     + document.getStrictErrorChecking());
*/
	        final Element root = document.getDocumentElement();
//	        System.out.println(root.getNodeName());

		} catch (SAXException | IOException e) {
			throw new java.io.FileNotFoundException();
		}
	}

}
