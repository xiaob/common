package tmp.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 哈夫曼编码是可变字长编码(VLC)的一种。Huffman于1952年提出一种编码方法，
 * 该方法完全依据字符出现概率来构造异字头的平均长度最短的码字，有时称之为最佳编码，
 * 一般就叫作Huffman编码（有时也称为霍夫曼编码）
 * 赫夫曼树是一种最优二叉树,因为他的WPL是最短的
 * 第一步： 我们将所有的节点都作为独根结点。
第二步:   我们将最小的C和A组建为一个新的二叉树，权值为左右结点之和。
第三步： 将上一步组建的新节点加入到剩下的节点中，排除上一步组建过的左右子树，我们选中B组建新的二叉树，然后取权值。
第四步： 同上。

 * 赫夫曼树的编码规则：左子树为0，右子树为1
 * @author yuan<cihang.yuan@happyelements.com>
 *
 */
public class Tree {

	public static void main(String[] args) {
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		treeNodeList.add(new TreeNode(2, "C"));
		treeNodeList.add(new TreeNode(5, "A"));
		treeNodeList.add(new TreeNode(7, "B"));
		treeNodeList.add(new TreeNode(13, "D"));
		
		TreeNode root = huffmanTree(treeNodeList);
		System.out.println(root.getWeight());
		
		Map<String, String> encodeTable = new HashMap<String, String>();
		encodeTree(root, encodeTable);
		
		String text = "DBCA";
		String encodedText = encode(encodeTable, text);
		System.out.println(encodedText);
		System.out.println(decode(root, encodedText));
	}
	
	public static String encode(Map<String, String> encodeTable, String text){
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<text.length(); i++){
			sb.append(encodeTable.get(String.valueOf(text.charAt(i))));
		}
		
		return sb.toString();
	}
	
	public static String decode(TreeNode root, String text){
		StringBuilder sb = new StringBuilder();
				
		TreeNode currentNode = root;
		for(int i=0; i<text.length(); i++){
			char c = text.charAt(i);
			if(c == '0'){
				if(currentNode.getLeft().isLeaf()){
					sb.append(currentNode.getLeft().getValue());
					currentNode = root;
				}else{
					currentNode = currentNode.getLeft();
				}
			}else{
				if(currentNode.getRight().isLeaf()){
					sb.append(currentNode.getRight().getValue());
					currentNode = root;
				}else{
					currentNode = currentNode.getRight();
				}
			}
		}
		
		return sb.toString();
	}
	
	// 将独立的节点构建成一棵霍夫曼树
	public static TreeNode huffmanTree(List<TreeNode> treeNodeList){
		if(treeNodeList.size() < 2){
			return null;
		}
		
		TreeNode root = null;
		while (treeNodeList.size() > 0) {
			root = selectMinWeightNode(treeNodeList);
		}
		
		return root;
	}
	// 选择最小的权重的两个节点组成一棵树
	private static TreeNode selectMinWeightNode(List<TreeNode> treeNodeList){
		TreeNode minWeightNode1 = null;
		TreeNode minWeightNode2 = null;
		
		for(TreeNode treeNode : treeNodeList){
			if(minWeightNode2 == null){
				minWeightNode2 = treeNode;
			}else if(minWeightNode1 == null){
				if(treeNode.getWeight() < minWeightNode2.getWeight()){
					minWeightNode1 = treeNode;
				}else{
					minWeightNode1 = minWeightNode2;
					minWeightNode2 = treeNode;
				}
			}else if(treeNode.getWeight() < minWeightNode2.getWeight()){
				if(treeNode.getWeight() < minWeightNode1.getWeight()){
					minWeightNode2 = minWeightNode1;
					minWeightNode1 = treeNode;
				}else{
					minWeightNode2 = treeNode;
				}
			}
		}
		
		if((minWeightNode1 == null) || (minWeightNode2 == null)){
			return null;
		}
		
		TreeNode parent = new TreeNode();
		minWeightNode1.setParent(parent);
		parent.setLeft(minWeightNode1);
		minWeightNode2.setParent(parent);
		parent.setRight(minWeightNode2);
		parent.setWeight(minWeightNode1.getWeight() + minWeightNode2.getWeight());
		
		treeNodeList.remove(minWeightNode1);
		treeNodeList.remove(minWeightNode2);
		// 如果好友节点则继续构建
		if(treeNodeList.size() > 0){
			treeNodeList.add(parent);
		}
		return parent;
	}
	
	// 遍历霍夫曼树进行编码
	public static void encodeTree(TreeNode node, Map<String, String> encodeTable){
		if(node.isLeaf()){
			encodeTable.put(node.getValue(), node.getHuffmanCode());
			return ;
		}
		
		if(node.getLeft() != null){
			node.getLeft().setCode("0");
			encodeTree(node.getLeft(), encodeTable);
		}
		if(node.getRight() != null){
			node.getRight().setCode("1");
			encodeTree(node.getRight(), encodeTable);
		}
	}

}

class TreeNode{
	
	private TreeNode parent;
	private TreeNode left;
	private TreeNode right;
	private int weight;
	private String value;
	private String code;
	
	public TreeNode() {
		super();
	}
	public TreeNode(int weight, String value) {
		super();
		this.weight = weight;
		this.value = value;
	}
	
	public boolean isLeaf(){
		return (left == null) && (right == null);
	}
	
	// 向上遍历获得该叶子节点的霍夫曼编码
	public String getHuffmanCode(){
		String huffmanCode = "";
		
		TreeNode pNode = this;
		while((pNode != null) && (pNode.getCode() != null)){
			huffmanCode = pNode.getCode() + huffmanCode;
			pNode = pNode.getParent();
		}
		
		return huffmanCode;
	}
	
	public TreeNode getParent() {
		return parent;
	}
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	public TreeNode getLeft() {
		return left;
	}
	public void setLeft(TreeNode left) {
		this.left = left;
	}
	public TreeNode getRight() {
		return right;
	}
	public void setRight(TreeNode right) {
		this.right = right;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
