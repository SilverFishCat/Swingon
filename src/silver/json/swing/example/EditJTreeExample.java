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

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import javax.swing.ButtonGroup;
import javax.swing.JTree;
import javax.swing.JScrollPane;

import silver.json.swing.ConstrainedJsonTreeCellEditor;
import silver.json.swing.JsonTreeCellRenderer;
import silver.json.swing.JsonTreeConstraints;
import silver.json.swing.JsonTreeModel;
import silver.json.swing.PlainJsonTreeCellEditor;
import silver.json.swing.TypedJsonTreeCellEditor;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.BoxLayout;

/**
 * An example of editing json jtrees, allowing all 3 types of available editors.
 * 
 * @author SilverFishCat
 *
 */
public class EditJTreeExample extends JDialog implements ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 887025985146791924L;
	private JRadioButton rdbtnTypedJsonEditor;
	private JTree tree;
	private JRadioButton rdbtnPlainJsonEditor;
	private JRadioButton rdbtnConstrainedJsonEditor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditJTreeExample dialog = new EditJTreeExample(Util.getExampleJsonObject());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EditJTreeExample(JsonElement root) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);
		
		tree = new JTree();
		tree.setModel(new JsonTreeModel(root));
		tree.setCellRenderer(new JsonTreeCellRenderer());
		tree.setEditable(true);
		scrollPane.setViewportView(tree);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		getContentPane().add(panel, BorderLayout.EAST);
		
		rdbtnPlainJsonEditor = new JRadioButton("Plain JSON editor");
		rdbtnPlainJsonEditor.addChangeListener(this);
		panel.add(rdbtnPlainJsonEditor);
		
		rdbtnTypedJsonEditor = new JRadioButton("Typed JSON editor");
		rdbtnTypedJsonEditor.addChangeListener(this);
		panel.add(rdbtnTypedJsonEditor);
		
		rdbtnConstrainedJsonEditor = new JRadioButton("Constrained JSON editor");
		rdbtnConstrainedJsonEditor.addChangeListener(this);
		panel.add(rdbtnConstrainedJsonEditor);
		
		ButtonGroup editorRadioButtonsGroup = new ButtonGroup();
		editorRadioButtonsGroup.add(rdbtnPlainJsonEditor);
		editorRadioButtonsGroup.add(rdbtnTypedJsonEditor);
		editorRadioButtonsGroup.add(rdbtnConstrainedJsonEditor);
		
		rdbtnPlainJsonEditor.setSelected(true);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(((JRadioButton)e.getSource()).isSelected()){
			if(e.getSource() == rdbtnTypedJsonEditor){
				tree.setCellEditor(new TypedJsonTreeCellEditor());
				tree.setEditable(true);
			}
			else if(e.getSource() == rdbtnPlainJsonEditor){
				tree.setCellEditor(new PlainJsonTreeCellEditor());
				tree.setEditable(true);
			}
			else if(e.getSource() == rdbtnConstrainedJsonEditor){
				JsonTreeConstraints.Node root = new JsonTreeConstraints.Node();
				JsonTreeConstraints.Node son = new JsonTreeConstraints.Node();
				son.setIsEditable(true);
				List<JsonElement> possibleValues = new ArrayList<>();
				possibleValues.add(new JsonPrimitive("option1"));
				son.setPossibleValues(possibleValues);
				son.setIsFreelyEditable(true);
				root.getChildNodes().put("text", son);
				tree.setCellEditor(new ConstrainedJsonTreeCellEditor(new JsonTreeConstraints(root)));
				tree.setEditable(true);
			}
		}
		
	}
}
