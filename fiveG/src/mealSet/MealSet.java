package mealSet;

import java.awt.EventQueue;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JOptionPane;

import javax.swing.JPanel;

import javax.swing.JProgressBar;

import javax.swing.JTextArea;

import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import foodDB.FoodDBVO;

import login.LoginFrame;

import memDBVO.MemDBVO;

import menuSelect.MenuSelect;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Scrollbar;

public class MealSet extends JFrame {

	private JPanel contentPane;
	private JTextField kcalB;
	private JTextField kcalL;
	private JTextField kcalD;
	private float bKcal; // ��ħ�Ĵ� Į�θ�
	private float lKcal; // ���ɽĴ� Į�θ�
	private float dKcal; // ����Ĵ� Į�θ�
	private JTextField textProBar;
	private JProgressBar progressBar;
	private int bmr;
	int i=0;
	int max=0;
	int pus=0;
	
	

	@SuppressWarnings("deprecation")
	public MealSet(MemDBVO login, ArrayList<FoodDBVO> fooddata) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// ����ȭ������ ��ư���
		JButton btnPre = new JButton("");
		btnPre.setBackground(Color.WHITE);
		btnPre.setForeground(new Color(102, 0, 255));
		btnPre.setToolTipText("\uC774\uC804 \uD654\uBA74");
		btnPre.setIcon(new ImageIcon(MealSet.class.getResource("/img/Arrow3030.png")));
		btnPre.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY,
				new Color(255, 255, 255), new Color(255, 255, 255)));
		btnPre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnPre) {
					int rs = JOptionPane.showConfirmDialog(contentPane, "���� ȭ������ ���ư� ��� ������ ������ϴ�.", "���� ȭ��",
							JOptionPane.OK_CANCEL_OPTION);
					if (rs == JOptionPane.OK_OPTION) {
						try {
							dispose();
							setVisible(false);
							new LoginFrame().setVisible(true);
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} // �α����������� �̵�
					} else {

					}

				}
			}
		});
		btnPre.setBounds(53, 50, 30, 30);
		contentPane.add(btnPre);

		// ���� Ȯ���ϱ� ��ư
		JButton btnAcc = new JButton("");
		btnAcc.setBackground(Color.WHITE);
		btnAcc.setIcon(new ImageIcon(MealSet.class.getResource("/img/5_myacc_img.png")));
		btnAcc.setToolTipText("\uB0B4 \uACC4\uC815");
		btnAcc.setBounds(790, 48, 45, 45);
		contentPane.add(btnAcc);
		btnAcc.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY,
				new Color(255, 255, 255), new Color(255, 255, 255)));
		btnAcc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == btnAcc) {
					setVisible(false);
					new MemDataUpdate(login, fooddata).setVisible(true);
				}
			}
		});

		
		JLabel lblB = new JLabel("");
		lblB.setIcon(new ImageIcon(MealSet.class.getResource("/img/5_mor.png")));
		lblB.setBounds(144, 178, 77, 23);
		contentPane.add(lblB);

		JLabel lalL = new JLabel("");
		lalL.setIcon(new ImageIcon(MealSet.class.getResource("/img/5_lun.png")));
		lalL.setBounds(370, 178, 77, 23);
		contentPane.add(lalL);

		JLabel lblD = new JLabel("");
		lblD.setIcon(new ImageIcon(MealSet.class.getResource("/img/5_din.png")));
		lblD.setBounds(600, 178, 77, 23);
		contentPane.add(lblD);


		// ��ħ �Ĵ� ��� â
		JTextArea textB = new JTextArea();
		textB.setFont(new Font("���� ���", Font.PLAIN, 13));
		textB.setBackground(new Color(245, 245, 245));
		textB.setBounds(144, 218, 166, 180);
		contentPane.add(textB);
		textB.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY,
				new Color(255, 255, 255), new Color(255, 255, 255)));
		if (login.getBre() != null) {
			int[] bre = stringSplit(login.getBre());
			for (FoodDBVO f : fooddata) {
				for (int i = 0; i < bre.length; i++)
					if (bre[i] == f.getNum()) {
						textB.append(f.getName() + "    " + f.getGram() + "g    " + f.getKcal() + "kcal \n");
					}
			}
		} else {
			textB.setText("\n + ��ư�� ���� \n ������ �߰����ּ���");
		}

		
		
		// ���� �Ĵ� ��� â
		JTextArea textL = new JTextArea();
		textL.setFont(new Font("���� ���", Font.PLAIN, 13));
		textL.setBackground(new Color(245, 245, 245));
		textL.setBounds(370, 218, 166, 180);
		textL.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY,
				new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(textL);
		if (login.getLun() != null) {
			int[] lau = stringSplit(login.getLun());
			for (FoodDBVO f : fooddata) {
				for (int i = 0; i < lau.length; i++)
					if (lau[i] == f.getNum()) {
						textL.append(f.getName() + "    " + f.getGram() + "g    " + f.getKcal() + "kcal \n");
					}
			}
		} else {
			textL.setText("\n + ��ư�� ���� \n ������ �߰����ּ���");
		}

		
		
		// ���� �Ĵ� ��� â
		JTextArea textD = new JTextArea();
		textD.setFont(new Font("���� ���", Font.PLAIN, 13));
		textD.setBackground(new Color(245, 245, 245));
		textD.setBounds(600, 218, 166, 180);
		textD.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY,
				new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(textD);
		if (login.getDin() != null) {
			int[] din = stringSplit(login.getDin());
			for (FoodDBVO f : fooddata) {
				for (int i = 0; i < din.length; i++)
					if (din[i] == f.getNum()) {
						textD.append(f.getName() + "    " + f.getGram() + "g    " + f.getKcal() + "kcal \n");
					}
			}
		} else {
			textD.setText("\n + ��ư�� ���� \n ������ �߰����ּ���");
		}

		
		// ��ħ���� �߰� ��ư
		JButton btnB = new JButton("");
		btnB.setIcon(new ImageIcon(MealSet.class.getResource("/img/5_plus.png")));
		btnB.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY,
				new Color(255, 255, 255), new Color(255, 255, 255)));
		btnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getSource() == btnB) {
					setVisible(false);
					try {
						new MenuSelect(login, fooddata, 1).setVisible(true);
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnB.setBounds(280, 178, 30, 30);
		contentPane.add(btnB);
		
		
		
		// �������� �߰� ��ư
		JButton btnL = new JButton("");
		btnL.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY,
				new Color(255, 255, 255), new Color(255, 255, 255)));
		btnL.setIcon(new ImageIcon(MealSet.class.getResource("/img/5_plus.png")));
		btnL.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == btnL) {
					setVisible(false);
					try {
						new MenuSelect(login, fooddata, 2).setVisible(true);
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnL.setBounds(506, 178, 30, 30);
		contentPane.add(btnL);

		
		
		// �������� �߰� ��ư
		JButton btnD = new JButton("");
		btnD.setIcon(new ImageIcon(MealSet.class.getResource("/img/5_plus.png")));
		btnD.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY,
				new Color(255, 255, 255), new Color(255, 255, 255)));
		btnD.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == btnD) {
					setVisible(false);
					try {
						new MenuSelect(login, fooddata, 3).setVisible(true);
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnD.setBounds(736, 178, 30, 30);
		contentPane.add(btnD);

		
		// ��ħ�Ĵܿ� ���� Į�θ� ��� text
		kcalB = new JTextField();
		kcalB.setFont(new Font("���� ���", Font.BOLD, 14));
		kcalB.setHorizontalAlignment(SwingConstants.RIGHT);
		kcalB.setForeground(new Color(0, 0, 0));
		kcalB.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY,
				new Color(255, 255, 255), new Color(255, 255, 255)));
		if (login.getBre() != null) {
			int[] bre = stringSplit(login.getBre());
			for (FoodDBVO f : fooddata) {
				for (int i = 0; i < bre.length; i++)
					if (bre[i] == f.getNum()) {
						bKcal = bKcal + f.getKcal();
					}
			}
			kcalB.setText(bKcal + "kcal");
		}
		kcalB.setBounds(144, 418, 166, 21);
		contentPane.add(kcalB);
		kcalB.setColumns(10);
	

		
		// ���ɽĴܿ� ���� Į�θ� ��� text
		kcalL = new JTextField();
		kcalL.setFont(new Font("���� ���", Font.BOLD, 14));
		kcalL.setHorizontalAlignment(SwingConstants.RIGHT);
		kcalL.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY,
				new Color(255, 255, 255), new Color(255, 255, 255)));
		if (login.getLun() != null) {
			int[] bru = stringSplit(login.getLun());
			for (FoodDBVO f : fooddata) {
				for (int i = 0; i < bru.length; i++)
					if (bru[i] == f.getNum()) {
						lKcal = lKcal + f.getKcal();
					}
			}
			kcalL.setText(lKcal + "kcal");
		}
		kcalL.setColumns(10);
		kcalL.setBounds(370, 418, 166, 21);
		contentPane.add(kcalL);
	

		
		// ����Ĵܿ� ���� Į�θ� ��� text
		kcalD = new JTextField();
		kcalD.setFont(new Font("���� ���", Font.BOLD, 14));
		kcalD.setHorizontalAlignment(SwingConstants.RIGHT);
		kcalD.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY,
				new Color(255, 255, 255), new Color(255, 255, 255)));
		if (login.getDin() != null) {
			int[] din = stringSplit(login.getDin());
			for (FoodDBVO f : fooddata) {
				for (int i = 0; i < din.length; i++)
					if (din[i] == f.getNum()) {
						dKcal = dKcal + f.getKcal();
					}
			}
			kcalD.setText(bKcal + "kcal");
		}
		kcalD.setColumns(10);
		kcalD.setBounds(600, 418, 166, 21);
		contentPane.add(kcalD);

		
		// ��ü Į�θ� ��� text
		textProBar = new JTextField();
		textProBar.setFont(new Font("���� ���", Font.PLAIN, 14));
		textProBar.setHorizontalAlignment(SwingConstants.CENTER);
		textProBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(255, 255, 255), Color.white,
				new Color(255, 255, 255), new Color(255, 255, 255)));
		textProBar.setText(String.format("%.1f", bKcal + lKcal + dKcal) + "kcal" + "  /  " + login.getBmr() + "kcal");
		textProBar.setBounds(383, 90, 150, 21);
		contentPane.add(textProBar);
		textProBar.setColumns(10);
		
		
		//���α׷��� ��
		
		bmr = login.getBmr();
		getContentPane().setLayout(null);
		progressBar = new JProgressBar();
		progressBar.setBounds(218, 66, 459, 14);
		progressBar.setMaximum(100000);
		contentPane.add(progressBar);
		progressBar.setVisible(true);
		progressBar.setForeground(new Color(102,0,250));
		progressBar.setStringPainted(true);
		pus=(int)((bKcal+lKcal+dKcal)/bmr*100000);
		if(pus>=100000) {
			max=100000;
		}else {
			max=pus;
		}
		Thread thread = new Thread( new Runnable() {				
			  public void run() {
				 
					while(i<max) {
						i=i+pus/50;
						progressBar.setValue(i);
						
						try {
						    java.lang.Thread.sleep(30);
						} catch (InterruptedException e) {
							   
						  e.printStackTrace();
						}
					}
			  }
			});
		thread.start();


			
		//progressBar.setValue((int) ((bKcal+lKcal+dKcal)/bmr*100000));
		//iterate();
		
	
	}

	public static int[] stringSplit(String a) {
		String[] b = a.split(",");
		int[] c = new int[b.length];
		for (int i = 0; i < b.length; i++) {
			c[i] = Integer.parseInt(b[i]);
		}
		return c;
	}

	// ��Ʈ�迭 ","�߰� ��Ʈ��ȭ
	public static String stringMake(int[] a) {
		String sm = "";
		for (int i = 0; i < a.length; i++) {
			sm = sm + a[i] + ",";
		}
		return sm;
	}

	public void run() {
		
		int i=0;
		int max=0;
		int pus=(int)((bKcal+lKcal+dKcal)/bmr*100000);
		if(pus>=100000) {
			max=100000;
		}else {
			max=pus;
		}
		while(i!=max) {
			i=i+pus/50;
			progressBar.setValue(i);
			try {
			    java.lang.Thread.sleep(30);
			} catch (InterruptedException e) {
				   
			  e.printStackTrace();
			}
		}
		
		
		
		
	}
	
}