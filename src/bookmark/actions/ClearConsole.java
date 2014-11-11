package bookmark.actions;

import org.eclipse.jface.action.IAction;
import  org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.console.*;

public class ClearConsole implements IWorkbenchWindowActionDelegate {

	public void run(IAction action) {
		// TODO Auto-generated method stub
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = win.getActivePage();

		ConsoleView view = (ConsoleView) page.findView("org.eclipse.ui.console.ConsoleView");
		if (view != null) {
			TextConsole console = (TextConsole) view.getConsole();
			console.clearConsole();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

}
