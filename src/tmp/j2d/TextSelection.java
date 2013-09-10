package tmp.j2d;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.yuan.common.util.StringUtil;

public class TextSelection implements Transferable {

	protected String text;
	public static final DataFlavor UNICODE_FLAVOR = new DataFlavor("text/plain; charset=unicode; class=java.io.InputStream", "Unicode Text"); 
	public static final DataFlavor LATIN1_FLAVOR = new DataFlavor("text/plain; charset=iso-8859-1; class=java.io.InputStream", "Latin-1 Text"); 
	public static final DataFlavor ASCII_FLAVOR = new DataFlavor("text/plain; charset=ascii; class=java.io.InputStream", "ASCII Text"); 
	public static DataFlavor[] SUPPORTED_FLAVORS = {DataFlavor.stringFlavor, UNICODE_FLAVOR, LATIN1_FLAVOR, ASCII_FLAVOR};
	
	public TextSelection(String text){
		this.text = text;
	}
	
	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if(flavor.equals(DataFlavor.stringFlavor)){
			return text;
		}else if(flavor.isMimeTypeEqual("text/plain")&&(flavor.getRepresentationClass().equals(InputStream.class))){
			String encoding = flavor.getParameter("charset");
			if(StringUtil.hasText(encoding)){
				return new ByteArrayInputStream(text.getBytes(encoding));
			}
			return new ByteArrayInputStream(text.getBytes());
		}
		
		return null;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return SUPPORTED_FLAVORS;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		for(DataFlavor df : SUPPORTED_FLAVORS){
			if(df.equals(flavor)){
				return true;
			}
		}//for
		return false;
	}

}
