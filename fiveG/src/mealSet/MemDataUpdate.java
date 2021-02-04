package mealSet;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import foodDB.FoodDBVO;
import memDBDAO.MemDBDAO;
import memDBVO.MemDBVO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;

public class MemDataUpdate extends JFrame {
	
	private JPanel contentPane;
	private JTextField nowPWtext;
	private JTextField upPWtext;
	private JTextField conPWtext;
	private JTextField heigText;
	private JTextField weigText;
	private JLabel id2Label;
	private JLabel nameLabel2;
	private JLabel genLabel2;
	private JLabel ageLabel2;
	
	public MemDataUpdate(MemDBVO login,ArrayList<FoodDBVO> fooddata) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setTitle("���� �� ����?");
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//Ÿ��Ʋ����
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/3_title.png")));
		label.setBounds(324, 26, 183, 57);
		label.setFont(new Font("����", Font.BOLD, 20));
		contentPane.add(label);//Ÿ��Ʋ �� 
		
		//����ȭ�� ��ư ����
		JButton button = new JButton("");
		button.setBackground(Color.WHITE);
		button.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/Arrow3030.png")));
		button.setForeground(Color.WHITE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==button) {
					setVisible(false);
					new MealSet(login,fooddata).setVisible(true);
				}
			}
		});
		button.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		button.setBounds(37, 32, 30, 30);
		contentPane.add(button);//����ȭ�� ��ư ��
		
		//id���� ����=======================================================================================
		JLabel id1Label = new JLabel("");
		id1Label.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/2_id.png")));
		id1Label.setBounds(12, 111, 80, 40);
		id1Label.setToolTipText("\uC544\uC774\uB514");
		contentPane.add(id1Label);
		id2Label = new JLabel(login.getId());
		id2Label.setFont(new Font("����", Font.BOLD, 18));
		id2Label.setBounds(141, 111, 189, 40);
		contentPane.add(id2Label);//id���� ��-------------------------------------------------------------
		
		//pw����1  ����=====================================================================================
		JLabel pwLabel = new JLabel("");
		pwLabel.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/3_PW.png")));
		pwLabel.setBounds(22, 195, 80, 40);
		contentPane.add(pwLabel);
		nowPWtext = new JPasswordField();
		nowPWtext.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		nowPWtext.setBounds(141, 195, 240, 40);
		contentPane.add(nowPWtext);
		nowPWtext.setColumns(10);
				
		//pw����2  ����=====================================================================================
		JLabel pwUpLabel = new JLabel("");
		pwUpLabel.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/3_change_PW.png")));
		pwUpLabel.setBounds(22, 283, 80, 40);
		contentPane.add(pwUpLabel);
		upPWtext = new JPasswordField();
		upPWtext.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		upPWtext.setBounds(141, 283, 240, 40);
		contentPane.add(upPWtext);
		upPWtext.setColumns(10);
				
		//pw����3  ����=====================================================================================
		JLabel conPwLabel = new JLabel("");
		conPwLabel.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/3_again.png")));
		conPwLabel.setBounds(12, 378, 80, 40);
		contentPane.add(conPwLabel);
		conPWtext = new JPasswordField();
		conPWtext.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		conPWtext.setBounds(141, 378, 240, 40);
		contentPane.add(conPWtext);
		conPWtext.setColumns(10);
				
		//pw��ư1  ����=====================================================================================
		JButton pwComButton = new JButton("");
		pwComButton.setForeground(Color.WHITE);
		pwComButton.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/3_edit.png")));
		pwComButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==pwComButton) {//if-1) ��ư===== ����
					if(nowPWtext.getText().isEmpty() || upPWtext.getText().isEmpty() || conPWtext.getText().isEmpty()){//if-2 ����üũ =====����
						JOptionPane.showMessageDialog(contentPane, "��ĭ�� �����մϴ�.");
					}else {//if-2 else
						if(login.getPw().equals(nowPWtext.getText())) {//if-3 �����й�ȣĭ�� ������ ��ġȮ��===== ����
							if(upPWtext.getText().equals(conPWtext.getText())){//if-4 �ٲ�pw�� ���Է°� ��ġȮ�� =====����
								if(!login.getPw().equals(upPWtext)) {//if-5 �����ȣ�� �ٲܾ�ȣ�������� false 
									login.setPw(upPWtext.getText());
									try {
										new MemDBDAO().update_pw (login.getId(),upPWtext.getText());
										JOptionPane.showMessageDialog(contentPane,"��й�ȣ�� �����Ǿ����ϴ�.");
									} catch (ClassNotFoundException | SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}else {//if-5 else
									JOptionPane.showMessageDialog(contentPane,"����pw�� ������pw�� �����մϴ�. ");
								}//if-5 -----��
							}else {//if-4 else
								JOptionPane.showMessageDialog(contentPane,"������pw�� ���Է�pw�� ��ġ���� �ʽ��ϴ�.");
							}//if-4 -----��
						}else {//if-3 else
							JOptionPane.showMessageDialog(contentPane,"��й�ȣ�� Ʋ�Ƚ��ϴ�.");
						}//if-3 -----��
					}//if-2 else -----��
				}//if-1  -----��
			}
		});
		pwComButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		pwComButton.setBounds(141, 471, 95, 40);
		contentPane.add(pwComButton);//pw��ư1 ��-----------------------------------------------------------------------------------
		
		//pw��ư2 ����=====================================================================================
		JButton pwNobutton = new JButton("");
		pwNobutton.setForeground(Color.WHITE);
		pwNobutton.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/3_reset.png")));
		pwNobutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==pwNobutton) {
					nowPWtext.setText("");
					upPWtext.setText("");
					conPWtext.setText("");
				}
			}
		});
		pwNobutton.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		pwNobutton.setBounds(286, 471, 95, 40);
		contentPane.add(pwNobutton);//pw��ư2 ��-----------------------------------------------------------
		
		//�̸����� ����====================================================================================
		JLabel nameLabel1 = new JLabel("");
		nameLabel1.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/2_name_rvsd.png")));
		nameLabel1.setBounds(427, 111, 80, 40);
		contentPane.add(nameLabel1);
		nameLabel2 = new JLabel(login.getName());
		nameLabel2.setFont(new Font("����", Font.BOLD, 18));
		nameLabel2.setBounds(533, 111, 194, 40);
		contentPane.add(nameLabel2);
		JButton nameUpButton = new JButton("");
		nameUpButton.setForeground(Color.WHITE);
		nameUpButton.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/3_edit.png")));
		nameUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==nameUpButton) {
					while(true) {
						String name = JOptionPane.showInputDialog(contentPane,"������ �̸��� �Է��ϼ���");
						if(name ==null) {
							JOptionPane.showMessageDialog(contentPane, "������ ��ҵ˴ϴ�.");
							break;
						}else if(name.equals("")){
							JOptionPane.showMessageDialog(contentPane, "�������̸��� �Է����ּ���");
						}else {
							login.setName(name);
							try {
								new MemDBDAO().update_name(login.getId(), name);
							} catch (ClassNotFoundException | SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							infoReset(login);
							JOptionPane.showMessageDialog(contentPane, "�̸��� ����Ǿ����ϴ�.");
							break;
						}
					}
					
				}

			}
		});
		nameUpButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		nameUpButton.setBounds(731, 111, 95, 40);
		contentPane.add(nameUpButton);//�̸����� ��----------------------------------------------------------------------------
		
		//�������� ����=============================================���� �޼ҵ����====================================
		JLabel genLabel1 = new JLabel("");
		genLabel1.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/2_gender.png")));
		genLabel1.setBounds(427, 195, 80, 40);
		contentPane.add(genLabel1);
		genLabel2 = new JLabel(login.getGen());
		genLabel2.setFont(new Font("���� ���", Font.BOLD, 20));
		genLabel2.setBounds(531, 195, 173, 40);
		contentPane.add(genLabel2);
		JButton genButton = new JButton("");
		genButton.setForeground(Color.WHITE);
		genButton.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/3_edit.png")));
		genButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==genButton) {
					String [] genArr= {"MALE","FEMALE"};
					while(true) {
						String gen = (String)JOptionPane.showInputDialog(contentPane,"������ ������ �����ϼ���","��������",JOptionPane.INFORMATION_MESSAGE,
								null,genArr,"MALE");
						if(gen ==null) {
							JOptionPane.showMessageDialog(contentPane, "������ ��ҵ˴ϴ�.");
							break;
						}else {
							if(login.getGen().equals(gen)) {
								JOptionPane.showMessageDialog(contentPane, "�̹� �ش� ���� �Դϴ�.");
								break;
							}else {
								login.setGen(gen);
								try {
									new MemDBDAO().update_gen(login.getGen(), gen);
								} catch (ClassNotFoundException | SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								infoReset(login);
								JOptionPane.showMessageDialog(contentPane, "������ ����Ǿ����ϴ�.");
								break;
							}
							
							
						}
					}
				}

			}
		});
		genButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		genButton.setBounds(731, 195, 95, 40);
		contentPane.add(genButton);//�������� ��------------------------------------------------------------
		
		//���̶��� ����=======================================================================================
		JLabel ageLabel1 = new JLabel("");
		ageLabel1.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/2_age.png")));
		ageLabel1.setBounds(427, 283, 80, 40);
		contentPane.add(ageLabel1);
		ageLabel2 = new JLabel(login.getAge()+"");
		ageLabel2.setFont(new Font("���� ���", Font.BOLD, 20));
		ageLabel2.setBounds(533, 283, 173, 40);
		contentPane.add(ageLabel2);
		JButton ageButton = new JButton("");
		ageButton.setForeground(Color.WHITE);
		ageButton.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/3_edit.png")));
		ageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==ageButton) {
					while(true) {
						String ageS = JOptionPane.showInputDialog(contentPane,"������ ������ �Է��ϼ���");
						if(ageS ==null) {
							JOptionPane.showMessageDialog(contentPane, "������ ��ҵ˴ϴ�.");
							break;
						}else if(ageS.equals("")){
							JOptionPane.showMessageDialog(contentPane, "����� ������ �Է����ּ���");
						}else {
							try {
								int age = Integer.parseInt(ageS);
								login.setAge(age);
								try {
									new MemDBDAO().update_age(login.getId(), age);
								} catch (ClassNotFoundException | SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								infoReset(login);
								JOptionPane.showMessageDialog(contentPane, "���̰� ����Ǿ����ϴ�.");
								break;
							}catch (NumberFormatException e2) {
								JOptionPane.showMessageDialog(contentPane,"���ڸ� �Է��ϼ���!!","�Է¿���",JOptionPane.ERROR_MESSAGE);
							}
							
						}
					}
					
				}

			}
		});
		ageButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		ageButton.setBounds(731, 283, 95, 40);
		contentPane.add(ageButton);//���� ���� ��--------------------------------------------------------------
		
		//Ű ���� ����===========================================================================================
		JLabel hlabel1 = new JLabel("");
		hlabel1.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/2_height.png")));
		hlabel1.setBounds(427, 378, 80, 40);
		contentPane.add(hlabel1);
		heigText = new JTextField();
		heigText.setFont(new Font("���� ���", Font.PLAIN, 20));
		heigText.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		heigText.setBounds(533, 378, 171, 40);
		contentPane.add(heigText);
		heigText.setColumns(10);
		heigText.setText(login.getHeig()+"");
		JButton heigButton = new JButton("");
		heigButton.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/3_edit.png")));
		heigButton.setForeground(Color.WHITE);
		heigButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==heigButton) {
					String heigS = heigText.getText();
					if(heigS.equals("")) {
						JOptionPane.showMessageDialog(contentPane, "��ĭ�� ������ �� �����ϴ�");
					}else {
						try {
							int heig = Integer.parseInt(heigS);
							if(login.getHeig()!=heig) {
								login.setHeig(heig);
								try {
									new MemDBDAO().update_heig(login.getId(), heig);
									login.setBmr(new MemDBDAO().calcBmr(login.getGen(), heig, login.getWeig(), login.getAge(), login.getId()));
								} catch (ClassNotFoundException | SQLException e1) {
										// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								infoReset(login);
								JOptionPane.showMessageDialog(contentPane, "Ű�� ����Ǿ����ϴ�.");
							}else {
								JOptionPane.showMessageDialog(contentPane, "���� Ű�� �����մϴ�.");
							}
						}catch (NumberFormatException e2) {
							JOptionPane.showMessageDialog(contentPane,"���ڸ� �Է��ϼ���!!","�Է¿���",JOptionPane.ERROR_MESSAGE);
						}	
					}
				}		
			}
		});
		heigButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		heigButton.setBounds(731, 378, 95, 40);
		contentPane.add(heigButton);//Ű ���� ��-----------------------------------------------------------------------
		
		//������ ���� ����=============================================================================================
		weigText = new JTextField();
		weigText.setFont(new Font("���� ���", Font.PLAIN, 20));
		weigText.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		weigText.setBounds(533, 471, 171, 40);
		contentPane.add(weigText);
		weigText.setColumns(10);
		JLabel wlabel1 = new JLabel("");
		wlabel1.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/2_weight.png")));
		wlabel1.setBounds(428, 480, 104, 28);
		contentPane.add(wlabel1);
		weigText.setText(login.getWeig()+"");
		JButton weigButton = new JButton("");
		weigButton.setForeground(Color.WHITE);
		weigButton.setIcon(new ImageIcon(MemDataUpdate.class.getResource("/img/3_edit.png")));
		weigButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==weigButton) {
					String weigS = weigText.getText();
					if(weigS.equals("")) {
						JOptionPane.showMessageDialog(contentPane, "��ĭ�� ������ �� �����ϴ�");
					}else {
						try {
							int weig = Integer.parseInt(weigS);
							if(login.getWeig()!=weig) {
								
								login.setWeig(weig);
								
								try {
									new MemDBDAO().update_weig(login.getId(), weig);
									login.setBmr(new MemDBDAO().calcBmr(login.getGen(), login.getHeig(), weig, login.getAge(), login.getId()));
								} catch (ClassNotFoundException | SQLException e1) {
										// TODO Auto-generated catch block
									e1.printStackTrace();
								}	
								infoReset(login);
								JOptionPane.showMessageDialog(contentPane, "�����԰� ����Ǿ����ϴ�.");
							}else {
								JOptionPane.showMessageDialog(contentPane, "���� �����Կ� �����մϴ�.");
							}
						}catch (NumberFormatException e2) {
							JOptionPane.showMessageDialog(contentPane,"���ڸ� �Է��ϼ���!!","�Է¿���",JOptionPane.ERROR_MESSAGE);
						}	
					}
				}		
			}
		});
		weigButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		weigButton.setBounds(731, 471, 95, 40);
		contentPane.add(weigButton);
		
	}
	public void infoReset(MemDBVO login) {
		heigText.setText(login.getHeig()+"");
		weigText.setText(login.getWeig()+"");
		nameLabel2.setText(login.getName());
		genLabel2.setText(login.getGen());
		ageLabel2.setText(login.getAge()+"");
	}
}
