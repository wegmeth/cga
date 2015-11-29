package extras;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.joml.Vector3f;

import assignment05.entities.Material;

public class Configurator extends JFrame {

	private JTextField txtShininess;
	private JVecortField txtDiffuse;
	private JVecortField txtSpecular;
	private JVecortField txtAmbient;

	public JTextField getTxtShininess() {
		return this.txtShininess;
	}

	public void setTxtShininess(JTextField txtShininess) {
		this.txtShininess = txtShininess;
	}

	public JVecortField getTxtDiffuse() {
		return this.txtDiffuse;
	}

	public void setTxtDiffuse(JVecortField txtDiffuse) {
		this.txtDiffuse = txtDiffuse;
	}

	public JVecortField getTxtSpecular() {
		return this.txtSpecular;
	}

	public void setTxtSpecular(JVecortField txtSpecular) {
		this.txtSpecular = txtSpecular;
	}

	public JVecortField getTxtAmbient() {
		return this.txtAmbient;
	}

	public void setTxtAmbient(JVecortField txtAmbient) {
		this.txtAmbient = txtAmbient;
	}

	public JVecortField getTxtEmission() {
		return this.txtEmission;
	}

	public void setTxtEmission(JVecortField txtEmission) {
		this.txtEmission = txtEmission;
	}

	private JVecortField txtEmission;

	public Configurator(Material material){
		this.txtEmission = new JVecortField(material.getEmission());
		this.txtAmbient= new JVecortField(material.getAmbient());
		this.txtSpecular = new JVecortField(material.getSpecular());
		this.txtDiffuse = new JVecortField(material.getDiffuse());
		this.txtShininess = new JTextField(material.getShininess()+"");

		this.add(this.txtShininess);
		this.add(this.txtDiffuse);
		this.add(this.txtSpecular);
		this.add(this.txtAmbient);
		this.add(this.txtEmission);

		this.setVisible(true);
	}

	private class JVecortField extends JPanel {
		public JTextField x = new JTextField();
		public JTextField y = new JTextField();
		public JTextField z = new JTextField();

		public JVecortField(Vector3f vector){
			this.x.setText(vector.x+"");
			this.y.setText(vector.y+"");
			this.z.setText(vector.z+"");

			this.add(this.x);
			this.add(this.y);
			this.add(this.z);
		}
	}

}