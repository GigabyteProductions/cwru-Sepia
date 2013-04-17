/**
 *  Strategy Engine for Programming Intelligent Agents (SEPIA)
    Copyright (C) 2012 Case Western Reserve University

    This file is part of SEPIA.

    SEPIA is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SEPIA is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with SEPIA.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.cwru.sepia.agent.visual;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

public abstract class DrawingStrategy {

	protected Rectangle bounds;
	
	private static DrawingStrategy tree;
	private static DrawingStrategy mine;
	private static DrawingStrategy revealedtree;
	private static DrawingStrategy revealedmine;
	private static DrawingStrategy fog;
	private static Map<Character,DrawingStrategy> letters;
	private static DrawingStrategy selected;
	private static DrawingStrategy infoBox; 
	
	String info = null;
	
	public void setInfo(String info) { this.info = info;}
	
	public abstract void draw(Graphics g, int tlx, int tly);
	
	public static DrawingStrategy revealedTreeGraphic() {
		if(revealedtree == null)
		{
			revealedtree = new DrawingStrategy() {
				@Override
				public void draw(Graphics g, int tlx, int tly) {
					Color previous = g.getColor();
					
					for (int[] xy : new int[][]{{3,5},{10, 13},{18, 4},{20, 20},{27,16}})
					{
						g.setColor(new Color(0,127,0));
						Polygon top = new Polygon();
						top.addPoint(tlx + xy[0], tly + xy[1]);
						top.addPoint(tlx + xy[0]+3, tly + xy[1]+5);
						top.addPoint(tlx + xy[0]-3, tly + xy[1]+5);
						g.fillPolygon(top);
						g.setColor(new Color(0xA5,0x2A,0x2A));
						g.fillRect(tlx + xy[0], tly + xy[1]+6, 1, 1);
						Font old = g.getFont();
						g.setColor(Color.pink);
						g.setFont(new Font(old.getFamily(),old.getStyle(),5));
						g.drawChars(new char[]{'?'}, 0, 1, tlx + xy[0]-1, tly+xy[1]+10);
						g.setFont(old);
					}
					g.setColor(previous);
				}
			};
		}
		return revealedtree;
	}
	public static DrawingStrategy revealedMineGraphic() {
		if(revealedmine == null)
		{
			revealedmine = new DrawingStrategy() {
				@Override
				public void draw(Graphics g, int tlx, int tly) {
					Color previous = g.getColor();
					
					for (int[] xy : new int[][]{{3,5},{10, 13},{18, 4},{20, 20},{27,16}})
					{
						g.setColor(new Color(0xFF,0xFF,0x33));
						g.fillRect(tlx+xy[0]-1, tly+xy[1]-1, 3, 3);
						Font old = g.getFont();
						g.setColor(Color.pink);
						g.setFont(new Font(old.getFamily(),old.getStyle(),5));
						g.drawChars(new char[]{'?'}, 0, 1, tlx + xy[0]-1, tly+xy[1]+10);
						g.setFont(old);
					}
					g.setColor(previous);
				}
			};
		}
		return revealedmine;
	}

	
	/**
	 * graphic for the fog of war
	 * @return
	 */
	public static DrawingStrategy fogGraphic() {
		if(fog == null)
		{
			fog = new DrawingStrategy() {
				@Override
				public void draw(Graphics g, int tlx, int tly) {
					Color previous = g.getColor();
					g.setColor(new Color(0x00,0x00,0x00,0x80));					
					g.fillRect(tlx, tly, 32, 32);
					g.setColor(previous);
				}
			};
		}
		return fog;
	}
	public static DrawingStrategy charGraphic(char c) {
		if(letters == null)
			letters = new HashMap<Character,DrawingStrategy>();
		DrawingStrategy strategy = letters.get(c);
		if(strategy == null)
		{
			strategy = new DrawingStrategy() {
				private char c;
				public DrawingStrategy setChar(char c) {
					this.c = c;
					return this;
				}
				@Override
				public void draw(Graphics g, int tlx, int tly) {
					Font old = g.getFont();
					g.setFont(new Font(old.getFamily(),old.getStyle(),24));
					g.drawChars(new char[]{c}, 0, 1, tlx+8, tly+24);
					g.setFont(old);
				}				
			}.setChar(c);
			letters.put(c, strategy);
		}
		return strategy;
	}
	
	public static DrawingStrategy selectedGraphic() {
		if(selected == null)
		{
			selected = new DrawingStrategy() {
				@Override
				public void draw(Graphics g, int tlx, int tly) {
					Color previous = g.getColor();
					g.setColor(new Color(0xFF,0xFF,0xFF));	
					g.drawRect(tlx, tly, 32, 32);
					g.setColor(previous);
				}
			};
		}
		return selected;
	}
	
	public static DrawingStrategy infoGraphic() {
		if(infoBox == null) 
		{
			infoBox = new DrawingStrategy() {
				@Override
				public void draw(Graphics g, int tlx, int tly) {
					Color previous = g.getColor();
					g.setColor(new Color(0xFF,0x00,0x00));
					g.drawRect(tlx, tly, 32, 32);
					g.setColor(new Color(0x00,0x00,0x00));
					info = info.trim();
					String[] infos = info.split("\n");
					int linenum = infos.length;
					for(int i=0; i<linenum; i++) {
						int height = (i+1)-linenum;
						if(tly==0)
							height = i+1;
						g.drawChars(infos[i].toCharArray(), 0, infos[i].length(), tlx, tly+height*12);
					}
					g.setColor(previous);
				}
			};
		}
		return infoBox;
	}
}







