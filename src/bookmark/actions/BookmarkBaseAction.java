package bookmark.actions;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorActionDelegate;
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

public class BookmarkBaseAction extends ActionDelegate
  implements IEditorActionDelegate, IWorkbenchWindowActionDelegate
{
  private IEditorPart m_editor;
  private IWorkbenchWindow m_window;

  public void setActiveEditor(IAction action, IEditorPart targetEditor)
  {
    this.m_editor = targetEditor;
  }

  public void init(IWorkbenchWindow window) {
    this.m_window = window;
  }

  public ITextEditor getTextEditor() {
    IWorkbench wb = PlatformUI.getWorkbench();
    IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
    
    IWorkbenchPage page = win.getActivePage();
    IEditorPart editor = page.getActiveEditor();
    return ((ITextEditor)editor);
  }

  public IResource getResource()
  {
    ITextEditor editor = getTextEditor();
    if (editor != null) {
      IEditorInput input = editor.getEditorInput();
      return ((IResource)input.getAdapter(IResource.class));
    }
    return null;
  }

  public IWorkbenchWindow getWindow() {
    return this.m_window;
  }

  protected int getSelectionLineNr()
  {
    ITextEditor editor = getTextEditor();
    IDocumentProvider provider = editor.getDocumentProvider();
    IDocument document = provider.getDocument(editor.getEditorInput());
    
    //editor.getSelectionProvider().setSelection(new TextSelection(3, 100));
    ITextSelection selection = (ITextSelection)getTextEditor().getSelectionProvider().getSelection();
    String text = selection.getText();
    //System.out.println(text.length());
    //System.out.println(selection.getOffset());
    if (selection.isEmpty())
      return -1;
    int start = selection.getOffset();
    
    int line = selection.getStartLine();
    if (line < 0)
      try {
        line = document.getLineOfOffset(start);
      } catch (BadLocationException x) {
        x.printStackTrace();
        return -1;
      }
    
    
    
    try {
    	int length = document.getLineLength(line);
    	int lineStart = document.getLineOffset(line);
        String lineStr = document.get(lineStart, length).trim();
        
        //鼠标在当前行的列数
        //System.out.println(document.getLineOffset(line));
        int current = start - 2 - document.getLineOffset(line);
        setSelection(lineStr, current, start, editor.getSelectionProvider());
        //System.out.println(lineStr);
        
		int lineLength = document.getLineLength(line);
		document.getNumberOfLines();
		//System.out.println(lineLength);
	} catch (BadLocationException e) {
		e.printStackTrace();
	}
    
    return line;
  }
  
  private void setSelection(String lineStr, int current, int index, ISelectionProvider provider){
	  String prefix = lineStr.substring(0, current);
	  String suffix = lineStr.substring(current);
	  
	  int preIndex = prefix.length() - prefix.lastIndexOf(".") - 1;
	  int sufIndex = suffix.indexOf(".") == -1? 0 : suffix.indexOf(".");
	  
	  int offset = index - preIndex;
	  int endset = index + sufIndex;
	  
	  provider.setSelection(new TextSelection(offset, endset-offset));
	  System.out.println(prefix);
	  System.out.println(suffix);
  }

  protected Map getInitialAttributes(String message) {
    Map attributes = new HashMap(11);
    getTextEditor();

    ITextSelection selection = (ITextSelection)getTextEditor().getSelectionProvider().getSelection();
    if (!(selection.isEmpty())) {
      int start = selection.getOffset();
      int length = selection.getLength();
      int line = getSelectionLineNr();

      MarkerUtilities.setMessage(attributes, message);
      MarkerUtilities.setLineNumber(attributes, line + 1);
      MarkerUtilities.setCharStart(attributes, start);
      MarkerUtilities.setCharEnd(attributes, start + length);
    }
    return attributes;
  }
}