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

	public Map<Integer,String>	indexMap;

	public Map<String,String> linkMap;

	public List<Node> nodeList;

	private List<Node> rootList;

	public Map<Integer,Node> nodeMap;

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
		nodeMap = new HashMap<Integer,Node>();
		
		int x = 0;
		int y = 0;
		//Nodeに深さと名前を振る
		for(var entrySet: deepMap.entrySet())
		{
			String[] strs = entrySet.getValue().split(",");
			
			for(String str : strs)
			{
				Node node = new Node(this,entrySet.getKey(), str,x,y);
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
					node.addNumberList(entrySet.getKey());
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
				if(node.sameNumberList(entrySet.getKey())){node.addchildrenNumber(entrySet.getValue());}
			}
			nodeList.stream()
					.takeWhile(node -> node.sameNumberList(entrySet.getKey()))
					.forEach(node -> node.addchildrenNumber(entrySet.getValue()));
		}


		rootList = nodeList.stream().takeWhile(node -> node.getDeep().equals(0)).collect(Collectors.toList());

		for(var entrySet : linkMap.entrySet())
		{
			Node node = nodeMap.get(Integer.valueOf(entrySet.getKey()));
			String[] strs = entrySet.getValue().split(",");
			for(String str : strs){
				node.addChildren(nodeMap.get(Integer.valueOf(str)));
			}
		}

		this.changed();
	}

	//Node名が同じものをなくす
	public List<Node> notRepetition(List<Node> list){
		Set set = new HashSet<String>();
		List<Node> aList = list.stream().filter(str -> set.add(str.getName()))
							.collect(Collectors.toList());
		return aList;
	}


	public Integer getIndex(String str){
		Integer index = null;

		for(var set : indexMap.entrySet()){
			if(set.getValue().equals(str)){
				index = set.getKey();
			}
		}

		return index;
	}

	public List<Node> getNodeList(){
		return this.nodeList;
	}

	public void animate(){
		Dimension windowSize = new Dimension(800,600);
	// 	int xoffset = (windowSize.width - 100) / deepMap.size();
	// 	List<Integer> yoffset = new ArrayList<Integer>();
	// 	List<Integer> countList = new ArrayList<Integer>();
	// 	for(var set: deepMap.entrySet())
	// 	{
	// 		String[] strs = set.getValue().split(",");
	// 		int y = (windowSize.height - 100) / strs.length  ;
	// 		yoffset.set(Integer.parseInt(set.getKey()),y);
	// 		countList.add(1);
	// 	}

	// 	try {
	// 		Thread.sleep(1000);
	// 	} catch (InterruptedException e){
	// 		e.printStackTrace();
	// 	}

	// 	int yoffset =  (windowSize.height - 100) / (rootList.size()+1);
	// 	Point point = new Point(20,yoffset);
	// 	for(Node root : rootList)
	// 	{
	// 		root.computePoint(point,0,xoffset,60);
	// 		point.y += yoffset;
	// 	}

		
		for(Integer i=0;i<deepMap.size();i++){
			List<Node> list = new ArrayList<Node>();
			for(var value : nodeMap.values()){
				if(value.getDeep().equals(i)){list.add(value);}
			}

			Integer count = 1;
			int size = list.size();
			int xoffset = (windowSize.width - 100) / deepMap.size();
			int yoffset = (windowSize.height - 100) / (list.size()+1);
			for(Node node : list){
				Point point = new Point(xoffset*i, yoffset*count);
				node.computePoint(point);
				count++;
			}
		}
			
		
	}
}
