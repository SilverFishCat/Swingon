package silver.json.swing;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.JTree;

import com.google.gson.JsonParser;

public class PlainJsonTreeCellEditor extends DefaultCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4452730053392179122L;

	public PlainJsonTreeCellEditor(){
		super(new JTextField());
	}
	
	@Override
	public Object getCellEditorValue() {
		return new JsonParser().parse((String) super.getCellEditorValue());
	}
	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row) {
		return super.getTreeCellEditorComponent(tree, ((JsonTreeNode) value).getValue(), isSelected, expanded,
				leaf, row);
	}
}
