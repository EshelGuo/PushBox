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
import android.graphics.BitmapFactory;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.SparseArray;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2019/10/30 19:24
 * <br>desc: 资源缓存管理
 */
public class Resource {
	private static SparseArray<Bitmap> cache = new SparseArray<>();

	public static Bitmap getResource(@NonNull Context context, @IdRes int id){
		if(id == 0)
			return null;
		Bitmap bitmap = cache.get(id);
		if(bitmap == null){
			bitmap = BitmapFactory.decodeResource(context.getResources(), id);
			cache.put(id,bitmap);
		}
		return bitmap;
	}
}
