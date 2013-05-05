package by.dev.madhead.eclipse.uuid.handlers;

import java.util.UUID;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

public class GenerateUUIDHandler extends AbstractHandler {
	public GenerateUUIDHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		IWorkbenchPage page = window.getActivePage();
		IEditorPart part = page.getActiveEditor();
		ITextEditor editor = null;

		if (part instanceof ITextEditor) {
			editor = (ITextEditor) part;
		} else if (part instanceof MultiPageEditorPart) {
			MultiPageEditorPart multipageEditorPart = (MultiPageEditorPart) part;

			if (null != multipageEditorPart.getAdapter(ITextEditor.class)) {
				editor = (ITextEditor) multipageEditorPart
						.getAdapter(ITextEditor.class);
			}
		}

		if (null == editor) {
			return null;
		}

		IDocument doc = editor.getDocumentProvider().getDocument(
				editor.getEditorInput());
		ITextSelection sel = (ITextSelection) editor.getSelectionProvider()
				.getSelection();

		try {
			doc.replace(sel.getOffset(), sel.getLength(), UUID.randomUUID()
					.toString());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return null;
	}
}
