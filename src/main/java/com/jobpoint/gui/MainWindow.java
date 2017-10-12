package com.jobpoint.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import com.jobpoint.controller.ProductController;
import com.jobpoint.model.Product;


public class MainWindow implements ActionListener{
	
	public static JPanel footerPanel;
	public static JFrame frame;
	private JTable table;
	private JButton addButton, closeButton, removeButton;
	private boolean ALLOW_ROW_SELECTION = true;
	public static ProductTableModel model;
	
	public MainWindow() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame("Product");
		
		ProductController productController = new ProductController();
		List<Product> productList = productController.getAllProduct();
		 
		model = new ProductTableModel(productList);
		
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(800, 800));
		table.setFillsViewportHeight(true);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		if (ALLOW_ROW_SELECTION) { // true by default
            ListSelectionModel rowSM = table.getSelectionModel();
            rowSM.addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent e) {
					// TODO Auto-generated method stub
					if (e.getValueIsAdjusting()) return;

			        //int[] selection = table.getSelectedRows();
			        
			        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
			       
			        if (lsm.isSelectionEmpty()) {
			            System.out.println("No rows are selected.");
			        } else {
			            /*int selectedRow = lsm.getMinSelectionIndex();
			            int id = (Integer) model.getValueAt(selectedRow, 0); 
			            ProductController productController = new ProductController();
			            productController.editProduct(id, selectedRow);
			            table.getSelectionModel().clearSelection();*/
			     
			        }
					
				}
            	
            });
        } else {
            table.setRowSelectionAllowed(false);
        }
		
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getClickCount() == 2) {
					int[] selection = table.getSelectedRows();
					if(selection.length > 0) {
						if(selection.length == 1) {
							int indexRow = selection[0];
							int id = (Integer) model.getValueAt(indexRow, 0); 
							ProductController productController = new ProductController();
							productController.editProduct(id, indexRow);
							table.getSelectionModel().clearSelection();
						}
					}
				}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		//table.getSelectionModel().addListSelectionListener(this);
		
		TableColumnModel tableColumnModel = table.getColumnModel();
		tableColumnModel.removeColumn(tableColumnModel.getColumn(0));

		JPanel mainPanel = new JPanel(new BorderLayout());
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(table);
		mainPanel.add(scrollPane);
		
		footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		frame.getContentPane().add(footerPanel, BorderLayout.PAGE_END);
		
		addButton = new JButton("Add Product");
		addButton.addActionListener(this);
		footerPanel.add(addButton);
		
		removeButton = new JButton("Remove");
		removeButton.addActionListener(this);
		footerPanel.add(removeButton);
		
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		footerPanel.add(closeButton);
		
		frame.setLocationRelativeTo(null);
		
		frame.setSize(600,600);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		int w = frame.getSize().width;
		int h = frame.getSize().height;
	    int x = (dimension.width-w)/2;
	    int y = (dimension.height-h)/2;
	    frame.setLocation(x, y);
	    
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource().equals(addButton)) {
			ProductController productController = new ProductController();
			productController.newProduct();
		}
		
		if(event.getSource().equals(removeButton)) {
			ProductController productController = new ProductController();
			int[] selection = table.getSelectedRows();
			
			if(selection.length > 0) {
				for(int i = selection.length - 1 ; i >= 0; i--) {
					
					int id = (int) model.getValueAt(selection[i], 0); 

					if(productController.deleteProduct(id, selection[i])) {
						JOptionPane.showMessageDialog(null, "Row/s successfully deleted!");
					}
				}		
			}
		}
		
		if(event.getSource().equals(closeButton)) {
			System.out.println("Cancel");
			frame.dispose();
		}
		
	}

}
