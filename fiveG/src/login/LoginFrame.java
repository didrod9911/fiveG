package login;

 

 

 

import java.awt.Color;

import java.awt.EventQueue;

import java.awt.Font;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.sql.SQLException;

import java.util.ArrayList;

 

import javax.swing.BorderFactory;

import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JOptionPane;

import javax.swing.JPanel;

import javax.swing.JPasswordField;

import javax.swing.JTextField;

import javax.swing.border.BevelBorder;

import javax.swing.border.EmptyBorder;

 

import foodDB.FoodDBDAO;

import foodDB.FoodDBVO;

import mealSet.MealSet;

import memDBDAO.MemDBDAO;

import memDBVO.MemDBVO;

import javax.swing.ImageIcon;

 

 

 

public class LoginFrame extends JFrame {

 

 

 

	private JPanel contentPane;

 

	private JTextField idtxt;

 

	private JTextField pwtxt;


	public LoginFrame() throws ClassNotFoundException, SQLException {

 

		MemDBDAO tidao = new MemDBDAO(); //MemDBDAO conn

 

		ArrayList<MemDBVO> dao = tidao.getAllInfo(); //��� ��������� ��������(id, pw������) //�߰�

 

		

		setTitle("���� �� ����?");

		

 

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

 

		setBounds(100, 100, 747, 535);

 

		contentPane = new JPanel();

 

		contentPane.setBackground(Color.WHITE);

		

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

 

		setContentPane(contentPane);

 

		contentPane.setLayout(null);

 

		

 

		JLabel idlbl = new JLabel("");

		idlbl.setIcon(new ImageIcon(LoginFrame.class.getResource("/img/IdPng.PNG")));

 

		idlbl.setBounds(267, 179, 49, 20);

 

		contentPane.add(idlbl);

 

		

 

		idtxt = new JTextField();

		idtxt.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));

		idtxt.setBounds(319, 178, 116, 21);

 

		contentPane.add(idtxt);

 

		idtxt.setColumns(10);

 

		

 

		pwtxt = new JPasswordField();

		pwtxt.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));

		pwtxt.setBounds(319, 218, 116, 21);

 

		contentPane.add(pwtxt);

 

		pwtxt.setColumns(10);

 

		

 

		JLabel lblNewLabel_2 = new JLabel("");

		lblNewLabel_2.setIcon(new ImageIcon(LoginFrame.class.getResource("/img/Title_Jalnan.PNG")));

 

		lblNewLabel_2.setFont(new Font("���� ���", Font.BOLD, 20));

 

		lblNewLabel_2.setBounds(234, 64, 261, 63);

 

		contentPane.add(lblNewLabel_2);

 

		

 

		JButton loginbtn = new JButton(""); //�α��� ��ư

		loginbtn.setForeground(Color.WHITE);

		loginbtn.setBackground(Color.WHITE);

		loginbtn.setIcon(new ImageIcon(LoginFrame.class.getResource("/img/LoginPng.PNG")));

 

 

 

		loginbtn.addActionListener(new ActionListener() {

 

			public void actionPerformed(ActionEvent e) {

 

				boolean flag = false;

				

				String id = idtxt.getText(); //�Էµ� �� ���� �Ѱ� �ֱ�

				String pw = pwtxt.getText();

 

				for(MemDBVO m : dao) { //id, pw �����۾�

 

					//id��ġ

					if(id.equals(m.getId())) {

						//pw��ġ

						if(pw.equals(m.getPw())) {

 

							flag = true;

							
							

							MemDBVO loginMem=m;         

							FoodDBDAO fdao = null;

							ArrayList<FoodDBVO> arrSelectAll = null;

							

							try {

								fdao = new FoodDBDAO();

							} catch (ClassNotFoundException | SQLException e1) {

								// TODO Auto-generated catch block

								e1.printStackTrace();

							}

							

							try {

								arrSelectAll = fdao.foodSelect();

							} catch (SQLException e1) {

								// TODO Auto-generated catch block

								e1.printStackTrace();

							}


				if(flag) {

					JOptionPane.showMessageDialog(contentPane, "�α��ο� �����߽��ϴ�.");

				}else {

					JOptionPane.showMessageDialog(contentPane, "�α��ο� �����߽��ϴ�.");

				}
				dispose(); //����â ����//�������� �ݴ°�

				 

				setVisible(false);

						new MealSet(loginMem,arrSelectAll).setVisible(true);   //����ϸ� �ٽ� LoginFrame���� ���ư���.

 }

						} 

					}

				

			}

		});

		loginbtn.setBounds(280, 273, 95, 40);

 

		loginbtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));

		

		contentPane.add(loginbtn);

 

		

 

		JButton signupbtn = new JButton(""); //ȸ������ ��ư

		signupbtn.setIcon(new ImageIcon(LoginFrame.class.getResource("/img/SingUpPng.PNG")));

		signupbtn.setBackground(Color.WHITE);

		signupbtn.setForeground(Color.WHITE);

 

		signupbtn.addActionListener(new ActionListener() {

 

			public void actionPerformed(ActionEvent e) {

 

				dispose(); //����â ����//�������� �ݴ°�

 

				setVisible(false);

 

				new SignupFrame(dao).setVisible(true); //ȸ������â���� �̵��ϸ� ArrayList<MemDBVO>�� ������ ����.

 

			}

 

		});

 

		signupbtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));

		

		signupbtn.setBounds(372, 273, 95, 40);

 

		contentPane.add(signupbtn);

 

		JLabel pwlbl = new JLabel("");

		pwlbl.setIcon(new ImageIcon(LoginFrame.class.getResource("/img/PwPng.PNG")));

 

		pwlbl.setBounds(257, 219, 49, 20);

 

		contentPane.add(pwlbl);

 

	}

 

}