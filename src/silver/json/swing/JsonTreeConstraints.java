//The MIT License (MIT)
//
//Copyright (c) 2015 , SilverFishCat@GitHub
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.


package silver.json.swing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import silver.json.swing.JsonTreeConstraints.JsonPath.Element;

import com.google.gson.JsonElement;

/**
 * <p>A set of constraints for a json tree editor.</p>
 * 
 * <p>The constraints are a tree of possible json properties with possible values.</p>
 * 
 * @author SilverFishCat
 *
 */
public class JsonTreeConstraints {
	/**
	 * A set of constraints for a particular node, including all its child nodes. 
	 * 
	 * @author SilverFishCat
	 *
	 */
	static public class Node {
		private Map<String, Node> _childNodes;
		private List<JsonElement> _possibleValues;
		private boolean _isEditable;
		private boolean _isFreelyEditable;
		
		/**
		 * Create a new node constraints object.
		 */
		public Node(){
			this(null, null, false, false);
		}
		/**
		 * Create a new node constraints object.
		 * 
		 * @param childNodes The child nodes of this node constraints object
		 */
		public Node(Map<String, Node> childNodes){
			this(childNodes, null, false, false);
		}
		/**
		 * Create a new node constraints object.
		 * 
		 * @param childNodes The child nodes of this node constraints object
		 */
		public Node(List<JsonElement> possibleValues){
			this(possibleValues, false);
		}
		/**
		 * Create a new node constraints object.
		 * 
		 * @param possibleValues The possible values for this node
		 * @param isFreelyEditable Is this node free text editable
		 */
		public Node(List<JsonElement> possibleValues, boolean isFreelyEditable){
			this(null, possibleValues, possibleValues != null && possibleValues.size() > 0 ,false);
		}
		/**
		 * Create a new node constraints object.
		 * 
		 * @param childNodes The child nodes of this node constraints object
		 * @param possibleValues The possible values for this node
		 * @param isEditable Is this node editable at all
		 * @param isFreelyEditable Is this node free text editable
		 */
		public Node(Map<String, Node> childNodes, List<JsonElement> possibleValues, boolean isEditable, boolean isFreelyEditable){
			setChildNodes(childNodes);
			setPossibleValues(possibleValues);
			setIsEditable(isEditable);
			setIsFreelyEditable(isFreelyEditable);
		}
		
		/**
		 * Set the child nodes of this node.
		 * 
		 * @param childNodes The child nodes of this node
		 */
		public void setChildNodes(Map<String, Node> childNodes){
			_childNodes = childNodes;
			if(_childNodes == null)
				_childNodes = new HashMap<>();
		}
		/**
		 * Set the possible values for this node.
		 * 
		 * @param possibleValues The possible values for this node
		 */
		public void setPossibleValues(List<JsonElement> possibleValues){
			_possibleValues = possibleValues;
			if(_possibleValues == null)
				_possibleValues = new ArrayList<>();
		}
		/**
		 * Set whether this node is editable.
		 * 
		 * @param isEditable New editable status
		 */
		public void setIsEditable(boolean isEditable){
			_isEditable = isEditable;
		}
		/**
		 * Set whether the node is freely editable besides the default values.
		 * 
		 * @param isFreelyEditable New freely editable status
		 */
		public void setIsFreelyEditable(boolean isFreelyEditable){
			_isFreelyEditable = isFreelyEditable;
		}
		
		/**
		 * Get the child nodes of this node.
		 * 
		 * @return The node's child nodes
		 */
		public Map<String, Node> getChildNodes(){
			return _childNodes;
		}
		/**
		 * Get the possible values for this node.
		 * 
		 * @return The node's possible values.
		 */
		public List<JsonElement> getPossibleValues() {
			return new ArrayList<JsonElement>(_possibleValues);
		}
		/**
		 * Get whether the node is editable.
		 * 
		 * @return Whetehr the nope is editable
		 */
		public boolean isEditable(){
			return _isEditable;
		}
		/**
		 * Get whether the node is freely editable.
		 * 
		 * @return Whetehr the nope is freely editable
		 */
		public boolean isFreelyEditable(){
			return _isFreelyEditable;
		}
	}
	/**
	 * A path through a json tree.
	 * 
	 * @author SilverFishCat
	 *
	 */
	static public class JsonPath extends ArrayList<JsonPath.Element> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 611451727340138564L;

		/**
		 * A node element in a json path.
		 * 
		 * @author SilverFishCat
		 *
		 */
		static public class Element{
			private String _childName;
			
			/**
			 * Create a json path element.
			 * 
			 * @param childName The name of this element as property
			 */
			public Element(String childName){
				setChildName(childName);
			}
			
			/**
			 * Set this element's name.
			 * 
			 * @param newName The new element name
			 */
			public void setChildName(String newName){
				_childName = newName;
				if(_childName == null)
					_childName = "";
			}
			/**
			 * Get the element's name as property.
			 * 
			 * @return The element's name
			 */
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
	
	/**
	 * Create a new json tree constraint set.
	 * 
	 * @param rootNode The root constraint
	 */
	public JsonTreeConstraints(Node rootNode){
		_root = rootNode;
	}
	
	/**
	 * Get the possible values for a given json path.
	 * 
	 * @param path A json path
	 * @return The possible values for the given path
	 */
	public List<JsonElement> getPossibleValues(JsonPath path){
		Node nodeAtPath = getNodeAtPath(path);
		
		if(nodeAtPath != null)
			return nodeAtPath.getPossibleValues();
		
		else
			return new ArrayList<>();
	}
	/**
	 * Check whether the node in the given path is editable.
	 * 
	 * @param path A json path
	 * @return Whether the node in the given path is editable, false if node not found
	 */
	public boolean isEditable(JsonPath path){
		Node nodeAtPath = getNodeAtPath(path);
		
		if(nodeAtPath != null)
			return nodeAtPath.isEditable();
		else
			return false;
	}
	/**
	 * Check whether the node in the given path is freely editable.
	 * 
	 * @param path A json path
	 * @return Whether the node in the given path is freely editable, false if no node found
	 */
	public boolean isFreelyEditable(JsonPath path) {
		Node nodeAtPath = getNodeAtPath(path);
		
		if(nodeAtPath != null)
			return nodeAtPath.isFreelyEditable();
		else
			return false;
	}
	/**
	 * Get the node constraints at a given path.
	 * 
	 * @param path A json path
	 * @return The node constriants at the given path, null if not found
	 */
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
