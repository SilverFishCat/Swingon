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

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

import silver.json.swing.JsonTreeConstraints.JsonPath;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * A tree cell editor for json objects, allowing only values from a list
 * (or values from a list plus an open editor).
 * 
 * @author SilverFishCat
 *
 */
public class ConstrainedJsonTreeCellEditor implements TreeCellEditor {	
	private DefaultCellEditor _innerEditor;
	private JComboBox<JsonElement> _possibleValuesComboBox;
	private JsonTreeConstraints _constraints;
	
	/**
	 * Create a new constrained json tree cell editor.
	 * 
	 * @param constraints The constraints to use in the editor
	 */
	public ConstrainedJsonTreeCellEditor(JsonTreeConstraints constraints){
		_possibleValuesComboBox = new JComboBox<JsonElement>();
		_innerEditor = new DefaultCellEditor(_possibleValuesComboBox);
		setConstraints(constraints);
	}
	
	/**
	 * Set the constraint for the editor.
	 * 
	 * @param constraints The constraints for the editor
	 */
	public void setConstraints(JsonTreeConstraints constraints){
		_constraints = constraints;
	}
	/**
	 * Get the editor's constraint.
	 * 
	 * @return The editor's constraints
	 */
	public JsonTreeConstraints getConstraints(){
		return _constraints;
	}
	
	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row) {
		setUpComboBox(tree.getPathForRow(row));
		return _innerEditor.getTreeCellEditorComponent(tree, ((JsonTreeNode) value).getValue(), isSelected, expanded,
				leaf, row);
	}
	
	/**
	 * Set up the combobox of options for the given path.
	 * 
	 * @param treePath The path to set the combobox for
	 */
	private void setUpComboBox(TreePath treePath){
		_possibleValuesComboBox.removeAllItems();
		
		JsonPath jsonPath = toJsonPathFromTreePath(treePath);
		
		for (JsonElement element : _constraints.getPossibleValues(jsonPath)) {
			_possibleValuesComboBox.addItem(element);
		}
		_possibleValuesComboBox.setEditable(_constraints.isFreelyEditable(jsonPath));
	}
	/**
	 * Get the json path from a swing tree path.
	 * 
	 * @param path The tree path to convert
	 * @return The json path matching to the given tree path
	 */
	private static JsonPath toJsonPathFromTreePath(TreePath path){
		JsonPath result = new JsonPath();
		
		for (int i = 1; i < path.getPathCount(); i++) {
			JsonTreeNode node =  (JsonTreeNode) path.getPathComponent(i);
			
			result.add(new JsonPath.Element(node.getName()));
		}
		
		return result;
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {
		_innerEditor.addCellEditorListener(l);
	}
	@Override
	public void cancelCellEditing() {
		_innerEditor.cancelCellEditing();
	}
	@Override
	public Object getCellEditorValue() {
		Object value = _innerEditor.getCellEditorValue();
		
		if(value instanceof JsonElement)
			return value;
		else if(value instanceof String)
			return new JsonParser().parse((String) value);
		
		return null;
	}
	@Override
	public boolean isCellEditable(EventObject anEvent) {
		MouseEvent event = (MouseEvent) anEvent;
		JTree tree = (JTree) event.getSource();
		
		JsonTreeConstraints.Node node;
		if(_constraints == null)
			node = null;
		else
			node = _constraints.getNodeAtPath(toJsonPathFromTreePath(tree.getPathForLocation(event.getX(), event.getY())));
		
		if(node != null)
			return node.isEditable();
		else
			return false;
	}
	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		_innerEditor.removeCellEditorListener(l);
	}
	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return _innerEditor.shouldSelectCell(anEvent);
	}
	@Override
	public boolean stopCellEditing() {
		return _innerEditor.stopCellEditing();
	}
}
