importPackage(javax.swing);
importPackage(java.lang);
importPackage(com.yuan.common.file);
importPackage(java.util.concurrent);

function doSwing(t){
	var f = new Packages.javax.swing.JFrame(t);
	f.setSize(400,300);
	f.setVisible(true);
}

Packages.com.yuan.common.file.FileUtil.writeToFile("=========", "d:/tmp/t.txt");
//doSwing("我的JS脚本窗体");