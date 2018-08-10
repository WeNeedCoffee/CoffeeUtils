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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import coffee.weneed.utils.lang.filter.ProfanityFilter;
public class FilterToolkit implements KeyListener {
	JFrame frame;
	JPanel panel;
	JTextArea textArea;
	JScrollPane scrollPane;
	JPanel panel1;
	JTextArea textArea1;
	JScrollPane scrollPane1;
	JPanel panel2;
	JTextArea textArea2;
	JScrollPane scrollPane2;
	static ProfanityFilter filter = new ProfanityFilter(true);
	JLabel l;
	JLabel l1;
	JLabel l2;
	public FilterToolkit() {
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
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 

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
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        //  panel.setLayout(null);
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
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        //  panel.setLayout(null);
        l2 = new JLabel();
        l2.setText("Remove words");
        panel2.add(scrollPane2, BorderLayout.PAGE_START);
        panel2.add(l2, BorderLayout.EAST);
        
        frame.add(panel, BorderLayout.PAGE_START);
        frame.add(panel1, BorderLayout.CENTER);
        frame.add(panel2, BorderLayout.PAGE_END);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FilterToolkit();
            }
        });
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (e.getComponent() instanceof JTextArea == false) return;
			JTextArea ta = (JTextArea) e.getComponent();
			String s = ta.getText();
			s = s.substring(0, s.length() - 1);//remove enter char
			if (ta.getName().equalsIgnoreCase("test")) {
				l.setText(filter.filterBadWords(s));
				ta.setText("");
				save();
			} else if (ta.getName().equalsIgnoreCase("test1")) {
				filter.addWord(s);
				ta.setText("");
				save();
			} else if (ta.getName().equalsIgnoreCase("test2")) {
				filter.removeWord(s);
				ta.setText("");
				save();
			}
		}
	}
	public void save() {
		File f = new File("tree.json");
		f.delete();
		try {
			FileOutputStream s = new FileOutputStream(f);
			s.write(filter.getRoot().toJSON().toString().getBytes());
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
