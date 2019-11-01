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

import android.app.ActivityOptions;
import android.graphics.Rect;
import android.support.annotation.NonNull;

/**
 * <br>createBy Eshel
 * <br>createTime: 2019/10/30 17:42
 * <br>desc: 坐标转换类
 * 游戏坐标系: 左上为1, 1
 */
@SuppressWarnings("WeakerAccess")
public class Location {
	private int mGameSizePX;
	private int singleLineGridNumber = 20;
	private int singleGridSizePX;

	public Location() {
	}

	public void setSingleLineGridNumber(int singleLineGridNumber) {
		if(singleLineGridNumber < 20)
			singleLineGridNumber = 20;
		this.singleLineGridNumber = singleLineGridNumber;
		singleGridSizePX = mGameSizePX / singleLineGridNumber;
	}

	public void update(int gameSizePX){
		mGameSizePX = gameSizePX;
		singleGridSizePX = gameSizePX / singleLineGridNumber;
	}


	public Rect getLocationByGrid(Grid grid) {
		int x = grid.getX();
		int y = grid.getY();

		int left = (x - 1) * singleGridSizePX;
		int top = (y - 1) * singleGridSizePX;
		int right = left + singleGridSizePX;
		int bottom = top + singleGridSizePX;

		return new Rect(left, top, right, bottom);
	}

	public Grid getGridIgnoreGoal(@NonNull Game game, int x, int y){
		for (Grid grid : game.boxs()) {
			if(grid.checkPoint(x, y)){
				return grid;
			}
		}

		for (Grid grid : game.walls()) {
			if(grid.checkPoint(x, y)){
				return grid;
			}
		}
		return null;
	}

	public Grid[] getGridByLocation(@NonNull Game game, int xPX, int yPX){
		int x = xPX / singleGridSizePX + (xPX % singleGridSizePX != 0 ? 1 : 0);
		int y = yPX / singleGridSizePX + (yPX % singleGridSizePX != 0 ? 1: 0);

		Grid goal = null;
		Grid boy = null;
		Grid box = null;
		Grid wall = null;

		for (Grid grid : game.goals()) {
			if(grid.checkPoint(x, y)){
				goal = grid;
				break;
			}
		}

		if(game.boy().checkPoint(x, y)){
			boy = game.boy();
		}

		if(boy != null){
			if(goal == null)
				return new Grid[]{boy};
			else
				return new Grid[]{boy, goal};
		}

		for (Grid grid : game.boxs()) {
			if(grid.checkPoint(x, y)){
				box = grid;
				break;
			}
		}

		if(box != null){
			if(goal == null)
				return new Grid[]{box};
			else
				return new Grid[]{box, goal};
		}

		for (Grid grid : game.walls()) {
			if(grid.checkPoint(x, y)){
				wall = grid;
				break;
			}
		}

		if(wall != null)
			return new Grid[]{wall};

		return null;
	}
}
