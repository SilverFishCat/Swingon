package silver.json.swing.example;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import silver.json.swing.JsonTreeCellRenderer;
import silver.json.swing.JsonTreeModel;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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
			JsonElement rootElement = new JsonParser().parse("{"
					+ "\"text\" : \"lorem ipsum etc etc etc\", "
					+ "\"number\" : 123, "
					+ "\"array\" : ["
					+ "1, 2, 3, "
					+ "\"a\", \"b\", \"c\","
					+ "true, null, false"
					+ "],"
					+ "\"object\" : {"
					+ "\"a\" : \"x\","
					+ "\"b\" : \"y\","
					+ "\"c\" : \"z\""
					+ "}"
					+ "}");
			
			JTreeExample dialog = new JTreeExample(rootElement);
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
				tree.setCellEditor(new DefaultCellEditor(new JComboBox<String>(new String[]{"adasd", "sdfdsf"})));
				tree.setEditable(true);
				scrollPane.setViewportView(tree);
			}
		}
	}

}
