package Janela;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import Servidor.Texto;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//import Janela.JanelaCliente;

public class Cliente extends javax.swing.JFrame implements Runnable{

	private JFrame frame;
	private JLabel lblMensagem;
	private JComboBox cbxSalas;
	private static JTextField txtNick;
	private static Socket conexao;
	private JTextField txtMensagem ;
	private ObjectOutputStream saida_dados;
	private ObjectInputStream entrada_dados;
	private Texto text;
	private JComboBox cbxDestinatarios;
	private JTextArea txaMensagens;
	private JTextField txtIp;
	private JTextField txtPorta;
	private JButton btnEnviar;
	private String[] listaSalas;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente window = new Cliente();
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
	public Cliente(){
		 initialize();		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = 
				new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) 
			{
				saida_dados.writeObject(new Texto("sair",null));
				saida_dados.close();
				System.exit(n);
			}
		});
		frame.setResizable(false);
		frame.setBounds(100, 100, 760, 369);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Ip servidor");
		panel.add(lblNewLabel);
		
		txtIp = new JTextField();
		panel.add(txtIp);
		txtIp.setColumns(10);
		
		JLabel lblNick = new JLabel("Nick");
		panel.add(lblNick);
		
		txtNick = new JTextField();
		txtNick.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(txtNick);
		txtNick.setColumns(10);
		
		Label lbSalas = new Label("Sala:");
		panel.add(lbSalas);
		
		cbxSalas = new JComboBox();
		cbxSalas.setModel(new DefaultComboBoxModel(new String[] {"", "Dinalva", "Claudio", "Lapa"}));
		panel.add(cbxSalas);
		cbxSalas.setMaximumRowCount(9);
		
		JButton btnConectar = new JButton("Conectar");
		panel.add(btnConectar);
		btnConectar.addActionListener(new ActionListener() 
		{
		public void actionPerformed(ActionEvent arg0) 
		{	
			txtIp.setText("localhost");
			
			if (txtIp.getText().equals("") || txtNick.getText().equals("") || cbxSalas.getSelectedItem().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Alerta", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			txaMensagens.setText("");//limpa as mensagens caso o usuário entre em outro servidor
			
			try {				
				conexao = new Socket(txtIp.getText().toString(),12345); //conectou com o servidor		
				saida_dados = new ObjectOutputStream(conexao.getOutputStream());
				entrada_dados = new ObjectInputStream(conexao.getInputStream());	
				txtMensagem.setEnabled(true);
				btnEnviar.setEnabled(true);				
				saida_dados.writeObject(new Texto("info_usuario",txtNick.getText().toString(),cbxSalas.getSelectedItem().toString(),null,null));
				
				Texto msgRecebida = (Texto)entrada_dados.readObject();
				
				if(msgRecebida.getTipo().equals("nicks"))
				{
					cbxDestinatarios.addItem("Todos");
					String[] nicks = (String[])msgRecebida.getComplemento1();
					for(int i = 0; i <= nicks.length-1; i++){
						cbxDestinatarios.addItem(nicks[i]);
					}
				}
				cbxSalas.setEnabled(false);
				txtIp.setEnabled(false);
				txtNick.setEnabled(false);
				btnConectar.setEnabled(false);
				cbxDestinatarios.setEnabled(true);			
				thread2.start();
			}
			
			catch(Exception erro)
			{
				JOptionPane.showMessageDialog(null, "Erro de conexão. Verifique o Ip/porta e o servidor.--"+erro.getMessage(),
						"Erro de conexão", JOptionPane.INFORMATION_MESSAGE);
				txtMensagem.setEnabled(false);
				cbxSalas.setEnabled(false);
				cbxDestinatarios.setEnabled(false);
				btnEnviar.setEnabled(false);
				erro.printStackTrace();
				return;
			}				
	
		}
		
	});	
		 
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblMensagem = new JLabel("Mensagem");
		panel_1.add(lblMensagem);
		
		txtMensagem = new JTextField();
		txtMensagem.setEnabled(false);
		panel_1.add(txtMensagem);
		txtMensagem.setColumns(10);
		
		JLabel lblRemetente = new JLabel("Destinatario");
		panel_1.add(lblRemetente);
		
		cbxDestinatarios = new JComboBox();
		cbxDestinatarios.setEnabled(false);
		cbxDestinatarios.setMaximumRowCount(9);
		panel_1.add(cbxDestinatarios);
		
		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try 
				{
					//validacoes					
					saida_dados.writeObject(new Texto("msg", txtNick.getText(),cbxDestinatarios.getSelectedItem().toString(),txtMensagem.getText().toString(),null));													
				}
				catch(Exception erro)
				{
					txaMensagens.append("Erro:"+erro.getMessage());	
				}
			}
		});
		btnEnviar.setEnabled(false);		
		panel_1.add(btnEnviar);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();  
		panel_2.add(panel_3, BorderLayout.EAST);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JScrollBar scrollBar = new JScrollBar();
		panel_3.add(scrollBar, BorderLayout.WEST);
		
		Panel panel_4 = new Panel();
		panel_3.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		txaMensagens = new JTextArea();
		txaMensagens.setBackground(SystemColor.window);
		txaMensagens.setEnabled(false);
		panel_2.add(txaMensagens, BorderLayout.CENTER);}

	Thread thread2 = new Thread () {
	public void run() {
		// TODO Auto-generated method stub
		try {
			for(;;) 
			{
				Texto recebido = (Texto)entrada_dados.readObject();						
				if(recebido.getTipo().equals("msg")) 
				{
					txaMensagens.append((String) recebido.getComplemento1() + " : " 
				    +(String) recebido.getComplemento2() + "\n") ;
				}
				else if(recebido.getTipo().equals("nicks"))
				{					
					String[] nicks = (String[])recebido.getComplemento1();
					cbxDestinatarios.removeAllItems();
					cbxDestinatarios.addItem("Todos");
					for(int i = 0; i <= nicks.length-1; i++)
					{
						cbxDestinatarios.addItem(nicks[i]);
					}
					
					txaMensagens.append("O usuário " + (String)recebido.getComplemento2() + " entrou na sala \n");
				}
			}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	};

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
		


	
