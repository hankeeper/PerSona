package TextEditorSystem;

import System.*;
import Event.ShowAttributeHandler;
import Graphic.TextEditorPane;
import Graphic.*;

public class MakeTexttoTree {
	/*
	 * text를 받아서 tree를 만들어주는 시스템
	 * text레퍼런스를 받아서 setTree로 MainSystem의 currentIndex위치에 tree만듦
	 * startMake실행할때마다 새로 만듬
	 */
	
	static String originalStr;
	static String [] splitedStr;
	//public메소드
	public static boolean startMake() {
		TextEditorPane TEP = MainSystem.getFrame().TEP;
		
		if(TEP.getText().equals("") == true) {
			return false;
		}
		
		MainSystem.setTree(new Tree());
		Tree tree = MainSystem.getCurrentTree();
		//null일떄를 위한 과정 null이 아니면 그냥 별 영향 X임
		MakeTexttoTree.originalStr = TEP.getText();
		MakeTexttoTree.splitedStr = MakeTexttoTree.originalStr.split("\n");
		
		if(MakeTexttoTree.isVaildTree(MakeTexttoTree.splitedStr) == false) {
			return false;
		}
		
		MakeTexttoTree.makeTree();
		//확인용
		tree.showTree();
		MainSystem.setTree(tree);
		
		return true;
	}
	//private메소드
	private static int getTextLevel(String str) {
		int level = 0;
		try {
			while(true) {
				if(str.charAt(level) != '\t') {
					break;
				}
				level++;
			}
		}
		catch(StringIndexOutOfBoundsException e) {
			
		}
		

		return level;
	}
	private static void makeTree() {
		Tree tree = MainSystem.getCurrentTree();
		
		TreeNode selectedNode = tree.root;
		//root 생성
		for(int i = 0; i < splitedStr.length; i++) {
			try {
				if(MakeTexttoTree.isChild(splitedStr[i-1], splitedStr[i]) == true) {
					//첫 child일경우
					selectedNode.setChild(new TreeNode());
					selectedNode.getChild().setParent(selectedNode);
					selectedNode = selectedNode.getChild();
					
				}else if(MakeTexttoTree.isSibling(splitedStr[i-1], splitedStr[i]) == true) {
					//sibling일경우
					selectedNode.setSibling(new TreeNode());
					selectedNode.getSibling().setParent(selectedNode.getParent());
					selectedNode = selectedNode.getSibling();
				}else {
					//둘다 아니고 탐색 해야함...
					selectedNode = MakeTexttoTree.isWhere(MakeTexttoTree.getTextLevel(splitedStr[i]));
					selectedNode.setSibling(new TreeNode());
					selectedNode.getSibling().setParent(selectedNode.getParent());
					selectedNode = selectedNode.getSibling();
				}
			}
			catch(ArrayIndexOutOfBoundsException e) {
				selectedNode = tree.root;
			}
			if(selectedNode != tree.root) {
				selectedNode.setLink(selectedNode.getThisLinkIndex(), (selectedNode.getThisLinkIndex()+2)%4);
			}
			selectedNode.setLevel(MakeTexttoTree.getTextLevel(splitedStr[i]));
			selectedNode.Map.setData(splitedStr[i].substring(selectedNode.getLevel()));
		}
	}
	private static boolean isChild(String parentStr, String childStr) {
		if(MakeTexttoTree.getTextLevel(parentStr) < MakeTexttoTree.getTextLevel(childStr)) {
			return true;
		} else {
			return false;
		}
	}
	private static boolean isSibling(String parentStr, String childStr) {
		if(MakeTexttoTree.getTextLevel(parentStr) == MakeTexttoTree.getTextLevel(childStr)) {
			return true;
		} else {
			return false;
		}
	}
	//Sibling위치 찾아주기
	private static TreeNode isWhere(int level) {
		Tree tree = MainSystem.getCurrentTree();
		
		TreeNode selectedNode = tree.getLastRightNode();
		while(true) {
			if(selectedNode.getLevel() == level) {
				break;
			}
			selectedNode = selectedNode.getParent();
		}
		while(true) {
			if(selectedNode.getSibling() == null) {
				break;
			}
			selectedNode = selectedNode.getSibling();
		}
		return selectedNode;
	}
	//splitedStr이 트리가 되는지 확인
	private static boolean isVaildTree(String [] SplitStr) {
		int rootNum = 0;
		//root가 없을때
		if(SplitStr[0].equals("")) {
			return false;
		}
		
		for(int i = 0; i < SplitStr.length; i++) {
			//데이터가 없을때
			if(SplitStr[i].substring(MakeTexttoTree.getTextLevel(SplitStr[i])).equals("")) {
				return false;
			}
			//root개수
			if(MakeTexttoTree.getTextLevel(SplitStr[i]) == 0) {
				rootNum++;
			}
		}
		//root여러개일때
		if(rootNum > 1) {
			return false;
		}
		return true;
	}
}
