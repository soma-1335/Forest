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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mvc.Model;

/**
 * moodel。ノードの情報を格納し、座標などの計算を行う。
 * 
 */
public class ForestModel extends Model {
	
	private Map<String,String> deepMap; 

	private Map<Integer,String>	indexMap;

	private Map<String,String> linkMap;

	private List<Node> nodeList;

	private List<Node> rootList;

	private Map<Integer,Node> nodeMap;

	private int base;


	/**
	 * コンストラクター。　このプログラムで使うデータを指定されたファイルから読み取りフィールドに束縛する。
	 * @param file 木構造が示されたファイル
	 */
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


	/**
	 * ファイルの内容を読み取り、リストに一行ずつ格納する。
	 * @param file 木構造が示されたファイル
	 * @return ファイルの内容が一行ずつ格納されたリスト
	 */
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

	/**
	 * ファイルから読み取った各情報からNodeの設定をする。
	 */
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


		//Nodeに番号を振る
		for(var entrySet : indexMap.entrySet())
		{
			for(Node node : nodeList){
				if(node.getName().equals(entrySet.getValue())){
					node.setNumber(entrySet.getKey());
					nodeMap.put(entrySet.getKey(), node);
				}
			}
		}

		// Nodeに子要素を振る
		for(var entrySet : linkMap.entrySet())
		{
			Node node = nodeMap.get(Integer.valueOf(entrySet.getKey()));
			String[] strs = entrySet.getValue().split(",");
			for(String str : strs){
				node.addChildren(nodeMap.get(Integer.valueOf(str)));
			}
		}
	}

	
	/**
	 * ノードが格納されたMapを応答する
	 * @return Nodeが格納されたMap
	 */
	public Map<Integer, Node> getNodeMap(){
		return this.nodeMap;
	}

	/**
	 * Nodeのリストを表示する。
	 */
	public void animate(){
		int height = 0;
		for(Node node : this.nodeList){
			animate(node,0,height);
			height += 15;
		}
	}

	/**
	 * Nodeのリストをアニメーションで表示する。
	 * @param node 一つのノード
	 * @param x　x座標
	 * @param y　y座標
	 */
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

	/**
	 * Nodeがクリックされたかを判定し、処理する
	 * @param aPoint クリックされた座標
	 */
	public void mouseClicked(Point aPoint) {
		for(Node node : nodeList){
			
			Point point = node.getPoint();
			
			Dimension size = node.getSize();
			if(aPoint.x >= point.x && aPoint.x <= point.x + size.width && aPoint.y >= point.y && aPoint.y <= point.y + size.height) {
				System.out.println(node.getName());
			}
		}
	}
}
