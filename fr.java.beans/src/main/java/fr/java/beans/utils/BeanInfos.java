package fr.java.beans.utils;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;

import fr.java.beans.annotations.BeanPropertyTag;

public class BeanInfos {

	public static String beanInfoToXML(Class<?> clazz) {
		StringBuilder sb = new StringBuilder();
		String tab = "\t";

		BeanDescriptor beanDescriptor = Beans.getBeanDescriptor(clazz);

		sb.append(xml_beginBeanDescriptor(beanDescriptor, tab));

			sb.append(xml_beginAttributes(tab));
			while(beanDescriptor.attributeNames().hasMoreElements()) {
				String attr = beanDescriptor.attributeNames().nextElement();
				sb.append("<Attribute>" + attr + " : " + beanDescriptor.getValue(attr) + "<Attribute>");
			}
			sb.append(xml_endAttributes(tab));

			sb.append(xml_beginProperties(tab));
			for(PropertyDescriptor property : Beans.getPropertyDescriptors(clazz)) {
				sb.append(xml_addPropertyDescriptor(property, tab + tab, tab));
			}
			sb.append(xml_endProperties(tab));

		sb.append(xml_endBeanDescriptor());

		return sb.toString();
	}

	private static String xml_beginBeanDescriptor(BeanDescriptor beanDescriptor, String _tab) {
		StringBuilder sb = new StringBuilder();

		sb.append("<BeanDescriptor>" + "\n");
		sb.append(_tab + "<name>" + beanDescriptor.getName() + "</name>" + "\n");
		sb.append(_tab + "<display>" + beanDescriptor.getDisplayName() + "</display>" + "\n");
		sb.append(_tab + "<description>" + beanDescriptor.getShortDescription() + "</description>" + "\n");
		sb.append(_tab + "<flags>" + (beanDescriptor.isExpert()    ? "expert" : "standard")
					   + (beanDescriptor.isHidden()    ? ", hidden" : "")
					   + (beanDescriptor.isPreferred() ? ", preferred" : "") + "</flags>" + "\n");

		return sb.toString();
	}
	private static String xml_endBeanDescriptor() {
		return "</BeanDescriptor>" + "\n";
	}

	private static String xml_beginAttributes(String tab) {
		return tab + "<Attributes>" + "\n";
	}
	private static String xml_endAttributes(String tab) {
		return tab + "</Attributes>" + "\n";
	}

	private static String xml_beginProperties(String tab) {
		return tab + "<Properties>" + "\n";
	}
	private static String xml_endProperties(String tab) {
		return tab + "</Properties>" + "\n";
	}
	
	private static String xml_addPropertyDescriptor(PropertyDescriptor property, String tab, String tab2) {
		StringBuilder sb = new StringBuilder();

		sb.append(tab + "<PropertyDescriptor>" + "\n");
		sb.append(tab + tab2 + "<name>" + property.getName() + "</name>" + "\n");
		sb.append(tab + tab2 + "<display>" + property.getDisplayName() + "</display>" + "\n");
		sb.append(tab + tab2 + "<description>" + property.getShortDescription() + "</description>" + "\n");
		sb.append(tab + tab2 + "<category>" + property.getValue(BeanPropertyTag.CATEGORY_PROPERTY) + "</category>" + "\n");

		String getter = property.getReadMethod() != null ? property.getReadMethod().getName() : "-";
		String setter = property.getWriteMethod() != null ? property.getWriteMethod().getName() : "-";

		sb.append(tab + tab2 + "<accessors>[ " + getter + " / " + setter + " ]</accessors>" + "\n");

		sb.append(tab + tab2 + "<flags>" + (setter == "-"          ? "read only, " : "")
							 + (property.isExpert()    ? "expert" : "standard")
							 + (property.isHidden()    ? ", hidden" : "")
							 + (property.isPreferred() ? ", preferred" : "") 
							 + (property.isConstrained() ? ", constrained" : "") 
							 + (property.isBound() ? ", bounded" : "") + "</flags>" + "\n");

		sb.append(tab + "</PropertyDescriptor>" + "\n");
		
		return sb.toString();
	}

}
