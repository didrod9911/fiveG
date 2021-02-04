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

	//전역변수선언
	private DefaultListModel dModel;		// 중앙(메뉴목록) JList에서 쓰는 DefaultListModel
	private DefaultListModel fModel;		// 좌측(선택목록) JList에서 쓰는 DefaultListModel
	private JList listMenulist;				// 중앙 메뉴목록 출력용 JList
	private JList listSelectedlist;			// 좌측 선택목록 출력용 JList
	private int jlistNum;					// 선택한 목록이 어느 선택화면에서 왔는지 구분하기 위해 사용
											// 1='메뉴'선택화면, 2='추천식단'선택화면, 3='내 식단'선택화면
	private String selectedMenuCenter;		// 중앙 JList에서 선택한 음식명
	private String selectedMenuLeft;		// 좌측 JList에서 선택한 음식명
	private JLabel lblTotalKcal;			// 선택한 메뉴들의 총 칼로리 합산을 출력할 라벨
	private float totalKcal;				// 메뉴들의 총 칼로리 합산
	private float todayTotalKcal;			// 추천식단 총칼로리
	private float[] myMenuTotalKcal;		// '내 식단'의 각 식단의 총칼로리
	private int[] myMenuCheck;				// DB에 등록되어있는 '내 식단'의 존재 체크용
	private int myMenuCnt;					// DB에 이미 등록되어있는 '내 식단' 식단의수(Max = 3)
	private String todayMenu;				// 추천식단
	private JTextField txtFieldMenuSearch;	// 메뉴 검색용 텍스트필드
	private String menuName;				// 음식명(JList또는 검색창에서 사용)
	private String finalMenu;				// 식단확정시 최종식단정보
	private String finalMenuNum;			// 식단확정시 최종식단 메뉴들의 번호
	private JButton btnMylistAdd;			// '내 식단' 추가버튼
	private JButton btnMylistDelete;		// '내 식단' 제거버튼
	
	//추천식단 목록
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
	//화면 호출시 들어오는 매개변수
	//login			: 회원 1명의 정보객체
	//arrSelectAll	: 모든 음식정보를 담는 ArrayList
	//dTime			: 아침, 점심, 저녁(어느쪽을 클릭해서 들어왔는지)(1=아침,2=점심,3=저녁)
	public MenuSelect() {}
	public MenuSelect(MemDBVO login, ArrayList<FoodDBVO> arrSelectAll, int dTime)
									throws ClassNotFoundException, SQLException{	
		setTitle("오늘 뭐 먹지?");

		MemDBDAO mdao = new MemDBDAO();				//회원데이터용 DAO
		totalKcal = 0;								//총 칼로리 합산 초기화
		jlistNum = 1;								//기본화면 = '메뉴'화면으로 세팅
		int todayRandom = (int)(Math.random()*5);	//추천식단용 난수

		//전체음식목록 세팅
		//처음으로 화면 진입시 중앙 JList에 전체음식목록이 출력되도록 한다
		//아래 DefaultListModel(dModel)에 전체 음식목록이 세팅되고
		//하단 JList출력부에서 이 DefaultListModel을 가져다 쓴다
		dModel = new DefaultListModel();						//객체생성
		for(int i=0; i<arrSelectAll.size(); i++) {				//dModel에 전체음식목록 세팅
			dModel.addElement(arrSelectAll.get(i).getNum()+". "	//루프를 돌리면서 정해진 형식대로 세팅한다
					+arrSelectAll.get(i).getName()+"("
					+arrSelectAll.get(i).getGram()+")");		//출력형식 : '번호. 음식이름(용량)'
		}														//ex) 1. 쌀밥(250g)

		//선택된 메뉴용(좌측) JList객체생성
		fModel = new DefaultListModel();
		//'내 메뉴' 추가/삭제버튼 객체생성 및 비활성화
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

		//'메뉴'버튼
		//클릭하면 전체 음식목록이 중앙 JList에 출력된다
		//버튼 클릭시 이벤트가 발생하고 그 전까지의 DefaultListModel의 내용을 모두 clear()하고
		//다시 목록을 세팅한다
		JButton btnMenu = new JButton("");
		btnMenu.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_\uBA54\uB274.png")));
		btnMenu.setFont(new Font("굴림", Font.PLAIN, 12));
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//이벤트 발생
				if(arg0.getSource() == btnMenu) {
					//'내 메뉴' 추가/삭제용 버튼 비활성화
					btnMylistAdd.setVisible(false);
					btnMylistDelete.setVisible(false);
					//'메뉴'버튼을 누르면 선택상태가 초기화된다
					listMenulist.clearSelection();
					//JList번호 세팅('메뉴'버튼 = 1)
					jlistNum = 1;
					//기존 DefaultListModel의 내용 초기화
					dModel.clear();
					//초기화된 DefaultListModel에 전체음식목록을 세팅한다
					for(int i=0; i<arrSelectAll.size(); i++) {
						dModel.addElement((i+1)+". "+arrSelectAll.get(i).getName()+"("+
								arrSelectAll.get(i).getGram()+")");
					}
					//중앙 JList 갱신
					//버튼을 누름으로서 내용이 바뀐 JList를 갱신함으로서 즉각적으로 보이도록 한다
					listMenulist.setModel(dModel);
					listMenulist.invalidate();
					listMenulist.repaint();
				}
			}
		});
		btnMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		btnMenu.setBounds(300, 93, 87, 36);
		contentPane.add(btnMenu);

		
		//'추천식단'버튼
		//클릭하면 오늘의 추천메뉴가 중앙 JList에 뜬다
		//버튼을 클릭하면 오늘의 식단을 DefaultModelList를 초기화후 JList에 세팅해 출력한다
		JButton btnMenuFavorite = new JButton("");
		btnMenuFavorite.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_rcmd.png")));
		btnMenuFavorite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//이벤트 발생
				if(arg0.getSource() == btnMenuFavorite) {
					//'내 메뉴' 추가/삭제용 버튼 비활성화
					btnMylistAdd.setVisible(false);
					btnMylistDelete.setVisible(false);
					//'추천식단'버튼을 누르면 선택상태가 초기화된다
					listMenulist.clearSelection();
					//JList번호 세팅('오늘의식단'버튼 = 2)
					jlistNum = 2;
					todayTotalKcal = 0;		//추천식단 총칼로리 초기화
					String todayMenuNum;	//추천식단을 담은 번호문자열
					//3번 화면에서 아침,점심,저녁중 어떤걸로 들어왔는지 확인후
					//각 식단이 있는 배열에서 랜덤으로 한 식단을 꺼내 식단번호를 세팅한다
					if(dTime == 1)//아침
						todayMenuNum = todayBrunch[todayRandom];
					else if(dTime == 2)//점심
						todayMenuNum = todayLunch[todayRandom];
					else if(dTime == 3)//저녁
						todayMenuNum = todayDinner[todayRandom];
					else
						//어떤 이유로 위 1,2,3이 아닐경우 null로 세팅
						todayMenuNum = null;
					try {
						//식단 번호를 담은 문자열을 각각 나누어 넣는다
						String[] todayMenus = todayMenuNum.split(",");
						todayMenu = "※ ";

						//식단 번호로 해당 식단을 검색 후
						//최종식단 변수에 하나씩 세팅한다
						for(int i=0; i<todayMenus.length; i++) {
							//식단번호를 모든 음식정보에서 검색
							for(FoodDBVO f : arrSelectAll) {
								if(f.getNum() == Integer.parseInt(todayMenus[i])) {
									//추천식단 출력용 문자열에 세팅하면서 동시에 해당 음식들의 칼로리를 누적합산한다
									todayMenu += f.getNum()+"."+f.getName()+"("+f.getGram()+"),";
									todayTotalKcal += f.getKcal();
								}
							}
						}
						
						//추천식단 문자열 맨 마지막에 식단의 총 칼로리 세팅
						todayMenu += " "+todayTotalKcal+"Kcal";
					} catch (NullPointerException e) {
						//위 아침,점심,저녁 확인부에서 어떤 이유로 오류가 나서 todayMenuNum = null이 되었을때
						//그 이후 코드 진행시 예외발생 ==> 추천식단이 없다는 메세지를 알림창에 띄우고 JList에는 아무것도 띄우지않음
						todayMenu = null;	//후에  '<<'(추가)버튼 눌렀을시 확인을 위해 null로 세팅
						JOptionPane.showMessageDialog(contentPane, "오늘의 추천식단이 없습니다");
					}

					//DefaultListModel 초기화
					dModel.clear();
					//DefaultListModel에 추천식단 세팅
					dModel.addElement(todayMenu);
					listMenulist.setModel(dModel);
					//JList 갱신
					listMenulist.invalidate();
					listMenulist.repaint();
				}
			}
		});
		btnMenuFavorite.setFont(new Font("굴림", Font.PLAIN, 12));
		btnMenuFavorite.setBounds(430, 93, 87, 36);
		btnMenuFavorite.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnMenuFavorite);

		
		//'내 메뉴'버튼
		//회원이 저장한 식단을 JList에 보여주는 버튼
		//회원의 즐겨찾기 메뉴
		JButton btnMenuMylist = new JButton("");
		btnMenuMylist.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_mymeal.png")));
		btnMenuMylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//이벤트 발생
				if(e.getSource() == btnMenuMylist) {
					//'내 메뉴' 추가/삭제용 버튼 활성화
					btnMylistAdd.setVisible(true);
					btnMylistDelete.setVisible(true);
					//'내 메뉴'버튼을 누르면 선택상태가 초기화된다
					listMenulist.clearSelection();
					//JList번호 세팅('내 메뉴'버튼 = 3)
					jlistNum = 3;
					//DefaultListModel 초기화
					dModel.clear();

					//'내 메뉴' JList 출력부
					//'내 메뉴' 각 식단들 총칼로리 초기화
					myMenuTotalKcal = new float[]{0,0,0};			//각 식단 총칼로리 초기화
					myMenuCheck = new int[]{0, 0, 0};				//DB에 저장된 '내 메뉴' 유무 체크용 배열 초기화
					myMenuCnt = 0;									//등록된 식단수 초기화
					String[] myMenus = {null, null, null};			//'내 메뉴' 번호들 리스트(하나하나가 식단)
																	//ex) {"1,2,3,4,5", "3,5,7,8,9", ...}
					String myMenuNums = null;						//'내 메뉴' 1개의 번호들(한 식단의 번호들)
																	//ex) "1,2,3,4,5"
					String myListFinal = null;						//JList에 출력될 1개의 식단문자열
																	//ex) 메뉴1. : 2.흑미밥(220g),5.미역국(120g)..

					//식단 존재 체크 및 식단 번호 세팅
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

					//등록된 '내 메뉴'만 출력(등록되지 않아 빈 '내 메뉴'는 출력에서 제외)
					for(int i=0; i<myMenuCheck.length; i++) {
						//식단이 등록되어 있으면 1, 아니면0
						//해당 위치에 식단이 등록되어있다면
						if(myMenuCheck[i] == 1) {
							myListFinal = "메뉴"+(i+1)+" : ";		//ex) "메뉴1 : "
							//해당 위치의 식단번호들 저장
							myMenuNums = myMenus[i];
							//예외처리
							try {
								//식단번호 분리후 각각 음식데이터에서 해당 데이터 검색 및 출력용 문자열로 세팅
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
								//DefaultListModel에 '내 메뉴' 세팅
								dModel.addElement(myListFinal);
							} catch (NullPointerException e2) {
								//예외발생시 해당 추가작업 아무것도 하지 않는다
							}
						}
					}

					//JList갱신
					listMenulist.setModel(dModel);
					listMenulist.invalidate();
					listMenulist.repaint();
				}
			}
		});
		btnMenuMylist.setFont(new Font("굴림", Font.PLAIN, 12));
		btnMenuMylist.setBounds(564, 93, 87, 36);
		btnMenuMylist.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnMenuMylist);

		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(300, 140, 350, 340);
		contentPane.add(scrollPane);
		//중앙 JList 세팅(음식메뉴 출력부)
		//JList인 listMenulist에 DefaultListModel인 dModel을 붙여 출력한다
		//JList listMenulist = new JList(); 가 원래의 코드였지만 전역으로 선언하면서 선언부는 맨 위쪽으로
		//JList에 DefaultListModel(dModel)을 붙인다
		listMenulist = new JList(dModel);
		//이벤트발생
		listMenulist.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(arg0.getSource() == listMenulist)
					//JList에서 선택한 음식을 String 변수에 담는다
					selectedMenuCenter = (String)listMenulist.getSelectedValue();
			}
		});
		listMenulist.setFont(new Font("굴림", Font.BOLD, 15));
		listMenulist.setModel(new AbstractListModel() {
			//이곳에 JList의 내용을 세팅하는 String[]이 있었지만
			//DefaultListModel을 내용으로 사용하면서 필요가 없어졌기에 삭제
			//기존에 있었던 코드들, 객체만 String[]이었던 values 에서 dModel(DefaultListModel)로 바뀌었다
			//JList의 크기와 요소들을 가져오는 메소드
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
		//좌측 JList 세팅(선택메뉴 출력부)
		//JList인 listSelectedlist에 DefaultListModel인 fModel을 붙여 출력한다
		//JList listSelectedlist = new JList(); 가 원래의 코드였지만 전역으로 선언하면서 선언부는 맨 위쪽으로
		//기본출력은 가장 최근에 선택했던 메뉴
		// 3번화면에서 넘어올때 좌측 JList에 기존에 선택했던 메뉴 띄우기
		String[] preMenuNums = null;	//이전 메뉴번호들
		//예외처리, 값이 null인 경우
		try {
			//아침,점심,저녁 체크 후 번호 세팅
			if(dTime == 1) {
				preMenuNums = login.getBre().split(",");
			}
			if(dTime == 2) {
				preMenuNums = login.getLun().split(",");
			}
			if(dTime == 3) {
				preMenuNums = login.getDin().split(",");
			}

			//각 메뉴번호에 해당하는 음식들 검색해서 DefaultListModel에 세팅
			for(int i=0; i<(preMenuNums.length); i++) {
				for(FoodDBVO f : arrSelectAll) {
					if(f.getNum() == Integer.parseInt(preMenuNums[i])) {
						fModel.addElement(f.getNum()+". "+f.getName()+"("+f.getGram()+")");
						totalKcal += f.getKcal();
					}
				}
			}

			//null이 아니라면 총 칼로리 출력 라벨은 메뉴들의 총 칼로리 합
			lblTotalKcal = new JLabel("총 칼로리 : " + totalKcal + "Kcal");

		} catch (NullPointerException e2) {
			//만약 null이라면 좌측 JList에는 아무것도 출력하지 않음
			//null일 경우 총 칼로리 출력 라벨은 0Kcal 출력
			lblTotalKcal = new JLabel("총 칼로리 : 0.0Kcal");
			totalKcal = 0;
		}


		//총 칼로리 출력용 라벨(초기 출력 세팅은 위 try-catch문 안에)
		lblTotalKcal.setFont(new Font("굴림", Font.BOLD, 13));
		lblTotalKcal.setBounds(40, 395, 165, 30);
		contentPane.add(lblTotalKcal);

		//세팅한 DefaultListModel을 JList에 붙인다
		listSelectedlist = new JList(fModel);
		listSelectedlist.addListSelectionListener(new ListSelectionListener() {
			//이벤트발생(목록클릭)
			public void valueChanged(ListSelectionEvent arg0) {
				if(arg0.getSource() == listSelectedlist)
					//JList에서 선택한 음식을 String 변수에 담는다
					selectedMenuLeft = (String)listSelectedlist.getSelectedValue();
			}
		});
		listSelectedlist.setModel(new AbstractListModel() {
			//이곳에 JList의 내용을 세팅하는 String[]이 있었지만
			//DefaultListModel을 내용으로 사용하면서 필요가 없어졌기에 삭제
			//기존에 있었던 코드들, 객체만 String[]이었던 values 에서 fModel(DefaultListModel)로 바뀌었다
			//JList의 크기와 요소들을 가져오는 메소드
			public int getSize() {
				return fModel.size();
			}
			public Object getElementAt(int index) {
				return fModel.get(index);
			}
		});
		listSelectedlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelectedlist.setFont(new Font("굴림", Font.PLAIN, 12));
		scrollPane_1.setViewportView(listSelectedlist);


		//음식추가버튼
		//중앙 JList에서 음식을 선택하고 이 버튼을 누르면 선택한 음식이 좌측 JList에 추가된다
		JButton btnAddMenu = new JButton("");
		btnAddMenu.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_Arrow4040_2.png")));
		btnAddMenu.addActionListener(new ActionListener() {
			//이벤트 발생
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnAddMenu) {
					//'메뉴'버튼에서 동작시
					if(jlistNum == 1) {
						//총 칼로리 출력
						//추가버튼을 눌러서 메뉴를 추가함과 동시에 좌측 JList하단부에 선택한 메뉴들의 총 칼로리를 출력
						try {
							//menuName : 추가한 메뉴의 번호(DB에서 검색할수있게 순수 번호 부분만 자른다)
							//ex) 1. 쌀밥(250g) => 1 로 되도록 자름
							menuName = selectedMenuCenter.substring(0, selectedMenuCenter.lastIndexOf("."));
							//총 칼로리 출력용 라벨 세팅
							//선택한 메뉴를 검색후 해당 메뉴의 칼로리를 라벨에 합산해서 세팅한다
							for(FoodDBVO f : arrSelectAll) {
								if(Integer.parseInt(menuName) == f.getNum()) {
									//선택한 메뉴의 칼로리를 총 칼로리에 합산
									totalKcal += f.getKcal();
									//세팅한 칼로리 라벨에 출력
									lblTotalKcal.setText("총 칼로리 : " + totalKcal + "Kcal");
								}
							}

							fModel.addElement(selectedMenuCenter);	//fModel : 좌측 JList용 DefaultListModel

						} catch (Exception e2) {
							//내용이 비어있으면 아무것도 하지 않는다
						}
					}
					//'추천식단'버튼에서 동작시
					//이 경우는 1줄로 되어있는 추천식단의 각 메뉴를 빼거나 할 수 있게 각각 좌측 JList에 세팅하는 작업을 한다
					if(jlistNum == 2) {
						//아무것도 누르지 않은 상태에서 눌렀을때 추가방지
						if(listMenulist.getSelectedValue() != null) {
							//아침,점심,저녁 확인부에서 에러시 todayMenu = null로 세팅됨
							//오류가 없다면 진행
							if(!(todayMenu == null)) {
								//추천식단 메뉴들을 각각 나누기
								String[] menus = todayMenu.substring(2).split(",");
								//맨 마지막엔 총 칼로리가 들어있기에 총 길이는 length-1 미만
								for(int i=0; i<(menus.length-1); i++) {
									//메뉴들을 각각 세팅
									fModel.addElement(menus[i]);
								}
								//하단부 라벨에 총 칼로리 세팅
								totalKcal += todayTotalKcal;
								lblTotalKcal.setText("총 칼로리 : " + totalKcal + "Kcal");
							}
						}
					}
					//'내 메뉴'버튼에서 동작시
					if(jlistNum == 3) {
						try {
							//'내 메뉴' 메뉴들을 각각 나누기
							String[] menus = selectedMenuCenter.substring(6).split(",");
							int num = (Integer.parseInt(selectedMenuCenter.substring(2,3)) -1) ;
							//맨 마지막엔 총 칼로리가 들어있기에 총 길이는 length-1미만
							for(int i=0; i<(menus.length-1); i++) {
								//메뉴들 각각 세팅
								fModel.addElement(menus[i]);
							}

							//하단부 라벨에 총 칼로리 세팅
							totalKcal += myMenuTotalKcal[num];
							lblTotalKcal.setText("총 칼로리 : " + totalKcal + "Kcal");

							
						} catch (NullPointerException | NumberFormatException e2) {
							//내용이 비어있으면 아무것도 하지 않는다
						}
					}
					
					//DefaultListModel에 값을 추가하고 JList에 붙인다
					listSelectedlist.setModel(fModel);	//listSelectedlist	: 좌측 JList객체명
					//JList갱신
					listSelectedlist.invalidate();
					listSelectedlist.repaint();
				}
			}
		});
		btnAddMenu.setBounds(226, 200, 52, 36);
		btnAddMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnAddMenu);

		
		//음식삭제버튼
		//좌측 JList에서 음식을 선택하고 이 버튼을 누르면 선택한 음식이 좌측 JList에서 삭제된다
		JButton btnDeleteMenu = new JButton("");
		btnDeleteMenu.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_Arrow4040.png")));
		btnDeleteMenu.addActionListener(new ActionListener() {
			//이벤트 발생
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnDeleteMenu) {
					//총 칼로리 출력
					//삭제버튼을 눌러서 메뉴를 삭제함과 동시에 좌측 JList하단부에 선택한 메뉴들의 총 칼로리를 출력
					try {
						//menuName : 추가한 메뉴의 번호(DB에서 검색할수있게 순수 번호 부분만 자른다)
						//ex) 1. 쌀밥(250g) => 1 로 되도록 자름
						menuName = selectedMenuLeft.substring(0, selectedMenuLeft.lastIndexOf("."));
						//총 칼로리 출력용 라벨 세팅
						//선택한 메뉴를 검색후 해당 메뉴의 칼로리를 라벨에 빼서 세팅한다
						for(FoodDBVO f : arrSelectAll) {
							if(Integer.parseInt(menuName) == f.getNum()) {
								//선택한 메뉴의 칼로리를 총 칼로리에서 뺌
								totalKcal -= f.getKcal();
								//세팅한 칼로리 라벨에 출력
								lblTotalKcal.setText("총 칼로리 : " + totalKcal + "Kcal");
							}
						}

						//DefaultListModel에 값을 제거하고 JList에 세팅한다
						fModel.removeElement(selectedMenuLeft);	//fModel	: 좌측 JList용 DefaultListModel
						listSelectedlist.setModel(fModel);
						//JList갱신
						listSelectedlist.invalidate();
						listSelectedlist.repaint();
					} catch (Exception e2) {
						//내용이 비어있으면 아무 행동도 하지 않는다
					}
				}
			}
		});
		btnDeleteMenu.setBounds(226, 280, 52, 36);
		btnDeleteMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnDeleteMenu);
		
		
		//초기화버튼
		//버튼을 클릭하면 이제까지 선택한 메뉴들을 전부 삭제한다
		JButton btnSelectedListClear = new JButton("");
		btnSelectedListClear.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/3_reset.png")));
		btnSelectedListClear.addActionListener(new ActionListener() {
			//이벤트발생
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnSelectedListClear) {
					//초기화 메시지
					JOptionPane.showMessageDialog(contentPane, "목록을 초기화 합니다");
					//DefaultListModel 초기화
					fModel.clear();
					//JList갱신
					listSelectedlist.invalidate();
					listSelectedlist.repaint();
					//총 칼로리 0으로 초기화
					totalKcal = 0;
					//세팅한 칼로리 라벨에 출력
					lblTotalKcal.setText("총 칼로리 : " + totalKcal + "Kcal");
				}
			}
		});
		btnSelectedListClear.setBounds(76, 435, 86, 36);
		btnSelectedListClear.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnSelectedListClear);

		
		//메뉴 검색용 텍스트필드
		txtFieldMenuSearch = new JTextField();
		txtFieldMenuSearch.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		txtFieldMenuSearch.setBounds(300, 23, 274, 40);
		contentPane.add(txtFieldMenuSearch);
		txtFieldMenuSearch.setColumns(10);
		txtFieldMenuSearch.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		
		
		//메뉴검색 버튼
		JButton btnMenuSearch = new JButton("");
		btnMenuSearch.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/3_macro.png")));
		btnMenuSearch.addActionListener(new ActionListener() {
			//이벤트발생
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource() == btnMenuSearch) {
					jlistNum = 1;
					//메뉴 존재 확인용 flag
					boolean flag = false;
					//검색창에서 입력한 메뉴명 저장
					menuName = txtFieldMenuSearch.getText();
					//모든 음식메뉴중에 검색창에 입력한 메뉴가 있는지 체크
					for(FoodDBVO f : arrSelectAll) {
						//검색 메뉴가 있다면
						if(menuName.equals(f.getName())) {
							//중앙 JList용 DefaultListModel 클리어
							dModel.clear();
							//검색한 메뉴 세팅(ex.  1. 쌀밥(250g)
							dModel.addElement(f.getNum()+". "+f.getName()
														+"("+f.getGram()+")");
							//JList갱신
							listMenulist.setModel(dModel);
							listMenulist.invalidate();
							listMenulist.repaint();
							//플래그 true로
							flag = true;
						}
					}
					//검색된 메뉴가 있다 => flag = true가 되기에 아래 if문에 들어가지 않음
					//검색된 메뉴가 없다 => flag = false 그대로 내려오기에 if문에 들어가 아래 메세지를 출력함
					if(!flag)	//메뉴가 없으면 출력
						JOptionPane.showMessageDialog(contentPane, "없는 메뉴입니다");
				}
			}
		});
		btnMenuSearch.setBackground(Color.WHITE);
		btnMenuSearch.setForeground(Color.WHITE);
		btnMenuSearch.setBounds(590, 23, 38, 38);
		btnMenuSearch.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnMenuSearch);

		
		//식단확정 버튼
		//버튼을 누르면 좌측 JList에 선택된 메뉴들이 식단으로 저장되며 그 정보들을 보냄과 동시에
		//3번 화면으로 간다
		JButton btnFinalMenu = new JButton("");
		btnFinalMenu.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_Mealcfm.png")));
		btnFinalMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//이벤트 발생
				if(arg0.getSource() == btnFinalMenu) {
					finalMenu = "";			// 최종식단
					finalMenuNum = "";		// 최종식단 메뉴들의 번호
					boolean check = false;
					//finalMenuNum에 선택된 메뉴들의 번호를 받아
					//finalMenu에 하나하나 추가한다
					for(int i=0; i<fModel.size(); i++) {						
						finalMenuNum = (String)fModel.get(i);	//메뉴의 번호 저장
						finalMenu = finalMenu					//최종식단에 메뉴번호들 추가
								+ finalMenuNum.substring(0, finalMenuNum.lastIndexOf("."))
								+ ",";				
					}
					
					//메뉴를 아무것도 선택하지 않은 상태에서 '식단확정'버튼 클릭시의 예외처리
					try {
						//최종메뉴체크
						if(!finalMenu.equals("")) {
							//아침,점심,저녁 체크
							//아침
							if(dTime == 1) {
								//멤버객체와 DB에 아침식단 세팅
								login.setBre(finalMenu);							//멤버객체
								check = mdao.update_bre(login.getId(), finalMenu);	//DB
								//점심
							}else if(dTime == 2) {
								//멤버객체와 DB에 아침식단 세팅
								login.setLun(finalMenu);							//멤버객체
								check = mdao.update_lun(login.getId(), finalMenu);	//DB
								//저녁
							}else if(dTime == 3) {
								//멤버객체와 DB에 아침식단 세팅
								login.setDin(finalMenu);							//멤버객체
								check = mdao.update_din(login.getId(), finalMenu);	//DB
							}
						}else {
							//최종메뉴가 오류 등으로  공백이 되면 강제 예외발생
							throw new NumberFormatException();
						}
						
						//3번화면에 값 넘기면서 활성화
						new MealSet(login, arrSelectAll).setVisible(true);						
						//현재화면 비활성화
						dispose();
						setVisible(false);
					} catch (NumberFormatException e) {
						//알림창 띄우고 현재 화면 그대로
						JOptionPane.showMessageDialog(contentPane, "메뉴를 선택해 주세요");
					}
				}
			}
		});
		btnFinalMenu.setFont(new Font("굴림", Font.BOLD, 14));
		btnFinalMenu.setBounds(721, 448, 86, 36);
		btnFinalMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnFinalMenu);

		
		JLabel lblMenuLabel = new JLabel("");
		lblMenuLabel.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_selectedmenu.png")));
		lblMenuLabel.setFont(new Font("굴림", Font.BOLD, 13));
		lblMenuLabel.setBounds(70, 95, 110, 30);
		contentPane.add(lblMenuLabel);

		
		//'내 메뉴' 추가 버튼
		//버튼을 누르면 좌측 JList에 선택된 메뉴들이 '내 메뉴'로 등록된다
		btnMylistAdd.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_addmeal.png")));
		btnMylistAdd.addActionListener(new ActionListener() {
			//이벤트발생
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnMylistAdd) {
					int menuInputNum = -1;			//비어있는 식단MyList위치
					String myMenuInput = null;		//'내 메뉴'로 넣을 최종 식단
					boolean updatecheck = false;	//DB적용 체크용
					//'내 메뉴'버튼을 눌렀을때 회원이 등록한 '내 메뉴'가 총 몇개인지 계산한다
					//그 식단수가 3개(전부등록)일때는 알림창
					if(myMenuCnt == 3)
						JOptionPane.showMessageDialog(contentPane, "저장 가능한 총 메뉴의 수는 3가지입니다");
					else {
						//예외처리
						try {
							//회원이 등록한 '내 메뉴'식단 중, 아직 등록하지 않은 식단의 위치 체크
							//'내 메뉴'저장시에도 사용
							for(int i=0; i<myMenuCheck.length; i++) {
								if(myMenuCheck[i] == 0) {
									menuInputNum = (i+1);
									break;	//1곳 이상 비어있더라도 맨 앞쪽만 찾으면 루프탈출(식단저장시 이 위치에 넣음)
								}
							}

							if(menuInputNum>=1 && menuInputNum<=3) {
								//저장할 식단 문자열 초기화
								myMenuInput = "";
								//임시 저장용 변수
								String fModelStr = "";
								//문자열 세팅(맨 마지막엔 ,를 붙이지 않기에 길이는 길이-1)
								for(int i=0; i<(fModel.size()-1); i++) {
									fModelStr = (String)fModel.get(i);
									myMenuInput += fModelStr.substring(0, fModelStr.lastIndexOf(".")) + ",";
								}
								//맨 마지막 음식번호 세팅
								fModelStr = (String)fModel.get(fModel.size()-1);
								myMenuInput += fModelStr.substring(0, fModelStr.lastIndexOf("."));
////////////////////////
								//DB Mylist에 식단등록
								updatecheck = mdao.insertMylist(menuInputNum, myMenuInput, login.getId());
								//DB작업 성공시 회원 객체에도 등록된 정보 세팅
								if(updatecheck) {
									if(menuInputNum == 1) {
										login.setMyList1(myMenuInput);;
									}else if(menuInputNum == 2) {
										login.setMyList2(myMenuInput);
									}else if(menuInputNum == 3) {
										login.setMyList3(myMenuInput);
									}

									JOptionPane.showMessageDialog(contentPane, "식단이 등록되었습니다");

								}else {
									JOptionPane.showMessageDialog(contentPane, "식단등록 실패");
								}
							}
						} catch (ArrayIndexOutOfBoundsException | NullPointerException e2) {
							JOptionPane.showMessageDialog(contentPane, "메뉴를 선택해 주세요");
						}
					}
				}
			}
		});
		btnMylistAdd.setBounds(695, 140, 138, 36);
		btnMylistAdd.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnMylistAdd);

		
		//'내 메뉴' 삭제 버튼
		//'내 메뉴'에서 해당 식단을 클릭하고 삭제 버튼을 누르면 메뉴삭제
		btnMylistDelete.setIcon(new ImageIcon(MenuSelect.class.getResource("/img/4_rmcmeal.png")));
		btnMylistDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//이벤트발생
				if(arg0.getSource() == btnMylistDelete) {
					try {
						//삭제할 메뉴번호 세팅
						int deleteMenuNum = Integer.parseInt(selectedMenuCenter.substring(2,3));
						//DB작업 체크용
						boolean deleteCheck = false;

						//DB작업
						deleteCheck = mdao.deleteMylist(deleteMenuNum, login.getId());
						//DB작업 성공시 회원 객체에도 삭제된 정보 세팅
						if(deleteCheck) {
							if(deleteMenuNum == 1) {
								login.setMyList1(null);
							}else if(deleteMenuNum == 2) {
								login.setMyList2(null);
							}else if(deleteMenuNum == 3) {
								login.setMyList3(null);
							}
							
							JOptionPane.showMessageDialog(contentPane, "식단이 삭제되었습니다");

						}else {
							JOptionPane.showMessageDialog(contentPane, "식단 삭제 실패");
						}
					} catch (NullPointerException e) {
						JOptionPane.showMessageDialog(contentPane, "메뉴를 선택해 주세요");
					}
				}	
			}
		});
		btnMylistDelete.setBounds(695, 190, 142, 36);
		btnMylistDelete.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(102, 0, 204), Color.LIGHT_GRAY, new Color(255, 255, 255), new Color(255, 255, 255)));
		contentPane.add(btnMylistDelete);
		
		
		//뒤로가기버튼
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