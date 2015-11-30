package extras;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.joml.Vector3f;

import assignment05.entities.Material;
import assignment05.renderEngine.Renderer;

/**
 * @author jan
 *
 */
public class Configurator extends JFrame {
	private JSpinner txtShininess;

	private JVecortField txtDiffuse;
	private JVecortField txtSpecular;
	private JVecortField txtAmbient;
	private JVecortField txtEmission;
	Renderer ren;

	public Configurator(Material material, Renderer rend) {
		this.ren = rend;
		this.setBounds(100, 100, 800, 100);
		this.txtEmission = new JVecortField(material.getEmission(), "Emission");
		this.txtAmbient = new JVecortField(material.getAmbient(), "Ambient");
		this.txtSpecular = new JVecortField(material.getSpecular(), "Specular");
		this.txtDiffuse = new JVecortField(material.getDiffuse(), "Diffuse");
		FlowLayout gridBagLayout = new FlowLayout();
		getContentPane().setLayout(gridBagLayout);
		getContentPane().add(this.txtDiffuse);
		getContentPane().add(this.txtSpecular);
		getContentPane().add(this.txtAmbient);
		getContentPane().add(this.txtEmission);
		this.txtShininess = new JSpinner();
		SpinnerNumberModel modelX = new SpinnerNumberModel(material.getShininess(), 0, 100, 1);
		this.txtShininess.setModel(modelX);

		GridBagConstraints gbc_txtShininess = new GridBagConstraints();
		gbc_txtShininess.fill = GridBagConstraints.BOTH;
		gbc_txtShininess.gridx = 0;
		gbc_txtShininess.gridy = 0;
		getContentPane().add(this.txtShininess, gbc_txtShininess);

		this.setVisible(true);
	}

	private class JVecortField extends JPanel {
		public JSpinner x = new JSpinner();
		public JSpinner y = new JSpinner();
		public JSpinner z = new JSpinner();

		public JVecortField(Vector3f vector, String name) {
			FlowLayout flow = new FlowLayout();
			this.setLayout(flow);

			SpinnerNumberModel modelX = new SpinnerNumberModel(vector.x, 0, 100, 0.01);
			SpinnerNumberModel modelY = new SpinnerNumberModel(vector.y, 0, 100, 0.01);
			SpinnerNumberModel modelZ = new SpinnerNumberModel(vector.z, 0, 100, 0.01);

			this.x.setModel(modelX);
			this.y.setModel(modelY);
			this.z.setModel(modelZ);

			JTextField t = new JTextField(name);
			t.setEditable(false);
			this.add(t);
			this.add(this.x);
			this.add(this.y);
			this.add(this.z);

			this.x.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					Configurator.this.matChange();
					System.out.println(Configurator.this.getBounds());
				}
			});
			this.z.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					Configurator.this.matChange();
				}
			});
			this.y.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					Configurator.this.matChange();
				}
			});
		}

		public Vector3f getVector() {
			return new Vector3f(new Float((double) this.x.getModel().getValue()).floatValue(),
					new Float((double) this.y.getModel().getValue()).floatValue(), new Float((double) this.z.getModel().getValue()).floatValue());
		}
	}

	protected void matChange() {
		Material m = new Material(this.txtEmission.getVector(),
				this.txtAmbient.getVector(),
				this.txtDiffuse.getVector(),
				this.txtSpecular.getVector(),
				new Float((double)(this.txtShininess.getModel().getValue())).floatValue());
		this.ren.setMaterial(m);
	}
}