import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Purpose:			Generate a random solar system with ray casting lines that collide with orbiting planets			
 * 					Esc = Exit program, P = Pause, R = Show rays, G = Gen more planets -change this to regen solar system
 */
public class SolarSystemWithRayCollisions extends JPanel implements KeyListener {
    private final int centerX = 900, centerY = 500; //central sun position
    private final ArrayList<Planet> planets = new ArrayList<>(); //list of planets
    private final Random random = new Random(); //randomizer for planets
    private final int rayCount = 360; //number of rays (one per degree) 360
	private int numplanets = 0;
	private boolean paused = false;
	private boolean showrays = true;

    public SolarSystemWithRayCollisions() {
		genplanets();
    	Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (!paused) {
					repaint();
				}
			}
		}, 16, 16);
    }
	
	void genplanets() {
		numplanets = random.nextInt(12)+1;
        for (int i = 0; i < numplanets; i++) { //generate random planets
            planets.add(new Planet(
                50 + i * 40, //orbit radius
                random.nextDouble() * 2 * Math.PI, //initial angle
                5 + random.nextInt(30), //size
                new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)), //random color
                0.0001 + random.nextDouble() * 0.001 //speed 0.05
            ));
        }
	}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
		g.setColor(new Color(0, 0, 0, 250)); //black background
		g.fillRect(0, 0, getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.YELLOW); //draw central sun
        g2d.fillOval(centerX - 30, centerY - 30, 60, 60);

        //draw light rays
		if (showrays) {
			g2d.setColor(new Color(255, 255, 0, 150)); //semi-transparent light rays
			for (int i = 0; i < rayCount; i++) {
				double angle = Math.toRadians(i); //current ray angle
				double rayX = centerX + Math.cos(angle) * 2000; //long ray (extend far out)
				double rayY = centerY + Math.sin(angle) * 2000;

				Point collisionPoint = getRayCollision(centerX, centerY, rayX, rayY); //check for collisions
				if (collisionPoint != null) {
					g2d.drawLine(centerX, centerY, collisionPoint.x, collisionPoint.y); //draw up to collision point
				} else {
					g2d.drawLine(centerX, centerY, (int) rayX, (int) rayY); //no collision, draw full ray
				}
			}

			g2d.setColor(new Color(255, 255, 0, 150)); //draw more light rays, prob a better way but lazy
			for (int i = 0; i < rayCount; i++) {
				double angle = Math.toRadians(i)-100;
				double rayX = centerX + Math.cos(angle) * 2000;
				double rayY = centerY + Math.sin(angle) * 2000;

				Point collisionPoint = getRayCollision(centerX, centerY, rayX, rayY);
				if (collisionPoint != null) {
					g2d.drawLine(centerX, centerY, collisionPoint.x, collisionPoint.y);
				} else {
					g2d.drawLine(centerX, centerY, (int) rayX, (int) rayY);
				}
			}
			g2d.setColor(new Color(255, 255, 0, 150));
			for (int i = 0; i < rayCount; i++) {
				double angle = Math.toRadians(i)-150;
				double rayX = centerX + Math.cos(angle) * 2000;
				double rayY = centerY + Math.sin(angle) * 2000;

				Point collisionPoint = getRayCollision(centerX, centerY, rayX, rayY);
				if (collisionPoint != null) {
					g2d.drawLine(centerX, centerY, collisionPoint.x, collisionPoint.y);
				} else {
					g2d.drawLine(centerX, centerY, (int) rayX, (int) rayY);
				}
			}
			g2d.setColor(new Color(255, 255, 0, 150));
			for (int i = 0; i < rayCount; i++) {
				double angle = Math.toRadians(i)-200;
				double rayX = centerX + Math.cos(angle) * 2000;
				double rayY = centerY + Math.sin(angle) * 2000;

				Point collisionPoint = getRayCollision(centerX, centerY, rayX, rayY);
				if (collisionPoint != null) {
					g2d.drawLine(centerX, centerY, collisionPoint.x, collisionPoint.y);
				} else {
					g2d.drawLine(centerX, centerY, (int) rayX, (int) rayY);
				}
			}
			g2d.setColor(new Color(255, 255, 0, 150));
			for (int i = 0; i < rayCount; i++) {
				double angle = Math.toRadians(i)-175;
				double rayX = centerX + Math.cos(angle) * 2000;
				double rayY = centerY + Math.sin(angle) * 2000;

				Point collisionPoint = getRayCollision(centerX, centerY, rayX, rayY);
				if (collisionPoint != null) {
					g2d.drawLine(centerX, centerY, collisionPoint.x, collisionPoint.y);
				} else {
					g2d.drawLine(centerX, centerY, (int) rayX, (int) rayY);
				}
			}
			g2d.setColor(new Color(255, 255, 0, 150));
			for (int i = 0; i < rayCount; i++) {
				double angle = Math.toRadians(i)-50;
				double rayX = centerX + Math.cos(angle) * 2000;
				double rayY = centerY + Math.sin(angle) * 2000;

				Point collisionPoint = getRayCollision(centerX, centerY, rayX, rayY);
				if (collisionPoint != null) {
					g2d.drawLine(centerX, centerY, collisionPoint.x, collisionPoint.y);
				} else {
					g2d.drawLine(centerX, centerY, (int) rayX, (int) rayY);
				}
			}
		}
        //render planets
        for (Planet planet : planets) {
            planet.updatePosition(); //update planet's position
            g2d.setColor(planet.color);
            g2d.fillOval((int) planet.x - planet.size / 2, (int) planet.y - planet.size / 2, planet.size, planet.size);
			//draw shadow
            double dx = planet.x - centerX; //delta x&y between planet and sun
            double dy = planet.y - centerY;
            double distance = Math.sqrt(dx * dx + dy * dy); //distance between sun and planet
            double shadowFactor = 7.0; //length multiplier for shadow

            //calculate shadow position
            double shadowX = planet.x + shadowFactor * dx / distance;
            double shadowY = planet.y + shadowFactor * dy / distance;

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2d.setColor(Color.BLACK);
            g2d.fillOval((int) shadowX - planet.size / 2, (int) shadowY - planet.size / 2, planet.size, planet.size);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));	
		}
    }
	
    //check for ray collision with planets
    private Point getRayCollision(double rayX1, double rayY1, double rayX2, double rayY2) {
        for (Planet planet : planets) {
            double dx = rayX2 - rayX1;
            double dy = rayY2 - rayY1;

            double fx = rayX1 - planet.x; //vector from ray start to planet center
            double fy = rayY1 - planet.y;

            double a = dx * dx + dy * dy; //ray direction vector magnitude squared
            double b = 2 * (fx * dx + fy * dy); //linear term
            double c = (fx * fx + fy * fy) - planet.size * planet.size / 4; //planet radius squared

            double discriminant = b * b - 4 * a * c; //quadratic discriminant
            if (discriminant >= 0) {
                discriminant = Math.sqrt(discriminant);

                double t1 = (-b - discriminant) / (2 * a); //find the nearest collision point
                if (t1 >= 0 && t1 <= 1) { //ensure collision point is within the ray's bounds
                    return new Point(
                        (int) (rayX1 + t1 * dx),
                        (int) (rayY1 + t1 * dy)
                    );
                }
            }
        }
        return null; //no collision
    }

    static class Planet {
        double orbitRadius, angle, speed;
        int size;
        double x, y;
        Color color;

        Planet(double orbitRadius, double angle, int size, Color color, double speed) {
            this.orbitRadius = orbitRadius;
            this.angle = angle;
            this.size = size;
            this.color = color;
            this.speed = speed;
        }

        void updatePosition() {
            angle += speed; //increment angle for orbiting
            x = 900 + orbitRadius * Math.cos(angle); //calculate x based on orbit
            y = 500 + orbitRadius * Math.sin(angle); //calculate y
        }
    }
	
	@Override
	public void keyPressed(KeyEvent e) { //key controls
	    switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
				showrays = !showrays;
				if (showrays) return;
			break;
			
            case KeyEvent.VK_P:
				paused = !paused;
				if (paused) return;
			break;
			
			case KeyEvent.VK_G:
				genplanets();
			break;
			
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
			break;	
        }
	}
	
	@Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Solar System with ray casting sun");
        SolarSystemWithRayCollisions panel = new SolarSystemWithRayCollisions();
        frame.add(panel);
        frame.setSize(1920, 1040); //set to your monitor res
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(panel);
        frame.setVisible(true);
    }
}