package coffee.weneed.utils.tests;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import coffee.weneed.utils.lang.filter.ProfanityFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class FilterToolkit.
 */
public class FilterToolkit implements KeyListener {

	/** The filter. */
	static ProfanityFilter filter = null;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new FilterToolkit();
			}
		});
	}

	/** The frame. */
	JFrame frame;

	/** The panel. */
	JPanel panel;

	/** The text area. */
	JTextArea textArea;

	/** The scroll pane. */
	JScrollPane scrollPane;

	/** The panel 1. */
	JPanel panel1;

	/** The text area 1. */
	JTextArea textArea1;

	/** The scroll pane 1. */
	JScrollPane scrollPane1;

	/** The panel 2. */
	JPanel panel2;

	/** The text area 2. */
	JTextArea textArea2;

	/** The scroll pane 2. */
	JScrollPane scrollPane2;

	/** The l. */
	JLabel l;

	/** The l 1. */
	JLabel l1;

	/** The l 2. */
	JLabel l2;

	/**
	 * Instantiates a new filter toolkit.
	 */
	public FilterToolkit() {
		try {
			FilterToolkit.filter = new ProfanityFilter(true,
					/*new URL("https://raw.githubusercontent.com/WeNeedCoffee/CoffeeUtils/master/tree.json?" + System.currentTimeMillis())*/
					new File("./tree.json").toURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame = new JFrame("Filter TookKit");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout(FlowLayout.TRAILING));
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.getDocument().putProperty("filterNewlines", Boolean.TRUE);
		textArea.setName("test");
		textArea.setPreferredSize(new Dimension(400, 20));
		textArea.setMinimumSize(new Dimension(400, 20));
		textArea.setBounds(0, 0, 400, 20);
		textArea.addKeyListener(this);
		scrollPane = new JScrollPane(textArea);
		scrollPane.setViewportView(textArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		l = new JLabel();
		l.setText("Output");

		panel.add(scrollPane, BorderLayout.PAGE_START);
		panel.add(l, BorderLayout.EAST);
		textArea1 = new JTextArea();
		textArea1.setLineWrap(true);
		textArea1.setWrapStyleWord(true);
		textArea1.getDocument().putProperty("filterNewlines", Boolean.TRUE);
		textArea1.setName("test1");
		textArea1.setPreferredSize(new Dimension(400, 20));
		textArea1.setMinimumSize(new Dimension(400, 20));
		textArea1.setBounds(0, 0, 400, 20);
		textArea1.addKeyListener(this);
		scrollPane1 = new JScrollPane(textArea1);
		scrollPane1.setViewportView(textArea1);
		scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// panel.setLayout(null);
		l1 = new JLabel();
		l1.setText("Add new words");
		panel1.add(scrollPane1, BorderLayout.PAGE_START);
		panel1.add(l1, BorderLayout.EAST);

		textArea2 = new JTextArea();
		textArea2.setLineWrap(true);
		textArea2.setWrapStyleWord(true);
		textArea2.getDocument().putProperty("filterNewlines", Boolean.TRUE);
		textArea2.setName("test2");
		textArea2.setPreferredSize(new Dimension(400, 20));
		textArea2.setMinimumSize(new Dimension(400, 20));
		textArea2.setBounds(0, 0, 400, 20);
		textArea2.addKeyListener(this);
		scrollPane2 = new JScrollPane(textArea2);
		scrollPane2.setViewportView(textArea2);
		scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// panel.setLayout(null);
		l2 = new JLabel();
		l2.setText("Remove words");
		panel2.add(scrollPane2, BorderLayout.PAGE_START);
		panel2.add(l2, BorderLayout.EAST);

		frame.add(panel, BorderLayout.PAGE_START);
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.PAGE_END);
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (e.getComponent() instanceof JTextArea == false) {
				return;
			}
			JTextArea ta = (JTextArea) e.getComponent();
			String s = ta.getText();
			s = s.substring(0, s.length() - 1);// remove enter char
			if (ta.getName().equalsIgnoreCase("test")) {
				l.setText(FilterToolkit.filter.filterBadWords(s));
				ta.setText("");
				save();
			} else if (ta.getName().equalsIgnoreCase("test1")) {
				FilterToolkit.filter.addWord(s);
				ta.setText("");
				save();
			} else if (ta.getName().equalsIgnoreCase("test2")) {
				FilterToolkit.filter.removeWord(s);
				ta.setText("");
				save();
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Save.
	 */
	public void save() {
		File f = new File("tree.json");
		f.delete();
		try {
			FileOutputStream s = new FileOutputStream(f);
			s.write(FilterToolkit.filter.getRoot().toJSON().toString(1).getBytes());
			s.flush();
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
