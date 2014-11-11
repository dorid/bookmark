package dialog;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.MarkerUtilities;

public class MyDialog extends Dialog {

	private TableViewer viewer;
	
	private IMarker[] markders;
	
	public MyDialog(Shell parentShell, IMarker[] markers) {
		super(parentShell);
		this.markders = markers;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		
		//parent.setLayout(new GridLayout(2, false));
		
		getShell().setText("´ô´ô");
		Composite container = (Composite) super.createDialogArea(parent);
		
		
		
		/*viewer = new TableViewer(container, SWT.FULL_SELECTION);
		GridData data = new GridData(GridData.FILL_BOTH);
		viewer.getControl().setLayoutData(data);
		
	    Table table = viewer.getTable();
	    table.setLinesVisible(true);
	    table.setHeaderVisible(true);
	    TableColumn tableColumn = new TableColumn(table, SWT.LEFT);
        tableColumn.setText("Desc");
	    tableColumn.setWidth(100);
	    
	    TableColumn tableColumn2 = new TableColumn(table, SWT.LEFT);
	    tableColumn2.setText("Path");
	    tableColumn2.setWidth(300);
	    
	    TableColumn tableColumn3 = new TableColumn(table, SWT.LEFT);
	    tableColumn3.setText("Location");
	    tableColumn3.setWidth(100);
	    
	    for(int i=0; i < markders.length; i++)
	    {
	    	TableItem item = new TableItem(table, SWT.NONE);
	    	item.setText(0, "11");
		    item.setText(1, MarkerUtilities.getMessage(markders[i]));
		    item.setText(2, "line " + MarkerUtilities.getLineNumber(markders[i]));
	    }
	    
	    
	    
	    
	    viewer.addDoubleClickListener(new IDoubleClickListener() {
			
			public void doubleClick(DoubleClickEvent event) {
				// TODO Auto-generated method stub
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				
				IWorkbench wb = PlatformUI.getWorkbench();
				IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
				IWorkbenchPage page = win.getActivePage();
				int index = ((TableViewer)event.getSource()).getTable().getSelectionIndex();
				try {
					IDE.openEditor(page, markders[index], true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	    
	    viewer.setContentProvider(new ArrayContentProvider());*/
	    
	    return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(500, 700); 
	}

	@Override
	protected int getShellStyle() {
		return SWT.MAX |SWT.CLOSE | SWT.TITLE | SWT.RESIZE | SWT.APPLICATION_MODAL;
	}
	
	
	
	
}
