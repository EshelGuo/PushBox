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

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	private MainGameView mMainGame;
	private ImageView mContorlUp;
	private ImageView mContorlLeft;
	private ImageView mContorlRight;
	private ImageView mContorlDown;
	private Button mBack;
	private Button mForward;
	private TextView mStep;
	private TextView mTitle;
	private TextView mAuthor;
	private TextView select_level;
	private int mLevel = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMainGame = findViewById(R.id.main_game);
		mContorlUp = findViewById(R.id.contorl_up);
		mContorlLeft = findViewById(R.id.contorl_left);
		mContorlRight = findViewById(R.id.contorl_right);
		mContorlDown = findViewById(R.id.contorl_down);
		mBack = findViewById(R.id.back);
		mForward = findViewById(R.id.forward);
		mStep = findViewById(R.id.step);
		mTitle = findViewById(R.id.title);
		mAuthor = findViewById(R.id.author);
		select_level = findViewById(R.id.select_level);

		HistoryListener historyListener = new HistoryListener();
		mBack.setOnClickListener(historyListener);
		mForward.setOnClickListener(historyListener);
		select_level.setOnClickListener(new SelectLevelListener());

		ContorlListener contorlListener = new ContorlListener();
		mContorlUp.setOnClickListener(contorlListener);
		mContorlLeft.setOnClickListener(contorlListener);
		mContorlRight.setOnClickListener(contorlListener);
		mContorlDown.setOnClickListener(contorlListener);


		newGame();
	}

	private class HistoryListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			if (v == mBack)
				mMainGame.backward();
			if (v == mForward)
				mMainGame.forward();
		}
	}

	private class SelectLevelListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			final String[] items = new String[Game.MAX_LEVEL];
			for (int i = 0; i < items.length; i++) {
				items[i] = String.valueOf(i+1);
			}
			new AlertDialog.Builder(v.getContext())
					.setTitle("选关")
					.setItems(items, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mLevel = Integer.parseInt(items[which]);
							newGame();
						}
					})
					.show();
		}
	}

	private void newGame() {
		final Game game = mMainGame.newGame(mLevel);
		game.setWinListener(new Game.WinListener() {
			@Override
			public void win() {
				final String result = game.getHistory().toString();
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("☺ 恭喜, 你赢了!!")
						.setMessage(result)
						.setCancelable(false)
						.setPositiveButton("下一关", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								mLevel++;
								if(mLevel > Game.MAX_LEVEL) {
									mLevel = Game.MAX_LEVEL;
									return;
								}
								newGame();
							}
						})
						.setNegativeButton("复制结果", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								copy(result);
								dialog.dismiss();
							}
						})
						.show();
			}
		});
		mTitle.setText(game.getLevelTitle());
		mAuthor.setText(game.getLevelAuthor());
		mStep.setText("步数: 0");
		game.setStepChangeListener(new History.StepChangeListener() {
			@Override
			public void stepChanged(int step) {
				mStep.setText("步数: " + step);
			}
		});
	}

	/**
	 * 复制内容到剪切板
	 *
	 * @param copyStr
	 * @return
	 */
	private boolean copy(String copyStr) {
		try {
			//获取剪贴板管理器
			ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			if(cm == null)
				return false;
			// 创建普通字符型ClipData
			ClipData mClipData = ClipData.newPlainText("Label", copyStr);
			// 将ClipData内容放到系统剪贴板里。
			cm.setPrimaryClip(mClipData);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private class ContorlListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			if (v == mContorlUp) {
				mMainGame.up();
				return;
			}
			if (v == mContorlDown) {
				mMainGame.down();
				return;
			}
			if (v == mContorlLeft) {
				mMainGame.left();
				return;
			}
			if (v == mContorlRight) {
				mMainGame.right();
			}
		}
	}
}
