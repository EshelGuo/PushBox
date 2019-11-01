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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import game.eshel.pushbox.style.AndroidStyle;
import game.eshel.pushbox.style.Style;

/**
 * <br>createBy Eshel
 * <br>createTime: 2019/10/30 17:15
 * <br>desc: 推箱子游戏
 */
public class MainGameView extends View implements Game.WinListener {

	private Location mLocation;
	private Game mGame;
	private DrawImpl mGridDraw;
	private Style mStyle;

	public MainGameView(Context context) {
		this(context, null);
	}

	public MainGameView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MainGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(context);
	}

	private void initialize(Context context) {
		setBackgroundColor(Color.BLACK);
		mStyle = new AndroidStyle();
		mLocation = new Location();
		mGridDraw = new DrawImpl(context, mStyle, mLocation);
	}

	public Game newGame(int level) {
		mGame = new Game(level);
		mGame.setWinListener(this);
		boolean isSuccess = mGame.load(getContext(), "Level_" + level + ".txt");
		if (!isSuccess)
			return null;
		mLocation.setSingleLineGridNumber(mGame.getSize());

		post(new Runnable() {
			@Override
			public void run() {
				invalidate();
			}
		});
		return mGame;
	}

	public void up(){
		mGame.up(mLocation);
		invalidate();
	}

	public void left(){
		mGame.left(mLocation);
		invalidate();
	}

	public void right(){
		mGame.right(mLocation);
		invalidate();
	}

	public void down(){
		mGame.down(mLocation);
		invalidate();
	}

	public void backward(){
		mGame.backward();
		invalidate();
	}

	public void forward(){
		mGame.forward();
		invalidate();
	}

	public int getSteps(){
		return mGame.getSteps();
	}

	public void setStepChangeListener(History.StepChangeListener listener){
		mGame.setStepChangeListener(listener);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int size = Math.min(width, height);
		mLocation.update(size);
		super.onMeasure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mGridDraw.update(canvas, mStyle);
		mGame.draw(mGridDraw);
	}

	@Override
	public void win() {
		new AlertDialog.Builder(getContext())
				.setTitle("☺ 恭喜, 你赢了!!")
				.setPositiveButton("下一关", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				})
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				})
				.show();
	}

	private static class DrawImpl implements Action<Grid> {
		private Canvas mCanvas;
		private Style mStyle;
		private Location mLocation;
		private Context mContext;
		private Paint mPaint;

		private void update(Canvas canvas, Style style) {
			this.mCanvas = canvas;
			mStyle = style;
		}

		private void update(Canvas canvas) {
			this.mCanvas = canvas;
		}

		private DrawImpl(Context context, Style style, Location location) {
			mContext = context;
			mStyle = style;
			mLocation = location;
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
		}

		@Override
		public void onAction(Grid grid) {
			Rect rect = mLocation.getLocationByGrid(grid);
			Bitmap bitmap = null;
			switch (grid.getType()){
				case BOX:
					bitmap = mStyle.getBox(mContext);
					break;
				case GOAL:
					bitmap = mStyle.getGoal(mContext);
					break;
				case WALL:
					bitmap = mStyle.getWall(mContext);
					break;
				case BOY:
					bitmap = mStyle.getBoy(mContext);
					break;
				case FLOOR:
					bitmap = mStyle.getFloor(mContext);
					break;
			}
			mCanvas.drawBitmap(bitmap, null, rect, mPaint);
		}
	}
}
