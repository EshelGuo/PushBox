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

import game.eshel.pushbox.R;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2019/11/1 16:33
 * <br>desc: IC 样式
 */
public class ICStyle extends Style{

	public ICStyle() {
		wallResId = R.drawable.p_wall;
		boyResId = R.drawable.p_player;
		goalResId = R.drawable.p_goal;
		boxResId = R.drawable.p_box;
		floorResId = R.drawable.p_floor;
	}
}
