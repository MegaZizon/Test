package Ch12_3;
import java.awt.*;
import javax.swing.*;

import Ch10_1.MenuActionEventEx;

import java.awt.event.*;


public class ShootingGame extends JFrame{
	
	boolean IsBulletshoot=false;//총알이 발사되었는가?
	int score=0;
	int TargetX,BulletY=280;   //타겟의 X좌표 Bullet의 Y좌표
	
	public ShootingGame() {
		setTitle("사격 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setFocusable(true);
		c.requestFocus();
		c.add(new StartPanel());
		c.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == '\n') {	//엔터키를 누르면 총알이 발사됨
					IsBulletshoot=true;
					
                }
			}
		});
		
		createMenu(c);
		setSize(400, 400);
        setVisible(true);
	}
	
	void createMenu(Container c) {
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("메뉴");
		JMenuItem gamestart = new JMenuItem("게임시작");
		JMenuItem gameexit = new JMenuItem("게임종료");

		gamestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cmd = e.getActionCommand();
				if (cmd.equals("게임시작")) {
					BulletThread th =new BulletThread(c); //게임시작하면 쓰레드가 시작됨
					th.start();
				}
			}
		});
		gameexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cmd = e.getActionCommand();
				if (cmd.equals("게임종료")) {
					int result = JOptionPane.showConfirmDialog(ShootingGame.this, "종료하시겠습니까?", "Confirm",
							JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						System.exit(0); // 다이얼로그 Yes 선택시 프로그램 종료
					} else {
					}
				}
			}
		});

		menu.add(gamestart); 
		menu.add(gameexit);
		mb.add(menu);
		setJMenuBar(mb); 
	}
	
	class GamingPanel extends JPanel {  //게임시작을 누르면 화면에 나오는 패널
		ImageIcon Ta = new ImageIcon("images/chicken.jpg"); //타겟 이미지
		Image Target = Ta.getImage();
		int bulletX = 190, bulletY = 280, targetX = 0, targetY = 0;
		String s = "0"; 
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(IsBulletshoot == true) {// 총알이 발사되면 총알의 위치 수정
				BulletY=BulletY-5;
				this.bulletY=BulletY;
				if(BulletY==0) {       //총알이 끝까지 날아가면 총알을 원래대로 복귀시킴
					IsBulletshoot=false;
					BulletY=280;
				}
			}
			g.fillRect(170, 290, 50, 50);  //총
			g.setColor(Color.red);
			g.fillRect(bulletX, bulletY, 10, 10); //총알위치 
			g.drawImage(Target, targetX, targetY, 50, 50, this); //타겟 위치
			g.setFont(new Font("Arial",Font.BOLD,30));
			g.drawString("Score:", 0, 300);  
			g.drawString(s, 110, 300);       //점수
			
			if(bulletX>=targetX-25 && bulletX<=targetX+25 &&
					bulletY>=targetY-25 && bulletY<=targetY+25) {//타겟에 총알이 명중하면
				score++;		//스코어 +1점
				s=Integer.toString(score);
				g.drawString(s, 110, 300);//스코어 다시그림
				IsBulletshoot=false;      //총알을 원래대로 복귀시킴
				BulletY=280;
				
			}
		}

		void getXY(int targetX,int bulletY, Container c) {
			this.targetX = targetX;
			this.bulletY = bulletY;
			
			c.removeAll();
			c.add(this);
			setVisible(true);
			repaint();
		}
	}

	class BulletThread extends Thread {
		Container c;
		BulletThread(Container c) {
			this.c = c;
		}

		int bulletX = 190, bulletY = 280, targetX = 0, targetY = 0;
		GamingPanel gp = new GamingPanel(); //게이밍 패널을 조작할 것이기 때문에 선언

		public void run() {
			while (true) {
				try {
					Thread.sleep(20);	//쓰레드가 실행되면 0.02초 있다가 움직인다

					this.targetX = targetX + 5;//타겟의 위치+5
					this.bulletY = BulletY;
					TargetX = this.targetX;
					gp.getXY(targetX, bulletY, c); //게이밍 패널 getXY에 위치 변환시킨 값들 전달
					if (targetX == 350) {          //타겟이 끝까지 갔다면 좌표0으로 초기화
						this.targetX = 0;
						TargetX = this.targetX;
					}
					
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	class StartPanel extends JPanel { //단순 출력 프로그램 실행시 보여지는 패널
		ImageIcon Ta = new ImageIcon("images/chicken.jpg");
		Image Target = Ta.getImage();

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.fillRect(170, 290, 50, 50);
			g.setColor(Color.red);
			g.fillRect(190, 280, 10, 10);
			g.drawImage(Target, 0, 0, 50, 50, this);
			g.setFont(new Font("Arial",Font.BOLD,30));
			g.drawString("Score:", 0, 300);
			g.drawString("0", 110, 300);
		}
	}

	public static void main(String[] args) {
		new ShootingGame();

	}

}
