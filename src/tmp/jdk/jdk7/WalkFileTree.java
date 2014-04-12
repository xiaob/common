package tmp.jdk.jdk7;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class WalkFileTree {

	public static void main(String[] args) throws IOException {
		test2();
	}
	
	public static void test1()throws IOException{
		Path start = Paths.get("D:/Images");
		
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)throws IOException {
				System.out.println(file.toString());
				return FileVisitResult.CONTINUE;
			}
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				System.out.println(dir.toString());
				return FileVisitResult.CONTINUE;
			}
		});
	}
	
	public static void test2()throws IOException{
		Path start = Paths.get("D:/Images");
		
		// 没有递归遍历子目录中的文件
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(start, "*.{java,txt,bat}");
		for(Path path : directoryStream){
			System.out.println(path.toString() + ", " + path.getFileName());
		}
	}

}
