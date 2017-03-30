package com.kt.hiot.homemanager.kafka.simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.kt.hiot.homemanager.kafka.console.KafkaConsumer;
import com.kt.hiot.homemanager.kafka.console.KafkaDevSetupManager;
import com.kt.hiot.homemanager.kafka.console.KafkaProducer;
import com.kt.hiot.homemanager.kafka.console.KafkaProducerImpl;
import com.kt.hiot.homemanager.kafka.type.KafkaMsgType;
import com.kt.smcp.gw.ca.domn.cnvy.CnvyRow;
import com.kt.smcp.gw.ca.domn.cnvy.ItgCnvyData;
import com.kt.smcp.gw.ca.domn.cnvy.SpotDevCnvyData;
import com.kt.smcp.gw.ca.domn.devmgt.frmwr.FrmwrUdateNtfy;
import com.kt.smcp.gw.ca.domn.qry.LastValRetv;
import com.kt.smcp.gw.ca.domn.row.BinData;
import com.kt.smcp.gw.ca.domn.row.BinSetupData;
import com.kt.smcp.gw.ca.domn.row.CmdData;
import com.kt.smcp.gw.ca.domn.row.ContlData;
import com.kt.smcp.gw.ca.domn.row.GenlSetupData;
import com.kt.smcp.gw.ca.domn.spotdev.SpotDevKey;
import com.kt.smcp.gw.ca.domn.spotdev.SpotDevRetv;

public class Display {

	public static final File LOG_SET_FILE = new File(System.getProperty("user.home")+"/simulator/log_set.xml");
	public static final File PROP_SET_FILE = new File(System.getProperty("user.home")+"/simulator/prop_set2.xml");
	public static Log logger = LogFactory.getLog(Display.class);
	private transient JDesktopPane _desktop = new JDesktopPane();
	private File _currentDir = new File(System.getProperty("user.home")+"/simulator/log4j/");
	public KafkaDevSetupManager manager = new KafkaDevSetupManager();
	public Gson gson = new Gson();
	public byte doorLockUserId = 0x01;
	
	private JFrame frame;
	private JTextField txtZookeeperConnect1;
	private JTextField txtConsumerTopic1;
	private JTextField txtBroker;
	private JTextField txtProducerTopic;
		
	KafkaConsumer consumer1;
	KafkaConsumer consumer2;
	KafkaProducer producer;
	
	private JTextField txtSvcTgtSeq;
	private JTextField txtSpotDevSeq;
	private JTextField txtGroupId1;
	private JTextField txtECServerId;
	private JTextField txtZookeeperConnect2;
	private JTextField txtConsumerTopic2;
	private JTextField txtGroupId2;
	private JTextField txtDeviceId;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Display window = new Display();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Display() {
		initialize();
        this.readConfig();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 575, 591);
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JInternalFrame logPanel = new JInternalFrame("LogFrame");
		tabbedPane.addTab("로그보기", null, logPanel, null);
		logPanel.setVisible(true);
		logPanel.getContentPane().add(_desktop);
		
		JPanel consumerPanel = new JPanel();
		tabbedPane.addTab("컨슈머", null, consumerPanel, null);
		GridBagLayout gbl_consumerPanel = new GridBagLayout();
		gbl_consumerPanel.columnWidths = new int[]{0, 0, 0};
		gbl_consumerPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_consumerPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_consumerPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		consumerPanel.setLayout(gbl_consumerPanel);
		
		JLabel lblNewLabel = new JLabel("Zookeeper");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		consumerPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		txtZookeeperConnect1 = new JTextField();
		txtZookeeperConnect1.setText("v-hms02:9091");
		GridBagConstraints gbc_txtZookeeperConnect1 = new GridBagConstraints();
		gbc_txtZookeeperConnect1.insets = new Insets(0, 0, 5, 0);
		gbc_txtZookeeperConnect1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtZookeeperConnect1.gridx = 1;
		gbc_txtZookeeperConnect1.gridy = 0;
		consumerPanel.add(txtZookeeperConnect1, gbc_txtZookeeperConnect1);
		txtZookeeperConnect1.setColumns(10);
		
		JLabel lblTopic = new JLabel("Topic");
		GridBagConstraints gbc_lblTopic = new GridBagConstraints();
		gbc_lblTopic.anchor = GridBagConstraints.EAST;
		gbc_lblTopic.insets = new Insets(0, 0, 5, 5);
		gbc_lblTopic.gridx = 0;
		gbc_lblTopic.gridy = 1;
		consumerPanel.add(lblTopic, gbc_lblTopic);
		
		txtConsumerTopic1 = new JTextField();
		txtConsumerTopic1.setText("EC_SND_ITG_001HIT003");
		GridBagConstraints gbc_txtConsumerTopic1 = new GridBagConstraints();
		gbc_txtConsumerTopic1.insets = new Insets(0, 0, 5, 0);
		gbc_txtConsumerTopic1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtConsumerTopic1.gridx = 1;
		gbc_txtConsumerTopic1.gridy = 1;
		consumerPanel.add(txtConsumerTopic1, gbc_txtConsumerTopic1);
		txtConsumerTopic1.setColumns(10);
		
		JLabel lblGroupId = new JLabel("Group ID");
		GridBagConstraints gbc_lblGroupId = new GridBagConstraints();
		gbc_lblGroupId.anchor = GridBagConstraints.EAST;
		gbc_lblGroupId.insets = new Insets(0, 0, 5, 5);
		gbc_lblGroupId.gridx = 0;
		gbc_lblGroupId.gridy = 2;
		consumerPanel.add(lblGroupId, gbc_lblGroupId);
		
		txtGroupId1 = new JTextField();
		txtGroupId1.setText("EC_SND_ITG_001HIT003_SIM_GROUP_TEST_01");
		GridBagConstraints gbc_txtGroupId1 = new GridBagConstraints();
		gbc_txtGroupId1.insets = new Insets(0, 0, 5, 0);
		gbc_txtGroupId1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGroupId1.gridx = 1;
		gbc_txtGroupId1.gridy = 2;
		consumerPanel.add(txtGroupId1, gbc_txtGroupId1);
		txtGroupId1.setColumns(10);
		
		final JButton btnNewButton = new JButton("연결");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnNewButton.setEnabled(false);
				try{
					if(consumer1 == null){
						consumer1 = new KafkaConsumer(txtConsumerTopic1.getText(), txtZookeeperConnect1.getText(), txtGroupId1.getText(), manager);
						consumer1.start();
					}else{
						consumer1.interrupt();
						consumer1 = new KafkaConsumer(txtConsumerTopic1.getText(), txtZookeeperConnect1.getText(), txtGroupId1.getText(), manager);
						consumer1.start();
					}
					final Timer timer = new Timer(1000, new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent evt) {
			            	JOptionPane.showMessageDialog(frame, "연결 성공!");
			            	btnNewButton.setEnabled(true);
			            }
			        });
					timer.setRepeats(false);
					timer.start();					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(frame, "연결 실패!");
					btnNewButton.setEnabled(true);
				}

			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 3;
		consumerPanel.add(btnNewButton, gbc_btnNewButton);
		
		JLabel lblNewLabel_5 = new JLabel("Zookeeper");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 4;
		consumerPanel.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		txtZookeeperConnect2 = new JTextField();
		txtZookeeperConnect2.setText("v-hms02:9091");
		GridBagConstraints gbc_txtZookeeperConnect2 = new GridBagConstraints();
		gbc_txtZookeeperConnect2.insets = new Insets(0, 0, 5, 0);
		gbc_txtZookeeperConnect2.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtZookeeperConnect2.gridx = 1;
		gbc_txtZookeeperConnect2.gridy = 4;
		consumerPanel.add(txtZookeeperConnect2, gbc_txtZookeeperConnect2);
		txtZookeeperConnect2.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Topic");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 5;
		consumerPanel.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		txtConsumerTopic2 = new JTextField();
		txtConsumerTopic2.setText("COLLECT_EVENT_001HIT003");
		GridBagConstraints gbc_txtConsumerTopic2 = new GridBagConstraints();
		gbc_txtConsumerTopic2.insets = new Insets(0, 0, 5, 0);
		gbc_txtConsumerTopic2.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtConsumerTopic2.gridx = 1;
		gbc_txtConsumerTopic2.gridy = 5;
		consumerPanel.add(txtConsumerTopic2, gbc_txtConsumerTopic2);
		txtConsumerTopic2.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Group ID");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 0;
		gbc_lblNewLabel_7.gridy = 6;
		consumerPanel.add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		txtGroupId2 = new JTextField();
		txtGroupId2.setText("COLLECT_EVENT_001HIT003_SIM_GROUP_TEST_01");
		GridBagConstraints gbc_txtGroupId2 = new GridBagConstraints();
		gbc_txtGroupId2.insets = new Insets(0, 0, 5, 0);
		gbc_txtGroupId2.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGroupId2.gridx = 1;
		gbc_txtGroupId2.gridy = 6;
		consumerPanel.add(txtGroupId2, gbc_txtGroupId2);
		txtGroupId2.setColumns(10);
		
		final JButton btnNewButton_7 = new JButton("연결");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton_7.setEnabled(false);
				try{
					if(consumer2 == null){
						consumer2 = new KafkaConsumer(txtConsumerTopic2.getText(), txtZookeeperConnect2.getText(), txtGroupId2.getText(), manager);
						consumer2.start();
					}else{
						consumer2.interrupt();
						consumer2 = new KafkaConsumer(txtConsumerTopic2.getText(), txtZookeeperConnect1.getText(), txtGroupId2.getText(), manager);
						consumer2.start();
					}
					
					final Timer timer = new Timer(1000, new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent evt) {
			            	JOptionPane.showMessageDialog(frame, "연결 성공!");
			            	btnNewButton_7.setEnabled(true);
			            }
			        });
					timer.setRepeats(false);
					timer.start();		
				}catch(Exception e2){
	            	JOptionPane.showMessageDialog(frame, "연결 실패!");
	            	btnNewButton_7.setEnabled(true);
				}
			}
		});
		GridBagConstraints gbc_btnNewButton_7 = new GridBagConstraints();
		gbc_btnNewButton_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_7.gridwidth = 2;
		gbc_btnNewButton_7.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_7.gridx = 0;
		gbc_btnNewButton_7.gridy = 7;
		consumerPanel.add(btnNewButton_7, gbc_btnNewButton_7);
		

		
		
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        
        JMenuItem fileOpenItem = new JMenuItem("Log Open");
        fileOpenItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser chooser = new JFileChooser(_currentDir);
                int returnVal = chooser.showOpenDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = new File(chooser.getCurrentDirectory(), chooser.getSelectedFile().getName());
                    try {
                        startLogging(file, null, new Rectangle(300, 300));
                        _currentDir = file.getParentFile();
                    }
                    catch (IOException e) {
                        JOptionPane.showMessageDialog(frame, file.toString() + " cannot be tail logged.", "Logging not started", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        
        fileMenu.add(fileOpenItem);
        JMenuItem fileExitItem = new JMenuItem("exit");
        // Exits the application.
        fileExitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                exit();
            }
        });        
        fileMenu.add(fileExitItem);
        
        JPanel producerPanel = new JPanel();
        tabbedPane.addTab("프로듀서", null, producerPanel, null);
        GridBagLayout gbl_producerPanel = new GridBagLayout();
        gbl_producerPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_producerPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_producerPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_producerPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        producerPanel.setLayout(gbl_producerPanel);
        
        JLabel lblNewLabel_1 = new JLabel("Broker");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 0;
        producerPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
        
        txtBroker = new JTextField();
        txtBroker.setText("211.42.137.222:9092");
        GridBagConstraints gbc_txtBroker = new GridBagConstraints();
        gbc_txtBroker.gridwidth = 3;
        gbc_txtBroker.insets = new Insets(0, 0, 5, 0);
        gbc_txtBroker.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtBroker.gridx = 1;
        gbc_txtBroker.gridy = 0;
        producerPanel.add(txtBroker, gbc_txtBroker);
        txtBroker.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("Topic");
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 0;
        gbc_lblNewLabel_2.gridy = 1;
        producerPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
        
        txtProducerTopic = new JTextField();
        txtProducerTopic.setText("API_SND_ITG_001HIT003");
        GridBagConstraints gbc_txtProducerTopic = new GridBagConstraints();
        gbc_txtProducerTopic.gridwidth = 3;
        gbc_txtProducerTopic.insets = new Insets(0, 0, 5, 0);
        gbc_txtProducerTopic.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtProducerTopic.gridx = 1;
        gbc_txtProducerTopic.gridy = 1;
        producerPanel.add(txtProducerTopic, gbc_txtProducerTopic);
        txtProducerTopic.setColumns(10);
        
        JButton btnNewButton_11 = new JButton("브로커 설정");
        btnNewButton_11.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
					producer = new KafkaProducerImpl(txtBroker.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
        	}
        });
        GridBagConstraints gbc_btnNewButton_11 = new GridBagConstraints();
        gbc_btnNewButton_11.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnNewButton_11.gridwidth = 4;
        gbc_btnNewButton_11.insets = new Insets(0, 0, 5, 0);
        gbc_btnNewButton_11.gridx = 0;
        gbc_btnNewButton_11.gridy = 2;
        producerPanel.add(btnNewButton_11, gbc_btnNewButton_11);
        
        JLabel lblNewLabel_3 = new JLabel("SvcTgtSeq");
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_3.gridx = 0;
        gbc_lblNewLabel_3.gridy = 3;
        producerPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
        
        txtSvcTgtSeq = new JTextField();
        txtSvcTgtSeq.setText("5000000001");
        GridBagConstraints gbc_txtSvcTgtSeq = new GridBagConstraints();
        gbc_txtSvcTgtSeq.insets = new Insets(0, 0, 5, 5);
        gbc_txtSvcTgtSeq.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtSvcTgtSeq.gridx = 1;
        gbc_txtSvcTgtSeq.gridy = 3;
        producerPanel.add(txtSvcTgtSeq, gbc_txtSvcTgtSeq);
        txtSvcTgtSeq.setColumns(10);
        
        JLabel lblNewLabel_4 = new JLabel("SpotDevSeq");
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_4.gridx = 2;
        gbc_lblNewLabel_4.gridy = 3;
        producerPanel.add(lblNewLabel_4, gbc_lblNewLabel_4);
        
        txtSpotDevSeq = new JTextField();
        txtSpotDevSeq.setText("2000000001");
        GridBagConstraints gbc_txtSpotDevSeq = new GridBagConstraints();
        gbc_txtSpotDevSeq.insets = new Insets(0, 0, 5, 0);
        gbc_txtSpotDevSeq.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtSpotDevSeq.gridx = 3;
        gbc_txtSpotDevSeq.gridy = 3;
        producerPanel.add(txtSpotDevSeq, gbc_txtSpotDevSeq);
        txtSpotDevSeq.setColumns(10);
        
        JLabel lblEcServerId = new JLabel("EC Server ID");
        GridBagConstraints gbc_lblEcServerId = new GridBagConstraints();
        gbc_lblEcServerId.anchor = GridBagConstraints.EAST;
        gbc_lblEcServerId.insets = new Insets(0, 0, 5, 5);
        gbc_lblEcServerId.gridx = 0;
        gbc_lblEcServerId.gridy = 4;
        producerPanel.add(lblEcServerId, gbc_lblEcServerId);
        
        txtECServerId = new JTextField();
        txtECServerId.setText("HM_EC_01");
        GridBagConstraints gbc_txtECServerId = new GridBagConstraints();
        gbc_txtECServerId.insets = new Insets(0, 0, 5, 5);
        gbc_txtECServerId.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtECServerId.gridx = 1;
        gbc_txtECServerId.gridy = 4;
        producerPanel.add(txtECServerId, gbc_txtECServerId);
        txtECServerId.setColumns(10);
        
        JLabel lblDeviceId = new JLabel("Device ID");
        GridBagConstraints gbc_lblDeviceId = new GridBagConstraints();
        gbc_lblDeviceId.anchor = GridBagConstraints.EAST;
        gbc_lblDeviceId.insets = new Insets(0, 0, 5, 5);
        gbc_lblDeviceId.gridx = 2;
        gbc_lblDeviceId.gridy = 4;
        producerPanel.add(lblDeviceId, gbc_lblDeviceId);
        
        txtDeviceId = new JTextField();
        txtDeviceId.setText("iothub");
        GridBagConstraints gbc_txtDeviceId = new GridBagConstraints();
        gbc_txtDeviceId.insets = new Insets(0, 0, 5, 0);
        gbc_txtDeviceId.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtDeviceId.gridx = 3;
        gbc_txtDeviceId.gridy = 4;
        producerPanel.add(txtDeviceId, gbc_txtDeviceId);
        txtDeviceId.setColumns(10);
        
        JLabel lblNewLabel_9 = new JLabel("============================================================");
        GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
        gbc_lblNewLabel_9.gridwidth = 4;
        gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel_9.gridx = 0;
        gbc_lblNewLabel_9.gridy = 5;
        producerPanel.add(lblNewLabel_9, gbc_lblNewLabel_9);
        
        JLabel lblNewLabel_8 = new JLabel("");
        GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
        gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_8.gridx = 0;
        gbc_lblNewLabel_8.gridy = 6;
        producerPanel.add(lblNewLabel_8, gbc_lblNewLabel_8);
        
        final JComboBox<Object> comboBox = new JComboBox<Object>();
        comboBox.setBackground(new Color(102, 205, 170));
        comboBox.setMaximumRowCount(20);
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"GW z-wave inclusion 요청(S/W Key)", "GW z-wave exclusion 요청(S/W Key)", "GW 펌웨어 업그레이드 통보", "GW repair 요청", "GW 리부팅 수행", "GW 특정 end-device 제거", "GW IoT 단말 연결상태  조회", "도어락 삭제", "도어락 사용자 PW 추가", "도어락 사용자 PW 변경", "도어락 사용자 개별 삭제", "도어락 사용자 전체 삭제", "도어락 원격 제어(open만됨)", "도어락 방범 모드 설정/해제", "도어락 상태 확인", "Gas valve 제어(끌수만 있음)", "Gas valve 상태 확인"}));
        
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.gridwidth = 4;
        gbc_comboBox.insets = new Insets(0, 0, 5, 0);
        gbc_comboBox.gridx = 0;
        gbc_comboBox.gridy = 7;
        producerPanel.add(comboBox, gbc_comboBox);
        
        JButton btnNewButton_12 = new JButton("카프카 전송");
        btnNewButton_12.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int selectedItem = comboBox.getSelectedIndex();
        		String transacId = null;
        		String strPw = null;
        		String inputUserSeq = null;
        		String firmwrVer = null;
        		
        		byte[] command;
        		int idx = 0;
        		int userSeq = 0;
        		
        		BinData binData = new BinData();
        		CmdData cmdData1 = new CmdData();
        		CmdData cmdData2 = new CmdData();
        		BinSetupData binSetupData = new BinSetupData();
        		
        		switch(selectedItem){
        		case 0 : //"GW z-wave inclusion 요청(S/W Key)" 
            		transacId = manager.create("apiSimulatorSend", 1);
					binData.setSnsnTagCd("50000003");
					binData.setBinValTxn(new byte[0]);
					producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, 
							getDummyItgCnvyData(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), txtECServerId.getText(), binData));        			
					break;
        		case 1 : //"GW z-wave exclusion 요청(S/W Key)" 
            		transacId = manager.create("apiSimulatorSend", 1);
					binData.setSnsnTagCd("50000004");
					binData.setBinValTxn(new byte[0]);
					producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, 
							getDummyItgCnvyData(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), txtECServerId.getText(), binData));
					break;
        		case 2 : //"GW 펌웨어 업그레이드 통보"
//        			JOptionPane.showMessageDialog(frame, "EC 미구현.");
        			transacId = manager.create("apiSimulatorSend", 1);
        			firmwrVer = JOptionPane.showInputDialog(frame, "펌웨어 버전을 입력하세요.");
        			if(StringUtils.isEmpty(firmwrVer)){
        				return;
        			}
        			producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.FRMWR_UDATE_NTFY, 
        					getDummyFrmwrUdate(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), firmwrVer));
        			break;
        		case 3 : //"GW repair 요청" 
            		transacId = manager.create("apiSimulatorSend", 1);
					binData.setSnsnTagCd("50000005");
					binData.setBinValTxn(new byte[0]);
					producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, 
							getDummyItgCnvyData(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), txtECServerId.getText(), binData));
					break;
        		case 4 : //"GW 리부팅 수행" 
            		transacId = manager.create("apiSimulatorSend", 1);
					binData.setSnsnTagCd("50000007");
					binData.setBinValTxn(new byte[0]);
					producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, 
							getDummyItgCnvyData(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), txtECServerId.getText(), binData));
					break;
        		case 5 : //"GW 특정 end-device 제거" 
            		transacId = manager.create("apiSimulatorSend", 1);
					binData.setSnsnTagCd("50000007");
					binData.setBinValTxn(new byte[0]);
					producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, 
							getDummyItgCnvyData(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), txtECServerId.getText(), binData));
					break;
        		case 6 : //"GW IoT 단말 연결상태  조회" 
            		transacId = manager.create("apiSimulatorSend", 1);
            		cmdData1.setSnsnTagCd("31000008");
            		cmdData1.setCmdValTxn(new byte[0]);
					producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.INITA_DEV_RETV_EXTR, 
							getDummySpotDevRetv(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), txtECServerId.getText(), cmdData1));        		
					break;
        		case 7 : //"도어락 삭제" 
            		transacId = manager.create("apiSimulatorSend", 1);
					binData.setSnsnTagCd("50998504");
					binData.setBinValTxn(new byte[0]);
					producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, 
							getDummyItgCnvyData(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), txtECServerId.getText(), binData));
					break;
        		case 8 : //"도어락 사용자 PW 추가" 
            		transacId = manager.create("apiSimulatorSend", 1);
            		binSetupData.setSnsnTagCd("82996301");
					strPw = JOptionPane.showInputDialog(frame, "추가할 패스워드를 입력하세요.");
					if(StringUtils.isEmpty(strPw)){
						return;
					}
					command = new byte[strPw.length()+2];
					idx = 0;
					command[idx++] = (byte)doorLockUserId++; //User Identifier
					command[idx++] = (byte)0x01; //User ID Status
					for(byte pw : strToBinary(strPw)){
						command[idx++] = pw;
					}
					binSetupData.setSetupVal(command);
					producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, 
							getDummyItgCnvyData(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), txtECServerId.getText(), binSetupData));
        		
        			break;
        		case 9 : //"도어락 사용자 PW 변경"
            		transacId = manager.create("apiSimulatorSend", 1);
            		binSetupData.setSnsnTagCd("82996301");
					inputUserSeq = JOptionPane.showInputDialog(frame, "수정할 사용자ID의 Seq를 입력하세요.");
					if(StringUtils.isEmpty(inputUserSeq)){
						return;
					}
					strPw = JOptionPane.showInputDialog(frame, "수정할 패스워드를 입력하세요.");
					if(StringUtils.isEmpty(strPw)){
						return;
					}
					userSeq = Integer.parseInt(inputUserSeq);
					command = new byte[strPw.length()+2];
					idx = 0;
					command[idx++] = (byte)userSeq; //User Identifier
					command[idx++] = (byte)0x01; //User ID Status
					for(byte pw : strToBinary(strPw)){
						command[idx++] = pw;
					}
					binSetupData.setSetupVal(command);
					producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, 
							getDummyItgCnvyData(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), txtECServerId.getText(), binSetupData));
        			
        			break;
        		case 10 : //"도어락 사용자 개별 삭제" 
            		transacId = manager.create("apiSimulatorSend", 1);
					binSetupData.setSnsnTagCd("82996301");
					inputUserSeq = JOptionPane.showInputDialog(frame, "삭제할 사용자ID의 Seq를 입력하세요.");
					if(StringUtils.isEmpty(inputUserSeq)){
						return;
					}
					userSeq = Integer.parseInt(inputUserSeq);
					strPw = "0000";
					command = new byte[strPw.length()+2];
					idx = 0;
					command[idx++] = (byte)userSeq; //User Identifier
					command[idx++] = (byte)0x00; //User ID Status(Available - not set)
					for(byte pw : strToBinary(strPw)){
						command[idx++] = pw;
					}
					binSetupData.setSetupVal(command);
					producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, 
							getDummyItgCnvyData(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), txtECServerId.getText(), binSetupData));        			
        			break;
        		case 11 : //"도어락 사용자 전체 삭제" 
            		transacId = manager.create("apiSimulatorSend", 1);
					binSetupData.setSnsnTagCd("82996301");
					command = new byte[1];
					command[0] = (byte)0x00; //User Identifier

					binSetupData.setSetupVal(command);
					producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, 
							getDummyItgCnvyData(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), txtECServerId.getText(), binSetupData));        			
        			break;

        		case 12 : //"도어락 원격 제어" 
            		transacId = manager.create("apiSimulatorSend", 1);
					binData.setSnsnTagCd("50996201");
					command = new byte[1];
					command[0] = (byte)0x00;	//open				
					binData.setBinValTxn(command);
					producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, 
							getDummyItgCnvyData(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), txtECServerId.getText(), binData));
					break;
        		case 13 : //"도어락 방범 모드 설정/해제" 
        			JOptionPane.showMessageDialog(frame, "센싱태그 정의 필요");
        			break;
        		case 14 : //"도어락 상태 확인" 
					transacId = manager.create("apiSimulatorSend", 1);
					cmdData1.setSnsnTagCd("31996202");
					cmdData1.setCmdValTxn(new byte[0]);
					cmdData2.setSnsnTagCd("31998002");
					cmdData2.setCmdValTxn(new byte[0]);
					producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.QRY_LAST_VAL, 
							getDummyLastValRetv(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), txtECServerId.getText(), cmdData1, cmdData2));
        			break;
        		case 15 : //"Gas valve 제어(끌수만 있음)" 
            		transacId = manager.create("apiSimulatorSend", 1);
					cmdData1.setSnsnTagCd("31992501");
					cmdData1.setCmdValTxn(new java.math.BigInteger("00", 16).toByteArray());
					producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, 
							getDummyItgCnvyData(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), txtECServerId.getText(), cmdData1));
		        	break;
        		case 16 : //"Gas valve 상태 확인" 
					transacId = manager.create("apiSimulatorSend", 1);
					cmdData1.setSnsnTagCd("31992502");
					cmdData1.setCmdValTxn(new byte[0]);
					cmdData2.setSnsnTagCd("31998002");
					cmdData2.setCmdValTxn(new byte[0]);
					producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.QRY_LAST_VAL, 
					getDummyLastValRetv(transacId, Long.parseLong(txtSpotDevSeq.getText()), Long.parseLong(txtSvcTgtSeq.getText()), txtDeviceId.getText(), txtECServerId.getText(), cmdData1, cmdData2));
        			break;
        		default : JOptionPane.showMessageDialog(frame, "매치된 선택값 없음.("+selectedItem+") - "+comboBox.getSelectedItem());
        		}
        	}
        });
        GridBagConstraints gbc_btnNewButton_12 = new GridBagConstraints();
        gbc_btnNewButton_12.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnNewButton_12.gridwidth = 4;
        gbc_btnNewButton_12.insets = new Insets(0, 0, 5, 0);
        gbc_btnNewButton_12.gridx = 0;
        gbc_btnNewButton_12.gridy = 8;
        producerPanel.add(btnNewButton_12, gbc_btnNewButton_12);
        
        frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
        });
        
        btnNewButton_11.doClick();
             
	}

	
    private void startLogging(File file, ArrayList<HighlightRule> rules, Rectangle bounds) throws IOException {
        LogTail iFrame = new LogTail(frame, file, bounds);
        if (rules != null) {
            ArrayList<HighlightRule> logRules = iFrame.getRules();
            synchronized (logRules) {
                logRules.addAll(rules);
            }
        }
        _desktop.add(iFrame);
        iFrame.moveToFront();
        Thread t = new Thread(iFrame);
        t.start();
    }
    
    private void readConfig() {
        try {
            logger.info("Trying to read previous log configuration...");
            java.beans.XMLDecoder decoder = new java.beans.XMLDecoder(new BufferedInputStream(new FileInputStream(LOG_SET_FILE)));
            
            frame.setBounds((Rectangle)decoder.readObject());
            _desktop.setBounds((Rectangle)decoder.readObject());

            int frameCount = ((Integer)decoder.readObject()).intValue();
            for (int i = 0; i < frameCount; i++) {
                String path = (String)decoder.readObject();
                File file = new File(path);
                Rectangle bounds = (Rectangle)decoder.readObject();
                
                int ruleCount = ((Integer)decoder.readObject()).intValue();
                ArrayList<HighlightRule> rules = new ArrayList<HighlightRule>();
                for (int j = 0; j < ruleCount; j++) {
                    String name = (String)decoder.readObject();
                    String regexp = (String)decoder.readObject();
                    boolean underlined = ((Boolean)decoder.readObject()).booleanValue();
                    boolean bold = ((Boolean)decoder.readObject()).booleanValue();
                    boolean filtered = ((Boolean)decoder.readObject()).booleanValue();
                    boolean beep = ((Boolean)decoder.readObject()).booleanValue();
                    Color color = (Color)decoder.readObject();
                    HighlightRule rule = new HighlightRule(name, regexp, underlined, bold, filtered, beep, color);
                    rules.add(rule);
                }
                startLogging(file, rules, bounds);
            }
            
            
            decoder.close();
        }
        catch (Exception e) {
            logger.info("Could not find previous log configuration: assuming defaults.");
        }
        
        try {
            logger.info("Trying to read previous prop configuration...");
            java.beans.XMLDecoder decoder = new java.beans.XMLDecoder(new BufferedInputStream(new FileInputStream(PROP_SET_FILE)));
            
            txtZookeeperConnect1.setText((String) decoder.readObject());
            txtConsumerTopic1.setText((String) decoder.readObject());
            txtBroker.setText((String) decoder.readObject());
            txtProducerTopic.setText((String) decoder.readObject());
            txtSvcTgtSeq.setText((String) decoder.readObject());
            txtSpotDevSeq.setText((String) decoder.readObject());
            txtGroupId1.setText((String) decoder.readObject());
            txtECServerId.setText((String) decoder.readObject());
            txtZookeeperConnect2.setText((String) decoder.readObject());
            txtConsumerTopic2.setText((String) decoder.readObject());
            txtGroupId2.setText((String) decoder.readObject());
            txtDeviceId.setText((String) decoder.readObject());

            decoder.close();
        }
        catch (Exception e) {
            logger.info("Could not find previous prop configuration: assuming defaults.");
        }        
        
    }
    
    private byte[] strToBinary(String input){
    	StringBuffer buffer = new StringBuffer();
    	for(int i=0; i<input.length(); i++){
    		String hex = String.format("%02X", (int) input.charAt(i));
    		buffer.append(hex);
    	}
    	return new java.math.BigInteger(buffer.toString(), 16).toByteArray();
    }
    
    private void exit() {
    	
        frame.setVisible(false);
        if(consumer1 != null){
        	consumer1.interrupt();
        }
        if(consumer2 != null){
        	consumer2.interrupt();
        }

        // Save configuration!
        try {
            java.beans.XMLEncoder encoder = new java.beans.XMLEncoder(new BufferedOutputStream(new FileOutputStream(LOG_SET_FILE)));
            encoder.writeObject(frame.getBounds());
            encoder.writeObject(_desktop.getBounds());
            
            JInternalFrame[] frames = _desktop.getAllFrames();
            int frameCount = frames.length;
            encoder.writeObject(new Integer(frameCount));
            for (int n = 0; n < frameCount; n++) {
                LogTail frame = (LogTail)frames[n];
                encoder.writeObject(frame.getFile().getPath());
                encoder.writeObject(frame.getBounds());
                
                ArrayList<HighlightRule> rules = frame.getRules();
                synchronized (rules) {
                    encoder.writeObject(new Integer(rules.size()));
                    for (int i = 0; i < rules.size(); i++) {
                        HighlightRule rule = (HighlightRule)rules.get(i);
                        encoder.writeObject(rule.getName());
                        encoder.writeObject(rule.getRegexp());
                        encoder.writeObject(new Boolean(rule.getUnderlined()));
                        encoder.writeObject(new Boolean(rule.getBold()));
                        encoder.writeObject(new Boolean(rule.getFiltered()));
                        encoder.writeObject(new Boolean(rule.getBeep()));
                        encoder.writeObject(rule.getColor());
                    }
                }
            }

            
            encoder.flush();
            encoder.close();
            logger.info("Log Configuration saved.");
        }
        catch (Exception e) {
        	logger.info("Unable to save log configuration for next use: ", e);
        }
        
        // Save configuration!
        try {
            java.beans.XMLEncoder encoder = new java.beans.XMLEncoder(new BufferedOutputStream(new FileOutputStream(PROP_SET_FILE)));
            encoder.writeObject(txtZookeeperConnect1.getText());
            encoder.writeObject(txtConsumerTopic1.getText());
            encoder.writeObject(txtBroker.getText());
            encoder.writeObject(txtProducerTopic.getText());
            encoder.writeObject(txtSvcTgtSeq.getText());
            encoder.writeObject(txtSpotDevSeq.getText());
            encoder.writeObject(txtGroupId1.getText());
            encoder.writeObject(txtECServerId.getText());
            encoder.writeObject(txtZookeeperConnect2.getText());
            encoder.writeObject(txtConsumerTopic2.getText());
            encoder.writeObject(txtGroupId2.getText());
            encoder.writeObject(txtDeviceId.getText());
            

            encoder.flush();
            encoder.close();
            logger.info("Prop Configuration saved.");
        }
        catch (Exception e) {
        	logger.info("Unable to save prop configuration for next use: ", e);
        }
        
        // No need to explictly tidy anything else up as we're only reading files.
        System.exit(0);
    }	
    
    //제어/설정 525
	private String getDummyItgCnvyData(String transacId, long spotDevSeq, long svcTgtSeq, String spotDevId, String ecServerId, Object... array) {
		
		ItgCnvyData itgCnvyData = new ItgCnvyData();
		List<SpotDevCnvyData> spotDevCnvyDataList = new ArrayList<SpotDevCnvyData>();
		SpotDevCnvyData spotDevCnvyData = new SpotDevCnvyData();
		
		//EC server name
		itgCnvyData.setUnitSvcCd("001HIT003");
		itgCnvyData.setReqEcSrvrId(ecServerId);
		spotDevCnvyData.setTransacId(transacId+"_"+System.currentTimeMillis());
		List<CnvyRow> cnvyRowList = new ArrayList<CnvyRow>();
		CnvyRow cnvyRow = new CnvyRow();
		List<GenlSetupData> genlSetupDataList = new ArrayList<GenlSetupData>();
		GenlSetupData genlSetupData = new GenlSetupData();
		List<ContlData> contlDataList = new ArrayList<ContlData>();
		List<BinData> binDataList = new ArrayList<BinData>();
		List<CmdData> cmdDataList = new ArrayList<CmdData>();
		List<BinSetupData> binSetupDataList = new ArrayList<BinSetupData>();
		
		itgCnvyData.setTransacId(transacId);
		itgCnvyData.setSpotDevCnvyDatas(spotDevCnvyDataList);
		
		spotDevCnvyDataList.add(spotDevCnvyData);
		spotDevCnvyData.setCnvyRows(cnvyRowList);
		cnvyRowList.add(cnvyRow);
		cnvyRow.setGenlSetupDatas(genlSetupDataList);
		genlSetupDataList.add(genlSetupData);
		cnvyRow.setContlDatas(contlDataList);
		cnvyRow.setBinDatas(binDataList);
		cnvyRow.setCmdDatas(cmdDataList);
		cnvyRow.setBinSetupDatas(binSetupDataList);
		
		//고정
		spotDevCnvyData.setSvcTgtSeq(svcTgtSeq);
		spotDevCnvyData.setSpotDevSeq(spotDevSeq);
		spotDevCnvyData.setSpotDevId(spotDevId);
		
		
		for(Object o : array){
			if(o instanceof GenlSetupData){
				genlSetupDataList.add((GenlSetupData) o);
			}
			
			if(o instanceof ContlData){
				contlDataList.add((ContlData) o);
			}
			
			if(o instanceof BinData){
				binDataList.add((BinData) o);
			}
			
			if(o instanceof CmdData){
				cmdDataList.add((CmdData) o);
			}

			if(o instanceof BinSetupData){
				binSetupDataList.add((BinSetupData) o);
			}

		}

		
		
		return gson.toJson(itgCnvyData, ItgCnvyData.class);
		
	}		
	
	//외부 장치 조회 333
	private String getDummySpotDevRetv(String transacId, long spotDevSeq, long svcTgtSeq, String spotDevId, String ecServerId, CmdData... array){
		SpotDevRetv spotDevRetv = new SpotDevRetv();
		List<CmdData> cmdDataList = new ArrayList<CmdData>();
		List<SpotDevKey> spotDevKeyList = new ArrayList<SpotDevKey>(); 
		SpotDevKey spotDevKey = new SpotDevKey();
		spotDevKeyList.add(spotDevKey);

		//		spotDevKey.setGwCnctId(gwCnctId);	// openapi 적용시 gwCncId 필요
		spotDevKey.setSpotDevId(spotDevId);
		spotDevKey.setSpotDevSeq(spotDevSeq);
		spotDevKey.setSvcTgtSeq(svcTgtSeq);
		
		spotDevRetv.setTransacId(transacId);
		spotDevRetv.setReqEcSrvrId(ecServerId);
		spotDevRetv.setUnitSvcCd("001HIT003");
		spotDevRetv.setCmdDatas(cmdDataList);
		spotDevRetv.setInclSpotDevKeys(spotDevKeyList);
		
		for (CmdData cmdData : array) {
			cmdDataList.add(cmdData);
		}
		
		return gson.toJson(spotDevRetv, SpotDevRetv.class);
	}    
	
	//쿼리 711
	private String getDummyLastValRetv(String transacId, long spotDevSeq, long svcTgtSeq, String spotDevId, String ecServerId, CmdData... array){
		LastValRetv lastValRetv = new LastValRetv();
		List<CmdData> cmdDataList = new ArrayList<CmdData>();
		List<SpotDevKey> spotDevKeyList = new ArrayList<SpotDevKey>(); 
		SpotDevKey spotDevKey = new SpotDevKey();
		spotDevKeyList.add(spotDevKey);

		//		spotDevKey.setGwCnctId(gwCnctId);	// openapi 적용시 gwCncId 필요
		spotDevKey.setSpotDevId(spotDevId);
		spotDevKey.setSpotDevSeq(spotDevSeq);
		spotDevKey.setSvcTgtSeq(svcTgtSeq);
		
		lastValRetv.setTransacId(transacId);
		lastValRetv.setReqEcSrvrId(ecServerId);
		lastValRetv.setUnitSvcCd("001HIT003");
		lastValRetv.setCmdDatas(cmdDataList);
		lastValRetv.setInclSpotDevKeys(spotDevKeyList);
		
		for (CmdData cmdData : array) {
			cmdDataList.add(cmdData);
		}
		
		return gson.toJson(lastValRetv, LastValRetv.class);
	}    
	//쿼리 812
	private String getDummyFrmwrUdate(String transacId, long spotDevSeq, long svcTgtSeq, String spotDevId, String firwrVer){
		FrmwrUdateNtfy noti = new FrmwrUdateNtfy();
		noti.setUdateTransacId(transacId);
		noti.setSpotDevSeq(spotDevSeq);
		noti.setSvcTgtSeq(svcTgtSeq);
		noti.setSpotDevId(spotDevId);
		noti.setFrmwrVer(firwrVer);
		return gson.toJson(noti, FrmwrUdateNtfy.class);
	}    	
}




































