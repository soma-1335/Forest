package forest;

import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import mvc.Model;


public class ForestModel extends Model {
	
	private Map<String,String> deepMap; 

	private Map<Integer,String>	indexMap;

	private Map<String,String> linkMap;

	private List<Node> nodeList;

	private List<Node> rootList;

	private Map<Integer,Node> nodeMap;

	private int base;


	public ForestModel(File file){
		super();
		List<String> aList = readFile(file);
		

		deepMap = new HashMap<String,String>();
		indexMap = new TreeMap<Integer,String>();
		linkMap = new HashMap<String,String>();

		Iterator<String> iterator = aList.iterator();
		String regrex = "[^\\|\\s\\-]";
		Pattern p = Pattern.compile(regrex);
		Integer actionFlag = 0;

		while(iterator.hasNext()) 
		{
			String str = iterator.next();
			
			if(str.equals("trees:")){actionFlag = 1;continue;}
			if(str.equals("nodes:")){actionFlag = 2;continue;}
			if(str.equals("branches:")){actionFlag = 3;continue;}
			
			
			switch(actionFlag){

				case 1:
					Matcher m = p.matcher(str);
					Integer index = m.find() ? m.start() : null;
					Integer deep = index / 4;
					deepMap.merge(deep.toString() , str.substring(index), 
								(v1,v2) -> {
										return v1 + "," + v2;
								});
					break;

				case 2:
					List<String> strList1 = Arrays.asList(str.split(","));
					indexMap.put(Integer.valueOf(strList1.get(0)) , strList1.get(1).trim());
					break;
				
				case 3:
					List<String> strList2 = Arrays.asList(str.split(","));
					linkMap.merge(strList2.get(0),strList2.get(1).trim() ,
									(v1,v2) -> {
										return v1.toString() + "," + v2.toString();
									});
					break;
			}
		}

		paform();
	}


	private List<String> readFile(File file){

		List<String> aList = new ArrayList<String>();

		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

			String aString = null;
			while((aString = reader.readLine()) != null){
				aList.add(aString);
			}
			reader.close();
		}
		catch(FileNotFoundException e){e.printStackTrace();}
		catch(IOException e){e.printStackTrace();}

		return aList;
	}

	public void paform(){
		nodeList = new ArrayList<Node>();
		rootList = new ArrayList<Node>();
		nodeMap = new TreeMap<Integer,Node>();
		
		int x = 0;
		int y = 0;
		//Nodeに深さと名前を振る
		for(var entrySet: deepMap.entrySet())
		{
			String[] strs = entrySet.getValue().split(",");
			
			for(String str : strs)
			{
				Node node = new Node(entrySet.getKey(), str,x,y);
				nodeList.add(node);
				y += 15;
			}
		}

		//nodeList = notRepetition(nodeList);

		//Nodeに番号を振る
		for(var entrySet : indexMap.entrySet())
		{
			for(Node node : nodeList){
				if(node.getName().equals(entrySet.getValue())){
					node.setNumber(entrySet.getKey());
					nodeMap.put(entrySet.getKey(), node);
				}
			}
			// nodeList.stream()
			// 		.takeWhile(node -> node.getName().equals(entrySet.getValue()))
			// 		.forEach(node -> node.addNumberList(entrySet.getKey()));
		}

		//Nodeに子要素を振る
		for(var entrySet : linkMap.entrySet())
		{
			for(Node node: nodeList){
				if(node.sameNumber(entrySet.getKey())){node.addchildrenNumber(entrySet.getValue());}
			}
			// nodeList.stream()
			// 		.takeWhile(node -> node.sameNumberList(entrySet.getKey()))
			// 		.forEach(node -> node.addchildrenNumber(entrySet.getValue()));
		}


		//rootList = nodeList.stream().takeWhile(node -> node.getDeep().equals(0)).collect(Collectors.toList());

		for(var entrySet : linkMap.entrySet())
		{
			Node node = nodeMap.get(Integer.valueOf(entrySet.getKey()));
			String[] strs = entrySet.getValue().split(",");
			for(String str : strs){
				node.addChildren(nodeMap.get(Integer.valueOf(str)));
			}
		}
	}

	// //Node名が同じものをなくす
	// public List<Node> notRepetition(List<Node> list){
	// 	Set set = new HashSet<String>();
	// 	List<Node> aList = list.stream().filter(str -> set.add(str.getName()))
	// 						.collect(Collectors.toList());
	// 	return aList;
	// }


	// public Integer getIndex(String str){
	// 	Integer index = null;

	// 	for(var set : indexMap.entrySet()){
	// 		if(set.getValue().equals(str)){
	// 			index = set.getKey();
	// 		}
	// 	}

	// 	return index;
	// }

	public List<Node> getNodeList(){
		return this.nodeList;
	}

	public Map<Integer, Node> getNodeMap(){
		return this.nodeMap;
	}

	public void animate(){
		int height = 0;
		for(Node node : this.nodeList){
			animate(node,0,height);
			height += 15;
		}
	}

	public void animate(Node node,int x,int y){
		int size = 16;
		int move = 0;
		int sumHeight = 0;
		boolean isParent = false;
		List<Node> children = node.getChildren();
		

		if(!node.getFlag()){
			node.computePoint(x,y);
			node.inited();
		}
		else{
			isParent = true;
			this.base = y + size;
			return;
		}

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if(!node.haveChildren()){
			this.base = y + size;
			changed();
			isParent = false;
		}
		else{
			changed();
			for(Node child : children){
				Point lastPoint = child.getPoint();
				animate(child,x + node.getSize().width+25*2,this.base);
				sumHeight += lastPoint.y;

				try {
					Thread.sleep(20);
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if(node.haveChildren()){
				if(isParent){
					node.computePoint(x, y);
					
				}
				else{ 
					move = sumHeight / children.size();
					node.computePoint(x,move);
				}
				changed();
			}
		}

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void mouseClicked(Point aPoint) {
		//System.out.println("model");
		for(Node node : nodeList){
			
			Point point = node.getPoint();
			
			Dimension size = node.getSize();
			if(aPoint.x >= point.x && aPoint.x <= point.x + size.width && aPoint.y >= point.y && aPoint.y <= point.y + size.height) {
				System.out.println(node.getName());
			}
		}
	}
}
