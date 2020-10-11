package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import dados.Atividade;
import dados.Projeto;
import negocios.Sistema;
import exceptions.SelectException;
import persistencia.ProjetoDAO;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;


public class Main {
	
	Sistema sistema = new Sistema();
	
	private JFrame SystemFrame;

	JTable tblProjetos = new JTable();
	JTable tblAtividades = new JTable();
	
	JPanel listarProjPanel = new JPanel();
	JPanel listarAtvPanel = new JPanel();
	JPanel cadastrarProj = new JPanel();
	JPanel cadastrarAtvPanel = new JPanel();
	JPanel removerProj = new JPanel();
	JPanel removerAtvPanel = new JPanel();
	JPanel alterarProj = new JPanel();
	JPanel alterarAtvPanel = new JPanel();
	JPanel Panel = new JPanel();
	
	JScrollPane atvScrollPane = new JScrollPane(tblAtividades);
	JScrollPane projScrollPane = new JScrollPane(tblProjetos);
	
	Button cadastrarProjeto = new Button("Cadastrar Projeto");
	Button alterarProjeto = new Button("Alterar Projeto");
	Button removerProjeto = new Button("Remover Projeto");
	Button cadastrarAtv = new Button("Cadastrar Atividade");
	Button alterarAtv = new Button("Alterar Atividade");
	Button removerAtv = new Button("Remover Atividade");
	
	private JTextField nomeProjField;
	private JTextField dataInicialField;
	private JTextField dataFinalField;
	private JTextField idField;
	private JTextField idAtvField;
	private JTextField nomeAtvField;
	private JTextField dataInicialAtvField;
	private JTextField dataFinalAtvField;
	private JTextField DescriçãoField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.SystemFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		initialize();
	}

	private void initialize() {
		
		SystemFrame = new JFrame();
		SystemFrame.setTitle("Agenda de Projetos");
		SystemFrame.getContentPane().setBackground(new Color(241, 240, 255));
		SystemFrame.getContentPane().setLayout(null);
		SystemFrame.setLocationRelativeTo(null);
		SystemFrame.setBounds(100, 100, 1000, 800);
		SystemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
//botao cadastrar projeto
		cadastrarProjeto.setForeground(new Color(0, 0, 0));
		cadastrarProjeto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				listarAtvPanel.setVisible(false);
				cadastrarAtvPanel.setVisible(false);
				removerProj.setVisible(false);
				removerAtvPanel.setVisible(false);
				alterarProj.setVisible(false);
				alterarAtvPanel.setVisible(false);
				
				atvScrollPane.setVisible(false);
				cadastrarAtv.setVisible(false);
				alterarAtv.setVisible(false);
				removerAtv.setVisible(false);
				
				cadastrarProj.setVisible(true);
				
				cadastrarProj.setBackground(new Color(195, 195, 229));
				cadastrarProj.setBounds(16, 131, 520, 600);
				SystemFrame.getContentPane().add(cadastrarProj);
				cadastrarProj.setLayout(null);				
				
				nomeProjField = new JTextField();
				nomeProjField.setText("Nome do projeto");
				nomeProjField.setBounds(10, 39, 500, 30);
				cadastrarProj.add(nomeProjField);
				nomeProjField.setColumns(10);
				nomeProjField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						nomeProjField.setText("");
					}
					@Override
					public void focusLost(FocusEvent e) {
						if (nomeProjField.getText().length()==0)
							nomeProjField.setText("Nome do projeto");
					}
				});
				
				dataInicialField = new JTextField();
				dataInicialField.setText("Data inicial do projeto (formato dd/mm/yyyy)");
				dataInicialField.setColumns(10);
				dataInicialField.setBounds(10, 98, 500, 30);
				cadastrarProj.add(dataInicialField);
				dataInicialField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						dataInicialField.setText("");
					}
					@Override
					public void focusLost(FocusEvent e) {
						if (dataInicialField.getText().length()==0)
							dataInicialField.setText("Data inicial do projeto (formato dd/mm/yyyy)");
					}
				});
				
				
				dataFinalField = new JTextField();
				dataFinalField.setText("Data final do projeto (formato dd/mm/yyyy)");
				dataFinalField.setColumns(10);
				dataFinalField.setBounds(10, 157, 500, 30);
				cadastrarProj.add(dataFinalField);
				dataFinalField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						dataFinalField.setText("");
					}
					@Override
					public void focusLost(FocusEvent e) {
						if (dataFinalField.getText().length()==0)
							dataFinalField.setText("Data final do projeto (formato dd/mm/yyyy)");
					}
				});	
				Button FinalizarCadProj = new Button("Finalizar Cadastro");
				cadastrarProj.add(FinalizarCadProj);
				FinalizarCadProj.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						Projeto projeto = new Projeto();
						String aux;
						
						projeto.setNomeProj(nomeProjField.getText());
						
						SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
						Date dataFormatada;
						try {
							aux = dataInicialField.getText();
							dataFormatada = dtf.parse(aux);
							projeto.getDataInicial().setTime(dataFormatada);
							
							aux = dataFinalField.getText();
							dataFormatada = dtf.parse(aux);
							projeto.getDataFinal().setTime(dataFormatada);							
						} catch (ParseException e1) {
							System.out.println("erro de formatação da data");
							return;
						}
											
						sistema.cadastrarProjeto(projeto);
						
						JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso !");
						cadastrarProj.setVisible(false);
						
						 attTblProj();	
					}
				});
				FinalizarCadProj.setBounds(364, 552, 146, 38);
				cadastrarProj.add(FinalizarCadProj);
			}
		});
		cadastrarProjeto.setBounds(597, 10, 115, 30);
		SystemFrame.getContentPane().add(cadastrarProjeto);
		
// botao alterar projeto
		alterarProjeto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				listarAtvPanel.setVisible(false);
				cadastrarProj.setVisible(false);
				cadastrarAtvPanel.setVisible(false);
				removerProj.setVisible(false);
				removerAtvPanel.setVisible(false);
				alterarAtvPanel.setVisible(false);
				
				atvScrollPane.setVisible(false);
				cadastrarAtv.setVisible(false);
				alterarAtv.setVisible(false);
				removerAtv.setVisible(false);
				
				alterarProj.setVisible(true);
				
				alterarProj.setBackground(new Color(195, 195, 229));
				alterarProj.setBounds(16, 131, 520, 600);
				SystemFrame.getContentPane().add(alterarProj);
				alterarProj.setLayout(null);
				
				idField = new JTextField();
				idField.setText("id do projeto");
				idField.setBounds(10, 50, 130, 30);
				alterarProj.add(idField);
				idField.setColumns(10);
				idField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						idField.setText("");
					}
					@Override
					public void focusLost(FocusEvent e) {
						if (idField.getText().length()==0)
							idField.setText("Digite o id do projeto");
					}
				});
				
				nomeProjField = new JTextField();
				nomeProjField.setText("Nome do projeto");
				nomeProjField.setBounds(10, 111, 500, 30);
				alterarProj.add(nomeProjField);
				nomeProjField.setColumns(10);
				nomeProjField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						nomeProjField.setText("");
					}
					@Override
					public void focusLost(FocusEvent e) {
						if (nomeProjField.getText().length()==0)
							nomeProjField.setText("Nome do projeto");
					}
				});
				
				dataInicialField = new JTextField();
				dataInicialField.setText("Data inicial do projeto (formato dd/mm/yyyy)");
				dataInicialField.setColumns(10);
				dataInicialField.setBounds(10, 174, 500, 30);
				alterarProj.add(dataInicialField);
				dataInicialField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						dataInicialField.setText("");
					}
					@Override
					public void focusLost(FocusEvent e) {
						if (dataInicialField.getText().length()==0)
							dataInicialField.setText("Data inicial do projeto (formato dd/mm/yyyy)");
					}
				});
				

				dataFinalField = new JTextField();
				dataFinalField.setText("Data final do projeto (formato dd/mm/yyyy)");
				dataFinalField.setColumns(10);
				dataFinalField.setBounds(10, 238, 500, 30);
				alterarProj.add(dataFinalField);
				dataFinalField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						dataFinalField.setText("");
					}
					@Override
					public void focusLost(FocusEvent e) {
						if (dataFinalField.getText().length()==0)
							dataFinalField.setText("Data final do projeto (formato dd/mm/yyyy)");
						
					}
				});	
				
				Button finalizarAltProj = new Button("Finalizar Altera\u00E7\u00E3o");
				finalizarAltProj.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						Projeto projeto = new Projeto();
						String aux;
						
						aux = idField.getText();
						 int id = Integer.valueOf(aux);
						projeto.setId(id);
						projeto.setNomeProj(nomeProjField.getText());
						
						SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
						Date dataFormatada;
						try {
							aux = dataInicialField.getText();
							dataFormatada = dtf.parse(aux);
							projeto.getDataInicial().setTime(dataFormatada);
							
							aux = dataFinalField.getText();
							dataFormatada = dtf.parse(aux);
							projeto.getDataFinal().setTime(dataFormatada);							
						} catch (ParseException e1) {
							System.out.println("erro de formatação da data");
							return;
						}
											
						sistema.alterarProjeto(projeto);
						
						JOptionPane.showMessageDialog(null, "Alteração efetuada com sucesso !");
						alterarProj.setVisible(false);
						
						 attTblProj();
					}
				});
				finalizarAltProj.setBounds(364, 552, 146, 38);
				alterarProj.add(finalizarAltProj);
			}
		});
		alterarProjeto.setBounds(718, 10, 115, 30);
		SystemFrame.getContentPane().add(alterarProjeto);
		
//botao remover projeto
		removerProjeto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				listarAtvPanel.setVisible(false);
				cadastrarProj.setVisible(false);
				cadastrarAtvPanel.setVisible(false);
				removerAtvPanel.setVisible(false);
				alterarProj.setVisible(false);
				alterarAtvPanel.setVisible(false);
				
				atvScrollPane.setVisible(false);
				cadastrarAtv.setVisible(false);
				alterarAtv.setVisible(false);
				removerAtv.setVisible(false);
				
				removerProj.setVisible(true);
				
				removerProj.setBackground(new Color(195, 195, 229));
				removerProj.setBounds(16, 131, 520, 600);
				SystemFrame.getContentPane().add(removerProj);
				removerProj.setLayout(null);

				idField = new JTextField();
				idField.setText("Digite o id do projeto");
				idField.setBounds(10, 50, 130, 30);
				removerProj.add(idField);
				idField.setColumns(10);
				idField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						idField.setText("");
					}
					@Override
					public void focusLost(FocusEvent e) {
						if (idField.getText().length()==0)
							idField.setText("Digite o id do projeto");
					}
				});	
				
				Button finalizarRemProj = new Button("Remover Projeto");
				finalizarRemProj.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						String aux;
						aux = idField.getText();
						 int id = Integer.valueOf(aux);
						sistema.removerProjeto(id);
						
						JOptionPane.showMessageDialog(null, "Remoção efetuada com sucesso !");
						removerProj.setVisible(false);
						
						 attTblProj();
					}
				});

				finalizarRemProj.setBounds(364, 552, 146, 38);
				removerProj.add(finalizarRemProj);
			}
		});
		removerProjeto.setBounds(839, 10, 115, 30);
		SystemFrame.getContentPane().add(removerProjeto);
				
		JLabel seusProjetos = new JLabel("Seus Projetos");
		seusProjetos.setForeground(new Color(68, 50, 102));
		seusProjetos.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		seusProjetos.setBounds(553, 98, 159, 22);
		SystemFrame.getContentPane().add(seusProjetos);
		
		listarProjPanel.setBackground(new Color(195, 195, 229));
		SystemFrame.getContentPane().add(listarProjPanel);
		listarProjPanel.setLayout(null);
		listarProjPanel.setBounds(546, 131, 428, 599);
		
		listarAtvPanel.setBackground(new Color(195, 195, 229));
		SystemFrame.getContentPane().add(listarAtvPanel);
		listarAtvPanel.setLayout(null);
		listarAtvPanel.setBounds(16, 131, 520, 600);
		
				tblProjetos.setBorder(new LineBorder(new Color(0, 0, 0)));
				tblProjetos.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"ID","Nome", "Data Inicial", "Data Final","% Completa", "Atrasado"
					}
				));
				tblProjetos.setBounds(64, 67, 546, 178);
				tblProjetos.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						
						cadastrarAtvPanel.setVisible(false);
						cadastrarProj.setVisible(false);
						removerProj.setVisible(false);
						removerAtvPanel.setVisible(false);
						alterarProj.setVisible(false);
						alterarAtvPanel.setVisible(false);
						
						atvScrollPane.setVisible(true);
						cadastrarAtv.setVisible(true);
						alterarAtv.setVisible(true);
						removerAtv.setVisible(true);
						
						
						listarAtvPanel.setVisible(true);
	
						int linha = tblProjetos.getSelectedRow();
						String aux = String.valueOf(tblProjetos.getValueAt(linha, 0));
						 int idProj = Integer.valueOf(aux);

//botao cadastrar atividade
						cadastrarAtv.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
			
								cadastrarAtvPanel.setBackground(new Color(195, 195, 229));
								cadastrarAtvPanel.setBounds(16, 131, 520, 600);
								SystemFrame.getContentPane().add(cadastrarAtvPanel);
								cadastrarAtvPanel.setLayout(null);
								cadastrarAtvPanel.setVisible(true);
								
								nomeAtvField = new JTextField();
								nomeAtvField.setText("Nome da atividade");
								nomeAtvField.setBounds(10, 46, 500, 30);
								cadastrarAtvPanel.add(nomeAtvField);
								nomeAtvField.setColumns(10);
								nomeAtvField.addFocusListener(new FocusAdapter() {
									@Override
									public void focusGained(FocusEvent e) {
										nomeAtvField.setText("");
									}
									@Override
									public void focusLost(FocusEvent e) {
										if (nomeAtvField.getText().length()==0)
											nomeAtvField.setText("Nome do atividade");
									}
								});
								
								dataInicialAtvField = new JTextField();
								dataInicialAtvField.setText("Data Inicial");
								dataInicialAtvField.setBounds(10, 90, 500, 30);
								cadastrarAtvPanel.add(dataInicialAtvField);
								dataInicialAtvField.setColumns(10);
								dataInicialAtvField.addFocusListener(new FocusAdapter() {
									@Override
									public void focusGained(FocusEvent e) {
										dataInicialAtvField.setText("");
									}
									@Override
									public void focusLost(FocusEvent e) {
										if (dataInicialAtvField.getText().length()==0)
											dataInicialAtvField.setText("Data Inicial");
									}
								});
								
								dataFinalAtvField = new JTextField();
								dataFinalAtvField.setText("Data Final");
								dataFinalAtvField.setBounds(10, 141, 500, 30);
								cadastrarAtvPanel.add(dataFinalAtvField);
								dataFinalAtvField.setColumns(10);
								dataFinalAtvField.addFocusListener(new FocusAdapter() {
									@Override
									public void focusGained(FocusEvent e) {
										dataFinalAtvField.setText("");
									}
									@Override
									public void focusLost(FocusEvent e) {
										if (dataFinalAtvField.getText().length()==0)
											dataFinalAtvField.setText("Data Final");
									}
								});
								
								DescriçãoField = new JTextField();
								DescriçãoField.setText("Descrição");
								DescriçãoField.setBounds(10, 192, 500, 30);
								cadastrarAtvPanel.add(DescriçãoField);
								DescriçãoField.setColumns(10);
								DescriçãoField.addFocusListener(new FocusAdapter() {
									@Override
									public void focusGained(FocusEvent e) {
										DescriçãoField.setText("");
									}
									@Override
									public void focusLost(FocusEvent e) {
										if (DescriçãoField.getText().length()==0)
											DescriçãoField.setText("Descrição");
									}
								});
								
								
								JCheckBox finalizadaCheckBox = new JCheckBox("Finalizada");
								finalizadaCheckBox.setBounds(10, 247, 97, 23);
								cadastrarAtvPanel.add(finalizadaCheckBox);
								finalizadaCheckBox.setVisible(true);
								
								
								JButton finalizarCadAtv = new JButton("Finalizar Cadastro");
								finalizarCadAtv.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseClicked(MouseEvent e) {
										Atividade atividade = new Atividade();
										String aux;
										
										atividade.setIdProj(idProj);
										atividade.setNomeAtv(nomeAtvField.getText());
										
										SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
										Date dataFormatada;
										try {
											aux = dataInicialAtvField.getText();
											dataFormatada = dtf.parse(aux);
											atividade.getDataInicialA().setTime(dataFormatada);
											
											aux = dataFinalAtvField.getText();
											dataFormatada = dtf.parse(aux);
											atividade.getDataFinalA().setTime(dataFormatada);							
										} catch (ParseException e1) {
											System.out.println("erro de formatação da data");
											return;
										}
										
										atividade.setDescricao(DescriçãoField.getText());
										
										if (finalizadaCheckBox.isSelected())
										       atividade.setFinalizada(true);
										   else 
										        atividade.setFinalizada(false);

										    
														
										sistema.cadastrarAtividade(atividade);
										cadastrarAtvPanel.setVisible(false);
										JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso !");
										
										 attTblAtv(idProj);
										 attTblProj();
										
									}
								});
								finalizarCadAtv.setVisible(true);
								finalizarCadAtv.setBounds(358, 247, 130, 23);
								cadastrarAtvPanel.add(finalizarCadAtv);
							}
						});
						cadastrarAtv.setBounds(10, 10, 105, 30);
						listarAtvPanel.add(cadastrarAtv);
						
//botao alterar atividade
						alterarAtv.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								
								cadastrarProj.setVisible(false);
								cadastrarAtvPanel.setVisible(false);
								removerProj.setVisible(false);
								removerAtvPanel.setVisible(false);
								alterarProj.setVisible(false);
								
								alterarAtvPanel.setVisible(true);
								
								alterarAtvPanel.setBackground(new Color(195, 195, 229));
								alterarAtvPanel.setBounds(16, 131, 520, 600);
								SystemFrame.getContentPane().add(alterarAtvPanel);
								alterarAtvPanel.setLayout(null);
								
								nomeAtvField = new JTextField();
								nomeAtvField.setText("Nome da atividade");
								nomeAtvField.setBounds(10, 46, 500, 30);
								alterarAtvPanel.add(nomeAtvField);
								nomeAtvField.setColumns(10);
								nomeAtvField.addFocusListener(new FocusAdapter() {
									@Override
									public void focusGained(FocusEvent e) {
										nomeAtvField.setText("");
									}
									@Override
									public void focusLost(FocusEvent e) {
										if (nomeAtvField.getText().length()==0)
											nomeAtvField.setText("Nome da atividade");
									}
								});
								
								
								dataInicialAtvField = new JTextField();
								dataInicialAtvField.setText("Data Inicial");
								dataInicialAtvField.setBounds(10, 87, 500, 30);
								alterarAtvPanel.add(dataInicialAtvField);
								dataInicialAtvField.setColumns(10);
								dataInicialAtvField.addFocusListener(new FocusAdapter() {
									@Override
									public void focusGained(FocusEvent e) {
										dataInicialAtvField.setText("");
									}
									@Override
									public void focusLost(FocusEvent e) {
										if (dataInicialAtvField.getText().length()==0)
											dataInicialAtvField.setText("Data Inicial");
									}
								});
								
								dataFinalAtvField = new JTextField();
								dataFinalAtvField.setText("Data Final");
								dataFinalAtvField.setBounds(10, 128, 500, 30);
								alterarAtvPanel.add(dataFinalAtvField);
								dataFinalAtvField.setColumns(10);
								dataFinalAtvField.addFocusListener(new FocusAdapter() {
									@Override
									public void focusGained(FocusEvent e) {
										dataFinalAtvField.setText("");
									}
									@Override
									public void focusLost(FocusEvent e) {
										if (dataFinalAtvField.getText().length()==0)
											dataFinalAtvField.setText("Data Final");
									}
								});
								
								DescriçãoField = new JTextField();
								DescriçãoField.setText("Descrição");
								DescriçãoField.setBounds(10, 169, 500, 30);
								alterarAtvPanel.add(DescriçãoField);
								DescriçãoField.setColumns(10);
								DescriçãoField.addFocusListener(new FocusAdapter() {
									@Override
									public void focusGained(FocusEvent e) {
										DescriçãoField.setText("");
									}
									@Override
									public void focusLost(FocusEvent e) {
										if (DescriçãoField.getText().length()==0)
											DescriçãoField.setText("Descrição");
									}
								});
								
								idAtvField = new JTextField();
								idAtvField.setText("ID da atividade");
								idAtvField.setBounds(10, 210, 97, 30);
								alterarAtvPanel.add(idAtvField);
								idAtvField.setColumns(10);
								idAtvField.addFocusListener(new FocusAdapter() {
									@Override
									public void focusGained(FocusEvent e) {
										idAtvField.setText("");
									}
									@Override
									public void focusLost(FocusEvent e) {
										if (idAtvField.getText().length()==0)
											idAtvField.setText("ID da atividade");
									}
								});
								
								JCheckBox finalizadaCheckBox = new JCheckBox("Finalizada");
								finalizadaCheckBox.setBounds(10, 247, 97, 23);
								alterarAtvPanel.add(finalizadaCheckBox);
								finalizadaCheckBox.setVisible(true);
								
								JButton finalizarAltAtv = new JButton("Finalizar Alteração");
								finalizarAltAtv.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseClicked(MouseEvent e) {
										Atividade atividade = new Atividade();
										String aux;
										
										atividade.setIdProj(idProj);
										
										aux = idAtvField.getText();
										 int id = Integer.valueOf(aux);
										atividade.setId(id);
										atividade.setNomeAtv(nomeAtvField.getText());
										
										SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
										Date dataFormatada;
										try {
											aux = dataInicialAtvField.getText();
											dataFormatada = dtf.parse(aux);
											atividade.getDataInicialA().setTime(dataFormatada);
											
											aux = dataFinalAtvField.getText();
											dataFormatada = dtf.parse(aux);
											atividade.getDataFinalA().setTime(dataFormatada);							
										} catch (ParseException e1) {
											System.out.println("erro de formatação da data");
											return;
										}
										
										atividade.setDescricao(DescriçãoField.getText());
										
										if (finalizadaCheckBox.isSelected())
										       atividade.setFinalizada(true);
										   else 
										        atividade.setFinalizada(false);

										    
														
										sistema.alterarAtividade(atividade);
										alterarAtvPanel.setVisible(false);
										JOptionPane.showMessageDialog(null, "Alteração efetuada com sucesso !");
										
										 attTblAtv(idProj);
										 attTblProj();
									}
								});
								finalizarAltAtv.setVisible(true);
								finalizarAltAtv.setBounds(358, 247, 130, 23);
								alterarAtvPanel.add(finalizarAltAtv);
							}
						});
						alterarAtv.setBounds(121, 10, 105, 30);
						listarAtvPanel.add(alterarAtv);
						
//botao remover atividade
						removerAtv.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								
								cadastrarProj.setVisible(false);
								cadastrarAtvPanel.setVisible(false);
								removerProj.setVisible(false);
								alterarProj.setVisible(false);
								alterarAtvPanel.setVisible(false);
								
								removerAtvPanel.setVisible(true);
								
								removerAtvPanel.setBackground(new Color(195, 195, 229));
								removerAtvPanel.setBounds(16, 131, 520, 600);
								SystemFrame.getContentPane().add(removerAtvPanel);
								removerAtvPanel.setLayout(null);	
								
								idAtvField = new JTextField();
								idAtvField.setText("ID da atividade");
								idAtvField.setBounds(10, 46, 105, 30);
								removerAtvPanel.add(idAtvField);
								idAtvField.setColumns(10);
								idAtvField.addFocusListener(new FocusAdapter() {
									@Override
									public void focusGained(FocusEvent e) {
										idAtvField.setText("");
									}
									@Override
									public void focusLost(FocusEvent e) {
										if (idAtvField.getText().length()==0)
											idAtvField.setText("ID da atividade");
									}
								});
								
								JButton finalizarRemAtv = new JButton("Remover Atividade");
								finalizarRemAtv.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseClicked(MouseEvent e) {
										String aux;
										aux = idAtvField.getText();
										 int id = Integer.valueOf(aux);

										sistema.removerAtividade(id);
										removerAtvPanel.setVisible(false);
										JOptionPane.showMessageDialog(null, "Remoção efetuada com sucesso !");
										
										 attTblAtv(idProj);
										 attTblProj();
									}
								});
								finalizarRemAtv.setBounds(358, 247, 130, 23);
								removerAtvPanel.add(finalizarRemAtv);
							}
						});
						removerAtv.setBounds(232, 10, 105, 30);
						listarAtvPanel.add(removerAtv);
						
						SystemFrame.getContentPane().add(listarAtvPanel);		
						
								tblAtividades.setBorder(new LineBorder(new Color(0, 0, 0)));
								tblAtividades.setModel(new DefaultTableModel(
									new Object[][] {
									},
									new String[] {
										"ID","Nome", "Data Inicial", "Data Final","Finalizada", "Descrição"
									}
								));
								tblAtividades.setBounds(10, 50, 546, 178);
								
								
								atvScrollPane.setBounds(0, 287, 520, 313);
								listarAtvPanel.add(atvScrollPane);
								atvScrollPane.setViewportView(tblAtividades);
								
								 attTblAtv(idProj);
				
					}
				});
				projScrollPane.setBounds(0, 0, 428, 599);
				listarProjPanel.add(projScrollPane);
				projScrollPane.setViewportView(tblProjetos);	
				
				attTblProj();

		
	}

	
	public void atualizaTabelaProj(ArrayList<Projeto> lista) {
		limpaTabela(tblProjetos);
		String linha[] = new String[] {"", "", "", "","",""};
		try {
			DefaultTableModel dadosProj = (DefaultTableModel) tblProjetos.getModel();
			SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
			
			int pos = -1;
			for(Projeto projeto : lista) {
				pos++;
				dadosProj.addRow(linha);
				dadosProj.setValueAt(projeto.getId(), pos, 0);
				dadosProj.setValueAt(projeto.getNomeProj(), pos, 1);
				String dataI = s.format(projeto.getDataInicial().getTime());
				dadosProj.setValueAt(dataI, pos, 2);
				String dataF = s.format(projeto.getDataFinal().getTime());
				dadosProj.setValueAt(dataF, pos, 3);
				dadosProj.setValueAt(projeto.getCompleto(), pos, 4);
				dadosProj.setValueAt(projeto.getAtrasado(), pos, 5);

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erros: " + e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void atualizaTabelaAtv(ArrayList<Atividade> lista) {
		limpaTabela(tblAtividades);
		String linha[] = new String[] {"", "", "", "","",""};
		try {
			DefaultTableModel dadosAtv = (DefaultTableModel) tblAtividades.getModel();
			SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
			
			int pos = -1;
			for(Atividade atividade : lista) {
				pos++;
				dadosAtv.addRow(linha);
				dadosAtv.setValueAt(atividade.getId(), pos, 0);
				dadosAtv.setValueAt(atividade.getNomeAtv(), pos, 1);
				String dataI = s.format(atividade.getDataInicialA().getTime());
				dadosAtv.setValueAt(dataI, pos, 2);
				String dataF = s.format(atividade.getDataFinalA().getTime());
				dadosAtv.setValueAt(dataF, pos, 3);
				dadosAtv.setValueAt(atividade.isFinalizada(), pos, 4);
				dadosAtv.setValueAt(atividade.getDescricao(), pos, 5);

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erros: " + e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void limpaTabela(JTable table) {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
	}
	
	public void attTblProj() {
		ArrayList<Projeto> lista = null;
		try {
			lista = ProjetoDAO.getInstance().selectAll();
		} catch (SelectException e1) {
			e1.printStackTrace();
		}
		atualizaTabelaProj(lista);	
	}
	
	public void attTblAtv(int id) {
		ArrayList<Atividade> lista = null;
			lista = sistema.buscarAtividades(id);

		atualizaTabelaAtv(lista);	
		}
}
