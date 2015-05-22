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


package silver.json.swing.example;

import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import silver.json.swing.TypedJsonTreeCellEditor;
import silver.json.swing.JsonTreeCellRenderer;
import silver.json.swing.JsonTreeModel;

import com.google.gson.JsonElement;

/**
 * An example of a JSON displayed in a JTree, using the libraries' components.
 * 
 * @author SilverFishCat
 *
 */
public class JTreeExample extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6914802673028446653L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {			
			JTreeExample dialog = new JTreeExample(Util.getExampleJsonObject());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public JTreeExample(JsonElement rootElement) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		{
			JScrollPane scrollPane = new JScrollPane();
			getContentPane().add(scrollPane);
			{
				JTree tree = new JTree();
				tree.setModel(new JsonTreeModel(rootElement));
				tree.setCellRenderer(new JsonTreeCellRenderer());
				tree.setCellEditor(new TypedJsonTreeCellEditor());
				tree.setEditable(true);
				scrollPane.setViewportView(tree);
			}
		}
	}

}
