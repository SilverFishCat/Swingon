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

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.JTree;

import com.google.gson.JsonParser;

/**
 * A plain text editor for a json tree.
 * 
 * @author SilverFishCat
 *
 */
public class PlainJsonTreeCellEditor extends DefaultCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4452730053392179122L;

	/**
	 * Create a new plain json tree cell editor.
	 */
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
