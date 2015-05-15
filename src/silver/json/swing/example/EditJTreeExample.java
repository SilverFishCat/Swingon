package silver.json.swing.example;

import java.awt.BorderLayout;
import javax.swing.JDialog;

import com.google.gson.JsonElement;

import javax.swing.JTree;
import javax.swing.JScrollPane;

import silver.json.swing.JsonTreeCellRenderer;
import silver.json.swing.JsonTreeModel;
import silver.json.swing.TypedJsonTreeCellEditor;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EditJTreeExample extends JDialog implements ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 887025985146791924L;
	private JRadioButton rdbtnTypedJsonEditor;
	private JTree tree;

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
		getContentPane().add(panel, BorderLayout.EAST);
		
		rdbtnTypedJsonEditor = new JRadioButton("Typed JSON editor");
		rdbtnTypedJsonEditor.addChangeListener(this);
		panel.add(rdbtnTypedJsonEditor);
		
		rdbtnTypedJsonEditor.setSelected(true);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == rdbtnTypedJsonEditor){
			tree.setCellEditor(new TypedJsonTreeCellEditor());
			tree.setEditable(true);
		}
		
	}
}
