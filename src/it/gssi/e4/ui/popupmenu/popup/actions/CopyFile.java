package it.gssi.e4.ui.popupmenu.popup.actions;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.Workbench;

public class CopyFile implements IObjectActionDelegate {

	private Shell shell;
	
	/**
	 * Constructor for Action1.
	 */
	public CopyFile() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		
		ISelectionService selectionService = 
		Workbench.getInstance().getActiveWorkbenchWindow().getSelectionService();
		
		ISelection selection = selectionService.getSelection();
		
		List<String> names=new ArrayList<String>();
		
		if(selection instanceof IStructuredSelection) {
		
			List<IStructuredSelection> selection_list=(List) (((IStructuredSelection) selection).toList());
		    Iterator iterator = selection_list.iterator();
			
		    
		    while(iterator.hasNext()){
		    Object element=iterator.next();
		    //System.err.println(element.getClass());
		    if (element instanceof IFile) {
		    
		    	IFile single=(IFile)element;
		    	
		    	IPath path_single = single.getLocation();
		    	File file_single = path_single.toFile();
		    	
		    	File target=new File(single.getLocation().toString()+".copy");
		    	
		    	try {
		    		
					this.copy(file_single, target);
					ResourcesPlugin.getWorkspace().getRoot().getProjects()[0].refreshLocal(IResource.DEPTH_INFINITE, null);
		    	} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}      
		    	names.add(single.getName());
		    	
		    } 
		    }
		    
		}	
		MessageDialog.openInformation(
			shell,
			"Popupmenu",
			"Copy of the files: "+names.toString()+"was executed.");
	}
	
	
	void copy(File source, File target) throws IOException {
		 
	      InputStream in = new FileInputStream(source);
	      OutputStream out = new FileOutputStream(target);
	   
	      // Copy the bits from instream to outstream
	      byte[] buf = new byte[1024];
	      int len;
	 
	      while ((len = in.read(buf)) > 0) {
	          out.write(buf, 0, len);
	      }
	 
	      in.close();
	      out.close();
	  }
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		
	}

}
