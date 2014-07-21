package Project1.Part3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 1: Part 3 - Tree Printer
 * Daniel Kerr and Charles So
 * Date: 06/20/2014
 * File: PrintTree.java
 */
/**
 * @author SoCh
 *
 */
public class PrintTree {
    
    /**
     * printTheTree prints the tree to the console 'sideways'. I remember vaguely
     * back in the dawn-days doing this, but I couldn't quite nail the details, 
     * so I did look here to jog my memory: 
     * http://www.java2s.com/Code/C/Data-Structure-Algorithm/Displaysabinarytree.htm
     * @param rootNode the root node of the subtree to print
     * @param currLevel the current height of the subtree's root
     */
    public static void printTheTree(BinaryNode rootNode, int currLevel){
        if(rootNode == null){
            return;
        }
        String nodeString = "";
        printTheTree(rootNode.getRightNode(), currLevel + 1);
        for(int i = 0; i < currLevel; i++){
            nodeString += "   ";
        }
        nodeString += rootNode.getValue();
        System.out.println(nodeString);
        printTheTree(rootNode.getLeftNode(), currLevel + 1);
    }
    
    public static void printLevels(BinaryNode tree) {

        Queue<BinaryNode> queue = new java.util.LinkedList<BinaryNode>();
        Queue<Integer> levels = new java.util.LinkedList<Integer>();
        queue.add(tree);
        levels.add(0);

        int lastLevel= 0;
        while (queue.size() > 0) {
        	BinaryNode node = queue.remove();
            int level = levels.remove();

            if(level!=lastLevel){
                System.out.println();
                lastLevel = level;
            }

            System.out.print(node.getValue() + " ");

            if(node.getLeftNode()!=null){
                queue.add(node.getLeftNode());
                levels.add(level+1);
            } else {
            	System.out.print("     ");
            }

            if(node.getRightNode() !=null ){
                queue.add(node.getRightNode());
                levels.add(level+1);
            } else {
            	System.out.print("     ");
            }
        }
        System.out.println("");
    }

    /**
     * DO NOT USE, as this will take up a lot of space to display the binary tree
     * 
     */
    public static void printMyTree(BinaryNode rootNode) {
    	int maxLevel = PrintTree.maxLevel(rootNode);
    	
    	printMyTreeInternal(Collections.singletonList(rootNode), 1, maxLevel);
    }
    
    /**
     * DO NOT USE, as this will take up a lot of space to display the binary tree
     * 
     * @param listToPrint
     * @param level
     * @param maxLevel
     */
    private static void printMyTreeInternal (List<BinaryNode> listToPrint, int level, int maxLevel) { if (listToPrint.isEmpty() || PrintTree.isAllElementsNull(listToPrint))
    		return;
    	
    	int floor = maxLevel - level;
    	int edgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
    	int firstSpaces = (int) Math.pow(2, (floor)) - 1;
    	int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;
    	
    	PrintTree.printWhitespaces(firstSpaces);
    	
    	List<BinaryNode> newNodes = new ArrayList<BinaryNode> ();
    	for (BinaryNode node : listToPrint) {
    		if (node != null) {
    			System.out.print(node.getValue());
    			newNodes.add(node.getLeftNode());
    			newNodes.add(node.getRightNode());
    		} else {
    			newNodes.add(null);
    			newNodes.add(null);
    			System.out.print(" ");
    		}
    		
    		PrintTree.printWhitespaces(betweenSpaces);
    	}
    	System.out.println("");
    	
    	for (int i = 1; i < edgeLines; i++) {
    		for (int j = 0; j < listToPrint.size(); j++) {
    			PrintTree.printWhitespaces(firstSpaces - i);
    			if (listToPrint.get(j) == null) {
    				PrintTree.printWhitespaces(edgeLines + edgeLines + i + 1);
    				continue;
    			}
    			
    			if (listToPrint.get(j).getLeftNode() != null)
    				System.out.print("/");
    			else
    				PrintTree.printWhitespaces(1);
    			
    			PrintTree.printWhitespaces(i + i - 1);
    			
    			if (listToPrint.get(j).getRightNode() != null)
    				System.out.print("\\");
    			else
    				PrintTree.printWhitespaces(1);
    			
    			PrintTree.printWhitespaces(edgeLines + edgeLines - 1);
    		}
    		System.out.println("");
    	}
    	
    	printMyTreeInternal(newNodes, level + 1, maxLevel);
    }
    
    private static void printWhitespaces(int count) {
    	for (int i = 0; i < count; i++) {
    		System.out.print(" ");
    	}
    }
    
    private static int maxLevel(BinaryNode node) {
    	if (node == null)
    		return 0;
    	
    	return Math.max(PrintTree.maxLevel(node.getLeftNode()), PrintTree.maxLevel(node.getRightNode())) + 1;
    }
    
    private static boolean isAllElementsNull(List<BinaryNode> list) {
    	for (BinaryNode curNode : list) {
    		if (curNode != null)
    			return false;
    	}
    	
    	return true;
    }
}
