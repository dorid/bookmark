package bookmark.actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.osgi.framework.Bundle;

import bookmark.Activator;

import dialog.*;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class ListAction extends BookmarkBaseAction {
	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public ListAction() {
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		
		
		/*Dialog dialog = new MyDialog(window.getShell(), getBookMark());
		dialog.open();*/
		
		 // Create the preference manager
	    PreferenceManager mgr = new PreferenceManager();

	    // Create the nodes
	    IMarker[] markders = getBookMark();
	    for(int i=0; i < markders.length; i++)
	    {
	    	String name = MarkerUtilities.getMessage(markders[i]);
	    	String no = MarkerUtilities.getLineNumber(markders[i]) + "";
	    	
	    	PreferenceNode node = new PreferenceNode(name + no);
	 	    FieldEditorPageTwo page = new FieldEditorPageTwo(name, MarkerUtilities.getLineNumber(markders[i]), markders[i]);
	 	    page.setTitle(name + " " + no);
	 	    
	 	    node.setPage(page);
	 	    mgr.addToRoot(node);
	    }

	    // Create the preferences dialog
	    PreferenceDialog dlg = new PreferenceDialog(null, mgr);
	    dlg.create();
	    dlg.getShell().setText("Author:Dori Ding    Mail:sghcel.ok@163.com");
		dlg.open();
		
		if(FieldEditorPageTwo.isOk){
			IWorkbench wb = PlatformUI.getWorkbench();
			IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
			IWorkbenchPage page = win.getActivePage();
			IMarker marker = ((FieldEditorPageTwo)dlg.getSelectedPage()).marker;
			try {
				IDE.openEditor(page, marker, true);
			} catch (Exception e) {
				MessageDialog.openInformation(window.getShell(), "info", e.toString());
			}
		}
	}
	
	public IMarker[] getBookMark(){
		try {
			
			IMarker[] ar = ResourcesPlugin.getWorkspace().getRoot().findMarkers("org.eclipse.core.resources.bookmark", true, 2);
			return ar;
		} catch (CoreException e) {
			e.printStackTrace();
			Bundle bundle = Platform.getBundle("org.eclipse.ui");
			ILog log = Platform.getLog(bundle);
			log.log(e.getStatus());

			Shell shell = getTextEditor().getSite().getShell();
			String title = "Error";
			String msg = "Can't set bookmark";
			ErrorDialog.openError(shell, title, msg, e.getStatus());
		}
		return null;
	}


	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}