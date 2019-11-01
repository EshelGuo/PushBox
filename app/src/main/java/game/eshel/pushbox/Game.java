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

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static game.eshel.pushbox.Step.DOWN;
import static game.eshel.pushbox.Step.LEFT;
import static game.eshel.pushbox.Step.RRIGHT;
import static game.eshel.pushbox.Step.UP;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2019/10/30 18:32
 * <br>desc: 游戏资源管理
 */
@SuppressWarnings("WeakerAccess")
public class Game {

	private static final String TAG = "Game_";

	public static final int MAX_LEVEL = 27;

	private static final char man 		= '@';
	private static final char manOnGoal = '+';
	private static final char box 		= '$';
	private static final char boxOnGoal = '*';
	private static final char wall 		= '#';
	private static final char goal 		= '.';
	private static final char floor		= '-';

	private int level;
	private String levelTitle;
	private String levelAuthor;

	private List<Grid> walls;
	private List<Grid> goals;
	private List<Grid> boxs;
	private Grid boy;

	private WinListener mWinListener;
	private History mHistory;
	private int mSize;

	public Game(int level) {
		this.level = level;
		walls = new ArrayList<>(20);
		boxs = new ArrayList<>(8);
		goals = new ArrayList<>(8);
		mHistory = new History();
	}

	public int getSize() {
		return mSize;
	}

	public void setWinListener(WinListener listener){
		mWinListener = listener;
	}

	public boolean load(Context context, String fileName){
		try {
			InputStream is = context.getAssets().open(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			loadInternal(br);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void draw(Action<Grid> action){
		for (Grid grid : walls) {
			action.onAction(grid);
		}

		for (Grid grid : goals) {
			action.onAction(grid);
		}

		for (Grid grid : boxs) {
			action.onAction(grid);
		}

		action.onAction(boy);
	}

	public List<Grid> walls(){
		return walls;
	}

	public List<Grid> goals(){
		return goals;
	}

	public List<Grid> boxs(){
		return boxs;
	}

	public Grid boy(){
		return boy;
	}

	private void loadInternal(BufferedReader br) throws IOException {
		String line;
		int x;
		int y = 0;

		int maxWidth = 1;
		int maxHeight = 1;

		while ((line = br.readLine()) != null){
			try {
				if(line.startsWith("Title")){
					levelTitle = line.split(":")[1];
					continue;
				}
				if(line.startsWith("Author")){
					levelAuthor = line.split(":")[1];
					continue;
				}
			}catch (Exception e){
				e.printStackTrace();
			}
			y++;
			char[] chars = line.toCharArray();
			maxWidth = Math.max(maxWidth, chars.length);
			for (int i = 0; i < chars.length; i++) {
				x = i + 1;
				char c = chars[i];
				switch (c){
					case man:
						boy = Grid.Factory.getBoy(x, y);
						break;
					case manOnGoal:
						boy = Grid.Factory.getBoy(x, y);
						goals.add(Grid.Factory.createGoal(x, y));
						break;
					case box:
						boxs.add(Grid.Factory.createBox(x, y));
						break;
					case boxOnGoal:
						boxs.add(Grid.Factory.createBox(x, y));
						goals.add(Grid.Factory.createGoal(x, y));
						break;
					case wall:
						walls.add(Grid.Factory.createWall(x, y));
						break;
					case goal:
						goals.add(Grid.Factory.createGoal(x, y));
						break;
					case floor:
						break;
				}
			}
			Log.i(TAG, line);
		}

		maxHeight = y;

		mSize = Math.max(maxWidth, maxHeight);

	}

	public void up(Location location) {
		Grid next = location.getGridIgnoreGoal(this, boy.getX(), boy.getUp());
		if(next == null){
			Step step = mHistory.saveBefore(boy);
			boy.up();
			mHistory.saveAfter(step, UP);
		}else if(next.getType() == Type.BOX){
			Grid boxNext = location.getGridIgnoreGoal(this, next.getX(), next.getUp());
			if(boxNext == null){
				Step step = mHistory.saveBefore(boy, next);
				boy.up();
				next.up();
				mHistory.saveAfter(step, UP);
			}
		}
		checkWin();
	}

	public void left(Location location) {
		Grid next = location.getGridIgnoreGoal(this, boy.getLeft(), boy.getY());
		if(next == null){
			Step step = mHistory.saveBefore(boy);
			boy.left();
			mHistory.saveAfter(step, LEFT);
		}else if(next.getType() == Type.BOX){
			Grid boxNext = location.getGridIgnoreGoal(this, next.getLeft(), next.getY());
			if(boxNext == null){
				Step step = mHistory.saveBefore(boy, next);
				boy.left();
				next.left();
				mHistory.saveAfter(step, LEFT);
			}
		}
		checkWin();
	}

	public void right(Location location) {
		Grid next = location.getGridIgnoreGoal(this, boy.getRight(), boy.getY());
		if(next == null){
			Step step = mHistory.saveBefore(boy);
			boy.right();
			mHistory.saveAfter(step, RRIGHT);
		}else if(next.getType() == Type.BOX){
			Grid boxNext = location.getGridIgnoreGoal(this, next.getRight(), next.getY());
			if(boxNext == null){
				Step step = mHistory.saveBefore(boy, next);
				boy.right();
				next.right();
				mHistory.saveAfter(step, RRIGHT);
			}
		}
		checkWin();
	}

	public void down(Location location) {
		Grid next = location.getGridIgnoreGoal(this, boy.getX(), boy.getDown());
		if(next == null){
			Step step = mHistory.saveBefore(boy);
			boy.down();
			mHistory.saveAfter(step, DOWN);
		}else if(next.getType() == Type.BOX){
			Grid boxNext = location.getGridIgnoreGoal(this, next.getX(), next.getDown());
			if(boxNext == null){
				Step step = mHistory.saveBefore(boy, next);
				boy.down();
				next.down();
				mHistory.saveAfter(step, DOWN);
			}
		}
		checkWin();
	}

	public History getHistory() {
		return mHistory;
	}

	public void backward(){
		mHistory.backward();
	}

	public void forward(){
		mHistory.forward();
	}

	public int getSteps(){
		return mHistory.getSteps();
	}

	public void setStepChangeListener(History.StepChangeListener listener){
		mHistory.setStepChangeListener(listener);
	}

	public String getLevelTitle() {
		return levelTitle;
	}

	public String getLevelAuthor() {
		return levelAuthor;
	}

	private void checkWin(){
		for (Grid goal : goals) {
			boolean match = false;
			for (Grid box : boxs) {
				if(goal.checkPoint(box.getX(), box.getY())){
					match = true;
					break;
				}
			}
			if(!match){
				return;
			}
		}
		if(mWinListener != null)
			mWinListener.win();
	}
	public interface WinListener{
		void win();
	}
}
