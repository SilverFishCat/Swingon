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
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.TreeCellEditor;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonToken;

/**
 * <p>A tree cell editor, using a different component kind for every JSON type.</p>
 * 
 * <p>Boolean values are set using a combo box with true and false values, 
 * Number values are editted using a formatted text field and String
 * values are editted using an oridinary text field.</p>
 * 
 * @author SilverFishCat
 *
 */
public class TypedJsonTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3227038845561183100L;

	private TreeCellEditor _stringEditor;
	private TreeCellEditor _numberEditor ;
	private TreeCellEditor _booleanEditor;
	private JsonToken _lastObjectType;
	private BridgingCellEditorListener _bridgingCellEditorListener;

	/**
	 * Create a typed json tree cell editor.
	 */
	public TypedJsonTreeCellEditor() {
		_stringEditor = new DefaultCellEditor(new JTextField());
		
		JFormattedTextField numberField = new JFormattedTextField(NumberFormat.getInstance());
		_numberEditor = new DefaultCellEditor(numberField);
		
		_booleanEditor = new DefaultCellEditor(new JComboBox<Boolean>(new Boolean[]{true, false}));
		
		_bridgingCellEditorListener = new BridgingCellEditorListener();
		_stringEditor.addCellEditorListener(_bridgingCellEditorListener);
		_numberEditor.addCellEditorListener(_bridgingCellEditorListener);
		_booleanEditor.addCellEditorListener(_bridgingCellEditorListener);
		
		_lastObjectType = JsonToken.NULL;
	}
	
	@Override
	public boolean isCellEditable(EventObject e) {
		MouseEvent mouseEvent = (MouseEvent) e;
		JTree tree = (JTree) e.getSource();
		Object nodeObject = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY()).getLastPathComponent();
		if(nodeObject instanceof JsonTreeNode){
			JsonTreeNode node = (JsonTreeNode) nodeObject;
			JsonElement jsonValue = node.getValue();
			
			return jsonValue.isJsonPrimitive();
		}
		
		return false;
	}
	@Override
	public Object getCellEditorValue() {
		switch (_lastObjectType) {
		case STRING:
			return _stringEditor.getCellEditorValue();
			
		case NUMBER:
			try {
				return new  JsonPrimitive(NumberFormat.getInstance().parse((String) _numberEditor.getCellEditorValue()));
			} catch (ParseException e) {
				return null;
			}
		case BOOLEAN:
			return _booleanEditor.getCellEditorValue();
		case NULL:
			return JsonNull.INSTANCE;

		default:
			return null;
		}
	}

	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row) {
		JsonElement element = ((JsonTreeNode) value).getValue();
		if(element.isJsonPrimitive()){
			JsonPrimitive primitive = element.getAsJsonPrimitive();
			if(primitive.isString()){
				_lastObjectType = JsonToken.STRING;
				return _stringEditor.getTreeCellEditorComponent(tree, primitive.getAsString(), isSelected, expanded, leaf, row);
			}
			else if(primitive.isNumber()){
				_lastObjectType = JsonToken.NUMBER;
				return _numberEditor.getTreeCellEditorComponent(tree, primitive.getAsNumber(), isSelected, expanded, leaf, row);
			}
			else if(primitive.isBoolean()){
				_lastObjectType = JsonToken.BOOLEAN;
				return _booleanEditor.getTreeCellEditorComponent(tree, primitive.getAsBoolean(), isSelected, expanded, leaf, row);
			}
			else if(primitive.isJsonNull())
				_lastObjectType = JsonToken.NULL;
				return null;
		}
		return null;
	}

	/**
	 * A cell editor listener to bridge the inner cell editors and this cell editor.
	 * 
	 * @author SilverFishCat
	 *
	 */
	private class BridgingCellEditorListener implements CellEditorListener{

		@Override
		public void editingCanceled(ChangeEvent e) {
			TypedJsonTreeCellEditor.this.cancelCellEditing();
		}

		@Override
		public void editingStopped(ChangeEvent e) {
			TypedJsonTreeCellEditor.this.stopCellEditing();
		}
		
	}
}
