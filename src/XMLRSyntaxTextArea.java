import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

public class XMLRSyntaxTextArea extends RSyntaxTextArea implements
                DropTargetListener {
        public XMLRSyntaxTextArea(int rows, int cols) {
                super(rows, cols);
                this.setCodeFoldingEnabled(true);
                new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
        }

        public void dragEnter(DropTargetDragEvent dtde) {

        }

        public void dragOver(DropTargetDragEvent dtde) {
        }

        public void dropActionChanged(DropTargetDragEvent dtde) {

        }

        public void dragExit(DropTargetEvent dte) {
        }

        public void drop(DropTargetDropEvent dtde) {
                try {
                        Transferable tr = dtde.getTransferable();
                        if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                                List list = (List) (dtde.getTransferable()
                                                .getTransferData(DataFlavor.javaFileListFlavor));
                                Iterator iterator = list.iterator();
                                while (iterator.hasNext()) {
                                        File f = (File) iterator.next();
                                        String filePath = f.getAbsolutePath();
                                        String xml = FileUtil
                                                        .getOSDefaultXML(filePath);
                                        if (xml != null) {
                                                String plistInfo = FileUtil
                                                                .getFileData(filePath,
                                                                                null,
                                                                                true);
                                                this.setText(plistInfo, xml);
                                        }
                                        break;
                                }
                                dtde.dropComplete(true);
                                this.updateUI();
                        } else {
                                dtde.rejectDrop();
                        }
                } catch (IOException ioe) {
                        ioe.printStackTrace();
                } catch (UnsupportedFlavorException ufe) {
                        ufe.printStackTrace();
                }

        }

        public void setText(String t, String findFile) {
                this.setSyntaxEditingStyle(FileUtil.getSynatx(findFile));
                super.setText(t);
        }
}
