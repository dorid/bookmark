package bookmark.actions;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.ui.console.actions.TextViewerGotoLineAction;

public class GoToLineAction extends TextViewerGotoLineAction{

	public GoToLineAction(ITextViewer viewer) {
		super(viewer);
	}
	
	public void gotoLine(int line){
		super.gotoLine(line);
	}

}
