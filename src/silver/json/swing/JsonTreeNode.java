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
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * A node in a json tree.
 * 
 * @author SilverFishCat
 *
 */
public class JsonTreeNode {
	private JsonElement _value;
	private String _elementName;
	private List<JsonTreeNode> _children;
	
	/**
	 * Create a new json tree node.
	 * 
	 * @param value The json element of this node
	 */
	public JsonTreeNode(JsonElement value){
		this(value, (String)null);
	}
	/**
	 * Create a new json tree node.
	 * 
	 * @param value The json element of this node
	 * @param elementName The name of the element
	 */
	public JsonTreeNode(JsonElement value, String elementName){
		setValue(value);
		if(elementName == null)
			this._elementName = null;
		else
			this._elementName = elementName;
	}
	
	/**
	 * Get the json element of this node.
	 * 
	 * @return The json element of this node
	 */
	public JsonElement getValue(){
		return _value;
	}
	/**
	 * Set the value of this node, creating the child nodes.
	 * 
	 * @param value The value of this node
	 */
	public void setValue(JsonElement value){
		setValue(value, true);
	}
	/**
	 * Set the value of this node, creating the children only if told to.
	 * 
	 * @param value The value of this node
	 * @param createChildren Whether to create the children or not
	 */
	public void setValue(JsonElement value, boolean createChildren){
		_value = value;
		if(createChildren)
			createChildren();
	}
	/**
	 * Get the name of the node, if it's a subnode to an object.
	 * 
	 * @return The name of the node
	 */
	public String getName(){
		return _elementName;
	}
	/**
	 * Set the name of the node.
	 * 
	 * @param name The name of the node
	 */
	public void setName(String name){
		_elementName = name;
	}
	/**
	 * Get all the child nodes of this node.
	 * 
	 * @return This node's children nodes
	 */
	public List<JsonTreeNode> getChildren(){
		return new ArrayList<>(_children);
	}
	
	/**
	 * Check if the node is a leaf node.
	 * 
	 * @return True if a json primitive or null, false otherwise
	 */
	public boolean isLeaf(){
		return (!_value.isJsonArray()) && (!_value.isJsonObject());
	}
	@Override
	public String toString() {
		String name = _elementName;
		String valueString;
		if(_value.isJsonPrimitive()){
			valueString = _value.toString();
		}
		else if(_value.isJsonNull()){
			valueString = "null";
		}
		else{
			valueString = "";
		}
		
		if(name == null || name.equals("")){
			name = "";
		}
		else if(valueString == null || valueString.equals("")){
			valueString = "";
		}
		else{
			return name + " : " + valueString;
		}
		
		return name + valueString;
	}
	
	/**
	 * Create the childrens of this node.
	 */
	private void createChildren(){
		_children = new ArrayList<JsonTreeNode>();
		
		if(_value.isJsonArray()){
			JsonArray array = _value.getAsJsonArray();
			
			for (JsonElement jsonElement : array) {
				_children.add(new JsonTreeNode(jsonElement));
			}
		}
		else if(_value.isJsonObject()){
			JsonObject object = _value.getAsJsonObject();
			
			for (Entry<String, JsonElement> entry : object.entrySet()) {
				_children.add(new JsonTreeNode(entry.getValue(), entry.getKey()));
			}
		}
	}
}
