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
 * <br>createTime: 2019/10/30 20:52
 * <br>desc: 计步器
 */
public class History {
	//回退栈
//	private Stack<Step> mBackStack = new Stack<>();
	//前进栈
//	private Stack<Step> mForwardStack = new Stack<>();

	private Step firstStep;
	private Step currentStep;
	private int size;
	private StepChangeListener mStepChangeListener;

	public Step saveBefore(Grid boy){
		Step step = new Step(boy);
		linkStep(step);
		size++;
		if(mStepChangeListener != null) mStepChangeListener.stepChanged(getSteps());
		return step;
	}

	public Step saveBefore(Grid boy, Grid box){
		Step step = new Step(boy, box);
		linkStep(step);
		size++;
		if(mStepChangeListener != null) mStepChangeListener.stepChanged(getSteps());
		return step;
	}

	public void saveAfter(Step step){
		step.after();
	}

	// 回退
	public void backward(){
		if(currentStep == null)
			return;
		boolean success = currentStep.backward();
		if(success){
			size--;
			if(mStepChangeListener != null) mStepChangeListener.stepChanged(getSteps());
			Step last = currentStep.last;
			if(last == null)
				return;
			currentStep = last;
		}
	}

	//前进
	public void forward(){
		if(currentStep == null)
			return;
		boolean success = currentStep.forward();
		if(success){
			size++;
			if(mStepChangeListener != null) mStepChangeListener.stepChanged(getSteps());
			Step next = currentStep.next;
			if(next == null)
				return;
			currentStep = next;
		}
	}

	public void setStepChangeListener(StepChangeListener stepChangeListener) {
		mStepChangeListener = stepChangeListener;
	}

	private void linkStep(Step step) {
		if (firstStep == null) {
			firstStep = step;
			currentStep = firstStep;
		} else {
			currentStep.next = step;
			step.last = currentStep;
			currentStep = step;
		}
	}

	public int getSteps(){
		return size;
	}

	public interface StepChangeListener{
		void stepChanged(int step);
	}
}
