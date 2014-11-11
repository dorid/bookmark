package bookmark.actions;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
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
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.osgi.framework.Bundle;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class ToggleBookMark extends BookmarkBaseAction {

	/**
	 * The constructor.
	 */
	public ToggleBookMark() {
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {

		try {
			ITextSelection selection = (ITextSelection) getTextEditor()
					.getSelectionProvider().getSelection();
			if (selection.isEmpty())
				return;

			int line = getSelectionLineNr() + 1;
			IResource resource = getResource();
			if (resource == null) {
				return;
			}
			IMarker[] ar = resource.findMarkers("org.eclipse.core.resources.bookmark", true, 2);
			if (ar != null)
		      {
		        boolean hasmarkers = false;
		        for (int i = ar.length; --i >= 0; ) {
		          if (MarkerUtilities.getLineNumber(ar[i]) == line) {
		            ar[i].delete();
		            hasmarkers = true;
		          }
		        }
		        if (hasmarkers) {
		          return;
		        }
		      }
			
			Map attributes = getInitialAttributes(resource.getFullPath().toString());
		    MarkerUtilities.createMarker(resource, attributes, "org.eclipse.core.resources.bookmark");
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
	}
}