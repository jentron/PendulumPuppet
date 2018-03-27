import processing.core.*;

public class Pendulum {
	Object origin; // FIXME: merge
	PVector currentOrigin; 
	PVector originOffset; // FIXME: merge
	float armLength;
	PVector position; // FIXME: merge
	float angle;

	float aVelocity;
	float aAcceleration;
	float damping;
	float ballRadius;
	boolean dragging;
	float offset;
	PendulumPuppet p;
	String label;
	
	Pendulum(PendulumPuppet p, String label, Object origin, float originx, float originy, float armLength, float angle) {
		this.label = label;
	    this.origin = origin;
	    this.currentOrigin = new PVector();
	    this.originOffset = new PVector(originx, originy);
	    this.armLength = armLength;
	    this.position = new PVector();
	    this.angle = angle;
	    
	    this.aVelocity = 0.0f;
	    this.aAcceleration = 0.0f;
	    this.damping = 0.995f;
	    this.ballRadius = 25.0f;      
	    this.dragging = false;
	    this.offset   = angle;
	    this.p = p;
	};
	
	void go() {
	    this.update();
	    this.display();
	};
	
	void update() {
	    // As long as we aren't dragging the pendulum, let it swing!
	    if (!this.dragging && p.pendulum) {
	        // Arbitrary constant
	        float gravity = 0.4f;
	        // Calculate acceleration
	        this.aAcceleration = (float) ((-1 * gravity / this.armLength) * Math.sin(this.angle-this.offset));
	        // Increment velocity
	        this.aVelocity += this.aAcceleration;
	        // Arbitrary damping
	        this.aVelocity *= this.damping;
	        // Increment angle
	        this.angle += this.aVelocity;
	    }
	};
	
	void updateOrigin(){
	    if (this.origin instanceof PVector) {
	        this.currentOrigin=(PVector)this.origin;
	    } else {
	    	float tangle = ((Pendulum) this.origin).angle - this.offset;
	    	PVector a = ((Pendulum) this.origin).position.copy();
	    	PVector b = this.originOffset.copy();
	    	b.rotate(-tangle);
	    	a.add(b);
	        this.currentOrigin.set(a);
	    }		
	}
	void display() {
		updateOrigin();
	    this.position.set(
	      (float) (this.armLength * Math.sin(this.angle)),
	      (float) (this.armLength * Math.cos(this.angle)));
	    this.position.add(this.currentOrigin);
	    p.stroke(0f, 0f, 0f);
	    p.strokeWeight(3f);
	    p.line(this.currentOrigin.x, this.currentOrigin.y, this.position.x, this.position.y);
	    p.fill(224, 194, 134);
	    if (this.dragging) {
	    	p.fill(143, 110, 44);
	    }
	    p.ellipse(this.position.x, this.position.y, this.ballRadius, this.ballRadius);
	};
	void handleClick(int mx, int my) {
	    float d = PApplet.dist(mx, my, this.position.x, this.position.y);
	    if (d < this.ballRadius) {
	        this.dragging = true;
	    }
	};

	void stopDragging() {
	    this.aVelocity = 0;
	    this.dragging = false;
	};
	
	void handleDrag(int mx, int my) {
	    if (this.dragging) {
	      PVector diff = PVector.sub(this.currentOrigin, new PVector(mx, my));
	      this.angle = (float) (Math.atan2(-1*diff.y, diff.x) - Math.toRadians(90));
	    }
	};
}
