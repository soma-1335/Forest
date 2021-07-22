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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import mvc.Model;


public class ForestModel extends Model {
	
	private Map<String,String> deepMap; 

	private Map<Integer,String>	indexMap;

	private Map<String,String> linkMap;

	private List<Node> nodeList;

	public ForestModel(File file){
		super();
		List<String> aList = readFile(file);

		deepMap = new HashMap<String,String>();
		indexMap = new HashMap<Integer,String>();
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
					indexMap.put(Integer.valueOf(strList1.get(0)) , strList1.get(1));
					break;
				
				case 3:
					List<String> strList2 = Arrays.asList(str.split(","));
					linkMap.merge(strList2.get(0),strList2.get(1) ,
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
		
		//Nodeに深さと名前を振る
		for(var entrySet: deepMap.entrySet())
		{
			String[] strs = entrySet.getValue().split(",");
			int x = 0;
			int y = 0;
			for(String str : strs)
			{
				Node node = new Node(entrySet.getKey(), str);
				Dimension size = node.getPreferredSize();
				node.setBounds(x, y,size.width, size.height);
				nodeList.add(node);
				y += 10;
			}
		}

		nodeList = notRepetition(nodeList);

		//Nodeに番号を振る
		for(var entrySet : indexMap.entrySet())
		{
			nodeList.stream()
					.takeWhile(node -> node.getName().equals(entrySet.getValue()))
					.forEach(node -> node.addNumberList(entrySet.getKey()));
		}

		//Nodeに子要素を振る
		for(var entrySet : linkMap.entrySet())
		{
			nodeList.stream()
					.takeWhile(node -> node.sameNumberList(entrySet.getKey()))
					.forEach(node -> node.addChildren(entrySet.getValue()));
		}


	}

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
		this.changed();
	}
}
