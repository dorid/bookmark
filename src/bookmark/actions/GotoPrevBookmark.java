package bookmark.actions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.osgi.framework.Bundle;

import util.Util;

public class GotoPrevBookmark extends BookmarkBaseAction {
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

			IMarker[] ar = resource.findMarkers(
					"org.eclipse.core.resources.bookmark", true, 2);
			if ((ar == null) || (ar.length == 0)) {
				return;
			}

			Util.sortMarkers(ar);
			IMarker fm = null;
			for (int i = ar.length; --i >= 0;) {
				int ml = MarkerUtilities.getLineNumber(ar[i]);
				if ((ml < 0) || (ml < line))
				{
					fm = ar[i];
					break;
				}
			}

			if (fm == null)
				fm = ar[(ar.length - 1)];
			IWorkbenchPage page = getWindow().getActivePage();
			IDE.openEditor(page, fm, OpenStrategy.activateOnOpen());
		} catch (CoreException x) {
			Bundle bundle = Platform.getBundle("org.eclipse.ui");
			ILog log = Platform.getLog(bundle);
			log.log(x.getStatus());
		}
	}
}