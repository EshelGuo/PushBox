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
 * <br>createBy guoshiwen
 * <br>createTime: 2019/10/30 20:53
 * <br>desc: TODO
 */
public class Step {

	public static final char LEFT = 'L';
	public static final char UP = 'U';
	public static final char RRIGHT = 'R';
	public static final char DOWN = 'D';

	private Grid boy, box;

	private int boyX;
	private int boyY;

	private int boxX;
	private int boxY;

	private int boyXAfter;
	private int boyYAfter;

	private int boxXAfter;
	private int boxYAfter;

	Step last;
	Step next;

	private char type;

	public Step(Grid boy) {
		this.boy = boy;
		boyX = boy.getX();
		boyY = boy.getY();
	}

	public Step(Grid boy, Grid box) {
		this(boy);
		this.box = box;
		boxX = box.getX();
		boxY = box.getY();
	}

	public void after(char type){
		this.type = type;
		if(boy != null){
			boyXAfter = boy.getX();
			boyYAfter = boy.getY();
		}

		if(box != null){
			boxXAfter = box.getX();
			boxYAfter = box.getY();
		}
	}

	public boolean backward(){
		boolean isSuccess = false;
		if (boy != null) {
			if(!boy.checkPoint(boyX, boyY)){
				isSuccess = true;
			}
			boy.move(boyX, boyY);
		}

		if(box != null){
			box.move(boxX, boxY);
		}
		return isSuccess;
	}

	public boolean forward() {
		boolean isSuccess = false;
		if (boy != null) {
			if(!boy.checkPoint(boyXAfter, boyYAfter)){
				isSuccess = true;
			}
			boy.move(boyXAfter, boyYAfter);
		}

		if(box != null){
			box.move(boxXAfter, boxYAfter);
		}
		return isSuccess;
	}

	public char getType() {
		return type;
	}
}
