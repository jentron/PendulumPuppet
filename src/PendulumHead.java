import processing.core.PApplet;
import processing.core.PVector;

public class PendulumHead extends Pendulum {

	PendulumHead(PendulumPuppet p, String label, Object origin, float offsetx, float offsety, float armLength, float angle) {
		super(p, label, origin, offsetx, offsety, armLength, angle);
		// TODO Auto-generated constructor stub
	}

	void display() {
		updateOrigin();
		this.position.set((float)(this.armLength * Math.sin(this.angle)), (float)(this.armLength * Math.cos(this.angle)));
		this.position.add(this.currentOrigin);
		p.stroke(0, 0, 0);
		p.strokeWeight(3);
		p.line(this.currentOrigin.x, this.currentOrigin.y, this.position.x, this.position.y);
		p.fill(224, 194, 134);
		if (this.dragging) {
			p.fill(143, 110, 44);
		}
		p.rect(this.position.x, this.position.y, 50, 64, 30); // head
	}

}
