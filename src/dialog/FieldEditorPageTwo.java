package dialog;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.osgi.framework.Bundle;

import bookmark.actions.GoToLineAction;

/**
 * This class demonstrates field editors
 */

public class FieldEditorPageTwo extends PreferencePage {
	
	
	public static boolean isOk = false;
	
	private Map lineImage = new HashMap();
	 Image image = null;
	 private StyledText styledText;
	 private int y = 0;
	 private List list = null;
	 
	 private String name;
	 private int no;
	 
	 public IMarker marker;
	 
	 private int begin;
	 private int length;
	 
	 
	 public FieldEditorPageTwo(String name, int no, IMarker marker) {
		 isOk = false;
		 this.name = name;
		 this.no = no;
		 this.marker = marker;
	}


@Override
protected Control createContents(Composite parent) {
	
	parent.setLayout(new GridLayout(1, true));
	GridData gd = new GridData(GridData.FILL_BOTH);
	gd.heightHint = 300;
	gd.widthHint = 300;
	
	
	TextViewer viewer = new TextViewer(parent, SWT.PUSH | SWT.V_SCROLL | SWT.H_SCROLL);
	viewer.setEditable(false);
	
	String content = readFile(name);
	Document doc = new Document(content);
	viewer.setDocument(doc);
	viewer.getControl().setLayoutData(gd);
	
	TextPresentation style = new TextPresentation();
	Color read = new Color(null, 255, 0, 0);
	style.addStyleRange(new StyleRange(begin, length, read, null));
	viewer.changeTextPresentation(style, true);
	
	GoToLineAction action = new GoToLineAction(viewer);
	action.gotoLine(no - 1);
	
	/*final CompositeRuler ruler= new CompositeRuler();//创建一个标尺ruler
	final AnnotationRulerColumn annotationRulerColumn = new AnnotationRulerColumn(12);
    ruler.addDecorator(0, annotationRulerColumn);
    ruler.addDecorator(1, new LineNumberRulerColumn());//为这个标尺添加行号显示
    SourceViewer viewer = new SourceViewer(parent, ruler, SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.H_SCROLL);
	Document doc = new Document("1234\ndfasf");
	viewer.setDocument(doc);
	styledText = viewer.getTextWidget();
	//styledText.setSize(400, 400);
    
    
    final Control rulerControl = ruler.getControl();
    rulerControl.addMouseListener(new MouseAdapter() {
        @SuppressWarnings("unchecked")
        public void mouseDoubleClick(final MouseEvent e) {
            final GC gc= new GC(annotationRulerColumn.getControl());
            image =new Image(annotationRulerColumn.getControl().getDisplay(), "c:/sample.gif");
            Image image1 =new Image(annotationRulerColumn.getControl().getDisplay(), "c:/sample.gif");
            
            int line = ruler.getLineOfLastMouseButtonActivity();
            
            if(lineImage.get(line)!=null){
                int dely = styledText.getLineHeight()*line+1;
                gc.drawImage(image1, 1, dely);
                lineImage.remove(line);
            }else{
                lineImage.put(line, line);
            }
            
            Set set = lineImage.keySet();
            list = new LinkedList(set);
            
            for(int i=0;i<lineImage.size();i++){
                Integer linei = (Integer) list.get(i);
                y = styledText.getLineHeight()*linei+1;//根据当前鼠标点击行和输入框行高计算出绘画的y坐标                    
                gc.drawImage(image, 1, y);
            }
            image1.dispose();
        }
    });
    
    
    annotationRulerColumn.getControl().addPaintListener(new PaintListener(){
        public void paintControl(PaintEvent painte){
            image =new Image(annotationRulerColumn.getControl().getDisplay(), "c:/sample.gif");
            for(int i=0;i<lineImage.size();i++){
                Integer linei = (Integer) list.get(i);
                y = styledText.getLineHeight()*linei+1;//根据当前鼠标点击行和输入框行高计算出绘画的y坐标
                painte.gc.drawImage(image, 1, y);    
            }
            painte.gc.dispose();
        }
    });*/
    
    
	//viewer.getTextWidget().setCaretOffset(2);
	//viewer.getTextWidget().setFocus();  
	
	return parent;
}



public String readFile(String name){
	
	
	String path = marker.getResource().getLocation().toFile().getAbsolutePath();
	StringBuffer sb = new StringBuffer();
	BufferedReader reader = null;
	try {
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		String line = reader.readLine();
		int index = 1;
		int count = 0;
		while(line != null){
			sb.append(line);
			if(index == no){
				length = line.length();
				begin = count;
			}
			
			sb.append("\n");
			count = sb.length();
			line = reader.readLine();
			index++;
		}
		
	} catch (Exception e) {
		MessageDialog.openInformation(getShell().getShell(), "info", e.toString());
	}finally{
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	return sb.toString();
}


@Override
public boolean performOk() {
	isOk = true;
	return super.performOk();
}



}