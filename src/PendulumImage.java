import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class PendulumImage extends Pendulum {
	PImage img;
	float imgx;
	float imgy;

	PendulumImage(PendulumPuppet p, String label, Object origin, float originx, float originy, float armLength, float angle, String imageName,
			float damping, float imgx, float imgy) {
		super(p, label, origin, originx, originy, armLength, angle);
		// TODO Auto-generated constructor stub
		this.img = p.loadImage(imageName);
		this.imgx = imgx;
		this.imgy = imgy;
		this.damping = damping;
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
		p.pushMatrix();
		p.translate(this.position.x, this.position.y);
		p.rotate(-this.angle-this.offset);
		p.image(this.img, this.imgx, this.imgy);
		p.ellipse(0, 0, this.ballRadius, this.ballRadius);
		p.popMatrix();
		p.noFill();
		p.ellipse(this.currentOrigin.x, this.currentOrigin.y, this.ballRadius, this.ballRadius);
	}

}
