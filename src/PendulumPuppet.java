import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class PendulumPuppet extends PApplet {
	float limbLength = 128;
	PImage bodyImage;
	PVector puppetCenter;
	float spinAngle = 0;
	boolean pendulum=true;

	List<Pendulum> limbs = new ArrayList<Pendulum>();

	public static void main(String[] args) {
		PApplet.main("PendulumPuppet");
	}

	public void settings() {
		size(800, 1000, P2D);
	}

	private Pendulum findPendulumByLabel(String myLabel) {
		return limbs.stream().filter(limb -> limb.label.equals(myLabel)).findFirst().get();
	}

	public void setup() {
		// angleMode = "radians";
		rectMode(CENTER);
		imageMode(CENTER);
		puppetCenter = new PVector(width / 2f, 240f);
		// bodyImage = loadImage("body.png");
		// Step 1: Use loops to to display and handle mouse events for ALL
		// Step 2: Connect to an origin
		// Step 3: Provide default angle
		loadData();

	}

	void loadData() {
		// Load the JSON file and grab the array.
		JSONObject json = loadJSONObject("data.json");
		JSONArray limbData = json.getJSONArray("limbs");

		for (int i = 0; i < limbData.size(); i++) {

			// Iterate through the array, grabbing each JSON object one at a
			// time.
			JSONObject limb = limbData.getJSONObject(i);

			// Get a position object
			JSONObject position = limb.getJSONObject("position");
			// Get (x,y) from JSON object "position"
			int x = position.getInt("x");
			int y = position.getInt("y");

			JSONObject imagepos = limb.getJSONObject("imagepos");
			// Get (x,y) from JSON object "position"
			int imgx = imagepos.getInt("x");
			int imgy = imagepos.getInt("y");

			JSONObject origin = limb.getJSONObject("origin");
			// Get (x,y) from JSON object "position"
			int originx = origin.getInt("x");
			int originy = origin.getInt("y");
			// Get diameter and label
			float length = limb.getFloat("length");
			float offset = limb.getFloat("offset");
			float damping = limb.getFloat("damping");
			String image = limb.getString("image");
			String label = limb.getString("label");
			String parent = limb.getString("parent");
			if (parent == null) {
				limbs.add(new PendulumImage(this, label, new PVector(puppetCenter.x + x, puppetCenter.y + y), originx,
						originy, length, offset, image, damping, imgx, imgy));
			} else {
				limbs.add(new PendulumImage(this, label, findPendulumByLabel(parent), originx, originy, length, offset,
						image, damping, imgx, imgy));
			}

		}
	}

	public void draw() {
		background(0, 168, 107);

		// Draw the body
		// image(bodyImage, puppetCenter.x, puppetCenter.y + 180 - 40 );

		for (Pendulum p : limbs) {
			p.go();
		}
//		limbs.get(1).angle = spinAngle;
//		spinAngle -= (float) (Math.PI / 20f);
//		if(spinAngle > 2*Math.PI) spinAngle += 2*Math.PI;
//		strokeWeight(4);
//		line(puppetCenter.x - 89, puppetCenter.y, puppetCenter.x + 89, puppetCenter.y);// shoulders
//		line(puppetCenter.x, puppetCenter.y, puppetCenter.x, puppetCenter.y + 266); // spine
//		line(puppetCenter.x - 52, puppetCenter.y + 266, puppetCenter.x + 52, puppetCenter.y + 266); // hips

	}

	public void mousePressed() {
		for (Pendulum p : limbs) {
			p.handleClick(mouseX, mouseY);
		}
	};

	public void mouseDragged() {
		for (Pendulum p : limbs) {
			p.handleDrag(mouseX, mouseY);
		}
	};

	public void mouseOut() {
		for (Pendulum p : limbs) {
			p.stopDragging();
		}
	};

	public void mouseReleased() {
		for (Pendulum p : limbs) {
			p.stopDragging();
		}
	};

	public void keyPressed() {
		if (key == 'R') {
			noLoop();
			limbs.clear();
			loadData();
			loop();
		}
		if (key == 'p') {
			pendulum = !pendulum;
		}	}

}
