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
