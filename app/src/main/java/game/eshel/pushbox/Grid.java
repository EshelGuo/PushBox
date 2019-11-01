/*
 * Copyright (c) 2019 Eshel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package game.eshel.pushbox;

/**
 * <br>createBy Eshel
 * <br>createTime: 2019/10/30 18:01
 * <br>desc: 游戏基础元素
 */
public class Grid {

	private Type mType;

	private int x;
	private int y;

	private Grid(Type type, int x, int y) {
		mType = type;
		this.x = x;
		this.y = y;
	}

	public void move(int x, int y){
		this.x = x;
		this.y = y;
	}

	public void up(){
		y-=1;
	}

	public int getUp(){
		return y - 1;
	}

	public void down(){
		y+=1;
	}

	public int getDown(){
		return y + 1;
	}

	public void left(){
		x-=1;
	}

	public int getLeft(){
		return x - 1;
	}

	public void right(){
		x += 1;
	}

	public int getRight(){
		return x + 1;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Type getType() {
		return mType;
	}

	public boolean checkPoint(int x, int y){
		return getX() == x && getY() == y;
	}

	public static class Factory{
		private static final Grid BOY = new Grid(Type.BOY, -1, -1);

		public static Grid createBox(int x, int y){
			return new Grid(Type.BOX, x, y);
		}

		public static Grid createWall(int x, int y){
			return new Grid(Type.WALL, x, y);
		}

		public static Grid createGoal(int x, int y){
			return new Grid(Type.GOAL, x, y);
		}

		public static Grid getBoy(int x, int y){
			BOY.x = x;
			BOY.y = y;
			return BOY;
		}
	}
}
