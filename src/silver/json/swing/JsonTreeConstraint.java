package silver.json.swing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import silver.json.swing.JsonTreeConstraint.JsonPath.Element;

import com.google.gson.JsonElement;

public class JsonTreeConstraint {
	static public class Node { 
		private Map<String, Node> _childNodes;
		private List<JsonElement> _possibleValues;
		private boolean _isEditable;
		private boolean _isFreelyEditable;
		
		public Node(){
			setChildNodes(null);
			setPossibleValues(null);
			setIsEditable(false);
			setIsFreelyEditable(false);
		}
		
		public void setChildNodes(Map<String, Node> childNodes){
			_childNodes = childNodes;
			if(_childNodes == null)
				_childNodes = new HashMap<>();
		}
		public void setPossibleValues(List<JsonElement> possibleValues){
			_possibleValues = possibleValues;
			if(_possibleValues == null)
				_possibleValues = new ArrayList<>();
		}
		public void setIsEditable(boolean isEditable){
			_isEditable = isEditable;
		}
		public void setIsFreelyEditable(boolean isFreelyEditable){
			_isFreelyEditable = isFreelyEditable;
		}
		
		public Map<String, Node> getChildNodes(){
			return _childNodes;
		}
		public List<JsonElement> getPossibleValues() {
			return new ArrayList<JsonElement>(_possibleValues);
		}
		public boolean isEditable(){
			return _isEditable;
		}
		public boolean isFreelyEditable(){
			return _isFreelyEditable;
		}
	}
	static public class JsonPath extends ArrayList<JsonPath.Element> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 611451727340138564L;

		static public class Element{
			private String _childName;
			
			public Element(String childName){
				setChildName(childName);
			}
			
			public void setChildName(String newName){
				_childName = newName;
				if(_childName == null)
					_childName = "";
			}
			public String getChildName(){
				return new String(_childName);
			}
		}

		public JsonPath() {
			super();
		}

		public JsonPath(Collection<? extends Element> c) {
			super(c);
		}

		public JsonPath(int initialCapacity) {
			super(initialCapacity);
		}
	}

	
	private Node _root;
	
	public JsonTreeConstraint(Node rootNode){
		_root = rootNode;
	}
	
	public List<JsonElement> getPossibleValues(JsonPath path){
		Node nodeAtPath = getNodeAtPath(path);
		
		if(nodeAtPath != null)
			return nodeAtPath.getPossibleValues();
		
		else
			return new ArrayList<>();
	}
	public boolean isEditable(JsonPath path){
		Node nodeAtPath = getNodeAtPath(path);
		
		if(nodeAtPath != null)
			return nodeAtPath.isEditable();
		else
			return false;
	}
	public boolean isFreelyEditable(JsonPath path) {
		Node nodeAtPath = getNodeAtPath(path);
		
		if(nodeAtPath != null)
			return nodeAtPath.isFreelyEditable();
		else
			return false;
	}
	public Node getNodeAtPath(JsonPath path){
		Node currentNode = _root;
		
		for (Element element : path) {
			if(currentNode == null)
				break;
			
			currentNode = currentNode.getChildNodes().get(element.getChildName());
		}
		
		return currentNode;
	}
}
