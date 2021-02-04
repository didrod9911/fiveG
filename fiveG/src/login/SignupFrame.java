package login;

 

 

 

import java.awt.Color;

 

import java.awt.EventQueue;

 

import java.awt.Font;

 

import java.awt.event.ActionEvent;

 

import java.awt.event.ActionListener;

 

import java.sql.SQLException;

 

import java.util.ArrayList;

 

 

 

import javax.swing.BorderFactory;

 

import javax.swing.ButtonGroup;

 

import javax.swing.JButton;

 

import javax.swing.JFrame;

 

import javax.swing.JLabel;

 

import javax.swing.JOptionPane;

 

import javax.swing.JPanel;

 

import javax.swing.JRadioButton;

 

import javax.swing.JTextField;

 

import javax.swing.border.BevelBorder;

 

import javax.swing.border.EmptyBorder;

 

 

 

import memDBDAO.MemDBDAO;

 

import memDBVO.MemDBVO;

 

import javax.swing.ImageIcon;

 

 

 

public class SignupFrame extends JFrame {

 

 

 

	private JPanel contentPane;

 

 

 

	private JTextField idtxt;

 

 

 

	private JTextField pwtxt;

 

 

 

	private JTextField nametxt;

 

 

 

	private JTextField agetxt;

 

 

 

	private JTextField heigtxt;

 

 

 

	private JTextField weigtxt;

 

 

 

	private int gender ;       // ridobtn의 값을 받기 위한 변수 설정

 

 

 

	/**

 

	 * 

 

	 * Launch the application.

 

	 * 

 

	 */

 

 

 

/*	public static void main(String[] args) {

 

 

 

		EventQueue.invokeLater(new Runnable() {

 

 

 

			public void run() {

 

 

 

				try {

 

 

 

					SignupFrame frame = new SignupFrame();

 

 

 

					frame.setVisible(true);

 

 

 

				} catch (Exception e) {

 

 

 

					e.printStackTrace();

 

 

 

				}

 

 

 

			}

 

 

 

		});

 

 

 

	}

 

 

 

	*//**

 

	 * 

 

	 * Create the frame.

 

	 * 

 

	 *//*

 

	public SignupFrame() {}

*/

 

 

	public SignupFrame(ArrayList<MemDBVO> memarr) {

 

 

 

		

 

		setTitle("오늘 뭐 먹지?");

 

		

 

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

 

 

 

		setBounds(100, 100, 900, 600);

 

 

 

		contentPane = new JPanel();

 

 

 

		contentPane.setBackground(Color.WHITE);

 

		

 

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

 

 

 

		setContentPane(contentPane);

 

 

 

		contentPane.setLayout(null);

 

 

 

		//중앙 상단 회원가입 lbl

 

		JLabel label = new JLabel("");

 

		label.setIcon(new ImageIcon(SignupFrame.class.getResource("/img/2_TitlePng.png")));

 

 

 

		label.setFont(new Font("한컴 고딕", Font.BOLD, 20));

 

 

 

		label.setBounds(335, 47, 181, 63);

 

 

 

		contentPane.add(label);

 

 

 

		

 

		

 

		JLabel idlbl = new JLabel("");

 

		idlbl.setIcon(new ImageIcon(SignupFrame.class.getResource("/img/2_id.png")));

 

		idlbl.setForeground(Color.WHITE);

 

 

 

		idlbl.setBounds(299, 158, 57, 15);

 

 

 

		contentPane.add(idlbl);

 

 

 

		idtxt = new JTextField();

 

 

 

		idtxt.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));

 

		

 

		idtxt.setBounds(412, 158, 116, 21);

 

 

 

		contentPane.add(idtxt);

 

 

 

		idtxt.setColumns(10);

 

 

 

		

 

		JLabel pwlbl = new JLabel("PW");

 

		pwlbl.setIcon(new ImageIcon(SignupFrame.class.getResource("/img/2_pw.png")));

 

		pwlbl.setForeground(Color.WHITE);

 

 

 

		pwlbl.setBounds(300, 188, 57, 15);

 

 

 

		contentPane.add(pwlbl);

 

 

 

		pwtxt = new JTextField();

 

 

 

		pwtxt.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));

 

		

 

		pwtxt.setColumns(10);

 

 

 

		pwtxt.setBounds(413, 191, 116, 21);

 

 

 

		contentPane.add(pwtxt);

 

 

 

		

 

		JLabel namelbl = new JLabel("");

 

		namelbl.setIcon(new ImageIcon(SignupFrame.class.getResource("/img/2_name.png")));

 

 

 

		namelbl.setBounds(300, 224, 57, 18);

 

 

 

		contentPane.add(namelbl);

 

 

 

		nametxt = new JTextField();

 

 

 

		nametxt.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));

 

		

 

		nametxt.setColumns(10);

 

 

 

		nametxt.setBounds(413, 227, 116, 21);

 

 

 

		contentPane.add(nametxt);

 

 

 

		

 

		JLabel agelbl = new JLabel("");

 

		agelbl.setForeground(Color.WHITE);

 

		agelbl.setIcon(new ImageIcon(SignupFrame.class.getResource("/img/2_age.png")));

 

 

 

		agelbl.setBounds(300, 253, 57, 15);

 

 

 

		contentPane.add(agelbl);

 

 

 

		agetxt = new JTextField();

 

 

 

		agetxt.setColumns(10);

 

 

 

		agetxt.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));

 

		

 

		agetxt.setBounds(413, 253, 116, 21);

 

 

 

		contentPane.add(agetxt);

 

 

 

		

 

		JLabel heiglbl = new JLabel("\uD0A4");

 

		heiglbl.setIcon(new ImageIcon(SignupFrame.class.getResource("/img/2_height.png")));

 

		heiglbl.setForeground(Color.WHITE);

 

 

 

		heiglbl.setBounds(287, 283, 70, 16);

 

 

 

		contentPane.add(heiglbl);

 

 

 

		heigtxt = new JTextField();

 

 

 

		heigtxt.setColumns(10);

 

 

 

		heigtxt.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));

 

		

 

		heigtxt.setBounds(413, 287, 116, 21);

 

 

 

		contentPane.add(heigtxt);

 

 

 

		

 

		JLabel weiglbl = new JLabel("");

 

		weiglbl.setIcon(new ImageIcon(SignupFrame.class.getResource("/img/2_weight.png")));

 

		weiglbl.setForeground(Color.WHITE);

 

 

 

		weiglbl.setBounds(257, 312, 100, 18);

 

 

 

		contentPane.add(weiglbl);

 

 

 

		weigtxt = new JTextField();

 

 

 

		weigtxt.setColumns(10);

 

 

 

		weigtxt.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));

 

		

 

		weigtxt.setBounds(413, 320, 116, 21);

 

 

 

		contentPane.add(weigtxt);

 

 

 

		

 

		JLabel genlbl = new JLabel("");

 

		genlbl.setIcon(new ImageIcon(SignupFrame.class.getResource("/img/2_gender.png")));

 

		genlbl.setForeground(Color.WHITE);

 

 

 

		genlbl.setBounds(300, 345, 57, 15);

 

 

 

		contentPane.add(genlbl);

 

 

 

		

 

		//RadionButton (Male, Female)

 

		JRadioButton rdbtnMale = new JRadioButton("\uB0A8\uC790");

 

		rdbtnMale.setFont(new Font("맑은 고딕", Font.BOLD, 20));

 

		rdbtnMale.setBackground(Color.WHITE);

 

		rdbtnMale.addActionListener(new ActionListener() {

 

			public void actionPerformed(ActionEvent arg0) {

 

 

 

				if (arg0.getSource() == rdbtnMale) {

 

					gender = 1;

 

				}

 

			}

 

		});

 

		rdbtnMale.setBounds(412, 345, 82, 29);

 

		contentPane.add(rdbtnMale);

 

 

 

		JRadioButton rdbtnFemale = new JRadioButton("\uC5EC\uC790");

 

		rdbtnFemale.setFont(new Font("맑은 고딕", Font.BOLD, 20));

 

		rdbtnFemale.setBackground(Color.WHITE);

 

		rdbtnFemale.addActionListener(new ActionListener() {

 

			public void actionPerformed(ActionEvent e) {

 

 

 

				if (e.getSource() == rdbtnFemale) {

 

					gender = 2;

 

				}

 

			}

 

		});

 

		rdbtnFemale.setBounds(491, 345, 77, 29);

 

		contentPane.add(rdbtnFemale);

 

 

 

		ButtonGroup group = new ButtonGroup(); // Radiobtn을 묶어 한개만 선택할수 있도록 그룹 설정

 

		group.add(rdbtnMale);

 

		group.add(rdbtnFemale);

 

		// 수정 //수정 //수정 //수정 //수정 //수정 //수정//수정 //수정 //수정 //수정 //수정 //수정 //수정 //수정

 

		// //수정//수정 //수정

 

 

 

		JButton canbtn = new JButton(""); // 취소 버튼

 

		canbtn.setIcon(new ImageIcon(SignupFrame.class.getResource("/img/Arrow3030.png")));

 

		canbtn.setForeground(Color.WHITE);

 

		canbtn.setBackground(Color.WHITE);

 

 

 

		canbtn.addActionListener(new ActionListener() {

 

 

 

			public void actionPerformed(ActionEvent e) {

 

 

 

				JOptionPane.showMessageDialog(contentPane, "회원가입을 취소합니다.");

 

				dispose(); // 지금창 해제//프레임을 닫는것

 

 

 

				setVisible(false);

 

 

 

				try {

 

					new LoginFrame().setVisible(true);

 

				} catch (ClassNotFoundException | SQLException e1) {

 

					// TODO Auto-generated catch block

 

					e1.printStackTrace();

 

				} // 취소하면 다시 LoginFrame으로 돌아간다.

 

 

 

			}

 

 

 

		});

 

 

 

		canbtn.setBounds(36, 34, 30, 30);

 

 

 

		canbtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));

 

		

 

		contentPane.add(canbtn);

 

 

 

		JButton sucbtn = new JButton(""); // 완료 버튼

 

		sucbtn.setIcon(new ImageIcon(SignupFrame.class.getResource("/img/ok.png")));

 

		sucbtn.setForeground(Color.WHITE);

 

 

 

		sucbtn.addActionListener(new ActionListener() {

 

 

 

			public void actionPerformed(ActionEvent e) {

 

 

 

				MemDBDAO dao = null;

 

				try {

 

					dao = new MemDBDAO();

 

				} catch (ClassNotFoundException | SQLException e2) {

 

					e2.printStackTrace();

 

				} // MemDBDAO와 Conn

 

				

 

				

 

		

 

				

 

				

 

				

 

				if (idtxt.getText().isEmpty() || pwtxt.getText().isEmpty() || nametxt.getText().isEmpty()    // 빈칸이 존재하는지 검사

 

						|| agetxt.getText().isEmpty() || weigtxt.getText().isEmpty() || heigtxt.getText().isEmpty()) {

 

					JOptionPane.showMessageDialog(contentPane, "정확한 정보를 입력해주세요.");                        // 빈칸이 존재한다면 실행

 

 

 

				} else {                                           //빈칸이 존재하지 않을떄 실행

 

					int sw =0;

 

					String id = idtxt.getText();

 

						

 

					String pw = pwtxt.getText();

 

 

 

					String name = nametxt.getText();

 

 

 

					int age = Integer.parseInt(agetxt.getText());

 

 

 

					double weig = Double.parseDouble(weigtxt.getText());

 

 

 

					double heig = Double.parseDouble(heigtxt.getText());

 

 

 

					String gen;

 

 

 

					for (MemDBVO mem : memarr) {

 

						if(mem.getId().equals(id)) {

 

							sw=1;

 

						}

 

					}

 

					

 

					if(sw==0) {

 

						if (gender == 1) {         //radiobtn을 통해 성별 구분

 

							gen = "Male";

 

						} else { 

 

							gen = "Female";

 

						}

 

 

 

						boolean b1 = dao.signUp(id, pw, name, age, weig, heig, gen);  //Text입력값들을 객체에 담아 dao로 전송

 

 

 

						JOptionPane.showMessageDialog(contentPane, "회원가입에 성공했습니다.");

 

 

 

						dispose(); // 지금창 해제//프레임을 닫는것

 

 

 

						setVisible(false);

 

 

 

						try {

 

							new LoginFrame().setVisible(true);

 

						} catch (ClassNotFoundException | SQLException e1) {

 

							// TODO Auto-generated catch block

 

							e1.printStackTrace();

 

						}

 

					}else {

 

						JOptionPane.showMessageDialog(contentPane, "이미 등록된 ID입니다.");

 

					}

 

 

 

 

 

				}

 

 

 

			}

 

 

 

		});

 

 

 

		sucbtn.setBounds(363, 402, 95, 40);

 

 

 

		sucbtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));

 

		

 

		contentPane.add(sucbtn);

 

		

 

		JLabel lblNewLabel = new JLabel("");

 

		lblNewLabel.setIcon(new ImageIcon(SignupFrame.class.getResource("/img/2_Info.png")));

 

		lblNewLabel.setBounds(314, 108, 243, 21);

 

		contentPane.add(lblNewLabel);

 

 

 

	}

 

}