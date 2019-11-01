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

package game.eshel.pushbox.style;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2019/10/30 19:24
 * <br>desc: 游戏皮肤
 */
public abstract class Style {

	@IdRes
	protected int wallResId;
	@IdRes
	protected int boyResId;
	@IdRes
	protected int goalResId;
	@IdRes
	protected int boxResId;
	@IdRes
	protected int floorResId;

	public Bitmap getWall(Context context){
		return Resource.getResource(context, wallResId);
	}

	public Bitmap getBoy(Context context){
		return Resource.getResource(context, boyResId);
	}

	public Bitmap getGoal(Context context){
		return Resource.getResource(context, goalResId);
	}

	public Bitmap getBox(Context context){
		return Resource.getResource(context, boxResId);
	}

	public Bitmap getFloor(Context context){
		return Resource.getResource(context, floorResId);
	}
}
