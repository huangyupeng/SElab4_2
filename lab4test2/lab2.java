package lab2;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

import javax.swing.*;






public class lab2  extends JFrame implements ActionListener{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel result = new JLabel("0");
	private JButton button1 = new JButton ("1");
	private JButton button2 = new JButton("2");
	private JButton button3 = new JButton("3");
	private JButton button4 = new JButton("4");
	private JButton button5 = new JButton("5");
	private JButton button6 = new JButton("6");
	private JButton button7 = new JButton("7");
	private JButton button8 = new JButton("8");
	private JButton button9 = new JButton("9");
	private JButton button0 = new JButton("0");
	private JButton buttonadd = new JButton("+");
	private JButton buttonsub = new JButton("-");
	private JButton buttonmul = new JButton("*");
	private JButton buttondiv = new JButton("/");
	private JButton buttonres = new JButton("=");
	private JButton buttonpoint = new JButton(".");
	//float res = 0;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        lab2 frame = new lab2();
        
        
        
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public lab2(){
		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());
		jp1.add(result);
		JPanel jp2 = new JPanel();
		jp2.setLayout(new GridLayout(4,3,5,5));
		
		jp2.add(button1);
		jp2.add(button2);
		jp2.add(button3);
		jp2.add(button4);
		jp2.add(button5);
		jp2.add(button6);
		jp2.add(button7);
		jp2.add(button8);
		jp2.add(button9);
		jp2.add(button0);
		jp2.add(buttonpoint);
		jp2.add(buttonres);
		
		JPanel jp3 =new JPanel();
		jp3.setLayout(new GridLayout(4,1,5,5));
		
		jp3.add(buttonadd);
		jp3.add(buttonsub);
		jp3.add(buttonmul);
		jp3.add(buttondiv);
		
		
		
		getContentPane().setLayout(new BorderLayout(5,10));
		getContentPane().add(jp1,BorderLayout.NORTH);
		getContentPane().add(jp2,BorderLayout.CENTER);
		getContentPane().add(jp3,BorderLayout.EAST);
		
		
		button0.addActionListener(this);
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);
		button8.addActionListener(this);
		button9.addActionListener(this);
		buttonadd.addActionListener(this);
		buttonsub.addActionListener(this);
		buttonmul.addActionListener(this);
		buttondiv.addActionListener(this);
		buttonres.addActionListener(this);
		buttonpoint.addActionListener(this);
		
	}
	
	StringBuffer val =new StringBuffer();
	double data = 0;
	char op = '\0';
	boolean isComp = false;
	
	
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		char c = b.getText().charAt(0);
		if (Character.isDigit(c) || c=='.') {
			if (result.getText().equals("0")) {
				if (c != '0') {
					val.append(c);
					setResult(val.toString());
				}
			} else {
				if (isComp) {
					val.delete(0, val.length());
					val.append(c);
					setResult(val.toString());
					isComp = false;
				} else {
					if (!val.toString().equals("0")) {
						val.append(c);
						setResult(val.toString());
					}
				}
			}
		} else if (c == '+' || c == '-' || c == '*' || c == '/') {
			if (op != '\0') {
				isComp = compute();
				op = '\0';
			}
			op = c;
			if (val.length() == 0) {
				data = 0;
			} else {
				data = Double.parseDouble(val.toString());
			}
			val.delete(0, val.length());
			isComp = false;
		} else if (c == '=') {
			isComp = compute();
			op = '\0';
		}
	}
	
	
	
	private boolean compute() {
		double data1 = 0;
		if (val.length() == 0) {
			data1 = 0;
		} else {
			data1 = Double.parseDouble(val.toString());
		}
		val.delete(0, val.length());
		switch (op) {
		case '+' :
			val.append(data + data1);
			break;
		case '-' :
			val.append(data - data1);
			break;
		case '*' :
			val.append(data * data1);
			break;
		case '/' :
			if (data1 == 0) {
				val.append(0);
			} else {
				val.append(data / data1);
			}
			break;
		}
		setResult(val.toString());
		return true;
	}
	
	private void setResult(String data) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(8);
		result.setText(nf.format(Double.parseDouble(data)));
	}

}
