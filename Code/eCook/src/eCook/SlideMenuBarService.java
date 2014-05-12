package eCook;

import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class SlideMenuBarService extends Service<Void> {

	@Override
	protected Task createTask() {
		return new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				System.out.println("Sleeping");
				TimeUnit.SECONDS.sleep(3);
				return null;
			}
			
		};
		 
		
	};

}
