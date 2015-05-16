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

import silver.json.swing.JsonTreeConstraint.JsonPath;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ConstrainedJsonTreeCellEditor implements TreeCellEditor {	
	private DefaultCellEditor _innerEditor;
	private JComboBox<JsonElement> _possibleValuesComboBox;
	private JsonTreeConstraint _constraint;
	
	public ConstrainedJsonTreeCellEditor(JsonTreeConstraint constraint){
		_possibleValuesComboBox = new JComboBox<JsonElement>();
		_innerEditor = new DefaultCellEditor(_possibleValuesComboBox);
		setConstraint(constraint);
	}
	
	public void setConstraint(JsonTreeConstraint constraint){
		_constraint = constraint;
	}
	public JsonTreeConstraint getConstraint(){
		return _constraint;
	}
	
	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row) {
		setUpComboBox(tree.getPathForRow(row));
		return _innerEditor.getTreeCellEditorComponent(tree, ((JsonTreeNode) value).getValue(), isSelected, expanded,
				leaf, row);
	}
	
	private void setUpComboBox(TreePath treePath){
		_possibleValuesComboBox.removeAllItems();
		
		JsonPath jsonPath = getJsonPathFromTreePath(treePath);
		
		for (JsonElement element : _constraint.getPossibleValues(jsonPath)) {
			_possibleValuesComboBox.addItem(element);
		}
		_possibleValuesComboBox.setEditable(_constraint.isFreelyEditable(jsonPath));
	}
	private JsonPath getJsonPathFromTreePath(TreePath path){
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
		
		JsonTreeConstraint.Node node = _constraint.getNodeAtPath(getJsonPathFromTreePath(tree.getPathForLocation(event.getX(), event.getY())));
		
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
