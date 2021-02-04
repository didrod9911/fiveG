package menuSelect;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import foodDB.FoodDBDAO;
import foodDB.FoodDBVO;
import mealSet.MealSet;
import memDBDAO.MemDBDAO;
import memDBVO.MemDBVO;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class MenuSelect extends JFrame {

	private JPanel contentPane;

	//������������
	private DefaultListModel dModel;		// �߾�(�޴����) JList���� ���� DefaultListModel
	private DefaultListModel fModel;		// ����(���ø��) JList���� ���� DefaultListModel
	private JList listMenulist;				// �߾� �޴���� ��¿� JList
	private JList listSelectedlist;			// ���� ���ø�� ��¿� JList
	private int jlistNum;					// ������ ����� ��� ����ȭ�鿡�� �Դ��� �����ϱ� ���� ���
											// 1='�޴�'����ȭ��, 2='��õ�Ĵ�'����ȭ��, 3='�� �Ĵ�'����ȭ��
	private String selectedMenuCenter;		// �߾� JList���� ������ ���ĸ�
	private String selectedMenuLeft;		// ���� JList���� ������ ���ĸ�
	private JLabel lblTotalKcal;			// ������ �޴����� �� Į�θ� �ջ��� ����� ��
	private float totalKcal;				// �޴����� �� Į�θ� �ջ�
	private float todayTotalKcal;			// ��õ�Ĵ� ��Į�θ�
	private float[] myMenuTotalKcal;		// '�� �Ĵ�'�� �� �Ĵ��� ��Į�θ�
	private int[] myMenuCheck;				// DB�� ��ϵǾ��ִ� '�� �Ĵ�'�� ���� üũ��
	private int myMenuCnt;					// DB�� �̹� ��ϵǾ��ִ� '�� �Ĵ�' �Ĵ��Ǽ�(Max = 3)
	private String todayMenu;				// ��õ�Ĵ�
	private JTextField txtFieldMenuSearch;	// �޴� �˻��� �ؽ�Ʈ�ʵ�
	private String menuName;				// ���ĸ�(JList�Ǵ� �˻�â���� ���)
	private String finalMenu;				// �Ĵ�Ȯ���� �����Ĵ�����
	private String finalMenuNum;			// �Ĵ�Ȯ���� �����Ĵ� �޴����� ��ȣ
	private JButton btnMylistAdd;			// '�� �Ĵ�' �߰���ư
	private JButton btnMylistDelete;		// '�� �Ĵ�' ���Ź�ư
	
	//��õ�Ĵ� ���
	private String[] todayBrunch	= { "97,98,99,100",
										"20,101,102,103",
										"52,1,115,104",
										"97,1,99,102",
										"20,98,103,104"};

	private String[] todayLunch		= {	"110","111","112","113","114"};

	private String[] todayDinner	= {	"105,106",
										"105,107",
										"108,109",
										"108,12",
										"108,88"};

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MenuSelect frame = new MenuSelect();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	//ȭ�� ȣ��� ������ �Ű�����
	//login			: ȸ�� 1���� ������ü
	//arrSelectAll	: ��� ���������� ��� ArrayList
	//dTime			: ��ħ, ����, ����(������� Ŭ���ؼ� ���Դ���)(1=��ħ,2=����,3=����)
	public MenuSelect() {}
	public MenuSelect(MemDBVO login, ArrayList<FoodDBVO> arrSelectAll, int dTime)
									throws ClassNotFoundException, SQLException{	
		setTitle("���� �� ����?");

		MemDBDAO mdao = new MemDBDAO();				//ȸ�������Ϳ� DAO
		totalKcal = 0;								//�� Į�θ� �ջ� �ʱ�ȭ
		jlistNum = 1;								//�⺻ȭ�� = '�޴�'ȭ������ ����
		int todayRandom = (int)(Math.random()*5);	//��õ�Ĵܿ� ����

		//��ü���ĸ�� ����
		//ó������ ȭ�� ���Խ� �߾� JList�� ��ü���ĸ���� ��µǵ��� �Ѵ�
		//�Ʒ� DefaultListModel(dModel)�� ��ü ���ĸ���� ���õǰ�
		//�ϴ� JList��ºο��� �� DefaultListModel�� ������ ����
		dModel = new DefaultListModel();						//��ü����
		for(int i=0; i<arrSelectAll.size(); i++) {				//dModel�� ��ü���ĸ�� ����
			dModel.addElement(arrSelectAll.get(i).getNum()+". "	//������ �����鼭 ������ ���Ĵ�� �����Ѵ�
					+arrSelectAll.get(i).getName()+"("
					+arrSelectAll.get(i).getGram()+")");		//������� : '��ȣ. �����̸�(�뷮)'
		}														//ex) 1. �ҹ�(250g)

		//���õ� �޴���(����) JList��ü����
		fModel = new DefaultListModel();
		//'�� �޴�' �߰�/������ư ��ü���� �� ��Ȱ��ȭ
		btnMylistAdd = new JButton("");
		btnMylistDelete = new JButton("");
		btnMylistAdd.setVisible(false);
		btnMylistDelete.setVisible(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//'�޴�'��ư
		//Ŭ���ϸ� ��ü ���ĸ���� �߾� JList�� ��µȴ�
		//��ư Ŭ���� �̺�Ʈ�� �߻��ϰ� �� �������� DefaultListModel�� ������ ��� clear()�ϰ�
		//�ٽ� ����� �����Ѵ�
		JButton btnMenu = new JButton("");
		btnMenu.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_\uBA54\uB274.png")));
		btnMenu.setFont(new Font("����", Font.PLAIN, 12));
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//�̺�Ʈ �߻�
				if(arg0.getSource() == btnMenu) {
					//'�� �޴�' �߰�/������ ��ư ��Ȱ��ȭ
					btnMylistAdd.setVisible(false);
					btnMylistDelete.setVisible(false);
					//'�޴�'��ư�� ������ ���û��°� �ʱ�ȭ�ȴ�
					listMenulist.clearSelection();
					//JList��ȣ ����('�޴�'��ư = 1)
					jlistNum = 1;
					//���� DefaultListModel�� ���� �ʱ�ȭ
					dModel.clear();
					//�ʱ�ȭ�� DefaultListModel�� ��ü���ĸ���� �����Ѵ�
					for(int i=0; i<arrSelectAll.size(); i++) {
						dModel.addElement((i+1)+". "+arrSelectAll.get(i).getName()+"("+
								arrSelectAll.get(i).getGram()+")");
					}
					//�߾� JList ����
					//��ư�� �������μ� ������ �ٲ� JList�� ���������μ� �ﰢ������ ���̵��� �Ѵ�
					listMenulist.setModel(dModel);
					listMenulist.invalidate();
					listMenulist.repaint();
				}
			}
		});
		btnMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		btnMenu.setBounds(300, 93, 87, 36);
		contentPane.add(btnMenu);

		
		//'��õ�Ĵ�'��ư
		//Ŭ���ϸ� ������ ��õ�޴��� �߾� JList�� ���
		//��ư�� Ŭ���ϸ� ������ �Ĵ��� DefaultModelList�� �ʱ�ȭ�� JList�� ������ ����Ѵ�
		JButton btnMenuFavorite = new JButton("");
		btnMenuFavorite.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_rcmd.png")));
		btnMenuFavorite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//�̺�Ʈ �߻�
				if(arg0.getSource() == btnMenuFavorite) {
					//'�� �޴�' �߰�/������ ��ư ��Ȱ��ȭ
					btnMylistAdd.setVisible(false);
					btnMylistDelete.setVisible(false);
					//'��õ�Ĵ�'��ư�� ������ ���û��°� �ʱ�ȭ�ȴ�
					listMenulist.clearSelection();
					//JList��ȣ ����('�����ǽĴ�'��ư = 2)
					jlistNum = 2;
					todayTotalKcal = 0;		//��õ�Ĵ� ��Į�θ� �ʱ�ȭ
					String todayMenuNum;	//��õ�Ĵ��� ���� ��ȣ���ڿ�
					//3�� ȭ�鿡�� ��ħ,����,������ ��ɷ� ���Դ��� Ȯ����
					//�� �Ĵ��� �ִ� �迭���� �������� �� �Ĵ��� ���� �Ĵܹ�ȣ�� �����Ѵ�
					if(dTime == 1)//��ħ
						todayMenuNum = todayBrunch[todayRandom];
					else if(dTime == 2)//����
						todayMenuNum = todayLunch[todayRandom];
					else if(dTime == 3)//����
						todayMenuNum = todayDinner[todayRandom];
					else
						//� ������ �� 1,2,3�� �ƴҰ�� null�� ����
						todayMenuNum = null;
					try {
						//�Ĵ� ��ȣ�� ���� ���ڿ��� ���� ������ �ִ´�
						String[] todayMenus = todayMenuNum.split(",");
						todayMenu = "�� ";

						//�Ĵ� ��ȣ�� �ش� �Ĵ��� �˻� ��
						//�����Ĵ� ������ �ϳ��� �����Ѵ�
						for(int i=0; i<todayMenus.length; i++) {
							//�Ĵܹ�ȣ�� ��� ������������ �˻�
							for(FoodDBVO f : arrSelectAll) {
								if(f.getNum() == Integer.parseInt(todayMenus[i])) {
									//��õ�Ĵ� ��¿� ���ڿ��� �����ϸ鼭 ���ÿ� �ش� ���ĵ��� Į�θ��� �����ջ��Ѵ�
									todayMenu += f.getNum()+"."+f.getName()+"("+f.getGram()+"),";
									todayTotalKcal += f.getKcal();
								}
							}
						}
						
						//��õ�Ĵ� ���ڿ� �� �������� �Ĵ��� �� Į�θ� ����
						todayMenu += " "+todayTotalKcal+"Kcal";
					} catch (NullPointerException e) {
						//�� ��ħ,����,���� Ȯ�κο��� � ������ ������ ���� todayMenuNum = null�� �Ǿ�����
						//�� ���� �ڵ� ����� ���ܹ߻� ==> ��õ�Ĵ��� ���ٴ� �޼����� �˸�â�� ���� JList���� �ƹ��͵� ���������
						todayMenu = null;	//�Ŀ�  '<<'(�߰�)��ư �������� Ȯ���� ���� null�� ����
						JOptionPane.showMessageDialog(contentPane, "������ ��õ�Ĵ��� �����ϴ�");
					}

					//DefaultListModel �ʱ�ȭ
					dModel.clear();
					//DefaultListModel�� ��õ�Ĵ� ����
					dModel.addElement(todayMenu);
					listMenulist.setModel(dModel);
					//JList ����
					listMenulist.invalidate();
					listMenulist.repaint();
				}
			}
		});
		btnMenuFavorite.setFont(new Font("����", Font.PLAIN, 12));
		btnMenuFavorite.setBounds(430, 93, 87, 36);
		btnMenuFavorite.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnMenuFavorite);

		
		//'�� �޴�'��ư
		//ȸ���� ������ �Ĵ��� JList�� �����ִ� ��ư
		//ȸ���� ���ã�� �޴�
		JButton btnMenuMylist = new JButton("");
		btnMenuMylist.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_mymeal.png")));
		btnMenuMylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//�̺�Ʈ �߻�
				if(e.getSource() == btnMenuMylist) {
					//'�� �޴�' �߰�/������ ��ư Ȱ��ȭ
					btnMylistAdd.setVisible(true);
					btnMylistDelete.setVisible(true);
					//'�� �޴�'��ư�� ������ ���û��°� �ʱ�ȭ�ȴ�
					listMenulist.clearSelection();
					//JList��ȣ ����('�� �޴�'��ư = 3)
					jlistNum = 3;
					//DefaultListModel �ʱ�ȭ
					dModel.clear();

					//'�� �޴�' JList ��º�
					//'�� �޴�' �� �Ĵܵ� ��Į�θ� �ʱ�ȭ
					myMenuTotalKcal = new float[]{0,0,0};			//�� �Ĵ� ��Į�θ� �ʱ�ȭ
					myMenuCheck = new int[]{0, 0, 0};				//DB�� ����� '�� �޴�' ���� üũ�� �迭 �ʱ�ȭ
					myMenuCnt = 0;									//��ϵ� �Ĵܼ� �ʱ�ȭ
					String[] myMenus = {null, null, null};			//'�� �޴�' ��ȣ�� ����Ʈ(�ϳ��ϳ��� �Ĵ�)
																	//ex) {"1,2,3,4,5", "3,5,7,8,9", ...}
					String myMenuNums = null;						//'�� �޴�' 1���� ��ȣ��(�� �Ĵ��� ��ȣ��)
																	//ex) "1,2,3,4,5"
					String myListFinal = null;						//JList�� ��µ� 1���� �Ĵܹ��ڿ�
																	//ex) �޴�1. : 2.��̹�(220g),5.�̿���(120g)..

					//�Ĵ� ���� üũ �� �Ĵ� ��ȣ ����
					if(login.getMyList1() != null) {
						myMenus[0] = login.getMyList1();
						myMenuCheck[0] = 1;
						myMenuCnt++;
					}
					if(login.getMyList2() != null) {
						myMenus[1] = login.getMyList2();
						myMenuCheck[1] = 1;
						myMenuCnt++;
					}
					if(login.getMyList3() != null) {
						myMenus[2] = login.getMyList3();
						myMenuCheck[2] = 1;
						myMenuCnt++;
					}

					//��ϵ� '�� �޴�'�� ���(��ϵ��� �ʾ� �� '�� �޴�'�� ��¿��� ����)
					for(int i=0; i<myMenuCheck.length; i++) {
						//�Ĵ��� ��ϵǾ� ������ 1, �ƴϸ�0
						//�ش� ��ġ�� �Ĵ��� ��ϵǾ��ִٸ�
						if(myMenuCheck[i] == 1) {
							myListFinal = "�޴�"+(i+1)+" : ";		//ex) "�޴�1 : "
							//�ش� ��ġ�� �Ĵܹ�ȣ�� ����
							myMenuNums = myMenus[i];
							//����ó��
							try {
								//�Ĵܹ�ȣ �и��� ���� ���ĵ����Ϳ��� �ش� ������ �˻� �� ��¿� ���ڿ��� ����
								String[] myMenu = myMenuNums.split(",");
								for(int j=0; j<myMenu.length; j++) {
									for(FoodDBVO f : arrSelectAll) {
										if(f.getNum() == Integer.parseInt(myMenu[j])) {
											myListFinal += f.getNum()+". "+f.getName()+"("+f.getGram()+"),";
											myMenuTotalKcal[i] += f.getKcal();
										}
									}
								}

								myListFinal += " "+myMenuTotalKcal[i]+"Kcal";
								//DefaultListModel�� '�� �޴�' ����
								dModel.addElement(myListFinal);
							} catch (NullPointerException e2) {
								//���ܹ߻��� �ش� �߰��۾� �ƹ��͵� ���� �ʴ´�
							}
						}
					}

					//JList����
					listMenulist.setModel(dModel);
					listMenulist.invalidate();
					listMenulist.repaint();
				}
			}
		});
		btnMenuMylist.setFont(new Font("����", Font.PLAIN, 12));
		btnMenuMylist.setBounds(564, 93, 87, 36);
		btnMenuMylist.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnMenuMylist);

		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(300, 140, 350, 340);
		contentPane.add(scrollPane);
		//�߾� JList ����(���ĸ޴� ��º�)
		//JList�� listMenulist�� DefaultListModel�� dModel�� �ٿ� ����Ѵ�
		//JList listMenulist = new JList(); �� ������ �ڵ忴���� �������� �����ϸ鼭 ����δ� �� ��������
		//JList�� DefaultListModel(dModel)�� ���δ�
		listMenulist = new JList(dModel);
		//�̺�Ʈ�߻�
		listMenulist.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(arg0.getSource() == listMenulist)
					//JList���� ������ ������ String ������ ��´�
					selectedMenuCenter = (String)listMenulist.getSelectedValue();
			}
		});
		listMenulist.setFont(new Font("����", Font.BOLD, 15));
		listMenulist.setModel(new AbstractListModel() {
			//�̰��� JList�� ������ �����ϴ� String[]�� �־�����
			//DefaultListModel�� �������� ����ϸ鼭 �ʿ䰡 �������⿡ ����
			//������ �־��� �ڵ��, ��ü�� String[]�̾��� values ���� dModel(DefaultListModel)�� �ٲ����
			//JList�� ũ��� ��ҵ��� �������� �޼ҵ�
			public int getSize() {
				return dModel.size();
			}
			public Object getElementAt(int index) {
				return dModel.get(index);
			}
		});
		listMenulist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listMenulist);

		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(40, 140, 165, 243);
		contentPane.add(scrollPane_1);
		//���� JList ����(���ø޴� ��º�)
		//JList�� listSelectedlist�� DefaultListModel�� fModel�� �ٿ� ����Ѵ�
		//JList listSelectedlist = new JList(); �� ������ �ڵ忴���� �������� �����ϸ鼭 ����δ� �� ��������
		//�⺻����� ���� �ֱٿ� �����ߴ� �޴�
		// 3��ȭ�鿡�� �Ѿ�ö� ���� JList�� ������ �����ߴ� �޴� ����
		String[] preMenuNums = null;	//���� �޴���ȣ��
		//����ó��, ���� null�� ���
		try {
			//��ħ,����,���� üũ �� ��ȣ ����
			if(dTime == 1) {
				preMenuNums = login.getBre().split(",");
			}
			if(dTime == 2) {
				preMenuNums = login.getLun().split(",");
			}
			if(dTime == 3) {
				preMenuNums = login.getDin().split(",");
			}

			//�� �޴���ȣ�� �ش��ϴ� ���ĵ� �˻��ؼ� DefaultListModel�� ����
			for(int i=0; i<(preMenuNums.length); i++) {
				for(FoodDBVO f : arrSelectAll) {
					if(f.getNum() == Integer.parseInt(preMenuNums[i])) {
						fModel.addElement(f.getNum()+". "+f.getName()+"("+f.getGram()+")");
						totalKcal += f.getKcal();
					}
				}
			}

			//null�� �ƴ϶�� �� Į�θ� ��� ���� �޴����� �� Į�θ� ��
			lblTotalKcal = new JLabel("�� Į�θ� : " + totalKcal + "Kcal");

		} catch (NullPointerException e2) {
			//���� null�̶�� ���� JList���� �ƹ��͵� ������� ����
			//null�� ��� �� Į�θ� ��� ���� 0Kcal ���
			lblTotalKcal = new JLabel("�� Į�θ� : 0.0Kcal");
			totalKcal = 0;
		}


		//�� Į�θ� ��¿� ��(�ʱ� ��� ������ �� try-catch�� �ȿ�)
		lblTotalKcal.setFont(new Font("����", Font.BOLD, 13));
		lblTotalKcal.setBounds(40, 395, 165, 30);
		contentPane.add(lblTotalKcal);

		//������ DefaultListModel�� JList�� ���δ�
		listSelectedlist = new JList(fModel);
		listSelectedlist.addListSelectionListener(new ListSelectionListener() {
			//�̺�Ʈ�߻�(���Ŭ��)
			public void valueChanged(ListSelectionEvent arg0) {
				if(arg0.getSource() == listSelectedlist)
					//JList���� ������ ������ String ������ ��´�
					selectedMenuLeft = (String)listSelectedlist.getSelectedValue();
			}
		});
		listSelectedlist.setModel(new AbstractListModel() {
			//�̰��� JList�� ������ �����ϴ� String[]�� �־�����
			//DefaultListModel�� �������� ����ϸ鼭 �ʿ䰡 �������⿡ ����
			//������ �־��� �ڵ��, ��ü�� String[]�̾��� values ���� fModel(DefaultListModel)�� �ٲ����
			//JList�� ũ��� ��ҵ��� �������� �޼ҵ�
			public int getSize() {
				return fModel.size();
			}
			public Object getElementAt(int index) {
				return fModel.get(index);
			}
		});
		listSelectedlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelectedlist.setFont(new Font("����", Font.PLAIN, 12));
		scrollPane_1.setViewportView(listSelectedlist);


		//�����߰���ư
		//�߾� JList���� ������ �����ϰ� �� ��ư�� ������ ������ ������ ���� JList�� �߰��ȴ�
		JButton btnAddMenu = new JButton("");
		btnAddMenu.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_Arrow4040_2.png")));
		btnAddMenu.addActionListener(new ActionListener() {
			//�̺�Ʈ �߻�
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnAddMenu) {
					//'�޴�'��ư���� ���۽�
					if(jlistNum == 1) {
						//�� Į�θ� ���
						//�߰���ư�� ������ �޴��� �߰��԰� ���ÿ� ���� JList�ϴܺο� ������ �޴����� �� Į�θ��� ���
						try {
							//menuName : �߰��� �޴��� ��ȣ(DB���� �˻��Ҽ��ְ� ���� ��ȣ �κи� �ڸ���)
							//ex) 1. �ҹ�(250g) => 1 �� �ǵ��� �ڸ�
							menuName = selectedMenuCenter.substring(0, selectedMenuCenter.lastIndexOf("."));
							//�� Į�θ� ��¿� �� ����
							//������ �޴��� �˻��� �ش� �޴��� Į�θ��� �󺧿� �ջ��ؼ� �����Ѵ�
							for(FoodDBVO f : arrSelectAll) {
								if(Integer.parseInt(menuName) == f.getNum()) {
									//������ �޴��� Į�θ��� �� Į�θ��� �ջ�
									totalKcal += f.getKcal();
									//������ Į�θ� �󺧿� ���
									lblTotalKcal.setText("�� Į�θ� : " + totalKcal + "Kcal");
								}
							}

							fModel.addElement(selectedMenuCenter);	//fModel : ���� JList�� DefaultListModel

						} catch (Exception e2) {
							//������ ��������� �ƹ��͵� ���� �ʴ´�
						}
					}
					//'��õ�Ĵ�'��ư���� ���۽�
					//�� ���� 1�ٷ� �Ǿ��ִ� ��õ�Ĵ��� �� �޴��� ���ų� �� �� �ְ� ���� ���� JList�� �����ϴ� �۾��� �Ѵ�
					if(jlistNum == 2) {
						//�ƹ��͵� ������ ���� ���¿��� �������� �߰�����
						if(listMenulist.getSelectedValue() != null) {
							//��ħ,����,���� Ȯ�κο��� ������ todayMenu = null�� ���õ�
							//������ ���ٸ� ����
							if(!(todayMenu == null)) {
								//��õ�Ĵ� �޴����� ���� ������
								String[] menus = todayMenu.substring(2).split(",");
								//�� �������� �� Į�θ��� ����ֱ⿡ �� ���̴� length-1 �̸�
								for(int i=0; i<(menus.length-1); i++) {
									//�޴����� ���� ����
									fModel.addElement(menus[i]);
								}
								//�ϴܺ� �󺧿� �� Į�θ� ����
								totalKcal += todayTotalKcal;
								lblTotalKcal.setText("�� Į�θ� : " + totalKcal + "Kcal");
							}
						}
					}
					//'�� �޴�'��ư���� ���۽�
					if(jlistNum == 3) {
						try {
							//'�� �޴�' �޴����� ���� ������
							String[] menus = selectedMenuCenter.substring(6).split(",");
							int num = (Integer.parseInt(selectedMenuCenter.substring(2,3)) -1) ;
							//�� �������� �� Į�θ��� ����ֱ⿡ �� ���̴� length-1�̸�
							for(int i=0; i<(menus.length-1); i++) {
								//�޴��� ���� ����
								fModel.addElement(menus[i]);
							}

							//�ϴܺ� �󺧿� �� Į�θ� ����
							totalKcal += myMenuTotalKcal[num];
							lblTotalKcal.setText("�� Į�θ� : " + totalKcal + "Kcal");

							
						} catch (NullPointerException | NumberFormatException e2) {
							//������ ��������� �ƹ��͵� ���� �ʴ´�
						}
					}
					
					//DefaultListModel�� ���� �߰��ϰ� JList�� ���δ�
					listSelectedlist.setModel(fModel);	//listSelectedlist	: ���� JList��ü��
					//JList����
					listSelectedlist.invalidate();
					listSelectedlist.repaint();
				}
			}
		});
		btnAddMenu.setBounds(226, 200, 52, 36);
		btnAddMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnAddMenu);

		
		//���Ļ�����ư
		//���� JList���� ������ �����ϰ� �� ��ư�� ������ ������ ������ ���� JList���� �����ȴ�
		JButton btnDeleteMenu = new JButton("");
		btnDeleteMenu.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_Arrow4040.png")));
		btnDeleteMenu.addActionListener(new ActionListener() {
			//�̺�Ʈ �߻�
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnDeleteMenu) {
					//�� Į�θ� ���
					//������ư�� ������ �޴��� �����԰� ���ÿ� ���� JList�ϴܺο� ������ �޴����� �� Į�θ��� ���
					try {
						//menuName : �߰��� �޴��� ��ȣ(DB���� �˻��Ҽ��ְ� ���� ��ȣ �κи� �ڸ���)
						//ex) 1. �ҹ�(250g) => 1 �� �ǵ��� �ڸ�
						menuName = selectedMenuLeft.substring(0, selectedMenuLeft.lastIndexOf("."));
						//�� Į�θ� ��¿� �� ����
						//������ �޴��� �˻��� �ش� �޴��� Į�θ��� �󺧿� ���� �����Ѵ�
						for(FoodDBVO f : arrSelectAll) {
							if(Integer.parseInt(menuName) == f.getNum()) {
								//������ �޴��� Į�θ��� �� Į�θ����� ��
								totalKcal -= f.getKcal();
								//������ Į�θ� �󺧿� ���
								lblTotalKcal.setText("�� Į�θ� : " + totalKcal + "Kcal");
							}
						}

						//DefaultListModel�� ���� �����ϰ� JList�� �����Ѵ�
						fModel.removeElement(selectedMenuLeft);	//fModel	: ���� JList�� DefaultListModel
						listSelectedlist.setModel(fModel);
						//JList����
						listSelectedlist.invalidate();
						listSelectedlist.repaint();
					} catch (Exception e2) {
						//������ ��������� �ƹ� �ൿ�� ���� �ʴ´�
					}
				}
			}
		});
		btnDeleteMenu.setBounds(226, 280, 52, 36);
		btnDeleteMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnDeleteMenu);
		
		
		//�ʱ�ȭ��ư
		//��ư�� Ŭ���ϸ� �������� ������ �޴����� ���� �����Ѵ�
		JButton btnSelectedListClear = new JButton("");
		btnSelectedListClear.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/3_reset.png")));
		btnSelectedListClear.addActionListener(new ActionListener() {
			//�̺�Ʈ�߻�
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnSelectedListClear) {
					//�ʱ�ȭ �޽���
					JOptionPane.showMessageDialog(contentPane, "����� �ʱ�ȭ �մϴ�");
					//DefaultListModel �ʱ�ȭ
					fModel.clear();
					//JList����
					listSelectedlist.invalidate();
					listSelectedlist.repaint();
					//�� Į�θ� 0���� �ʱ�ȭ
					totalKcal = 0;
					//������ Į�θ� �󺧿� ���
					lblTotalKcal.setText("�� Į�θ� : " + totalKcal + "Kcal");
				}
			}
		});
		btnSelectedListClear.setBounds(76, 435, 86, 36);
		btnSelectedListClear.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnSelectedListClear);

		
		//�޴� �˻��� �ؽ�Ʈ�ʵ�
		txtFieldMenuSearch = new JTextField();
		txtFieldMenuSearch.setFont(new Font("���� ���", Font.PLAIN, 20));
		txtFieldMenuSearch.setBounds(300, 23, 274, 40);
		contentPane.add(txtFieldMenuSearch);
		txtFieldMenuSearch.setColumns(10);
		txtFieldMenuSearch.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		
		
		//�޴��˻� ��ư
		JButton btnMenuSearch = new JButton("");
		btnMenuSearch.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/3_macro.png")));
		btnMenuSearch.addActionListener(new ActionListener() {
			//�̺�Ʈ�߻�
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource() == btnMenuSearch) {
					jlistNum = 1;
					//�޴� ���� Ȯ�ο� flag
					boolean flag = false;
					//�˻�â���� �Է��� �޴��� ����
					menuName = txtFieldMenuSearch.getText();
					//��� ���ĸ޴��߿� �˻�â�� �Է��� �޴��� �ִ��� üũ
					for(FoodDBVO f : arrSelectAll) {
						//�˻� �޴��� �ִٸ�
						if(menuName.equals(f.getName())) {
							//�߾� JList�� DefaultListModel Ŭ����
							dModel.clear();
							//�˻��� �޴� ����(ex.  1. �ҹ�(250g)
							dModel.addElement(f.getNum()+". "+f.getName()
														+"("+f.getGram()+")");
							//JList����
							listMenulist.setModel(dModel);
							listMenulist.invalidate();
							listMenulist.repaint();
							//�÷��� true��
							flag = true;
						}
					}
					//�˻��� �޴��� �ִ� => flag = true�� �Ǳ⿡ �Ʒ� if���� ���� ����
					//�˻��� �޴��� ���� => flag = false �״�� �������⿡ if���� �� �Ʒ� �޼����� �����
					if(!flag)	//�޴��� ������ ���
						JOptionPane.showMessageDialog(contentPane, "���� �޴��Դϴ�");
				}
			}
		});
		btnMenuSearch.setBackground(Color.WHITE);
		btnMenuSearch.setForeground(Color.WHITE);
		btnMenuSearch.setBounds(590, 23, 38, 38);
		btnMenuSearch.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnMenuSearch);

		
		//�Ĵ�Ȯ�� ��ư
		//��ư�� ������ ���� JList�� ���õ� �޴����� �Ĵ����� ����Ǹ� �� �������� ������ ���ÿ�
		//3�� ȭ������ ����
		JButton btnFinalMenu = new JButton("");
		btnFinalMenu.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_Mealcfm.png")));
		btnFinalMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//�̺�Ʈ �߻�
				if(arg0.getSource() == btnFinalMenu) {
					finalMenu = "";			// �����Ĵ�
					finalMenuNum = "";		// �����Ĵ� �޴����� ��ȣ
					boolean check = false;
					//finalMenuNum�� ���õ� �޴����� ��ȣ�� �޾�
					//finalMenu�� �ϳ��ϳ� �߰��Ѵ�
					for(int i=0; i<fModel.size(); i++) {						
						finalMenuNum = (String)fModel.get(i);	//�޴��� ��ȣ ����
						finalMenu = finalMenu					//�����Ĵܿ� �޴���ȣ�� �߰�
								+ finalMenuNum.substring(0, finalMenuNum.lastIndexOf("."))
								+ ",";				
					}
					
					//�޴��� �ƹ��͵� �������� ���� ���¿��� '�Ĵ�Ȯ��'��ư Ŭ������ ����ó��
					try {
						//�����޴�üũ
						if(!finalMenu.equals("")) {
							//��ħ,����,���� üũ
							//��ħ
							if(dTime == 1) {
								//�����ü�� DB�� ��ħ�Ĵ� ����
								login.setBre(finalMenu);							//�����ü
								check = mdao.update_bre(login.getId(), finalMenu);	//DB
								//����
							}else if(dTime == 2) {
								//�����ü�� DB�� ��ħ�Ĵ� ����
								login.setLun(finalMenu);							//�����ü
								check = mdao.update_lun(login.getId(), finalMenu);	//DB
								//����
							}else if(dTime == 3) {
								//�����ü�� DB�� ��ħ�Ĵ� ����
								login.setDin(finalMenu);							//�����ü
								check = mdao.update_din(login.getId(), finalMenu);	//DB
							}
						}else {
							//�����޴��� ���� ������  ������ �Ǹ� ���� ���ܹ߻�
							throw new NumberFormatException();
						}
						
						//3��ȭ�鿡 �� �ѱ�鼭 Ȱ��ȭ
						new MealSet(login, arrSelectAll).setVisible(true);						
						//����ȭ�� ��Ȱ��ȭ
						dispose();
						setVisible(false);
					} catch (NumberFormatException e) {
						//�˸�â ���� ���� ȭ�� �״��
						JOptionPane.showMessageDialog(contentPane, "�޴��� ������ �ּ���");
					}
				}
			}
		});
		btnFinalMenu.setFont(new Font("����", Font.BOLD, 14));
		btnFinalMenu.setBounds(721, 448, 86, 36);
		btnFinalMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnFinalMenu);

		
		JLabel lblMenuLabel = new JLabel("");
		lblMenuLabel.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_selectedmenu.png")));
		lblMenuLabel.setFont(new Font("����", Font.BOLD, 13));
		lblMenuLabel.setBounds(70, 95, 110, 30);
		contentPane.add(lblMenuLabel);

		
		//'�� �޴�' �߰� ��ư
		//��ư�� ������ ���� JList�� ���õ� �޴����� '�� �޴�'�� ��ϵȴ�
		btnMylistAdd.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_addmeal.png")));
		btnMylistAdd.addActionListener(new ActionListener() {
			//�̺�Ʈ�߻�
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnMylistAdd) {
					int menuInputNum = -1;			//����ִ� �Ĵ�MyList��ġ
					String myMenuInput = null;		//'�� �޴�'�� ���� ���� �Ĵ�
					boolean updatecheck = false;	//DB���� üũ��
					//'�� �޴�'��ư�� �������� ȸ���� ����� '�� �޴�'�� �� ����� ����Ѵ�
					//�� �Ĵܼ��� 3��(���ε��)�϶��� �˸�â
					if(myMenuCnt == 3)
						JOptionPane.showMessageDialog(contentPane, "���� ������ �� �޴��� ���� 3�����Դϴ�");
					else {
						//����ó��
						try {
							//ȸ���� ����� '�� �޴�'�Ĵ� ��, ���� ������� ���� �Ĵ��� ��ġ üũ
							//'�� �޴�'����ÿ��� ���
							for(int i=0; i<myMenuCheck.length; i++) {
								if(myMenuCheck[i] == 0) {
									menuInputNum = (i+1);
									break;	//1�� �̻� ����ִ��� �� ���ʸ� ã���� ����Ż��(�Ĵ������ �� ��ġ�� ����)
								}
							}

							if(menuInputNum>=1 && menuInputNum<=3) {
								//������ �Ĵ� ���ڿ� �ʱ�ȭ
								myMenuInput = "";
								//�ӽ� ����� ����
								String fModelStr = "";
								//���ڿ� ����(�� �������� ,�� ������ �ʱ⿡ ���̴� ����-1)
								for(int i=0; i<(fModel.size()-1); i++) {
									fModelStr = (String)fModel.get(i);
									myMenuInput += fModelStr.substring(0, fModelStr.lastIndexOf(".")) + ",";
								}
								//�� ������ ���Ĺ�ȣ ����
								fModelStr = (String)fModel.get(fModel.size()-1);
								myMenuInput += fModelStr.substring(0, fModelStr.lastIndexOf("."));
////////////////////////
								//DB Mylist�� �Ĵܵ��
								updatecheck = mdao.insertMylist(menuInputNum, myMenuInput, login.getId());
								//DB�۾� ������ ȸ�� ��ü���� ��ϵ� ���� ����
								if(updatecheck) {
									if(menuInputNum == 1) {
										login.setMyList1(myMenuInput);;
									}else if(menuInputNum == 2) {
										login.setMyList2(myMenuInput);
									}else if(menuInputNum == 3) {
										login.setMyList3(myMenuInput);
									}

									JOptionPane.showMessageDialog(contentPane, "�Ĵ��� ��ϵǾ����ϴ�");

								}else {
									JOptionPane.showMessageDialog(contentPane, "�Ĵܵ�� ����");
								}
							}
						} catch (ArrayIndexOutOfBoundsException | NullPointerException e2) {
							JOptionPane.showMessageDialog(contentPane, "�޴��� ������ �ּ���");
						}
					}
				}
			}
		});
		btnMylistAdd.setBounds(695, 140, 138, 36);
		btnMylistAdd.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnMylistAdd);

		
		//'�� �޴�' ���� ��ư
		//'�� �޴�'���� �ش� �Ĵ��� Ŭ���ϰ� ���� ��ư�� ������ �޴�����
		btnMylistDelete.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_rmcmeal.png")));
		btnMylistDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//�̺�Ʈ�߻�
				if(arg0.getSource() == btnMylistDelete) {
					try {
						//������ �޴���ȣ ����
						int deleteMenuNum = Integer.parseInt(selectedMenuCenter.substring(2,3));
						//DB�۾� üũ��
						boolean deleteCheck = false;

						//DB�۾�
						deleteCheck = mdao.deleteMylist(deleteMenuNum, login.getId());
						//DB�۾� ������ ȸ�� ��ü���� ������ ���� ����
						if(deleteCheck) {
							if(deleteMenuNum == 1) {
								login.setMyList1(null);
							}else if(deleteMenuNum == 2) {
								login.setMyList2(null);
							}else if(deleteMenuNum == 3) {
								login.setMyList3(null);
							}
							
							JOptionPane.showMessageDialog(contentPane, "�Ĵ��� �����Ǿ����ϴ�");

						}else {
							JOptionPane.showMessageDialog(contentPane, "�Ĵ� ���� ����");
						}
					} catch (NullPointerException e) {
						JOptionPane.showMessageDialog(contentPane, "�޴��� ������ �ּ���");
					}
				}	
			}
		});
		btnMylistDelete.setBounds(695, 190, 142, 36);
		btnMylistDelete.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnMylistDelete);
		
		
		//�ڷΰ����ư
		JButton btnBack = new JButton("");
		btnBack.setBackground(Color.WHITE);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource() == btnBack) {
					new MealSet(login, arrSelectAll).setVisible(true);						
					dispose();
					setVisible(false);
				}
			}
		});
		btnBack.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/Arrow3030.png")));
		btnBack.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		btnBack.setBounds(40, 23, 30, 30);
		contentPane.add(btnBack);
	}
}