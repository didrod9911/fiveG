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
	private float bKcal; // 아침식단 칼로리
	private float lKcal; // 점심식단 칼로리
	private float dKcal; // 저녁식단 칼로리
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

		// 이전화면으로 놀아가기
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
					int rs = JOptionPane.showConfirmDialog(contentPane, "이전 화면으로 돌아갈 경우 정보가 사라집니다.", "이전 화면",
							JOptionPane.OK_CANCEL_OPTION);
					if (rs == JOptionPane.OK_OPTION) {
						try {
							dispose();
							setVisible(false);
							new LoginFrame().setVisible(true);
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} // 로그인페이지로 이동
					} else {

					}

				}
			}
		});
		btnPre.setBounds(53, 50, 30, 30);
		contentPane.add(btnPre);

		// 계정 확인하기 버튼
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


		// 아침 식단 출력 창
		JTextArea textB = new JTextArea();
		textB.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
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
			textB.setText("\n + 버튼을 눌러 \n 음식을 추가해주세요");
		}

		
		
		// 점심 식단 출력 창
		JTextArea textL = new JTextArea();
		textL.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
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
			textL.setText("\n + 버튼을 눌러 \n 음식을 추가해주세요");
		}

		
		
		// 저녁 식단 출력 창
		JTextArea textD = new JTextArea();
		textD.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
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
			textD.setText("\n + 버튼을 눌러 \n 음식을 추가해주세요");
		}

		
		// 아침음식 추가 버튼
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
		
		
		
		// 점심음식 추가 버튼
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

		
		
		// 저녁음식 추가 버튼
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

		
		// 아침식단에 대한 칼로리 출력 text
		kcalB = new JTextField();
		kcalB.setFont(new Font("맑은 고딕", Font.BOLD, 14));
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
	

		
		// 점심식단에 대한 칼로리 출력 text
		kcalL = new JTextField();
		kcalL.setFont(new Font("맑은 고딕", Font.BOLD, 14));
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
	

		
		// 저녁식단에 대한 칼로리 출력 text
		kcalD = new JTextField();
		kcalD.setFont(new Font("맑은 고딕", Font.BOLD, 14));
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

		
		// 전체 칼로리 출력 text
		textProBar = new JTextField();
		textProBar.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		textProBar.setHorizontalAlignment(SwingConstants.CENTER);
		textProBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(255, 255, 255), Color.white,
				new Color(255, 255, 255), new Color(255, 255, 255)));
		textProBar.setText(String.format("%.1f", bKcal + lKcal + dKcal) + "kcal" + "  /  " + login.getBmr() + "kcal");
		textProBar.setBounds(383, 90, 150, 21);
		contentPane.add(textProBar);
		textProBar.setColumns(10);
		
		
		//프로그래스 바
		
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

	// 인트배열 ","추가 스트링화
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