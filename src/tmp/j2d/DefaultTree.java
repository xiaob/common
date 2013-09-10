package tmp.j2d;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class DefaultTree extends JScrollPane {

	private static final long serialVersionUID = 1L;

	private JTree tree;
	private DefaultMutableTreeNode rootNode;
	private JTextArea textArea;
	
	public DefaultTree(JTextArea textArea){
		super();
		this.textArea = textArea;
		buildRootNode();
		tree = new JTree(rootNode);
		tree.addTreeSelectionListener(new TreeSelectionHandle());
		this.expandAll();
		super.setViewportView(tree);
	}
	
	protected void buildRootNode(){
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("水果");
		node1.add(new DefaultMutableTreeNode("苹果"));
		node1.add(new DefaultMutableTreeNode("桔子"));
		DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("蔬菜");
		node2.add(new DefaultMutableTreeNode("白菜"));
		node2.add(new DefaultMutableTreeNode("西红柿"));
		DefaultMutableTreeNode node3 = new DefaultMutableTreeNode("礼品");
		DefaultMutableTreeNode cc1 = new DefaultMutableTreeNode("礼品");
		cc1.add(new DefaultMutableTreeNode("礼品一"));
		cc1.add(new DefaultMutableTreeNode("礼品二"));
		node3.add(cc1);
		node3.add(new DefaultMutableTreeNode("茅台"));
		
		rootNode = new DefaultMutableTreeNode("今天要买的东西");
		rootNode.add(node1);
		rootNode.add(node2);
		rootNode.add(node3);
	}
	
	public void expandAll(){
		expandNodeAndChildren(rootNode, new TreePath(rootNode));
	}
	
	protected void expandNodeAndChildren(TreeNode node, TreePath nodePath){
		if(node.isLeaf()){
			return ;
		}
		tree.expandPath(nodePath);
		int childCount = node.getChildCount();
		for(int i=0; i<childCount; i++){
			TreeNode childNode = node.getChildAt(i);
			if(!childNode.isLeaf()){
				expandNodeAndChildren(childNode, nodePath.pathByAddingChild(childNode));
			}
		}
	}
	
	/**
	 * 只展开第一级节点
	 */
	public void expandRoot(){
		TreePath rootPath = new TreePath(rootNode);
		tree.expandPath(rootPath);
	}
	
	public void collapseAll(){
		collapseNodeAndChildren(rootNode, new TreePath(rootNode));
	}
	
	protected void collapseNodeAndChildren(TreeNode node, TreePath nodePath){
		if(node.isLeaf()){
			return ;
		}
		tree.collapsePath(nodePath);
		int childCount = node.getChildCount();
		for(int i=0; i<childCount; i++){
			TreeNode childNode = node.getChildAt(i);
			if(!childNode.isLeaf()){
				collapseNodeAndChildren(childNode, nodePath.pathByAddingChild(childNode));
			}
		}
	}
	
	public void collapseRoot(){
		TreePath rootPath = new TreePath(rootNode);
		tree.collapsePath(rootPath);
	}
	
	public DefaultMutableTreeNode insertAfter(String userObject, String newUserObject){
		DefaultMutableTreeNode node = findNode(userObject);
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)node.getParent();
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newUserObject);
		parentNode.insert(newNode, parentNode.getIndex(node) + 1);
		return newNode;
	}
	public DefaultMutableTreeNode insertBefore(String userObject, String newUserObject){
		DefaultMutableTreeNode node = findNode(userObject);
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)node.getParent();
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newUserObject);
		parentNode.insert(newNode, parentNode.getIndex(node));
		return newNode;
	}
	
	public void removeNode(String userObject){
		DefaultMutableTreeNode node = findNode(userObject);
		rootNode.remove(node);
	}
	
	public DefaultMutableTreeNode findNode(String userObject){
		return findNode(rootNode, new TreePath(rootNode), userObject);
	}
	
	protected DefaultMutableTreeNode findNode(DefaultMutableTreeNode node, TreePath nodePath, String userObject){
		if(node.isLeaf()){
			String nodeUserObject = (String)node.getUserObject();
			if(nodeUserObject.equals(userObject)){
				return node;
			}
			return null;
		}
		
		int childCount = node.getChildCount();
		for(int i=0; i<childCount; i++){
			TreeNode childNode = node.getChildAt(i);
			if(!childNode.isLeaf()){
				DefaultMutableTreeNode result =findNode((DefaultMutableTreeNode)childNode, nodePath.pathByAddingChild(childNode), userObject);
				if(result != null){
					return result;
				}
			}else{
				String nodeUserObject = (String)((DefaultMutableTreeNode)childNode).getUserObject();
				if(nodeUserObject.equals(userObject)){
					return (DefaultMutableTreeNode)childNode;
				}
			}
		}
		return null;
	}
	
	private class TreeSelectionHandle implements TreeSelectionListener{
		@Override
		public void valueChanged(TreeSelectionEvent event) {
			Object[] nodeArray = event.getPath().getPath();
			TreeNode node = (TreeNode)nodeArray[nodeArray.length - 1];
			textArea.append(node.toString() + "\n\r");
		}
	}
}
