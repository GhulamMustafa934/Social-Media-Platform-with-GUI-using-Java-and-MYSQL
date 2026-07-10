package View;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageSelector {
    
    private JFileChooser fileChooser;
    private String selectedPath;
    
    public ImageSelector() {
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Pictures"));
        fileChooser.setDialogTitle("Select an image");
        
        // Filter for image files
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image Files", "jpg", "jpeg", "png", "gif", "bmp");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
    }
    
    public String selectImage() {
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedPath = selectedFile.getAbsolutePath();
            return selectedPath;
        }
        return null;
    }
}
