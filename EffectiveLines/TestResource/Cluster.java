import java.util.ArrayList;

public class Cluster<T>{
	private ArrayList<T> dataPoints = new ArrayList<T>();//类簇中的样本点
	//private String clusterName;
	private T clusterCenter ;
	private int numberInCluster;
	public Cluster(){}
	public Cluster(int numberInCluster,T clusterCenter){
		this.numberInCluster = numberInCluster;
		this.clusterCenter = clusterCenter;
	}
	public Cluster(ArrayList<T> dataPoints,int numberInCluster,T clusterCenter){
		this.dataPoints = dataPoints;
		this.numberInCluster = numberInCluster;
		this.clusterCenter = clusterCenter;
	}
	public ArrayList<T> getDataPoints(){
		return dataPoints;
	}
	public void setDataPoints(ArrayList<T> dataPoints){
		this.dataPoints = dataPoints;
	}
	public T getClusterCenter(){
		return clusterCenter;
	}
	public void setClusterCenter(T clusterCenter){
		this.clusterCenter = clusterCenter;
	}
	/*public String getClusterName(){
		return clusterName;
	}
	public void setClusterName(String clusterName){
		this.clusterName = clusterName;
	}*/
	public int getNumberInCluster(){
		return numberInCluster;
	}
	public void setNumberInCluster(int numberInCluster ){
		this.numberInCluster = numberInCluster;
	}
}