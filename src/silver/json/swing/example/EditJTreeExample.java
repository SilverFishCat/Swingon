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
import silver.json.swing.JsonTreeConstraint;
import silver.json.swing.JsonTreeModel;
import silver.json.swing.PlainJsonTreeCellEditor;
import silver.json.swing.TypedJsonTreeCellEditor;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.BoxLayout;

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
				JsonTreeConstraint.Node root = new JsonTreeConstraint.Node();
				JsonTreeConstraint.Node son = new JsonTreeConstraint.Node();
				son.setIsEditable(true);
				List<JsonElement> possibleValues = new ArrayList<>();
				possibleValues.add(new JsonPrimitive("option1"));
				son.setPossibleValues(possibleValues);
				son.setIsFreelyEditable(true);
				root.getChildNodes().put("text", son);
				tree.setCellEditor(new ConstrainedJsonTreeCellEditor(new JsonTreeConstraint(root)));
				tree.setEditable(true);
			}
		}
		
	}
}
