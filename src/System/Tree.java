package System;

import java.awt.Dimension;
import java.awt.Rectangle;

import MindMapSystem.makeShellMap;

public class Tree {
	public TreeNode root = new TreeNode(0);
	
	public Tree() {	}
	//메소드
	public void showTree() {
		TreeNode selectedNode = this.root;
		while(true) {
			System.out.println(selectedNode.showInfo());
			if(this.goNext(selectedNode) == null) {
				break;
			}
			selectedNode = this.goNext(selectedNode);
		}
	}
	//생성 관련
	public TreeNode getLastRightNode() {
		//트리 가장 오른쪽 노드
		//트리 끝을 찾는다.
		TreeNode selectedNode = this.root;
		while(true) {
			if(selectedNode.getChild() == null) {
				break;
			}
			selectedNode = selectedNode.getChild();
			while(true) {
				if(selectedNode.getSibling() == null) {
					break;
				}
				selectedNode = selectedNode.getSibling();
			}
		}
		return selectedNode;
	}
	public TreeNode goNext(TreeNode node) {
		/*
		 * 그 레벨의 sibling이 없거나 child가 없으면
		 * 윗단계의 sibling을 반환
		 * root -> child -> sibling순
		 */
		TreeNode selectNode = node;
		if(node.hasNext() != null) {
			//다음 인자 children 이나 sibling이 있으면 반환
			return node.hasNext();
		}
		else {
			while(true) {
				if(selectNode.getSibling() != null) {
					//sibling이 있으면 sibling 반환
					return selectNode.getSibling();
				}
				if(selectNode == this.root) {
					//root면 null반환
					return null;
				}
				//parent로 올라가서 sibling을 찾는다.
				selectNode = selectNode.getParent();
			}
		}
	}
	
	public Dimension getTreeSize() {
		double width, height;
		
		TreeNode selectedNode = this.root;
		
		int maxLevel = 0;
						
		while(true) {
			if(selectedNode.getLevel() > maxLevel) {
				maxLevel = selectedNode.getLevel();
			}
			
			if(this.goNext(selectedNode) == null) {
				break;
			}
			selectedNode = this.goNext(selectedNode);
		}
		width = makeShellMap.defWidth + Math.pow(1+makeShellMap.ratioDistance, maxLevel)*makeShellMap.distance*2;
		height = makeShellMap.defHeight + Math.pow(1+makeShellMap.ratioDistance, maxLevel)*makeShellMap.distance*2;
		
		if(800 >= width) {
			width = MainSystem.getFrame().MMP.getX()+MainSystem.getFrame().MMP.getWidth();
		}
		if(600 >= height) {
			height = MainSystem.getFrame().MMP.getX()+MainSystem.getFrame().MMP.getHeight();
		}
		
		return new Dimension((int)width, (int)height);
	}
}
