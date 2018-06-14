package webdev.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Widget {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JsonIgnore
	private Topic topic;
	private String text;
	private String name;
	//Heading, Paragraph, List, Image
	private String widgetType;
	private int position;
	private int size;
	private String href;
	private String src;
	private String listItems;
	enum ListType {
		ordered, unordered
	};
	private ListType listType;
	private String className;
	private String width;
	private String height;
	private String style;
	
	
	
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getListItems() {
		return listItems;
	}

	public void setListItems(String listItems) {
		this.listItems = listItems;
	}

	public ListType getListType() {
		return listType;
	}

	public void setListType(ListType listType) {
		this.listType = listType;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Topic getTopic() {
		return this.topic;
	}
	
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	
	public String getWidgetType() {
		return widgetType;
	}
	public void setWidgetType(String widgetType) {
		this.widgetType = widgetType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	
	
}
