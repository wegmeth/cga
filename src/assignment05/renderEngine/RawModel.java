package assignment05.renderEngine;

public class RawModel {
	
	private int vaoID;
	private int vertexCount;
	
	public RawModel(int vaoID, int vertexCount){
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	public RawModel(RawModel model){
		this.vaoID = model.getVaoID();
		this.vertexCount = model.getVertexCount();
	}
	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
	

}
