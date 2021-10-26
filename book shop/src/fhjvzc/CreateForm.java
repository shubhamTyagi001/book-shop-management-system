package fhjvzc;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CreateForm {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateForm window = new CreateForm();
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
	public CreateForm() {
		initialize();
		  Connect();
		  table_load();
	}
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	
	
	public void Connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/javacrud", "root","");
        }
        catch (ClassNotFoundException ex)
        {
          ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
 
    }
	
	 public void table_load()
	    {
	     try
	     {
	    pst = con.prepareStatement("select * from book");
	    rs = pst.executeQuery();
	    table.setModel(DbUtils.resultSetToTableModel(rs));
	}
	     catch (SQLException e)
	     {
	     e.printStackTrace();
	  }
	    }
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 817, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 812, 467);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Name");
		lblNewLabel.setBounds(10, 84, 66, 20);
		panel.add(lblNewLabel);
		
		JLabel lblEdition = new JLabel("Edition");
		lblEdition.setBounds(10, 115, 66, 20);
		panel.add(lblEdition);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(10, 146, 66, 20);
		panel.add(lblPrice);
		
		txtbname = new JTextField();
		txtbname.setBounds(107, 84, 126, 20);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtedition = new JTextField();
		txtedition.setColumns(10);
		txtedition.setBounds(107, 115, 126, 20);
		panel.add(txtedition);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(107, 146, 126, 20);
		panel.add(txtprice);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bname,edition,price;
				
				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				try {
				pst = con.prepareStatement("insert into book(name,edition,price)values(?,?,?)");
				pst.setString(1, bname);
				pst.setString(2, edition);
				pst.setString(3, price);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
				table_load();
				          
				txtbname.setText("");
				txtedition.setText("");
				txtprice.setText("");
				txtbname.requestFocus();
				   }
				 
				catch (SQLException e1)
				        {
				e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(10, 212, 89, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Exit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(144, 212, 89, 23);
		panel.add(btnNewButton_1);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtbname.setText("");
				txtedition.setText("");
				txtprice.setText("");
				txtbname.requestFocus();
				
			}
		});
		btnClear.setBounds(10, 275, 89, 23);
		panel.add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(329, 52, 455, 313);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblBookId = new JLabel("Book ID");
		lblBookId.setBounds(10, 360, 66, 20);
		panel.add(lblBookId);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
			          
		            String id = txtbid.getText();
		 
		                pst = con.prepareStatement("select name,edition,price from book where id = ?");
		                pst.setString(1, id);
		                ResultSet rs = pst.executeQuery();
		 
		            if(rs.next()==true)
		            {
		              
		                String name = rs.getString(1);
		                String edition = rs.getString(2);
		                String price = rs.getString(3);
		                
		                txtbname.setText(name);
		                txtedition.setText(edition);
		                txtprice.setText(price);
		                
		                
		            }  
		            else
		            {
		             txtbname.setText("");
		             txtedition.setText("");
		                txtprice.setText("");
		                
		            }
		            
		 
		 
		        }
		catch (SQLException ex) {
		          
		        }
			}
		});
		txtbid.setColumns(10);
		txtbid.setBounds(73, 360, 160, 20);
		panel.add(txtbid);
		
		JButton btnNewButton_1_1 = new JButton("Update");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bname,edition,price,bid;
				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				bid  = txtbid.getText();
				try {
				pst = con.prepareStatement("update book set name= ?,edition=?,price=? where id =?");
				pst.setString(1, bname);
				            pst.setString(2, edition);
				            pst.setString(3, price);
				            pst.setString(4, bid);
				            pst.executeUpdate();
				            JOptionPane.showMessageDialog(null, "Record Update!!!!!");
				            table_load();
				          
				            txtbname.setText("");
				            txtedition.setText("");
				            txtprice.setText("");
				            txtbname.requestFocus();
				}
				 
				            catch (SQLException e1) {
				e1.printStackTrace();
				}
			}
		});
		btnNewButton_1_1.setBounds(352, 401, 89, 23);
		panel.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_2 = new JButton("Delete");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String bid;
				 bid  = txtbid.getText();
				 try {
				 pst = con.prepareStatement("delete from book where id =?");
				             pst.setString(1, bid);
				             pst.executeUpdate();
				             JOptionPane.showMessageDialog(null, "Record Delete!!!!!");
				             table_load();
				           
				             txtbname.setText("");
				             txtedition.setText("");
				             txtprice.setText("");
				             txtbname.requestFocus();
				 }
				  
				             catch (SQLException e1) {
				 e1.printStackTrace();
				 }
			}
		});
		btnNewButton_1_2.setBounds(477, 401, 89, 23);
		panel.add(btnNewButton_1_2);
		
		JLabel lblNewLabel_1 = new JLabel("BOOK Store System");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblNewLabel_1.setBounds(265, 11, 176, 14);
		panel.add(lblNewLabel_1);
	}
}
