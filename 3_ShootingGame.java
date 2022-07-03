package Ch12_3;
import java.awt.*;
import javax.swing.*;

import Ch10_1.MenuActionEventEx;

import java.awt.event.*;


public class ShootingGame extends JFrame{
	
	boolean IsBulletshoot=false;//�Ѿ��� �߻�Ǿ��°�?
	int score=0;
	int TargetX,BulletY=280;   //Ÿ���� X��ǥ Bullet�� Y��ǥ
	
	public ShootingGame() {
		setTitle("��� ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setFocusable(true);
		c.requestFocus();
		c.add(new StartPanel());
		c.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == '\n') {	//����Ű�� ������ �Ѿ��� �߻��
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
		JMenu menu = new JMenu("�޴�");
		JMenuItem gamestart = new JMenuItem("���ӽ���");
		JMenuItem gameexit = new JMenuItem("��������");

		gamestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cmd = e.getActionCommand();
				if (cmd.equals("���ӽ���")) {
					BulletThread th =new BulletThread(c); //���ӽ����ϸ� �����尡 ���۵�
					th.start();
				}
			}
		});
		gameexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cmd = e.getActionCommand();
				if (cmd.equals("��������")) {
					int result = JOptionPane.showConfirmDialog(ShootingGame.this, "�����Ͻðڽ��ϱ�?", "Confirm",
							JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						System.exit(0); // ���̾�α� Yes ���ý� ���α׷� ����
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
	
	class GamingPanel extends JPanel {  //���ӽ����� ������ ȭ�鿡 ������ �г�
		ImageIcon Ta = new ImageIcon("images/chicken.jpg"); //Ÿ�� �̹���
		Image Target = Ta.getImage();
		int bulletX = 190, bulletY = 280, targetX = 0, targetY = 0;
		String s = "0"; 
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(IsBulletshoot == true) {// �Ѿ��� �߻�Ǹ� �Ѿ��� ��ġ ����
				BulletY=BulletY-5;
				this.bulletY=BulletY;
				if(BulletY==0) {       //�Ѿ��� ������ ���ư��� �Ѿ��� ������� ���ͽ�Ŵ
					IsBulletshoot=false;
					BulletY=280;
				}
			}
			g.fillRect(170, 290, 50, 50);  //��
			g.setColor(Color.red);
			g.fillRect(bulletX, bulletY, 10, 10); //�Ѿ���ġ 
			g.drawImage(Target, targetX, targetY, 50, 50, this); //Ÿ�� ��ġ
			g.setFont(new Font("Arial",Font.BOLD,30));
			g.drawString("Score:", 0, 300);  
			g.drawString(s, 110, 300);       //����
			
			if(bulletX>=targetX-25 && bulletX<=targetX+25 &&
					bulletY>=targetY-25 && bulletY<=targetY+25) {//Ÿ�ٿ� �Ѿ��� �����ϸ�
				score++;		//���ھ� +1��
				s=Integer.toString(score);
				g.drawString(s, 110, 300);//���ھ� �ٽñ׸�
				IsBulletshoot=false;      //�Ѿ��� ������� ���ͽ�Ŵ
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
		GamingPanel gp = new GamingPanel(); //���̹� �г��� ������ ���̱� ������ ����

		public void run() {
			while (true) {
				try {
					Thread.sleep(20);	//�����尡 ����Ǹ� 0.02�� �ִٰ� �����δ�

					this.targetX = targetX + 5;//Ÿ���� ��ġ+5
					this.bulletY = BulletY;
					TargetX = this.targetX;
					gp.getXY(targetX, bulletY, c); //���̹� �г� getXY�� ��ġ ��ȯ��Ų ���� ����
					if (targetX == 350) {          //Ÿ���� ������ ���ٸ� ��ǥ0���� �ʱ�ȭ
						this.targetX = 0;
						TargetX = this.targetX;
					}
					
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	class StartPanel extends JPanel { //�ܼ� ��� ���α׷� ����� �������� �г�
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
