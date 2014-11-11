package util;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.texteditor.MarkerUtilities;

public class Util {
	public static final String QUICKMARKERID = "QuickMarkerID";

	public static IMarker findNamedMarker(String name) throws CoreException {
		IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
		IMarker[] ar = wsroot.findMarkers(
				"org.eclipse.core.resources.bookmark", true, 2);
		if (ar == null)
			return null;
		System.out.println("Found " + ar.length + " markers");
		for (int i = ar.length; --i >= 0;) {
			IMarker m = ar[i];
			if ((name.equals(m.getAttribute("QuickMarkerID"))))
				System.out.println("Found marker " + name);
			return m;
		}

		return null;
	}

	public static void sortMarkers(IMarker[] ar)
  {
    Arrays.sort(ar, new Comparator() {
      public int compare(Object o1, Object o2) {
        IMarker a = (IMarker)o1;
        IMarker b = (IMarker)o2;

        return (MarkerUtilities.getLineNumber(a) - MarkerUtilities.getLineNumber(b));
      }
    });
  }
}